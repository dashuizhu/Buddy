package com.example.administrator.buddy.ui.BindDevice.presenter;

import android.util.Log;
import com.example.administrator.buddy.bean.DeviceStatusResult;
import com.example.administrator.buddy.bean.NetworkResult;
import com.example.administrator.buddy.bean.UserBindDeviceBean;
import com.example.administrator.buddy.bean.UserBindDeviceResult;
import com.example.administrator.buddy.ui.BasePresenter;
import com.example.administrator.buddy.view.IBaseView;
import java.util.List;
import javax.inject.Inject;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zhuj on 2017/10/27 19:41.
 */
public class DeviceBindPresenter extends BasePresenter {
    private IBaseView mBaseView;
    private DeviceBindMdoel mListMdoel;

    @Inject public DeviceBindPresenter(IBaseView iBaseView,DeviceBindMdoel mListMdoel){
        this.mBaseView=iBaseView;
        this.mListMdoel=mListMdoel;
    }

    public void getdeviceStatus(String bindCode){
        mBaseView.displayDialog();
        addSubscrier(mListMdoel.getdeviceStatus(bindCode)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DeviceStatusResult>() {
                    @Override public void onCompleted() {

                    }

                    @Override public void onError(Throwable throwable) {
                        Log.e("bindp",throwable.toString());
                            mBaseView.onError(throwable.getMessage());
                            throwable.printStackTrace();

                    }

                    @Override public void onNext(DeviceStatusResult networkResult) {
                        Log.e("bindp",networkResult.toString());
                            mBaseView.shutDialg();
                            //networkResult.setTag(.TAG_DELETE);
                            mBaseView.success(networkResult);

                    }
                }));

    }

    public void bindeDevice( List<UserBindDeviceBean> list){
        mBaseView.displayDialog();
        addSubscrier(mListMdoel.postbindDevice(list)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<UserBindDeviceResult>() {
                    @Override public void onCompleted() {

                    }

                    @Override public void onError(Throwable throwable) {
                        mBaseView.onError(throwable.getMessage());
                        throwable.printStackTrace();
                    }

                    @Override public void onNext(UserBindDeviceResult networkResult) {
                        mBaseView.shutDialg();
                        mBaseView.success(networkResult);
                    }
                }));
    }
  public void unBind(){
      mBaseView.displayDialog();
      addSubscrier(mListMdoel.unBind()
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(new Subscriber<NetworkResult>() {
          @Override public void onCompleted() {

          }

          @Override public void onError(Throwable e) {
              mBaseView.onError(e.getMessage());
              e.printStackTrace();
          }

          @Override public void onNext(NetworkResult result) {
              mBaseView.shutDialg();
              mBaseView.success(result);
          }
      }));
  }
}
