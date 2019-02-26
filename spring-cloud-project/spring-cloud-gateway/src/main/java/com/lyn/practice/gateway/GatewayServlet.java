package com.lyn.practice.gateway;

import com.lyn.practice.gateway.loadbanlance.ZookeeperLoadBalancer;
import com.netflix.loadbalancer.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

@WebServlet(name = "gateway",urlPatterns = "/gateway/*")
public class GatewayServlet extends HttpServlet {

    @Autowired
    private ZookeeperLoadBalancer zookeeperLoadBalancer;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String pathInfo = req.getPathInfo();
        String queryString = req.getQueryString();

        if(pathInfo!=null){
            String[] appNameAndUri = StringUtils.split(pathInfo.substring(1),"/");
            if(appNameAndUri.length == 2){
                String appName = appNameAndUri[0];
                String serviceUri ="/"+appNameAndUri[1];

                Server server = zookeeperLoadBalancer.chooseServer(appName);

                String targetUrl = server.getScheme()+"://"+server.getHostPort()+"/"+serviceUri;

                if(StringUtils.hasText(queryString)){
                    targetUrl += "?"+queryString;
                }

                RestTemplate restTemplate = new RestTemplate();
                try {
                    RequestEntity<byte[]> requestEntity = this.createRequestEntity(req,targetUrl);
                    ResponseEntity<byte[]> results = restTemplate.exchange(requestEntity,byte[].class);
                    writeHeader(results,resp);
                    writeBody(results,resp);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void writeBody(ResponseEntity<byte[]> entity,HttpServletResponse response) throws IOException {
        response.getWriter().print(new String(entity.getBody()));
    }

    private void writeHeader(ResponseEntity<byte[]> entity,HttpServletResponse response){
         MultiValueMap<String,String> headers = entity.getHeaders();
         for(Map.Entry<String, List<String>> entry:headers.entrySet()){

             String keyName = entry.getKey();
             List<String> list = entry.getValue();
             for(String proValue:list){
                 response.addHeader(keyName,proValue);
             }
         }
    }

    private RequestEntity<byte[]> createRequestEntity(HttpServletRequest req,String url) throws URISyntaxException, IOException {

        String method = req.getMethod();
        HttpMethod httpMethod = HttpMethod.resolve(method);

        MultiValueMap<String,String> httpHeader = this.createHttpHeader(req);
        byte[] requestBody = this.createRequestBody(req);
        RequestEntity<byte[]> requestEntity = new RequestEntity(requestBody,httpHeader,httpMethod,new URI(url));
        return  requestEntity;
    }

    private MultiValueMap<String,String> createHttpHeader(HttpServletRequest req){
        MultiValueMap<String,String> header = new HttpHeaders();
        Enumeration<String> enumeration = req.getHeaderNames();
        while(enumeration.hasMoreElements()){
            String headerName = enumeration.nextElement();
            header.add(headerName,req.getHeader(headerName));
        }
        return header;
    }

    private byte[] createRequestBody(HttpServletRequest req)throws IOException{
        InputStream inputStream = req.getInputStream();
        return StreamUtils.copyToByteArray(inputStream);
    }
}
