package com.example.administrator.buddy;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.WindowManager;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by zhuj on 2017/8/13 13:08.
 */

public class AccountAcyivity extends Activity {

    private TimerTask task;
    private SharedPreferences userInfo;
    private Subscription mDonwcount;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_accout);
        twoseconds();
    }

    private void twoseconds() {

        mDonwcount= Observable.timer(2, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Long>() {
                    @Override public void onCompleted() {
                        finish();
                    }

                    @Override public void onError(Throwable e) {
                        onCompleted();
                    }
                    @Override public void onNext(Long aLong) {

                        //退出全屏
                        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,
                                WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

                        Intent intent;
                        userInfo = getSharedPreferences("userInfo", 0);
                        Boolean name = userInfo.getBoolean("login", false);//设置 Boolean类型的name 存储的消息login 是false
                        Boolean first = userInfo.getBoolean("isFirst", false);
                        if(!first) {
                            if (name) {
                                intent = new Intent(AccountAcyivity.this, MainActivity.class);
                            } else {
                                intent = new Intent(AccountAcyivity.this, LoginActivity.class);
                            }
                        }else{
                            intent = new Intent(AccountAcyivity.this, BootInterfaceActivity.class);
                        }
                        startActivity(intent);
                    }

                });

        //Timer timer=new Timer();
        // task= new TimerTask() {
        //    @Override public void run() {
        //        Intent intent;
        //        userInfo =getSharedPreferences("userInfo", 0);
        //        Boolean name=userInfo.getBoolean("login", false);//设置 Boolean类型的name 存储的消息login 是false
        //        Boolean first=userInfo.getBoolean("isFirst",false);
        //        //判断Boolean name trun 或者false
        //        if(!first) {
        //            if (name) {
        //                intent = new Intent(AccountAcyivity.this, MainActivity.class);
        //            } else {
        //                intent = new Intent(AccountAcyivity.this, LoginActivity.class);
        //            }
        //        }else{
        //            intent = new Intent(AccountAcyivity.this, BootInterfaceActivity.class);
        //        }
        //        startActivity(intent);
        //        finish();
        //    }
        //};
        //timer.schedule(task,2*1000);
    }

    //关闭时线程动作
    @Override protected void onDestroy() {
        super.onDestroy();
        if (task != null) {
            task.cancel();
        }
        if (mDonwcount != null) {
            if (!mDonwcount.isUnsubscribed()) {
                mDonwcount.unsubscribe();
            }
            mDonwcount = null;
        }
    }
}
