package com.paul.urule.springboot.model;

import com.bstek.urule.model.Label;

/**
 * Copyright ?2019 广州智数信息技术有限公司. All rights reserved.
 *
 * @className
 * @Description
 * @auther FengZhi
 * @email 2045532295@qq.com
 * @create 2019-08-11 22:47
 */

public class Order {

    @Label("名称")
    private String name;
    @Label("价格")
    private float price;
    @Label("数量")
    private int amount;

    public Order() {
    }

    public Order(int price) {
        this.price = price;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return this.price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getAmount() {
        return this.amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
