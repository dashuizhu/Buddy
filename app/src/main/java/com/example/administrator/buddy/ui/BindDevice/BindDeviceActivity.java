package com.example.administrator.buddy.ui.BindDevice;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.administrator.buddy.BaseActivity;
import com.example.administrator.buddy.R;
import com.example.administrator.buddy.bean.DeviceContactsResult;
import com.example.administrator.buddy.bean.NetworkResult;
import com.example.administrator.buddy.bean.UserBindDeviceBean;
import com.example.administrator.buddy.injector.components.DaggerPresenterComponent;
import com.example.administrator.buddy.injector.components.PresenterComponent;
import com.example.administrator.buddy.injector.modules.ModelModule;
import com.example.administrator.buddy.ui.BindDevice.presenter.DeviceBindPresenter;
import com.example.administrator.buddy.ui.device.UserConcernsAddActivity;
import com.example.administrator.buddy.utils.SharedPreUser;
import com.example.administrator.buddy.view.HeaderView;
import com.example.administrator.buddy.view.MyItemView;
import com.facebook.drawee.view.SimpleDraweeView;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhuj on 2017/9/29 21:33.
 */
public class BindDeviceActivity extends BaseActivity {

    @BindView(R.id.headerView) HeaderView mHeaderView;
    @BindView(R.id.item_contacts_name) MyItemView mItemContactsName;
    @BindView(R.id.img_contacts_right) ImageView mImgContactsRight;
    @BindView(R.id.textView_my_item_head) SimpleDraweeView mTextViewMyItemHead;
    @BindView(R.id.item_contacts_head) RelativeLayout mItemContactsHead;
    @BindView(R.id.edit_contacts_name) EditText mEditContactsName;
    @BindView(R.id.btn_confirm) Button mBtnConfirm;
    private UserBindDeviceBean mUserBean;
    private DeviceBindPresenter mUserPresenter;

    private List<UserBindDeviceBean> mList;
    private SharedPreferences attentionUserSql;
    private String url = null;
    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_bind);
        ButterKnife.bind(this);
        injectorPresenter();
        mUserBean=new UserBindDeviceBean();
        attentionUserSql =getSharedPreferences("mapurl", 0);
        url=attentionUserSql.getString("url", "");
        mTextViewMyItemHead.setImageURI(url);

    }

    @OnClick(R.id.layout_header_back) public void onBack() {
        finish();
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

    private void inprogress(){

    }

    private void injectorPresenter() {

        PresenterComponent authenticationComponent =
                DaggerPresenterComponent.builder().modelModule(new ModelModule(this)).build();
        mUserPresenter = authenticationComponent.getDeviceBindPresenter();
    }

    @OnClick({ R.id.textView_my_item_head, R.id.edit_contacts_name ,R.id.item_contacts_name,R.id.btn_confirm})
    public void onViewClicked(View view) {

        switch (view.getId()) {
            case R.id.item_contacts_name:

                break;
            case R.id.textView_my_item_head:

                break;
            case R.id.edit_contacts_name:

                break;
            case R.id.btn_confirm:
                mUserBean.setRelation(0);
                mUserBean.setUserName(mEditContactsName.getText().toString().trim());
                //url.toString();
                mUserBean.setAvatar("1");
                UserBindDeviceBean.HolderBean Holder =new UserBindDeviceBean.HolderBean();
                mUserBean.setHolder(Holder);
                mList=  new ArrayList<>();
                mList.add(mUserBean);
                mUserPresenter.bindeDevice(mList);
                break;
        }
    }

    @Override public void success(Object o) {
        Log.e("attentionUser",o.toString());
        if (o instanceof DeviceContactsResult){
            NetworkResult list  = (DeviceContactsResult) o;
            SharedPreUser.getInstance().put(this,SharedPreUser.KEY_USER_ID,list.getUserId());
            SharedPreUser.getInstance().put(this,SharedPreUser.KEY_DEVICE_ID,list.getDeviceId());
            Intent integer = new Intent(BindDeviceActivity.this, UserConcernsAddActivity.class);
            startActivity(integer);
        }
        super.success(o);
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }
}
