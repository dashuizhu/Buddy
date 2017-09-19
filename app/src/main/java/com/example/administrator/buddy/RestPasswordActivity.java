package com.example.administrator.buddy;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.administrator.buddy.presenter.LoginPresenter;

public class RestPasswordActivity extends BaseActivity {
    EditText password;
    EditText acctount;
    EditText verifiaction;
    Button yzm;
    public Thread thread ;
    protected  LoadDialog mLoadDialog;
    LoginPresenter mLoginPresenter;


    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpassword);
        mLoginPresenter = new LoginPresenter(this);
        regis();

    }
    Handler mHandler =new Handler(){
        @Override public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    yzm.setText(""+msg.arg1);
                    break;
                case  2:
                    yzm.setText(R.string.app_logo_yzm);
                    yzm.setBackgroundResource(R.color.colorBule);
                    yzm.setEnabled(true);
                    break;
                case 3:
                    Toast.makeText(RestPasswordActivity.this,"网络异常" ,Toast.LENGTH_SHORT).show();
                    break;
                case 4:
                    mLoadDialog.dismiss();
                    break;
                case 5:
                    Toast.makeText(RestPasswordActivity.this,"发送成功" ,Toast.LENGTH_SHORT).show();
                    break;
                case 6:
                    Toast.makeText(RestPasswordActivity.this,"发送失败" ,Toast.LENGTH_SHORT).show();
                    break;
                case 7:
                    Toast.makeText(RestPasswordActivity.this,""+msg.obj ,Toast.LENGTH_SHORT).show();
                    break;
                case 8:
                    Toast.makeText(RestPasswordActivity.this,"重置密码成功" ,Toast.LENGTH_SHORT).show();
                    break;

            }
        }
    };

    protected void regis(){
        acctount = (EditText) findViewById(R.id.et_zh2);
        password = (EditText) findViewById(R.id.et_pssword2);
        verifiaction= (EditText) findViewById(R.id.et_yzm);
        yzm =(Button) findViewById(R.id.btn_yzm);
        yzm.setOnClickListener(new View.OnClickListener(){


            @Override public void onClick(View v  ){
                String acc =acctount.getText().toString();
                if (acc.length()==0){
                    Toast.makeText(RestPasswordActivity.this,"账号不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    ver();
                    Log.e("Register1", "显示的账号"+acc);
                    return;
                }
            }
        });

        findViewById(R.id.btn_register).setOnClickListener(new View.OnClickListener(){
            @Override public void onClick(View v  ){
                String acc =acctount.getText().toString();
                String pas =password.getText().toString();
                String ver =verifiaction.getText().toString();
                if (acc .length()==0){
                    Toast.makeText(RestPasswordActivity.this,"账号不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(acc.length()<6||acc.length()>20){
                    Toast.makeText(RestPasswordActivity.this,"输入账号不能少于6位数或者大于20位数",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (pas.length()==0){
                    Toast.makeText(RestPasswordActivity.this,"密码不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (pas.length()<6){
                    Toast.makeText(RestPasswordActivity.this,"输入密码不能少于6位数或者大于20位数",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(ver.length()==0){
                    Toast.makeText(RestPasswordActivity.this,"验证码不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    reg();
                }
            }
        });
    }



    //启动线程倒计时
     public void ver(){
         mLoginPresenter.verMdoel(acctount.getText().toString());
        thread = new Thread(new Runnable() {
             @Override public void run() {
                 for (int i =60; i >= 0 ;i--){
                     try {
                         Thread.sleep(1000);
                     }
                     //异常处理
                     catch (InterruptedException e) {
                         e.printStackTrace();
                         return;
                     }
                     Message message=new Message();
                     message.what=1;
                     message.arg1=i;
                     mHandler.sendMessage(message);//传递参数1或i
                    // yzm.setText(""+i);
                 }//
                 mHandler.sendEmptyMessage(2);
                // yzm.setText(R.string.app_logo_yzm);
             }
         });
         thread.start();//触发按钮
         yzm.setEnabled(false);//设置不可点击
         //yzm.setBackgroundResource(R.color.colorwhite);//设置按键颜色
     }

    @Override public void success(Object o) {
        finish();
        super.success(o);
    }
    public void reg(){
        mLoginPresenter.register(acctount.getText().toString(),password.getText().toString(),verifiaction.getText().toString(),false);
        //mLoadDialog= new LoadDialog(this);
        //mLoadDialog.show();
        //thread = new Thread(new Runnable() {
        //    @Override public void run() {
        //
        //        String acc = acctount.getText().toString();
        //        String pas = password.getText().toString();
        //        String ver =verifiaction.getText().toString();
        //        JSONObject json = new JSONObject();
        //        try {
        //            json.put("accountId", acc);
        //            json.put("password", Md5Tools.MD5(pas));
        //            json.put("verifyCode", ver);
        //            Log.e("registered", json.toString());
        //            rPostReg(json.toString());
        //        } catch (JSONException e) {
        //            e.printStackTrace();
        //        }
        //    }
        //});
        //thread.start();
    }

    //销毁线程， thread值不为空是打断并thread赋予为空
    @Override protected void onDestroy() {
        super.onDestroy();
        if(thread!=null) {
            thread.interrupt();
            thread=null;
        }
    }



}
