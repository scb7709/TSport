package me.lam.maidong.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import me.lam.maidong.R;
import me.lam.maidong.activity.WebViewActivity;
import me.lam.maidong.circle.RoundProgressBar;
import me.lam.maidong.column.HistogramView;
import me.lam.maidong.entity.PointVsRata;
import me.lam.maidong.entity.dataResualtCallBack;
import me.lam.maidong.line.MyView;
import me.lam.maidong.utils.OKHttp;
import me.lam.maidong.utils.ShareUitls;

@ContentView(R.layout.fragment_mai_dongnew)
public class MaiDongActivityFragmentOld extends Fragment {

/*
    @ViewInject(R.id.fragment_maidong_heartrate_image)
    RelativeLayout fragment_maidong_heartrate_image;
    @ViewInject(R.id.fragment_maidong_heartrate_time)
    LinearLayout fragment_maidong_heartrate_time;

    @ViewInject(R.id.fragment_maidong_heartrate_time_fiveman_layout)
    LinearLayout fragment_maidong_heartrate_time_fiveman_layout;
    @ViewInject(R.id.fragment_maidong_heartrate_time_bar_layout)
    LinearLayout fragment_maidong_heartrate_time_bar_layout;


    @ViewInject(R.id.fragment_maidong_TotalTime)
    TextView TotalTime;
    @ViewInject(R.id.fragment_maidong_AvgHeartRate)
    TextView AvgHeartRate;
    @ViewInject(R.id.fragment_maidong_TotalEnergyExpenditure)
    TextView TotalEnergyExpenditure;
    @ViewInject(R.id.fragment_maidong_TotalLowRateTime)
    TextView TotalLowRateTime;
    @ViewInject(R.id.fragment_maidong_TotalMediumRateTime)
    TextView TotalMediumRateTime;
    @ViewInject(R.id.fragment_maidong_TotalHighRateTime)
    TextView TotalHighRateTime;
    @ViewInject(R.id.fragment_maidong_youxiaoyundong)
    TextView youxiaoyundong;
    @ViewInject(R.id.fragment_maidong_jian)
    Button fragment_maidong_jian;//zuo
    @ViewInject(R.id.fragment_maidong_jia)
    Button fragment_maidong_jia;//you
    @ViewInject(R.id.fragment_maidong_t2)
    TextView t2;
    @ViewInject(R.id.fragment_maidong_t3)
    TextView t3;
    @ViewInject(R.id.fragment_maidong_t5)
    TextView t5;
    @ViewInject(R.id.fragment_maidong_t1)
    TextView t1;
    @ViewInject(R.id.fragment_maidong_t0)
    TextView t0;
    @ViewInject(R.id.fragment_maidong_t4)
    TextView t4;
    @ViewInject(R.id.fragment_maidong_Fat)
    TextView Fat;
    @ViewInject(R.id.fragment_maidong_SuggestEnergy)
    TextView SuggestEnergy;
    @ViewInject(R.id.fragment_maidong_SportEvaluate)
    TextView SportEvaluate;
    @ViewInject(R.id.fragment_maidong_SportRate)
    TextView SportRate;
    @ViewInject(R.id.fragment_maidong_shanshi)
    Button shanshi;


    @ViewInject(R.id.fragment_maidong_xiaohaozhifan)
    TextView xiaohaozhifan;
    @ViewInject(R.id.fragment_maidong_ldanwei)
    TextView ldanwei;
    @ViewInject(R.id.fragment_maidong_jianyisheru)
    TextView jianyisheru;
    @ViewInject(R.id.fragment_maidong_rdanwei)
    TextView rdanwei;
    @ViewInject(R.id.fragment_maidong_zuigao)
    TextView zuigao;
    @ViewInject(R.id.fragment_maidong_zuigaoyingyu)
    TextView zuigaoyingyu;
    @ViewInject(R.id.fragment_maidong_pingjun)
    TextView pingjun;
    @ViewInject(R.id.fragment_maidong_pingjunyingyu)
    TextView pingjunyingyu;


    @ViewInject(R.id.fragment_maidong_drawlineback)
    FrameLayout drawlineback;
    @ViewInject(R.id.fragment_maidong_pane)
    RelativeLayout relativeLayout;
    @ViewInject(R.id.fragment_maidong_line1)
    TextView line1;
    @ViewInject(R.id.fragment_maidong_line4)
    TextView line4;
    @ViewInject(R.id.fragment_maidong_up)
    TextView up;
    @ViewInject(R.id.fragment_maidong_down)
    TextView down;

    @ViewInject(R.id.fragment_maidong_shanshidown)
    Button shanshidown;
    @ViewInject(R.id.fragment_maidong_pingjia)
    LinearLayout pingjia;
    @ViewInject(R.id.fragment_maidong_histogram)
    private HistogramView histogramView;
    @ViewInject(R.id.fragment_maidong_yundongpingjia)
    TextView yundongpingjia;


    @ViewInject(R.id.fragment_maidong_roundProgressBar2)
    private RoundProgressBar mRoundProgressBar2;
    private int progress = 0;
    //低中高
    //每次点击按钮变换数据
    int cishu;
    int i = 0;
    List<Integer> point2 = new ArrayList<Integer>();
    List<String> times = new ArrayList<String>();

    int num1;
    int num2;
    int num3;
    String num11;
    String num22;
    String num33;
    String allTime44;
    int allTime;

    int a = 3;
    int b = 3;
    int c = 4;
    int d = 2;
    int e = 3;
    int f = 2;
    //圆柱
    //定义一个整形数组
    private int[] data = null;
    private int[] text = new int[]{0, 0, 0};


    int roundnum = 0;
    dataResualtCallBack dataRes;
    String EduCode;

    boolean ISOVER;//由于本界面有网络请求，有延迟线程 所以设立此标记当本Fragment结束时 终止线程 和网络任务


    @Override
    public void onDestroy() {
        super.onDestroy();
        ISOVER = true;
    }

    @SuppressLint("ValidFragment")
    public MaiDongActivityFragmentOld(int i) {
        Log.e("isNull", i + "iiiiii");
        this.i = i;
    }


    public MaiDongActivityFragmentOld() {
    }

    int width;
    int height;
    View view;

    Button ripaihang;
    RelativeLayout msg;
    String EducationalCode;
    String LastSportDate;


    int[] color = {
            -1118482,
            -1837057,
            -3740935,
            -7678223,
            -12401673
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mai_dong, null);
        return org.xutils.x.view().inject(this, inflater, container);
    }

    @Override
    public void onResume() {
        super.onResume();
        //  h.sendEmptyMessageDelayed(1, 1);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

       *//* for (int i = 0; i < 5; i++) {
            Log.i("color[i]" + i, "  " + color[i]);

        }*//*

        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        //获取屏幕宽高
        width = wm.getDefaultDisplay().getWidth();
        height = wm.getDefaultDisplay().getHeight();
        EducationalCode = ShareUitls.getString(getActivity(), "EducationCode", "");


        activity_main_title_center = (TextView) getActivity().findViewById(R.id.activity_main_title_center);
        activity_main_title_right = (TextView) getActivity().findViewById(R.id.activity_main_title_right);
        activity_main_title_left = (TextView) getActivity().findViewById(R.id.activity_main_title_left);


        EduCode = ShareUitls.getString(getActivity(), "EducationCode", "");
        shanshidown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getActivity(), WebViewActivity.class);
                getActivity().startActivity(intent); //这里用
            }
        });
        shanshi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setClass(getActivity(), WebViewActivity.class);
                getActivity().startActivity(intent); //这里用
            }
        });

        for (int i = 0; i < 5; i++) {
            View fragment_mai_dongnew_time_view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_mai_dongnew_time_view, null);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
            ImageView fragment_mai_dongnew_view_man = (ImageView) fragment_mai_dongnew_time_view.findViewById(R.id.fragment_mai_dongnew_view_man);
            View fragment_mai_dongnew_view_round = (View) fragment_mai_dongnew_time_view.findViewById(R.id.fragment_mai_dongnew_view_round);
            TextView fragment_mai_dongnew_view_time = (TextView) fragment_mai_dongnew_time_view.findViewById(R.id.fragment_mai_dongnew_view_time);
            fragment_mai_dongnew_view_man.setImageResource(R.mipmap.ic_launcher);

            OvalShape ovalShape = new OvalShape();
            ShapeDrawable drawable = new ShapeDrawable(ovalShape);
            drawable.getPaint().setColor(color[i]);
            drawable.getPaint().setStyle(Paint.Style.FILL);
            fragment_mai_dongnew_view_round.setBackground(drawable);
            fragment_mai_dongnew_view_time.setText(i + "小时" + i + "分");
            fragment_mai_dongnew_time_view.setLayoutParams(lp);
            fragment_maidong_heartrate_time_fiveman_layout.addView(fragment_mai_dongnew_time_view);
        }

        for (int i = 0; i < 24; i++) {
            View view1 = new View(getActivity());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
            view1.setLayoutParams(lp);
            if (i == 0) {
                float[] innerRadii = {60, 60, 0, 0, 0, 0, 60, 60};//内矩形 圆角半径
                RoundRectShape roundRectShape = new RoundRectShape(innerRadii, null, null); //无内矩形
                ShapeDrawable drawable = new ShapeDrawable(roundRectShape);
                drawable.getPaint().setColor(color[(int)(5*Math.random())]);
                drawable.getPaint().setAntiAlias(true);
                // 指定填充模式
                drawable.getPaint().setStyle(Paint.Style.FILL);
                // drawable.getPaint().setStyle(Paint.Style.STROKE);//描边

                view1.setBackground(drawable);


            } else if (i == 23) {
                float[] innerRadii = {0, 0, 60, 60, 60, 60, 0, 0};//内矩形 圆角半径
                RoundRectShape roundRectShape = new RoundRectShape(innerRadii, null, null); //无内矩形
                ShapeDrawable drawable = new ShapeDrawable(roundRectShape);
                drawable.getPaint().setColor(color[(int)(5*Math.random())]);
                drawable.getPaint().setAntiAlias(true);
                drawable.getPaint().setStyle(Paint.Style.FILL);
                // drawable.getPaint().setStyle(Paint.Style.STROKE);//描边
                view1.setBackground(drawable);
            } else {
                view1.setBackgroundColor(color[(int)(5*Math.random())]);
            }
            fragment_maidong_heartrate_time_bar_layout.addView(view1);
        }

        fragment_maidong_jian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ISOVER = true;
                h.sendEmptyMessageDelayed(101, 1000);//切换数据是需要结束当前线程 所以延迟

            }
        });
        fragment_maidong_jia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ISOVER = true;
                h.sendEmptyMessageDelayed(102, 1000);//切换数据是需要结束当前线程 所以延迟

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        activity_main_title_center.setText("迈动");
        activity_main_title_right.setText("");
        activity_main_title_left.setText("");
        // getOKHttp();
        String maidongdata = ShareUitls.getString(getActivity(), "maidongdata", "");
        String maidongflag = ShareUitls.getString(getActivity(), "maidongflag", "");
        if (!maidongflag.equals("1") && !maidongdata.equals("")) {
            setData(maidongdata, 0);
        } else {
            getOKHttp();
        }
        //
        //getRetrifitHttp();
    }

    int x;
    int y;
    int gap;
    int top;
    int bootom;
    int daohangHigh;
    public int hisgap;
    int sum = 0;
    public Handler h = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case 101:
                    setData(null, location - 1);
                    break;
                case 102:
                    setData(null, location + 1);
                    break;
                case 5:
                    h.sendEmptyMessageDelayed(5, 5);
                    break;
                case 2:
                    FrameLayout relativeLayout = (FrameLayout) getActivity().findViewById(R.id.fragment_maidong_drawlineback);
                    if (count == 1) {
                        Log.e("huidiao", "主线程开始画1");
                        dd = new draw(getActivity(), msg.arg1, msg.arg2, msg.obj);
                        relativeLayout.addView(dd);
                        count++;
                    } else {
                        Log.e("huidiao", "主线程开始画222");
                        relativeLayout.removeView(dd);
                        dd = new draw(getActivity(), msg.arg1, msg.arg2, msg.obj);
                        relativeLayout.addView(dd);
                    }
                    break;
                case 1://柱状图
                    drawHeartImage();
                    break;
            }


        }
    };

    private void drawHeartImage() {
        if (relativeLayout.getWidth() != 0 && relativeLayout.getHeight() != 0 && drawlineback.getWidth() != 0 && drawlineback.getHeight() != 0) {
            int[] location666 = new int[2];
            relativeLayout.getLocationOnScreen(location666);
            daohangHigh = location666[1];
            int[] location = new int[2];
            drawlineback.getLocationOnScreen(location);
            x = location[0];
            y = location[1];
            Log.e("zuobiao", "x:" + x + "y:" + y);
            Log.e("zuobiao", "图片各个角Left：" + drawlineback.getLeft() + "Right：" + drawlineback.getRight() + "Top：" + drawlineback.getTop() + "Bottom：" + drawlineback.getBottom());

            int[] locationup = new int[2];
            up.getLocationOnScreen(locationup);
            int xup = locationup[0];
            int yup = locationup[1];
            Log.e("zuobiao", "xup:" + xup + "yup:" + yup);
            Log.e("zuobiao", "up：" + up.getLeft() + "Right：" + up.getRight() + "Top：" + up.getTop() + "Bottom：" + up.getBottom());

            int[] locationdown = new int[2];
            down.getLocationOnScreen(locationdown);
            int xdown = locationdown[0];
            int ydwon = locationdown[1];

            Log.e("zuobiao", "down：" + down.getLeft() + "Right：" + down.getRight() + "Top：" + down.getTop() + "Bottom：" + down.getBottom());
            hisgap = down.getTop() - up.getTop();
            Log.e("hisgap", "xdown:" + xdown + "ydwon:" + ydwon + "hisgap" + hisgap);


            int[] location0 = new int[2];
            line1.getLocationOnScreen(location0);
            int x0 = location0[0];
            int y0 = location0[1];
            Log.e("zuobiao", "x1:" + x0 + "y1:" + y0);
            Log.e("zuobiao", "t1：" + line1.getLeft() + "Right：" + line1.getRight() + "Top：" + line1.getTop() + "Bottom：" + line1.getBottom());
            bootom = line1.getTop();


            int[] location2 = new int[2];
            line4.getLocationOnScreen(location2);
            int x2 = location2[0];
            int y2 = location2[1];
            Log.e("zuobiao", "x2:" + x2 + "y2:" + y2);
            Log.e("zuobiao", "t4：" + line4.getLeft() + "Right：" + line4.getRight() + "Top：" + line4.getTop() + "Bottom：" + line4.getBottom());
            gap = (line4.getTop() - line1.getTop());
            top = line4.getTop();
            //画折线
            FrameLayout ll = (FrameLayout) getActivity().findViewById(R.id.fragment_maidong_drawlineback);
            //  Log.e("haha", dataRes.getDailySport().get(i).getHeartRateTable().get(0).getRate() + "：：：：" + dataRes.getDailySport().get(i).getHeartRateTable().get(3).getRate());
            if (dataRes.getDailySport().get(i).getHeartRateTable().get(0) != null && dataRes.getDailySport().get(i).getHeartRateTable().size() == 30) {
                Log.e("gap", gap + "高度");
                ll.addView(new MyView(ll, x, y, width, (line4.getTop() - line1.getTop()), getActivity(), dataRes.getDailySport().get(i).getHeartRateTable().get(0).getRate(), dataRes.getDailySport().get(i).getHeartRateTable().get(1).getRate(), dataRes.getDailySport().get(i).getHeartRateTable().get(2).getRate(), dataRes.getDailySport().get(i).getHeartRateTable().get(3).getRate(), dataRes.getDailySport().get(i).getHeartRateTable().get(4).getRate(), dataRes.getDailySport().get(i).getHeartRateTable().get(5).getRate(), dataRes.getDailySport().get(i).getHeartRateTable().get(6).getRate(), dataRes.getDailySport().get(i).getHeartRateTable().get(7).getRate(), dataRes.getDailySport().get(i).getHeartRateTable().get(8).getRate(), dataRes.getDailySport().get(i).getHeartRateTable().get(9).getRate(), dataRes.getDailySport().get(i).getHeartRateTable().get(10).getRate(), dataRes.getDailySport().get(i).getHeartRateTable().get(11).getRate(), dataRes.getDailySport().get(i).getHeartRateTable().get(12).getRate(), dataRes.getDailySport().get(i).getHeartRateTable().get(13).getRate(), dataRes.getDailySport().get(i).getHeartRateTable().get(14).getRate(), dataRes.getDailySport().get(i).getHeartRateTable().get(15).getRate(), dataRes.getDailySport().get(i).getHeartRateTable().get(16).getRate(), dataRes.getDailySport().get(i).getHeartRateTable().get(17).getRate(), dataRes.getDailySport().get(i).getHeartRateTable().get(18).getRate(), dataRes.getDailySport().get(i).getHeartRateTable().get(19).getRate(), dataRes.getDailySport().get(i).getHeartRateTable().get(20).getRate(), dataRes.getDailySport().get(i).getHeartRateTable().get(21).getRate(), dataRes.getDailySport().get(i).getHeartRateTable().get(22).getRate(), dataRes.getDailySport().get(i).getHeartRateTable().get(23).getRate(), dataRes.getDailySport().get(i).getHeartRateTable().get(24).getRate(), dataRes.getDailySport().get(i).getHeartRateTable().get(25).getRate(), dataRes.getDailySport().get(i).getHeartRateTable().get(26).getRate(), dataRes.getDailySport().get(i).getHeartRateTable().get(27).getRate(), dataRes.getDailySport().get(i).getHeartRateTable().get(28).getRate(), dataRes.getDailySport().get(i).getHeartRateTable().get(29).getRate()));
            }
            sum = 1;
            return;
        } else {
            if (sum != 1) {
                h.sendEmptyMessageDelayed(1, 1);
            }
        }
    }


    public TextView activity_main_title_left;

    public TextView activity_main_title_center;

    public TextView activity_main_title_right;


    Boolean isnull = false;

    int max = 0;
    int location;//当前是第几次

    private void setData(String response, int i) {
        final Gson gson = new Gson();
        location = i;
        ISOVER = false;
        if (response != null) {
            dataRes = gson.fromJson(response, dataResualtCallBack.class);
        }
        if (dataRes == null) {
            return;
        }
        cishu = dataRes.getDailySport().size();

        Log.i("dataResualtCallBack1", response);

        if (dataRes.StatusCode == 0 || cishu == 0 || ((dataRes.getDailySport().get(i).getHeartRateTable() == null || dataRes.getDailySport().get(i).getHeartRateTable().size() == 0) && i != 0)) {
            return;
        }
        activity_main_title_center.setText(getChinese(i));
        activity_main_title_right.setText(getChinese(i + 1));
        activity_main_title_left.setText(getChinese(i - 1));

        if (cishu == 1) {
            fragment_maidong_jian.setVisibility(View.GONE);
            fragment_maidong_jia.setVisibility(View.GONE);
        } else {
            if (i - 1 >= 0) {
                fragment_maidong_jian.setVisibility(View.VISIBLE);

            } else {
                fragment_maidong_jian.setVisibility(View.GONE);
            }
            if (i + 1 < cishu) {
                fragment_maidong_jia.setVisibility(View.VISIBLE);

            } else {
                fragment_maidong_jia.setVisibility(View.GONE);
            }

        }


        Log.e("dataResualtCallBack2", dataRes.toString());
        // Log.e("isNull", "是不是为空" + dataRes.getDailySport().get(i).getHeartRateTable().get(0));
        dataResualtCallBack.DailySportEntity dailySportEntity=dataRes.getDailySport().get(i);
        RoundProgressBar bar = new RoundProgressBar(getActivity(), 0);
        if (dailySportEntity.getTotalTime() / 60 == 0) {
            bar.setMmnun(1000);
        } else {

            bar.setMmnun(dailySportEntity.getTotalTime() / 60);
        }

*//*
        //处理后台获取的数据
        num1 = dailySportEntity.getTotalLowRateTime() / 60;
        num2 = dailySportEntity.getTotalMediumRateTime() / 60;
        num3 = dailySportEntity.getTotalHighRateTime() / 60;

        num11 = (num1 + "") + "'" + dailySportEntity.getTotalLowRateTime() % 60 + "''";
        num22 = (num2 + "") + "'" + dailySportEntity.getTotalMediumRateTime() % 60 + "''";
        num33 = (num3 + "") + "'" + dailySportEntity.getTotalHighRateTime() % 60 + "''";
        allTime = dailySportEntity.getTotalTime() / 60;
        allTime44 = (allTime + "") + "'" + dailySportEntity.getTotalTime() % 60 + "''";
        roundnum = num2 + num3;


        int youxiao = dailySportEntity.getTotalMediumRateTime() + dailySportEntity.getTotalHighRateTime();*//*
        TotalTime.setText(allTime44);
        AvgHeartRate.setText(dailySportEntity.getAvgHeartRate() + "");
        TotalEnergyExpenditure.setText(dailySportEntity.getTotalEnergyExpenditure() + "");
      //  youxiaoyundong.setText(youxiao / 60 + "'" + (youxiao % 60) + "''");

    *//*    MaxHeartRate.setText(dataRes.getDailySport().get(i).getMaxHeartRate());*//*
     *//*   AvgHeart.setText(dataRes.getDailySport().get(i).getAvgHeartRate());*//*

        TotalLowRateTime.setText(num11);
        TotalMediumRateTime.setText(num22);
        TotalHighRateTime.setText(num33);

        pingjia.setBackgroundResource(R.drawable.layout);
        SportRate.setText("等级：" + dailySportEntity.getSportRate());
        SportEvaluate.setText("   " + dailySportEntity.getSportEvaluate());
        SuggestEnergy.setText("" + dailySportEntity.getSuggestEnergy());
        Fat.setText("" + dailySportEntity.getFat());
        ldanwei.setText("克");
        rdanwei.setText("千卡");
        zuigao.setText("最高心率");
        zuigaoyingyu.setText("Maximum");
        pingjunyingyu.setText("Average");


        //圆柱渐变
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (ISOVER) {//界面结束
                        return;
                    }
                    if (hisgap != 0) {
                        //取柱形图高度的最大值
                        Log.e("hisgap", "hisgap" + hisgap + "num1:" + num1 + "(num1*hisgap)/60" + (num1 * hisgap) / 80 + "num2:" + num2 + "(num2*hisgap)/60" + (num2 * hisgap) / 80 + "num3:" + num3 + "(num3*hisgap)/60" + (num3 * hisgap) / 80 + "--------------" + roundnum + "求余" + num11);
                        if (num1 > num2) {
                            max = num1;
                        } else {
                            max = num2;
                        }
                        if (max > num3) {
                            max = max;
                        } else {
                            max = num3;
                        }
                        Log.e("hisgap", "max" + max + "hisgap" + hisgap + "num1:" + num1 + "(num1*hisgap)/60" + (num1 * hisgap) / 80 + "num2:" + num2 + "(num2*hisgap)/60" + (num2 * hisgap) / 80 + "num3:" + num3 + "(num3*hisgap)/60" + (num3 * hisgap) / 80 + "--------------" + roundnum + "求余" + num11);

                        //获取数据为0那么就不设置高度（坟墓随意取个大数除后为0）
                        if (max == 0) {
                            max = 1000;
                        }
                        data = new int[]{(num1 * hisgap) / max, (num2 * hisgap) / max, (num3 * hisgap) / max};
                        //圆柱渐变
                        boolean a = true;
                        if (a) {
                            //传入数组跟高度限
                            histogramView.setProgress(data, hisgap);
                        }
                    } else {
                        try {
                            Thread.sleep(5);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                    }
                }

            }
        }).start();
        h.sendEmptyMessageDelayed(1, 1);//启动柱状图
        //线程控制环形改变

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (progress <= roundnum) {
                    if (ISOVER) {//界面结束
                        return;
                    }
                    mRoundProgressBar2.setProgress(progress);
                    progress += 1;
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();
        if (i == 0) {//每天各时长运功状态
            fragment_maidong_heartrate_image.setVisibility(View.GONE);
            fragment_maidong_heartrate_time.setVisibility(View.VISIBLE);


        } else {//心率图
            fragment_maidong_heartrate_image.setVisibility(View.VISIBLE);
            fragment_maidong_heartrate_time.setVisibility(View.GONE);
            for (int t = 0; t < dailySportEntity.getHeartRateTable().size(); t++) {
                //  point2.add(dataRes.getDailySport().get(i).getHeartRateTable().get(t).getRate());
                if (t == 3) {
                    times.add(dailySportEntity.getHeartRateTable().get(t).getSportTime());
                    Log.i("times.get", "" + times.get(0));
                    t0.setText(times.get(0));
                    // t0.setText(times.get(0).substring(11, 16));
                }
                if (t == 8) {
                    times.add(dailySportEntity.getHeartRateTable().get(t).getSportTime());
                    t1.setText(times.get(1));
                    //t1.setText(times.get(1).substring(11, 16));
                }
                if (t == 13) {
                    times.add(dailySportEntity.getHeartRateTable().get(t).getSportTime());
                    t2.setText(times.get(2));
                    // t2.setText(times.get(2).substring(11, 16));
                }
                if (t == 18) {
                    times.add(dailySportEntity.getHeartRateTable().get(t).getSportTime());
                    t3.setText(times.get(3));
                    // t3.setText(times.get(3).substring(11, 16));
                }
                if (t == 23) {
                    times.add(dailySportEntity.getHeartRateTable().get(t).getSportTime());
                    t4.setText(times.get(4));
                    // t4.setText(times.get(4).substring(11, 16));
                }
                if (t == 28) {
                    times.add(dailySportEntity.getHeartRateTable().get(t).getSportTime());
                    t5.setText(times.get(5));
                    // t5.setText(times.get(4).substring(11, 16));
                }
            }
            new MyView(getActivity()).setOnShow(new MyView.Back() {
                @Override
                public void Back(List<PointVsRata> poiintData) {
                    temp = poiintData;
                    Log.e("huidiao", "执行几次" + temp.size());
                    view.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, final MotionEvent event) {
                            Log.e("huidiao", "点击执行几次");
                            final int xpiont = (int) event.getX();
                            final int ypoint = (int) event.getY();
                            int extend = 0;
                            for (int i = 0; i < temp.size(); i++) {
                                if (temp.get(i).getRata() == 8) {
                                    extend = temp.get(i - 1).getRata();
                                } else {
                                    extend = temp.get(i).getRata();
                                }
                                if ((temp.get(i).getXpoint() + 8) > xpiont && xpiont > (temp.get(i).getXpoint() - 8)) {
                                    Log.e("huidiao", xpiont + "====xcur" + ypoint);
                                    Log.e("huidiao", temp.get(i).getXpoint() + "pre");
                                    Message m = h.obtainMessage();
                                    m.arg1 = temp.get(i).getXpoint();
                                    m.arg2 = 500;
                                    m.obj = extend;
                                    m.what = 2;
                                    h.sendMessage(m);
                                }
                            }

                            return false;
                        }
                    });
                }
            });
        }
    }

    List<PointVsRata> temp;
    int count;

    draw dd;

    public class draw extends View {
        int x = 0;
        int y = 0;
        int rate = 0;

        public draw(Context context, int x, int y, Object rate) {
            super(context);
            setWillNotDraw(false);
            Log.e("0000", x + "开始画了---1111" + y + "开始画了" + rate);
            this.x = x;
            this.y = y;
            this.rate = (int) rate;
        }

        @Override
        public void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            setWillNotDraw(false);
            Log.e("0000", x + "开始画了---22222" + y + "开始画了");
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.STROKE);
            paint.setAntiAlias(true);
            paint.setColor(Color.parseColor("#ffcc33"));
            paint.setStrokeWidth((float) 1.0);
            paint.setTextSize(20);
            canvas.drawText(rate + "", x - 10, 30, paint);
            paint.setStrokeWidth((float) 1.0);
            Path path = new Path();
            path.moveTo(x, 50);
            path.lineTo(x, gap - 50);
            PathEffect effects = new DashPathEffect(new float[]{5, 5, 5, 5}, 1);
            paint.setPathEffect(effects);
            canvas.drawPath(path, paint);
        }
    }

    public void replaceFragment(Fragment newFragment) {
        FragmentTransaction trasection = getFragmentManager()
                .beginTransaction();
        if (!newFragment.isAdded()) {
            try {
                // FragmentTransaction trasection =
                getFragmentManager().beginTransaction();
                trasection.replace(R.id.fragment, newFragment);
                trasection.addToBackStack(null);
                trasection.commit();
            } catch (Exception e) {
            }
        } else
            trasection.show(newFragment);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    private void getOKHttp() {
        LastSportDate = ShareUitls.getString(getActivity(), "LastSportDay", "");
        String url = "NewSportDate/?EducationalCode=" + EducationalCode + "&Date=" + LastSportDate;
        OKHttp.sendRequestRequestParams(getActivity(), "", true, url, new OKHttp.ResponseListener() {
            @Override
            public void onResponse(String response) {
                if (!ISOVER) {

                    Log.i("getAsynHttp", response.toString());
                    Message message = Message.obtain();
                    message.obj = response;
                    handler.sendMessage(message);
                }
            }

            @Override
            public void onErrorResponse() {
                Toast.makeText(getActivity(), "网络请求失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

  *//*  private void getRetrifitHttp() {

        Map<String ,String> map=new HashMap();
        map.put("EducationalCode",EducationalCode);
        map.put("Date",LastSportDate);

        RetrofitHttp.sendRequestRequestParams(getActivity(),"", map, true, new RetrofitHttp.ResponseListener() {
            @Override
            public void onResponse(String response) {

            }

            @Override
            public void onErrorResponse() {

            }
        });
    }*//*


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ShareUitls.putString(getActivity(), "maidongdata", (String) msg.obj);
            ShareUitls.putString(getActivity(), "maidongflag", "0");
            setData((String) msg.obj, 0);//

        }
    };

    private String getChinese(int i) {
        String str = "";
        if (i < 0 || i >= cishu) {

            str = "";
        } else {
            switch (i) {
                case 0:
                    str = "今天";
                    break;
                case 1:
                    str = "第一次";
                    break;
                case 2:
                    str = "第二次";
                    break;
                case 3:
                    str = "第三次";
                    break;
                case 4:
                    str = "第四次";
                    break;
                case 5:
                    str = "第五次";
                    break;
                case 6:
                    str = "第六次";
                    break;
                case 7:
                    str = "第七次";
                    break;
                case 8:
                    str = "第八次";
                    break;
                case 9:
                    str = "第九次";
                    break;
                case 10:
                    str = "第十次";
                    break;
            }
        }
        return str;
    }*/
}
