package me.lam.maidong.entity;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 1 on 2015/12/1.
 */
public class RegEntity {
    private  static String phone;
    private static String pwd;
    private static String sex;
    private  static String age;
    private  static  String height;
    private  static String weight;
    private  static String userId;



    private static RegEntity instance=null;
    public static List<Activity> RegActivityList=null;

    public static RegEntity getInstance(){
        if(instance==null){
            synchronized(RegEntity.class){
                if(instance==null){
                    RegActivityList=new ArrayList<>();
                    instance=new RegEntity();
                }
            }
        }
        return instance;
    }
    private RegEntity(){}

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }



}
