package com.example.administrator.buddy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.administrator.buddy.adapter.GridFormatAdapter;
import com.facebook.drawee.backends.pipeline.Fresco;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhuj on 2017/9/7 20:24.
 */
public class MainFragmentGame extends BaseFragment  {
    private List mList;
    private int [] mapid = {R.mipmap.home_tabbar_game  };
    private GridFormatAdapter mGFAdapter;
    private RecyclerView mRView;
    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        Fresco.initialize(getContext());
        View view =inflater.inflate(R.layout.fragment_game,container,false);
        mRView = (RecyclerView) view.findViewById(R.id.rlv_baby);
        mRView.setLayoutManager( new GridLayoutManager(getContext(),4));
        mList = new ArrayList<>();
        mGFAdapter =new GridFormatAdapter(getContext() ,mapid);
        mRView.setAdapter(mGFAdapter);
        return view;
    }
    //刷新

}
