package com.example.administrator.buddy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.administrator.buddy.R;
import java.util.List;

/**
 * Created by zhuj on 2017/9/12 20:48.
 */
public class GridFormatAdapter extends RecyclerView.Adapter {
    private List mList;
    private int[] mapid;
    private Context mContext;

    public GridFormatAdapter(Context context, int[]  apid) {
        super();
        this.mapid = apid;
        this.mContext = context;
    }

    @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new viewholder(
                LayoutInflater.from(mContext).inflate(R.layout.recyclerview_habit_item, null));
    }

    @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        viewholder viewholder = (viewholder) holder;
        //setBackgroundResource
        viewholder.bt.setImageResource(mapid[position]);
        viewholder.bt.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Toast.makeText(mContext, "点击成功 ", Toast.LENGTH_LONG).show();

            }
        });
    }

    @Override public int getItemCount() {
        return mapid == null ? 0 : mapid.length;
    }

    private class viewholder extends RecyclerView.ViewHolder {
        ImageView bt;

        public viewholder(View itemView) {
            super(itemView);
            bt = (ImageView) itemView.findViewById(R.id.btn_baby);
        }
    }
}
