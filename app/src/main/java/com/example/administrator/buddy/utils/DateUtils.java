package com.example.administrator.buddy.utils;

import android.text.TextUtils;
import com.example.administrator.buddy.MyApplication;
import com.example.administrator.buddy.R;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author zhuj
 * @date 2017/6/9 下午6:31.
 */
public class DateUtils {

  public static SimpleDateFormat sdf_none = new SimpleDateFormat("yyyy-MM-dd HH:mm");
  public static SimpleDateFormat sdf_year = new SimpleDateFormat("MM-dd HH:mm");
  public static SimpleDateFormat sdf_month = new SimpleDateFormat("dd HH:mm");
  public static SimpleDateFormat sdf_date_none = new SimpleDateFormat("yyyy-MM-dd");
  public static SimpleDateFormat sdf_date_year = new SimpleDateFormat("MM-dd");
  public static SimpleDateFormat sdf_time = new SimpleDateFormat("HH:mm");

  /**
   * 获得指定时期的  单月最大 日
   * @param year
   * @param month
   * @return
   */
  public static int getMaxDayByMonth(int year, int month) {
    int maxDay = 31;
    switch (month) {
      case 2:
        if (year % 100 != 0 && year % 4 == 0) {
          maxDay = 29;
        } else {
          maxDay = 28;
        }
        break;
      case 4:
      case 6:
      case 9:
      case 11:
        maxDay = 30;
        break;
    }
    return maxDay;
  }

  /**
   * 根据时间戳 ，获得HH：mm
   */
  public static String getTime(long time) {
    Date date = new Date(time);
    return String.format("%02d:%02d", date.getHours(), date.getMinutes());
  }


  /**
   * 将 hh:mm的时间 转化成 int[]{hh:mm}
   * @param time
   * @return 默认返回当前小时：分钟
   */
  public static int[] getHourAndMinute(String time) {
    int hour, minute;
    try {
      Date date = sdf_time.parse(time);
      hour = date.getHours();
      minute = date.getMinutes();
    } catch (Exception e) {
      e.printStackTrace();
      Calendar c = Calendar.getInstance();
      hour = c.get(Calendar.HOUR_OF_DAY);
      minute = c.get(Calendar.MINUTE);
    }
    int[] timeArray = new int[2];
    timeArray[0] = hour;
    timeArray[1] = minute;
    return timeArray;
  }

  ///**
  // * yyyy.MM.dd HH:mm:ss
  // * @param time hh:mm
  // * @return
  // */
  //public static String getHourAndMinuteFormat(String time) {
  //  if (isDateString(time)) {
  //
  //  }
  //  return "";
  //}
  //
  //public static boolean isDateString(String date) {
  //  Pattern p = Pattern.compile("^(?<year>\\d{2,4})[.](?<month>\\d{1,2})[.](?<day>\\d{1,2})$");
  //  Matcher m = p.matcher(date);
  //  boolean b = m.matches();
  //  return b;
  //}

  /**
   * 将星期数值转换成文字，
   * 每天、工作日、 一、三、五 类似这样
   */
  public static String getWeekValueString(String weekValue) {
    String str="";
    if (TextUtils.isEmpty(weekValue)) {
      return "";
    }
    if (weekValue.length() != 7) {
      return "";
    }
    if (weekValue.equals("1111111")) {
      str = MyApplication.getContext().getString(R.string.label_week_all);
    } else if (weekValue.equals("0111110")) {
      str = MyApplication.getContext().getString(R.string.label_week_workday);
    } else {
      StringBuffer stringBuffer = new StringBuffer();
      if (weekValue.charAt(1) == '1') {
        stringBuffer.append(MyApplication.getContext().getString(R.string.label_week_short_1));
        stringBuffer.append("、");
      }
      if (weekValue.charAt(2) == '1') {
        stringBuffer.append(MyApplication.getContext().getString(R.string.label_week_short_2));
        stringBuffer.append("、");
      }
      if (weekValue.charAt(3) == '1') {
        stringBuffer.append(MyApplication.getContext().getString(R.string.label_week_short_3));
        stringBuffer.append("、");
      }
      if (weekValue.charAt(4) == '1') {
        stringBuffer.append(MyApplication.getContext().getString(R.string.label_week_short_4));
        stringBuffer.append("、");
      }
      if (weekValue.charAt(5) == '1') {
        stringBuffer.append(MyApplication.getContext().getString(R.string.label_week_short_5));
        stringBuffer.append("、");
      }
      if (weekValue.charAt(6) == '1') {
        stringBuffer.append(MyApplication.getContext().getString(R.string.label_week_short_6));
        stringBuffer.append("、");
      }
      if (weekValue.charAt(0) == '1') {
        stringBuffer.append(MyApplication.getContext().getString(R.string.label_week_short_7));
      } else {
        if (stringBuffer.length() > 0) {
          //去除最后一个'、'
          stringBuffer.deleteCharAt(stringBuffer.length()-1);
        }
      }
      //也就是说有星期值
      if (stringBuffer.length()>0) {
         str = "星期"+stringBuffer.toString();
      }
    }
    return str;
  }

