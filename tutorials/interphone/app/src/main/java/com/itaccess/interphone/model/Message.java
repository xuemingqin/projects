package com.itaccess.interphone.model;

import java.io.Serializable;

/**
 * Created by linxi on 2019/01/17.
 */

public class Message implements  Serializable{

    public enum Direct {CLIENT, SERVER}
    private String from;

    private String msgTime;

    private MessageBody body;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getMsgTime() {
        return msgTime;
    }

    public void setMsgTime(String msgTime) {
        this.msgTime = msgTime;
    }

    public MessageBody getBody() {
        return body;
    }

    public void setBody(MessageBody body) {
        this.body = body;
    }

    public Direct direct() {
        if ("02".equals(from)) {
            return Direct.SERVER;
        } else {
            return Direct.CLIENT;
        }
    }
}
