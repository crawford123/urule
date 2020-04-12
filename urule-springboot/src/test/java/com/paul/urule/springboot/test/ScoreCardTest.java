package com.paul.urule.springboot.test;

import cn.hutool.core.lang.Console;
import com.alibaba.fastjson.JSON;
import com.bstek.urule.model.scorecard.ScorecardDefinition;
import com.bstek.urule.parse.deserializer.ScorecardDeserializer;
import com.paul.urule.springboot.Application;
import com.paul.urule.springboot.utils.cpt.ScoreCardUtil;
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

import java.io.IOException;
import java.util.Map;

/**
 * Copyright ?2019 广州智数信息技术有限公司. All rights reserved.
 *
 * @className
 * @Description
 * @auther FengZhi
 * @email 2045532295@qq.com
 * @create 2019-08-12 16:58
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@ImportResource({"classpath:urule-console-context.xml"})
public class ScoreCardTest {

    @Autowired
    private ApplicationContext applicationContext;


    //测试评分卡
    @Test
    public  void testScoreCard() throws IOException {
//
//        //从Spring中获取KnowledgeService接口实例
//        KnowledgeService service = (KnowledgeService) Utils.getApplicationContext().getBean(KnowledgeService.BEAN_ID);
//        //通过KnowledgeService接口获取指定的资源包"projectName/test123"
//        //执行规则集
//        KnowledgePackage knowledgePackage = service.getKnowledge("demo/评分卡");
//        //通过取到的KnowledgePackage对象创建KnowledgeSession对象
//        KnowledgeSession session = KnowledgeSessionFactory.newKnowledgeSession(knowledgePackage);
//        Customer customer = new Customer();
//        customer.setAge(12);
//        session.insert(customer);
//
//        //执行规则失败,需要分析原因
//        session.fireRules();
//        String score = (String) session.getParameter("score");
//        System.out.println("score的值为：" + score);
    }

    @Test
    public void test() throws DocumentException {

        String content = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "\n" +
                "<scorecard weight-support=\"false\" name=\"评分卡\" attr-col-width=\"200\" attr-col-name=\"属性\" attr-col-category=\"会员\" condition-col-width=\"220\" condition-col-name=\"条件\" score-col-width=\"180\" score-col-name=\"分值\" scoring-type=\"weightsum\" assign-target-type=\"parameter\" var-category=\"参数\" var=\"score\" var-label=\"得分\" datatype=\"String\">\n" +
                "  <remark><![CDATA[测试评分卡内容。]]></remark>\n" +
                "  <import-parameter-library path=\"jcr:/demo/参数库.pl.xml\"/>\n" +
                "  <import-variable-library path=\"jcr:/demo/custom.vl.xml\"/>\n" +
                "  <import-constant-library path=\"jcr:/demo/constants.cl.xml\"/>\n" +
                "  <import-action-library path=\"jcr:/demo/动作库.al.xml\"/>\n" +
                "  <card-cell type=\"attribute\" row=\"2\" col=\"1\" var=\"age\" var-label=\"年龄\" datatype=\"Integer\"/>\n" +
                "  <card-cell type=\"condition\" row=\"2\" col=\"2\">\n" +
                "    <joint type=\"or\">\n" +
                "      <condition op=\"LessThen\">\n" +
                "        <value content=\"18\" type=\"Input\"/>\n" +
                "      </condition>\n" +
                "      <condition op=\"GreaterThen\">\n" +
                "        <value content=\"12\" type=\"Input\"/>\n" +
                "      </condition>\n" +
                "    </joint>\n" +
                "  </card-cell>\n" +
                "  <card-cell type=\"score\" row=\"2\" col=\"3\">\n" +
                "    <value content=\"4\" type=\"Input\"/>\n" +
                "  </card-cell>\n" +
                "  <card-cell type=\"condition\" row=\"3\" col=\"2\">\n" +
                "    <joint type=\"and\">\n" +
                "      <condition op=\"GreaterThen\">\n" +
                "        <value content=\"11\" type=\"Input\"/>\n" +
                "      </condition>\n" +
                "    </joint>\n" +
                "  </card-cell>\n" +
                "  <card-cell type=\"score\" row=\"3\" col=\"3\">\n" +
                "    <value content=\"11\" type=\"Input\"/>\n" +
                "  </card-cell>\n" +
                "  <card-cell type=\"attribute\" row=\"4\" col=\"1\" var=\"gender\" var-label=\"性别\" datatype=\"Boolean\"/>\n" +
                "  <card-cell type=\"condition\" row=\"4\" col=\"2\">\n" +
                "    <joint type=\"and\">\n" +
                "      <condition op=\"Equals\">\n" +
                "        <value content=\"男\" type=\"Input\"/>\n" +
                "      </condition>\n" +
                "    </joint>\n" +
                "  </card-cell>\n" +
                "  <card-cell type=\"score\" row=\"4\" col=\"3\">\n" +
                "    <value content=\"1\" type=\"Input\"/>\n" +
                "  </card-cell>\n" +
                "  <card-cell type=\"attribute\" row=\"5\" col=\"1\" var=\"mobile\" var-label=\"手机号\" datatype=\"String\"/>\n" +
                "  <card-cell type=\"condition\" row=\"5\" col=\"2\">\n" +
                "    <joint type=\"and\">\n" +
                "      <condition op=\"Equals\">\n" +
                "        <value content=\"c\" type=\"Input\"/>\n" +
                "      </condition>\n" +
                "    </joint>\n" +
                "  </card-cell>\n" +
                "  <card-cell type=\"score\" row=\"5\" col=\"3\">\n" +
                "    <value content=\"11\" type=\"Input\"/>\n" +
                "  </card-cell>\n" +
                "  <attribute-row row-number=\"2\">\n" +
                "    <condition-row row-number=\"3\"/>\n" +
                "  </attribute-row>\n" +
                "  <attribute-row row-number=\"4\"/>\n" +
                "  <attribute-row row-number=\"5\"/>\n" +
                "</scorecard>\n";
        ScorecardDeserializer scorecardDeserializer = (ScorecardDeserializer) applicationContext.getBean(ScorecardDeserializer.BEAN_ID);
        Document document = DocumentHelper.parseText(content);
        Element element = document.getRootElement();
        ScorecardDefinition scorecardDefinition = scorecardDeserializer.deserialize(element);
        Console.log("scorecardDefinition:{}", JSON.toJSONString(scorecardDefinition));
        //Console.log("scorecardDefinition:{}", JSON.toJSONString(scorecardDefinition));
        Map<String,Object> scoreCardContent = ScoreCardUtil.simplifyScoreCardContent(scorecardDefinition);
        Console.log("scoreCardContent:{}", JSON.toJSONString(scoreCardContent));
    }


}
