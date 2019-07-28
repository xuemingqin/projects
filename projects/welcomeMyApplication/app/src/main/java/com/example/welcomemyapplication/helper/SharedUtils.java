package com.example.welcomemyapplication.helper;

import android.content.Context;
import android.content.SharedPreferences;


public class SharedUtils{
    private static final String FILE_NAME="dianoping";
    private static final String MODE_NAME ="welcome";
    private static final String TUAN_LOAD ="tuanload";

    public static boolean getWelcomeBoolean(Context context){
        return context.getSharedPreferences (FILE_NAME,Context.MODE_PRIVATE).getBoolean ( MODE_NAME,false );

    }
    public static void putWelcome( Context context,Boolean isFirst){
        SharedPreferences.Editor editor = context.getSharedPreferences ( FILE_NAME, Context.MODE_PRIVATE ).edit ( );
        editor.putBoolean ( MODE_NAME,isFirst);
        editor.commit ();
    }

    public static boolean getTuanloadedBoolean(Context context){
        return context.getSharedPreferences (FILE_NAME,Context.MODE_PRIVATE).getBoolean ( TUAN_LOAD,false );

    }
    public static void putTuanloaded( Context context, Boolean isLoaded){
        SharedPreferences.Editor editor = context.getSharedPreferences ( FILE_NAME, Context.MODE_PRIVATE ).edit ( );
        editor.putBoolean ( TUAN_LOAD, isLoaded);
        editor.commit ();
    }
}