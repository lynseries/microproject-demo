package com.lyn.practice.gateway.loadbanlance;

import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.RoundRobinRule;
import com.netflix.loadbalancer.Server;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ZookeeperLoadBalancer extends BaseLoadBalancer {

    private DiscoveryClient discoveryClient;

    private Map<String,BaseLoadBalancer> loadBalancerMap = new ConcurrentHashMap<>();

    public ZookeeperLoadBalancer(DiscoveryClient discoveryClient){
        this.discoveryClient = discoveryClient;
        this.updateServers();
    }

    @Override
    public Server chooseServer(Object key) {
        if(key instanceof String){
            String keyStr = String.valueOf(key);
            BaseLoadBalancer loadBalancer = loadBalancerMap.get(keyStr);
            return loadBalancer.chooseServer(keyStr);
        }
        return super.chooseServer(key);
    }



    @Scheduled(fixedRate = 5000)
    public void updateServers(){
        List<String> serviceIds =  discoveryClient.getServices();
        for(String serviceId:serviceIds){
            BaseLoadBalancer loadBalancer = new BaseLoadBalancer();
            List<ServiceInstance> instances = discoveryClient.getInstances(serviceId);
            for(ServiceInstance instance:instances){
                Server server = new Server(instance.isSecure()?"https":"http",instance.getHost(),instance.getPort());
                loadBalancer.addServer(server);
            }
            loadBalancer.setRule(new RoundRobinRule(loadBalancer));
            this.loadBalancerMap.put(serviceId,loadBalancer);
        }
    }
}
