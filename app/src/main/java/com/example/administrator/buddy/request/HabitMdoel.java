package com.example.administrator.buddy.request;

import android.content.SharedPreferences;
import android.util.Log;
import com.example.administrator.buddy.AppString;
import com.example.administrator.buddy.MyApplication;
import com.example.administrator.buddy.bean.HabitBean;
import com.example.administrator.buddy.bean.HabitDetailBean;
import com.example.administrator.buddy.bean.HabitDetailResult;
import com.example.administrator.buddy.bean.HabitResult;
import com.example.administrator.buddy.bean.NetworkResult;
import com.example.administrator.buddy.exception.CustomException;
import com.example.administrator.buddy.network.IHttpAPI;
import com.example.administrator.buddy.utils.JsonUtils;
import okhttp3.RequestBody;
import org.json.JSONException;
import org.json.JSONObject;
import rx.Observable;
import rx.functions.Action1;

/**
 * Created by zhuj on 2017/9/5 18:42.
 */
public class HabitMdoel {


    public Observable<HabitResult> habi() {
        IHttpAPI iHttpAPI = MyApplication.getIHttpApi();
        SharedPreferences userInfo;
        userInfo = MyApplication.getContext()
                .getSharedPreferences("userInfo", 0);
        return iHttpAPI.habit("888888888888888",userInfo.getString("UserId","")).doOnNext(new Action1<HabitResult>() {
            @Override public void call(HabitResult habitResult) {
                if (habitResult.isSuccess()) {
                    Log.e("habit",habitResult.getData().toString());
                }else {
                    throw new CustomException(habitResult.getMessage());
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
        //                 map.setMessage(message);
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
        //                 map.setMessage(message);
        //                 return map ;
        //             }
        //         }
        //     }catch (JSONException e){
        //         e.printStackTrace();
        //     }
        //return null; }
    }


    /**
     * 查询详情
     */
    public Observable<HabitDetailResult> getHabitDetail(HabitBean habitBean) {
        IHttpAPI iHttpApi = MyApplication.getIHttpApi();
        SharedPreferences userInfo = MyApplication.getContext()
                .getSharedPreferences("userInfo", 0);
        String userId = userInfo.getString("UserId","");
        String deviceId = AppString.deviceId;
        int i=0;
        return iHttpApi.getHabitInfo(deviceId, userId, habitBean.getHabitId(), i).doOnNext(new Action1<HabitDetailResult>() {
            @Override public void call(HabitDetailResult networkResult) {
                if (!networkResult.isSuccess()) {
                    throw new CustomException(networkResult.getMessage());
                }
            }
        });
    }

    /**
     * 订阅习惯，也就是添加
     */
    public Observable<NetworkResult> addHabit(HabitDetailBean habitBean) {
        IHttpAPI iHttpApi = MyApplication.getIHttpApi();

        SharedPreferences userInfo = MyApplication.getContext()
                .getSharedPreferences("userInfo", 0);
        String userId = userInfo.getString("UserId","");
        String deviceId = AppString.deviceId;

        JSONObject obj = new JSONObject();
        try {
            obj.put("habitId", habitBean.getHabitId());
            obj.put("playFlag", habitBean.getPlayFlag());
            obj.put("playTime", habitBean.getPlayTimeNew());
            if (habitBean.isRepeatWeek()) {
                obj.put("repeatExpression", habitBean.getRepeatExpression());
            }
            obj.put("voiceType", habitBean.getVoiceType());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = JsonUtils.toRequestBody(obj);
        return iHttpApi.addHabit(deviceId, userId, body).doOnNext(new Action1<NetworkResult>() {
            @Override public void call(NetworkResult networkResult) {
                if (!networkResult.isSuccess()) {
                    throw new CustomException(networkResult.getMessage());
                }
            }
        });
    }

    public Observable modifyHabitBean(HabitDetailBean habitBean) {
        IHttpAPI iHttpApi = MyApplication.getIHttpApi();

        SharedPreferences userInfo = MyApplication.getContext()
                .getSharedPreferences("userInfo", 0);
        String userId = userInfo.getString("UserId","");
        String deviceId = AppString.deviceId;

        JSONObject obj = new JSONObject();
        try {
            obj.put("id", habitBean.getSubscribeId());
            obj.put("playFlag", habitBean.getPlayFlag());
            obj.put("playTime", habitBean.getPlayTimeNew());
            if (habitBean.isRepeatWeek()) {
                obj.put("repeatExpression", habitBean.getRepeatExpression());
            }
            obj.put("voiceType", habitBean.getVoiceType());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = JsonUtils.toRequestBody(obj);
        return iHttpApi.modifyHabit(deviceId, userId, body).doOnNext(new Action1<NetworkResult>() {
            @Override public void call(NetworkResult networkResult) {
                if (!networkResult.isSuccess()) {
                    throw new CustomException(networkResult.getMessage());
                }
            }
        });
    }
}
