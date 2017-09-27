package me.lam.maidong.entity;

import java.util.List;

/**
 * Created by abc on 2017/6/19.
 */
public class CalendarData {
    /**
     * StatusCode : 1
     * SportDay : [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,19,0,21,22,0,0,0,0,0,28,0,0,0]
     */


    private int StatusCode;
    private String StatusMessage;
    private List<Integer> SportDay;
    public void setMyStatus(int StatusCode) {
        this.StatusCode = StatusCode;
    }

    public void setSportDay(List<Integer> SportDay) {
        this.SportDay = SportDay;
    }

    public int getMyStatus() {
        return StatusCode;
    }

    public List<Integer> getSportDay() {
        return SportDay;
    }
}
