package com.example.docomomyapplication.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Data implements Serializable{


    String login;

    @SerializedName( "html_url" )
    String htmlurl;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getHtmlurl() {
        return htmlurl;
    }

    public void setHtmlurl(String htmlurl) {
        this.htmlurl = htmlurl;
    }


}
