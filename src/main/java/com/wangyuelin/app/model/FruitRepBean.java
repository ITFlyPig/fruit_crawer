package com.wangyuelin.app.model;

import java.util.List;

public class FruitRepBean {
    private String month;
    private int monthNum;
    private List<FruitBean> fruits;

    public String getMonth() {
        return month;
    }

    public int getMonthNum() {
        return monthNum;
    }

    public List<FruitBean> getFruits() {
        return fruits;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setMonthNum(int monthNum) {
        this.monthNum = monthNum;
    }

    public void setFruits(List<FruitBean> fruits) {
        this.fruits = fruits;
    }
}
