package com.example.administrator.buddy;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

public class WActivity extends Activity {
    TextView loginuser;
    TextView singOnt;
    private SharedPreferences userInfo;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_w);
        loginuser =(TextView)findViewById(R.id.tv_loginuser);
        userInfo =getSharedPreferences("userInfo", 0);
        signOntButton();
    }

    private void signOntButton(){
        singOnt =(TextView)findViewById(R.id.tv_Signout);
        singOnt.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                SharedPreferences.Editor editor=userInfo.edit();
                editor.putBoolean("login",false);
                editor.commit();
                Intent intent = new Intent(WActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }


}
