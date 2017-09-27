package me.lam.maidong.entity;

/**
 * Created by 1 on 2015/12/8.
 */
public class LogCallBack {
    /**
     * MyStatus : 1
     * LastSportDay : 无
     * EducationCode : 080
     * StatusMessage : 登录成功！
     */
    private int MyStatus;
    private String LastSportDay;
    private String EducationCode;
    private String StatusMessage;

    public void setMyStatus(int MyStatus) {
        this.MyStatus = MyStatus;
    }

    public void setLastSportDay(String LastSportDay) {
        this.LastSportDay = LastSportDay;
    }

    public void setEducationCode(String EducationCode) {
        this.EducationCode = EducationCode;
    }

    public void setStatusMessage(String StatusMessage) {
        this.StatusMessage = StatusMessage;
    }

    public int getMyStatus() {
        return MyStatus;
    }

    public String getLastSportDay() {
        return LastSportDay;
    }

    public String getEducationCode() {
        return EducationCode;
    }

    public String getStatusMessage() {
        return StatusMessage;
    }

}
