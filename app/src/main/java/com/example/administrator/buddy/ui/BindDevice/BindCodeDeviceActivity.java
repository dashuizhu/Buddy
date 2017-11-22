package com.example.administrator.buddy.ui.BindDevice;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.administrator.buddy.BaseActivity;
import com.example.administrator.buddy.R;
import com.example.administrator.buddy.bean.DeviceStatusBean;
import com.example.administrator.buddy.bean.DeviceStatusResult;
import com.example.administrator.buddy.injector.components.DaggerPresenterComponent;
import com.example.administrator.buddy.injector.components.PresenterComponent;
import com.example.administrator.buddy.injector.modules.ModelModule;
import com.example.administrator.buddy.ui.BindDevice.presenter.DeviceBindPresenter;
import com.example.administrator.buddy.utils.SharedPreUser;

/**
 * Created by zhuj on 2017/10/29 13:32.
 */
public class BindCodeDeviceActivity extends BaseActivity {

    @BindView(R.id.editText) TextView mEditText;
    @BindView(R.id.btn_confirm) Button mBtnConfirm;
    private DeviceBindPresenter mPresenter;
    private String bind;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_bind_code);
        ButterKnife.bind(this);
        injectorPresenter();
        Bundle bundle = this.getIntent().getExtras();
        bind = bundle.getString("bind_code");
        mEditText.setText(bind);
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
        mPresenter.getdeviceStatus(bind);
    }

    //private void inview(){
    //    Bundle bundle= this.getIntent().getExtras();
    //    String bind =bundle.getString("bind");
    //    mEditText.setText(bind);
    //}

    @Override public void success(Object o) {
        DeviceStatusBean data = ((DeviceStatusResult) o).getFamilyBeanList();

        switch (data.getCurrentStatus()) {
            case 0:
                Toast.makeText(this, "设备已停用", Toast.LENGTH_LONG).show();
                break;
            case 1:
                bindDevice(data.getBindStaus());
                break;
            case 2:
                Toast.makeText(this, "设备正在维修", Toast.LENGTH_LONG).show();
                break;
            case 3:
                Toast.makeText(this, "设备已被冻洁", Toast.LENGTH_LONG).show();
                break;
        }
        super.success(o);
    }

    private void injectorPresenter() {
        PresenterComponent component =
                DaggerPresenterComponent.builder().modelModule(new ModelModule(this)).build();
        mPresenter = component.getDeviceBindPresenter();
    }

    private void bindDevice(int i) {
        SharedPreUser.getInstance().put(this,SharedPreUser.KEY_BIND_CODE,bind);
        Bundle bundle=new Bundle();
        if (i == 1) {
            Toast.makeText(this, "设备已被绑定", Toast.LENGTH_LONG).show();
            //Intent integer = new Intent(BindCodeDeviceActivity.this, UserConcernsAddActivity.class);
            //startActivity(integer);
            bundle.putBoolean("bind_status",true);
        } else {
            bundle.putBoolean("bind_status",false);
        }
            //Intent integer = new Intent(BindCodeDeviceActivity.this, BindDeviceActivity.class);
            //startActivity(integer);
        Intent integer = new Intent(BindCodeDeviceActivity.this, UserConcernsActivity.class);
        integer.putExtras(bundle);
        startActivity(integer);
        finish();
    }
}
