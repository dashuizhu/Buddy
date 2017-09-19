package com.example.administrator.buddy.bean;

import java.util.List;

/**
 * Created by zhuj on 2017/9/5 20:31.
 */
public class HabitResult extends HabitBean {
    protected List<HabitBean> mList;

    public List<HabitBean> getList() {
        return mList;
    }

    public void setList(List<HabitBean> l){
         this.mList= l;
    }
    public int length() {
        return mList.size();
    }

}
