package com.example.administrator.buddy;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by zhuj on 2017/8/31 20:05.
 */
public class MyService  extends Service {
    //主要用于在后台处理一些耗时的逻辑，或者去执行某些需要长期运行的任务。必要的时候我们甚至可以在程序退出的情况下，让Service在后台继续保持运行状态。
    public static final String Tag = "MyService";
    private MyBinder mBinder = new MyBinder();
    public void onCreate (){
        super.onCreate();
        Log.e(Tag,"onCreate executed");
    }

    public int  onStartCommand(Intent intent, int flags, int startId) {
        Log.d(Tag, "onStartCommand() executed");
        return super.onStartCommand(intent, flags, startId);
    }

    public void onDestroy() {
        super.onDestroy();
        Log.d(Tag, "onDestroy() executed");
    }

    @Nullable @Override public IBinder onBind(Intent intent) {
        return mBinder;
    }

    class MyBinder extends Binder{

        public void startDownload() {
            Log.d(Tag, "startDownload() executed");
            // 执行具体的下载任务
        }
    }
//   创建前台Service
    //@Override
    //public void onCreate() {
    //    super.onCreate();
    //    Notification notification = new Notification(R.drawable.ic_launcher,
    //            "有通知到来", System.currentTimeMillis());
    //    Intent notificationIntent = new Intent(this, MainActivity.class);
    //    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
    //            notificationIntent, 0);
    //    notification.setLatestEventInfo(this, "这是通知的标题", "这是通知的内容",
    //            pendingIntent);
    //    startForeground(1, notification);
    //    Log.d(TAG, "onCreate() executed");
    //}

}

