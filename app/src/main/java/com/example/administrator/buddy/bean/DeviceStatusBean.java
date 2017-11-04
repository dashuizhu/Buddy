package com.example.administrator.buddy.bean;

import android.os.Parcel;
import android.os.Parcelable;
import lombok.Data;

/**
 * 设备状态
 * @author zhu
 * @date 2017/6/15 下午4:51.
 */
@Data
public class DeviceStatusBean implements Parcelable {

  /**
   "deviceId":"357409039056637",
   "bindCode":"323b453885f5181f",
   "currentStatus": 1
   "bindStatus":1,
   "onlineStatus":0,
   "versionCode":"xxx_2503A_V0.0.1"

   */

  private String deviceId;
  private String bindCode;
  private int currentStatus;
  private int bindStaus;
  private int onlineStatus;



  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.deviceId);
    dest.writeString(this.bindCode);
    dest.writeInt(this.currentStatus);
    dest.writeInt(this.bindStaus);
    dest.writeInt(this.onlineStatus);

  }
  protected DeviceStatusBean(Parcel in) {
    this.deviceId = in.readString();
    this.currentStatus = in.readInt();
    this.bindCode = in.readString();
    this.bindStaus = in.readInt();
    this.onlineStatus = in.readInt();


  }

  public static final Creator<DeviceStatusBean> CREATOR =
          new Creator<DeviceStatusBean>() {
            @Override public DeviceStatusBean createFromParcel(Parcel source) {
              return new DeviceStatusBean(source);
            }

            @Override public DeviceStatusBean[] newArray(int size) {
              return new DeviceStatusBean[size];
            }
          };

}