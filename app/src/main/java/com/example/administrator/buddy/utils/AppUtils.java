package com.example.administrator.buddy.utils;

import android.widget.EditText;

/**
 * @author zhuj 2017/9/28 下午1:46.
 */
public class AppUtils {

  /**
   * 输入框 光标移动到最后一个文字处
   */
  public static void initSelecton(EditText et) {
    if (et != null) {
      String str = et.getText().toString();
      if (str.length() > 0) {
        et.setSelection(str.length());
      }
    }
  }
}
