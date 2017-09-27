package me.lam.maidong.utils;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DataString {
    private static String mYear;  
    private static String mMonth;  
    private static String mDay;  
    private static String mWay;  
      
    public static String StringData(){  
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        mYear = String.valueOf(c.get(Calendar.YEAR)); // 获取当前年份  
        mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份  
        mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前月份的日期号码  
        mWay = String.valueOf(c.get(Calendar.DAY_OF_WEEK));
      /*  if("1".equals(mWay)){
            mWay ="天";
        }else if("2".equals(mWay)){
            mWay ="一";
        }else if("3".equals(mWay)){
            mWay ="二";
        }else if("4".equals(mWay)){
            mWay ="三";
        }else if("5".equals(mWay)){
            mWay ="四";
        }else if("6".equals(mWay)){
            mWay ="五";
        }else if("7".equals(mWay)){
            mWay ="六";
        }*/
        return mWay;
    }
    public static int isToDayDate(String goalDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String today = format.format(new Date());
        Log.i("jjjjjjjjjjjjjjjj", goalDate + "  " + today + "  " + today.substring(0, 4) + " " + goalDate.substring(0, 4) + " " + goalDate.length() + "  " + today.length());

        int year = Integer.parseInt(today.substring(0, 4)) - Integer.parseInt(goalDate.substring(0, 4));
        int month = Integer.parseInt(today.substring(5, 7)) - Integer.parseInt(goalDate.substring(5, 7));
        int day = Integer.parseInt(today.substring(8, 10)) - Integer.parseInt(goalDate.substring(8, 10));
        if (year < 0) {
            return 1;
        } else if (year > 0) {
            return -1;
        } else {
            if (month < 0) {
                return 1;
            } else if (month > 0) {
                return -1;
            } else {
                if (day < 0) {
                    return 1;
                } else if (day > 0) {
                    return -1;
                } else {
                    return 0;
                }
            }
        }
    }
      
}