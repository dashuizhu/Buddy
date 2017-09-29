package com.example.administrator.buddy.bean;

import android.os.Parcel;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by zhuj on 2017/9/5 20:31.
 */
@Data @EqualsAndHashCode(callSuper = false) public class HabitResult extends NetworkResult {
  /**
   * message : 成功
   * data : [{"habitId":14,"title":"写字","recommend":1,"state":0,"flag":0,"playState":0,"playTime":"17:30"},{"habitId":15,"title":"学英语","recommend":1,"state":0,"flag":0,"playState":0,"playTime":"20:23"}]
   * timestamp : 1499310124179
   */


  protected List<HabitBean> data;

  public List<HabitBean> getList() {
    return data;
  }

  public void setList(List<HabitBean> l) {
    this.data = l;
  }

  public int length() {
    return data.size();
  }
}
