package com.itaccess.interphone.model;

import android.content.Context;

/**
 * Created by linxi on 2019/02/01.
 */

public class AutoResponseSet {

    public Boolean isDecided;

    public String contentNum;

    public Boolean getDecided() {
        return isDecided;
    }

    public void setDecided(Boolean decided) {
        isDecided = decided;
    }

    public String getContentNum() {
        return contentNum;
    }

    public void setContentNum(String contentNum) {
        this.contentNum = contentNum;
    }
}
