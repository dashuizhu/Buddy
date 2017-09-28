package com.example.administrator.buddy.ui.device;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.androidcommon.adapter.BGAOnItemChildCheckedChangeListener;
import com.example.administrator.buddy.AppString;
import com.example.administrator.buddy.BaseActivity;
import com.example.administrator.buddy.R;
import com.example.administrator.buddy.adapter.DeviceRelationAdapter;
import com.example.administrator.buddy.bean.AppType;
import com.example.administrator.buddy.bean.DeviceRelationBean;
import com.example.administrator.buddy.view.SpaceItemDecoration;
import java.util.ArrayList;
import java.util.List;

/**
 * 设备关系界面设置
 */
public class DeviceRelationSelectActivity extends BaseActivity {

  private List<DeviceRelationBean> mBeanList;
  @BindView(R.id.recyclerView) RecyclerView mRecyclerView;
  DeviceRelationAdapter mAdapter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_device_relation_select);
    ButterKnife.bind(this);
    initViews();
  }

  private void initViews() {
    mAdapter = new DeviceRelationAdapter(mRecyclerView);
    GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
    //间距 自定义
    mRecyclerView.addItemDecoration(new SpaceItemDecoration(16, 2, false));
    mRecyclerView.setLayoutManager(layoutManager);
    mRecyclerView.setAdapter(mAdapter);
    //取消动画
    ((SimpleItemAnimator)mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

    int relation = getIntent().getIntExtra(AppString.KEY_RELATION, AppType.RELATION_FATHER);
    int selectPosition ;
    if (relation == 0) {
      selectPosition = 6;
    } else {
      selectPosition = relation-1;
    }

    mBeanList = new ArrayList<>();
    mBeanList.add(new DeviceRelationBean(AppType.RELATION_FATHER, getString(R.string.relation_father)));
    mBeanList.add(new DeviceRelationBean(AppType.RELATION_MOTHER, getString(R.string.relation_mother)));
    mBeanList.add(new DeviceRelationBean(AppType.RELATION_GRAND_FATHER, getString(R.string.relation_grand_father)));
    mBeanList.add(new DeviceRelationBean(AppType.RELATION_GRAND_MOTHER, getString(R.string.relation_grand_mother)));
    mBeanList.add(new DeviceRelationBean(AppType.RELATION_GRAND_PA, getString(R.string.relation_grand_pa)));
    mBeanList.add(new DeviceRelationBean(AppType.RELATION_GRAND_MA, getString(R.string.relation_grand_ma)));
    mBeanList.add(new DeviceRelationBean(AppType.RELATION_OTHER, getString(R.string.relation_other)));

    mAdapter.setData(mBeanList);
    mAdapter.setCheckItem(selectPosition);
    mAdapter.setOnItemChildCheckedChangeListener(new BGAOnItemChildCheckedChangeListener() {
      @Override
      public void onItemChildCheckedChanged(ViewGroup viewGroup, CompoundButton compoundButton,
              int i, boolean b) {
        mAdapter.setCheckItem(i);
      }
    });
  }

  @OnClick(R.id.layout_header_back) public void onBack() {
    finish();
  }

  @OnClick(R.id.layout_header_right) public void onSave() {
    DeviceRelationBean bean = mBeanList.get(mAdapter.getCheckItem());
    getIntent().putExtra(AppString.KEY_RELATION_BEAN, bean);
    setResult(RESULT_OK, getIntent());
    finish();
  }
}
