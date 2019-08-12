package com.paul.urule.springboot.test;

import com.alibaba.fastjson.JSON;
import com.bstek.urule.Utils;
import com.bstek.urule.action.ActionValue;
import com.bstek.urule.runtime.KnowledgePackage;
import com.bstek.urule.runtime.KnowledgeSession;
import com.bstek.urule.runtime.KnowledgeSessionFactory;
import com.bstek.urule.runtime.response.RuleExecutionResponse;
import com.bstek.urule.runtime.service.KnowledgeService;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.paul.urule.springboot.model.Customer;

import java.util.List;

/**
 * Copyright ?2019 广州智数信息技术有限公司. All rights reserved.
 *
 * @className
 * @Description
 * @auther FengZhi
 * @email 2045532295@qq.com
 * @create 2019-08-11 23:29
 */

public class KnowledgePackageTest {


    public static  String  doTest() throws  Exception{
        //从Spring中获取KnowledgeService接口实例
        KnowledgeService service = (KnowledgeService) Utils.getApplicationContext().getBean(KnowledgeService.BEAN_ID);
        //通过KnowledgeService接口获取指定的资源包"projectName/test123"
        KnowledgePackage knowledgePackage = service.getKnowledge("demo/test1");
        //通过取到的KnowledgePackage对象创建KnowledgeSession对象
        KnowledgeSession session = KnowledgeSessionFactory.newKnowledgeSession(knowledgePackage);

        Customer customer = new Customer();
        customer.setAge(20);
        customer.setLevel(3);
        customer.setMarried(true);

        session.insert(customer);
        RuleExecutionResponse ruleExecutionResponse = session.fireRules();
        List<ActionValue> actionValueList =  ruleExecutionResponse.getActionValues();
        String result = JSON.toJSONString(actionValueList);
        return result;
    }

}
