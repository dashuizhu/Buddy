package com.example.administrator.buddy;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.administrator.buddy.injector.components.DaggerPresenterComponent;
import com.example.administrator.buddy.injector.components.PresenterComponent;
import com.example.administrator.buddy.injector.modules.PresenterModule;
import com.example.administrator.buddy.presenter.LoginPresenter;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

public class RegisterActivity extends BaseActivity {
    EditText password;
    EditText acctount;
    EditText verifiaction;
    Button yzm;
    public Thread thread;
    private Subscription mDonwcount;
    protected LoadDialog mLoadDialog;
    LoginPresenter mLoginPresenter;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        PresenterComponent authenticationComponent = DaggerPresenterComponent.builder()
                .presenterModule(new PresenterModule(this))
                .build();
        mLoginPresenter = authenticationComponent.getLoginPresenter();
        regis();
    }

    Handler mHandler = new Handler() {
        @Override public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    yzm.setText("" + msg.arg1);
                    break;
                case 2:
                    yzm.setText(R.string.app_logo_yzm);
                    yzm.setBackgroundResource(R.color.colorBule);
                    yzm.setEnabled(true);
                    break;
                case 3:
                    Toast.makeText(RegisterActivity.this, "网络异常", Toast.LENGTH_SHORT).show();
                    break;
                case 4:
                    mLoadDialog.dismiss();
                    break;
                case 5:
                    Toast.makeText(RegisterActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
                    break;
                case 6:
                    Toast.makeText(RegisterActivity.this, "发送失败", Toast.LENGTH_SHORT).show();
                    break;
                case 7:
                    Toast.makeText(RegisterActivity.this, "" + msg.obj, Toast.LENGTH_SHORT).show();
                    break;
                case 8:
                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    protected void regis() {
        acctount = (EditText) findViewById(R.id.et_zh2);
        password = (EditText) findViewById(R.id.et_pssword2);
        verifiaction = (EditText) findViewById(R.id.et_yzm);
        yzm = (Button) findViewById(R.id.btn_yzm);
        yzm.setOnClickListener(new View.OnClickListener() {

            @Override public void onClick(View v) {
                String acc = acctount.getText().toString();
                if (acc.length() == 0) {
                    Toast.makeText(RegisterActivity.this, "账号不能为空", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    ver();
                    Log.e("Register1", "显示的账号" + acc);
                    return;
                }
            }
        });

        findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                String acc = acctount.getText().toString();
                String pas = password.getText().toString();
                String ver = verifiaction.getText().toString();
                if (acc.length() == 0) {
                    Toast.makeText(RegisterActivity.this, "账号不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (acc.length() < 6 || acc.length() > 20) {
                    Toast.makeText(RegisterActivity.this, "输入账号不能少于6位数或者大于20位数", Toast.LENGTH_SHORT)
                            .show();
                    return;
                }
                if (pas.length() == 0) {
                    Toast.makeText(RegisterActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (pas.length() < 6) {
                    Toast.makeText(RegisterActivity.this, "输入密码不能少于6位数或者大于20位数", Toast.LENGTH_SHORT)
                            .show();
                    return;
                }
                if (ver.length() == 0) {
                    Toast.makeText(RegisterActivity.this, "验证码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    reg();
                }
            }
        });
    }

    //protected  void rPostVer(String data){
    //    try{
    //        String baseUrl  ;
    //        baseUrl ="http://47.92.49.151:8080/api/apps/BUDDY_API_TEST/accounts/sendVerifyCode";
    //
    //        byte[] postData = data.getBytes();
    //        URL url=new URL(baseUrl);
    //        HttpURLConnection urlConn =(HttpURLConnection) url.openConnection();
    //        urlConn.setChunkedStreamingMode(5*1000);
    //        urlConn.setReadTimeout(5*1000);
    //        urlConn.setDoOutput(true);
    //        urlConn.setDoInput(true);
    //        urlConn.setUseCaches(false);
    //        urlConn.setRequestMethod("POST");
    //        urlConn.setInstanceFollowRedirects(true);
    //        urlConn.setRequestProperty("Content-Type", "application/json");
    //        urlConn.connect();
    //        DataOutputStream dos =new DataOutputStream(urlConn.getOutputStream());
    //        dos.write(postData);
    //        dos.flush();
    //        dos.close();
    //        if (urlConn.getResponseCode() == 200) {
    //            String result = streamToString(urlConn.getInputStream());
    //            Log.e("Register", "Post方式请求成功，result--->" + result);
    //            verJSONObject(result);
    //            mHandler.sendEmptyMessage(4);
    //        } else {
    //            Log.e("Register", "Post方式请求失败");
    //            urlConn.disconnect();
    //            mHandler.sendEmptyMessage(4);
    //        }
    //        urlConn.disconnect();
    //    } catch (Exception e) {
    //        Log.e("Register", "异常");
    //        e.printStackTrace();
    //        mHandler.sendEmptyMessage(4);
    //
    //    }
    //
    //}

    //启动线程倒计时
    public void ver() {
        mLoginPresenter.verMdoel(acctount.getText().toString());
        yzm.setEnabled(false);
        mDonwcount = Observable.interval(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Long>() {
                    @Override public void onCompleted() {
                        yzm.setText(R.string.app_logo_yzm);
                        yzm.setBackgroundResource(R.color.colorBule);
                        yzm.setEnabled(true);
                        mDonwcount.unsubscribe();
                    }

                    @Override public void onError(Throwable e) {
                        onCompleted();
                    }

                    @Override public void onNext(Long aLong) {
                        yzm.setText("" + (60 - aLong));
                        if (aLong == 60) {
                            onCompleted();
                        }
                    }
                });

        //.subscribe(mobserver);

        //thread = new Thread(new Runnable() {
        //    @Override public void run() {
        //        for (int i = 60; i >= 0; i--) {
        //            try {
        //                Thread.sleep(1000);
        //            }
        //            //异常处理
        //            catch (InterruptedException e) {
        //                e.printStackTrace();
        //                return;
        //            }
        //            Message message = new Message();
        //            message.what = 1;
        //            message.arg1 = i;
        //            mHandler.sendMessage(message);//传递参数1或i
        //            // yzm.setText(""+i);
        //        }//
        //        mHandler.sendEmptyMessage(2);
        //                        // yzm.setText(R.string.app_logo_yzm);
        //                    }
        //                });
        ////thread.start();//触发按钮
        ////yzm.setEnabled(false);//设置不可点击
        //yzm.setBackgroundResource(R.color.colorwhite);//设置按键颜色
    }

    //protected void verJSONObject (String jsonData)  {
    //    try {
    //        JSONObject jsonobjiect =   new JSONObject(jsonData);
    //        int code = jsonobjiect.getInt("code");
    //        String message = jsonobjiect.getString("message");
    //        String timestamp = jsonobjiect.getString("timestamp");
    //        Log.e("Register", " " + code +" "+message+" "+timestamp);
    //        if(code== 0){
    //            mHandler.sendEmptyMessage(5);
    //        }
    //        else {
    //            mHandler.sendEmptyMessage(6);
    //        }
    //    }catch (Exception e){
    //        e.printStackTrace();
    //    }
    //}
    //注册
    //protected  void rPostReg(String data){
    //    try{
    //        String baseUrl  ;
    //        baseUrl ="http://47.92.49.151:8080/api/apps/BUDDY_API_TEST/accounts/register";
    //        byte[] postData = data.getBytes();
    //        URL url=new URL(baseUrl);
    //        HttpURLConnection urlConn =(HttpURLConnection) url.openConnection();
    //        urlConn.setChunkedStreamingMode(5*1000);
    //        urlConn.setReadTimeout(5*1000);
    //        urlConn.setDoOutput(true);
    //        urlConn.setDoInput(true);
    //        urlConn.setUseCaches(false);
    //        urlConn.setRequestMethod("POST");
    //        urlConn.setInstanceFollowRedirects(true);
    //        urlConn.setRequestProperty("Content-Type", "application/json");
    //        urlConn.connect();
    //        DataOutputStream dos =new DataOutputStream(urlConn.getOutputStream());
    //        dos.write(postData);
    //        dos.flush();
    //        dos.close();
    //        if (urlConn.getResponseCode() == 200) {
    //            String result = streamToString(urlConn.getInputStream());
    //            Log.e("registered", "Post方式请求成功，result--->" + result);
    //            mHandler.sendEmptyMessage(4);
    //            parseJSONObjectReg(result);
    //
    //        } else {
    //            Log.e("registered", "Post方式请求失败");
    //            mHandler.sendEmptyMessage(4);
    //            urlConn.disconnect();
    //
    //        }
    //        urlConn.disconnect();
    //    } catch (Exception e) {
    //        Log.e("registered", "异常");
    //        mHandler.sendEmptyMessage(4);
    //        e.printStackTrace();
    //    }
    //
    //}
    public void reg() {
        mLoginPresenter.register(acctount.getText().toString(), password.getText().toString(),
                verifiaction.getText().toString(), true);

        //thread = new Thread(new Runnable() {
        //    @Override public void run() {
        //
        //        String acc = acctount.getText().toString();
        //        String pas = password.getText().toString();
        //        String ver =verifiaction.getText().toString();
        //        String nickName ="foo";
        //        String avatar =" ";
        //        JSONObject json = new JSONObject();
        //        try {
        //            json.put("phone", acc);
        //            json.put("password", pas);
        //            json.put("verifyCode", ver);
        //            json.put("nickName", nickName);
        //            json.put("avatar", avatar);
        //            Log.e("registered", json.toString());
        //            rPostReg(json.toString());
        //        } catch (JSONException e) {
        //            e.printStackTrace();
        //        }
        //    }
        //});
        //thread.start();
    }

    //success
    @Override public void success(Object o) {
        finish();
        super.success(o);
    }
    //protected void parseJSONObjectReg (String jsonData)  {
    //    try {
    //        JSONObject jsonobjiect =   new JSONObject(jsonData);
    //        int code = jsonobjiect.getInt("code");
    //        String message = jsonobjiect.getString("message");
    //        String timestamp = jsonobjiect.getString("timestamp");
    //        Log.e("registered", " " + code +" "+message+" "+timestamp);
    //        if(code== 0){
    //            mHandler.sendEmptyMessage(8);
    //            String acc = acctount.getText().toString();
    //            String pas = password.getText().toString();
    //            getIntent().putExtra("dengr",acc);
    //            getIntent().putExtra("mima0", Md5Tools.MD5(pas));
    //            setResult(RESULT_OK,getIntent());
    //            finish();//返回
    //        }
    //        else {
    //            Message mes = new Message();
    //            mes.obj =message;
    //            mes.what = 7;
    //            mHandler.sendMessage(mes);
    //        }
    //    }catch (Exception e){
    //        e.printStackTrace();
    //    }
    //}

    //销毁线程， thread值不为空是打断并thread赋予为空
    @Override protected void onDestroy() {
        super.onDestroy();
        if (thread != null) {
            thread.interrupt();
            thread = null;
        }
        if (mDonwcount != null) {
            if (!mDonwcount.isUnsubscribed()) {
                mDonwcount.unsubscribe();
            }
            mDonwcount = null;
        }
    }
}
