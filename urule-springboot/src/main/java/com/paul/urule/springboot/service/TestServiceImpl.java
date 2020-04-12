package com.paul.urule.springboot.service;


import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.IdUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Copyright © 2020 广州智数信息技术有限公司. All rights reserved.
 *
 * @author 20455
 * @ClassName
 * @Description
 * @Auther FengZhi
 * @Email 2045532295@qq.com
 * @Create 2020-01-19 11:31
 * @Version
 */

@Service
public class TestServiceImpl  implements TestService{

    private Logger logger = LoggerFactory.getLogger(TestService.class);


    @Override
    public void test(){
        /**
         * 打印顺序如下
         * 正在测试.......
         * 出错啦！！
         * java.lang.Exception: 出错了！！
         */
        try{
            logger.info("正在测试.......");
            throw new Exception("出错了！！");
        }catch(Exception ex){
            //这里捕获异常后，外面就不会捕获了，除非继续抛出异常
            logger.error("出错啦！！", ex);
//            throw new Exception();
        }
    }

    @Override
    public Object testThreadPool(Map<String, Object> dataMap) throws Exception {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        MyCallable1 myCallable1 = new MyCallable1(dataMap);
        Future future1 = executorService.submit(myCallable1);
        String result = (String) future1.get();
        if (!result.contains("验证")){
            dataMap.put("taskId", result);
            MyCallable2 myCallable2 = new MyCallable2(dataMap);
            executorService.submit(myCallable2);
        }
        return result;
    }

    @Override
    public Object testThreadPool2(Map<String, Object> dataMap) throws Exception {
        String test = MapUtil.getStr(dataMap, "test");
        if ("test1".equals(test)){
            return "验证不通过！";
        }else if ("test2".equals(test)){
            String taskId = IdUtil.fastSimpleUUID();
            dataMap.put("taskId", taskId);
            return taskId;
        }
        logger.info("正在保存风控方案信息....");
        logger.info("验证通过，正在保存数据");
        Thread.sleep(5000);
        logger.info("保存成功！！");
        return null;
    }
}
