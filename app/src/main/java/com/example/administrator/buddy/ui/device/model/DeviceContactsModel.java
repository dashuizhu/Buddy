package com.example.administrator.buddy.ui.device.model;

import com.example.administrator.buddy.AppString;
import com.example.administrator.buddy.MyApplication;
import com.example.administrator.buddy.bean.DeviceContactsBean;
import com.example.administrator.buddy.bean.DeviceContactsResult;
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
 * 手表联系人相关接口
 * @author zhuj
 * @date 2017/6/15 下午4:27.
 */
public class DeviceContactsModel {

  /**
   * 获取联系人白名单列表
   */
  public Observable<DeviceContactsResult> getDeviceContactsList() {
    IHttpAPI iHttpApi = MyApplication.getIHttpApi();
    String deviceId = AppString.deviceId;
    String userId = SharedPreUser.getInstance().getKeyUserId();
    return iHttpApi.getDeviceContactsList(deviceId, userId)
            .doOnNext(new Action1<DeviceContactsResult>() {
              @Override public void call(DeviceContactsResult deviceContactsResult) {
                if (!deviceContactsResult.isSuccess()) {
                  throw new CustomException(deviceContactsResult.getMessage());
                }
              }
            });
  }

  /**
   * 添加联系人 、白名单
   */
  public Observable<NetworkResult> addContactsBean(List<DeviceContactsBean> list) {
    IHttpAPI iHttpApi = MyApplication.getIHttpApi();
    String deviceId = AppString.deviceId;
    String userId = SharedPreUser.getInstance().getKeyUserId();

    JSONArray array = new JSONArray();
    JSONObject obj;
    DeviceContactsBean familyBean;
    for (int i=0; i< list.size(); i++) {
      familyBean = list.get(i);
      obj = new JSONObject();
      try {
        obj.put("name", familyBean.getName());
        obj.put("mobile", familyBean.getMobile());
        obj.put("avatar", familyBean.getAvatar());
        obj.put("relation", familyBean.getRelation());
        obj.put("avatar", String.valueOf(familyBean.getRelation()));
        array.put(obj);
      } catch (JSONException e) {
        e.printStackTrace();
      }
    }

    RequestBody body = JsonUtils.toRequestBody(list);
    return iHttpApi.addContacts(deviceId, userId, body).doOnNext(new Action1<NetworkResult>() {
      @Override public void call(NetworkResult networkResult) {
        if (!networkResult.isSuccess()) {
          throw new CustomException(networkResult.getMessage());
        }
      }
    });
  }

  public Observable modifyContactsBean(List<DeviceContactsBean> list) {
    IHttpAPI iHttpApi = MyApplication.getIHttpApi();
    String deviceId = AppString.deviceId;
    String userId = SharedPreUser.getInstance().getKeyUserId();
    JSONArray array = new JSONArray();
    JSONObject obj;
    DeviceContactsBean familyBean;
    for (int i=0; i< list.size(); i++) {
      familyBean = list.get(i);
      obj = new JSONObject();
      try {
        obj.put("id", familyBean.getId());
        obj.put("name", familyBean.getName());
        obj.put("mobile", familyBean.getMobile());
        obj.put("avatar", String.valueOf(familyBean.getRelation()));
        obj.put("relation", familyBean.getRelation());
        array.put(obj);
      } catch (JSONException e) {
        e.printStackTrace();
      }
    }
    RequestBody body = JsonUtils.toRequestBody(array);
    return iHttpApi.modifyContacts(deviceId, userId, body).doOnNext(new Action1<NetworkResult>() {
      @Override public void call(NetworkResult networkResult) {
        if (!networkResult.isSuccess()) {
          throw new CustomException(networkResult.getMessage());
        }
      }
    });
  }

  public Observable<NetworkResult> deleteContactsBean(List<DeviceContactsBean> list) {
    IHttpAPI iHttpApi = MyApplication.getIHttpApi();
    String deviceId = AppString.deviceId;
    String userId = SharedPreUser.getInstance().getKeyUserId();
    int[] array = new int[list.size()];
    for (int i = 0; i < list.size(); i++) {
      array[i] = list.get(i).getId();
    }
    RequestBody body = JsonUtils.toRequestBody(array);
    return iHttpApi.deleteContacts(deviceId, userId, body).doOnNext(new Action1<NetworkResult>() {
      @Override public void call(NetworkResult networkResult) {
        if (!networkResult.isSuccess()) {
          throw new CustomException(networkResult.getMessage());
        }
      }
    });
  }

  /**
   * 设置sos号码
   */
  public Observable setSos(List<DeviceContactsBean> list) {
    IHttpAPI iHttpApi = MyApplication.getIHttpApi();
    String deviceId = AppString.deviceId;
    String userId = SharedPreUser.getInstance().getKeyUserId();
    JSONArray array = new JSONArray();
    JSONObject obj;
    DeviceContactsBean familyBean;
    for (int i=0; i< list.size(); i++) {
      familyBean = list.get(i);
      obj = new JSONObject();
      try {
        obj.put("id", familyBean.getId());
        obj.put("isSos", familyBean.isSos());
        obj.put("seq", familyBean.getSeq());
        array.put(obj);
      } catch (JSONException e) {
        e.printStackTrace();
      }
    }
    RequestBody body = JsonUtils.toRequestBody(array);
    return iHttpApi.setSos(deviceId, userId, body).doOnNext(new Action1<NetworkResult>() {
      @Override public void call(NetworkResult networkResult) {
        if (!networkResult.isSuccess()) {
          throw new CustomException(networkResult.getMessage());
        }
      }
    });
  }
}
