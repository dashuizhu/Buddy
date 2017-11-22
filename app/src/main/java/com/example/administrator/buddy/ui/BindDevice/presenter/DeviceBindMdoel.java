package com.example.administrator.buddy.ui.BindDevice.presenter;

import com.example.administrator.buddy.MyApplication;
import com.example.administrator.buddy.bean.DeviceContactsBean;
import com.example.administrator.buddy.bean.DeviceStatusResult;
import com.example.administrator.buddy.bean.NetworkResult;
import com.example.administrator.buddy.bean.UserBindDeviceBean;
import com.example.administrator.buddy.bean.UserBindDeviceResult;
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
 * Created by Administrator on 2017/11/21.
 */

public class DeviceBindMdoel {


  public Observable<DeviceStatusResult> getdeviceStatus(final String bindCode){
    IHttpAPI iHttpAPI = MyApplication.getIHttpApi();

    return iHttpAPI.getDeviceStatus(bindCode)
            .doOnNext(new Action1<DeviceStatusResult>() {
              @Override public void call(DeviceStatusResult deviceStatusResult) {
               if (!deviceStatusResult.isSuccess()){
                 throw new ClassCastException(deviceStatusResult.getMessage());
               }else {
                 SharedPreUser.getInstance().put(MyApplication.getContext(),SharedPreUser.KEY_BIND_CODE,bindCode);
               }
              }
            });
  }
  public Observable<UserBindDeviceResult> postbindDevice(List<UserBindDeviceBean> list){
    IHttpAPI iHttpAPI = MyApplication.getIHttpApi();
    String userId= SharedPreUser.getInstance().getKeyUserId();
    JSONArray array = new JSONArray();
    JSONObject obj;
    UserBindDeviceBean familyBean;

      familyBean = list.get(0);
      obj = new JSONObject();
      try {
        obj.put("userName", familyBean.getUserName());
        //obj.put("timeZone", familyBean.getTimeZone());
        //obj.put("avatar", familyBean.getAvatar());
        //obj.put("relation", familyBean.getRelation());
        //obj.put("avatar", String.valueOf(familyBean.getRelation()));
        array.put(obj);
      } catch (JSONException e) {
        e.printStackTrace();
      }

    String bindCode= SharedPreUser.getInstance().getBindCode();
    RequestBody body = JsonUtils.toRequestBody(familyBean);
    return iHttpAPI.bindDevice(bindCode,userId,body)
            .doOnNext(new Action1<UserBindDeviceResult>() {
              @Override public void call(UserBindDeviceResult userBindDeviceResult) {
                if (!userBindDeviceResult.isSuccess()){
                  throw new ClassCastException(userBindDeviceResult.getMessage());
                }
              }
            });
  }
  public Observable<NetworkResult> unBind(){
    IHttpAPI iHttpAPI = MyApplication.getIHttpApi();
    String userId= SharedPreUser.getInstance().getKeyUserId();
    String deviceId= SharedPreUser.getInstance().getDeviceId();
    return iHttpAPI.removeBind(deviceId,userId)
            .doOnNext(new Action1<NetworkResult>() {
              @Override public void call(NetworkResult deviceStatusResult) {
                if (!deviceStatusResult.isSuccess()){
                  throw new ClassCastException(deviceStatusResult.getMessage());
                }
              }
            });
  }
}
