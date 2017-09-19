package com.example.administrator.buddy.request;

import android.content.SharedPreferences;
import android.util.Log;
import com.example.administrator.buddy.MyApplication;
import com.example.administrator.buddy.bean.NetworkResult;
import com.example.administrator.buddy.utils.Md5Tools;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zhuj on 2017/9/3 11:57.
 */
public class LoginMdoel {

    public void login(final String acctount, final String password,
            final NetworkCallback callback) {

        Thread thread = new Thread(new Runnable() {

            @Override public void run() {
                JSONObject json = new JSONObject();
                try {
                    json.put("accountId", acctount);
                    json.put("password", Md5Tools.MD5(password));//加密密码
                    json.put("clientSecret", "a123af4e331cf61c0324cd43cbc2135d");
                    Log.e("login", json.toString());
                    String req = new Model().requsetPost(json.toString(),
                            "http://47.92.49.151:8080/api/apps/BUDDY_API_TEST/accounts/login");
                    if (req == null) {
                        callback.callback(null);
                    } else {
                        //解析 封装成NetworkResult
                        JSONObject jsonobjiect = new JSONObject(req);
                        int code = jsonobjiect.getInt("code");
                        String message = jsonobjiect.getString("message");
                        String timestamp = jsonobjiect.getString("timestamp");
                        Log.e("login", " " + code + " " + message + " " + timestamp);
                        if (code == 0) {
                            SharedPreferences userInfo;
                            userInfo =
                                    MyApplication.getContext().getSharedPreferences("userInfo", 0);
                            SharedPreferences.Editor editor = userInfo.edit();
                            editor.putString("USER_NAME", acctount);
                            editor.putString("PASSWORD", password);
                            editor.putBoolean("login", true);//提交 true
                            editor.commit();
                        }
                        //
                        NetworkResult mess = new NetworkResult();
                        mess.setCode(code);
                        mess.setMessge(message);
                        callback.callback(mess);
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                callback.callback(null);
            }
        });
        thread.start();
    }
}
