package com.itaccess.interphone.model;

import java.io.Serializable;

/**
 * Created by linxi on 2019/01/17.
 */

public class MessageBody implements Serializable{

    private String type;

    private String message;

    private String url;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
