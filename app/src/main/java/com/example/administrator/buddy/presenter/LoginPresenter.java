package com.example.administrator.buddy.presenter;

import android.os.Looper;
import com.example.administrator.buddy.bean.DeviceHolderResult;
import com.example.administrator.buddy.bean.HabitResult;
import com.example.administrator.buddy.bean.LoginResult;
import com.example.administrator.buddy.bean.NetworkResult;
import com.example.administrator.buddy.bean.UserConcernsResult;
import com.example.administrator.buddy.request.HabitMdoel;
import com.example.administrator.buddy.request.LoginMdoel;
import com.example.administrator.buddy.request.RegisterMdel;
import com.example.administrator.buddy.request.SetupMdoel;
import com.example.administrator.buddy.request.VerificationMdoel;
import com.example.administrator.buddy.ui.BasePresenter;
import com.example.administrator.buddy.ui.device.model.UserConcernsListMdoel;
import com.example.administrator.buddy.view.IBaseView;
import javax.inject.Inject;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by zhuj on 2017/9/3 12:05.
 */
public class LoginPresenter extends BasePresenter {
    private IBaseView mIBaseView;
    private LoginMdoel mLoginMdoel;
    private RegisterMdel mRegisterMdel;
    private VerificationMdoel mVerMdoel;
    private HabitMdoel mHabitMdoel;
    private SetupMdoel mSetupMdoel;
    private UserConcernsListMdoel mListMdoel;

    //@Inject 1) 如果是用在字段上,表示需要注入该字段的对象;
    //2) 如果用在构造方法上, 表示构造当前类对象通过此构造方法;
    //3) 如果是用在方法上, 表示当前类对象构造完成后, 立马调用该方法.
    @Inject public LoginPresenter(IBaseView l, LoginMdoel loginMdoel, RegisterMdel registerMdel,
            VerificationMdoel verMdoel, HabitMdoel habitMdoel, SetupMdoel setupMdoel,
            UserConcernsListMdoel ListMdoel) {
        this.mIBaseView = l;
        this.mLoginMdoel = loginMdoel;
        this.mRegisterMdel = registerMdel;
        this.mVerMdoel = verMdoel;
        this.mHabitMdoel = habitMdoel;
        this.mSetupMdoel = setupMdoel;
        this.mListMdoel = ListMdoel;
    }

    public void login(final String accout, final String password) {
        //  在Presenter里通过继承BaseActivity引用接口IBaseView的displayDialog方法.
        mIBaseView.displayDialog();
        //Rxjava
        mLoginMdoel.login(accout, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<LoginResult>() {
                    @Override public void onCompleted() {

                    }

                    @Override public void onError(Throwable e) {
                        mIBaseView.shutDialg();
                        mIBaseView.onError(e.getMessage());
                    }

                    @Override public void onNext(LoginResult loginResult) {
                        mIBaseView.shutDialg();
                        mIBaseView.success(loginResult);
                    }
                });
        //    @Override public void callback(NetworkResult result) {
        //        // 在子线程中初始化一个Looper对象
        //        Looper.prepare();
        //        mIBaseView.shutDialg();
        //        if (result == null) {
        //            mIBaseView.onError(null);
        //        } else if (result.isSuccess()) {
        //            mIBaseView.success(result);
        //        } else {
        //            mIBaseView.onError(result.getMessage());
        //        }
        //        // 把刚才初始化的Looper对象运行起来，循环消息队列的消息
        //        Looper.loop();
        //    }
        //});
    }

    public void loginSQL(final String accout, final String password) {
        mIBaseView.displayDialog();
        mLoginMdoel.login(accout, password)
                .flatMap(new Func1<LoginResult, Observable<?>>() {
                    @Override public Observable<?> call(LoginResult loginResult) {

                        getUserList();

                        return null;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Object>() {
                    @Override public void onCompleted() {

                    }

                    @Override public void onError(Throwable e) {
                        mIBaseView.shutDialg();
                        mIBaseView.onError(e.getMessage());
                    }

                    @Override public void onNext(Object o) {
                        mIBaseView.shutDialg();
                        mIBaseView.success(o);
                    }
                });
    }

    public void getUserList() {
        mIBaseView.displayDialog();
        addSubscrier(mListMdoel.getlist()
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<UserConcernsResult>() {
                    @Override public void onCompleted() {

                    }

                    @Override public void onError(Throwable throwable) {
                        mIBaseView.onError(throwable.getMessage());
                        throwable.printStackTrace();
                    }

                    @Override public void onNext(UserConcernsResult networkResult) {
                        mIBaseView.shutDialg();
                        mIBaseView.success(networkResult);
                    }
                }));
        //    IHttpAPI iHttpApi = MyApplication.getIHttpApi();
        //    String userId = SharedPreUser.getInstance().getKeyUserId();
        //    return iHttpApi.getUserConcernsList( userId)
        //            .doOnNext(new Action1<UserConcernsResult>() {
        //                @Override public void call(UserConcernsResult deviceContactsResult) {
        //                    if (!deviceContactsResult.isSuccess()) {
        //                        throw new CustomException(deviceContactsResult.getMessage());
        //                    }else {
        //                        DeviceListSQLMdoel mSql=new DeviceListSQLMdoel(MyApplication.getContext());
        //                       List<UserConcernsBean> mlist = deviceContactsResult.getFamilyBeanList();
        //                        mSql.addtoSqlList(mlist);
        //                    }
        //                }
        //            });
    }

    public void habitMdel() {
        //mIBaseView.displayDialog();

        mHabitMdoel.habi()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HabitResult>() {
                    @Override public void onCompleted() {
                    }

                    @Override public void onError(Throwable e) {
                        mIBaseView.shutDialg();
                        mIBaseView.onError(e.getMessage());
                    }

                    @Override public void onNext(HabitResult habitResult) {
                        mIBaseView.shutDialg();
                        mIBaseView.success(habitResult.getData());
                    }
                });
    }

