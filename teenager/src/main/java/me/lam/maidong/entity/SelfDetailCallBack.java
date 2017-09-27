package me.lam.maidong.entity;

import java.io.Serializable;

/**
 * Created by 1 on 2015/12/10.
 */
public class SelfDetailCallBack  implements Serializable{


    /**
     * {"MyStatus":1,"StudentName":"梁晖","Age":"16","Height":"168","Weight":"84.0","Sex":2,"StatusMessage":"获取成功！"}

     */
    private int MyStatus;
    private int Sex;
    private String Height;
    private String StudentName;
    private String Age;
    private String Weight;
    private String StatusMessage;






    public static SelfDetailCallBack instance=null;
    public static SelfDetailCallBack getInstance(){
        if(instance==null){
            synchronized(SelfDetailCallBack.class){
                if(instance==null){
                    instance=new SelfDetailCallBack();
                }
            }
        }
        return instance;
    }
    public SelfDetailCallBack(){}




    public void setStatus(int Status) {
        this.MyStatus = Status;
    }

    public void setSex(int Sex) {
        this.Sex = Sex;
    }

    public void setHeight(String Height) {
        this.Height = Height;
    }

    public void setStudentName(String StudentName) {
        this.StudentName = StudentName;
    }

    public void setAge(String Age) {
        this.Age = Age;
    }

    public void setWeight(String Weight) {
        this.Weight = Weight;
    }

    public int getStatus() {
        return MyStatus;
    }

    public int getSex() {
        return Sex;
    }

    public String getHeight() {
        return Height;
    }

    public String getStudentName() {
        return StudentName;
    }

    public String getAge() {
        return Age;
    }

    public String getWeight() {
        return Weight;
    }
}
