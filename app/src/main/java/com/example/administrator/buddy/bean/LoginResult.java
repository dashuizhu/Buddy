package com.example.administrator.buddy.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by zhuj on 2017/9/19 22:15.
 */
@Data @EqualsAndHashCode(callSuper = false)
public class LoginResult extends NetworkResult {

    /**
     * message : 成功
     * data : {"userId":"13813800001","nickName":"foo","avatar":"http://xxx","accessToken":"0f229945-fb3b-46b1-8c11-5cb390952b4f","refreshToken":"12329945-fb3b-46b1-8c11-5cb390952321","expiresIn":3600}
     */

    /**
     * userId : 13813800001
     * nickName : foo
     * avatar : http://xxx
     * accessToken : 0f229945-fb3b-46b1-8c11-5cb390952b4f
     * refreshToken : 12329945-fb3b-46b1-8c11-5cb390952321
     * expiresIn : 3600
     */

    private DataBean data;

    @Data public static class DataBean {
        private String userId;
        private String nickName;
        private String avatar;
        private String accessToken;
        private String refreshToken;
        private int expiresIn;
    }
}
