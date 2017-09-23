package com.example.administrator.buddy.network;

import com.example.administrator.buddy.bean.HabitResult;
import com.example.administrator.buddy.bean.LoginResult;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by zhuj on 2017/9/19 22:06.
 */
public interface IHttpAPI {
    //@Path("accountId") String accountId,
    @POST("api/apps/BUDDY_API_TEST/accounts/login")
    Observable<LoginResult> login(
          @Body RequestBody data);

    @GET("api/devices/{deviceId}/habits/today")
    Observable<HabitResult> habit(
            @Path("deviceId")String deviceId
            ,@Query("userId") String userId
    );

}
