package com.paul.urule.springboot.utils.cpt;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.IdUtil;
import com.bstek.urule.model.rule.RuleSet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright © 2019 广州智数信息技术有限公司. All rights reserved.
 *
 * @ClassName
 * @Description
 * @Auther FengZhi
 * @Email 2045532295@qq.com
 * @Create 2019-12-16 10:48
 * @Version
 */

public class RuleSetUtil {

    /**
     * 简化规则文件内容 这个方法直接在外层转为map，值得用
     * @param objectList
     * @return List<Object>
     */
    public static List<Object> simplifyRuleSetContent(List<Object> objectList) {
        List<Object> rules = null;
        if (CollUtil.isNotEmpty(objectList)) {
            Map<String, Object> ruleSet = ((Map<String, Object>) objectList.get(0));
            if (MapUtil.isNotEmpty(ruleSet)) {
                rules = (List<Object>) ruleSet.get("rules");
                if(CollUtil.isNotEmpty(rules)){
                    rules.forEach(rule -> {
                        List<Object> newCriterions = new ArrayList<>();
                        Map<String,Object> ruleMap = (Map<String, Object>) rule;
                        Map<String,Object> lhsMap = (Map<String,Object>)ruleMap.get("lhs");
                        if (MapUtil.isNotEmpty(lhsMap)){
                            Map<String,Object> criterionMap = (Map<String,Object>)lhsMap.get("criterion");
                            if (MapUtil.isNotEmpty(criterionMap)){
                                List<Map<String,Object>> criterions = (List<Map<String, Object>>)criterionMap.get("criterions");
                                String id1 = IdUtil.fastSimpleUUID();
                                //解析criterions（条件）具体内容
                                criterions.forEach(criterion -> {
                                    criterion.remove("parent");
                                    //转换variableCategory字段
                                    Map<String,Object> leftMap = (Map<String, Object>) criterion.get("left");
                                    if(MapUtil.isNotEmpty(leftMap)){
                                        Map<String,Object> leftPartMap = (Map<String, Object>) leftMap.get("leftPart");
                                        //解析variableCategory属性
                                       // VariableUtil.parseVariableCategory(leftPartMap);
                                        //解析variableLable属性
                                        VariableUtil.parseVariableLabel();
                                        if (CollUtil.isNotEmpty(leftPartMap)){
                                            if(!leftPartMap.containsKey("variableName")){
                                                leftPartMap.put("variableName", "");
                                                leftPartMap.put("variableLabel", "");
                                                leftPartMap.put("apiCode", "");
                                                leftPartMap.put("apiName", "");
                                            }
                                        }
                                    }
                                    //普通条件
                                    if (!criterion.containsKey("junctionType")){
                                        criterion.put("id", id1);
                                        newCriterions.add(criterion);
                                    }else {
                                        //联合条件
                                        String id2 = IdUtil.fastSimpleUUID();
                                        List<Map<String,Object>> criterionsList = (List<Map<String, Object>>) criterion.get("criterions");
                                        if(CollUtil.isNotEmpty(criterionsList)){
                                            String junctionType = MapUtil.getStr(criterion,"junctionType");
                                            for (int i = 0; i < criterionsList.size(); i++) {
                                                Map<String,Object> criterion1 = criterionsList.get(i);
                                                criterion1.remove("parent");
                                                //转换variableCategory字段
                                                Map<String,Object> leftMap1 = (Map<String, Object>) criterion1.get("left");
                                                if(MapUtil.isNotEmpty(leftMap1)){
                                                    Map<String,Object> leftPartMap = (Map<String, Object>) leftMap1.get("leftPart");
                                                    //解析variableCategory属性
                                                    //VariableUtil.parseVariableCategory(leftPartMap);
                                                    //解析variableLable属性
                                                    VariableUtil.parseVariableLabel();                                        }
                                                if (i == 0){
                                                    criterion1.put("junctionType", junctionType);
                                                    criterion1.put("count", criterionsList.size() - 1);
                                                }
                                                criterion1.put("id", id2);
                                                newCriterions.add(criterion1);
                                            }
                                        }
                                    }

                                });
                                criterionMap.put("criterions", newCriterions);
                            }
                        }

                        //增加actionName字段
                        //解析other对象的信息
                        Map<String,Object> otherMap = (Map<String, Object>) ruleMap.get("other");
                        List<Map<String,Object>> otherActions = (List<Map<String, Object>>) otherMap.get("actions");
                        if (CollUtil.isNotEmpty(otherActions)){
                            otherActions.forEach(action -> {
                                //String actionType = MapUtil.getStr(action, "actionType");
                                //action.put("actionName", RiskCptActionInfo.CONSOLE_PRINT.getName());
                            });
                        }else{
                            Map<String,Object> action = new HashMap<>();
                            action.put("actionType", "");
                            Map<String,Object> value = new HashMap<>();
                            value.put("content", "");
                            action.put("value", value);
                            if (otherActions != null){
                                otherActions.add(action);
                            }
                        }
                        //解析rhs对象的信息
                        Map<String,Object> rhsMap = (Map<String, Object>) ruleMap.get("rhs");
                        List<Map<String,Object>> rhsActions = (List<Map<String, Object>>) rhsMap.get("actions");
                        if (CollUtil.isNotEmpty(rhsActions)){
                            rhsActions.forEach(action -> {
                                //String actionType = MapUtil.getStr(action, "actionType");
                               // action.put("actionName", RiskCptActionInfo.CONSOLE_PRINT.getName());
                            });
                        }else{
                            Map<String,Object> action = new HashMap<>();
                            action.put("actionType", "");
                            Map<String,Object> value = new HashMap<>();
                            value.put("content", "");
                            action.put("value", value);
                            if (rhsActions != null){
                                rhsActions.add(action);
                            }
                        }
                    });
                }
            }
        }
        return rules;
    }

