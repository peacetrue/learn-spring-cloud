package com.github.peaceture.learn.spring.cloud;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author xiayx
 */
@SpringBootApplication
@RestController
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @RequestMapping("/fallback")
    public Mono<String> fallback() {
        return Mono.just("fallback");
    }

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        String httpUri = "http://httpbin.org:80";
        return builder.routes()
                .route(p -> p
                        .path("/get")
                        .filters(f -> f.addRequestHeader("Hello", "World"))
                        .uri(httpUri).order(1))
                .route(p -> p
                        .host("*.hystrix.com")
                        .filters(f -> f.hystrix(config -> config.setName("mycmd").setFallbackUri("forward:/fallback")))
                        .uri(httpUri)
                        .order(2))
                .build();
    }

    public static class HostAddrKeyResolver implements KeyResolver {
        private Logger logger = LoggerFactory.getLogger(getClass());

        @Override
        public Mono<String> resolve(ServerWebExchange exchange) {
            String hostAddress = exchange.getRequest().getRemoteAddress().getAddress().getHostAddress();
            logger.info("hostAddress: {}", hostAddress);
            return Mono.just(hostAddress);
        }
    }

    @Bean
    public HostAddrKeyResolver hostAddrKeyResolver() {
        return new HostAddrKeyResolver();
    }

}
