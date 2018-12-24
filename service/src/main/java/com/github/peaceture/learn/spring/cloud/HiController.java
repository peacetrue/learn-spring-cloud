package com.github.peaceture.learn.spring.cloud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author xiayx
 */
@Controller
public class HiController {

    @Autowired
    private HiService hiService;

    @ResponseBody
    @RequestMapping("/hi")
    public String hi(@RequestParam(value = "name", defaultValue = "an ning") String name) {
        return hiService.hi(name);
    }
}