  /**
   * MM-dd hh:mm
   */
  public static String getDateFormat(long msgTime) {
    Date d = new Date(msgTime);
    return sdf_year.format(d);
  }

  public static boolean isToday(long time) {
    //因为服务器返回数据，有的时候返回的秒， 有的是毫秒
    //秒的话就是10位数
    if(time <100 * 1000 * 1000 * 1000) {
      time = time *1000;
    }
    //与今天0点0份判断，是否是当天
    Calendar calendar = Calendar.getInstance();
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    long todayTime = calendar.getTimeInMillis();
    return time >= todayTime;
  }

  public static boolean isYesterday(long time) {
    //因为服务器返回数据，有的时候返回的秒， 有的是毫秒
    //秒的话就是10位数
    if(time <100 * 1000 * 1000 * 1000) {
      time = time *1000;
    }
    //与今天0点0份判断，是否是当天
    Calendar calendarNow = Calendar.getInstance();
    Calendar cTime = Calendar.getInstance();
    cTime.setTimeInMillis(time);
    int cDay = cTime.get(Calendar.DAY_OF_YEAR);
    int nowDay = calendarNow.get(Calendar.DAY_OF_YEAR);
    return (nowDay==cDay+1);
  }

  public static String getDateByNone(Date time) {
    return sdf_date_none.format(time);
  }

  /**
   * 返回 MM-dd
   * @param date
   * @return
   */
  public static String getDateByYear(Date date) {
    return sdf_date_year.format(date);
  }

  /**
   * 显示 mm:ss ，如果超过1小时就 hh:mm:ss
   * @param maxTime
   * @return
   */
  public static String showTimeAll(int maxTime) {
    if (maxTime < 3600) {
      return String.format("%02d:%02d", maxTime/60 , maxTime%60);
    } else   {
      return String.format("%02d:%02d:%02d", maxTime/3600 , maxTime%3600 /60, maxTime%60);
    }
  }

  public static String getToday(String formatStr){
    Calendar now = Calendar.getInstance();
    int year = now.get(Calendar.YEAR);
    int month = now.get(Calendar.MONTH)+ 1;
    int day = now.get(Calendar.DAY_OF_MONTH);
    return String.format(formatStr,year,month,day);
  }

  /**
   * 一小时内  xx分钟前
   * 24小时内  xx小时前
   * 昨天
   * xx月xx日
   * @param time 时间
   * @return 显示内容
   */
  public static String getTimeShowString(long time) {
    long now = System.currentTimeMillis();
    long delayTime = now - time ;
    if (delayTime < 3600 * 1000) { //一小时内
      int a = (int) (delayTime/60000);
      if (a <1) a=1;
      return a +"分钟前";
    } else if (delayTime < 24 * 3600 * 1000) { //24小时内
      int hour  = (int) (delayTime / 3600000);
      return hour+"小时前";
    } else if (isYesterday(time)){
      return "昨天";
    } else{
      return DateUtils.getDateFormat(time);
    }
  }

}
