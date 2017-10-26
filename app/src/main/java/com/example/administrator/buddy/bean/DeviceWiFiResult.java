package com.example.administrator.buddy.bean;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 家庭成员
 * @author zhuj
 * @date 2017/6/15 下午4:49.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DeviceWiFiResult extends NetworkResult {

  @SerializedName("data") private List<DeviceWiFiBean> familyBeanList;

}
