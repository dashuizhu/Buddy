package com.example.administrator.buddy;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by zhuj on 2017/8/18 22:12.
 */
public class PoputextDialog extends Dialog {
    private Button det;
    private Button can;
    private EditText mText;
    protected onClickconfirm mOnClickconfirm;



    public PoputextDialog(@NonNull Context context) {
        super(context, R.style.mydialog);

    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popuptext);
        mText=(EditText)findViewById(R.id.et_text);
        det=(Button) findViewById(R.id.btn_det);
        det.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                String text =mText.getText().toString();
                if (mOnClickconfirm!=null){
                    mOnClickconfirm.onClickcon(text);
                }
            }
        });
        can=(Button) findViewById(R.id.btn_can);
        can.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                if (mOnClickconfirm!=null){
                    mOnClickconfirm.onClickcancel();
                }
            }
        });
    }
    protected  interface  onClickconfirm{
        void onClickcon (String s);
        void onClickcancel ();
    }
    protected void setOnClickconfirm(onClickconfirm  o){
        this.mOnClickconfirm =o;
    }
    protected   void setmText(String s){
        this.mText.setText(s);
    }
}
