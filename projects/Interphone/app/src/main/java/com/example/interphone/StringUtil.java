package com.example.interphone;

public class StringUtil{
    public static boolean isNull(final String checkChar){
        if (null == checkChar || "".equals( checkChar )){
            return true;
        }
        return false;
    }

}
