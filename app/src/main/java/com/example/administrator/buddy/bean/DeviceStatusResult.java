package com.example.administrator.buddy.bean;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 设备状态
 * @author zhuj
 * @date 2017/6/15 下午4:49.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DeviceStatusResult extends NetworkResult {

  @SerializedName("data") private DeviceStatusBean familyBeanList;

}
