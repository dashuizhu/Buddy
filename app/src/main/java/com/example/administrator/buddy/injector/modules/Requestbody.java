package com.example.administrator.buddy.injector.modules;

import okhttp3.RequestBody;
import org.json.JSONObject;

/**
 * Created by zhuj on 2017/9/20 14:49.
 */
public class Requestbody {
    public static RequestBody toRequestBody(JSONObject data) {
        RequestBody body =
                RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"),
                        data.toString());
        return body;
    }
}
