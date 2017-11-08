package com.example.administrator.buddy.request;

import com.example.administrator.buddy.MyApplication;
import com.example.administrator.buddy.bean.DeviceHolderResult;
import com.example.administrator.buddy.bean.HabitBean;
import com.example.administrator.buddy.bean.NetworkResult;
import com.example.administrator.buddy.exception.CustomException;
import com.example.administrator.buddy.network.IHttpAPI;
import com.example.administrator.buddy.utils.JsonUtils;
import com.example.administrator.buddy.utils.SharedPreUser;
import java.util.List;
import okhttp3.RequestBody;
import org.json.JSONObject;
import rx.Observable;
import rx.functions.Action1;

/**
 * Created by zhuj on 2017/9/15 19:59.
 */
public class SetupMdoel {
    protected List<HabitBean> mlist;


    public Observable<DeviceHolderResult> getDeviceHolderInformation(){
        IHttpAPI iHttpAPI=MyApplication.getIHttpApi();
        String deviceId =SharedPreUser.getInstance().getDeviceId();
        String userId =SharedPreUser.getInstance().getKeyUserId();
        return iHttpAPI.getDeviceHolderInfo(deviceId,userId)
                .doOnNext(new Action1<DeviceHolderResult>() {
                    @Override public void call(DeviceHolderResult deviceHolderResult) {
                    if (!deviceHolderResult.isSuccess()){
                        throw new CustomException(deviceHolderResult.getMessage());
                    }
                    }
                });
    }

    public Observable<NetworkResult> setDeviceHolderInformation(final String name, final String birthday, final String school,
            final String startSchool,final String url){
        IHttpAPI iHttpAPI = MyApplication.getIHttpApi();
        String deviceId = SharedPreUser.getInstance().getDeviceId();
        String userId=SharedPreUser.getInstance().getKeyUserId();

        JSONObject object=new JSONObject();

            try {
                object.put("name",name);
                //object.put("sim",holderBean.getSim());
                object.put("birthday",birthday);
                //object.put("gender",holderBean.getGender());
                object.put("avatar",url);
                object.put("school",school);
                object.put("startSchool",startSchool);
            }catch (Exception e){
                e.printStackTrace();
            }
        RequestBody data = JsonUtils.toRequestBody(object);
        return iHttpAPI.setDeviceHolderInfo(deviceId,userId,data)
                .doOnNext(new Action1<NetworkResult>() {
                    @Override public void call(NetworkResult deviceHolderResult) {
                        if (!deviceHolderResult.isSuccess()) {
                            throw new CustomException(deviceHolderResult.getMessage());
                        }

                    }
                });
    }
}
