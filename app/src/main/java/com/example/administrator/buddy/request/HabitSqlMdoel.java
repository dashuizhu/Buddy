package com.example.administrator.buddy.request;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.example.administrator.buddy.Helper.HabitSql;
import com.example.administrator.buddy.bean.HabitResult;
import java.util.List;

/**
 * Created by zhuj on 2017/9/14 20:52.
 */
public class HabitSqlMdoel {
    HabitSql dbhelper ;
    public HabitSqlMdoel(Context context){
        dbhelper= new  HabitSql(context);
    }

    public void addtoSql(Long id,int state,String playTime ,String title,int playState){
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        //生成要修改或者插入的键值
        ContentValues cv = new ContentValues();
        cv.put("id",id);
        cv.put("state" ,state);
        cv.put("playTime" ,playTime);
        cv.put("title" ,title);
        cv.put("playState" ,playState);
        // insert 操作
        db.insert(HabitSql.TABLE_NAME, null, cv);
        //关闭数据库
        Log.e("habit","插入数据库");
        db.close();
    }
    public void addtoSqlList(List<HabitResult.DataBean> l){
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        ContentValues cv;

        for (int i=0;i<l.size();i++) {
           try {
               HabitResult.DataBean habit = l.get(i);
               int j = habit.getHabitId();
               cv = new ContentValues();
               cv.put("id", j);
               cv.put("state", habit.getState());
               cv.put("playTime", habit.getPlayTime());
               cv.put("title", habit.getTitle());
               cv.put("playState", habit.getPlayState());
               db.insert(HabitSql.TABLE_NAME, null, cv);
           }catch (Exception e ){
               e.printStackTrace();
           }
        }
        // insert 操作
        //关闭数据库
        Log.e("habit","插入数据库");
        db.close();
    }

    public void deleteSql(int  id){
        //StringBuffer whereBuffer = new StringBuffer();
        //whereBuffer.append("id").append(" = ").append("'").append("").append("'");
        //获取写数据库
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        // delete 操作
        //db.delete(HabitSql.TABLE_NAME, whereBuffer.toString(), null);
        db.delete("test_db","id=?",new String[]{String.valueOf(id)});
        //关闭数据库
        db.close();
    }

    public void inquireSql(SQLiteDatabase db) {
        Cursor cursor = db.query ("TABLE_NAME",null,null,null,null,null,null);

        //判断游标是否为空
        if(cursor.moveToFirst() ){
            //遍历游标
            for (int i = 0; i < cursor.getCount(); i++) {
                cursor.move(i);
                //获得ID
                String playTime = cursor.getString(0);
                //获得用户名
                String state = cursor.getString(1);
                //获得密码
                String password = cursor.getString(2);
                //输出用户信息 System.out.println(id+":"+playTime+":"+state);
            }
        }
    }

}
