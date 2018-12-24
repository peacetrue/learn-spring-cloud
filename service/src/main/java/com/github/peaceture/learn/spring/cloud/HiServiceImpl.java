package com.github.peaceture.learn.spring.cloud;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author xiayx
 */
public class HiServiceImpl implements HiService {

    @Value("${server.port}")
    private String port;

    @Override
    public String hi(String name) {
        return "hi " + name + " ,i am from port:" + port;
    }
}
