package me.lam.maidong.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;


import me.lam.maidong.ClenderUtil.DateUtil;
import me.lam.maidong.ClenderUtil.TViewCalendar;
import me.lam.maidong.ClenderUtil.TViewCalendarController;
import me.lam.maidong.R;
import me.lam.maidong.entity.dataResualtCallBack;
import me.lam.maidong.utils.ShareUitls;


public class CalendarActivityCalendar extends CalendarBaseActivity implements View.OnClickListener{


    private TViewCalendarController calendarController;
    private TViewCalendar tViewCalendar;
    public static dataResualtCallBack getDataRes() {
        return dataRes;
    }

    public static String getEduCode() {
        return EduCode;
    }
    RelativeLayout back;
    Button recentData;
    public static String LastSportDate;
    public static String EduCode;
    public static dataResualtCallBack dataRes;
//public  static Activity activity;
    public String hanzi(int i){
        if(i==1)
        return "一月";
        if(i==2)
            return "二月";
        if(i==3)
            return "三月";
        if(i==4)
            return "四月";
        if(i==5)
            return "五月";
        if(i==6)
            return "六月";
        if(i==7)
            return "七月";
        if(i==8)
            return "八月";
        if(i==9)
            return "九月";
        if(i==10)
            return "十月";
        if(i==11)
            return "十一月";
        if(i==12)
            return "十二月";
       return  "";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EduCode= ShareUitls.getString(CalendarActivityCalendar.this, "EducationCode", "");
        LastSportDate= ShareUitls.getString(CalendarActivityCalendar.this, "LastSportDate", "");
        // 实例化对象
        calendarController = new TViewCalendarController(CalendarActivityCalendar.this,EduCode);
        tViewCalendar = calendarController.getTViewCalendar();
        // 加载视图
        ll_main.addView(tViewCalendar.getView());
        back = (RelativeLayout)tViewCalendar.getView() .findViewById(R.id.bt_back);
        recentData = (Button)tViewCalendar.getView() .findViewById(R.id.bt_recent);
        // 设置监听
        tViewCalendar.setViewOnClickListener(this);
        back.setOnClickListener(CalendarActivityCalendar.this);
        recentData.setOnClickListener(CalendarActivityCalendar.this);

        String year = DateUtil.getYear() + "/" + hanzi(DateUtil.getMonth());
        tViewCalendar.setDate(year);
    }
    long temptime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)//主要是对这个函数的复写
    {
        // TODO Auto-generated method stub

        if ((keyCode == KeyEvent.KEYCODE_BACK) && (event.getAction() == KeyEvent.ACTION_DOWN)) {
            if (System.currentTimeMillis() - temptime > 2000) // 2s内再次选择back键有效
            {
                System.out.println(Toast.LENGTH_LONG);
                Toast.makeText(this, "请再按一次返回退出", Toast.LENGTH_SHORT).show();
                temptime = System.currentTimeMillis();
            } else {
               ShareUitls.putString(getApplicationContext(), "count", "null");
                finish();
               //凡是非零都表示异常退出!0表示正常退出!
            }

            return true;

        }
        return super.onKeyDown(keyCode, event);
    }


//暂时没用
    @Override
    public void OnClick(int viewId) {
        switch (viewId) {
            case R.id.bt_back:
                break;

            default:
                break;
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_back:
                finish();
                break;
            case R.id.bt_recent:
                String recent= ShareUitls.getString(getApplicationContext(), "recent", "null");
                ShareUitls.putString(getApplicationContext(), "LastSportDay", recent);
                ShareUitls.putString(getApplicationContext(),"maidongflag","1");
                MainActivity.activity.finish();
                startActivity(new Intent(CalendarActivityCalendar.this, MainActivity.class));
                finish();

                break;
            default:
                break;
        }
    }



}
