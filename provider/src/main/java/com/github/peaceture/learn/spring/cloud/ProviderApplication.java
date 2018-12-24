package com.github.peaceture.learn.spring.cloud;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 * @author xiayx
 */
@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
@EnableHystrix
@EnableHystrixDashboard
@EnableCircuitBreaker
public class ProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProviderApplication.class, args);
    }

    @Service
    @Primary
    public static class HiServiceProxy implements HiService {
        @Autowired
        private HiService hiService;

        @Override
        @HystrixCommand(fallbackMethod = "hiError")
        public String hi(String name) {
            return hiService.hi(name);
        }
        public String hiError(String name) {
            return "hi! " + name + " error";
        }
    }
}
