package com.example.administrator.buddy.ui.habit;

import com.example.administrator.buddy.bean.HabitBean;
import com.example.administrator.buddy.bean.HabitDetailBean;
import com.example.administrator.buddy.bean.HabitDetailResult;
import com.example.administrator.buddy.bean.NetworkResult;
import com.example.administrator.buddy.request.HabitMdoel;
import com.example.administrator.buddy.ui.BasePresenter;
import com.example.administrator.buddy.view.IBaseView;
import javax.inject.Inject;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author zhuj
 * @date 2017/6/15 下午4:27.
 */
public class HabitPresenter extends BasePresenter {

  private IBaseView mBaseView;
  private HabitMdoel mHabitModel;

  @Inject public HabitPresenter(IBaseView baseView, HabitMdoel model) {
    this.mBaseView = baseView;
    mHabitModel = model;
  }

  /**
   * 获得所有推荐习惯
   */
  //public void getHabitList(int category) {
  //  addSubscrier(mHabitModel.getHabitsList(category)
  //          .subscribeOn(Schedulers.io())
  //          .observeOn(AndroidSchedulers.mainThread())
  //          .subscribe(new Subscriber<HabitDetailListResult>() {
  //            @Override public void onCompleted() {
  //
  //            }
  //
  //            @Override public void onError(Throwable throwable) {
  //              mBaseView.onLoadError(throwable);
  //              throwable.printStackTrace();
  //            }
  //
  //            @Override public void onNext(HabitDetailListResult networkResult) {
  //              mBaseView.hideProgress();
  //              mBaseView.onSuccess(networkResult);
  //            }
  //          }));
  //}

  /**
   * 获得今天习惯
   */
  //public void getHabitListToday(int category) {
  //  addSubscrier(mHabitModel.getHabitsListToday(category)
  //          .subscribeOn(Schedulers.io())
  //          .observeOn(AndroidSchedulers.mainThread())
  //          .subscribe(new Subscriber<HabitListResult>() {
  //            @Override public void onCompleted() {
  //
  //            }
  //
  //            @Override public void onError(Throwable throwable) {
  //              mBaseView.onLoadError(throwable);
  //              throwable.printStackTrace();
  //            }
  //
  //            @Override public void onNext(HabitListResult networkResult) {
  //              mBaseView.hideProgress();
  //              mBaseView.onSuccess(networkResult);
  //            }
  //          }));
  //}

  /**
   * 获得历史习惯
   */
  public void getHabitDetail(HabitBean habitBean) {
    mBaseView.displayDialog();
    addSubscrier(mHabitModel.getHabitDetail(habitBean)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Subscriber<HabitDetailResult>() {
              @Override public void onCompleted() {

              }

              @Override public void onError(Throwable throwable) {
                mBaseView.onError(throwable.getMessage());
                throwable.printStackTrace();
              }

              @Override public void onNext(HabitDetailResult networkResult) {
                mBaseView.shutDialg();
                mBaseView.success(networkResult);
              }
            }));
  }

  public void addHabit(HabitDetailBean habitBean) {
    mBaseView.displayDialog();
    addSubscrier(mHabitModel.addHabit(habitBean)
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
                mBaseView.success(networkResult);
              }
            }));
  }

  public void modifyHabit(HabitDetailBean bean) {
    mBaseView.displayDialog();
    addSubscrier(mHabitModel.modifyHabitBean(bean)
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
                mBaseView.success(networkResult);
              }
            }));
  }


}
