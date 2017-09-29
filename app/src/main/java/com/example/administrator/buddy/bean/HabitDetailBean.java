package com.example.administrator.buddy.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.example.administrator.buddy.MyApplication;
import com.example.administrator.buddy.R;
import com.example.administrator.buddy.utils.DateUtils;
import java.text.ParseException;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户行为习惯
 * Created by zhuj on 2017/6/8 上午10:53.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class HabitDetailBean extends HabitBean implements Parcelable {

  /**
   * subscribeId : null
   * playFlag : 1
   * repeatExpression : 0110100
   * playContent : 我发现一个奇怪的现象，妈妈总是洗碗后把洗碗巾展开晾起来，你想知道为什么吗？问问妈妈吧！
   * education : 洗碗巾晾起来的意义
   * respond : 洗碗巾晾起来的意义
   * expertVideoUrl : http://buddyweb.oss-cn-zhangjiakou.aliyuncs.com/content/20170711193951577_test.mp4
   */

  private Integer subscribeId;
  private int playFlag;
  private String repeatExpression;
  private String playContent;
  private String education;
  private String respond;
  private String expertVideoUrl;
  private int contentId;

  private Integer voiceType;
  private String voiceName;
  private String voiceUrl;

  private String educationUrl;//教育意义音频
  private String respondUrl;//回应孩子音频

  public HabitDetailBean() {}
  private String time;
  private String repeatDate;
  private String repeatDateShow;

  private boolean add;

  /**
   * 字段拆分，方便显示 和控制
   */
  public void spitPlayTime() {
    if (TextUtils.isEmpty(playTime)) {
      return;
    }
    try {
      Date date;
      switch (playFlag) {
        case AppType.REPERT_NONE:
          if (playTime.length() < 14) return;
          date = DateUtils.sdf_none.parse(playTime);
          repeatDate = DateUtils.getDateByNone(date);
          time = DateUtils.getTime(date.getTime());
          break;
        case AppType.REPERT_YEAR:
          if (playTime.length() < 7) return;
          date = DateUtils.sdf_year.parse(playTime);
          repeatDate = DateUtils.getDateByYear(date);
          time = DateUtils.getTime(date.getTime());
          break;
        case AppType.REPERT_MONTH:
          if (playTime.length() < 5) return;
          date = DateUtils.sdf_month.parse(playTime);
          repeatDate = "" + date.getDate();
          time = DateUtils.getTime(date.getTime());
          break;
        case AppType.REPERT_WEEK:
        case AppType.REPERT_EVERY_DAY:
          time = getPlayTime();
          break;
      }
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }

  /**
   * 将拆分的字段  合并
   */
  public String getPlayTimeNew() {
    if (isRepeatWeek()) {
      return time;
    } else if (isRepeatEeveryDay()) {
      return time;
    }
    //年月日， 单独处理，加上秒， 协议如此
    //if (playFlag == AppType.REPERT_NONE) {
    //  return repeatDate+" " +time;
    //}
    return repeatDate+" " + time;
  }

  private String getRepeatDate() {
    return repeatDate;
  }

  /**
   * 是否是星期循环
   * @return
   */
  public boolean isRepeatWeek() {
    return playFlag == AppType.REPERT_WEEK;
  }

  public boolean isRepeatEeveryDay() {
    return playFlag == AppType.REPERT_EVERY_DAY;
  }

  /**
   * 循环周期 显示值，
   * 显示  yyyy-mm-dd  mm-dd
   * 或者  星期、每天类型  所对应的显示值
   * @return
   */
  public String getRepeatDateShow() {
    if (repeatDate == null) {
      spitPlayTime();
    }
    if (isRepeatWeek()) {
      return DateUtils.getWeekValueString(repeatExpression);
    } else if (isRepeatEeveryDay()) {
      return MyApplication.getContext().getString(R.string.label_every_day);
    } else if (playFlag == AppType.REPERT_MONTH) {
      return "每月"+repeatDate+"日";
    }
    return repeatDate;
  }

  /**
   * 获得时间，相当于  hh:mm
   * @return
   */
  public String getTime() {
    if (time == null) {
      spitPlayTime();
    }
    return time;
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {
    dest.writeValue(this.subscribeId);
    dest.writeInt(this.playFlag);
    dest.writeString(this.repeatExpression);
    dest.writeString(this.playContent);
    dest.writeString(this.education);
    dest.writeString(this.respond);
    dest.writeString(this.expertVideoUrl);
    dest.writeInt(this.contentId);
    dest.writeValue(this.voiceType);
    dest.writeString(this.voiceName);
    dest.writeString(this.voiceUrl);
    dest.writeString(this.educationUrl);
    dest.writeString(this.respondUrl);
    dest.writeString(this.time);
    dest.writeString(this.repeatDate);
    dest.writeString(this.repeatDateShow);
    dest.writeByte(this.add ? (byte) 1 : (byte) 0);
  }

  protected HabitDetailBean(Parcel in) {
    this.subscribeId = (Integer) in.readValue(Integer.class.getClassLoader());
    this.playFlag = in.readInt();
    this.repeatExpression = in.readString();
    this.playContent = in.readString();
    this.education = in.readString();
    this.respond = in.readString();
    this.expertVideoUrl = in.readString();
    this.contentId = in.readInt();
    this.voiceType = (Integer) in.readValue(Integer.class.getClassLoader());
    this.voiceName = in.readString();
    this.voiceUrl = in.readString();
    this.educationUrl = in.readString();
    this.respondUrl = in.readString();
    this.time = in.readString();
    this.repeatDate = in.readString();
    this.repeatDateShow = in.readString();
    this.add = in.readByte() != 0;
  }

  public static final Creator<HabitDetailBean> CREATOR = new Creator<HabitDetailBean>() {
    @Override public HabitDetailBean createFromParcel(Parcel source) {
      return new HabitDetailBean(source);
    }

    @Override public HabitDetailBean[] newArray(int size) {
      return new HabitDetailBean[size];
    }
  };
}
