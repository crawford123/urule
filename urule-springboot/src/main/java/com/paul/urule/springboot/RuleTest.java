package com.paul.urule.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bstek.urule.runtime.KnowledgePackage;
import com.bstek.urule.runtime.KnowledgeSession;
import com.bstek.urule.runtime.KnowledgeSessionFactory;
import com.bstek.urule.runtime.service.KnowledgeService;

/**
 * @author Jacky.gao
 * @since 2019年5月16日
 */
@Controller
public class RuleTest {
	@Autowired
	private KnowledgeService service;
	@RequestMapping("/test")
	public String test() throws Exception{
		KnowledgePackage knowledgePackage=service.getKnowledge("3dxt-CXB/test");
        KnowledgeSession session=KnowledgeSessionFactory.newKnowledgeSession(knowledgePackage);
        session.fireRules();
        session.writeLogFile();
		return null;
	}
}
