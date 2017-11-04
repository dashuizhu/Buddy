package com.example.administrator.buddy.bean;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by zhuj on 2017/11/2 22:44.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserBindDeviceResult extends NetworkResult {
    @SerializedName("data") private UserBindDeviceBean familyBeanList;
}
