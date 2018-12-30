package com.github.peaceture.learn.spring.cloud;

import org.springframework.core.env.Environment;

/**
 * @author xiayx
 */
public interface HiService {

    static String hi(String... args) {
        return String.format("application: %s, port: %s, bus: %s, input: %s", args);
    }

    static String hi(Environment env, String input) {
        return hi(
                env.getProperty("spring.application.name"),
                env.getProperty("server.port"),
                env.getProperty("test.cloud.bus.enable"),
                input
        );
    }

    String hi(String name);
}
