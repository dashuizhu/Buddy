package com.example.administrator.buddy.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.example.administrator.buddy.bean.UserConcernsBean;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhuj on 2017/9/14 20:52.
 */
public class DeviceListSQLMdoel {
  DeviceListSQL mSQL;
  static final String TABLE = "device_list";
  static String FIELD_ID = "id";//
  static String FIELD_NAME = "name";//列名

  public static String crateTable() {
    return "CREATE TABLE "
            + TABLE
            + "("
            + FIELD_ID
            + " integer primary key autoincrement ,"
            + "deviceId Text, bindCode Text,name Text,sim Text,avatar Text,isAdmin INTEGER);";
  }

  public DeviceListSQLMdoel(Context context) {
    mSQL = new DeviceListSQL(context);
  }

  public void addtoSql(String deviceId, String bindCode, String name, String sim, String avatar,
          Boolean isAdmin) {
    int i;
    if (isAdmin) {
      i = 1;
    } else {
      i = 2;
    }
    SQLiteDatabase db = mSQL.getWritableDatabase();
    //生成要修改或者插入的键值
    ContentValues cv = new ContentValues();
    cv.put("deviceId", deviceId);
    cv.put("bindCode", bindCode);
    cv.put("name", name);
    cv.put("sim", sim);
    cv.put("avatar", avatar);
    cv.put("isAdmin", i);
    // insert 操作
    db.insert(TABLE, null, cv);
    //关闭数据库
    Log.e("habit", "插入数据库");
    db.close();
  }

  public void addtoSqlList(List<UserConcernsBean> l) {
    SQLiteDatabase db = mSQL.getWritableDatabase();
    ContentValues cv;

    for (int i = 0; i < l.size(); i++) {
      try {
        UserConcernsBean device = l.get(i);
        cv = new ContentValues();
        cv.put("id", i);
        cv.put("deviceId", device.getDeviceId());
        cv.put("bindCode", device.getBindCode());
        cv.put("name", device.getName());
        cv.put("sim", device.getSim());
        cv.put("avatar", device.getAvatar());
        cv.put("isAdmin", device.isAdmin());
        db.insert(TABLE, null, cv);
        Log.e("DeviceList", "插入SQL成功" + device.getName());
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    // insert 操作
    //关闭数据库
    db.close();
  }

  public void deleteSql(int id) {
    //获取写数据库
    SQLiteDatabase db = mSQL.getWritableDatabase();
    // delete 操作
    db.delete("device_list", "id=?", new String[] { String.valueOf(id) });
    //关闭数据库
    db.close();
  }

  public List<UserConcernsBean> inquireDeviceIdList() {
    SQLiteDatabase db = mSQL.getReadableDatabase();
    Cursor cursor = null;
    List<UserConcernsBean> list = new ArrayList<>();
    cursor = db.rawQuery("SELECT * FROM device_list", null);
    try {
      while (cursor.moveToNext()) {
        UserConcernsBean bean = new UserConcernsBean();
        bean.setId(cursor.getInt(cursor.getColumnIndex("id")));
        bean.setDeviceId(cursor.getString(cursor.getColumnIndex("deviceId")));
        bean.setBindCode(cursor.getString(cursor.getColumnIndex("bindCode")));
        bean.setName(cursor.getString(cursor.getColumnIndex("name")));
        bean.setSim(cursor.getString(cursor.getColumnIndex("sim")));
        bean.setAvatar(cursor.getString(cursor.getColumnIndex("avatar")));
        bean.setAdmin((cursor.getColumnIndex("isAdmin") != 0));
        list.add(bean);
      }
      Log.e("SQlinquire", list.toString());
      return list;
    } catch (Exception e) {
      e.printStackTrace();
    }
    cursor.close();

    db.close();
    return null;
  }

  public String inquireDeviceId() {

    SQLiteDatabase db = mSQL.getReadableDatabase();
    Cursor cursor = null;
    cursor = db.rawQuery("SELECT * FROM device_list", null);
    try {
      if (cursor.moveToNext()) {
        return cursor.getString(cursor.getColumnIndex("deviceId"));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    cursor.close();

    db.close();
    return null;
  }
}
