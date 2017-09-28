package com.example.administrator.buddy.ui.device;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.androidcommon.adapter.BGAOnItemChildCheckedChangeListener;
import cn.bingoogolapple.androidcommon.adapter.BGAOnItemChildClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;
import com.example.administrator.buddy.AppString;
import com.example.administrator.buddy.BaseActivity;
import com.example.administrator.buddy.R;
import com.example.administrator.buddy.adapter.DeviceContactsAdapter;
import com.example.administrator.buddy.bean.CheckedResult;
import com.example.administrator.buddy.bean.DeviceContactsBean;
import com.example.administrator.buddy.bean.DeviceContactsResult;
import com.example.administrator.buddy.bean.NetworkResult;
import com.example.administrator.buddy.injector.components.DaggerPresenterComponent;
import com.example.administrator.buddy.injector.components.PresenterComponent;
import com.example.administrator.buddy.injector.modules.ModelModule;
import com.example.administrator.buddy.ui.device.presenter.DeviceContactsPresenter;
import com.example.administrator.buddy.view.HeaderView;
import com.example.administrator.buddy.view.RecyclerViewDivider;
import java.util.ArrayList;
import java.util.List;

/**
 * 手表sos联系人
 *
 * @author zhuj
 * @date 2017/6/15 下午4:24
 */
public class DeviceContactsActivity extends BaseActivity implements BGARefreshLayout.BGARefreshLayoutDelegate{

  private final int ACTIVITY_UPDATE = 11;
  private final int ACTIVITY_ADD = 12;

  @BindView(R.id.headerView) HeaderView mHeaderView;
  @BindView(R.id.refreshLayout) BGARefreshLayout mRefreshLayout;
  @BindView(R.id.recyclerView)      RecyclerView      mRecyclerView;

