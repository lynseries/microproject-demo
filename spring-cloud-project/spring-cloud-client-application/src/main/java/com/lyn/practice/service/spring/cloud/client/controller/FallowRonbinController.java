package com.lyn.practice.service.spring.cloud.client.controller;

import com.lyn.practice.service.spring.cloud.client.annotation.ConsumerLoadBalanced;
import com.lyn.practice.service.spring.cloud.client.interceptor.LoadBalancedRequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RestController
public class FallowRonbinController {


    @Autowired
    @ConsumerLoadBalanced
    private RestTemplate lbRestTemplate;

    @GetMapping(value = "invoke/lb/{serviceId}/say")
    public String invokeSay(@PathVariable String serviceId, @RequestParam("message") String message) {

        String targetUrl = "/" + serviceId + "/say?message=" + message;

        String result = lbRestTemplate.getForObject(targetUrl, String.class);

        return result;
    }

    @Bean
    @Qualifier
    public ClientHttpRequestInterceptor interceptor() {
        return new LoadBalancedRequestInterceptor();
    }

    @Bean
    @ConsumerLoadBalanced
    public RestTemplate lbRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate;
    }

    @Bean
    @Autowired
    public Object customRest(@ConsumerLoadBalanced Collection<RestTemplate> restTemplates, @Qualifier ClientHttpRequestInterceptor interceptor) {
        restTemplates.forEach(r ->
                r.setInterceptors(Arrays.asList(interceptor))
        );

        return new Object();
    }


}
