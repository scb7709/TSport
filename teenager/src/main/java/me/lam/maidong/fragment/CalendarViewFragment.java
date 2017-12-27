package me.lam.maidong.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;


import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import me.lam.maidong.ClenderUtil.CalendarView;
import me.lam.maidong.ClenderUtil.CustomDate;
import me.lam.maidong.ClenderUtil.WaitDialog;
import me.lam.maidong.R;
import me.lam.maidong.activity.MainActivity;
import me.lam.maidong.entity.CalendarData;
import me.lam.maidong.utils.DataString;
import me.lam.maidong.utils.OKHttp;
import me.lam.maidong.utils.ShareUitls;

/**
 * Created by abc on 2017/9/24.
 */

@ContentView(R.layout.fragment_calendarview)
public class CalendarViewFragment extends BaseFragment {
    @ViewInject(R.id.fragment_calendarview_layout)
    private LinearLayout fragment_calendarview_layout;
    private String date;
    private Calendar calendar;
    private int DayCount;
    private CalendarViewClickListener calendarViewClickListener;
    private Activity activity;
    private Gson gson;
    private static WaitDialog waitDialog;
    public String EduCode;
    public String LastSportDate;


    @SuppressLint("ValidFragment")
    public CalendarViewFragment(String date) {
        this.date = date;
    }

    @SuppressLint("ValidFragment")
    public CalendarViewFragment(Calendar calendar) {
        this.calendar = calendar;

    }

    private List<Integer> list;

    public CalendarViewFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return x.view().inject(this, inflater, container);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity = getActivity();
        waitDialog = new WaitDialog(activity);
        waitDialog.setMessage("正在获取请稍后...");

        gson = new Gson();
        list = new ArrayList<>();
        EduCode = ShareUitls.getString(activity, "EducationCode", "");
        LastSportDate = ShareUitls.getString(activity, "LastSportDate", "");
        calendarViewClickListener = new CalendarViewClickListener();
        DayCount = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        for (int i = 0; i < DayCount; i++) {
            list.add(0);
        }
        fragment_calendarview_layout.removeAllViews();
        CalendarView calendarView = new CalendarView(activity, calendar, list, calendarViewClickListener);
        fragment_calendarview_layout.addView(calendarView);
        getCalendar();


    }


    private class CalendarViewClickListener implements CalendarView.CallBack {
        @Override
        public void clickDate(CustomDate date) {
            final String yearmonth = date.year + "-" + getNum(date.month);
            String yearmonthdate = yearmonth + "-" + getNum(date.day);
            Log.i("myblue", yearmonthdate);
            if (DataString.isToDayDate(yearmonthdate + "") != 1&&((calendar.get(GregorianCalendar.MONTH)+1)==date.getMonth())) {//只能点击本月不大于今天的
                if(MainActivity.activity!=null){
                    MainActivity.activity.finish();
                }
                ShareUitls.putString(activity, "LastSportDay", yearmonthdate);
                ShareUitls.putString(activity, "CLICKDADE", yearmonthdate);
                ShareUitls.putString(activity, "maidongflag", "1");
                activity.startActivity(new Intent(activity, MainActivity.class));
                activity.finish();
            } else if(DataString.isToDayDate(yearmonthdate + "") == 1){
                Toast.makeText(activity, "暂无数据", Toast.LENGTH_SHORT).show();
            }else {

            }

        }

        @Override
        public void initHandler() {

        }

        @Override
        public void changeDate(CustomDate date) {

        }
    }


    private String getNum(int month) {
        if (month < 10) {
            return "0" + month;
        }
        return month + "";
    }


    private void getCalendar() {
        waitDialog.showDailog();
        String url = "NewSportCalender/?EducationalCode=" + EduCode + "&YearMonth=" + getdate(calendar);
        OKHttp.sendRequestRequestParams(activity, "", false, url, new OKHttp.ResponseListener() {
            @Override
            public void onResponse(String response) {
                Log.i("AAAAAATTTTTTTT", response + "  " + getdate(calendar));
                fragment_calendarview_layout.removeAllViews();
                CalendarData calendarData = gson.fromJson(response, CalendarData.class);
                list.clear();
                for (int i = 0; i < DayCount; i++) {//calendarData.getSportDay().get(i)
                  list.add(calendarData.getSportDay().get(i));
                    //  list.add(i%2);
                }
                CalendarView calendarView = new CalendarView(activity, calendar, list, calendarViewClickListener);
                fragment_calendarview_layout.addView(calendarView);
                waitDialog.dismissDialog();
            }

            @Override
            public void onErrorResponse() {

            }
        });


    }

    public String getdate(Calendar calendar) {
        SimpleDateFormat dformat = new SimpleDateFormat("yyyy/MM");
        Date dat = calendar.getTime();
        return dformat.format(dat);
    }

}
