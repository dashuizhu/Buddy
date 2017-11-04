package com.example.administrator.buddy.network;

import com.example.administrator.buddy.bean.DeviceContactsResult;
import com.example.administrator.buddy.bean.DeviceStatusResult;
import com.example.administrator.buddy.bean.DeviceWiFiResult;
import com.example.administrator.buddy.bean.HabitDetailResult;
import com.example.administrator.buddy.bean.HabitResult;
import com.example.administrator.buddy.bean.LoginResult;
import com.example.administrator.buddy.bean.NetworkResult;
import com.example.administrator.buddy.bean.UserBindDeviceResult;
import com.example.administrator.buddy.bean.UserConcernsResult;
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
    /**
     * 请求登入
     */
    @POST("api/apps/BUDDY_API_TEST/accounts/login")
    Observable<LoginResult> login(
          @Body RequestBody data);
    /**
     *获取用户习惯
     */
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


    /**
     * 获得白名单列表
     * @param deviceId
     * @param userId
     * @return
     */
    @GET("api/devices/{deviceId}/contacts")
    @Headers({
            "Connection:close"
    })
    Observable<DeviceContactsResult> getDeviceContactsList(
            @Path("deviceId") String deviceId,@Query("userId") String userId);

    /**
     * 添加白名单
     * @param deviceId
     * @param userId
     * @param data
     * @return
     */
    @POST("api/devices/{deviceId}/contacts") Observable<NetworkResult> addContacts(
            @Path("deviceId") String deviceId,@Query("userId") String userId, @Body RequestBody data);

    /**
     * 修改联系人(白名单)
     * @param deviceId
     * @param userId
     * @return
     */
    @PUT("api/devices/{deviceId}/contacts") Observable<NetworkResult> modifyContacts(
            @Path("deviceId") String deviceId,@Query("userId") String userId, @Body RequestBody data);

    /**
     * 删除联系人(白名单)
     * @param deviceId
     * @param userId
     * @param data
     * @return
     */
    @POST("api/devices/{deviceId}/contacts/delete")
    Observable<NetworkResult> deleteContacts(
            @Path("deviceId") String deviceId, @Query("userId") String userId,
            @Body RequestBody data);

    /**
     * 设置SOS(白名单)
     * @param deviceId
     * @param userId
     * @param data
     * @return
     */
    @POST("api/devices/{deviceId}/contacts/sos")
    Observable<NetworkResult> setSos(
            @Path("deviceId") String deviceId,@Query("userId") String userId, @Body RequestBody data);

    /**
     * 获得WIFI
     */
    @GET("api/devices/{deviceId}/wifi")
    @Headers({
            "Connection:close"
    })
    Observable<DeviceWiFiResult> getDeviceWiFIList(
            @Path("deviceId") String deviceId,@Query("userId") String userId);

    /**
     * 添加wifi
     *
     */
    @POST("api/devices/{deviceId}/wifi")
    Observable<NetworkResult>addwifi( @Path("deviceId") String deviceId, @Query("userId") String userId,
            @Body RequestBody body);
    /**
     * 删除wifi
     *
     */
    @POST("api/devices/{deviceId}/wifi")
    Observable<NetworkResult>deletewifi( @Path("deviceId") String deviceId, @Query("userId") String userId,
            @Body RequestBody body);
    /**
     * 修改wifi
     *
     */
    @PUT("api/devices/{deviceId}/wifi") Observable<NetworkResult> modifyWifi(
            @Path("deviceId") String deviceId,@Query("userId") String userId, @Body RequestBody data);

    /**
     * 获取用户关注设备列表
     *
     */
    @GET("api/devices/holder/list")
    Observable<UserConcernsResult> getUserConcernsList(
           @Query("userId") String userId);

    /**
     * 用户关注设备
     *
     */
    @POST("api/devices/code/{bindCode}/follow")
    Observable<DeviceContactsResult>attentionUser( @Path("bindCode") String bindCode,
            @Query("userId") String userId,@Body RequestBody body);

    /**
     * 删除关注用户
     *
     */
    @POST("api/devices/{deviceId}/familyMembers/delete")
    Observable<NetworkResult>deleteUser( @Path("deviceId") String deviceId, @Query("userId") String userId,
            @Body RequestBody body);

    /**
     * 获取设备状态
     *
     */
    @GET("api/devices/code/{bindCode}/status")
    Observable<DeviceStatusResult>getDeviceStatus(
            @Path("bindCode") String bindCode
    );

    /**
     * 绑定设备
     *
     */
    @POST("api/devices/code/{bindCode}/bind")
    Observable<UserBindDeviceResult>bindDevice(@Path("bindCode") String bindCode,
            @Query("userId") String userId,
            @Body RequestBody body);
}
