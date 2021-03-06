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
public class DeviceContactsResult extends NetworkResult {
  // @SerializedName可以直接将json数据解析成Java对象或者集合。
  @SerializedName("data") private List<DeviceContactsBean> familyBeanList;

}
