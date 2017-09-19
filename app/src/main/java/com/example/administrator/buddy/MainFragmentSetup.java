package com.example.administrator.buddy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by zhuj on 2017/8/16 14:41.
 */
public class MainFragmentSetup extends Fragment {
    Button dropOut;
    ImageView setup1;
    private SharedPreferences userInfo;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        //对View中控件的操作方法
        View view = inflater.inflate(R.layout.fragment_setup, container, false);
        userInfo =getActivity().getSharedPreferences("userInfo", 0);
        dropOut =(Button)view.findViewById(R.id.tv_setup_dropOut);
        setup1 =(ImageView)view.findViewById(R.id.imageview_setup2);
        signOntButton();
        babysetup();
        return view;
    }

    private void signOntButton(){
       // dropOut =(TextView)view().findViewById(R.id.tv_setup_dropOut);
        dropOut.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                SharedPreferences.Editor editor=userInfo.edit();
                editor.putBoolean("login",false);
                editor.commit();
                Intent intent = new Intent(getContext(),LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }
    protected void babysetup(){
        setup1.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Intent intent = new Intent(getContext(),BabySetupAcivity.class);
                startActivity(intent);
            }
        });
    }

}