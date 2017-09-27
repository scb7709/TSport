package me.lam.maidong.entity;

/**
 * Created by 1 on 2015/12/4.
 */
public class isResged {


    /**
     * MyStatus : 0
     * StatusMessage : 该用户名已被注册！
     */
    private int MyStatus;
    private String StatusMessage;

    public void setMyStatus(int MyStatus) {
        this.MyStatus = MyStatus;
    }

    public void setStatusMessage(String StatusMessage) {
        this.StatusMessage = StatusMessage;
    }

    public int getMyStatus() {
        return MyStatus;
    }

    public String getStatusMessage() {
        return StatusMessage;
    }
}
