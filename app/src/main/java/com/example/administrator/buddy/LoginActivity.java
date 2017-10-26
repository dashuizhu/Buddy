package com.example.administrator.buddy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.administrator.buddy.injector.components.DaggerPresenterComponent;
import com.example.administrator.buddy.injector.components.PresenterComponent;
import com.example.administrator.buddy.injector.modules.ModelModule;
import com.example.administrator.buddy.presenter.LoginPresenter;
import java.util.TimerTask;

public class LoginActivity extends BaseActivity {

    EditText acctount;
    EditText password;
    private Button button;
    private SharedPreferences userInfo;
    private TimerTask task;
    LoginPresenter mLoginPresenter;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //P层对象注入
        PresenterComponent authenticationComponent = DaggerPresenterComponent.builder()
                .modelModule(new ModelModule(this))
                .build();
        mLoginPresenter = authenticationComponent.getLoginPresenter();
        login();
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            //
            return;
        }
        if (requestCode == 12) {//得到返回启动参数
            String acc = data.getStringExtra("dengr");
            acctount.setText(acc);
            String pas = data.getStringExtra("mima0");
            password.setText(pas);
        }
        if (requestCode == 11) {//得到返回启动参数
            String acc = data.getStringExtra("dengr");
            acctount.setText(acc);
            String pas = data.getStringExtra("mima0");
            password.setText(pas);
        }
    }

    //Handler mHandler = new Handler(){
    //    @Override public void handleMessage(Message msg) {
    //        super.handleMessage(msg);
    //        switch (msg.what){
    //            case 1:
    //                Toast.makeText(LoginActivity.this," "+ msg.obj ,Toast.LENGTH_SHORT).show();
    //                break;
    //            case 2:
    //                Toast.makeText(LoginActivity.this,"网络异常" ,Toast.LENGTH_SHORT).show();
    //                shutDialg();
    //                break;
    //            case 3 :
    //                shutDialg();
    //                //mLoadDialog.dismiss();
    //                break;
    //        }
    //    }
    //};
    protected void login() {
        acctount = (EditText) findViewById(R.id.et_zh);
        password = (EditText) findViewById(R.id.et_pssword);
        button = (Button) findViewById(R.id.btn_login);
        userInfo = getSharedPreferences("userInfo",
                0);//打开Preferences，名称为userInfo，如果存在则打开它，否则创建新的Preferencesw文件
        final String name = userInfo.getString("USER_NAME", "");
        final String passw = userInfo.getString("PASSWORD", "");
        acctount.setText(name);
        password.setText(passw);
        button.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                String acc = acctount.getText().toString();
                String pass = password.getText().toString();
                //让userInfo处于编辑状态
                if (acc.length() == 0) {
                    Toast.makeText(LoginActivity.this, "账号不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (acc.length() < 6) {
                    Toast.makeText(LoginActivity.this, "请输入不少于或等于6位数的账号", Toast.LENGTH_SHORT)
                            .show();
                    Log.i("acc", "" + acc);
                    return;
                }
                if (pass.length() == 0) {
                    Toast.makeText(LoginActivity.this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (pass.length() < 6) {
                    Toast.makeText(LoginActivity.this, "请输入不少于或等于6位数的密码", Toast.LENGTH_SHORT)
                            .show();
                    return;
                } else {
                    twoseconds();
                    // SharedPreferences.Editor editor=userInfo.edit();
                    // editor.putString("USER_NAME", acc);//提交名字为USER_NAME ，String类型的acc内容
                    //  editor.putString("PASSWORD", pass);
                    // editor.putBoolean("login",true);//提交 true
                    // editor.commit();//完成提交
                    //equals:被用来检测两个对象是否相等，即两个对象的内容是否相等，区分大小写
                    //else {
                    //  Toast.makeText(LoginActivity.this, "账号或者密码不正确，请重新输入", Toast.LENGTH_SHORT).show();
                    //}
                }
            }
        });

        findViewById(R.id.tv_register).setOnClickListener(new View.OnClickListener() {
            //启动
            @Override public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                intent.putExtra("wczc", 1);
                //"12"启动标志
                startActivityForResult(intent, 12);
            }
        });

        findViewById(R.id.tv_ftp).setOnClickListener(new View.OnClickListener() {
            //启动
            @Override public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RestPasswordActivity.class);
                intent.putExtra("wczc", 1);
                startActivityForResult(intent, 11);
            }
        });
    }

    @Override public void success(Object o) {

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);//跳转
            //intent.putExtra("user",nickName);//传递数据
            startActivity(intent);//执行跳转
            finish();


        super.success(o);
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        if (task != null) {
            task.cancel();
        }
    }

    private void twoseconds() {
        mLoginPresenter.login(acctount.getText().toString(), password.getText().toString());
        //displayDialog();
        //new Thread(new Runnable() {
        //    @Override public void run() {
        //         String acc=acctount.getText().toString();
        //         String pass=password.getText().toString();
        //        JSONObject json = new JSONObject();
        //        try{
        //            json.put("accountId",acc);
        //            json.put("password", Md5Tools.MD5(pass));//加密密码
        //            json.put("clientSecret","a123af4e331cf61c0324cd43cbc2135d");
        //            Log.e("login",   json.toString());
        //           String req = new Model().requsetPost(json.toString(),"http://47.92.49.151:8080/api/apps/BUDDY_API_TEST/accounts/login");
        //            if(req ==null){
        //                mHandler.sendEmptyMessage(2);
        //            }else {
        //                parseJSONObject(req);
        //            }
        //        }catch(JSONException e){
        //            e.printStackTrace();
        //        }
        //    }
        //}).start();
    }
    //protected  boolean requsetPost(String data){
    //    try{
    //        String baseUrl ="http://47.92.49.151:8080/api/apps/BUDDY_API_TEST/accounts/login";
    //        // 请求的参数转换为byte数组
    //        byte[] postData = data.getBytes();
    //        // 新建一个URL对象
    //        URL url=new URL(baseUrl);
    //        // 打开一个HttpURLConnection连接
    //        HttpURLConnection urlConn =(HttpURLConnection) url.openConnection();
    //        // 设置连接超时时间
    //        urlConn.setChunkedStreamingMode(5*1000);
    //        //设置从主机读取数据超时
    //        urlConn.setReadTimeout(5*1000);
    //        // Post请求必须设置允许输出 默认false
    //        urlConn.setDoOutput(true);
    //        //设置请求允许输入 默认是true
    //        urlConn.setDoInput(true);
    //        // Post请求不能使用缓存
    //        urlConn.setUseCaches(false);
    //        // 设置为Post请求
    //        urlConn.setRequestMethod("POST");
    //        //设置本次连接是否自动处理重定向
    //        urlConn.setInstanceFollowRedirects(true);
    //        // 配置请求Content-Type
    //        urlConn.setRequestProperty("Content-Type", "application/json");
    //        // 开始连接
    //        urlConn.connect();
    //        // 发送请求参数
    //        DataOutputStream dos =new DataOutputStream(urlConn.getOutputStream());
    //        dos.write(postData);
    //        dos.flush();
    //        dos.close();
    //        // 判断请求是否成功
    //        if (urlConn.getResponseCode() == 200) {
    //            // 获取返回的数据
    //            String result = streamToString(urlConn.getInputStream());
    //            Log.e("login", "Post方式请求成功，result--->" + result);
    //            parseJSONObject(result);
    //            mHandler.sendEmptyMessage(3);
    //        } else {
    //            Log.e("login", "Post方式请求失败");
    //            urlConn.disconnect();
    //            mHandler.sendEmptyMessage(3);
    //            return false;
    //        }
    //        //关闭连接
    //        urlConn.disconnect();
    //    } catch (Exception e) {
    //        e.printStackTrace();
    //        mHandler.sendEmptyMessage(3);
    //        return false;
    //    }
    //    return true;
    //}

    //protected void parseJSONObject (String jsonData)  {
    //    try {
    //        JSONObject jsonobjiect =   new JSONObject(jsonData);
    //        int code = jsonobjiect.getInt("code");
    //        String message = jsonobjiect.getString("message");
    //        String timestamp = jsonobjiect.getString("timestamp");
    //        Log.e("login", " " + code +" "+message+" "+timestamp);
    //        if(code== 0){
    //            String acc=acctount.getText().toString();
    //            String pass=password.getText().toString();
    //          String   nickName = jsonobjiect.getJSONObject("data").getString("nickName");
    //            SharedPreferences.Editor editor=userInfo.edit();
    //            editor.putString("USER_NAME", acc);
    //            editor.putString("PASSWORD", pass);
    //            editor.putBoolean("login",true);//提交 true
    //            editor.commit();
    //            Intent intent=new Intent(LoginActivity.this,MainActivity.class);//跳转
    //             intent.putExtra("user",nickName);//传递数据
    //              startActivity(intent);//执行跳转
    //               finish();//关闭本界面
    //        }
    //        else {
    //            Message mes = new Message();
    //            mes.obj =message;
    //            mes.what = 1;
    //            mHandler.sendMessage(mes);
    //        }
    //    }catch (Exception e){
    //        e.printStackTrace();
    //    }
    //}

    //关闭时线程动作
}
