package com.paul.urule.springboot.utils;

import com.bstek.urule.RuleException;
import com.bstek.urule.Utils;
import com.bstek.urule.console.repository.RepositoryResourceProvider;
import com.bstek.urule.console.repository.RepositoryService;
import com.bstek.urule.parse.deserializer.*;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Copyright © 2019 广州智数信息技术有限公司. All rights reserved.
 *
 * @ClassName
 * @Description
 * @Auther FengZhi
 * @Email 2045532295@qq.com
 * @Create 2019-12-12 23:08
 * @Version
 */

@Component
public class LoadXmlUtil {



    protected List<Deserializer<?>> deserializers=new ArrayList<Deserializer<?>>();

    @Resource
    private LoadXmlUtil loadXmlUtil;

    @Autowired
    private ApplicationContext applicationContext;

    @Resource
    private RepositoryService repositoryService;


    /**
     * 加载xml
     * @return
     */
    public List<Object> loadXml(String files){
        ActionLibraryDeserializer actionLibraryDeserializer=(ActionLibraryDeserializer)applicationContext.getBean(ActionLibraryDeserializer.BEAN_ID);
        VariableLibraryDeserializer variableLibraryDeserializer=(VariableLibraryDeserializer)applicationContext.getBean(VariableLibraryDeserializer.BEAN_ID);
        ConstantLibraryDeserializer constantLibraryDeserializer=(ConstantLibraryDeserializer)applicationContext.getBean(ConstantLibraryDeserializer.BEAN_ID);
        ScorecardDeserializer scorecardDeserializer =(ScorecardDeserializer)applicationContext.getBean(ScorecardDeserializer.BEAN_ID);
        RuleSetDeserializer ruleSetDeserializer=(RuleSetDeserializer)applicationContext.getBean(RuleSetDeserializer.BEAN_ID);
        DecisionTableDeserializer decisionTableDeserializer=(DecisionTableDeserializer)applicationContext.getBean(DecisionTableDeserializer.BEAN_ID);
        ScriptDecisionTableDeserializer scriptDecisionTableDeserializer=(ScriptDecisionTableDeserializer)applicationContext.getBean(ScriptDecisionTableDeserializer.BEAN_ID);
        DecisionTreeDeserializer decisionTreeDeserializer=(DecisionTreeDeserializer)applicationContext.getBean(DecisionTreeDeserializer.BEAN_ID);
        ParameterLibraryDeserializer parameterLibraryDeserializer=(ParameterLibraryDeserializer)applicationContext.getBean(ParameterLibraryDeserializer.BEAN_ID);
        FlowDeserializer flowDeserializer = (FlowDeserializer) applicationContext.getBean(FlowDeserializer.BEAN_ID);
        deserializers.add(actionLibraryDeserializer);
        deserializers.add(variableLibraryDeserializer);
        deserializers.add(constantLibraryDeserializer);
        deserializers.add(ruleSetDeserializer);
        deserializers.add(decisionTableDeserializer);
        deserializers.add(scriptDecisionTableDeserializer);
        deserializers.add(decisionTreeDeserializer);
        deserializers.add(parameterLibraryDeserializer);
        deserializers.add(flowDeserializer);
        deserializers.add(scorecardDeserializer);

        List<Object> result=new ArrayList<Object>();
        files= Utils.decodeURL(files);
        boolean isaction=false;
        if(files!=null){
            if(files.startsWith("builtinactions")){
                isaction=true;
            }else{
                String[] paths=files.split(";");
                for(String path:paths){
                    if(path.startsWith(RepositoryResourceProvider.JCR)){
                        path=path.substring(4,path.length());
                    }
                    String[] subpaths=path.split(",");
                    path=subpaths[0];
                    String version=null;
                    if(subpaths.length==2){
                        version=subpaths[1];
                    }
                    try{
                        InputStream inputStream=null;
                        if(StringUtils.isEmpty(version)){
                            inputStream=repositoryService.readFile(path,null);
                        }else{
                            inputStream=repositoryService.readFile(path,version);
                        }
                        Element element= XmlUtils.parseXml(inputStream);
                        System.out.println(element);
                        for(Deserializer<?> des:deserializers){
                            if(des.support(element)){
                                result.add(des.deserialize(element));
                                break;
                            }
                        }
                        return result;
                    }catch(Exception ex){
                        throw new RuleException(ex);
                    }
                }
            }
        }
        return null;
    }

}
