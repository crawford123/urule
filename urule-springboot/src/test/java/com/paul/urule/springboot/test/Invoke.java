package com.paul.urule.springboot.test;

import com.alibaba.fastjson.JSON;
import com.bstek.urule.Utils;
import com.bstek.urule.action.ActionValue;
import com.bstek.urule.model.GeneralEntity;
import com.bstek.urule.model.rete.RuleData;
import com.bstek.urule.runtime.*;
import com.bstek.urule.runtime.response.FlowExecutionResponse;
import com.bstek.urule.runtime.response.RuleExecutionResponse;
import com.bstek.urule.runtime.rete.ReteInstance;
import com.bstek.urule.runtime.service.KnowledgeService;
import com.paul.urule.springboot.Application;
import com.paul.urule.springboot.model.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * Copyright ?2019 广州智数信息技术有限公司. All rights reserved.
 *
 * @className
 * @Description
 * @auther FengZhi
 * @email 2045532295@qq.com
 * @create 2019-08-11 22:37
 */


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@ImportResource({"classpath:urule-console-context.xml"})

public class Invoke {


    //测试知识包规则集
    //第一种方法
    @Test
    public void doTest() throws  Exception{
        //从Spring中获取KnowledgeService接口实例
        KnowledgeService service = (KnowledgeService) Utils.getApplicationContext().getBean(KnowledgeService.BEAN_ID);
        //通过KnowledgeService接口获取指定的资源包"projectName/test123"
        //执行规则集
        KnowledgePackage knowledgePackage = service.getKnowledge("demo/普通规则");
        //通过取到的KnowledgePackage对象创建KnowledgeSession对象
        KnowledgeSession session = KnowledgeSessionFactory.newKnowledgeSession(knowledgePackage);

        Customer customer = new Customer();
        customer.setAge(20);
        customer.setLevel(3);
        customer.setMarried(false);
        //customer.setName("");
     /*   GeneralEntity generalEntity = new GeneralEntity("com.paul.urule.springboot.model.Customer");
        generalEntity.put("age",20);*/
        //将业务数据对象customer插入到KnowledgeSession中
        session.insert(customer);
        List<RuleData> ruleDataList = session.getAllRuleData();
        List<ReteInstance> reteInstanceList = session.getReteInstanceList();
        List<KnowledgePackage> knowledgePackageList = session.getKnowledgePackageList();
        // System.out.println("ruleDataList的值为：" + ruleDataList);
            //执行所有满足条件的规则
            //执行规则流
            /*Map<String, Object> parameter = new HashMap<>();
            parameter.put("level", 5);
            parameter.put("name", "金牌会员");*/
            //触发规则时并设置参数
            RuleExecutionResponse ruleExecutionResponse = session.fireRules();
            List<ActionValue> actionValueList = ruleExecutionResponse.getActionValues();
            String reult = JSON.toJSONString(actionValueList);
            String name = (String) session.getParameter("name");
            System.out.println("reult的值为：" + reult);
            System.out.println("name的值为：" + name);
            System.out.println(customer);
            System.out.println("name的值为：" + customer.getName());


        //获取计算后的result值，要通过KnowledgeSession,而不能通过原来的parameter对象
//        String score = (String) session.getParameter("result");
//        System.out.println("score的值为：" + score);
    }

    //测试知识包规则集
    //第二种方法
    @Test
    public void doTest3() throws IOException {
        //从Spring中获取KnowledgeService接口实例
        KnowledgeService service = (KnowledgeService) Utils.getApplicationContext().getBean(KnowledgeService.BEAN_ID);
        //通过KnowledgeService接口获取指定的资源包"projectName/test123"
        //执行规则集
        KnowledgePackage knowledgePackage = service.getKnowledge("demo/普通规则");
        //通过取到的KnowledgePackage对象创建KnowledgeSession对象
        KnowledgeSession session = KnowledgeSessionFactory.newKnowledgeSession(knowledgePackage);

        GeneralEntity generalEntity = new GeneralEntity("com.paul.urule.springboot.model.Customer");
        generalEntity.put("age",20);
        generalEntity.put("level",3);
        generalEntity.put("married",true);
        //将业务数据对象generalEntity插入到KnowledgeSession中
        session.insert(generalEntity);
        session.fireRules();
        String name = (String) generalEntity.get("name");
        System.out.println("name的值为：" + name);
    }

    //测试规则流（决策流）
    @Test
    public void doTest1() throws IOException {
        //从Spring中获取KnowledgeService接口实例
        KnowledgeService service = (KnowledgeService) Utils.getApplicationContext().getBean(KnowledgeService.BEAN_ID);
        //通过KnowledgeService接口获取指定的资源包"projectName/test123"
        //执行规则集
        KnowledgePackage knowledgePackage = service.getKnowledge("demo/决策流");
        //通过取到的KnowledgePackage对象创建KnowledgeSession对象
        KnowledgeSession session = KnowledgeSessionFactory.newKnowledgeSession(knowledgePackage);

        Customer customer = new Customer();
        customer.setLevel(8);

        //将业务数据对象customer插入到KnowledgeSession中
        session.insert(customer);

        /*Map<String,Object> parameter = new HashMap<>();
        parameter.put("score",100);*/

        //开始规则流并设置参数
        //这里填写决策流id
        session.startProcess("flow-demo");

        //获取计算后的result值，要通过KnowledgeSession,而不能通过原来的parameter对象
        String name = (String) session.getParameter("name");
        System.out.println("name的值为：" + name);
        System.out.println("name的值为：" + customer.getName());
    }

    //批处理支持，开启多线程
    @Test
    public void doTest2() throws IOException {
        //从Spring中获取KnowledgeService接口实例
        KnowledgeService service=(KnowledgeService)Utils.getApplicationContext().getBean(KnowledgeService.BEAN_ID);
        //通过KnowledgeService接口获取指定的资源包"aaa"
        KnowledgePackage knowledgePackage=service.getKnowledge("demo/决策流");
        //通过取的KnowledgePackage对象创建BatchSession对象,在这个对象中，我们将开启5个线程，每个线程最多放置10个Bussiness接口实例运行
        BatchSession batchSession = KnowledgeSessionFactory.newBatchSession(knowledgePackage, 5, 10);

        for (int i = 0; i < 100; i++) {
            batchSession.addBusiness(new Business() {
                @Override
                public void execute(KnowledgeSession knowledgeSession) {
                    Customer customer = new Customer();
                    customer.setLevel(3);
                    //将业务数据对象Customer插入到KnowledgeSession中
                    knowledgeSession.insert(customer);
                    //knowledgeSession.fireRules();
                    FlowExecutionResponse flowExecutionResponse = knowledgeSession.startProcess("flow-demo");
                    List<RuleExecutionResponse> ruleExecutionResponseList = flowExecutionResponse.getRuleExecutionResponses();
                    //System.out.println("结果为：" + JSON.toJSONString(ruleExecutionResponseList));
                    System.out.println("name的值为：" + customer.getName());
                }
            });

        }
        //等待所有的线程执行完成，对于BatchSession调用来说，此行代码必不可少，否则将导致错误
        batchSession.waitForCompletion();

    }






}
