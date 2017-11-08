package com.example.administrator.buddy.ui.device.model;

import android.util.Log;
import com.example.administrator.buddy.Helper.DeviceListSQLMdoel;
import com.example.administrator.buddy.MyApplication;
import com.example.administrator.buddy.bean.DeviceContactsBean;
import com.example.administrator.buddy.bean.DeviceContactsResult;
import com.example.administrator.buddy.bean.NetworkResult;
import com.example.administrator.buddy.bean.UserConcernsBean;
import com.example.administrator.buddy.bean.UserConcernsResult;
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
public class UserConcernsListMdoel {

    public Observable<DeviceContactsResult>attentionUser(List<DeviceContactsBean> list){
        IHttpAPI iHttpApi = MyApplication.getIHttpApi();
        String userId = SharedPreUser.getInstance().getKeyUserId();
        JSONObject obj= new JSONObject();
        DeviceContactsBean familyBean;
        for (int i=0; i< list.size(); i++) {
            familyBean = list.get(i);
            obj = new JSONObject();
            try {
                obj.put("userName", familyBean.getUserName());
                obj.put("avatar", familyBean.getAvatar());
                obj.put("relation", familyBean.getRelation());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        RequestBody body = JsonUtils.toRequestBody(obj);
        String bindCode = SharedPreUser.getInstance().getBindCode();
        return iHttpApi.attentionUser(bindCode,userId,body).doOnNext(new Action1<DeviceContactsResult>() {
            @Override public void call(DeviceContactsResult networkResult) {
                if (!networkResult.isSuccess()) {
                    throw new CustomException(networkResult.getMessage());
                }
            }
        });
    }

    public Observable<UserConcernsResult> getlist() {
        IHttpAPI iHttpApi = MyApplication.getIHttpApi();
        String userId = SharedPreUser.getInstance().getKeyUserId();
            return iHttpApi.getUserConcernsList( userId)
                    .doOnNext(new Action1<UserConcernsResult>() {
                        @Override public void call(UserConcernsResult deviceContactsResult) {
                            if (!deviceContactsResult.isSuccess()) {
                                throw new CustomException(deviceContactsResult.getMessage());
                            }else {
                                DeviceListSQLMdoel mSql=new DeviceListSQLMdoel(MyApplication.getContext());
                                List<UserConcernsBean> mlist = deviceContactsResult.getFamilyBeanList();
                                if (mlist!=null){
                                    mSql.addtoSqlList(mlist);
                                    Log.e("devicelist",mlist.toString());
                                }
                            }
                        }
                    });

    }

    public Observable<NetworkResult> deleteUserBean() {
        IHttpAPI iHttpApi = MyApplication.getIHttpApi();
        String deviceId = SharedPreUser.getInstance().getDeviceId();
        String userId = SharedPreUser.getInstance().getKeyUserId();
        //int[] array = new int[list.size()];
        //for (int i = 0; i < list.size(); i++) {
        //    array[i] = list.get(i).getId();
        //}
        //RequestBody body = JsonUtils.toRequestBody(array);
        return iHttpApi.deleteUser(deviceId, userId).doOnNext(new Action1<NetworkResult>() {
            @Override public void call(NetworkResult networkResult) {
                if (!networkResult.isSuccess()) {
                    throw new CustomException(networkResult.getMessage());
                }
            }
        });
    }
}
