package com.example.administrator.buddy.adapter;

import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import com.example.administrator.buddy.R;
import com.example.administrator.buddy.bean.DeviceRelationBean;

/**
 * 睡前故事适配器
 *
 * @author zhuj 2017/6/12 下午5:13.
 */
public class DeviceRelationAdapter extends BGARecyclerViewAdapter<DeviceRelationBean> {

  private int mCheckPosition;

  public DeviceRelationAdapter(RecyclerView recyclerView) {
    super(recyclerView, R.layout.recycler_relation_item);
  }

  @Override protected void setItemChildListener(BGAViewHolderHelper helper, int viewType) {
    helper.setItemChildCheckedChangeListener(R.id.cb_relation_checked);
    super.setItemChildListener(helper, viewType);
  }

  @Override
  protected void fillData(BGAViewHolderHelper bgaViewHolderHelper, int i, DeviceRelationBean bean) {
    bgaViewHolderHelper.setChecked(R.id.cb_relation_checked, bean.isChecked());
    TextView tv = bgaViewHolderHelper.getTextView(R.id.tv_relation);
    tv.setText(bean.getName());
    tv.setSelected(bean.isChecked());
    Drawable drawable = ResourcesCompat.getDrawable(
            bgaViewHolderHelper.getConvertView().getContext().getResources(),
            bean.getRelationResBig(), null);

    /// 这一步必须要做,否则不会显示.
    if(drawable != null) {
      drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
      tv.setCompoundDrawables(null, drawable, null, null);
    }
  }

  public int getCheckItem() {
    return mCheckPosition;
  }

  public void setCheckItem(int checkItem) {
    if (checkItem < 0 || mCheckPosition >= getData().size()) {
      return;
    }
    getData().get(mCheckPosition).setChecked(false);
    notifyItemChanged(mCheckPosition);
    mCheckPosition = checkItem;
    getData().get(mCheckPosition).setChecked(true);
    notifyItemChanged(mCheckPosition);
  }
}
