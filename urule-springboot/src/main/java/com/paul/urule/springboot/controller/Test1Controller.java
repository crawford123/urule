package com.paul.urule.springboot.controller;

import com.paul.urule.springboot.service.TestService;
import com.paul.urule.springboot.utils.WebApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright © 2020 广州智数信息技术有限公司. All rights reserved.
 *
 * @ClassName
 * @Description
 * @Auther FengZhi
 * @Email 2045532295@qq.com
 * @Create 2020-03-25 23:16
 * @Version
 */

@RestController
public class Test1Controller {

    @Autowired
    private TestService testService;

    @RequestMapping(value = "testThreadPool", method = RequestMethod.POST)
    public WebApiResponse testThreadPool(@RequestBody Map<String, Object> dataMap) throws Exception {
        Object result = testService.testThreadPool(dataMap);
        return WebApiResponse.success(result);
    }


    @RequestMapping(value = "testThreadPool2", method = RequestMethod.POST)
    public WebApiResponse testThreadPool2(@RequestBody Map<String, Object> dataMap) throws Exception {
        Object result = testService.testThreadPool2(dataMap);
        return WebApiResponse.success(result);
    }

    @RequestMapping(value = "testPost", method = {RequestMethod.POST, RequestMethod.PUT})
    public WebApiResponse testPost(@RequestBody Map<String, Object> dataMap) {
        return WebApiResponse.success(dataMap);
    }

    @RequestMapping(value = "testGet", method = RequestMethod.GET)
    public WebApiResponse testGet() {
        Map<String, Object> dataMap = new HashMap<>(16);
        dataMap.put("name", "Tom");
        dataMap.put("gender", "male");
        return WebApiResponse.success(dataMap);
    }
}
