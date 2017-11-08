package com.example.administrator.buddy.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.example.administrator.buddy.R;

/**
 * Created by zhuj on 2017/8/18 22:12.
 */
public class LoadDialogBoxDialog extends Dialog {

    @BindView(R.id.title) TextView mTitle;
    @BindView(R.id.tv_Text) TextView mTvText;
    @BindView(R.id.btn_cancel) Button mBtnCancel;
    @BindView(R.id.btn_determine) Button mBtnDetermine;
    protected onClickconfirm onClickconfirm;

    public LoadDialogBoxDialog(@NonNull Context context) {
        super(context, R.style.mydialog);
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_load);
        ButterKnife.bind(this);
    }

    @OnClick({ R.id.btn_cancel, R.id.btn_determine }) public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
                if (onClickconfirm != null) {
                    onClickconfirm.onCancel();
                }
                break;
            case R.id.btn_determine:
                if (onClickconfirm != null) {
                    onClickconfirm.onDetermine();
                }
                break;
        }
    }

    public interface onClickconfirm {
        void onDetermine();

        void onCancel();
    }

    //
    public void setOnClickconfirm(onClickconfirm o) {
        this.onClickconfirm = o;
    }
}
