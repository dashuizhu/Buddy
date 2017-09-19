package com.example.administrator.buddy;

import android.support.v4.app.Fragment;
import android.widget.Toast;
import com.example.administrator.buddy.view.IBaseView;

/**
 * Created by zhuj on 2017/9/5 19:43.
 */
public class BaseFragment extends Fragment implements IBaseView {
    public LoadDialog mDialog;
    @Override public void displayDialog() {
        if (mDialog==null){
            mDialog =  new  LoadDialog(getContext());
        }
        mDialog.show();
    }

    @Override public void shutDialg() {
        if (mDialog!=null){
            mDialog.dismiss();
        }
    }

    @Override public void success(Object o) {

    }

    @Override public void onError(String meg) {
        if (meg!=null){
            Toast.makeText(getContext(),meg,Toast.LENGTH_LONG).show();
        }

    }
}
