package com.example.administrator.buddy.bean;

import android.os.Parcel;
import android.os.Parcelable;
import lombok.Data;

/**
 * Created by zhuj on 2017/10/12 21:05.
 */
@Data
public class DeviceHolderBean implements Parcelable {

    protected String deviceId;
    protected String bindCode;
    protected String name;
    protected String sim;
    protected String avatar;
    protected boolean isAdmin;
    protected String birthday;
    protected int gender;
    protected int height;
    protected double weight;
    protected int grade;
    protected int relation;
    protected String school;
    protected String startSchool;
    public  DeviceHolderBean(){}
    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.deviceId);
        dest.writeString(this.bindCode);
        dest.writeString(this.name);
        dest.writeString(this.sim);
        dest.writeString(this.avatar);
        dest.writeString(this.birthday);
        dest.writeString(this.school);
        dest.writeString(this.startSchool);
        dest.writeInt(this.gender);
        dest.writeInt(this.height);
        dest.writeInt(this.grade);
        dest.writeInt(this.relation);
        dest.writeByte((byte) (isAdmin?1:0));//if myBoolean == true, byte == 1
        dest.writeDouble(this.weight);
    }
    protected DeviceHolderBean(Parcel in){
        this.deviceId =in.readString();
        this.bindCode =in.readString();
        this.name =in.readString();
        this.sim =in.readString();
        this.avatar =in.readString();
        this.birthday =in.readString();
        this.school =in.readString();
        this.startSchool =in.readString();
        this.gender =in.readInt();
        this.height =in.readInt();
        this.grade =in.readInt();
        this.relation =in.readInt();
        this.isAdmin =in.readByte()!=0;//myBoolean == true if byte != 0
        this.weight =in.readInt();

    }
    public static final Creator<DeviceHolderBean> CREATOR = new Creator<DeviceHolderBean>() {
        @Override public DeviceHolderBean createFromParcel(Parcel source) {
            return new DeviceHolderBean(source);
        }

        @Override public DeviceHolderBean[] newArray(int size) {
            return new DeviceHolderBean[size];
        }
    };


}
