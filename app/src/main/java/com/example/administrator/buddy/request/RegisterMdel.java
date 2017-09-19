package com.example.administrator.buddy.request;

import android.util.Log;
import com.example.administrator.buddy.bean.NetworkResult;
import com.example.administrator.buddy.utils.Md5Tools;
import org.json.JSONException;
import org.json.JSONObject;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by zhuj on 2017/9/4 19:09.
 */
public class RegisterMdel {
    public Observable<NetworkResult> register(final String acctount, final String password, final String verifyCode){
        //
        return Observable.create(new Observable.OnSubscribe<NetworkResult>() {
            @Override public void call(Subscriber<? super NetworkResult> subscriber) {
                JSONObject json = new JSONObject();
                try {
                    json.put("phone", acctount);
                    json.put("password", Md5Tools.MD5(password));//加密密码
                    json.put("verifyCode", verifyCode);
                    json.put("nickName", "nickName");
                    json.put("avatar", "avatar");
                    Model model = new Model();
                    String req = model.requsetPost(json.toString(),
                            "http://47.92.49.151:8080/api/apps/BUDDY_API_TEST/accounts/register");
                    if (req == null) {
                        subscriber.onError(new Throwable("null"));
                    } else {
                        //解析 封装成NetworkResult
                        JSONObject jsonobjiect = new JSONObject(req);
                        int code = jsonobjiect.getInt("code");
                        String message = jsonobjiect.getString("message");
                        String timestamp = jsonobjiect.getString("timestamp");
                        Log.e("login", " " + code + " " + message + " " + timestamp);
                        if (code == 0) {
                            JSONObject data = jsonobjiect.getJSONObject("data");
                            String userId = data.getString("userId");
                        }
                        NetworkResult mess = new NetworkResult();
                        mess.setCode(code);
                        mess.setMessge(message);
                        subscriber.onNext(mess);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }
            }
        });

        //        JSONObject json = new JSONObject();
        //        try {
        //            json.put("phone", acctount);
        //            json.put("password", Md5Tools.MD5(password));//加密密码
        //            json.put("verifyCode", verifyCode);
        //            json.put("nickName", "nickName");
        //            json.put("avatar", "avatar");
        //                Model model = new Model();
        //                String req = model.requsetPost(json.toString(),
        //                        "http://47.92.49.151:8080/api/apps/BUDDY_API_TEST/accounts/register");
        //            if (req == null) {
        //                return null;
        //            } else {
        //                //解析 封装成NetworkResult
        //                JSONObject jsonobjiect = new JSONObject(req);
        //                int code = jsonobjiect.getInt("code");
        //                String message = jsonobjiect.getString("message");
        //                String timestamp = jsonobjiect.getString("timestamp");
        //                Log.e("login", " " + code + " " + message + " " + timestamp);
        //                if (code == 0) {
        //                    JSONObject data = jsonobjiect.getJSONObject("data");
        //                    String userId = data.getString("userId");
        //                }
        //                NetworkResult mess = new NetworkResult();
        //                mess.setCode(code);
        //                mess.setMessge(message);
        //                return mess;
        //            }
        //        } catch (JSONException e) {
        //            e.printStackTrace();
        //        }
        //return null;
    }

    public Observable<NetworkResult> reset(final String acctount, final String password, final String verifyCode) {
        return Observable.create(new Observable.OnSubscribe<NetworkResult>() {
            @Override public void call(Subscriber<? super NetworkResult> subscriber) {
                JSONObject json = new JSONObject();
                try {
                    json.put("accountId", acctount);
                    json.put("password", Md5Tools.MD5(password));
                    json.put("verifyCode", verifyCode);
                    String req = new Model().requsetPost(json.toString(),
                            "http://47.92.49.151:8080/api/apps/BUDDY_API_TEST/accounts/findPassword");
                    if (req == null) {
                        subscriber.onError(new Throwable("null"));
                    } else {
                        JSONObject jsonObject = new JSONObject(req);
                        int code = jsonObject.getInt("code");
                        String message = jsonObject.getString("message");
                        NetworkResult mess = new NetworkResult();
                        mess.setCode(code);
                        mess.setMessge(message);
                        subscriber.onNext(mess);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    subscriber.onError(e);
                }
            }
        });
    }
}


