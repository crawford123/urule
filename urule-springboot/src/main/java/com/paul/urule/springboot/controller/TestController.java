package com.paul.urule.springboot.controller;

import com.paul.urule.springboot.test.KnowledgePackageTest;
import org.apache.velocity.app.event.implement.EscapeXmlReference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Copyright ?2019 广州智数信息技术有限公司. All rights reserved.
 *
 * @className
 * @Description
 * @auther FengZhi
 * @email 2045532295@qq.com
 * @create 2019-08-11 23:31
 */

@RestController
public class TestController {

    @RequestMapping(value = "doTest")
    public  String doTest() throws Exception {
        String result = KnowledgePackageTest.doTest();
        return result;
    }

    @RequestMapping(value = "tests")
    public String test(){
        return  "test";
    }
}
