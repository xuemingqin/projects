package com.example.docomomyapplication.utils;

import android.content.Context;
import android.content.SharedPreferences;


public class SharedUtils{
   private static final String FILE_NAME="dianoping";
   private static final String MODE_NAME ="welcome";
    public static boolean getWelcomeBoolean(Context context){
        return context.getSharedPreferences (FILE_NAME,Context.MODE_PRIVATE).getBoolean ( MODE_NAME,false );

    }
    public static void putWelcome( Context context,Boolean isFirst){
        SharedPreferences.Editor editor = context.getSharedPreferences ( FILE_NAME, Context.MODE_PRIVATE ).edit ( );
          editor.putBoolean ( MODE_NAME,isFirst);
          editor.commit ();
    }
}
