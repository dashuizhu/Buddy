package com.example.administrator.buddy.ui.device.presenter;

import com.example.administrator.buddy.bean.DeviceContactsBean;
import com.example.administrator.buddy.bean.DeviceContactsResult;
import com.example.administrator.buddy.bean.NetworkResult;
import com.example.administrator.buddy.bean.UserConcernsResult;
import com.example.administrator.buddy.ui.BasePresenter;
import com.example.administrator.buddy.ui.device.model.UserConcernsListMdoel;
import com.example.administrator.buddy.view.IBaseView;
import java.util.List;
import javax.inject.Inject;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zhuj on 2017/10/27 19:41.
 */
public class UserConcernsPresenter extends BasePresenter {
    private IBaseView mBaseView;
    private UserConcernsListMdoel mListMdoel;

    @Inject public UserConcernsPresenter(IBaseView iBaseView,UserConcernsListMdoel mListMdoel){
        this.mBaseView=iBaseView;
        this.mListMdoel=mListMdoel;
    }

    public void attentionUser(List<DeviceContactsBean> list){
        mBaseView.displayDialog();
        addSubscrier(mListMdoel.attentionUser(list)
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
                            //networkResult.setTag(NetworkResult.TAG_DELETE);
                            mBaseView.success(networkResult);

                    }
                }));

    }

    public void getUserList(){
        addSubscrier(mListMdoel.getlist()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<UserConcernsResult>() {
                    @Override public void onCompleted() {

                    }

                    @Override public void onError(Throwable throwable) {
                        mBaseView.onError(throwable.getMessage());
                        throwable.printStackTrace();
                    }

                    @Override public void onNext(UserConcernsResult networkResult) {
                        mBaseView.shutDialg();
                        mBaseView.success(networkResult);
                    }
                }));
    }
    public void deleteUser(){
        mBaseView.displayDialog();
        addSubscrier(mListMdoel.deleteUserBean()
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
}
