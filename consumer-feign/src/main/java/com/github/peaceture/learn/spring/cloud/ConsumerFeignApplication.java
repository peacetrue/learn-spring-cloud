package com.github.peaceture.learn.spring.cloud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author xiayx
 */
@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
@EnableFeignClients
@EnableHystrix
@EnableHystrixDashboard
@RefreshScope
public class ConsumerFeignApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerFeignApplication.class, args);
    }

    @FeignClient(value = "provider", fallback = FallbackHiService.class)
    public interface FeignHiService extends HiService {
        @RequestMapping(value = "/hi", method = RequestMethod.GET)
        String hi(@RequestParam(value = "name") String name);
    }

    @Component
    public static class FallbackHiService implements FeignHiService {
        @Autowired
        private Environment env;

        public String hi(String name) {
            return HiService.hi(env, name) + ", error: true";
        }
    }

}
