package com.example.administrator.buddy.ui.BindDevice;

import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;
import com.example.administrator.buddy.R;

/**
 * Created by zhuj on 2017/10/29 13:32.
 */
public class ScanQRcodeActivity extends AppCompatActivity implements QRCodeView.Delegate {

    @BindView(R.id.zxingview) ZXingView mZxingview;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_device_qr);
        ButterKnife.bind(this);
        mZxingview.setDelegate(this);
    }

    @OnClick(R.id.layout_header_back) public void onBack() {
        finish();
    }



    @Override protected void onStart() {
        super.onStart();
        mZxingview.startCamera();
        mZxingview.showScanRect();
        mZxingview.startSpot();
    }

    @Override protected void onPause() {
        super.onPause();
    }
    @Override protected void onResume() {
        super.onResume();
    }
    @Override protected void onStop() {
        mZxingview.stopCamera();
        super.onStop();
    }

    @Override protected void onRestart() {
        super.onRestart();
    }

    @Override protected void onDestroy() {
        mZxingview.onDestroy();
        super.onDestroy();
    }

    @Override public void onScanQRCodeSuccess(String result) {
        Log.e("QR", result);
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        Intent integer = new Intent(ScanQRcodeActivity.this, BindCodeDeviceActivity.class);
        //Bundle能打包需要传递的数据
        Bundle bundle=new Bundle();
        bundle.putString("bind_code",result);
        integer.putExtras(bundle);
        startActivity(integer);
        finish();
    }
    //手机颤抖
    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @Override public void onScanQRCodeOpenCameraError() {
        Log.e("QR", "失败");
    }

    //Handler mHandler = new Handler() {
    //    @Override public void handleMessage(Message msg) {
    //        super.handleMessage(msg);
    //        switch (msg.what) {
    //            case 1:
    //                break;
    //            case 2:
    //                break;
    //            case 3:
    //                break;
    //        }
    //    }
    //};

}