    public void register(final String acctount, final String password, final String verifyCode,
            final boolean b) {
        mIBaseView.displayDialog();
        Observable req = null;
        if (b) {
            req = mRegisterMdel.register(acctount, password, verifyCode);
        } else {
            req = mRegisterMdel.reset(acctount, password, verifyCode);
        }
        req.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<NetworkResult>() {
                    @Override public void onCompleted() {

                    }

                    @Override public void onError(Throwable e) {
                        mIBaseView.shutDialg();
                    }

                    @Override public void onNext(NetworkResult result) {
                        mIBaseView.shutDialg();
                        if (result == null) {
                            mIBaseView.onError(null);
                        } else if (result.isSuccess()) {
                            mIBaseView.success(result);
                        } else {
                            mIBaseView.onError(result.getMessage());
                        }
                    }
                });
    }
    //Thread thread = new Thread(new Runnable() {
    //    @Override public void run() {
    //        NetworkResult reg = null;
    //        if (b) {
    //            reg = mRegisterMdel.register(acctount, password, verifyCode);
    //        } else {
    //            reg = mRegisterMdel.reset(acctount, password, verifyCode);
    //        }
    //        Looper.prepare();
    //        mIBaseView.shutDialg();
    //        if (reg == null) {
    //            mIBaseView.onError(null);
    //        } else if (reg.isSuccess()) {
    //            mIBaseView.success(reg);
    //        } else {
    //            mIBaseView.onError(reg.getMessage());
    //        }
    //        Looper.loop();
    //    }
    //});
    //thread.start();

    public void verMdoel(final String accoutId) {
        mIBaseView.displayDialog();
        Thread thread = new Thread(new Runnable() {
            @Override public void run() {
                NetworkResult ver = mVerMdoel.verMdoel(accoutId);
                Looper.prepare();
                mIBaseView.shutDialg();
                if (ver == null) {
                    mIBaseView.onError(null);
                } else {
                    mIBaseView.onError(ver.getMessage());
                }
                Looper.loop();
            }
        });
        thread.start();
    }

    //public void babysetupMdel() {
    //    mIBaseView.displayDialog();
    //    Thread thread = new Thread(new Runnable() {
    //        @Override public void run() {
    //            HabitResult habit = mSetupMdoel.setupget();
    //            Looper.prepare();
    //            mIBaseView.shutDialg();
    //            if (habit == null) {
    //                mIBaseView.onError(null);
    //            } else if (habit.isSuccess()) {
    //                mIBaseView.success(habit.getList());
    //            } else {
    //                mIBaseView.onError(habit.getMessage());
    //            }
    //            Log.e("baby", "得到数据成功");
    //            Looper.loop();
    //        }
    //    });
    //    thread.start();
    //}
    //
    //public void babySetupMdelPost(final String name, final String birthday, final String school,
    //        final String startSchool) {
    //    mIBaseView.displayDialog();
    //    Thread thread = new Thread(new Runnable() {
    //        @Override public void run() {
    //            NetworkResult reg = mSetupMdoel.setipPost(name, birthday, school, startSchool);
    //            Looper.prepare();
    //            mIBaseView.shutDialg();
    //            if (reg == null) {
    //                mIBaseView.onError(null);
    //            } else if (reg.isSuccess()) {
    //                mIBaseView.success(reg);
    //            } else {
    //                mIBaseView.onError(reg.getMessage());
    //            }
    //            Log.e("baby", "发送数据成功");
    //            Looper.loop();
    //        }
    //    });
    //    thread.start();
    //}
    public void getDeviceHolder() {
        mIBaseView.displayDialog();
        mSetupMdoel.getDeviceHolderInformation()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DeviceHolderResult>() {
                    @Override public void onCompleted() {
                    }

                    @Override public void onError(Throwable e) {
                        mIBaseView.onError(e.getMessage());
                        e.printStackTrace();
                    }

                    @Override public void onNext(DeviceHolderResult deviceHolderResult) {
                        mIBaseView.shutDialg();
                        mIBaseView.success(deviceHolderResult);
                    }
                });
    }

    public void setDeviceHolder(final String name, final String birthday, final String school,
            final String startSchool, final String url) {
        mIBaseView.displayDialog();
        mSetupMdoel.setDeviceHolderInformation(name, birthday, school, startSchool, url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<NetworkResult>() {
                    @Override public void onCompleted() {

                    }

                    @Override public void onError(Throwable e) {
                        mIBaseView.onError(e.getMessage());
                        e.printStackTrace();
                    }

                    @Override public void onNext(NetworkResult networkResult) {
                        mIBaseView.shutDialg();
                        mIBaseView.success(networkResult);
                    }
                });
    }
}
