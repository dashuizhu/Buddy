package com.example.administrator.buddy.request;

import android.content.SharedPreferences;
import com.example.administrator.buddy.MyApplication;
import com.example.administrator.buddy.bean.HabitResult;
import com.example.administrator.buddy.network.IHttpAPI;
import java.util.List;
import rx.Observable;
import rx.functions.Action1;

/**
 * Created by zhuj on 2017/9/5 18:42.
 */
public class HabitMdoel {

    protected List<HabitResult.DataBean> mList;

    public Observable<HabitResult> habi() {
        IHttpAPI iHttpAPI = MyApplication.getIHttpApi();
        SharedPreferences userInfo;
        userInfo = MyApplication.getContext()
                .getSharedPreferences("userInfo", 0);
        return iHttpAPI.habit("777777777777777",userInfo.getString("UserId","")).doOnNext(new Action1<HabitResult>() {
            @Override public void call(HabitResult habitResult) {
                if (habitResult.isSuccess()) {
                    mList =  habitResult.getData();
                    HabitSqlMdoel mHabitSql;
                    mHabitSql = new HabitSqlMdoel(MyApplication.getContext());
                    mHabitSql.addtoSqlList(mList);
                }else {
                     new Exception(habitResult.getMessage());
                }

            }
        });
        //HabitResult map = new HabitResult();
        ////                 mList = new ArrayList<>();
        //                 mHabitSql = new HabitSqlMdoel(MyApplication.getContext());
        //                 for (int i = 0; i < data.length(); i++) {
        //                     JSONObject json = data.getJSONObject(i);
        //                     HabitBean habit=new HabitBean();
        //                     int habitId =json.getInt("habitId");
        //                     String title = json.getString("title");
        //                     String playTime = json.getString("playTime");
        //                     int state = json.getInt("state");
        //                     int playState = json.getInt("playState");
        //                     habit.setTitle(title);
        //                     habit.setPlayTime(playTime);
        //                     habit.setState(state);
        //                     habit.setHabitId(habitId);
        //                     habit.setPlayState(playState);
        //                     mList.add(habit);
        //                     //if (mHabitSql==null) {
        //                 }
        //                 map.setList(mList);
        //                 map.setCode(code);
        //                 map.setMessge(message);
        //                 return map ;
        //             }

        //     try {
        //         JSONObject object = new JSONObject();
        //         String req = new Model().requsetGet(object.toString(),
        //                 "http://47.92.49.151:8080/api/devices/777777777777777/habits/today?userId=4bc7e2383c404841b6b66b18ac1c9321&access_token=c2c5e568-4a15-40c1-b890-e1bcabc566a4");
        //         if (req == null) {
        //             return null;
        //         } else {
        //             JSONObject jsonobjiect = new JSONObject(req);
        //
        //             int code = jsonobjiect.getInt("code");
        //             if (code == 0) {
        //                 JSONArray data = jsonobjiect.getJSONArray("data");
        //                 String message  = jsonobjiect.getString("message");
        //                 HabitResult map = new HabitResult();
        //                 mList = new ArrayList<>();
        //                 HabitSqlMdoel mHabitSql;
        //                 mHabitSql = new HabitSqlMdoel(MyApplication.getContext());
        //                 for (int i = 0; i < data.length(); i++) {
        //                     JSONObject json = data.getJSONObject(i);
        //                     HabitBean habit=new HabitBean();
        //                     int habitId =json.getInt("habitId");
        //                     String title = json.getString("title");
        //                     String playTime = json.getString("playTime");
        //                     int state = json.getInt("state");
        //                     int playState = json.getInt("playState");
        //                     habit.setTitle(title);
        //                     habit.setPlayTime(playTime);
        //                     habit.setState(state);
        //                     habit.setHabitId(habitId);
        //                     habit.setPlayState(playState);
        //                     mList.add(habit);
        //                     //if (mHabitSql==null) {
        //                 }
        //                 mHabitSql.addtoSqlList(mList);
        //                 map.setList(mList);
        //                 map.setCode(code);
        //                 map.setMessge(message);
        //                 return map ;
        //             }
        //         }
        //     }catch (JSONException e){
        //         e.printStackTrace();
        //     }
        //return null; }
    }
}
