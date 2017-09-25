package com.example.administrator.buddy.ui.device;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.administrator.buddy.BaseFragment;
import com.example.administrator.buddy.R;
import com.example.administrator.buddy.view.BuddyCircleGroupView;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * @author zhuj 2017/9/25 下午6:32.
 */
public class GuardFragment extends BaseFragment {

  @BindView(R.id.iv_phone) ImageView mIvPhone;
  @BindView(R.id.iv_chat) ImageView mIvChat;
  @BindView(R.id.iv_map) ImageView mIvMap;
  @BindView(R.id.iv_monitor) ImageView mIvMonitor;
  @BindView(R.id.iv_step) ImageView mIvStep;
  @BindView(R.id.iv_story) ImageView mIvStory;
  @BindView(R.id.iv_message) ImageView mIvMessage;
  @BindView(R.id.buddyCircleView) BuddyCircleGroupView mBuddyCircleView;
  @BindView(R.id.draweeView) SimpleDraweeView mDraweeView;

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
          @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.activity_guard, container, false);
    ButterKnife.bind(this, view);
    String head = getContext().getSharedPreferences("userInfo", 0).getString("mapurl", "");
    if (!TextUtils.isEmpty(head)) {
      mDraweeView.setImageURI(Uri.parse(head));
    }
    return view;
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
  }

}
