package com.example.administrator.buddy;

import android.app.Application;
import android.content.Context;

/**
 * Created by zhuj on 2017/9/3 22:38.
 */
public class MyApplication extends Application {

    public static MyApplication mapp;

    @Override public void onCreate() {
        super.onCreate();
        mapp =this;
    }

    public static Context getContext(){

        return mapp.getApplicationContext();
    }
}
