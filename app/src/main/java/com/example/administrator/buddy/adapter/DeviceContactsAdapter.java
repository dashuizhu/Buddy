package com.example.administrator.buddy.adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import cn.bingoogolapple.swipeitemlayout.BGASwipeItemLayout;
import com.example.administrator.buddy.R;
import com.example.administrator.buddy.bean.DeviceContactsBean;
import com.example.administrator.buddy.bean.DeviceRelationBean;
import com.facebook.drawee.view.SimpleDraweeView;
import java.util.ArrayList;
import java.util.List;


/**
 * 设备的联系人适配器
 *
 * @author zhuj
 * @date 2017/6/12 下午5:13.
 */
public class DeviceContactsAdapter extends BGARecyclerViewAdapter<DeviceContactsBean> {

  /**
   * 当前处于打开状态的item
   */
  private List<BGASwipeItemLayout> mOpenedSil = new ArrayList<>();

  public DeviceContactsAdapter(RecyclerView recyclerView) {
    super(recyclerView, R.layout.recycler_device_contacts_item);
  }

  @Override protected void setItemChildListener(BGAViewHolderHelper helper, int viewType) {
    BGASwipeItemLayout swipeItemLayout = helper.getView(R.id.layout_swipe);
    swipeItemLayout.setDelegate(new BGASwipeItemLayout.BGASwipeItemLayoutDelegate() {
      @Override
      public void onBGASwipeItemLayoutOpened(BGASwipeItemLayout swipeItemLayout) {
        closeOpenedSwipeItemLayoutWithAnim();
        mOpenedSil.add(swipeItemLayout);
      }

      @Override
      public void onBGASwipeItemLayoutClosed(BGASwipeItemLayout swipeItemLayout) {
        mOpenedSil.remove(swipeItemLayout);
      }

      @Override
      public void onBGASwipeItemLayoutStartOpen(BGASwipeItemLayout swipeItemLayout) {
        closeOpenedSwipeItemLayoutWithAnim();
      }
    });
    helper.setItemChildClickListener(R.id.tv_delete);
    helper.setItemChildCheckedChangeListener(R.id.cb_family_sos);
  }

  @Override
  protected void fillData(BGAViewHolderHelper bgaViewHolderHelper, int i, DeviceContactsBean bean) {
    bgaViewHolderHelper.setText(R.id.tv_family_name, bean.getName())
            .setText(R.id.tv_family_phone, bean.getMobile())
            .setChecked(R.id.cb_family_sos, bean.isSos());
    SimpleDraweeView draweeView = bgaViewHolderHelper.getView(R.id.draweeView);
    DeviceRelationBean relationBean =
        new DeviceRelationBean(bean.getRelation(), bean.getName());
    if (bean.getRelation() == 0 && !TextUtils.isEmpty(bean.getAvatar())) {
      draweeView.setImageURI(Uri.parse(bean.getAvatar()));
    }else{
      draweeView.setImageResource(relationBean.getRelationResSmall());
    }

  }

  public void closeOpenedSwipeItemLayoutWithAnim() {
    for (BGASwipeItemLayout sil : mOpenedSil) {
      sil.closeWithAnim();
    }
    mOpenedSil.clear();
  }
}
