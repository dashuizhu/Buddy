package com.example.administrator.buddy.network;

import com.example.administrator.buddy.bean.LoginResult;
import retrofit2.http.Field;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by zhuj on 2017/9/19 22:06.
 */
public interface IHttpAPI {

    @POST("http://47.92.49.151:8080/api/apps/BUDDY_API_TEST/accounts/login")
    Observable<LoginResult> login(@Field("accountId") String acctount,
            @Field("password") String password, @Field("clientSecret") String clientSecret);
}
