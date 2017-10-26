package com.example.administrator.buddy.ui.device;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.androidcommon.adapter.BGAOnItemChildClickListener;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;
import com.example.administrator.buddy.BaseActivity;
import com.example.administrator.buddy.R;
import com.example.administrator.buddy.adapter.DeviceWiFiAdapter;
import com.example.administrator.buddy.bean.DeviceWiFiBean;
import com.example.administrator.buddy.bean.DeviceWiFiResult;
import com.example.administrator.buddy.injector.components.DaggerPresenterComponent;
import com.example.administrator.buddy.injector.components.PresenterComponent;
import com.example.administrator.buddy.injector.modules.ModelModule;
import com.example.administrator.buddy.ui.device.presenter.DeviceWiFiPresenter;
import com.example.administrator.buddy.view.HeaderView;
import com.example.administrator.buddy.view.RecyclerViewDivider;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhuj on 2017/9/29 21:33.
 */
public class DeviceWifiActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate{
    private final int ACTIVITY_UPDATE = 11;
    private final int ACTIVITY_ADD = 12;
    @BindView(R.id.refreshLayout) BGARefreshLayout mRefreshLayout;
    private DeviceWiFiAdapter mAdapter;
    private List<DeviceWiFiBean> mWiFiBeen;
    private ItemTouchHelper mItemTouchHelper;
    private DeviceWiFiPresenter mPresenter;
    @BindView(R.id.headerView) HeaderView mHeaderView;
    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_wifi);
        ButterKnife.bind(this);
        injectorPresenter();
        inview();
    }

    private void inview() {
        mAdapter = new DeviceWiFiAdapter(mRecyclerView);
        mItemTouchHelper = new ItemTouchHelper(new ItemTouchHelperCallback());
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
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
        //加载更多内容
        //RefreshViewHolder.setLoadingMoreText(getString(R.string.text_loadmore));
        //RefreshViewHolder.setLoadMoreBackgroundDrawableRes(R.drawable.progress);
        mRefreshLayout.setRefreshViewHolder(RefreshViewHolder);
        //开始刷新
        mRefreshLayout.beginRefreshing();

        mAdapter.setOnItemChildClickListener(new BGAOnItemChildClickListener() {
            @Override public void onItemChildClick(ViewGroup viewGroup, View view, int i) {
                //if(!checkPermission())return;
               int position = i;
                List<DeviceWiFiBean> list = new ArrayList<DeviceWiFiBean>();
                switch (view.getId()) {
                    case R.id.tv_delete:
                        list.add(mWiFiBeen.get(i));
                        mPresenter.deleteWifI(list);
                        break;
                }
            }
        });
        //mAdapter.setOnRVItemClickListener(new BGAOnRVItemClickListener() {
        //    @Override public void onRVItemClick(ViewGroup viewGroup, View view, int i) {
        //        if(!checkPermission())return;
        //        Intent intent = new Intent(DeviceContactsActivity.this, DeviceContactsEditActivity.class);
        //        intent.putExtra(AppString.KEY_DEVICE_CONTACTS, mWiFiBeen.get(i));
        //        startActivityForResult(intent, ACTIVITY_UPDATE);
        //    }
        //});
    }

    @Override public void success(Object o) {
        super.success(o);
        Log.e("wifififi",o.toString());
        List<DeviceWiFiBean> list  = ((DeviceWiFiResult) o).getFamilyBeanList();
        mWiFiBeen = list;
        mAdapter.setData(mWiFiBeen);
        mRefreshLayout.endRefreshing();
    }

    private void injectorPresenter() {

        PresenterComponent authenticationComponent = DaggerPresenterComponent.builder()
                .modelModule(new ModelModule(this))
                .build();
        mPresenter = authenticationComponent.getDeviceWiFiPresenter();
    }
    //private ItemTouchHelper.Callback ItemTouchHelperCallback() {
    //    return null;
    //}

    @OnClick({ R.id.headerView, R.id.recyclerView }) public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.headerView:
                break;
            case R.id.recyclerView:
                break;
        }
    }

    @OnClick(R.id.layout_header_back) public void onBack() {
        finish();
    }

    @OnClick(R.id.layout_header_right) public void onAdd() {
        Intent intent = new Intent(this, DeviceWifiAddActivity.class);
        startActivity(intent);
    }
    //在BGARefresh上刷新
    @Override public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        mPresenter.getWiFiList();
    }

    @Override public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        return false;
    }

    private class ItemTouchHelperCallback extends ItemTouchHelper.Callback {
        public static final float ALPHA_FULL = 1.0f;

        @Override public boolean isLongPressDragEnabled() {
            //            return true;
            return true;
        }

        @Override public boolean isItemViewSwipeEnabled() {
            return false;
        }

        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            int dragFlags = ItemTouchHelper.UP
                    | ItemTouchHelper.DOWN
                    | ItemTouchHelper.LEFT
                    | ItemTouchHelper.RIGHT;
            int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;

            // 当加了 HeaderAndFooterAdapter 时需要加上下面的判断
            if (mAdapter.isHeaderOrFooter(viewHolder)) {
                dragFlags = swipeFlags = ItemTouchHelper.ACTION_STATE_IDLE;
            }

            return makeMovementFlags(dragFlags, swipeFlags);
        }

        @Override public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder source,
                RecyclerView.ViewHolder target) {
            if (source.getItemViewType() != target.getItemViewType()) {
                return false;
            }

            mAdapter.moveItem(source, target);

            return true;
        }

        @Override public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            //mAdapter.removeItem(viewHolder);
        }

        @Override public void onChildDraw(Canvas c, RecyclerView recyclerView,
                RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState,
                boolean isCurrentlyActive) {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                View itemView = viewHolder.itemView;
                float alpha = ALPHA_FULL - Math.abs(dX) / (float) itemView.getWidth();
                ViewCompat.setAlpha(viewHolder.itemView, alpha);
            }
        }

        @Override
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
            if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                viewHolder.itemView.setSelected(true);
            }
            super.onSelectedChanged(viewHolder, actionState);
        }

        @Override
        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            super.clearView(recyclerView, viewHolder);
            //ViewCompat.setAlpha(viewHolder.itemView, ALPHA_FULL);
            //viewHolder.itemView.setSelected(false);
        }
    }
}
