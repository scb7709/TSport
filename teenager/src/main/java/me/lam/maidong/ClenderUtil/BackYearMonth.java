package me.lam.maidong.ClenderUtil;

import java.util.List;

/**
 * Created by 1 on 2015/12/2.
 */
public class BackYearMonth {
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
