package com.example.administrator.buddy.ui.device.presenter;

import com.example.administrator.buddy.bean.CheckedResult;
import com.example.administrator.buddy.bean.DeviceContactsBean;
import com.example.administrator.buddy.bean.DeviceContactsResult;
import com.example.administrator.buddy.bean.NetworkResult;
import com.example.administrator.buddy.ui.BasePresenter;
import com.example.administrator.buddy.ui.device.model.DeviceContactsModel;
import com.example.administrator.buddy.view.IBaseView;
import java.util.List;
import javax.inject.Inject;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author zhuj
 * @date 2017/6/15 下午4:27.
 */
public class DeviceContactsPresenter extends BasePresenter {

  private IBaseView mBaseView;
  private DeviceContactsModel mDeviceContactsModel;

  @Inject public DeviceContactsPresenter(IBaseView baseView, DeviceContactsModel familyModel) {
    this.mBaseView = baseView;
    this.mDeviceContactsModel = familyModel;
  }

  public void addContacts(List<DeviceContactsBean> list) {
    mBaseView.displayDialog();
    addSubscrier(mDeviceContactsModel.addContactsBean(list)
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

  public void getContactsList() {
    addSubscrier(mDeviceContactsModel.getDeviceContactsList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Subscriber<DeviceContactsResult>() {
              @Override public void onCompleted() {

              }

              @Override public void onError(Throwable throwable) {
                mBaseView.onError(throwable.getMessage());
                throwable.printStackTrace();
              }

              @Override public void onNext(DeviceContactsResult networkResult) {
                mBaseView.shutDialg();
                mBaseView.success(networkResult);
              }
            }));
  }

  public void deleteContacts(List<DeviceContactsBean> list) {
    mBaseView.displayDialog();
    addSubscrier(mDeviceContactsModel.deleteContactsBean(list)
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

  public void modifyContacts(List<DeviceContactsBean> list) {
    mBaseView.displayDialog();
    addSubscrier(mDeviceContactsModel.modifyContactsBean(list)
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

  public void setSos(final List<DeviceContactsBean> list) {
    mBaseView.displayDialog();
    addSubscrier(mDeviceContactsModel.setSos(list)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Subscriber<NetworkResult>() {
              @Override public void onCompleted() {

              }

              @Override public void onError(Throwable throwable) {
                mBaseView.onError(throwable.getMessage());
                throwable.printStackTrace();
                CheckedResult result = new CheckedResult();
                result.setObject(list);
                mBaseView.success(result);
              }

              @Override public void onNext(NetworkResult networkResult) {
                mBaseView.shutDialg();
                networkResult.setTag(NetworkResult.TAG_MODIFY);
                mBaseView.success(networkResult);
              }
            }));
  }

  public void save(final List<DeviceContactsBean> list) {
    mBaseView.displayDialog();
    addSubscrier(mDeviceContactsModel.setSos(list)
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


  //public void testDevice(String bindCode) {
  //  mBaseView.showProgress();
  //  addSubscrier(mDeviceModel.device(bindCode)
  //          .subscribeOn(Schedulers.io())
  //          .observeOn(AndroidSchedulers.mainThread())
  //          .subscribe(new Subscriber<UserResult>() {
  //            @Override public void onCompleted() {
  //
  //            }
  //
  //            @Override public void onLoadError(Throwable throwable) {
  //              mBaseView.hideProgress();
  //              throwable.printStackTrace();
  //            }
  //
  //            @Override public void onNext(UserResult networkResult) {
  //              mBaseView.hideProgress();
  //              networkResult.setTag(NetworkResult.TAG_MODIFY);
  //              mBaseView.onSuccess(networkResult);
  //            }
  //          }));
  //
  //}
}
