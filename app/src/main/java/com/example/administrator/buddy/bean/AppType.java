package com.example.administrator.buddy.bean;

import android.support.annotation.IntDef;
import android.support.annotation.StringDef;

/**
 * @author zhuj 2017/7/5 下午3:27.
 */
public class AppType {

  //----------------------------重复时间类型-----------------------------

  public final static int REPERT_NONE = 0;//显示年月日
  public final static int REPERT_WEEK = 1;//显示星期
  public final static int REPERT_MONTH = 2;//显示日
  public final static int REPERT_YEAR = 3;//显示月RI
  public final static int REPERT_EVERY_DAY = 4;//每天

  /**
   * 重复日期类型
   */
  @IntDef({REPERT_YEAR , REPERT_MONTH, REPERT_WEEK, REPERT_NONE, REPERT_EVERY_DAY})
  public @interface RepeatType{}


  //----------------------------设备操作命令-----------------------------
  /**
   * 监听
   */
  public final static int DEVICE_CMD_MONITOR = 0;//监听
  /**
   * 远程查找设备
   */
  public final static int DEVICE_CMD_FIND = 1;
  /**
   * 话费查询
   */
  public final static int DEVICE_CMD_BILL_QUERY = 2;
  /**
   * 流量查询
   */
  public final static int DEVICE_CMD_FLOW_QUERY = 3;
  /**
   * 设备操作cmd类型
   */
  @IntDef({DEVICE_CMD_MONITOR , DEVICE_CMD_FIND, DEVICE_CMD_BILL_QUERY, DEVICE_CMD_FLOW_QUERY })
  public @interface DeviceCmdType{}

  //-----------------OSS 文件类型---------------------
  /**
   * 语音聊天
   */
  public final static String OSS_IM = "im";
  /**
   * 头像
   */
  public final static String OSS_AVATAR = "avatar";
  /**
   * 闹钟
   */
  public final static String OSS_VIOCE = "voice";
  /**
   * 身份证
   */
  public final static String OSS_CARD = "card";
  /**
   * rear育儿圈
   */
  public final static String OSS_REAR = "rear";
  @StringDef({OSS_IM, OSS_AVATAR, OSS_VIOCE, OSS_CARD, OSS_REAR})
  public @interface OssFileType{}

  //--------------关系-----------------------------------------
  public final static int RELATION_OTHER = 0;
  public final static int RELATION_FATHER = 1;
  public final static int RELATION_MOTHER = 2;
  public final static int RELATION_GRAND_FATHER = 3;
  public final static int RELATION_GRAND_MOTHER = 4;
  public final static int RELATION_GRAND_PA = 5;
  public final static int RELATION_GRAND_MA = 6;

  @IntDef({RELATION_FATHER, RELATION_OTHER, RELATION_MOTHER,
  RELATION_GRAND_FATHER, RELATION_GRAND_MOTHER, RELATION_GRAND_PA, RELATION_GRAND_MA})
  public @interface RelationType{}


  //---------------------PUSH -----------------------------
  public final static int PUSH_CHAT = 10;       //设备发送聊天
  public final static int PUSH_SOS = 20;        //设备SOS报警
  public final static int PUSH_LOCATION = 21;   //设备手动请求位置
  public final static int PUSH_POWER = 22;      //低电量提醒
  public final static int PUSH_SAFE_AREA = 23;  //设备安全区域
  public final static int PUSH_PHONE = 24;      //设备通话提醒
  public final static int PUSH_SHUT_DOWN = 25;  //设备关机
  public final static int PUSH_SMS = 26;        //短信运营商
  public final static int PUSH_UNBIND = 30;     //解除绑定设备
  public final static int PUSH_DELETE_FLOW = 31;//删除关注着
  public final static int PUSH_FLOW = 32;       //设备关注
  public final static int PUSH_UNFLOW = 33;     //取消关注
  public final static int PUSH_ARREARS = 40;     //欠费通知
  public final static int PUSH_AUTH_SUCCESS = 50;//实名认证审核通过通知
  public final static int PUSH_AUTH_FAIL = 51;   //实名认证审核失败通知

  @IntDef({PUSH_CHAT, PUSH_SOS, PUSH_LOCATION, PUSH_POWER, PUSH_SAFE_AREA, PUSH_PHONE,
  PUSH_SHUT_DOWN, PUSH_SMS, PUSH_UNBIND, PUSH_DELETE_FLOW, PUSH_FLOW, PUSH_UNFLOW, PUSH_ARREARS
  ,PUSH_AUTH_SUCCESS ,PUSH_AUTH_FAIL})
  public @interface PushType{}
}
