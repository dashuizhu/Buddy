package com.example.administrator.buddy.ui.device.model;

import com.example.administrator.buddy.AppString;
import com.example.administrator.buddy.MyApplication;
import com.example.administrator.buddy.bean.DeviceWiFiBean;
import com.example.administrator.buddy.bean.DeviceWiFiResult;
import com.example.administrator.buddy.bean.NetworkResult;
import com.example.administrator.buddy.exception.CustomException;
import com.example.administrator.buddy.network.IHttpAPI;
import com.example.administrator.buddy.utils.JsonUtils;
import com.example.administrator.buddy.utils.SharedPreUser;
import java.util.List;
import okhttp3.RequestBody;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import rx.Observable;
import rx.functions.Action1;

/**
 * Created by zhuj on 2017/10/25 19:47.
 */
public class DeviceWiFiModel {

    public Observable<DeviceWiFiResult> getDeviceWiFiList(){
        IHttpAPI iHttpApi = MyApplication.getIHttpApi();
        String deviceId = AppString.deviceId;
        String userId = SharedPreUser.getInstance().getKeyUserId();
        //return null;
        return iHttpApi.getDeviceWiFIList(deviceId,userId)
                .doOnNext(new Action1<DeviceWiFiResult>() {
                    @Override public void call(DeviceWiFiResult wiFiResult) {
                        if (!wiFiResult.isSuccess()) {
                            throw new CustomException(wiFiResult.getMessage());
                        }
                    }
                });
    }

    public Observable<NetworkResult> addwifiBean(List<DeviceWiFiBean> list) {
        IHttpAPI iHttpApi = MyApplication.getIHttpApi();
        String deviceId = AppString.deviceId;
        String userId = SharedPreUser.getInstance().getKeyUserId();

        JSONArray array = new JSONArray();
        JSONObject obj;
        DeviceWiFiBean familyBean;
        for (int i=0; i< list.size(); i++) {
            familyBean = list.get(i);
            obj = new JSONObject();
            try {
                obj.put("ssid", familyBean.getSsid());
                obj.put("password", familyBean.getPassword());
                array.put(obj);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        RequestBody body = JsonUtils.toRequestBody(list);
        return iHttpApi.addwifi(deviceId, userId, body).doOnNext(new Action1<NetworkResult>() {
            @Override public void call(NetworkResult networkResult) {
                if (!networkResult.isSuccess()) {
                    throw new CustomException(networkResult.getMessage());
                }
            }
        });
    }

    public Observable<NetworkResult> deleteWiFisBean(List<DeviceWiFiBean> list) {
        IHttpAPI iHttpApi = MyApplication.getIHttpApi();
        String deviceId = AppString.deviceId;
        String userId = SharedPreUser.getInstance().getKeyUserId();
        int[] array = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i).getId();
        }
        RequestBody body = JsonUtils.toRequestBody(array);
        return iHttpApi.deletewifi(deviceId, userId, body).doOnNext(new Action1<NetworkResult>() {
            @Override public void call(NetworkResult networkResult) {
                if (!networkResult.isSuccess()) {
                    throw new CustomException(networkResult.getMessage());
                }
            }
        });
    }
    public Observable modifyWifiBean(List<DeviceWiFiBean> list) {
        IHttpAPI iHttpApi = MyApplication.getIHttpApi();
        String deviceId = AppString.deviceId;
        String userId = SharedPreUser.getInstance().getKeyUserId();
        JSONArray array = new JSONArray();
        JSONObject obj;
        DeviceWiFiBean familyBean;
        for (int i=0; i< list.size(); i++) {
            familyBean = list.get(i);
            obj = new JSONObject();
            try {
                obj.put("id", familyBean.getId());
                obj.put("ssid", familyBean.getSsid());
                obj.put("password", familyBean.getPassword());
                array.put(obj);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        RequestBody body = JsonUtils.toRequestBody(array);
        return iHttpApi.modifyWifi(deviceId, userId, body).doOnNext(new Action1<NetworkResult>() {
            @Override public void call(NetworkResult networkResult) {
                if (!networkResult.isSuccess()) {
                    throw new CustomException(networkResult.getMessage());
                }
            }
        });
    }
}
