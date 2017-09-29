package com.example.administrator.buddy;

import android.app.Activity;
import android.support.annotation.StringRes;
import android.widget.Toast;
import com.example.administrator.buddy.view.IBaseView;

/**
 * Created by zhuj on 2017/9/3 11:06.
 */
public class BaseActivity extends Activity implements IBaseView{
    public LoadDialog mDialog;


    public void displayDialog(){
        if(mDialog==null){
            mDialog = new LoadDialog(this);
        }
        mDialog.show();
    }
    public void shutDialg(){
        if (mDialog!=null){
            mDialog.dismiss();
        }
    }

    @Override public void success(Object o) {

    }

    @Override public void onError(String meg) {
        shutDialg();
        if (meg!=null){
            Toast.makeText(this,meg,Toast.LENGTH_LONG).show();
        }
    }

    private Toast mToast;

    public void showToast(String str) {
        if (mToast == null) {
            mToast = Toast.makeText(this, str, Toast.LENGTH_LONG);
        }
        mToast.setText(str);
        mToast.setDuration(Toast.LENGTH_LONG);
        mToast.show();
    }

    public void showToast(@StringRes int resId) {
        String str = getString(resId);
        showToast(str);
    }
}
