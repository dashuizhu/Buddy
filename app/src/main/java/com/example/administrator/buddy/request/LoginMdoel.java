package com.example.administrator.buddy.request;

import android.content.SharedPreferences;
import android.util.Log;
import com.example.administrator.buddy.MyApplication;
import com.example.administrator.buddy.bean.LoginResult;
import com.example.administrator.buddy.injector.modules.Requestbody;
import com.example.administrator.buddy.network.IHttpAPI;
import com.example.administrator.buddy.utils.Md5Tools;
import com.example.administrator.buddy.utils.SharedPreUser;
import okhttp3.RequestBody;
import org.json.JSONException;
import org.json.JSONObject;
import rx.Observable;
import rx.functions.Action1;

/**
 * Created by zhuj on 2017/9/3 11:57.
 */
public class LoginMdoel {


    public Observable<LoginResult> login(final String accountId, final String password) {
        IHttpAPI iHttpAPI = MyApplication.getIHttpApi() ;
        JSONObject json = new JSONObject();
                try {
                    json.put("accountId", accountId);
                    json.put("password", Md5Tools.MD5(password));//加密密码
                    json.put("clientSecret", "a123af4e331cf61c0324cd43cbc2135d");
                }catch (JSONException e){
                    e.printStackTrace();
                }
        RequestBody body =  Requestbody.toRequestBody(json);
                    Log.e("login", json.toString());
        return iHttpAPI.login(body)
                .doOnNext(new Action1<LoginResult>() {
                    @Override public void call(LoginResult loginResult) {
                            if (loginResult.isSuccess()) {
                                SharedPreferences userInfo;
                                userInfo = MyApplication.getContext()
                                        .getSharedPreferences("userInfo", 0);
                                SharedPreferences.Editor editor = userInfo.edit();
                                editor.putString("USER_NAME", accountId);
                                editor.putString("PASSWORD", password);
                                editor.putString("UserId", loginResult.getData().getUserId());
                                editor.putString("nickName", loginResult.getData().getNickName());
                                editor.putString("avatar", loginResult.getData().getAvatar());
                                editor.putBoolean("login", true);//提交 true
                                editor.commit();

                              SharedPreUser.getInstance().put(MyApplication.getContext(),
                                      SharedPreUser.KEY_USER_ID, loginResult.getData().getUserId());
                            }else {
                                new Exception(loginResult.getMessage());
                            }


                    }
                });

        //Thread thread = new Thread(new Runnable() {
        //
        //    @Override public void run() {
        //        JSONObject json = new JSONObject();
        //        try {
        //            json.put("accountId", acctount);
        //            json.put("password", Md5Tools.MD5(password));//加密密码
        //            json.put("clientSecret", "a123af4e331cf61c0324cd43cbc2135d");
        //            Log.e("login", json.toString());
        //            String req = new Model().requsetPost(json.toString(),
        //                    "http://47.92.49.151:8080/api/apps/BUDDY_API_TEST/accounts/login");
        //            if (req == null) {
        //                callback.callback(null);
        //            } else {
        //                //解析 封装成NetworkResult
        //                JSONObject jsonobjiect = new JSONObject(req);
        //                int code = jsonobjiect.getInt("code");
        //                String message = jsonobjiect.getString("message");
        //                String timestamp = jsonobjiect.getString("timestamp");
        //                Log.e("login", " " + code + " " + message + " " + timestamp);
        //                if (code == 0) {
        //                    SharedPreferences userInfo;
        //                    userInfo =
        //                            MyApplication.getContext().getSharedPreferences("userInfo", 0);
        //                    SharedPreferences.Editor editor = userInfo.edit();
        //                    editor.putString("USER_NAME", acctount);
        //                    editor.putString("PASSWORD", password);
        //                    editor.putBoolean("login", true);//提交 true
        //                    editor.commit();
        //                }
        //                //
        //                NetworkResult mess = new NetworkResult();
        //                mess.setCode(code);
        //                mess.setMessage(message);
        //                callback.callback(mess);
        //                return;
        //            }
        //        } catch (JSONException e) {
        //            e.printStackTrace();
        //        }
        //        callback.callback(null);
        //    }
        //});
        //thread.start();
    }
}
