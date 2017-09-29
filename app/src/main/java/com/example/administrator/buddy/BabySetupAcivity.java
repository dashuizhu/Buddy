package com.example.administrator.buddy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.qqtheme.framework.picker.DatePicker;
import com.example.administrator.buddy.bean.HabitBean;
import com.example.administrator.buddy.controls.CombinationControlsTwo;
import com.example.administrator.buddy.injector.components.DaggerPresenterComponent;
import com.example.administrator.buddy.injector.components.PresenterComponent;
import com.example.administrator.buddy.injector.modules.ModelModule;
import com.example.administrator.buddy.presenter.LoginPresenter;
import com.facebook.drawee.view.SimpleDraweeView;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;
import me.iwf.photopicker.PhotoPicker;

/**
 * Created by zhuj on 2017/8/24 14:20.
 */
public class BabySetupAcivity extends BaseActivity {
    @BindView(R.id.imageview_babysetup) ImageView mImageviewBabysetup;//
    @BindView(R.id.tv_baby_save) TextView mTvBabySave;
    @BindView(R.id.imageview_babysetup1) SimpleDraweeView mImageviewBabysetup1;
    @BindView(R.id.iamgeview_babysetup2) ImageView mIamgeviewBabysetup2;
    @BindView(R.id.tv_baby_name) CombinationControlsTwo mTvBabyName;
    @BindView(R.id.tv_baby_local) CombinationControlsTwo mTvBabyLocal;
    @BindView(R.id.tv_baby_birthday) CombinationControlsTwo mTvBabyBirthday;
    @BindView(R.id.tv_baby_school) CombinationControlsTwo mTvBabySchool;
    @BindView(R.id.tv_baby_schoolYear) CombinationControlsTwo mTvBabySchoolYear;
    @BindView(R.id.tv_baby_class) CombinationControlsTwo mTvBabyClass;
    private PoputextDialog mPoputextDialog;
    private LoginPresenter mLoginPresenter;
    private List<HabitBean> mList;
    private ImageView setup;
    private TextView save;
    private LoadDialog mLoadDialog;
    private TimerTask task;
    private String url = null;
    private String urllabel;
    private SharedPreferences mapurl;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_setup_babysetup);
        ButterKnife.bind(this);
        //map();
        mapurl =getSharedPreferences("mapurl", 0);
        url=mapurl.getString("url", "");
        mImageviewBabysetup1.setImageURI(url);
        //P层对象初始化
        PresenterComponent authenticationComponent = DaggerPresenterComponent.builder()
                .modelModule(new ModelModule(this))
                .build();
        mLoginPresenter = authenticationComponent.getLoginPresenter();
        mLoginPresenter.babysetupMdel();
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode== RESULT_OK&&requestCode == PhotoPicker.REQUEST_CODE){
            ArrayList<String> photos= data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
            url="file://"+photos.get(0);
            Log.e("url",url);
            mImageviewBabysetup1.setImageURI(url);
            SharedPreferences.Editor editor = mapurl.edit();
            editor.putString("url",url);
            editor.commit();
        }
    }

    //默认图片加载，设置
    //protected void map(){
    //    GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(getResources());
    //    /**
    //     * 设置淡入淡出效果的显示时间
    //     * */
    //    GenericDraweeHierarchy hierarchy = builder.setFadeDuration(1000).build();
    //    DraweeController placeHolderDraweeController = Fresco.newDraweeControllerBuilder()
    //            .setUri("mipmap-xhdpi/babys_watch_headphoto_img.png") //为图片设置url
    //            .setTapToRetryEnabled(true)   //设置在加载失败后,能否重试
    //            .setOldController(mImageviewBabysetup1.getController())
    //            .build();
    //    mImageviewBabysetup1.setController(placeHolderDraweeController);
    //    mImageviewBabysetup1.setHierarchy(hierarchy);
    //}

    //protected void babysetup() {
    //    setup = (ImageView) findViewById(R.id.imageview_babysetup);
    //    setup.setOnClickListener(new View.OnClickListener() {
    //        @Override public void onClick(View v) {
    //            finish();
    //        }
    //    });
    //    save = (TextView) findViewById(R.id.tv_baby_save);
    //    save.setOnClickListener(new View.OnClickListener() {
    //        @Override public void onClick(View v) {
    //            twoseconds();
    //        }
    //    });
    //    name.setOnClickListener(new View.OnClickListener() {
    //        @Override public void onClick(View v) {
    //            mPoputextDialog = new PoputextDialog(BabySetupAcivity.this);
    //            mPoputextDialog.show();
    //            String text = name.getcontent().getText().toString();
    //            mPoputextDialog.setmText(text);
    //            mPoputextDialog.setOnClickconfirm(new PoputextDialog.onClickconfirm() {
    //                @Override public void onClickcon(String s) {
    //                    name.getcontent().setText(s);
    //                    mPoputextDialog.dismiss();
    //                }
    //                @Override public void onClickcancel() {
    //                    mPoputextDialog.dismiss();
    //                }
    //            });
    //        }
    //    });
    //}
    @OnClick({
            R.id.imageview_babysetup, R.id.tv_baby_save, R.id.imageview_babysetup1,
            R.id.iamgeview_babysetup2, R.id.tv_baby_name, R.id.tv_baby_local, R.id.tv_baby_birthday,
            R.id.tv_baby_school, R.id.tv_baby_schoolYear, R.id.tv_baby_class
    }) public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageview_babysetup:
                getIntent().putExtra("avatarurl", url);
                setResult(RESULT_OK,getIntent());
                finish();
                break;
            case R.id.tv_baby_save:
                twoseconds();
                break;
            case R.id.imageview_babysetup1:
              PhotoPicker picker= new  PhotoPicker();
                picker.builder()
                        .setPhotoCount(1)
                        .setShowCamera(true)
                        .setShowGif(true)
                        .setPreviewEnabled(false)
                        .start(this, PhotoPicker.REQUEST_CODE);
                break;
            case R.id.iamgeview_babysetup2:
                break;
            case R.id.tv_baby_name:

                break;
            case R.id.tv_baby_local:
                break;
            case R.id.tv_baby_birthday:
                int year,  month, day;
                DatePicker datePicker=new DatePicker(BabySetupAcivity.this);
                datePicker.setRangeStart(2000,1,1);
                datePicker.setRangeEnd(2020,12,31);
                String text=mTvBabyBirthday.getcontent().getText().toString();
                String time[] =text.split("-");
                year= Integer.parseInt(time[0]);
                month= Integer.parseInt(time[1]);
                day= Integer.parseInt(time[2]);
                datePicker.setSelectedItem(year,month,day);
                datePicker.show();
                datePicker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
                    @Override public void onDatePicked(String year, String month, String day) {
                        mTvBabyBirthday.getcontent().setText(year+"-"+month+"-"+day);
                        Log.e("DateDilog",year+month+day);
                    }
                });
                break;
            case R.id.tv_baby_school:
                break;
            case R.id.tv_baby_schoolYear:
                break;
            case R.id.tv_baby_class:
                break;
        }
    }

    Handler mHandler = new Handler() {
        @Override public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    Toast.makeText(BabySetupAcivity.this, " ", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    Toast.makeText(BabySetupAcivity.this, "网络异常", Toast.LENGTH_SHORT).show();
                    break;
                case 3:
                    mLoadDialog.dismiss();
                    break;
                case 4:
                    mList = (List<HabitBean>) msg.obj;
                    for (int i = 0; i < mList.size(); i++) {
                        try {
                            HabitBean habit = mList.get(i);
                            mTvBabyName.getcontent().setText(habit.getName());
                            mTvBabyBirthday.getcontent().setText(habit.getBirthday());
                            mTvBabySchool.getcontent().setText(habit.getSchool());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }
        }
    };

    @Override public void success(final Object o) {
        super.success(o);
        if (o instanceof List) {
            //mList = (List<HabitBean>) o;得到的mlist不能更新主线程的mlist用message方法封装得到的o传递给Handler更新UI
            //mAdapter.notifyDataSetChanged();
            Message mess = new Message();
            mess.obj = o;
            mess.what = 1;
            mHandler.sendMessage(mess);
        }
    }

    //private void requestGet() {
    //    try {
    //        String baseUrl =
    //                "http://47.92.49.151:8080/api/devices/777777777777777/holder/profile?access_token=c2c5e568-4a15-40c1-b890-e1bcabc566a4&userId=4bc7e2383c404841b6b66b18ac1c9321";
    //        //  StringBuilder tempParams = new StringBuilder();
    //        // String requestUrl = baseUrl + tempParams.toString();
    //        // 新建一个URL对象
    //        URL url = new URL(baseUrl);
    //        // 打开一个HttpURLConnection连接
    //        HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
    //        // 设置连接主机超时时间
    //        urlConn.setConnectTimeout(5 * 1000);
    //        //设置从主机读取数据超时
    //        urlConn.setReadTimeout(5 * 1000);
    //        // 设置是否使用缓存  默认是true
    //        urlConn.setUseCaches(true);
    //        // 设置为Post请求
    //        urlConn.setRequestMethod("GET");
    //        //urlConn设置请求头信息
    //        //设置请求中的媒体类型信息。
    //        urlConn.setRequestProperty("Content-Type", "application/json");
    //        //设置客户端与服务连接类型
    //        urlConn.addRequestProperty("Connection", "Keep-Alive");
    //        urlConn.connect();
    //
    //        if (urlConn.getResponseCode() == 200) {
    //
    //            String result = streamToString(urlConn.getInputStream());
    //            if (result == null) {
    //                reg();
    //            } else {
    //                Log.e("login", "Get方式请求成功，result--->" + result);
    //                babyJSONObject(result);
    //            }
    //        } else {
    //            Log.e("login", "Get方式请求失败");
    //        }
    //        // 关闭连接
    //        urlConn.disconnect();
    //    } catch (Exception e) {
    //        Log.e("login", e.toString());
    //    }
    //    mHandler.sendEmptyMessage(3);
    //}

    //protected boolean requsetPost(String data) {
    //    try {
    //        String baseUrl =
    //                "http://47.92.49.151:8080/api/devices/777777777777777/holder/profile?access_token=c2c5e568-4a15-40c1-b890-e1bcabc566a4&userId=4bc7e2383c404841b6b66b18ac1c9321";
    //        // 请求的参数转换为byte数组
    //        byte[] postData = data.getBytes();
    //        // 新建一个URL对象
    //        URL url = new URL(baseUrl);
    //        // 打开一个HttpURLConnection连接
    //        HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
    //        // 设置连接超时时间
    //        urlConn.setChunkedStreamingMode(5 * 1000);
    //        //设置从主机读取数据超时
    //        urlConn.setReadTimeout(5 * 1000);
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
    //        DataOutputStream dos = new DataOutputStream(urlConn.getOutputStream());
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

    //protected void parseJSONObject(String jsonData) {
    //    try {
    //        JSONObject jsonobjiect = new JSONObject(jsonData);
    //        int code = jsonobjiect.getInt("code");
    //        String message = jsonobjiect.getString("message");
    //        String timestamp = jsonobjiect.getString("timestamp");
    //        Log.e("login", " " + code + " " + message + " " + timestamp);
    //        if (code == 0) {
    //            String acc = name.getcontent().getText().toString();
    //            SharedPreferences.Editor editor = userInfo.edit();
    //            editor.putString("USER_NAME", acc);
    //            editor.commit();
    //        } else {
    //            Message mes = new Message();
    //            mes.obj = message;
    //            mes.what = 1;
    //            mHandler.sendMessage(mes);
    //        }
    //    } catch (Exception e) {
    //        e.printStackTrace();
    //    }
    //}

    private void twoseconds() {
        mLoginPresenter.babySetupMdelPost(mTvBabyName.getcontent().getText().toString(),
                mTvBabyBirthday.getcontent().getText().toString(),
                mTvBabySchool.getcontent().getText().toString(),
                mTvBabySchoolYear.getcontent().getText().toString());
        //mLoadDialog = new LoadDialog(this);
        //mLoadDialog.show();
        //new Thread(new Runnable() {
        //    @Override public void run() {
        //        String nam = name.getcontent().getText().toString();
        //        String bir = birthday.getcontent().getText().toString();
        //        String sch = school.getcontent().getText().toString();
        //        String yea = schoolYear.getcontent().getText().toString();
        //        JSONObject json = new JSONObject();
        //        try {
        //            json.put("name", nam);
        //            json.put("sim", "13800000001");
        //            json.put("birthday", bir);
        //            json.put("gender", 0);
        //            json.put("height", 120);
        //            json.put("weight", 30);
        //            json.put("grade", 0);
        //            json.put("avatar", 2);
        //            json.put("school", sch);
        //            json.put("startSchool", yea);
        //            Log.e("login", json.toString());
        //            boolean req = requsetPost(json.toString());
        //            if (!req) {
        //                mHandler.sendEmptyMessage(2);
        //            }
        //        } catch (JSONException e) {
        //            e.printStackTrace();
        //        }
        //    }
        //}).start();
    }

    //关闭时线程动作
    @Override protected void onDestroy() {
        super.onDestroy();
        if (task != null) {
            task.cancel();
        }
    }
}
