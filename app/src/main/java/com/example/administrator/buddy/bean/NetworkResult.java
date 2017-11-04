package com.example.administrator.buddy.bean;

import lombok.Data;

/**
 * Created by zhuj on 2017/9/3 12:02.
 */
@Data
public class NetworkResult {

    public final static int TAG_REFRESH = 1; //刷新
    public final static int TAG_LOADMORE = 2;//加载更多
    public final static int TAG_ADD = 3;     //添加
    public final static int TAG_MODIFY = 4;  //修改
    public final static int TAG_DELETE = 5;  //删除

    int code;
    String message;
    String time;
    String nickName;
    String avatar;
    String userId;
    String timestamp ;
    String deviceId;


    /**
     * 用来记录请求类别，是添加、修改、删除等
     * 参考 TAG_DEVICE_XXXXX
     */
    private int tag;

    public boolean isSuccess() {
        return code==0;
    }
}
