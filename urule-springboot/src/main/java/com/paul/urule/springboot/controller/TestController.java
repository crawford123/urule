package com.paul.urule.springboot.controller;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.bstek.urule.console.DefaultUser;
import com.bstek.urule.exception.RuleException;
import com.paul.urule.springboot.test.KnowledgePackageTest;
import com.paul.urule.springboot.utils.LoadXmlUtil;
import com.paul.urule.springboot.utils.WebApiResponse;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright ?2019 广州智数信息技术有限公司. All rights reserved.
 *
 * @className
 * @Description
 * @auther FengZhi
 * @email 2045532295@qq.com
 * @create 2019-08-11 23:31
 */



@Controller
@RequestMapping("test")
public class TestController {


    @Value("${urule.api.url}")
    private String URULE_API_URL;

    private Logger logger = LoggerFactory.getLogger(TestController.class);

    @Resource
    private LoadXmlUtil loadXmlUtil;

    private static DefaultUser user = new DefaultUser();

    //urule用户默认权限
    static {
        user.setCompanyId("bstek");
        user.setUserName("admin");
        user.setAdmin(true);
    }



    @RequestMapping(value = "doTest")
    @ResponseBody
    public  String doTest() throws Exception {
        String result = KnowledgePackageTest.doTest();
        return result;
    }

    @GetMapping(value = "tests")
    @ResponseBody
    public String test(){
        return  "test";
    }


    @RequestMapping(value = "getRuleInfo",method = RequestMethod.GET)
    @ResponseBody
    public List<Object> getRuleInfo(){
        Map<String,Object> params = new HashMap<>();
        params.put("files","/demo/test5.rs.xml");
        String body = null;
        try {
            body = HttpUtil.post("http://localhost:8080" + "/urule/common/loadXml", params);
        } catch (Exception e) {
            logger.error("评分卡指标集加载异常，原因：",e);
            throw  new RuleException();
        }
        List<Object> result = null;
        if (StringUtils.isNotBlank(body)){
            result = JSONObject.parseObject(body, List.class);
        }
        return result;
    }


    @RequestMapping(value = "getDecisionFlowInfo",method = RequestMethod.GET)
    @ResponseBody
    public List<Object> getDecisionFlowInfo(){
        Map<String,Object> params = new HashMap<>();
        params.put("files","/demo/决策流1.rl.xml");
        String body = null;
        try {
            body = HttpUtil.post("http://localhost:8080" + "/urule/common/loadXml", params);
        } catch (Exception e) {
            logger.error("决策流指标集加载异常，原因：",e);
            throw  new RuleException();
        }
        List<Object> result = null;
        if (StringUtils.isNotBlank(body)){
            result = JSONObject.parseObject(body, List.class);
        }
        return result;
    }

    @RequestMapping(value = "getCptInto/{file}",method = RequestMethod.GET)
    @ResponseBody
    public WebApiResponse getDecisionFlowInfo(@PathVariable("file")String file) {

        List<Object> result = loadXmlUtil.loadXml("/demo/" + file);
        return WebApiResponse.success(result);
    }
}
