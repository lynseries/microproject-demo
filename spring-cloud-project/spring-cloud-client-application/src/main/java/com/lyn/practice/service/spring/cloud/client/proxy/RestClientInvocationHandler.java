package com.lyn.practice.service.spring.cloud.client.proxy;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

public class RestClientInvocationHandler implements InvocationHandler {

    private String serverAppName;

    private BeanFactory beanFactory;

    public RestClientInvocationHandler(String serverAppName,BeanFactory beanFactory){
        this.serverAppName = serverAppName;
        this.beanFactory = beanFactory;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        GetMapping requestMapping = AnnotationUtils.findAnnotation(method, GetMapping.class);

        RestTemplate restTemplate = (RestTemplate) beanFactory.getBean("lbRestTemplate");
        AtomicReference<Object> result = new AtomicReference<>("");

        if(requestMapping!=null){
            String[] uris = requestMapping.value();
            Class<?>[] parameterTypes = method.getParameterTypes();
            Annotation[][] parameterAnnotations = method.getParameterAnnotations();
            final StringBuffer queryStrBuf = new StringBuffer();

            for(int i=0;i<parameterAnnotations.length;i++){
                Annotation[] annotations = parameterAnnotations[i];
                boolean hasQuery = false;
                StringBuffer expression = new StringBuffer();
                for(Annotation annotation:annotations){
                    if(annotation instanceof RequestParam){
                        hasQuery = true;
                        RequestParam requestParam = (RequestParam) annotation;
                        expression.append(requestParam.value()).append("=").append(args[i]);
                    }
                }
                if(i == 0 && hasQuery){
                    queryStrBuf.append("?").append(expression.toString());
                }else if(hasQuery){
                    queryStrBuf.append("&").append(expression.toString());
                }

            }



            Stream.of(uris).forEach(uri ->{
                StringBuffer hostAndUri = new StringBuffer();
                hostAndUri.append(serverAppName)
                        .append(uri);
                hostAndUri.append(queryStrBuf.toString());

                String targetUrl = hostAndUri.toString();
                System.out.printf("目标地址:"+targetUrl);

                result.set(restTemplate.getForObject(targetUrl, method.getReturnType()));

            });
        }



        return result.get();
    }


}
