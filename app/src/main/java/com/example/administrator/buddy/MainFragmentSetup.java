package com.example.administrator.buddy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by zhuj on 2017/8/16 14:41.
 */
public class MainFragmentSetup extends Fragment {
    private Button dropOut;
    private RelativeLayout setup1;
    private SharedPreferences userInfo;
    private SimpleDraweeView iamgeview_setup1;
    private String url=null;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        //对View中控件的操作方法
        Fresco.initialize(getContext());
        View view = inflater.inflate(R.layout.fragment_setup, container, false);
        userInfo =getActivity().getSharedPreferences("mapurl", 0);
        dropOut =(Button)view.findViewById(R.id.tv_setup_dropOut);
        setup1 =(RelativeLayout)view.findViewById(R.id.imageview_setup2);
        iamgeview_setup1=(SimpleDraweeView) view.findViewById(R.id.iamgeview_setup1);
        url=userInfo.getString("url", "");
        iamgeview_setup1.setImageURI(url);
        signOntButton();
        babysetup();
        return view;
    }

    @Override public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==11){
            String url=data.getStringExtra("avatarurl");
            Log.e("uel",url);
            iamgeview_setup1.setImageURI(url);
        }
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
                intent.putExtra("Avatar", 1);
                startActivityForResult(intent, 11);
            }
        });
    }

}