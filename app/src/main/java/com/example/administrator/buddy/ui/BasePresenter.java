package com.example.administrator.buddy.ui;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * presenter的基类
 * 调用{@link #addSubscrier(Subscription)} 记录网络操作，
 * 然后可使用{@link #destroy()} 关闭
 * @author zhuj
 * @date 2017/6/9 下午4:09
 */
public class BasePresenter {

  private CompositeSubscription mCompositeSubscription = new CompositeSubscription();

  /**
   * 停止添加监控的网络请求
   */
  public void destroy() {
    if (mCompositeSubscription == null) return;
    if (!mCompositeSubscription.isUnsubscribed()) {
      mCompositeSubscription.unsubscribe();
    }
    mCompositeSubscription = null;
  }

  /**
   * 添加到监控
   * @param subscription
   */
  protected void addSubscrier(Subscription subscription) {
    mCompositeSubscription.add(subscription);
  }
}
