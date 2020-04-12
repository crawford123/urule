package com.paul.urule.springboot.service;


import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.IdUtil;

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
 * @Create 2020-03-25 14:23
 * @Version
 */

public class MyCallable1 implements Callable {

    private Map<String, Object> dataMap = new HashMap<>();

    public MyCallable1(Map<String, Object> dataMap) {
        this.dataMap = dataMap;
    }

    @Override
    public Object call() throws Exception {

        String test = MapUtil.getStr(dataMap, "test");
        if ("test1".equals(test)){
            return "验证不通过！";
        }else if ("test2".equals(test)){
            String taskId = IdUtil.fastSimpleUUID();
            dataMap.put("taskId", taskId);
            return taskId;
        }
        return null;
    }

}
