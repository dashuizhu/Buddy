package com.example.administrator.buddy;

import android.app.Activity;
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
        if (meg!=null){
            Toast.makeText(this,meg,Toast.LENGTH_LONG).show();
        }
    }
}
