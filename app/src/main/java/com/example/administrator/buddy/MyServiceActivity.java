package com.example.administrator.buddy;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhuj on 2017/8/31 20:19.
 */
public class MyServiceActivity extends Activity implements View.OnClickListener {
    @BindView(R.id.btn_service_start) Button mBtnServiceStart;
    @BindView(R.id.btn_service_stop) Button mBtnServiceStop;
    @BindView(R.id.btn_service_bind) Button mBtnServiceBind;
    @BindView(R.id.btn_service_unbind) Button mBtnServiceUnbind;
    private Button start;
    private Button stop;
    private Button bind;
    private Button unbind;
    private MyService.MyBinder myBinder;
    private ServiceConnection connection = new ServiceConnection() {
        @Override public void onServiceConnected(ComponentName name, IBinder service) {
            myBinder = (MyService.MyBinder) service;
            myBinder.startDownload();
        }

        @Override public void onServiceDisconnected(ComponentName name) {
        }
    };

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        ButterKnife.bind(this);
        //start = (Button) findViewById(R.id.btn_service_start);
        //stop = (Button) findViewById(R.id.btn_service_stop);
        //bind = (Button) findViewById(R.id.btn_service_bind);
        //unbind = (Button) findViewById(R.id.btn_service_unbind);
        //start.setOnClickListener(this);
        //stop.setOnClickListener(this);
        //bind.setOnClickListener(this);
        //unbind.setOnClickListener(this);
    }

    @Override public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_service_start:
                Intent startintent = new Intent(this, MyService.class);
                startService(startintent);
                break;
            case R.id.btn_service_stop:
                Intent stopintent = new Intent(this, MyService.class);
                startService(stopintent);
                break;
            case R.id.btn_service_bind:
                Intent bindintent = new Intent(this, MyService.class);
                bindService(bindintent, connection, BIND_AUTO_CREATE);
                break;
            case R.id.btn_service_unbind:
                unbindService(connection);
                break;
            default:
                break;
        }//一个Service必须要在既没有和任何Activity关联又处理停止状态的时候才会被销毁。
    }
}
