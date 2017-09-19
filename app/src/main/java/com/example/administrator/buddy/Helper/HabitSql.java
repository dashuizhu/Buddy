package com.example.administrator.buddy.Helper;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by zhuj on 2017/9/14 19:14.
 */
public class HabitSql extends SQLiteOpenHelper {
    private static final String DB_NAME = "test_db";//数据库名字
    private static final int DB_VERSION = 1;
    public static String TABLE_NAME = "person";// 表名
    public HabitSql(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override public void onCreate(SQLiteDatabase db) {
        String sql="CREATE TABLE  "+TABLE_NAME+"(id INTEGER PRIMARY KEY AUTOINCREMENT,state text  ,playTime text,title text , playState text )" ;
        try {
            db.execSQL(sql);
        } catch (SQLException e) {
            Log.e("tag", "onCreate " +TABLE_NAME + "Error" + e.toString());
            return;
        }

    }


    @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    //
    //public void deleteSql(){
    //    StringBuffer whereBuffer = new StringBuffer();
    //    whereBuffer.append(DBHelper.FIELD_NAME).append(" = ").append("'").append(name).append("'");
    //    //获取写数据库
    //    SQLiteDatabase db = dbhelper.getWritableDatabase();
    //    // delete 操作
    //    db.delete(DBHelper.TABLE_NAME, whereBuffer.toString(), null);
    //    //关闭数据库
    //    db.close();
    //}
    //
    //public void inquireSql(){
    //    StringBuffer whereBuffer = new StringBuffer();
    //    whereBuffer.append(DBHelper.FIELD_NAME).append(" = ").append("'").append(name).append("'");
    //    //指定要查询的是哪几列数据
    //    String[] columns = {DBHelper.FIELD_NAME};
    //    //获取可读数据库
    //    SQLiteDatabase db = dbhelper.getReadableDatabase();
    //    //查询数据库
    //    Cursor cursor = null;
    //    try {
    //        cursor = db.query(DBHelper.TABLE_NAME, columns, whereBuffer.toString(), null, null, null, null);
    //        if (cursor != null && cursor.getCount() > 0) {
    //            int count = cursor.getColumnCount();
    //            String columName = cursor.getColumnName(0);
    //            String  tname = cursor.getString(0);
    //            Log.e("tag", "count = " + count + " columName = " + columName + "  name =  " +tname);
    //            cursor.moveToFirst();
    //        }
    //        if (cursor != null) {
    //            cursor.close();
    //        }
    //    } catch (SQLException e) {
    //        Log.e("tag", "queryDatas" + e.toString());
    //    }
    //    //关闭数据库
    //    db.close();
    //}


}
