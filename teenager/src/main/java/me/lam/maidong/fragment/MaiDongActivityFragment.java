package me.lam.maidong.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ValueFormatter;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import me.lam.maidong.R;
import me.lam.maidong.activity.WebViewActivity;
import me.lam.maidong.circle.RoundProgressBar;
import me.lam.maidong.entity.dataResualtCallBack;
import me.lam.maidong.myview.NoPreloadViewPager;
import me.lam.maidong.utils.Constant;
import tech.linjiang.suitlines.SuitLines;
import tech.linjiang.suitlines.Unit;

@ContentView(R.layout.fragment_mai_dongnew)
public class MaiDongActivityFragment extends Fragment {


    @ViewInject(R.id.fragment_maidong_heartrate_image)
    FrameLayout fragment_maidong_heartrate_image;
    @ViewInject(R.id.fragment_maidong_heartrate_time)
    LinearLayout fragment_maidong_heartrate_time;

    @ViewInject(R.id.fragment_maidong_heartrate_time_fiveman_layout)
    LinearLayout fragment_maidong_heartrate_time_fiveman_layout;
    @ViewInject(R.id.fragment_maidong_heartrate_time_bar_layout)
    LinearLayout fragment_maidong_heartrate_time_bar_layout;
    @ViewInject(R.id.fragment_maidong_barChart)
    BarChart fragment_maidong_barChart;
    private XAxis xAxis;


    @ViewInject(R.id.fragment_maidong_TotalTime)
    TextView TotalTime;
    //  @ViewInject(R.id.fragment_maidong_ValidRate)
    // TextView fragment_maidong_ValidRate;

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
    RelativeLayout fragment_maidong_jian;//zuo
    @ViewInject(R.id.fragment_maidong_jia)
    RelativeLayout fragment_maidong_jia;//you

    @ViewInject(R.id.fragment_maidong_Fat)
    TextView Fat;
    @ViewInject(R.id.fragment_maidong_SuggestEnergy)
    TextView SuggestEnergy;
    @ViewInject(R.id.fragment_maidong_SportEvaluate)
    TextView SportEvaluate;
    @ViewInject(R.id.fragment_maidong_SportRate)
    TextView SportRate;


    @ViewInject(R.id.fragment_maidong_zuigaoRate)
    TextView fragment_maidong_zuigaoRate;

    @ViewInject(R.id.fragment_maidong_steps)
    TextView fragment_maidong_steps;

    @ViewInject(R.id.fragment_maidong_averageRate)
    TextView fragment_maidong_averageRate;

    @ViewInject(R.id.fragment_maidong_mileage)
    TextView fragment_maidong_mileage;

    @ViewInject(R.id.fragment_maidong_notoday)
    LinearLayout fragment_maidong_notoday;


    @ViewInject(R.id.fragment_maidong_shanshidown)
    LinearLayout shanshidown;
    @ViewInject(R.id.fragment_maidong_pingjia)
    LinearLayout pingjia;
    @ViewInject(R.id.fragment_maidong_yundongpingjia)
    TextView yundongpingjia;

    //心率
    List<String> times = new ArrayList<String>();
    List<dataResualtCallBack.DailySportEntity.HeartRateTableEntity> heartRateTableEntityList;
    @ViewInject(R.id.t0)
    private TextView t0;
    @ViewInject(R.id.t1)
    private TextView t1;
    @ViewInject(R.id.t2)
    private TextView t2;
    @ViewInject(R.id.t3)
    private TextView t3;
    @ViewInject(R.id.t4)
    private TextView t4;
    @ViewInject(R.id.t5)
    private TextView t5;
    @ViewInject(R.id.drawline)
    private SuitLines suitLines;

/*    @ViewInject(R.id.drawline)
    private LineChartView lineChart;*/

    @ViewInject(R.id.fragment_maidong_roundProgressBar2)
    private RoundProgressBar mRoundProgressBar2;
    private int progress;
    int sleep;
    //低中高
    //每次点击按钮变换数据

    int i = 0;
    List<Integer> point2 = new ArrayList<Integer>();


    //    int num1;
//    int num2;
//    int num3;
//    String num11;
//    String num22;
//    String num33;
//
//    int allTime;
//    int roundnum = 0;
    String allTime44;
   // List<dataResualtCallBack.DailySportEntity> DailySport;

