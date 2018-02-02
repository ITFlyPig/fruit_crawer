package com.wangyuelin.crawer.processor.listener;

import org.springframework.context.ApplicationEvent;

public class Event extends ApplicationEvent {

    private String msg;//消息
    private int op;//操作码

    public Event(Object source) {
        super(source);
    }

    public Event(Object source, String msg, int op) {
        super(source);
        this.msg = msg;
        this.op = op;
    }

    public String getMsg() {
        return msg;
    }

    public int getOp() {
        return op;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setOp(int op) {
        this.op = op;
    }
}
