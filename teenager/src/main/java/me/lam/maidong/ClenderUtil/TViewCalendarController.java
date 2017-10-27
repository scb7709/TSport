package me.lam.maidong.ClenderUtil;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.Toast;


import com.google.gson.Gson;


import org.xutils.http.RequestParams;



import java.util.Calendar;

import me.lam.maidong.activity.MainActivity;
import me.lam.maidong.utils.Constant;
import me.lam.maidong.utils.DataString;

import me.lam.maidong.utils.HttpUtils;
import me.lam.maidong.utils.OKHttp;
import me.lam.maidong.utils.ShareUitls;



public class TViewCalendarController extends TViewControllerBase implements CalendarView.CallBack {

    private TViewCalendar tViewCalendar;
    private ViewPager viewPager;
    private CalendarView[] views;
    private CalendarViewBuilder builder;
    private WaitDialog waitDialog;
    private String[] UNIT;
    public static String EduCode;
    public static String userId = "";
    Activity activity;

    public TViewCalendarController(Activity activity, String EduCode) {
        super(activity, EduCode);
        Log.e("log", code + "clander传递过来的？");
        userId = code;
        this.EduCode = EduCode;
        this.activity = activity;
    }

    @Override
    public void initObject() {
        initDialog();
        UNIT = new String[]{"一", "二", "三", "四", "五", "六", "七", "八", "九", "十", "十一", "十二"};
        tViewCalendar = new TViewCalendar(mActivity);
        builder = new CalendarViewBuilder();
        views = builder.createMassCalendarViews(mActivity, 5, this);
        viewPager = tViewCalendar.getViewPager();
        setViewPager();
    }

    private void initDialog() {
        waitDialog = new WaitDialog(mActivity);
        waitDialog.setMessage("请稍候...");
        waitDialog.setCancleable(true);

    }

    public TViewCalendar getTViewCalendar() {
        return tViewCalendar;
    }

    @Override
    public void requestData() {
    }

    private void showDialog(boolean isShow) {
        if (isShow) {
            waitDialog.showDailog();
        } else {
            if (waitDialog != null) {
                waitDialog.dismissDialog();
            }
        }
    }

    private void setViewPager() {
        CustomViewPagerAdapter<CalendarView> viewPagerAdapter = new CustomViewPagerAdapter<>(views);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setCurrentItem(498);
        viewPager.setOnPageChangeListener(new CalendarViewPagerLisenter(viewPagerAdapter));
    }

    private void getData(String yearmonth, int i) {
        // Log.e("llll", "getData之前的" + copy.getInstance().SSSportDay.toString() + "----" + yearmonth);

        //下面是网络请求年月中哪几天有运动数据
        Log.e("nianyue", yearmonth + "开始网络请求的年月");
      //  showDialog(true);
        getMonthData(yearmonth, i);
        Log.e("next", 2 + "?");

    }


    static Boolean hasData = false;
    static Boolean askData = false;
    String allUrl = "";

    private void getMonthData(final String yearmonth, final int i) {
        String url = "NewSportCalender/?EducationCode=" + EduCode + "&YearMonth=" + yearmonth;
        OKHttp.sendRequestRequestParams(activity, "", true, url, new OKHttp.ResponseListener() {
            @Override
            public void onResponse(String response) {
                Log.i("getAsynHttp", response.toString());
                Message msg = h.obtainMessage();
                msg.what = i;
                msg.obj = response;
                h.sendMessageDelayed(msg, 1);
            }

            @Override
            public void onErrorResponse() {
              //  Toast.makeText(getActivity(), "网络请求失败", Toast.LENGTH_SHORT).show();

                if (me.lam.maidong.utils.ShareUitls.getString(mActivity, "count", "null").equals("null")) {

                } else {
                    if (i == -1 || i == 0 || i == 1) {
                        Toast.makeText(mActivity, "获取失败=" + i, Toast.LENGTH_SHORT).show();
                        Log.e("DDD", allUrl + "");
                    }
                }
            }
        });



    }

