package com.example.administrator.buddy.network;

import com.example.administrator.buddy.bean.HabitDetailResult;
import com.example.administrator.buddy.bean.HabitResult;
import com.example.administrator.buddy.bean.LoginResult;
import com.example.administrator.buddy.bean.NetworkResult;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by zhuj on 2017/9/19 22:06.
 */
//封装网络请求数据
public interface IHttpAPI {
    //@Path("accountId") String accountId,
    @POST("api/apps/BUDDY_API_TEST/accounts/login")
    Observable<LoginResult> login(
          @Body RequestBody data);

    @GET("api/devices/{deviceId}/habits/today")
    Observable<HabitResult> habit(
            @Path("deviceId")String deviceId
            ,@Query("userId") String userId);

    /**
     * 获取用户习惯详情
     */
    @GET("api/devices/{deviceId}/habits/info")
    @Headers({
            "Connection:close"
    })
    Observable<HabitDetailResult> getHabitInfo(
            @Path("deviceId") String deviceId, @Query("userId") String userId, @Query("habitId") long habitId,
            @Query("flag") int flag);



    /**
     * 订阅习惯， 也就是添加
     */
    @POST("api/devices/{deviceId}/habits/subscribe") Observable<NetworkResult> addHabit(
            @Path("deviceId") String deviceId, @Query("userId") String userId,
            @Body RequestBody body);

    /**
     * 修改订阅习惯
     */
    @PUT("api/devices/{deviceId}/habits/subscribe") Observable<NetworkResult> modifyHabit(
            @Path("deviceId") String deviceId, @Query("userId") String userId,
            @Body RequestBody body);
}
