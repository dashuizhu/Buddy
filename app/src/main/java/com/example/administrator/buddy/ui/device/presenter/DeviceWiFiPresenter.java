package com.example.administrator.buddy.ui.device.presenter;

import com.example.administrator.buddy.bean.DeviceWiFiBean;
import com.example.administrator.buddy.bean.DeviceWiFiResult;
import com.example.administrator.buddy.bean.NetworkResult;
import com.example.administrator.buddy.ui.BasePresenter;
import com.example.administrator.buddy.ui.device.model.DeviceWiFiModel;
import com.example.administrator.buddy.view.IBaseView;
import java.util.List;
import javax.inject.Inject;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zhuj on 2017/10/25 19:49.
 */
public class DeviceWiFiPresenter extends BasePresenter {
    private IBaseView mBaseView;
    private DeviceWiFiModel mWiFiModel;

    @Inject public DeviceWiFiPresenter(IBaseView iBaseView,DeviceWiFiModel wiFiModel){
        this.mBaseView=iBaseView;
        this.mWiFiModel=wiFiModel;
    }

    public void getWiFiList(){
        addSubscrier(mWiFiModel.getDeviceWiFiList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DeviceWiFiResult>() {
                    @Override public void onCompleted() {

                    }

                    @Override public void onError(Throwable throwable) {
                        mBaseView.onError(throwable.getMessage());
                        throwable.printStackTrace();
                    }

                    @Override public void onNext(DeviceWiFiResult deviceWiFiResult) {
                        mBaseView.shutDialg();
                        mBaseView.success(deviceWiFiResult);
                    }
                }));
    }
    public void addWiFi(List<DeviceWiFiBean> list ){
        mBaseView.displayDialog();
        addSubscrier(mWiFiModel.addwifiBean(list)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<NetworkResult>() {
                    @Override public void onCompleted() {

                    }

                    @Override public void onError(Throwable throwable) {
                        throwable.printStackTrace();
                        mBaseView.onError(throwable.getMessage());
                    }

                    @Override public void onNext(NetworkResult networkResult) {
                        mBaseView.shutDialg();
                        networkResult.setTag(NetworkResult.TAG_ADD);
                        mBaseView.success(networkResult);
                    }
                }));
    }

    public void deleteWifI(List<DeviceWiFiBean> list){
            mBaseView.displayDialog();
            addSubscrier(mWiFiModel.deleteWiFisBean(list)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<NetworkResult>() {
                        @Override public void onCompleted() {

                        }

                        @Override public void onError(Throwable throwable) {
                            mBaseView.onError(throwable.getMessage());
                            throwable.printStackTrace();
                        }

                        @Override public void onNext(NetworkResult networkResult) {
                            mBaseView.shutDialg();
                            networkResult.setTag(NetworkResult.TAG_DELETE);
                            mBaseView.success(networkResult);
                        }
                    }));

    }

    public void modifyWifi(List<DeviceWiFiBean> list){
        mBaseView.displayDialog();
        addSubscrier(mWiFiModel.modifyWifiBean(list)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<NetworkResult>() {
                    @Override public void onCompleted() {

                    }

                    @Override public void onError(Throwable throwable) {
                        mBaseView.onError(throwable.getMessage());
                        throwable.printStackTrace();
                    }

                    @Override public void onNext(NetworkResult networkResult) {
                        mBaseView.shutDialg();
                        networkResult.setTag(NetworkResult.TAG_MODIFY);
                        mBaseView.success(networkResult);
                    }
                }));
    }
}