    yearMonth logEntity;
    PreYearMonth preYearMonth;
    BackYearMonth backYearMonth;
    public Handler h = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 10) {
                if (logEntity != null && preYearMonth != null && backYearMonth != null) {
                    if (waitDialog.isShowing()) {
                        showDialog(false);
                        return;
                    }
                    Log.e("DDD", "33333");
                    showDialog(false);
                } else {
                    h.sendEmptyMessageDelayed(10, 1);
                }
            }

            Gson g = new Gson();
            ;
            if (msg.what == -1) {
                preYearMonth = g.fromJson(msg.obj.toString(),
                        PreYearMonth.class);
                if (preYearMonth.getMyStatus() == 1) {
                    copy.getInstance().SSSportDay = preYearMonth.getSportDay();
                    Log.e("llll", copy.getInstance().SSSportDay.toString() + "复制的滑动新请求的日历数据获取" + "-1" + "=====copy");
                    askData = false;
                    return;
                } else {

                }

            }
            if (msg.what == 0) {
                logEntity = g.fromJson(msg.obj.toString(), yearMonth.class);

                if (logEntity!=null&&logEntity.getMyStatus() == 1) {
                    copy1.getInstance().SSSportDay = logEntity.getSportDay();
                    askData = false;
                    return;
                } else {

                }

            }
            if (msg.what == 1) {
                backYearMonth = g.fromJson(msg.obj.toString(), BackYearMonth.class);
                if (backYearMonth.getMyStatus() == 1) {
                    copy2.getInstance().SSSportDay = backYearMonth.getSportDay();
                    askData = false;
                } else {

                }

            }

        }

    };

    Boolean has = false;
    String temp;
    String yearmonthdate;

    @Override
    public void clickDate(CustomDate date) {
        askData = true;
        Log.e("nianyue", "点击日期了" + "");
        final String yearmonth = date.year + "-" + getNum(date.month);
        yearmonthdate = yearmonth + "-" + getNum(date.day);
        int flag = DataString.isToDayDate(yearmonthdate + "");
        Log.e("rrrrr", yearmonth + "====你点了===" + yearmonthdate + "   " + flag);

        if (flag != 1) {

            ShareUitls.putString(mActivity, "LastSportDay", yearmonthdate);
            ShareUitls.putString(mActivity,"maidongflag","1");
            MainActivity.activity.finish();
            mActivity.startActivity(new Intent(mActivity, MainActivity.class));
            mActivity.finish();
            //mActivity.startActivity(new Intent(mActivity, MainActivity.class));
        } else {
            new CommonDialog(mActivity, null).setMessage("暂无运动数据").setSureText("确定");
        }
    }

    @Override
    public void initHandler() {

    }
    public int firstt = 0;

    @Override
    public void changeDate(CustomDate date) {
        Log.e("DDD", "changeDate2");
        if (me.lam.maidong.utils.ShareUitls.getString(mActivity, "count", "null").equals("null")) {
            if (firstt == 0 || firstt == 1 || firstt == 2 || firstt == 3 || firstt == 4) {
                Log.e("DDD", "changeDate2------------------" + firstt + "firstt");
                if (firstt == 4) {
                    ShareUitls.putString(mActivity, "count", "5");
                }
                firstt++;
            }
        } else {
            firstt++;
            Log.e("DDD", "changeDate()");
            logEntity = null;
            preYearMonth = null;
            backYearMonth = null;
            if (h != null) {
                h.sendEmptyMessageDelayed(10, 1);
                Log.e("DDD", "222222");
            }
            int k = 0;
            int overYear;
            String preMonth;
            int preOverYear;

            tViewCalendar.setDate(date.year + "/" + UNIT[date.month - 1] + "月");
            Log.e("ppp", "-" + (date.month - 1) + "-1");
            if ((date.month + 1) > 12) {
                overYear = date.year + 1;
            } else {
                overYear = date.year;
            }
            if ((date.month - 1) == 0) {
                preMonth = "12";
                preOverYear = date.year - 1;
                Log.e("ppp", "-preOverYear" + (date.year - 1) + "-1");
            } else {
                if (date.month - 1 == 10 || date.month - 1 == 11) {
                    preMonth = "" + (date.month - 1);
                    preOverYear = date.year;
                } else {
                    preMonth = "0" + (date.month - 1);
                    preOverYear = date.year;
                }
            }

            getData(preOverYear + "-" + preMonth, -1);
  /*  getData(-1, preOverYear + "-" + preMonth);*/
            Log.e("ppp", preOverYear + "-" + preMonth + "-1");

   /* getData(0, date.year + "-" + getNum2(date.month + 0));*/
            getData(date.year + "-" + getNum2(date.month + 0), 0);
            Log.e("ppp", date.year + "-" + getNum2(date.month + 0) + "+0");

  /*  getData(1,overYear + "-" + getNum2(date.month + 1));*/
            getData(overYear + "-" + getNum2(date.month + 1), 1);
            Log.e("ppp", overYear + "-" + getNum2(date.month + 1) + "+1");


        }


    }

    private String getNum2(int month) {
        if (month < 10) {
            return "0" + (month);
        }
        if (month > 12) {

            return "0" + (month - 12);
        }
        if (month == 0) {
            return "0" + 2;
        }
        return month + "";
    }

    private String getNum(int month) {
        if (month < 10) {
            return "0" + month;
        }
        return month + "";
    }
}
