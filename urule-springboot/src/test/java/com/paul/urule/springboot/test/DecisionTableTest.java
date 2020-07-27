package com.paul.urule.springboot.test;

import com.bstek.urule.Utils;
import com.bstek.urule.runtime.KnowledgePackage;
import com.bstek.urule.runtime.KnowledgeSession;
import com.bstek.urule.runtime.KnowledgeSessionFactory;
import com.bstek.urule.runtime.service.KnowledgeService;
import com.paul.urule.springboot.Application;
import com.paul.urule.springboot.model.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

/**
 * Copyright ?2019 广州智数信息技术有限公司. All rights reserved.
 *
 * @className
 * @Description
 * @auther FengZhi
 * @email 2045532295@qq.com
 * @create 2019-08-13 9:36
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@ImportResource({"classpath:urule-console-context.xml"})
public class DecisionTableTest {

    //测试决策表知识包
    @Test
    public void doTest1() throws IOException {
        KnowledgeService knowledgeService = (KnowledgeService) Utils.getApplicationContext().getBean(KnowledgeService.BEAN_ID);
        KnowledgePackage knowledgePackage = knowledgeService.getKnowledge("demo/决策表");
        KnowledgeSession knowledgeSession = KnowledgeSessionFactory.newKnowledgeSession(knowledgePackage);

        Customer customer  = new Customer();
        customer.setMarried(true);
        customer.setHouse(true);
        customer.setCar(true);
        knowledgeSession.insert(customer);
        knowledgeSession.fireRules();
        String name= customer.getName();
        System.out.println("name的值为:"+ name);
    }

    //测试交叉决策表
    @Test
    public void doTest2() throws IOException {
        KnowledgeService knowledgeService = (KnowledgeService) Utils.getApplicationContext().getBean(KnowledgeService.BEAN_ID);
        KnowledgePackage knowledgePackage = knowledgeService.getKnowledge("demo/交叉决策表");
        KnowledgeSession knowledgeSession = KnowledgeSessionFactory.newKnowledgeSession(knowledgePackage);

        Customer customer  = new Customer();
        //Parameter parameter = new Parameter();
        customer.setMarried(true);
        customer.setHouse(true);
        customer.setCar(true);
        customer.setGender(true);
        customer.setAge(18);
        knowledgeSession.insert(customer);
        knowledgeSession.fireRules();
        String score = (String) knowledgeSession.getParameter("score");
        System.out.println("score的值为:"+ score);
        //Integer score1 = parameter.getScore();
        //System.out.println("score1的值为:"+ score1);
    }

    //测试交叉决策树
    @Test
    public void doTest3() throws IOException {
        KnowledgeService knowledgeService = (KnowledgeService) Utils.getApplicationContext().getBean(KnowledgeService.BEAN_ID);
        KnowledgePackage knowledgePackage = knowledgeService.getKnowledge("demo/决策树");
        KnowledgeSession knowledgeSession = KnowledgeSessionFactory.newKnowledgeSession(knowledgePackage);

        Customer customer  = new Customer();
        //Parameter parameter = new Parameter();
        customer.setMarried(true);
        customer.setAge(17);
        knowledgeSession.insert(customer);
        knowledgeSession.fireRules();
        //必须要这样取值
        Integer level = customer.getLevel();
        //这样取值为null
//        Integer level = (Integer) knowledgeSession.getParameter("level");
//        Assert.assertEquals();
        System.out.println("level:" + level);
    }


}
