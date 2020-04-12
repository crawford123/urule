package com.paul.urule.springboot.service;

import java.util.Map;

/**
 * Copyright © 2020 广州智数信息技术有限公司. All rights reserved.
 *
 * @ClassName
 * @Description
 * @Auther FengZhi
 * @Email 2045532295@qq.com
 * @Create 2020-01-19 11:31
 * @Version
 */

public interface TestService {

   void test();

   Object testThreadPool(Map<String, Object> dataMap) throws Exception;

   Object testThreadPool2(Map<String, Object> dataMap) throws Exception;
}
