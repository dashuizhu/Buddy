package com.example.administrator.buddy.bean;

/**
 * Created by zhuj on 2017/8/21 21:23.
 * 自定义实体类
 * Gen
 */
public class HabitBean {
    protected String title ;
    protected String playTime;
    protected int state;
    protected int flag;
    protected int playState;
    protected int habitId;
    protected int code;
    protected String messge;
    protected String name;
    protected String birthday;
    protected String school;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }




    public int getHabitId() {
        return habitId;
    }

    public void setHabitId(int habitId) {
        this.habitId = habitId;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessge() {
        return messge;
    }

    public void setMessge(String messge) {
        this.messge = messge;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlayTime() {
        return playTime;
    }

    public void setPlayTime(String playTime) {
        this.playTime = playTime;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
    public int getFlag() {
        return flag;
    }
    public void setFlag(int flag) {
        this.flag = flag;
    }
    public int getPlayState() {
        return playState;
    }
    public void setPlayState(int playState) {
        this.playState = playState;
    }
    public boolean isSuccess() {
        return code==0;
    }
}
