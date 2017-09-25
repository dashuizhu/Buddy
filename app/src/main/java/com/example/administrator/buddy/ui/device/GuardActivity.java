package com.example.administrator.buddy.ui.device;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.administrator.buddy.BaseActivity;
import com.example.administrator.buddy.R;
import com.example.administrator.buddy.view.BuddyCircleGroupView;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * 守护页面，对应具体手表功能
 *
 * @author zhuj 2017/6/29 上午11:48
 */
public class GuardActivity extends BaseActivity {

  @BindView(R.id.draweeView) SimpleDraweeView mDraweeView;
  @BindView(R.id.iv_phone) ImageView mIvPhone;
  @BindView(R.id.iv_chat) ImageView mIvChat;
  @BindView(R.id.iv_map) ImageView mIvMap;
  @BindView(R.id.iv_monitor) ImageView mIvMonitor;
  @BindView(R.id.iv_step) ImageView mIvStep;
  @BindView(R.id.iv_story) ImageView mIvStory;
  @BindView(R.id.iv_message) ImageView mIvMessage;
  @BindView(R.id.buddyCircleView) BuddyCircleGroupView mBuddyCircleView;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_guard);
    ButterKnife.bind(this);

    initViews();
  }

  private void initViews() {
    String head = getSharedPreferences("userInfo", 0).getString("mapurl", "");
    if (!TextUtils.isEmpty(head)) {
      mDraweeView.setImageURI(Uri.parse(head));
    }
  }



  @OnClick({
          R.id.iv_phone, R.id.iv_chat, R.id.iv_map, R.id.iv_monitor, R.id.iv_step, R.id.iv_story,
          R.id.iv_message
  }) public void onViewClicked(View view) {
    switch (view.getId()) {
      case R.id.iv_phone:
        break;
      case R.id.iv_chat:
        break;
      case R.id.iv_map:
        break;
      case R.id.iv_monitor:
        break;
      case R.id.iv_step:
        break;
      case R.id.iv_story:
        break;
      case R.id.iv_message:
        break;
    }
  }

  @Override protected void onDestroy() {
    super.onDestroy();
  }

}