    /**
     * @deprecated
     * 这个方法需要bean转为map，不推荐用
     * @param ruleSet
     * @return
     */
    public static List<Object> simplifyRuleSetContent1(RuleSet ruleSet) {
        List<Object> rules = new ArrayList<>();
        Map<String, Object> ruleSetMap = BeanUtil.beanToMap(ruleSet);
        if (MapUtil.isNotEmpty(ruleSetMap)) {
            rules = (List<Object>) ruleSetMap.get("rules");
            if(CollUtil.isNotEmpty(rules)){
                rules.forEach(rule -> {
                    List<Object> newCriterions = new ArrayList<>();
                    Map<String,Object> ruleMap = BeanUtil.beanToMap(rule);
                    Map<String,Object> lhsMap = BeanUtil.beanToMap(ruleMap.get("lhs"));
                    if (MapUtil.isNotEmpty(lhsMap)){
                        Map<String,Object> criterionMap = BeanUtil.beanToMap(lhsMap.get("criterion"));
                        if (MapUtil.isNotEmpty(criterionMap)){
                            List<Object> criterions = (List<Object>)criterionMap.get("criterions");
                            String id1 = IdUtil.fastSimpleUUID();
                            //解析criterions（条件）具体内容
                            criterions.forEach(criterion -> {
                                Map<String, Object> criterionMap1 = BeanUtil.beanToMap(criterion);
                                criterionMap1.remove("parent");
                                //转换variableCategory字段
                                Map<String,Object> leftMap = BeanUtil.beanToMap(criterionMap1.get("left"));
                                if(MapUtil.isNotEmpty(leftMap)){
                                    Map<String,Object> leftPartMap =  BeanUtil.beanToMap(leftMap.get("leftPart"));
                                    //解析variableCategory属性
                                   // VariableUtil.parseVariableCategory(leftPartMap);
                                    //解析variableLable属性
                                    VariableUtil.parseVariableLabel();
                                    if (CollUtil.isNotEmpty(leftPartMap)){
                                        if(!leftPartMap.containsKey("variableName")){
                                            leftPartMap.put("variableName", "");
                                            leftPartMap.put("variableLabel", "");
                                            leftPartMap.put("apiCode", "");
                                            leftPartMap.put("apiName", "");
                                        }
                                    }
                                }
                                //普通条件
                                if (!criterionMap1.containsKey("junctionType")){
                                    criterionMap1.put("id", id1);
                                    newCriterions.add(criterionMap1);
                                }else {
                                    //联合条件
                                    String id2 = IdUtil.fastSimpleUUID();
                                    List<Object> criterionsList = (List<Object>) criterionMap1.get("criterions");
                                    if(CollUtil.isNotEmpty(criterionsList)){
                                        String junctionType = MapUtil.getStr(criterionMap1,"junctionType");
                                        for (int i = 0; i < criterionsList.size(); i++) {
                                            Map<String,Object> criterion1 = BeanUtil.beanToMap(criterionsList.get(i));
                                            criterion1.remove("parent");
                                            //转换variableCategory字段
                                            Map<String,Object> leftMap1 = BeanUtil.beanToMap(criterion1.get("left"));
                                            if(MapUtil.isNotEmpty(leftMap1)){
                                                Map<String,Object> leftPartMap = BeanUtil.beanToMap(leftMap1.get("leftPart"));
                                                //解析variableCategory属性
                                                //VariableUtil.parseVariableCategory(leftPartMap);
                                                //解析variableLable属性
                                                VariableUtil.parseVariableLabel();                                        }
                                            if (i == 0){
                                                criterion1.put("junctionType", junctionType);
                                                criterion1.put("count", criterionsList.size() - 1);
                                            }
                                            criterion1.put("id", id2);
                                            newCriterions.add(criterion1);
                                        }
                                    }
                                }

                            });
                            criterionMap.put("criterions", newCriterions);
                        }
                    }
                    //增加actionName字段
                    //解析other对象的信息
                    Map<String,Object> otherMap = BeanUtil.beanToMap(ruleMap.get("other"));
                    List<Object> otherActions = (List<Object>) otherMap.get("actions");
                    if (CollUtil.isNotEmpty(otherActions)){
                        otherActions.forEach(action -> {
                            //String actionType = MapUtil.getStr(action, "actionType");
                            Map<String, Object> actionMap = BeanUtil.beanToMap(action);
                            //actionMap.put("actionName", RiskCptActionInfo.CONSOLE_PRINT.getName());
                        });
                    }else{
                        Map<String,Object> actionMap = new HashMap<>();
                        actionMap.put("actionType", "");
                        Map<String,Object> value = new HashMap<>();
                        value.put("content", "");
                        actionMap.put("value", value);
                        if (otherActions != null){
                            otherActions.add(actionMap);
                        }
                    }
                    //解析rhs对象的信息
                    Map<String,Object> rhsMap = BeanUtil.beanToMap(ruleMap.get("rhs"));
                    List<Object> rhsActions = (List<Object>) rhsMap.get("actions");
                    if (CollUtil.isNotEmpty(rhsActions)){
                        rhsActions.forEach(action -> {
                            //String actionType = MapUtil.getStr(action, "actionType");
                            Map<String, Object> actionMap = BeanUtil.beanToMap(action);
                           // actionMap.put("actionName", RiskCptActionInfo.CONSOLE_PRINT.getName());
                        });
                    }else{
                        Map<String,Object> actionMap = new HashMap<>();
                        actionMap.put("actionType", "");
                        Map<String,Object> value = new HashMap<>();
                        value.put("content", "");
                        actionMap.put("value", value);
                        if (rhsActions != null){
                            rhsActions.add(actionMap);
                        }
                    }
                });
            }
        }
        return rules;
    }

}
