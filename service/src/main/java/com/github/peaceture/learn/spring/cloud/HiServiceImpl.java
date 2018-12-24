package com.github.peaceture.learn.spring.cloud;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author xiayx
 */
@Service
public class HiServiceImpl implements HiService {

    @Value("${server.port}")
    private String port;

    @Override
    public String hi(String name) {
        return "hi " + name + " ,i am from port:" + port;
    }
}
