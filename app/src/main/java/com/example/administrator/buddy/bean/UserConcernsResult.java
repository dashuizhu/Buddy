package com.example.administrator.buddy.bean;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by zhuj on 2017/9/28 21:34.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserConcernsResult extends NetworkResult {
    @SerializedName("data") private List<UserConcernsBean> familyBeanList;
}