  private DeviceContactsAdapter mAdapter;
  private List<DeviceContactsBean> mBeanList;
  private DeviceContactsPresenter mPresenter;
  private int position;
  private ItemTouchHelper mItemTouchHelper;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_device_contacts);
    ButterKnife.bind(this);
    injectorPresenter();
    initViews();
  }

  private void initViews() {
    mAdapter = new DeviceContactsAdapter(mRecyclerView);

    // 初始化拖拽排序
    mItemTouchHelper = new ItemTouchHelper(new ItemTouchHelperCallback());
    mItemTouchHelper.attachToRecyclerView(mRecyclerView);

    LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
    mRecyclerView.setLayoutManager(layoutManager);
    mRecyclerView.setAdapter(mAdapter);
    mRecyclerView.addItemDecoration(new RecyclerViewDivider(this, LinearLayoutManager.VERTICAL));
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
        if(!checkPermission())return;
        position = i;
        List<DeviceContactsBean> list = new ArrayList<DeviceContactsBean>();
        switch (view.getId()) {
          case R.id.tv_delete:
            list.add(mBeanList.get(i));
            mPresenter.deleteContacts(list);
            break;
        }
      }
    });
    mAdapter.setOnRVItemClickListener(new BGAOnRVItemClickListener() {
      @Override public void onRVItemClick(ViewGroup viewGroup, View view, int i) {
        if(!checkPermission())return;
        Intent intent = new Intent(DeviceContactsActivity.this, DeviceContactsEditActivity.class);
          intent.putExtra(AppString.KEY_DEVICE_CONTACTS, mBeanList.get(i));
        startActivityForResult(intent, ACTIVITY_UPDATE);
      }
    });
    mAdapter.setOnItemChildCheckedChangeListener(new BGAOnItemChildCheckedChangeListener() {
      @Override
      public void onItemChildCheckedChanged(ViewGroup viewGroup, CompoundButton compoundButton,
              int i, boolean b) {
        if(!checkPermission())return;
        DeviceContactsBean bean = mBeanList.get(i);
        bean.setSeq(mBeanList.size() - i);
        bean.setSos(b);
        List<DeviceContactsBean> list = new ArrayList<DeviceContactsBean>();
        list.add(bean);
        mPresenter.setSos(list);
      }
    });
  }

  private boolean checkPermission(){
    //DeviceHolderBean bean = mAlarmPresenter.getSelectedDeviceHolder();
    //if (bean == null || !bean.isAdmin()) {
    //  showToast(R.string.toast_permission_admin);
    //  return false;
    //}
    return true;
  }

  @OnClick(R.id.layout_header_back) public void onBack() {
    finish();
  }

  @OnClick(R.id.btn_add) public void addFamily() {
    if(!checkPermission())return;
    if (mBeanList == null || mBeanList.size()==0) return;
    for (int i=0; i<mBeanList.size(); i++) {
      mBeanList.get(i).setSeq(mBeanList.size() - i);
    }
    mPresenter.save(mBeanList);
  }

  @OnClick(R.id.layout_header_right) public void onAdd() {
    if(!checkPermission())return;
    Intent intent = new Intent(this, DeviceContactsEditActivity.class);
    startActivityForResult(intent, ACTIVITY_ADD);
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    //如果在编辑页面，编辑或添加成功了，回到list页面要刷新
    if (resultCode == RESULT_OK) {
      mRefreshLayout.beginRefreshing();
    }
    super.onActivityResult(requestCode, resultCode, data);
  }

  @Override public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout bgaRefreshLayout) {
    mPresenter.getContactsList();
  }

  @Override public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout bgaRefreshLayout) {
    //没有加载更多
    return false;
  }

  @Override public void shutDialg() {
    if (mRefreshLayout != null) {
      mRefreshLayout.endRefreshing();
    }
    super.shutDialg();
  }

  @Override public void success(Object successObj) {
    if (successObj instanceof DeviceContactsResult) {
      List<DeviceContactsBean> list  = ((DeviceContactsResult) successObj).getFamilyBeanList();
        mBeanList = list;
        mAdapter.setData(mBeanList);
    } else if (successObj instanceof NetworkResult) {
      switch (((NetworkResult) successObj).getTag()) {
        case NetworkResult.TAG_DELETE:
          mBeanList.remove(position);
          mAdapter.notifyItemRemoved(position);
          break;
      }
      showToast(((NetworkResult) successObj).getMessage());
    } else if (successObj instanceof CheckedResult) {
      List<DeviceContactsBean> list =
              (List<DeviceContactsBean>) ((CheckedResult) successObj).getObject();
      if (list == null) return;
      //修改状态，网络失败了， 要将checkBox选中值反回去
      for (DeviceContactsBean bean : list) {
        for (DeviceContactsBean contactsBean : mBeanList) {
          if (contactsBean.getId().equals(bean.getId())) {
            contactsBean.setSos(!bean.isSos());
          }
        }
      }
      mAdapter.notifyDataSetChanged();
    }
    super.success(successObj);
  }



  private void injectorPresenter() {
    PresenterComponent presnterComponent = DaggerPresenterComponent.builder().modelModule(new ModelModule(this)).build();
    mPresenter = presnterComponent.getDeviceContactsPresenter();
  }

  /**
   * 该类参考：https://github.com/iPaulPro/Android-ItemTouchHelper-Demo
   */
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

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
            float dX, float dY, int actionState, boolean isCurrentlyActive) {
      super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
      if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
        View itemView = viewHolder.itemView;
        float alpha = ALPHA_FULL - Math.abs(dX) / (float) itemView.getWidth();
        ViewCompat.setAlpha(viewHolder.itemView, alpha);
      }
    }

    @Override public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
      if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
        viewHolder.itemView.setSelected(true);
      }
      super.onSelectedChanged(viewHolder, actionState);
    }

    @Override public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
      super.clearView(recyclerView, viewHolder);
      //ViewCompat.setAlpha(viewHolder.itemView, ALPHA_FULL);
      //viewHolder.itemView.setSelected(false);
    }
  }
}
