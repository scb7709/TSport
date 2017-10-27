package me.lam.maidong.ClenderUtil;

import java.util.List;

/**
 * Created by 1 on 2015/12/2.
 */
public class PreYearMonth {
    /**
     * MyStatus : 1
     * SportDay : [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,19,0,21,22,0,0,0,0,0,28,0,0,0]
     */
  /*  static {
        new yearMonth().getSportDay().add(0);
        new yearMonth().getSportDay().add(0);
        new yearMonth().getSportDay().add(5);
        new yearMonth().getSportDay().add(5);
        new yearMonth().getSportDay().add(5);
        new yearMonth().getSportDay().add(6);
        new yearMonth().getSportDay().add(6);
        new yearMonth().getSportDay().add(6);
        new yearMonth().getSportDay().add(6);


    }*/

    private int MyStatus;
    private List<Integer> SportDay;

    public void setMyStatus(int MyStatus) {
        this.MyStatus = MyStatus;
    }

    public void setSportDay(List<Integer> SportDay) {
        this.SportDay = SportDay;
    }

    public int getMyStatus() {
        return MyStatus;
    }

    public List<Integer> getSportDay() {
        return SportDay;
    }
}
