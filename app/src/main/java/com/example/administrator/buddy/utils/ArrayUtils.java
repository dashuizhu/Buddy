package com.example.administrator.buddy.utils;

import android.content.Context;
import com.example.administrator.buddy.R;

/**
 * @author zhuj 2017/7/1 下午4:30.
 */
public class ArrayUtils {


  /**
   * 获得重复类型名称
   * @param context
   * @param repeatType
   * @return
   */
  public static String getRepeatTypeName(Context context, int repeatType) {
    String[] array = context.getResources().getStringArray(R.array.habit_repeat_type);
    return array[repeatType];
  }

}
