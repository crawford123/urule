package com.paul.urule.springboot.model;

import lombok.Data;

import java.util.List;

/**
 * Copyright ?2019 广州智数信息技术有限公司. All rights reserved.
 *
 * @className
 * @Description
 * @auther FengZhi
 * @email 2045532295@qq.com
 * @create 2019-08-13 9:52
 */


@Data
public class Parameter {

    //最大值
    private  Integer value;

    //测试集合
    private List list;

    //小于1000的订单数
    private Integer total1;

    //大于或等于1000订单数
    private  Integer total2;

    //得分
    private  Integer score;



}
