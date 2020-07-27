package com.paul.urule.springboot.service;

import cn.hutool.core.lang.Console;
import com.bstek.urule.Utils;
import com.bstek.urule.runtime.KnowledgePackage;
import com.bstek.urule.runtime.KnowledgeSession;
import com.bstek.urule.runtime.KnowledgeSessionFactory;
import com.bstek.urule.runtime.service.KnowledgeService;
import com.paul.urule.springboot.model.Customer;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Copyright © 2020 广州智数信息技术有限公司. All rights reserved.
 *
 * @ClassName
 * @Description
 * @Auther FengZhi
 * @Email 2045532295@qq.com
 * @Create 2020-07-08 11:21
 * @Version
 */


@Service
public class ScoreCardService {

    public  void testScoreCard() throws IOException {
        //从Spring中获取KnowledgeService接口实例
        KnowledgeService service = (KnowledgeService) Utils.getApplicationContext().getBean(KnowledgeService.BEAN_ID);
        //通过KnowledgeService接口获取指定的资源包"projectName/test123"
        //执行规则集
        KnowledgePackage knowledgePackage = service.getKnowledge("demo/评分卡联合条件测试");
        //通过取到的KnowledgePackage对象创建KnowledgeSession对象
        KnowledgeSession session = KnowledgeSessionFactory.newKnowledgeSession(knowledgePackage);
//        GeneralEntity generalEntity = new GeneralEntity("无1");
//        generalEntity.put("SCqry0004", "male");
        //        session.insert(generalEntity);
        Customer customer = new Customer();
        customer.setMobile("110");
        session.insert(customer);
        //执行规则失败,需要分析原因
        session.fireRules();
        int level = customer.getLevel();
        Integer level1 = (Integer) session.getParameter("level");
        Console.log("level的值为：{}",  level);
//        BigDecimal score1 = (BigDecimal) session.getParameter("score");
//        Console.log("score的值为：{}",  score1);
    }

}
