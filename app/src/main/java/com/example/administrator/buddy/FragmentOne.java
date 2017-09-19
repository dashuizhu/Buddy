package com.example.administrator.buddy;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by zhuj on 2017/8/16 14:41.
 */
public class FragmentOne extends Fragment {
    TextView singOnt;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        //对View中控件的操作方法
        View view = inflater.inflate(R.layout.fragment_bootinterfaceone, container, false);
        singOnt =(TextView)view.findViewById(R.id.rl_skip);
        skip();
        return view;



    }

    protected void skip(){
        singOnt.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Intent intent = new Intent(getActivity() ,LoginActivity.class);//未继承Activity，getActivity的属性
                startActivity(intent);
                getActivity().finish();
            }
        });
    }


}