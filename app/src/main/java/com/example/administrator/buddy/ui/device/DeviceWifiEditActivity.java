package com.example.administrator.buddy.ui.device;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.administrator.buddy.AppString;
import com.example.administrator.buddy.BaseActivity;
import com.example.administrator.buddy.R;
import com.example.administrator.buddy.bean.DeviceWiFiBean;
import com.example.administrator.buddy.bean.NetworkResult;
import com.example.administrator.buddy.injector.components.DaggerPresenterComponent;
import com.example.administrator.buddy.injector.components.PresenterComponent;
import com.example.administrator.buddy.injector.modules.ModelModule;
import com.example.administrator.buddy.ui.device.presenter.DeviceWiFiPresenter;
import com.example.administrator.buddy.view.HeaderView;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhuj on 2017/9/29 21:33.
 */
public class DeviceWifiEditActivity extends BaseActivity {

    private DeviceWiFiBean mWiFiBean;
    private DeviceWiFiPresenter mWiFiPresenter;
    @BindView(R.id.headerView) HeaderView mHeaderView;
    @BindView(R.id.edit_contacts_name) EditText mEditContactsName;
    @BindView(R.id.edit_contacts_phone) EditText mEditContactsPhone;
    @BindView(R.id.btn_confirm) Button mBtnConfirm;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_wifi_add);
        ButterKnife.bind(this);
        mWiFiBean =new DeviceWiFiBean();
        mWiFiBean = getIntent().getParcelableExtra(AppString.KEY_DEVICE_CONTACTS);
        inViews();
        injectorPresenter();
    }

    private void inViews(){
        mHeaderView.setTitle("编辑WiFi");
        mBtnConfirm.setText(R.string.label_confirm);
        mEditContactsName.setText(mWiFiBean.getSsid());
        mEditContactsPhone.setText(mWiFiBean.getPassword());
    }



    @OnClick(R.id.layout_header_back) public void onBack() {
        finish();
    }

    @OnClick(R.id.btn_confirm) public void onViewClicked() {
        mWiFiBean.setSsid(mEditContactsName.getText().toString().trim());
        mWiFiBean.setPassword(mEditContactsPhone.getText().toString().trim());
        List<DeviceWiFiBean> list = new ArrayList<>();
        list.add(mWiFiBean);
        Log.e("wififi",list.toString());
        mWiFiPresenter.modifyWifi(list);

    }
    private void injectorPresenter() {

        PresenterComponent authenticationComponent = DaggerPresenterComponent.builder()
                .modelModule(new ModelModule(this))
                .build();
        mWiFiPresenter = authenticationComponent.getDeviceWiFiPresenter();
    }

    @Override public void success(Object o) {
        if (o instanceof NetworkResult){
            String mesge=((NetworkResult)o).getMessage();
            Toast.makeText(DeviceWifiEditActivity.this,mesge,Toast.LENGTH_SHORT).show();
            onBack();
        }
        super.success(o);
    }
}
