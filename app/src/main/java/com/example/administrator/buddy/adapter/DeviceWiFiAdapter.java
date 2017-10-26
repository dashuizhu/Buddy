package com.example.administrator.buddy.adapter;

import android.support.v7.widget.RecyclerView;
import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import cn.bingoogolapple.swipeitemlayout.BGASwipeItemLayout;
import com.example.administrator.buddy.R;
import com.example.administrator.buddy.bean.DeviceWiFiBean;
import com.facebook.drawee.view.SimpleDraweeView;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhuj on 2017/10/23 21:47.
 */
public class DeviceWiFiAdapter extends BGARecyclerViewAdapter<DeviceWiFiBean> {
    private List<BGASwipeItemLayout> mOpenedSil = new ArrayList<>();

    public DeviceWiFiAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.recycler_device_wifi_item);
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
        helper.setItemChildCheckedChangeListener(R.id.cb_family_sos);
    }



    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, DeviceWiFiBean model) {
        helper.setText(R.id.edit_wifi_name, model.getSsid());
        SimpleDraweeView view = helper.getView(R.id.draweeView);
    }
    private void closeOpenedSwipeItemLayoutWithAnim() {
        for (BGASwipeItemLayout sil : mOpenedSil) {
            sil.closeWithAnim();
        }
        mOpenedSil.clear();
    }

}
