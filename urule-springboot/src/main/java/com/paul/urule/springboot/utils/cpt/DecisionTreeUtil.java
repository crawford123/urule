package com.paul.urule.springboot.utils.cpt;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.bstek.urule.model.decisiontree.DecisionTree;
import com.bstek.urule.parse.deserializer.DecisionTreeDeserializer;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Copyright © 2019 广州智数信息技术有限公司. All rights reserved.
 *
 * @ClassName
 * @Description
 * @Auther FengZhi
 * @Email 2045532295@qq.com
 * @Create 2019-12-26 9:45
 * @Version
 */

@Component
public class DecisionTreeUtil {

    @Autowired
    private ApplicationContext applicationContext;


    public Map<String, Object> testDecisionTreeInfo() throws DocumentException {
        String decisionTreeContent = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "\n" +
                "<decision-tree>\n" +
                "  <remark><![CDATA[]]></remark>\n" +
                "  <import-variable-library path=\"jcr:/demo/custom.vl.xml\"/>\n" +
                "  <import-constant-library path=\"jcr:/demo/custom.vl.xml\"/>\n" +
                "  <variable-tree-node>\n" +
                "    <left var-category=\"会员\" var=\"age\" var-label=\"年龄\" datatype=\"Integer\" type=\"variable\"/>\n" +
                "    <condition-tree-node op=\"LessThen\">\n" +
                "      <value content=\"18\" type=\"Input\"/>\n" +
                "      <variable-tree-node>\n" +
                "        <left var-category=\"会员\" var=\"married\" var-label=\"婚否\" datatype=\"Boolean\" type=\"variable\"/>\n" +
                "        <condition-tree-node op=\"Equals\">\n" +
                "          <value content=\"true\" type=\"Input\"/>\n" +
                "          <action-tree-node>\n" +
                "            <var-assign var-category=\"会员\" var=\"level\" var-label=\"等级\" datatype=\"Integer\" type=\"variable\">\n" +
                "              <value content=\"1\" type=\"Input\"/>\n" +
                "            </var-assign>\n" +
                "          </action-tree-node>\n" +
                "        </condition-tree-node>\n" +
                "        <condition-tree-node op=\"Equals\">\n" +
                "          <value content=\"false\" type=\"Input\"/>\n" +
                "          <action-tree-node>\n" +
                "            <var-assign var-category=\"会员\" var=\"level\" var-label=\"等级\" datatype=\"Integer\" type=\"variable\">\n" +
                "              <value content=\"5\" type=\"Input\"/>\n" +
                "            </var-assign>\n" +
                "          </action-tree-node>\n" +
                "        </condition-tree-node>\n" +
                "      </variable-tree-node>\n" +
                "    </condition-tree-node>\n" +
                "    <condition-tree-node op=\"GreaterThenEquals\">\n" +
                "      <value content=\"18\" type=\"Input\"/>\n" +
                "      <variable-tree-node>\n" +
                "        <left var-category=\"会员\" var=\"gender\" var-label=\"性别\" datatype=\"Boolean\" type=\"variable\"/>\n" +
                "        <condition-tree-node op=\"Equals\">\n" +
                "          <value content=\"true\" type=\"Input\"/>\n" +
                "          <action-tree-node>\n" +
                "            <var-assign var-category=\"会员\" var=\"level\" var-label=\"等级\" datatype=\"Integer\" type=\"variable\">\n" +
                "              <value content=\"3\" type=\"Input\"/>\n" +
                "            </var-assign>\n" +
                "          </action-tree-node>\n" +
                "        </condition-tree-node>\n" +
                "        <condition-tree-node op=\"Equals\">\n" +
                "          <value content=\"false\" type=\"Input\"/>\n" +
                "          <action-tree-node>\n" +
                "            <var-assign var-category=\"会员\" var=\"level\" var-label=\"等级\" datatype=\"Integer\" type=\"variable\">\n" +
                "              <value content=\"6\" type=\"Input\"/>\n" +
                "            </var-assign>\n" +
                "          </action-tree-node>\n" +
                "        </condition-tree-node>\n" +
                "      </variable-tree-node>\n" +
                "    </condition-tree-node>\n" +
                "  </variable-tree-node>\n" +
                "</decision-tree>\n";
        Document document = DocumentHelper.parseText(decisionTreeContent);
        Element decisionTreeElement = document.getRootElement();
        DecisionTreeDeserializer decisionTreeDeserializer = (DecisionTreeDeserializer)applicationContext.getBean(DecisionTreeDeserializer.BEAN_ID);
        DecisionTree decisionTree = decisionTreeDeserializer.deserialize(decisionTreeElement);
        Map<String, Object> decisionTreeMap = JSON.parseObject(JSON.toJSONString(decisionTree));
        decisionTreeMap = simplifyDecisionTreeContent(decisionTreeMap);
        return decisionTreeMap;
    }

    /**
     * 简化决策树内容
     * @param decisionTree
     * @return Map<String,Object>
     */
    public static Map<String,Object> simplifyDecisionTreeContent(Map<String,Object> decisionTree){
        Map<String,Object> resultMap = (Map<String,Object>) decisionTree.get("variableTreeNode");
        parseVariableCategory(resultMap);
        return resultMap;
    }

    /**
     * 采用递归的方法解析决策树变量节点中的variableCategory属性
     * @param dataMap
     */
    public static void parseVariableCategory(Map<String,Object> dataMap){
        dataMap.remove("parentNode");
        Map<String,Object> leftMap = (Map<String, Object>) dataMap.get("left");
        Map<String,Object> leftPartMap = (Map<String, Object>) leftMap.get("leftPart");
        //VariableUtil.parseVariableCategory(leftPartMap);
        List<Map<String, Object>> conditionTreeNodes = (List<Map<String, Object>>) dataMap.get("conditionTreeNodes");
        if (CollUtil.isNotEmpty(conditionTreeNodes)){
            conditionTreeNodes.forEach(conditionTreeNode -> {
                conditionTreeNode.remove("parentNode");
                List<Map<String,Object>> variableTreeNodes = (List<Map<String, Object>>) conditionTreeNode.get("variableTreeNodes");
                List<Map<String,Object>> actionTreeNodes = (List<Map<String, Object>>) conditionTreeNode.get("actionTreeNodes");
                if (CollUtil.isNotEmpty(actionTreeNodes)){
                    actionTreeNodes.forEach(actionTreeNode -> {
                        actionTreeNode.remove("parentNode");
                    });
                }
                if (CollUtil.isNotEmpty(variableTreeNodes)){
                    variableTreeNodes.forEach(variableTreeNode -> {
                        parseVariableCategory(variableTreeNode);
                    });
                }
            });
        }
    }

}
