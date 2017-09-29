package com.example.administrator.buddy.bean;

import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by zhuj on 2017/9/28 21:34.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserConcernsResult extends NetworkResult {
    /**
     * deviceId : 357409039056637
     * bindCode : 323b453885f5181f
     * name : bar
     * sim : 13800000001
     * avatar : http://x.x.x.x/x.jpg
     * isAdmin : true
     */
    private List<DataBean> data;
@Data
    public static class DataBean {
        private String deviceId;
        private String bindCode;
        private String name;
        private String sim;
        private String avatar;
        private boolean isAdmin;


    }
}
