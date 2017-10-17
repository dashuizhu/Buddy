package com.example.administrator.buddy.bean;

import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by zhuj on 2017/10/12 21:06.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DeviceHolderResult extends NetworkResult{
    protected List<DeviceHolderBean> data;

    public List<DeviceHolderBean> getList() {
        return data;
    }

    public void setList(List<DeviceHolderBean> l) {
        this.data = l;
    }

    public int length() {
        return data.size();
    }
}
