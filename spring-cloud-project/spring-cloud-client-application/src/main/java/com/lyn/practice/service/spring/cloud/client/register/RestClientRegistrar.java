package com.lyn.practice.service.spring.cloud.client.register;

import com.lyn.practice.service.spring.cloud.client.annotation.EnableRestClient;
import com.lyn.practice.service.spring.cloud.client.annotation.RestClient;
import com.lyn.practice.service.spring.cloud.client.proxy.RestClientInvocationHandler;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.type.AnnotationMetadata;

import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Stream;

public class RestClientRegistrar implements ImportBeanDefinitionRegistrar, BeanFactoryAware {

    private BeanFactory beanFactory;


    @Override
    public void registerBeanDefinitions(AnnotationMetadata metadata, BeanDefinitionRegistry registry) {

        Map<String,Object> restClientAttr =  metadata.getAnnotationAttributes(EnableRestClient.class.getName());

        Class<?>[] clientsClasses = (Class<?>[]) restClientAttr.get("clients");

        if(clientsClasses != null){
            this.registerRestClient(clientsClasses,registry);
        }


    }


    private void registerRestClient(Class<?>[] classes,BeanDefinitionRegistry registry){


        Stream.of(classes)
                .filter(Class::isInterface)
                .filter(interfaceClass -> interfaceClass.isAnnotationPresent(RestClient.class))
                .forEach(restClientClass -> {
                    ClassLoader classLoader = restClientClass.getClassLoader();
                    RestClient restClient = AnnotationUtils.findAnnotation(restClientClass,RestClient.class);
                    String serverAppName =  restClient.name();
                    Object proxy = Proxy.newProxyInstance(classLoader,new Class<?>[]{restClientClass},new RestClientInvocationHandler(serverAppName,beanFactory));
                    BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(RestClientFactoryBean.class);

                    builder.addConstructorArgValue(proxy);
                    builder.addConstructorArgValue(restClientClass);

                    String beanName = "restClient_"+serverAppName;
                    registry.registerBeanDefinition(beanName,builder.getBeanDefinition());
                });

    }



    public static class RestClientFactoryBean implements FactoryBean{

        private Object proxy;

        private Class<?> clazz;

        public RestClientFactoryBean(Object proxy,Class<?> clazz){
            this.proxy = proxy;
            this.clazz = clazz;
        }

        @Override
        public Object getObject() throws Exception {
            return proxy;
        }

        @Override
        public Class<?> getObjectType() {
            return clazz;
        }
    }




    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
