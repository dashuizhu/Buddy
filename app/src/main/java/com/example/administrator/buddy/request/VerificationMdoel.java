package com.example.administrator.buddy.request;

import com.example.administrator.buddy.bean.NetworkResult;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zhuj on 2017/9/4 21:45.
 */
public class VerificationMdoel {
    public NetworkResult verMdoel (String accountId  ){
        String templateId = "1";
        int expiresIn = 1800;
                JSONObject json = new JSONObject();
                try {
                    json.put("accountId", accountId);
                    json.put("templateId", templateId);
                    json.put("expiresIn", expiresIn);
                    String req = new Model().requsetPost(json.toString(),
                            "http://47.92.49.151:8080/api/apps/BUDDY_API_TEST/accounts/sendVerifyCode");
                    if (req == null) {
                        return null;
                    } else {
                        JSONObject jsonobjiect =   new JSONObject(req);
                        int code = jsonobjiect.getInt("code");
                        String message = jsonobjiect.getString("message");
                        NetworkResult mess = new NetworkResult();
                        mess.setCode(code);
                        mess.setMessge(message);
                        return mess;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
    return null;}

}
