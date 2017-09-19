package com.example.administrator.buddy.bean;

/**
 * Created by zhuj on 2017/9/15 20:20.
 */
public class BabySetupBean  {

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

    protected String name;
    protected String birthday;
    protected String school;
}
