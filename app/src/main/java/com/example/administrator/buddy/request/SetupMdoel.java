package com.example.administrator.buddy.request;

import com.example.administrator.buddy.bean.HabitBean;
import com.example.administrator.buddy.bean.HabitResult;
import com.example.administrator.buddy.bean.NetworkResult;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by zhuj on 2017/9/15 19:59.
 */
public class SetupMdoel {
    protected List<HabitBean> mlist;

    public HabitResult setupget() {
        try {
            JSONObject object = new JSONObject();
            String req = new Model().requsetGet(object.toString(),
                    "http://47.92.49.151:8080/api/devices/777777777777777/holder/profile?access_token=c2c5e568-4a15-40c1-b890-e1bcabc566a4&userId=4bc7e2383c404841b6b66b18ac1c9321");
            if (req == null) {
                return null;
            } else {
                JSONObject jsonObject = new JSONObject(req);
                int code = jsonObject.getInt("code");
                String message = jsonObject.getString("message");
                mlist = new ArrayList<>();
                if (code == 0) {
                    JSONArray data = jsonObject.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject json = data.getJSONObject(i);
                        HabitBean baby = new HabitBean();

                        String ne = json.getString("name");
                        String bi = json.getString("birthday");
                        String sc = json.getString("school");
                        baby.setName(ne);
                        baby.setBirthday(bi);
                        baby.setSchool(sc);
                        mlist.add(baby);
                    }
                    HabitResult map = new HabitResult();
                    map.setList(mlist);
                    map.setCode(code);
                    map.setMessge(message);
                    return map;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public NetworkResult setipPost(String name, String birthday, String school,
            String startSchool) {
        try {
            JSONObject object = new JSONObject();
            object.put("name", name);
            object.put("birthday", birthday);
            object.put("school", school);
            object.put("startSchool", startSchool);
            String req = new Model().requsetPost(object.toString(),
                    "http://47.92.49.151:8080/api/devices/777777777777777/holder/profile?access_token=c2c5e568-4a15-40c1-b890-e1bcabc566a4&userId=4bc7e2383c404841b6b66b18ac1c9321");
            if (req == null) {
                return null;
            } else {
                JSONObject json = new JSONObject(req);
                int code = json.getInt("code");
                String message = json.getString("message");
                String timestamp = json.getString("timestamp");
                NetworkResult map = new NetworkResult();
                map.setTimestamp(timestamp);
                map.setCode(code);
                map.setMessge(message);
                return map;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
