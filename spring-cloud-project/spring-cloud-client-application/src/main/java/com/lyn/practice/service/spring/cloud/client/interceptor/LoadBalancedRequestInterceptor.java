package com.lyn.practice.service.spring.cloud.client.interceptor;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.AbstractClientHttpResponse;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;


public class LoadBalancedRequestInterceptor implements ClientHttpRequestInterceptor {


    private Map<String, Set<String>> targetUrlsCache =  new ConcurrentHashMap<>();

    @Autowired
    private DiscoveryClient discoveryClient;

    @Scheduled(fixedRate = 10*1000)
    public void updateTargetUrls(){
        Map<String,Set<String>> newTargetUrlsCache = new ConcurrentHashMap<>();

        List<String> serviceIds = discoveryClient.getServices();
        serviceIds.forEach(serviceId->{
            Set<String> targetUrls = discoveryClient.getInstances(serviceId)
                    .stream()
                    .map(s->
                            s.isSecure()?
                                    "https://"+s.getHost()+":"+s.getPort()
                                    :"http://"+s.getHost()+":"+s.getPort()

                    )
                    .collect(Collectors.toSet());

            newTargetUrlsCache.put(serviceId,targetUrls);

        });

        this.targetUrlsCache = newTargetUrlsCache;

    }


    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        //  URI:  "/" + serviceName + "/say?message="
        URI requestUri = request.getURI();
        String path = requestUri.getPath();

        String[] parts = StringUtils.split(path.substring(1),"/",2);
        String serviceId = parts[0];
        Set<String> targetUrls = this.targetUrlsCache.get(serviceId);
        List<String> targetUrlList = new ArrayList<>(targetUrls);

        int index = new Random().nextInt(targetUrlList.size());
        String targetUrl = targetUrlList.get(index);

        String finalUrl = targetUrl+"/"+parts[1]+"?"+requestUri.getQuery();

        URL url = new URL(finalUrl);

        URLConnection urlConnection = url.openConnection();
        if(HttpURLConnection.class.isInstance(urlConnection)){
            return new LoadBalancedClientHttpResponse((HttpURLConnection) urlConnection);
        }
        return null ;
    }


    public static class LoadBalancedClientHttpResponse extends AbstractClientHttpResponse{

        private HttpURLConnection httpURLConnection;
        private HttpHeaders httpHeaders;

        private InputStream responseStream;


        public LoadBalancedClientHttpResponse(HttpURLConnection httpURLConnection){
            this.httpURLConnection = httpURLConnection;
        }


        @Override
        public int getRawStatusCode() throws IOException {
            return httpURLConnection.getResponseCode();
        }

        @Override
        public String getStatusText() throws IOException {
            return httpURLConnection.getResponseMessage();
        }

        @Override
        public void close() {
            if(this.responseStream!=null ){
                try {
                    StreamUtils.drain(responseStream);
                    this.responseStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public InputStream getBody() throws IOException {
            InputStream errorStream = this.httpURLConnection.getErrorStream();
            this.responseStream = (errorStream!=null?errorStream:this.httpURLConnection.getInputStream());
            return this.responseStream;
        }

        @Override
        public HttpHeaders getHeaders() {
            if (this.httpHeaders == null) {
                this.httpHeaders = new HttpHeaders();
                // Header field 0 is the status line for most HttpURLConnections, but not on GAE
                String name = this.httpURLConnection.getHeaderFieldKey(0);
                if (org.springframework.util.StringUtils.hasLength(name)) {
                    this.httpHeaders.add(name, this.httpURLConnection.getHeaderField(0));
                }
                int i = 1;
                while (true) {
                    name = this.httpURLConnection.getHeaderFieldKey(i);
                    if (!org.springframework.util.StringUtils.hasLength(name)) {
                        break;
                    }
                    this.httpHeaders.add(name, this.httpURLConnection.getHeaderField(i));
                    i++;
                }
            }
            return this.httpHeaders;
        }
    }


}
