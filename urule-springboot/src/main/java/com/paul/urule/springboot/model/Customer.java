package com.paul.urule.springboot.model;

import com.bstek.urule.ClassUtils;
import com.bstek.urule.model.Label;

import javax.smartcardio.Card;
import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * Copyright ?2019 广州智数信息技术有限公司. All rights reserved.
 *
 * @className
 * @Description
 * @auther FengZhi
 * @email 2045532295@qq.com
 * @create 2019-08-11 22:47
 */

public class Customer {

    @Label("名称")
    private String name;
    @Label("年龄")
    private int age;
    @Label("出生日期")
    private Date birthday;
    @Label("等级")
    private int level;
    @Label("手机号")
    private String mobile;
    @Label("性别")
    private boolean gender;
    @Label("是否有车")
    private boolean car;
    @Label("婚否")
    private boolean married;
    @Label("是否有房")
    private boolean house;
    @Label("卡")
    private List<Card> cards;
    @Label("地址")
    private List<String> address;
    @Label("订单")
    private List<Order> orders;

    public Customer() {
    }

    public static void main(String[] args) {
        ClassUtils.classToXml(Customer.class, new File("D:/customer.xml"));
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getBirthday() {
        return this.birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public boolean isGender() {
        return this.gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public boolean isCar() {
        return this.car;
    }

    public void setCar(boolean car) {
        this.car = car;
    }

    public boolean isMarried() {
        return this.married;
    }

    public void setMarried(boolean married) {
        this.married = married;
    }

    public boolean isHouse() {
        return this.house;
    }

    public void setHouse(boolean house) {
        this.house = house;
    }

    public List<Card> getCards() {
        return this.cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public List<String> getAddress() {
        return this.address;
    }

    public void setAddress(List<String> address) {
        this.address = address;
    }

    public List<Order> getOrders() {
        return this.orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", birthday=" + birthday +
                ", level=" + level +
                ", mobile='" + mobile + '\'' +
                ", gender=" + gender +
                ", car=" + car +
                ", married=" + married +
                ", house=" + house +
                ", cards=" + cards +
                ", address=" + address +
                ", orders=" + orders +
                '}';
    }
}
