package com.example.administrator.buddy.bean;

import android.os.Parcel;
import android.os.Parcelable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by zhuj on 2017/9/28 21:34.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserConcernsBean implements Parcelable {

    /**
     * deviceId : 357409039056637
     * bindCode : 323b453885f5181f
     * name : bar
     * sim : 13800000001
     * avatar : http://x.x.x.x/x.jpg
     * isAdmin : true
     */

    private String deviceId;
    private String bindCode;
    private String name;
    private String sim;
    private String avatar;
    private boolean isAdmin;


    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.deviceId);
        dest.writeString(this.bindCode);
        dest.writeString(this.name);
        dest.writeString(this.sim);
        dest.writeString(this.avatar);
        dest.writeByte(this.isAdmin ? (byte) 1 : (byte) 0);
    }
    protected UserConcernsBean(Parcel in) {
        this.deviceId = in.readString();
        this.name = in.readString();
        this.bindCode = in.readString();
        this.avatar = in.readString();
        this.sim = in.readString();
        this.isAdmin = in.readByte() != 0;

    }

    public static final Creator<UserConcernsBean> CREATOR =
            new Creator<UserConcernsBean>() {
                @Override public UserConcernsBean createFromParcel(Parcel source) {
                    return new UserConcernsBean(source);
                }

                @Override public UserConcernsBean[] newArray(int size) {
                    return new UserConcernsBean[size];
                }
            };

}
