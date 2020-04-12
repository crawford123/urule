package com.paul.urule.springboot.service;

import cn.hutool.core.map.MapUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Copyright © 2020 广州智数信息技术有限公司. All rights reserved.
 *
 * @ClassName
 * @Description
 * @Auther FengZhi
 * @Email 2045532295@qq.com
 * @Create 2020-03-25 14:34
 * @Version
 */

public class MyCallable2 implements Callable {

    private Logger logger = LoggerFactory.getLogger(MyCallable2.class);

    private Map<String, Object> dataMap = new HashMap<>();

    public MyCallable2(Map<String, Object> dataMap) {
        this.dataMap = dataMap;
    }

    @Override
    public Object call() throws Exception {
        String taskId = MapUtil.getStr(dataMap, "taskId");
        if (StringUtils.isNotBlank(taskId)){
            logger.info("正在保存风控方案信息....");
            logger.info("验证通过，正在保存数据");
            Thread.sleep(5000);
            logger.info("保存成功！！");
        }
        return  null;
    }
}
