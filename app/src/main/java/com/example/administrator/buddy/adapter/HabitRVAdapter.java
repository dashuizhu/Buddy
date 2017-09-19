package com.example.administrator.buddy.adapter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.administrator.buddy.R;
import com.example.administrator.buddy.bean.HabitBean;
import java.util.List;

/**
 * Created by zhuj on 2017/9/12 17:11.
 */
public class HabitRVAdapter extends RecyclerView.Adapter{
    private List<HabitBean>  mList;
    private Context mContext;

    public void setList(List<HabitBean> list) {
        this.mList = list;
    }
    public HabitRVAdapter(Context context, List<HabitBean> data) {
        super();
        mList =data;
        mContext =context;
    }

    @Override public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //rViewholder holder= new rViewholder(LayoutInflater.from(mContext).inflate(R.layout.merge_baby ,null));
        return  new rViewholder(LayoutInflater.from(mContext).inflate(R.layout.listview_habit_item ,null));
    }

    @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        rViewholder viewholder =(rViewholder) holder;
        viewholder.tvTime.setText(mList.get(position).getPlayTime());
        viewholder.tvText.setText(mList.get(position).getTitle());
        GradientDrawable drawable = (GradientDrawable) viewholder.btnplayState.getBackground();
        if (mList.get(position).getState()==0){
            viewholder.imstate.setBackgroundResource(R.mipmap.home_point_gray_img);
            viewholder.btnplayState.setTextColor(ContextCompat.getColor(mContext, R.color.colorwhh));
            drawable.setColor(ContextCompat.getColor(mContext, R.color.colorwhite));
        }else {
            viewholder.imstate.setBackgroundResource(R.mipmap.home_point_blue_img);
            if (mList.get(position).getPlayState()==0){
                viewholder.btnplayState.setText("未开始");
                viewholder.btnplayState.setTextColor(ContextCompat.getColor(mContext, R.color.colorhabitBule));
                drawable.setColor(ContextCompat.getColor(mContext, R.color.colorhabitbule));
            }if (mList.get(position).getPlayState()==1){
                viewholder.btnplayState.setText("进行中");
                viewholder.btnplayState.setTextColor(mContext.getResources().getColor(R.color.colorhabitAccent));
                drawable.setColor(ContextCompat.getColor(mContext, R.color.colorhabitaccent));
            }if (mList.get(position).getPlayState()==2){
                viewholder.btnplayState.setText("已完成");
                viewholder.btnplayState.setTextColor(ContextCompat.getColor(mContext, R.color.colorhabitGreen));
                drawable.setColor(ContextCompat.getColor(mContext, R.color.colorhabitgreen));
            }
        }
    }

    @Override public int getItemCount() {
        return mList.size();
    }
    private class rViewholder  extends RecyclerView.ViewHolder
    {
        TextView tvTime;
        TextView tvText;
        ImageView imstate;
        Button btnplayState;
        public rViewholder(View view)
        {
            super(view);
             tvTime =(TextView)view.findViewById(R.id.tv_tmie);
             tvText =(TextView)view.findViewById(R.id.textv_text);
             imstate = (ImageView)view.findViewById(R.id.tv_state);
             btnplayState= (Button)view.findViewById(R.id.btn_playState) ;
        }
    }
}
