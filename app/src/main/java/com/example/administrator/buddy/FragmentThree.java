package com.example.administrator.buddy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by zhuj on 2017/8/16 14:49.
 */
public class FragmentThree extends Fragment{
    TextView textViewSkip;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.fragment_bootinterfacethree, container, false);
        textViewSkip =(TextView)view.findViewById(R.id.tv_skip);
        skip();
        return view;
    }
    protected void skip(){


        textViewSkip.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Intent intent = new Intent(getActivity() ,LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }
}
