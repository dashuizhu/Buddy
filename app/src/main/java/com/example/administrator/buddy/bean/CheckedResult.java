package com.example.administrator.buddy.bean;

import lombok.Data;

/**
 * checkBox点击 就会改变状态， 网络失败的情况下 要通知UI，实际checked值
 * @author zhuj 2017/7/1 下午5:27.
 */
@Data
public class CheckedResult {

  private Object object;
  private int position;

}
