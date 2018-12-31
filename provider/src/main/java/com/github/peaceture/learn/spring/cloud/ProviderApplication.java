package com.github.peaceture.learn.spring.cloud;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.core.env.Environment;
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
    public static class HiServiceImpl implements HiService {

        private Logger logger = LoggerFactory.getLogger(getClass());
        @Autowired
        private Environment env;

        @Override
        @HystrixCommand(fallbackMethod = "hiError")
        public String hi(String name) {
            if ("timeout".equals(name)) {
                try {
                    Thread.sleep(1000 * 10);
                } catch (InterruptedException e) {
                    logger.error("睡眠异常", e);
                }
            }
            return HiService.hi(env, name);
        }

        public String hiError(String name) {
            return HiService.hi(env, name) + ", error: true";
        }
    }

}
