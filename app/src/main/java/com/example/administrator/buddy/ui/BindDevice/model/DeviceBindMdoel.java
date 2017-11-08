package com.example.administrator.buddy.ui.BindDevice.model;

import android.util.Log;
import com.example.administrator.buddy.MyApplication;
import com.example.administrator.buddy.bean.DeviceStatusResult;
import com.example.administrator.buddy.bean.NetworkResult;
import com.example.administrator.buddy.bean.UserBindDeviceBean;
import com.example.administrator.buddy.bean.UserBindDeviceResult;
import com.example.administrator.buddy.exception.CustomException;
import com.example.administrator.buddy.network.IHttpAPI;
import com.example.administrator.buddy.utils.JsonUtils;
import com.example.administrator.buddy.utils.SharedPreUser;
import java.util.List;
import okhttp3.RequestBody;
import org.json.JSONException;
import org.json.JSONObject;
import rx.Observable;
import rx.functions.Action1;

/**
 * 用户关注列表
 */
public class DeviceBindMdoel {

    public Observable<DeviceStatusResult>getdeviceStatus(String bindCode){
        IHttpAPI iHttpApi = MyApplication.getIHttpApi();

        return iHttpApi.getDeviceStatus(bindCode).doOnNext(new Action1<DeviceStatusResult>() {
            @Override public void call(DeviceStatusResult networkResult) {
                Log.e("bind",networkResult.toString());
                if (!networkResult.isSuccess()) {
                    throw new CustomException(networkResult.getMessage());
                }
            }
        });
    }

    public Observable<UserBindDeviceResult> postbindDevice(List<UserBindDeviceBean> list) {
        IHttpAPI iHttpApi = MyApplication.getIHttpApi();
        String bindCode = SharedPreUser.getInstance().getBindCode();
        String userId =SharedPreUser.getInstance().getKeyUserId();
        JSONObject obj=new JSONObject();
        UserBindDeviceBean familyBean;
        for (int i=0; i< list.size(); i++) {
            familyBean = list.get(i);
            obj = new JSONObject();
            try {
                obj.put("userName", familyBean.getUserName());
                obj.put("avatar", familyBean.getAvatar());
                obj.put("relation", familyBean.getRelation());
                //obj.put("timeZone", familyBean.getTimeZone());
            //    obj.put("holder", familyBean.getholder());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        RequestBody body = JsonUtils.toRequestBody(obj);
            return iHttpApi.bindDevice(bindCode,userId,body)
                    .doOnNext(new Action1<UserBindDeviceResult>() {
                        @Override public void call(UserBindDeviceResult deviceContactsResult) {
                            if (!deviceContactsResult.isSuccess()) {
                                throw new CustomException(deviceContactsResult.getMessage());
                            }

                        }
                    });

    }

    public Observable<NetworkResult> unBind() {
        IHttpAPI iHttpApi = MyApplication.getIHttpApi();
        String deviceId = SharedPreUser.getInstance().getDeviceId();
        String userId = SharedPreUser.getInstance().getKeyUserId();
        return iHttpApi.removeBind(deviceId, userId).doOnNext(new Action1<NetworkResult>() {
            @Override public void call(NetworkResult networkResult) {
                if (!networkResult.isSuccess()) {
                    throw new CustomException(networkResult.getMessage());
                }
            }
        });
    }
}
