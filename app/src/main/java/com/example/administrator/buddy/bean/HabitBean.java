package com.example.administrator.buddy.bean;

import android.os.Parcel;
import android.os.Parcelable;
import com.example.administrator.buddy.utils.DateUtils;
import lombok.Data;

/**
 * Created by zhuj on 2017/8/21 21:23.
 * 自定义实体类
 * Gen
 */
@Data
public class HabitBean implements Parcelable {
    private int habitId;
    protected String title;
    protected String playTime;
    protected int state;
    protected int playState;
    protected int code;
    protected String messge;
    protected String name;
    protected String birthday;
    protected String school;
    private int category;

    public HabitBean() {}

    public boolean isSuccess() {
        return code == 0;
    }

    public String getTimeFormat12() {
        int[] timeArray = DateUtils.getHourAndMinute(playTime);
        int showHour = timeArray[0];
        if (showHour >= 12) {
            showHour -= 12;
        }
        if (showHour == 0) showHour = 12;
        return String.format("%02d:%02d", showHour, timeArray[1]);
    }

    public String getTimeByAM() {
        int[] timeArray = DateUtils.getHourAndMinute(playTime);
        int showHour = timeArray[0];
        String str;
        if (showHour >= 12) {
            str = "PM";
        } else {
            str = "AM";
        }
        return str;
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.habitId);
        dest.writeString(this.title);
        dest.writeString(this.playTime);
        dest.writeInt(this.state);
        dest.writeInt(this.playState);
        dest.writeInt(this.code);
        dest.writeString(this.messge);
        dest.writeString(this.name);
        dest.writeString(this.birthday);
        dest.writeString(this.school);
        dest.writeInt(this.category);
    }

    protected HabitBean(Parcel in) {
        this.habitId = in.readInt();
        this.title = in.readString();
        this.playTime = in.readString();
        this.state = in.readInt();
        this.playState = in.readInt();
        this.code = in.readInt();
        this.messge = in.readString();
        this.name = in.readString();
        this.birthday = in.readString();
        this.school = in.readString();
        this.category = in.readInt();
    }

    public static final Creator<HabitBean> CREATOR = new Creator<HabitBean>() {
        @Override public HabitBean createFromParcel(Parcel source) {
            return new HabitBean(source);
        }

        @Override public HabitBean[] newArray(int size) {
            return new HabitBean[size];
        }
    };
}
