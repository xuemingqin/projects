package com.example.drysister;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.appcompat.app.AppCompatActivity;

public class SisterDBHelper{
    private static final String TAG ="SisterDBhelper";
    private static SisterDBHelper dbHelper;
    private SisterOpenHelper sqlHelper;
    private SQLiteDatabase db;


    private SisterDBHelper(){
        sqlHelper = new SisterOpenHelper(DrySisterApp.getContext());

    }
    public static SisterDBHelper getInstance(){
        if (dbHelper == null){
            synchronized (SisterDBHelper.class){
                if (dbHelper==null){
                    dbHelper=new SisterDBHelper();
                }
            }
        }
        return dbHelper;
    }
    public void insertSister(Sister sister){
        db = getWrit
    }
}
