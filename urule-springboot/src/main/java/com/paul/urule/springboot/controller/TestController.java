package com.paul.urule.springboot.controller;

import cn.hutool.core.lang.Console;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bstek.urule.RuleException;
import com.bstek.urule.console.DefaultUser;
import com.bstek.urule.model.rule.RuleSet;
import com.bstek.urule.parse.deserializer.RuleSetDeserializer;
import com.paul.urule.springboot.test.KnowledgePackageTest;
import com.paul.urule.springboot.utils.LoadXmlUtil;
import com.paul.urule.springboot.utils.WebApiResponse;
import com.paul.urule.springboot.utils.cpt.DecisionTreeUtil;
import com.paul.urule.springboot.utils.cpt.RuleSetUtil;
import com.paul.urule.springboot.vo.RuleSetInfoResponseVo;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
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

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private DecisionTreeUtil decisionTreeUtil;

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

    @GetMapping(value = "decisionTree/info")
    @ResponseBody
    public WebApiResponse getDecisionTreeInfo() throws DocumentException {
        Map<String, Object> content = decisionTreeUtil.testDecisionTreeInfo();
        logger.info("content:{}", JSON.toJSONString(content));
        return WebApiResponse.success(content);
    }


    @RequestMapping(value = "getScoreCardInfo",method = RequestMethod.GET)
    @ResponseBody
    public List<Object> getScoreCardInfo(){
        Map<String,Object> params = new HashMap<>();
        params.put("files","/demo/评分卡.sc");
        String body = null;
        try {
            body = HttpUtil.post("http://localhost:8095/risk" + "/urule/common/loadXml", params);
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
            body = HttpUtil.post("http://localhost:8095/risk" + "/urule/common/loadXml", params);
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


    @RequestMapping(value = "getRuleInfo",method = RequestMethod.GET)
    @ResponseBody
    public WebApiResponse getRuleInfo(@RequestParam("name")String name){
        Map<String,Object> params = new HashMap<>();
        params.put("files", StrUtil.format("/demo/{}.rs.xml", name));
        String body = null;
        try {
            body = HttpUtil.post("http://localhost:8095/risk" + "/urule/common/loadXml", params);
        } catch (Exception e) {
            logger.error("评分卡指标集加载异常，原因：",e);
            throw  new RuleException();
        }
//        List<Object> result = null;
        List<Object> ruleContentList = null;
        if (StringUtils.isNotBlank(body)){
//            result = JSONObject.parseObject(body, List.class);
//            Map<String, Object> dataMap = (Map<String, Object>) result.get(0);
//            result = RuleSetUtil.simplifyRuleSetContent(result);
            ruleContentList = JSON.parseObject(body, List.class);
        }
//        RuleSetInfoResponseVo ruleSetInfoResponseVo = new RuleSetInfoResponseVo();
//        ruleSetInfoResponseVo.setRules(result);
        logger.info("获取的规则详情报文为：{}", JSONUtil.toJsonPrettyStr(ruleContentList));
        return WebApiResponse.success(ruleContentList);
    }


    @RequestMapping(value = "ruleSet/infos",method = RequestMethod.GET)
    @ResponseBody
    public WebApiResponse getRuleSetDetailInfos() throws DocumentException {

        String content = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "\n" +
                "<rule-set>\n" +
                "  <import-variable-library path=\"jcr:/demo/指标库.vl.xml\"/>\n" +
                "  <remark><![CDATA[]]></remark>\n" +
                "  <rule name=\"rule\">\n" +
                "    <remark><![CDATA[]]></remark>\n" +
                "    <if>\n" +
                "      <and>\n" +
                "        <atom op=\"GreaterThen\">\n" +
                "          <left var-category=\"结果\" var=\"approve\" var-label=\"决策表结果\" datatype=\"String\" type=\"variable\"/>\n" +
                "          <value content=\"2121\" type=\"Input\"/>\n" +
                "        </atom>\n" +
                "        <atom op=\"GreaterThen\">\n" +
                "          <left var-category=\"指标库\" var=\"SCqry0006\" var-label=\"手机状态\" datatype=\"String\" type=\"variable\"/>\n" +
                "          <value content=\"2121\" type=\"Input\"/>\n" +
                "        </atom>\n" +
                "      </and>\n" +
                "    </if>\n" +
                "    <then/>\n" +
                "    <else/>\n" +
                "  </rule>\n" +
                "</rule-set>\n";
        Document document = DocumentHelper.parseText(content);
        Element ruleSetElement = document.getRootElement();
        RuleSetDeserializer ruleSetDeserializer = (RuleSetDeserializer) applicationContext.getBean(RuleSetDeserializer.BEAN_ID);
        RuleSet ruleSet = ruleSetDeserializer.deserialize(ruleSetElement);
        List<Object> resultList = RuleSetUtil.simplifyRuleSetContent1(ruleSet);
        RuleSetInfoResponseVo ruleSetInfoResponseVo = new RuleSetInfoResponseVo();
        ruleSetInfoResponseVo.setRules(resultList);
        return WebApiResponse.success(ruleSetInfoResponseVo);
    }


    @RequestMapping(value = "ruleSet/info",method = RequestMethod.GET)
    @ResponseBody
    public WebApiResponse getRuleSetDetailInfo() throws DocumentException {

        String content = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "\n" +
                "<rule-set>\n" +
                "  <import-variable-library path=\"jcr:/demo/指标库.vl.xml\"/>\n" +
                "  <remark><![CDATA[]]></remark>\n" +
                "  <rule name=\"rule\">\n" +
                "    <remark><![CDATA[]]></remark>\n" +
                "    <if>\n" +
                "      <and>\n" +
                "        <atom op=\"GreaterThen\">\n" +
                "          <left var-category=\"结果\" var=\"approve\" var-label=\"决策表结果\" datatype=\"String\" type=\"variable\"/>\n" +
                "          <value content=\"2121\" type=\"Input\"/>\n" +
                "        </atom>\n" +
                "        <atom op=\"GreaterThen\">\n" +
                "          <left var-category=\"指标库\" var=\"SCqry0006\" var-label=\"手机状态\" datatype=\"String\" type=\"variable\"/>\n" +
                "          <value content=\"2121\" type=\"Input\"/>\n" +
                "        </atom>\n" +
                "      </and>\n" +
                "    </if>\n" +
                "    <then/>\n" +
                "    <else/>\n" +
                "  </rule>\n" +
                "</rule-set>\n";
        Document document = DocumentHelper.parseText(content);
        Element ruleSetElement = document.getRootElement();
        RuleSetDeserializer ruleSetDeserializer=(RuleSetDeserializer)applicationContext.getBean(RuleSetDeserializer.BEAN_ID);
        RuleSet ruleSet = ruleSetDeserializer.deserialize(ruleSetElement);
        //String json1 = JSON.toJSONString(ruleSetMap, SerializerFeature.MapSortField);
        List<Object> dataList = new ArrayList<>();
        dataList.add(ruleSet);
        String json1 = JSON.toJSONString(dataList);
        Console.log("json1:{}", json1);
        List<Object> ruleSetList = JSON.parseObject(json1, List.class);
//        List<Object> resultList = RuleSetUtil.simplifyRuleSetContent(ruleSetList);
        RuleSetInfoResponseVo ruleSetInfoResponseVo = new RuleSetInfoResponseVo();
        ruleSetInfoResponseVo.setRules(ruleSetList);
        return WebApiResponse.success(ruleSetInfoResponseVo);
    }
    String json = "{\n" +
            "    \"libraries\": [\n" +
            "        {\n" +
            "            \"path\": \"jcr:/demo/指标库.vl.xml\",\n" +
            "            \"type\": \"Variable\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"remark\": \"\",\n" +
            "    \"rules\": [\n" +
            "        {\n" +
            "            \"lhs\": {\n" +
            "                \"criterion\": {\n" +
            "                    \"criterions\": [\n" +
            "                        {\n" +
            "                            \"id\": \"[变量]结果.决策表结果【大于】[字符]2121\",\n" +
            "                            \"left\": {\n" +
            "                                \"id\": \"[变量]结果.决策表结果\",\n" +
            "                                \"leftPart\": {\n" +
            "                                    \"datatype\": \"String\",\n" +
            "                                    \"id\": \"[变量]结果.决策表结果\",\n" +
            "                                    \"variableCategory\": \"结果\",\n" +
            "                                    \"variableLabel\": \"决策表结果\",\n" +
            "                                    \"variableName\": \"approve\"\n" +
            "                                },\n" +
            "                                \"type\": \"variable\"\n" +
            "                            },\n" +
            "                            \"op\": \"GreaterThen\",\n" +
            "                            \"parent\": {\n" +
            "                                \"$ref\": \"$.rules[0].lhs.criterion\"\n" +
            "                            },\n" +
            "                            \"value\": {\n" +
            "                                \"content\": \"2121\",\n" +
            "                                \"id\": \"[字符]2121\",\n" +
            "                                \"valueType\": \"Input\"\n" +
            "                            }\n" +
            "                        },\n" +
            "                        {\n" +
            "                            \"id\": \"[变量]指标库.手机状态【大于】[字符]2121\",\n" +
            "                            \"left\": {\n" +
            "                                \"id\": \"[变量]指标库.手机状态\",\n" +
            "                                \"leftPart\": {\n" +
            "                                    \"datatype\": \"String\",\n" +
            "                                    \"id\": \"[变量]指标库.手机状态\",\n" +
            "                                    \"variableCategory\": \"指标库\",\n" +
            "                                    \"variableLabel\": \"手机状态\",\n" +
            "                                    \"variableName\": \"SCqry0006\"\n" +
            "                                },\n" +
            "                                \"type\": \"variable\"\n" +
            "                            },\n" +
            "                            \"op\": \"GreaterThen\",\n" +
            "                            \"parent\": {\n" +
            "                                \"$ref\": \"$.rules[0].lhs.criterion\"\n" +
            "                            },\n" +
            "                            \"value\": {\n" +
            "                                \"content\": \"2121\",\n" +
            "                                \"id\": \"[字符]2121\",\n" +
            "                                \"valueType\": \"Input\"\n" +
            "                            }\n" +
            "                        }\n" +
            "                    ],\n" +
            "                    \"junctionType\": \"and\"\n" +
            "                }\n" +
            "            },\n" +
            "            \"loopRule\": false,\n" +
            "            \"name\": \"rule\",\n" +
            "            \"other\": {},\n" +
            "            \"remark\": \"\",\n" +
            "            \"rhs\": {\n" +
            "                \"actions\": []\n" +
            "            }\n" +
            "        }\n" +
            "    ]\n" +
            "}";
}
