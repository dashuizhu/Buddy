package com.example.administrator.buddy.adapter;

import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import cn.bingoogolapple.swipeitemlayout.BGASwipeItemLayout;
import com.example.administrator.buddy.R;
import com.example.administrator.buddy.bean.UserConcernsBean;
import java.util.ArrayList;
import java.util.List;

/**
 * 设备的家庭成员适配器
 *
 *
 * @date 2017/6/12 下午5:13.
 */
public class UserContactsAdapter extends BGARecyclerViewAdapter<UserConcernsBean> {


  private List<BGASwipeItemLayout> mOpenedSil = new ArrayList<>();

  public UserContactsAdapter(RecyclerView recyclerView) {
    super(recyclerView, R.layout.recycler_user_contacts_item);
  }

  @Override protected void setItemChildListener(BGAViewHolderHelper helper, int viewType) {
    BGASwipeItemLayout swipeItemLayout = helper.getView(R.id.layout_swipe);
    swipeItemLayout.setDelegate(new BGASwipeItemLayout.BGASwipeItemLayoutDelegate() {
      @Override
      //打开滑动物品布局
      public void onBGASwipeItemLayoutOpened(BGASwipeItemLayout swipeItemLayout) {
        //  关闭打开滑动项目布局与动画
        closeOpenedSwipeItemLayoutWithAnim();
        mOpenedSil.add(swipeItemLayout);
      }
      //关闭
      @Override
      public void onBGASwipeItemLayoutClosed(BGASwipeItemLayout swipeItemLayout) {
        mOpenedSil.remove(swipeItemLayout);
      }
      //开始打开
      @Override
      public void onBGASwipeItemLayoutStartOpen(BGASwipeItemLayout swipeItemLayout) {
        closeOpenedSwipeItemLayoutWithAnim();
      }
    });
    helper.setItemChildClickListener(R.id.tv_delete);
    //helper.setItemChildCheckedChangeListener(R.id.cb_family_sos);
  }

  @Override
  protected void fillData(BGAViewHolderHelper bgaViewHolderHelper, int i, UserConcernsBean bean) {
    bgaViewHolderHelper.setText(R.id.tv_family_name, bean.getName());
    ImageView draweeView = bgaViewHolderHelper.getView(R.id.image_isAdmin);
    if (bean.isAdmin()){
      draweeView.setImageResource(R.mipmap.manager_img);
    }


  }

  public void closeOpenedSwipeItemLayoutWithAnim() {
    for (BGASwipeItemLayout sil : mOpenedSil) {
      sil.closeWithAnim();
    }
    mOpenedSil.clear();
  }
}
