package com.example.interphone;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LoginHelper extends SQLiteOpenHelper{
    public static final int VERSION=1;

    public static final String TABLE_NAME = "person";

    public LoginHelper( Context context) {
        super( context, "db", null, VERSION );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="CREATE TABLE " + TABLE_NAME +
                " (name Text, password Text)";
        db.execSQL( sql );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS person");
        onCreate(db);
    }

}
