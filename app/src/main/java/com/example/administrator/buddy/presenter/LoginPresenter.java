package com.example.administrator.buddy.presenter;

import android.os.Looper;
import android.util.Log;
import com.example.administrator.buddy.bean.HabitResult;
import com.example.administrator.buddy.bean.NetworkResult;
import com.example.administrator.buddy.request.HabitMdoel;
import com.example.administrator.buddy.request.LoginMdoel;
import com.example.administrator.buddy.request.NetworkCallback;
import com.example.administrator.buddy.request.RegisterMdel;
import com.example.administrator.buddy.request.SetupMdoel;
import com.example.administrator.buddy.request.VerificationMdoel;
import com.example.administrator.buddy.view.IBaseView;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by zhuj on 2017/9/3 12:05.
 */
public class LoginPresenter {
    private IBaseView mIBaseView;
    private LoginMdoel mLoginMdoel;
    private RegisterMdel mRegisterMdel;
    private VerificationMdoel mVerMdoel;
    private HabitMdoel mHabitMdoel;
    private SetupMdoel mSetupMdoel;

    public LoginPresenter(IBaseView l) {
        this.mIBaseView = l;
        mLoginMdoel = new LoginMdoel();
        mRegisterMdel = new RegisterMdel();
        mVerMdoel = new VerificationMdoel();
        mHabitMdoel = new HabitMdoel();
        mSetupMdoel = new SetupMdoel();
    }

    public void login(final String accout, final String password) {
        mIBaseView.displayDialog();
        //new Thread(new Runnable() {
        //    @Override public void run() {
        mLoginMdoel.login(accout, password, new NetworkCallback() {
            @Override public void callback(NetworkResult result) {
                // 在子线程中初始化一个Looper对象
                Looper.prepare();
                mIBaseView.shutDialg();
                if (result == null) {
                    mIBaseView.onError(null);
                } else if (result.isSuccess()) {
                    mIBaseView.success(result);
                } else {
                    mIBaseView.onError(result.getMessge());
                }
                // 把刚才初始化的Looper对象运行起来，循环消息队列的消息
                Looper.loop();
            }
        });
    }

    public void register(final String acctount, final String password, final String verifyCode,
            final boolean b) {
        mIBaseView.displayDialog();
        Observable req =null;
        if (b) {
           req=  mRegisterMdel.register(acctount, password, verifyCode);
        }else {
          req =  mRegisterMdel.reset(acctount, password, verifyCode);
        }
                req.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<NetworkResult>() {
                    @Override public void onCompleted() {

                    }

                    @Override public void onError(Throwable e) {

                    }

                    @Override public void onNext(NetworkResult result) {
                        //NetworkResult reg = null;
                        //        if (b) {
                        //            reg = mRegisterMdel.register(acctount, password, verifyCode);
                        //        } else {
                        //            reg = mRegisterMdel.reset(acctount, password, verifyCode);
                        //        }
                                mIBaseView.shutDialg();
                                if (result == null) {
                                    mIBaseView.onError(null);
                                } else if (result.isSuccess()) {
                                    mIBaseView.success(result);
                                } else {
                                    mIBaseView.onError(result.getMessge());
                                }
                    }
                });


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
        //            mIBaseView.onError(reg.getMessge());
        //        }
        //        Looper.loop();
        //    }
        //});
        //thread.start();
    }

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
                    mIBaseView.onError(ver.getMessge());
                }
                Looper.loop();
            }
        });
        thread.start();
    }

    public void habitMdel() {
        //mIBaseView.displayDialog();
        Thread thread = new Thread(new Runnable() {
            @Override public void run() {
                HabitResult habit = mHabitMdoel.habi();
                Looper.prepare();
                mIBaseView.shutDialg();
                if (habit == null) {
                    mIBaseView.onError(null);
                } else if (habit.isSuccess()) {
                    mIBaseView.success(habit.getList());
                } else {
                    mIBaseView.onError(habit.getMessge());
                }
                Looper.loop();
            }
        });
        thread.start();
    }
    public void babysetupMdel() {
        mIBaseView.displayDialog();
        Thread thread = new Thread(new Runnable() {
            @Override public void run() {
                HabitResult habit = mSetupMdoel.setupget();
                Looper.prepare();
                mIBaseView.shutDialg();
                if (habit == null) {
                    mIBaseView.onError(null);
                } else if (habit.isSuccess()) {
                    mIBaseView.success(habit.getList());
                } else {
                    mIBaseView.onError(habit.getMessge());
                }
                Log.e("baby","得到数据成功");
                Looper.loop();
            }
        });
        thread.start();
    }
    public void babySetupMdelPost(final String name , final String birthday, final String school,
            final String  startSchool){
        mIBaseView.displayDialog();
        Thread thread = new Thread(new Runnable() {
            @Override public void run() {
                NetworkResult  reg = mSetupMdoel.setipPost(name, birthday, school,startSchool);
                Looper.prepare();
                mIBaseView.shutDialg();
                if (reg == null) {
                    mIBaseView.onError(null);
                } else if (reg.isSuccess()) {
                    mIBaseView.success(reg);
                } else {
                    mIBaseView.onError(reg.getMessge());
                }
                Log.e("baby","发送数据成功");
                Looper.loop();
            }
        });
        thread.start();
    }
}
