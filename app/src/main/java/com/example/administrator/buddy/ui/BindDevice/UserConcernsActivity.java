package com.example.administrator.buddy.ui.BindDevice;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bingoogolapple.androidcommon.adapter.BGAOnItemChildClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;
import com.example.administrator.buddy.AppString;
import com.example.administrator.buddy.BaseActivity;
import com.example.administrator.buddy.Helper.DeviceListSQLMdoel;
import com.example.administrator.buddy.LoginActivity;
import com.example.administrator.buddy.R;
import com.example.administrator.buddy.adapter.DeviceWiFiAdapter;
import com.example.administrator.buddy.adapter.UserContactsAdapter;
import com.example.administrator.buddy.bean.DeviceWiFiBean;
import com.example.administrator.buddy.bean.UserConcernsBean;
import com.example.administrator.buddy.ui.device.DeviceWifiActivity;
import com.example.administrator.buddy.ui.device.DeviceWifiEditActivity;
import com.example.administrator.buddy.utils.SharedPreUser;
import com.example.administrator.buddy.view.HeaderView;
import com.example.administrator.buddy.view.RecyclerViewDivider;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/21.
 */

public class UserConcernsActivity extends BaseActivity implements
        BGARefreshLayout.BGARefreshLayoutDelegate{
  @BindView(R.id.headerView) HeaderView mHeaderView;
  @BindView(R.id.recyclerView) RecyclerView mRecyclerView;
  @BindView(R.id.refreshLayout) BGARefreshLayout mRefreshLayout;
  @BindView(R.id.btn_add) Button mBtnAdd;
  private UserContactsAdapter mAdapter;
  private DeviceListSQLMdoel mSQL;
  private List<UserConcernsBean> mList;


  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_user_concerns);
    ButterKnife.bind(this);
    inView();
  }

  private void inView(){
    mSQL=new DeviceListSQLMdoel(this);
    mList=mSQL.inquireDeviceIdList();
    mAdapter = new UserContactsAdapter(mRecyclerView);
    mAdapter.setData(mList);
    LinearLayoutManager layoutManager =
            new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
    mRecyclerView.setLayoutManager(layoutManager);
    mRecyclerView.setAdapter(mAdapter);
    mRecyclerView.addItemDecoration(
            new RecyclerViewDivider(this, LinearLayoutManager.VERTICAL));
    //设置手势操作
    mRefreshLayout.setDelegate(this);
    //设置刷新view
    BGARefreshViewHolder RefreshViewHolder = new BGANormalRefreshViewHolder(this, false);
    mRefreshLayout.setRefreshViewHolder(RefreshViewHolder);
    //开始刷新
    mRefreshLayout.beginRefreshing();


    mAdapter.setOnItemChildClickListener(new BGAOnItemChildClickListener() {
      @Override public void onItemChildClick(ViewGroup viewGroup, View view, int i) {
        SharedPreUser.getInstance().put(UserConcernsActivity.this,
                SharedPreUser.KEY_DEVICE_ID,mList.get(i).getDeviceId());
      }
    });
    mAdapter.setOnRVItemClickListener(new BGAOnRVItemClickListener() {
      @Override public void onRVItemClick(ViewGroup viewGroup, View view, int i) {
      }
    });



  }

  @Override public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {

  }

  @Override public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
    return false;
  }
}
