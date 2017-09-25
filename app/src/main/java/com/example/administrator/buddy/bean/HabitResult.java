package com.example.administrator.buddy.bean;

import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by zhuj on 2017/9/5 20:31.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class HabitResult extends HabitBean {
    /**
     * message : 成功
     * data : [{"habitId":14,"title":"写字","recommend":1,"state":0,"flag":0,"playState":0,"playTime":"17:30"},{"habitId":15,"title":"学英语","recommend":1,"state":0,"flag":0,"playState":0,"playTime":"20:23"}]
     * timestamp : 1499310124179
     */

    private String message;
    private long timestamp;

    /**
     * habitId : 14
     * title : 写字
     * recommend : 1
     * state : 0
     * flag : 0
     * playState : 0
     * playTime : 17:30
     */

    //private DataBean habitdata;
    @Data
    public static class DataBean {
        private int habitId;
        private String title;
        private int recommend;
        private int state;
        private int flag;
        private int playState;
        private String playTime;

    }

    protected List<HabitBean> mList;
    private List<DataBean> data;

    public List<HabitBean> getList() {
        return mList;
    }

    public void setList(List<HabitBean> l){
        this.mList= l;
    }
    public int length() {
        return mList.size();
    }


    public List<HabitResult.DataBean> getData() {
        return data;
    }

    public void setData(List<HabitResult.DataBean> data) {
        this.data = data;
    }
}
