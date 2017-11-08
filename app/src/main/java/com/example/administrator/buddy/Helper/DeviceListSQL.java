package com.example.administrator.buddy.Helper;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by zhuj on 2017/9/14 19:09.
 */
public class DeviceListSQL extends SQLiteOpenHelper{
    private static final String DB_NAME = "test_db";//数据库名字
    private static final int DB_VERSION = 1;
    public DeviceListSQL(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override public void onCreate(SQLiteDatabase db) {

        try {
            db.execSQL(DeviceListSQLMdoel.crateTable());
            db.execSQL(HabitSqlMdoel.crateTable());
        } catch (SQLException e) {
         e.printStackTrace();
        }
    }

    @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }



}
