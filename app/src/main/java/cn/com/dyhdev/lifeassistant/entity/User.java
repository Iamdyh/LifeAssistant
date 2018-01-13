package cn.com.dyhdev.lifeassistant.entity;

import cn.bmob.v3.BmobUser;

/**
 * 项目名:     LifeAssistant
 * 包名:       cn.com.dyhdev.lifeassistant.entity
 * 文件名:     User
 * 作者:       dyh
 * 时间:       2018/1/13 10:08
 * 描述:       用户属性
 */

public class User extends BmobUser{

    private int age;          //年龄
    private boolean sex;      //性别
    private String desc;      //描述



    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
