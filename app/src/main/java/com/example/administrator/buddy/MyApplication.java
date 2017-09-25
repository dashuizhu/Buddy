package com.example.administrator.buddy;

import android.app.Application;
import android.content.Context;
import com.example.administrator.buddy.injector.components.AppComponent;
import com.example.administrator.buddy.injector.components.DaggerAppComponent;
import com.example.administrator.buddy.injector.modules.AppModule;
import com.example.administrator.buddy.network.IHttpAPI;
import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by zhuj on 2017/9/3 22:38.
 */
public class MyApplication extends Application {

    public static AppComponent mAppComponent;
    public static MyApplication mapp;

    @Override public void onCreate() {
        super.onCreate();
        mapp =this;
        mAppComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
        Fresco.initialize(this);

    }
    public static IHttpAPI getIHttpApi() {
        return mAppComponent.getHttpApi();
    }
    public static Context getContext(){

        return mapp.getApplicationContext();
    }

}
