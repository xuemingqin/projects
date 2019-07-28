package com.example.welcomemyapplication.helper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class TuanOpenHelper extends SQLiteOpenHelper{

    // データーベースのバージョン
    private static final int DATABASE_VERSION = 1;

    // データーベース名
    private static final String DATABASE_NAME = "TestDB.db";
    public static final String TABLE_NAME = "shop";

    private static final String TEST = "CREATE TABLE DEPARTMENT(\n" +
            "   ID INT PRIMARY KEY      NOT NULL,\n" +
            "   DEPT           CHAR(50) NOT NULL,\n" +
            "   EMP_ID         INT      NOT NULL\n" +
            ");";
    private static final String SQL_CREATE_ENTRIES =
                    "CREATE TABLE " + TABLE_NAME + "(\n" +
                    "  'sid' int(11) NOT NULL PRIMARY KEY,\n" +
                    "  'iid' int(11) DEFAULT NULL,\n" +
                    "  'sname' TEXT  DEFAULT NULL,\n" +
                    "  'stype' TEXT  DEFAULT NULL,\n" +
                    "  'saddress' TEXT  DEFAULT NULL,\n" +
                    "  'snear' TEXT  DEFAULT NULL,\n" +
                    "  'stel' TEXT  DEFAULT NULL,\n" +
                    "  'stime' TEXT  DEFAULT NULL,\n" +
                    "  'szhekou' TEXT  DEFAULT NULL,\n" +
                    "  'smembercard' TEXT  DEFAULT NULL,\n" +
                    "  'sper' TEXT  DEFAULT NULL,\n" +
                    "  'smoney' TEXT  DEFAULT NULL,\n" +
                    "  'snum' TEXT  DEFAULT NULL,\n" +
                    "  'slevel' TEXT  DEFAULT NULL,\n" +
                    "  'sflag_tuan' TEXT  DEFAULT NULL,\n" +
                    "  'sflag_quan' TEXT  DEFAULT NULL,\n" +
                    "  'sflag_ding' TEXT  DEFAULT NULL,\n" +
                    "  'sflag_ka' TEXT  DEFAULT NULL,\n" +
                    "  'longitude' TEXT  DEFAULT NULL,\n" +
                    "  'latitude' TEXT  DEFAULT NULL,\n" +
                    "  'sintroduction' TEXT  DEFAULT NULL,\n" +
                    "  'sdetails' TEXT  DEFAULT NULL,\n" +
                    "  'stips' TEXT  DEFAULT NULL,\n" +
                    "  'sflag_promise' TEXT  DEFAULT NULL\n" +
                    ");";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;


    public TuanOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // テーブル作成
        // SQLiteファイルがなければSQLiteファイルが作成される
        db.execSQL(
                SQL_CREATE_ENTRIES
        );
        tablesInDB(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // アップデートの判別
        db.execSQL(
                SQL_DELETE_ENTRIES
        );
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public ArrayList<String> tablesInDB(SQLiteDatabase db){
        ArrayList<String> list = new ArrayList<String>();
        String sql = "select name from sqlite_master where type='table'";
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                list.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }

        return list;

    }

}
