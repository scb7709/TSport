package me.lam.maidong.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by 1 on 2015/12/7.
 */
public class dataResualtCallBack implements Serializable {


    @Override
    public String toString() {
        return "dataResualtCallBack{" +
                "StatusCode=" + StatusCode +
                ", UserID='" + UserID + '\'' +
                ", DailySport=" + DailySport +
                ", DailyMessage='" + DailyMessage + '\'' +
                ", DailyAnalysis='" + DailyAnalysis + '\'' +
                ", StatDate='" + StatDate + '\'' +
                ", StatusMessage='" + StatusMessage + '\'' +
                '}';
    }

    public int StatusCode;
    public String StatusMessage;
    public String UserID;
    public List<DailySportEntity> DailySport;
    public String DailyMessage;
    public String DailyAnalysis;
    public String StatDate;

    public class DailySportEntity implements Serializable {
        public String TotalHighSpeedTime;
        public int ValidTime;
        public String ValidRate;
        public String AvgHeartRate;
        public String MaxHeartRate;
        public String Distance;
        public String Steps;
        public String TotalMediumSpeedTime;
        public float TotalHighRateTime;
        public String TotalLowSpeedTime;
        public float TotalMediumRateTime;
        public String SportRate;
        public String MessageInfo;
        public float TotalLowRateTime;
        public String TotalEnergyExpenditure;
        public int TotalTime;
        public String Fat;
        public String SuggestEnergy;
        public String SportEvaluate;
        public List<HeartRateTableEntity> HeartRateTable;
        public SportDayInfo SportDayInfo;

        @Override
        public String toString() {
            return "DailySportEntity{" +
                    "TotalHighSpeedTime='" + TotalHighSpeedTime + '\'' +
                    ", AvgHeartRate=" + AvgHeartRate +
                    ", MaxHeartRate=" + MaxHeartRate +
                    ", TotalMediumSpeedTime='" + TotalMediumSpeedTime + '\'' +
                    ", TotalHighRateTime=" + TotalHighRateTime +
                    ", TotalLowSpeedTime='" + TotalLowSpeedTime + '\'' +
                    ", TotalMediumRateTime=" + TotalMediumRateTime +
                    ", SportRate='" + SportRate + '\'' +
                    ", MessageInfo='" + MessageInfo + '\'' +
                    ", TotalLowRateTime=" + TotalLowRateTime +
                    ", TotalEnergyExpenditure=" + TotalEnergyExpenditure +
                    ", TotalTime=" + TotalTime +
                    ", Fat=" + Fat +
                    ", SuggestEnergy=" + SuggestEnergy +
                    ", SportEvaluate='" + SportEvaluate + '\'' +
                    ", HeartRateTable=" + HeartRateTable +
                    ", SportDayInfo=" + SportDayInfo +
                    '}';
        }

        public class SportDayInfo implements Serializable {
            public String[] DurationList;
            public int[] IntermitDurations;

        }
        public class HeartRateTableEntity implements Serializable {
            @Override
            public String toString() {
                return "HeartRateTableEntity{" +
                        "Rate=" + Rate +
                        ", SportTime='" + SportTime + '\'' +
                        '}';
            }

            public int Rate;
            public String SportTime = "";

        }
    }
}