    dataResualtCallBack.DailySportEntity dailySportEntity;
    public TextView activity_main_title_left;

    public TextView activity_main_title_center;

    public TextView activity_main_title_right;
    public int location;//当前是第几次
   public int size;
    public NoPreloadViewPager vPager;
    boolean ISOVER;//由于本界面有网络请求，有延迟线程 所以设立此标记当本Fragment结束时 终止线程 和网络任务

    String  SuggestEnergyCar;
    DecimalFormat df = new java.text.DecimalFormat("######0.0");

    @Override
    public void onPause() {
        super.onPause();
        ISOVER = true;
    }

    @SuppressLint("ValidFragment")
    public MaiDongActivityFragment(dataResualtCallBack.DailySportEntity dailySportEntity, int size, int location, NoPreloadViewPager vPager) {
       Log.i("DailySportsize3", size+"  "+location);
        this.location = location;
        this.size = size;
        this.dailySportEntity = dailySportEntity;
        this.vPager = vPager;
    }
    public MaiDongActivityFragment() {
    }


    View view;

    int[] color = {
            0,
            -1118482,
            -3740935,
            -12401673
    };
    int[] manid = {
            0,
            R.mipmap.icon_none,
            R.mipmap.icon_sit,
            R.mipmap.icon_strenuous_exercise
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mai_dong, null);
        return org.xutils.x.view().inject(this, inflater, container);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
/*
        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        //获取屏幕宽高*/
        activity_main_title_center = (TextView) getActivity().findViewById(R.id.activity_main_title_center);
        activity_main_title_right = (TextView) getActivity().findViewById(R.id.activity_main_title_right);
        activity_main_title_left = (TextView) getActivity().findViewById(R.id.activity_main_title_left);
        BarChatIaitialize();
     //   Log.i("DailySportsize4", size+"  "+location);
        setData();
    }


    @Event(value = {R.id.fragment_maidong_shanshidown, R.id.fragment_maidong_jia, R.id.fragment_maidong_jian})
    private void getEvent(View view) {
        switch (view.getId()) {
            case R.id.fragment_maidong_shanshidown:
                Intent intent = new Intent();
                intent.putExtra("title", "膳食建议");
                intent.putExtra("URL", Constant.BASE + "/MDSportAPIH5/food.aspx?SuggestEnergy=" + SuggestEnergyCar);
                intent.setClass(getActivity(), WebViewActivity.class);
                startActivity(intent); //这里用
                break;
            case R.id.fragment_maidong_jia:
                h.sendEmptyMessageDelayed(102, 10);//切换数据是需要结束当前线程 所以延迟
                break;
            case R.id.fragment_maidong_jian:
                h.sendEmptyMessageDelayed(101, 10);//切换数据是需要结束当前线程 所以延迟
                break;

        }
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    public Handler h = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case 101:
                    vPager.setCurrentItem(location - 1);
                    break;
                case 102:
                    vPager.setCurrentItem(location + 1);
                    break;
                case 5:
                    //   h.sendEmptyMessageDelayed(5, 5);
                    break;
                case 2:
                    //drawHeartImage();
                    break;
            }


        }
    };


    private void setData() {
        ISOVER = false;
        //int size=DailySport.size();
        Log.i("myblue", size + "   " + location);
        if (location < 0 || location >= size) {
            return;
        }
        if(dailySportEntity==null){
            return;
        }
        setTitle(location, size);
            if (((dailySportEntity.SportDayInfo==null||dailySportEntity.SportDayInfo.DurationList.length == 0 || dailySportEntity.SportDayInfo.IntermitDurations.length == 0) && location == 0)) {
               // Log.i("myblue","1");
                return;

            }
        if (((dailySportEntity.HeartRateTable== null || dailySportEntity.HeartRateTable.size() == 0) && location != 0)) {
         //   Log.i("myblue","2");
            return;
        }
        //Log.i("myblue","3");

        int allTime = dailySportEntity.TotalTime/ 60;
        int youxiao = dailySportEntity.ValidTime;
        allTime44 = (allTime + "") + "'" + dailySportEntity.TotalTime % 60 + "''";
        TotalTime.setText(allTime44);
        //  fragment_maidong_ValidRate.setText(dailySportEntity.ValidRate);
        AvgHeartRate.setText(dailySportEntity.AvgHeartRate + "");
        TotalEnergyExpenditure.setText((dailySportEntity.TotalEnergyExpenditure.equals("") ? "0" : dailySportEntity.TotalEnergyExpenditure) + "");

        youxiaoyundong.setText(youxiao / 60 + "'" + (youxiao % 60) + "''");

    /*    MaxHeartRate.setText(dataRes.getDailySport().get(location).getMaxHeartRate());*/
     /*   AvgHeart.setText(dataRes.getDailySport().get(location).getAvgHeartRate());*/
        //;
        String totalLowRateTime =df.format(dailySportEntity.TotalLowRateTime * 100);
        String totalMediumRateTime = df.format(dailySportEntity.TotalMediumRateTime * 100 );
        String totalHighRateTime =df.format(dailySportEntity.TotalHighRateTime * 100);


        TotalLowRateTime.setText(totalLowRateTime + "%");
        TotalMediumRateTime.setText(totalMediumRateTime + "%");
        TotalHighRateTime.setText(totalHighRateTime + "%");
        pingjia.setBackgroundResource(R.drawable.layout);
        //柱状图
      //  Log.i("myblue", dailySportEntity.TotalLowRateTime + "   " + dailySportEntity.TotalMediumRateTime + "  " + dailySportEntity.TotalHighRateTime);
        if (dailySportEntity.TotalLowRateTime == 0 && dailySportEntity.TotalMediumRateTime == 0 && dailySportEntity.TotalHighRateTime == 0) {
         //   Log.i("myblue", "quanshiO");
            fragment_maidong_barChart.setVisibility(View.INVISIBLE);
        } else {
            fragment_maidong_barChart.setVisibility(View.VISIBLE);
            BarChatSet(dailySportEntity.TotalLowRateTime, dailySportEntity.TotalMediumRateTime, dailySportEntity.TotalHighRateTime);
        }

        if (dailySportEntity.TotalTime == 0) {
            mRoundProgressBar2.setMmnun(1000);
        } else {
            mRoundProgressBar2.setMmnun(dailySportEntity.TotalTime);
        }
        if (dailySportEntity.ValidTime > 0) {

            progress = 0;
           // sleep = (int) (Math.random() * (4000 / dailySportEntity.ValidTime - 2000 / dailySportEntity.ValidTime + 1) + 2000 / dailySportEntity.ValidTime);//最多不超过4秒 最低不少于2秒
            sleep=1;
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    while (progress <= dailySportEntity.ValidTime) {
                        if (ISOVER) {//界面结束
                            //return;
                        }
                        mRoundProgressBar2.setProgress(progress);
                        progress += 1;
                        try {
                            Thread.sleep(sleep);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
           /* new Thread(new Runnable() {
                @Override
                public void run() {


                }
            }).start();*/
        }
        if (location == 0) {//每天各时长运功状态
            fragment_maidong_heartrate_image.setVisibility(View.GONE);
            fragment_maidong_heartrate_time.setVisibility(View.VISIBLE);
            shanshidown.setVisibility(View.VISIBLE);

            fragment_maidong_notoday.setVisibility(View.GONE);

            SportRate.setText("等级：" + dailySportEntity.SportRate);
            SportEvaluate.setText("   " + dailySportEntity.SportEvaluate);
            SuggestEnergyCar = dailySportEntity.SuggestEnergy;
            SuggestEnergy.setText("" + SuggestEnergyCar);
            Fat.setText("" + dailySportEntity.Fat);


            dataResualtCallBack.DailySportEntity.SportDayInfo SportDayInfo = dailySportEntity.SportDayInfo;
            for (int j = 1; j < 4; j++) {
                View fragment_mai_dongnew_time_view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_mai_dongnew_time_view, null);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
                ImageView fragment_mai_dongnew_view_man = (ImageView) fragment_mai_dongnew_time_view.findViewById(R.id.fragment_mai_dongnew_view_man);
                View fragment_mai_dongnew_view_round = fragment_mai_dongnew_time_view.findViewById(R.id.fragment_mai_dongnew_view_round);
                TextView fragment_mai_dongnew_view_time = (TextView) fragment_mai_dongnew_time_view.findViewById(R.id.fragment_mai_dongnew_view_time);
                fragment_mai_dongnew_view_man.setImageResource(manid[j]);

                OvalShape ovalShape = new OvalShape();
                ShapeDrawable drawable = new ShapeDrawable(ovalShape);
                drawable.getPaint().setColor(color[j]);
                drawable.getPaint().setStyle(Paint.Style.FILL);
                fragment_mai_dongnew_view_round.setBackground(drawable);
                fragment_mai_dongnew_view_time.setText(SportDayInfo.DurationList[j - 1]);
                fragment_mai_dongnew_time_view.setLayoutParams(lp);
                fragment_maidong_heartrate_time_fiveman_layout.addView(fragment_mai_dongnew_time_view);
            }

            for (int k = 0; k < SportDayInfo.IntermitDurations.length; k++) {
                View view1 = new View(getActivity());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
                view1.setLayoutParams(lp);
                if (k == 0) {
                    float[] innerRadii = {60, 60, 0, 0, 0, 0, 60, 60};//内矩形 圆角半径
                    RoundRectShape roundRectShape = new RoundRectShape(innerRadii, null, null); //无内矩形
                    ShapeDrawable drawable = new ShapeDrawable(roundRectShape);
                    drawable.getPaint().setColor(color[SportDayInfo.IntermitDurations[k]]);
                    drawable.getPaint().setAntiAlias(true);
                    // 指定填充模式
                    drawable.getPaint().setStyle(Paint.Style.FILL);
                    // drawable.getPaint().setStyle(Paint.Style.STROKE);//描边
                    view1.setBackground(drawable);
                } else if (k == SportDayInfo.IntermitDurations.length - 1) {
                    float[] innerRadii = {0, 0, 60, 60, 60, 60, 0, 0};//内矩形 圆角半径
                    RoundRectShape roundRectShape = new RoundRectShape(innerRadii, null, null); //无内矩形
                    ShapeDrawable drawable = new ShapeDrawable(roundRectShape);
                    drawable.getPaint().setColor(color[SportDayInfo.IntermitDurations[k]]);
                    drawable.getPaint().setAntiAlias(true);
                    drawable.getPaint().setStyle(Paint.Style.FILL);
                    // drawable.getPaint().setStyle(Paint.Style.STROKE);//描边
                    view1.setBackground(drawable);
                } else {
                    view1.setBackgroundColor(color[SportDayInfo.IntermitDurations[k]]);
                }
                fragment_maidong_heartrate_time_bar_layout.addView(view1);
            }

        } else {//心率图
            fragment_maidong_heartrate_image.setVisibility(View.VISIBLE);
            fragment_maidong_heartrate_time.setVisibility(View.GONE);
            shanshidown.setVisibility(View.GONE);
            fragment_maidong_notoday.setVisibility(View.VISIBLE);
            heartRateTableEntityList = dailySportEntity.HeartRateTable;

            fragment_maidong_zuigaoRate.setText(dailySportEntity.MaxHeartRate);
            fragment_maidong_steps.setText(dailySportEntity.Steps);
            fragment_maidong_averageRate.setText(dailySportEntity.AvgHeartRate);
            fragment_maidong_mileage.setText(dailySportEntity.Distance);


            for (int t = 0; t < dailySportEntity.HeartRateTable.size(); t++) {
                //  point2.add(dataRes.getDailySport().get(i).getHeartRateTable().get(t).getRate());
                if (t == 3) {
                    times.add(dailySportEntity.HeartRateTable.get(t).SportTime);
                //    Log.i("times.get", "" + times.get(0));
                    t0.setText(times.get(0));
                    // t0.setText(times.get(0).substring(11, 16));
                }
                if (t == 8) {
                    times.add(dailySportEntity.HeartRateTable.get(t).SportTime);
                    t1.setText(times.get(1));
                    //t1.setText(times.get(1).substring(11, 16));
                }
                if (t == 13) {
                    times.add(dailySportEntity.HeartRateTable.get(t).SportTime);
                    t2.setText(times.get(2));
                    // t2.setText(times.get(2).substring(11, 16));
                }
                if (t == 18) {
                    times.add(dailySportEntity.HeartRateTable.get(t).SportTime);
                    t3.setText(times.get(3));
                    // t3.setText(times.get(3).substring(11, 16));
                }
                if (t == 23) {
                    times.add(dailySportEntity.HeartRateTable.get(t).SportTime);
                    t4.setText(times.get(4));
                    // t4.setText(times.get(4).substring(11, 16));
                }
                if (t == 28) {
                    times.add(dailySportEntity.HeartRateTable.get(t).SportTime);
                    t5.setText(times.get(5));
                    // t5.setText(times.get(4).substring(11, 16));
                }
            }

            //  initLineChart();//hello折线图
            List<Unit> lines = new ArrayList<>();
            for (int i = 0; i < heartRateTableEntityList.size(); i++) {
                lines.add(new Unit(heartRateTableEntityList.get(i).Rate - 50, ""));//suitLines 折现是从0开始的 本项目UI需求需要从50开始 所以减去50
            }
            suitLines.setLineForm(true);
            suitLines.anim();
            int[] colors = new int[2];
            colors[0] = Color.parseColor("#FF3E96");
            colors[1] = Color.parseColor("#b51225");
            suitLines.setDefaultOneLineColor(colors);
            suitLines.setLineType(SuitLines.SEGMENT);
            suitLines.feedWithAnim(lines);
        }
    }

    private void setTitle(int location,int size) {
        activity_main_title_center.setText(""+dailySportEntity.MessageInfo);
        activity_main_title_right.setText(getChinese(location + 1, size));
        activity_main_title_left.setText(getChinese(location - 1, size));

        if (size == 1) {
            fragment_maidong_jian.setVisibility(View.INVISIBLE);
            fragment_maidong_jia.setVisibility(View.INVISIBLE);
        } else {
            if (location - 1 >= 0) {
                fragment_maidong_jian.setVisibility(View.VISIBLE);

            } else {
                fragment_maidong_jian.setVisibility(View.INVISIBLE);
            }
            if (location + 1 < size) {
                fragment_maidong_jia.setVisibility(View.VISIBLE);

            } else {
                fragment_maidong_jia.setVisibility(View.INVISIBLE);
            }

        }
    }


    private void BarChatIaitialize() {

        //1、基本设置
        xAxis = fragment_maidong_barChart.getXAxis();
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(false);
        fragment_maidong_barChart.setDrawGridBackground(false); // 是否显示表格颜色
        fragment_maidong_barChart.getAxisLeft().setDrawAxisLine(false);
        fragment_maidong_barChart.setTouchEnabled(false); // 设置是否可以触摸
        fragment_maidong_barChart.setDragEnabled(true);// 是否可以拖拽
        fragment_maidong_barChart.setScaleEnabled(true);// 是否可以缩放
        //2、y轴和比例尺

        fragment_maidong_barChart.setDescription("");// 数据描述

        fragment_maidong_barChart.getAxisLeft().setEnabled(false);
        fragment_maidong_barChart.getAxisRight().setEnabled(false);

        Legend legend = fragment_maidong_barChart.getLegend();//隐藏比例尺
        legend.setEnabled(false);

        //3、x轴数据,和显示位置

    }

    private void BarChatSet(float low, float med, float hight) {
        ArrayList<String> xValues = new ArrayList<String>();
        xValues.add("");
        xValues.add("");
        xValues.add("");
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//数据位于底部
        ArrayList<BarEntry> yValues = new ArrayList<BarEntry>();
        //new BarEntry(20, 0)前面代表数据，后面代码柱状图的位置；
        yValues.add(new BarEntry(low == 0 ? 1 : low, 0));
        yValues.add(new BarEntry(med == 0 ? 1 : med, 1));
        yValues.add(new BarEntry(hight == 0 ? 1 : hight, 2));
        //5、、设置柱状图不显示数据
        BarDataSet barDataSet = new BarDataSet(yValues, "");
        barDataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float v) {
                // int n = (int) v;
                return "";
            }
        });
        //6、设置柱状图的颜色
        barDataSet.setColors(new int[]{color[1], color[2], color[3]});
        //7、显示，柱状图的宽度和动画效果
        BarData barData = new BarData(xValues, barDataSet);
        barDataSet.setBarSpacePercent(40f);//值越大，柱状图就越宽度越小；
        fragment_maidong_barChart.animateY(1000);
        fragment_maidong_barChart.setData(barData); //

    }

    private String getChinese(int i,int size) {
        String str = "";
        if (i < 0 || i >= size) {
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
    }

}
