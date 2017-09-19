package com.example.administrator.buddy;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

/**
 * Created by zhuj on 2017/8/18 22:12.
 */
public class LoadDialog extends Dialog {

    public LoadDialog(@NonNull Context context) {
        super(context, R.style.mydialog);

    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog);
    }

}
