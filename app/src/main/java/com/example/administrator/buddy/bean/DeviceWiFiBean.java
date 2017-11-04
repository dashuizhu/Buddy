package com.example.administrator.buddy.bean;

import android.os.Parcel;
import android.os.Parcelable;
import lombok.Data;

/**WIFI信息
 * Created by zhuj on 2017/10/23 22:28.
 */
@Data
public class DeviceWiFiBean implements Parcelable {

    /**
     * "id":1,
     * ssid : test
     * password : 123456
     */
    private Integer id;
    private String ssid;
    private String password;

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.ssid);
        dest.writeString(this.password);
    }

    public DeviceWiFiBean(){}

    protected DeviceWiFiBean(Parcel in){
        this.id=in.readInt();
        this.ssid =in.readString();
        this.password=in.readString();
    }
    public static final Creator<DeviceWiFiBean> CREATOR=new Creator<DeviceWiFiBean>() {
        @Override public DeviceWiFiBean createFromParcel(Parcel source) {
            return new DeviceWiFiBean(source);
        }

        @Override public DeviceWiFiBean[] newArray(int size) {
            return new DeviceWiFiBean[size];
        }
    };
}
