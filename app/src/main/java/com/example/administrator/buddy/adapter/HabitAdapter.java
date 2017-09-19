package com.example.administrator.buddy.adapter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.administrator.buddy.R;
import com.example.administrator.buddy.bean.HabitBean;
import java.util.List;

/**
 * Created by zhuj on 2017/8/21 21:28.
 */
public class HabitAdapter extends BaseAdapter  {

    private List<HabitBean>  mList;
    private Context mContext;

    public HabitAdapter(Context context, List<HabitBean> data) {
        super();
        mList =data;
        mContext =context;
    }
    public List<HabitBean> getList() {
        return mList;
    }

    public void setList(List<HabitBean> list) {
        mList = list;
    }

    @Override public int getCount() {
        return mList.size();
    }

    @Override public HabitBean  getItem(int position) {
        return mList.get(position);
    }

    @Override public long getItemId(int position) {
        return 0;
    }

    @Override public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater =LayoutInflater.from(mContext);
        View view =inflater.inflate(R.layout.listview_habit_item ,null);
        TextView tvTime =(TextView)view.findViewById(R.id.tv_tmie);
        TextView tvText =(TextView)view.findViewById(R.id.textv_text);
        ImageView imstate = (ImageView)view.findViewById(R.id.tv_state);
        Button   btnplayState= (Button)view.findViewById(R.id.btn_playState) ;
        tvText.setText(getItem(position).getTitle());
        tvTime.setText(getItem(position).getPlayTime());
        GradientDrawable drawable = (GradientDrawable) btnplayState.getBackground();
        if (getItem(position).getState()==0){
            imstate.setBackgroundResource(R.mipmap.home_point_gray_img);
            btnplayState.setTextColor(ContextCompat.getColor(mContext, R.color.colorwhh));
            drawable.setColor(ContextCompat.getColor(mContext, R.color.colorwhite));
        }else {
            imstate.setBackgroundResource(R.mipmap.home_point_blue_img);
            if (getItem(position).getPlayState()==0){
                btnplayState.setText("未开始");
                btnplayState.setTextColor(ContextCompat.getColor(mContext, R.color.colorhabitBule));
                drawable.setColor(ContextCompat.getColor(mContext, R.color.colorhabitbule));
            }if (getItem(position).getPlayState()==1){
                btnplayState.setText("进行中");
                btnplayState.setTextColor(mContext.getResources().getColor(R.color.colorhabitAccent));
                drawable.setColor(ContextCompat.getColor(mContext, R.color.colorhabitaccent));
            }if (getItem(position).getPlayState()==2){
                btnplayState.setText("已完成");
                btnplayState.setTextColor(ContextCompat.getColor(mContext, R.color.colorhabitGreen));
                drawable.setColor(ContextCompat.getColor(mContext, R.color.colorhabitgreen));
            }
        }

        return view;
    }



}
