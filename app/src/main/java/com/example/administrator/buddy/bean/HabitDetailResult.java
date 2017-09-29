package com.example.administrator.buddy.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zhuj 2017/7/11 下午2:47.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class HabitDetailResult extends NetworkResult {
  private HabitDetailBean data;
}
