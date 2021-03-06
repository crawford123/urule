package com.paul.urule.springboot.controller;

import cn.hutool.core.lang.Console;
import com.alibaba.fastjson.JSON;
import com.bstek.urule.model.rule.RuleSet;
import com.bstek.urule.parse.deserializer.RuleSetDeserializer;
import com.paul.urule.springboot.Application;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;

/**
 * Copyright © 2020 广州智数信息技术有限公司. All rights reserved.
 *
 * @ClassName
 * @Description
 * @Auther FengZhi
 * @Email 2045532295@qq.com
 * @Create 2020-01-02 18:26
 * @Version
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@ImportResource({"classpath:urule-console-context.xml"})
public class TestControllerTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void getRuleInfo() throws DocumentException {

        String ruleContent = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "\n" +
                "<rule-set>\n" +
                "  <import-variable-library path=\"jcr:/demo11/指标库11.vl.xml\"/>\n" +
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
        Document document = DocumentHelper.parseText(ruleContent);
        Element ruleSetElement = document.getRootElement();
        RuleSetDeserializer ruleSetDeserializer=(RuleSetDeserializer)applicationContext.getBean(RuleSetDeserializer.BEAN_ID);
        RuleSet ruleSet = ruleSetDeserializer.deserialize(ruleSetElement);
        //将ruleSet转换为全部为map的数据结构
        Map<String, Object> ruleSetMap = JSON.parseObject(JSON.toJSONString(ruleSet));
        Console.log("ruleSetMap:{}", ruleSetMap);



    }
}