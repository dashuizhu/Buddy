package com.example.administrator.buddy.ui.BindDevice;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.administrator.buddy.BaseActivity;
import com.example.administrator.buddy.R;

/**
 * Created by zhuj on 2017/10/29 13:32.
 */
public class AddDeviceActivity extends BaseActivity {

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_add);
        ButterKnife.bind(this);
    }

    @Override protected void onStart() {
        super.onStart();
    }

    @Override protected void onResume() {
        super.onResume();
    }

    @Override protected void onPause() {
        super.onPause();
    }

    @Override protected void onStop() {
        super.onStop();
    }

    @Override protected void onRestart() {
        super.onRestart();
    }

    @Override protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick(R.id.layout_header_back) public void onBack() {
        finish();
    }
    @OnClick(R.id.btn_confirm) public void onViewClicked() {
        Intent integer =new Intent(AddDeviceActivity.this,ScanQRcodeActivity.class);
        startActivity(integer);
        finish();
    }


}
