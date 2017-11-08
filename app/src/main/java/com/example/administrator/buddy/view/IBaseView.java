package com.example.administrator.buddy.view;

/**
 *
 * Created by zhuj on 2017/9/3 12:09.
 */
public interface IBaseView {

     void displayDialog();

     void shutDialg();

     void success(Object o);

    void onError(String meg);

}
