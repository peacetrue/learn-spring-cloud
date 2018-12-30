package com.github.peaceture.learn.spring.cloud;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author xiayx
 */
@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
@EnableHystrix
@EnableHystrixDashboard
@RefreshScope
public class ConsumerRibbonApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerRibbonApplication.class, args);
    }

    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Service
    public static class HiServiceImpl implements HiService {

        @Autowired
        RestTemplate restTemplate;
        @Autowired
        private Environment env;

        @HystrixCommand(fallbackMethod = "hiError")
        public String hi(String name) {
            String result = restTemplate.getForObject("http://PROVIDER/hi?name=" + name, String.class);
            return HiService.hi(env, name) + ", result: {" + result + "}";
        }

        public String hiError(String name) {
            return HiService.hi(env, name) + ", error: true";
        }

    }


//    @Bean
//    public Sampler defaultSampler() {
//        return Sampler.ALWAYS_SAMPLE;
//    }
}
