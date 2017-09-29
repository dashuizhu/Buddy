package com.example.administrator.buddy.exception;

import android.text.TextUtils;

/**
 * @author zhuj 2017/9/26 上午9:40.
 */
public class CustomException extends RuntimeException {

  private String message;

  public CustomException(String message) {
    this.message = message;
  }

  public String getMessage() {
    if (TextUtils.isEmpty(message)) {
      return "";
    }
    return message;
  }

}
