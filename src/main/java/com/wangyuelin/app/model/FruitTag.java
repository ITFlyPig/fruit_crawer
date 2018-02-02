package com.wangyuelin.app.model;

import java.io.Serializable;

public class FruitTag implements Serializable {
    private String tag;//标签
    private String content;//子标签的文字

    public String getTag() {
        return tag;
    }

    public String getContent() {
        return content;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return " tag:" + tag + "  content:" + content;
    }
}
