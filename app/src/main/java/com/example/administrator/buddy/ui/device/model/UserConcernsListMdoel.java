package com.example.administrator.buddy.ui.device.model;

import android.content.SharedPreferences;
import com.example.administrator.buddy.MyApplication;
import com.example.administrator.buddy.bean.UserConcernsResult;
import com.example.administrator.buddy.exception.CustomException;
import com.example.administrator.buddy.network.IHttpAPI;
import java.util.List;
import rx.Observable;
import rx.functions.Action1;

/**
 * 用户关注列表
 */
public class UserConcernsListMdoel {
    public Observable<UserConcernsResult> devicelist() {
        IHttpAPI iHttpAPI = MyApplication.getIHttpApi();
        SharedPreferences userInfo;
        userInfo = MyApplication.getContext().getSharedPreferences("userInfo", 0);
        return iHttpAPI.getuserconcerns(userInfo.getString("UserId", ""))
                .doOnNext(new Action1<UserConcernsResult>() {
                    @Override public void call(UserConcernsResult result) {
                        if (result.isSuccess()) {
                            List<UserConcernsResult.DataBean> list= result.getData();
                            for (int i = 0; i < list.size(); i++) {
                               UserConcernsResult.DataBean data =list.get(i);
                                String deviceId = data.getDeviceId();
                                String bindCode = data.getBindCode();
                                String name = data.getName();
                                String Sin = data.getSim();
                                String avatar = data.getAvatar();
                                Boolean isAdmin = data.isAdmin();
                            }
                            }
                        else{
                                throw new CustomException(result.getMessage());
                            }
                        }
                    });
                }
    }
