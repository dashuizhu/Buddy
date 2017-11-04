package com.example.administrator.buddy.bean;

import android.os.Parcel;
import android.os.Parcelable;
import lombok.Data;

/**
 * 家庭联系人
 * @author zhuj
 * @date 2017/6/15 下午4:51.
 */
@Data
public class DeviceContactsBean implements Parcelable {

  /**
   * id : 1
   * name : 妈妈
   * mobile : 16800000001
   * avatar : 2
   * relation : 2
   * isSos : false
   * seq : 0
   */
  private String userName;
  private Integer id;
  private String name;
  private String mobile;
  private String avatar;
  private int relation;
  private boolean isSos;
  private int seq;//电话排序索引号

  public DeviceContactsBean () {
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeValue(this.id);
    dest.writeString(this.name);
    dest.writeString(this.mobile);
    dest.writeString(this.avatar);
    dest.writeInt(this.relation);
    dest.writeByte(this.isSos ? (byte) 1 : (byte) 0);
    dest.writeInt(this.seq);
    dest.writeString(this.userName);
  }

  protected DeviceContactsBean(Parcel in) {
    this.id = (Integer) in.readValue(Integer.class.getClassLoader());
    this.name = in.readString();
    this.mobile = in.readString();
    this.avatar = in.readString();
    this.relation = in.readInt();
    this.isSos = in.readByte() != 0;
    this.seq = in.readInt();
    this.userName=in.readString();
  }

  public static final Creator<DeviceContactsBean> CREATOR =
          new Creator<DeviceContactsBean>() {
            @Override public DeviceContactsBean createFromParcel(Parcel source) {
              return new DeviceContactsBean(source);
            }

            @Override public DeviceContactsBean[] newArray(int size) {
              return new DeviceContactsBean[size];
            }
          };
}