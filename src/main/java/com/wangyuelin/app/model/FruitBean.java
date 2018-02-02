package com.wangyuelin.app.model;

import com.wangyuelin.crawer.model.CookBookBean;
import com.wangyuelin.crawer.model.Tag;

import java.util.List;

public class FruitBean {
    private String icon;//图片
    private String des;//简介
    private String name;//水果的名称
    private String func;//功效
    private List<FruitTag> tags;//历史、注意事项等
    private List<CookBookBean> cookBookList;//菜谱

    public String getIcon() {
        return icon;
    }

    public String getDes() {
        return des;
    }

    public String getName() {
        return name;
    }

    public List<FruitTag> getTags() {
        return tags;
    }

    public void setTags(List<FruitTag> tags) {
        this.tags = tags;
    }

    public List<CookBookBean> getCookBookList() {
        return cookBookList;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setCookBookList(List<CookBookBean> cookBookList) {
        this.cookBookList = cookBookList;
    }

    public String getFunc() {
        return func;
    }

    public void setFunc(String func) {
        this.func = func;
    }
}
