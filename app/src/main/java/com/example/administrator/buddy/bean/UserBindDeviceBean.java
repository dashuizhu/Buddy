package com.example.administrator.buddy.bean;

import android.os.Parcel;
import android.os.Parcelable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by zhuj on 2017/9/28 21:34.
 */
@Data @EqualsAndHashCode(callSuper = false)
public class UserBindDeviceBean implements Parcelable {

    /**
     * {
     * "userName":"妈妈",
     * "avatar":"1",
     * "relation":1,
     * "timeZone":"Asia/Shanghai",
     * "holder":{
     * "name":"foo",
     * "sim":"13800000001"
     * }
     * }
     */

    private String userName;
    private String avatar;
    private int relation;
    private String timeZone;
    /**
     * name : foo
     * sim : 13800000001
     */

    private HolderBean holder;

    public UserBindDeviceBean(){}

    @Data
    @EqualsAndHashCode(callSuper = false)
    public static class HolderBean implements Parcelable {
        private String name;
        private String sim;

        public HolderBean() {
        }
        @Override public int describeContents() {
            return 0;
        }

        @Override public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.name);
            dest.writeString(this.sim);
        }

        protected HolderBean(Parcel in) {
            this.name = in.readString();
            this.sim = in.readString();
        }

        public static final Creator<HolderBean> CREATOR = new Creator<HolderBean>() {
            @Override public HolderBean createFromParcel(Parcel source) {
                return new HolderBean(source);
            }

            @Override public HolderBean[] newArray(int size) {
                return new HolderBean[size];
            }
        };
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.userName);
        dest.writeString(this.avatar);
        dest.writeInt(this.relation);
        dest.writeString(this.timeZone);
        dest.writeParcelable(this.holder, flags);
    }

    protected UserBindDeviceBean(Parcel in) {
        this.userName = in.readString();
        this.avatar = in.readString();
        this.relation = in.readInt();
        this.timeZone = in.readString();
        this.holder = in.readParcelable(HolderBean.class.getClassLoader());
    }

    public static final Creator<UserBindDeviceBean> CREATOR = new Creator<UserBindDeviceBean>() {
        @Override public UserBindDeviceBean createFromParcel(Parcel source) {
            return new UserBindDeviceBean(source);
        }

        @Override public UserBindDeviceBean[] newArray(int size) {
            return new UserBindDeviceBean[size];
        }
    };
}
