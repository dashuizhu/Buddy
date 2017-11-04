package com.example.administrator.buddy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.example.administrator.buddy.controls.CombinationControls;
import com.example.administrator.buddy.ui.BindDevice.AddDeviceActivity;
import com.example.administrator.buddy.ui.device.DeviceContactsActivity;
import com.example.administrator.buddy.ui.device.DeviceWifiActivity;
import com.example.administrator.buddy.ui.device.UserConcernsListActivity;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by zhuj on 2017/8/16 14:41.
 */
public class MainFragmentSetup extends Fragment {
    @BindView(R.id.iamgeview_setup1) SimpleDraweeView mIamgeviewSetup1;
    @BindView(R.id.textview_setup1) TextView mTextviewSetup1;
    @BindView(R.id.imageview_setup2) RelativeLayout mImageviewSetup2;
    @BindView(R.id.item_verified) CombinationControls mItemVerified;
    @BindView(R.id.item_bill) CombinationControls mItemBill;
    @BindView(R.id.item_contacts) CombinationControls mItemContacts;
    @BindView(R.id.item_family) CombinationControls mItemFamily;
    @BindView(R.id.item_disable_period) CombinationControls mItemDisablePeriod;
    @BindView(R.id.item_wifi) CombinationControls mItemWifi;
    @BindView(R.id.item_positioning) CombinationControls mItemPositioning;
    @BindView(R.id.item_safe_area) CombinationControls mItemSafeArea;
    @BindView(R.id.item_unbundled_device) CombinationControls mItemUnbundledDevice;
    @BindView(R.id.item_find_device) CombinationControls mItemFindDevice;
    @BindView(R.id.item_shut_down_device) CombinationControls mItemShutDownDevice;
    @BindView(R.id.item_share_device) CombinationControls mItemShareDevice;
    @BindView(R.id.item_APP_manage) CombinationControls mItemAPPManage;
    @BindView(R.id.tv_setup_dropOut) Button mTvSetupDropOut;
    Unbinder unbinder;
    @BindView(R.id.line_setup) LinearLayout mLineSetup;
    private RelativeLayout setup1;
    private SharedPreferences userInfo;
    private SimpleDraweeView iamgeview_setup1;
    private TextView mname;
    private String url = null;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        //对View中控件的操作方法
        View view = inflater.inflate(R.layout.fragment_setup, container, false);
        userInfo = getActivity().getSharedPreferences("mapurl", 0);
        setup1 = (RelativeLayout) view.findViewById(R.id.imageview_setup2);
        iamgeview_setup1 = (SimpleDraweeView) view.findViewById(R.id.iamgeview_setup1);
        mname = (TextView) view.findViewById(R.id.textview_setup1);
        url = userInfo.getString("url", "");
        mname.setText(userInfo.getString("name", ""));
        iamgeview_setup1.setImageURI(url);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 11) {
            String url = data.getStringExtra("avatarurl");
            Log.e("uel", url);
            iamgeview_setup1.setImageURI(url);
            String babyname = data.getStringExtra("babyName");
            mname.setText(babyname);
        }
    }

    private void signOntButton() {
        // dropOut =(TextView)view().findViewById(R.id.tv_setup_dropOut);

    }

    protected void babysetup() {
        //setup1.setOnClickListener(new View.OnClickListener() {
        //    @Override public void onClick(View v) {
        //        Intent intent = new Intent(getContext(), BabySetupAcivity.class);
        //        intent.putExtra("Avatar", 1);
        //        startActivityForResult(intent, 11);
        //    }
        //});
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({
            R.id.item_verified, R.id.item_bill, R.id.item_contacts, R.id.item_family,
            R.id.item_disable_period, R.id.item_wifi, R.id.item_positioning, R.id.item_safe_area,
            R.id.item_unbundled_device, R.id.item_find_device, R.id.item_shut_down_device,
            R.id.item_share_device, R.id.item_APP_manage, R.id.tv_setup_dropOut,
            R.id.imageview_setup2
    }) public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.imageview_setup2:
                 intent = new Intent(getContext(), BabySetupAcivity.class);
                intent.putExtra("Avatar", 1);
                startActivityForResult(intent, 11);
                break;
            case R.id.item_verified:
                break;
            case R.id.item_bill:
                break;
            case R.id.item_contacts:
                intent = new Intent(getContext(), DeviceContactsActivity.class);
                startActivity(intent);
                break;
            case R.id.item_family:
                intent = new Intent(getContext(), UserConcernsListActivity.class);
                startActivity(intent);
                break;
            case R.id.item_disable_period:
                break;
            case R.id.item_wifi:
                intent = new Intent(getContext(), DeviceWifiActivity.class);
                startActivity(intent);
                break;
            case R.id.item_positioning:
                break;
            case R.id.item_safe_area:
                break;
            case R.id.item_unbundled_device:
                intent = new Intent(getContext(), AddDeviceActivity.class);
                startActivity(intent);
                break;
            case R.id.item_find_device:
                break;
            case R.id.item_shut_down_device:
                break;
            case R.id.item_share_device:
                break;
            case R.id.item_APP_manage:
                break;
            case R.id.tv_setup_dropOut:
                SharedPreferences.Editor editor = userInfo.edit();
                SharedPreferences userInfoToken;
                userInfoToken = MyApplication.getContext().getSharedPreferences("userInfo", 0);
                SharedPreferences.Editor editorToken = userInfoToken.edit();
                editorToken.putString("accessToken", null);
                editor.putBoolean("login", false);
                editor.commit();
                intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
                break;
        }
    }


}