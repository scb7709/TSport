package me.lam.maidong.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/*import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ValueFormatter;*/

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
import me.lam.maidong.myview.HistogramButton;
import me.lam.maidong.myview.MyToash;
import me.lam.maidong.myview.NoPreloadViewPager;
import me.lam.maidong.utils.Constant;
import tech.linjiang.suitlines.SuitLines;
import tech.linjiang.suitlines.Unit;

@ContentView(R.layout.fragment_mai_dong_today)
public class MaiDongTodayFragment extends Fragment {


    @ViewInject(R.id.fragment_maidong_heartrate_time)
    LinearLayout fragment_maidong_heartrate_time;

    @ViewInject(R.id.fragment_maidong_heartrate_time_fiveman_layout)
    LinearLayout fragment_maidong_heartrate_time_fiveman_layout;
    @ViewInject(R.id.fragment_maidong_heartrate_time_bar_layout)
    LinearLayout fragment_maidong_heartrate_time_bar_layout;


    @ViewInject(R.id.public_barchat_layout)
    private LinearLayout public_barchat_layout;
    @ViewInject(R.id.barChart_1)
    private Button barChart_1;
    @ViewInject(R.id.barChart_2)
    private Button barChart_2;
    @ViewInject(R.id.barChart_3)
    private Button barChart_3;


    float low;
    float med;
    float hight;
    List<Button> BarChartList;
    int K = 20;
   // private float[] innerRadii = {K, K, K, K, 0, 0, 0, 0};//内矩形 圆角半径
   // private RoundRectShape roundRectShape;
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


    @ViewInject(R.id.fragment_maidong_shanshidown)
    LinearLayout shanshidown;
    @ViewInject(R.id.fragment_maidong_pingjia)
    LinearLayout pingjia;
    @ViewInject(R.id.fragment_maidong_yundongpingjia)
    TextView yundongpingjia;

    //心率
 //   List<String> times = new ArrayList<String>();
  //  List<dataResualtCallBack.DailySportEntity.HeartRateTableEntity> heartRateTableEntityList;


/*    @ViewInject(R.id.drawline)
    private LineChartView lineChart;*/

    @ViewInject(R.id.fragment_maidong_roundProgressBar2)
    private RoundProgressBar mRoundProgressBar2;
    private int progress;
    int sleep;
    //低中高
    //每次点击按钮变换数据

    int i = 0;

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

    String SuggestEnergyCar;
    DecimalFormat df = new DecimalFormat("######0.0");

    @Override
    public void onPause() {
        super.onPause();
        ISOVER = true;
    }

    @SuppressLint("ValidFragment")
    public MaiDongTodayFragment(dataResualtCallBack.DailySportEntity dailySportEntity, int size, int location, NoPreloadViewPager vPager) {
        Log.i("myblue", dailySportEntity.MessageInfo);
        this.location = location;
        this.size = size;
        this.dailySportEntity = dailySportEntity;
        this.vPager = vPager;
    }

    public MaiDongTodayFragment() {
    }


    View view;

    int[] color = {
            0,
            -1118482,
            -3740935,
            -12401673
    };
/*    int[] manid = {
            0,
            R.mipmap.icon_none,
            R.mipmap.icon_sit,
            R.mipmap.icon_strenuous_exercise
    };*/


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
        Log.i("DailySportsize4", size + "  " + location);
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
      //  roundRectShape = new RoundRectShape(innerRadii, null, null); //无内矩形
        BarChartList = new ArrayList<>();
        BarChartList.add(barChart_1);
        BarChartList.add(barChart_2);
        BarChartList.add(barChart_3);
        ISOVER = false;
        //int size=DailySport.size();
        Log.i("myblue", size + "   " + location + " " + dailySportEntity.MessageInfo);
        if (location < 0 || location >= size) {
            return;
        }
        if (dailySportEntity == null) {
            return;
        }

        setTitle(location, size);
        int allTime = dailySportEntity.TotalTime / 60;
        int youxiao = dailySportEntity.ValidTime;
        allTime44 = (allTime + "") + "'" + dailySportEntity.TotalTime % 60 + "''";
        TotalTime.setText(allTime44);
        //  fragment_maidong_ValidRate.setText(dailySportEntity.ValidRate);
        AvgHeartRate.setText(dailySportEntity.AvgHeartRate.equals("") ? "0" : dailySportEntity.AvgHeartRate + "");
        TotalEnergyExpenditure.setText((dailySportEntity.TotalEnergyExpenditure.equals("") ? "0" : dailySportEntity.TotalEnergyExpenditure) + "");
        youxiaoyundong.setText(youxiao / 60 + "'" + (youxiao % 60) + "''");
        String totalLowRateTime = df.format(dailySportEntity.TotalLowRateTime * 100);
        String totalMediumRateTime = df.format(dailySportEntity.TotalMediumRateTime * 100);
        String totalHighRateTime = df.format(dailySportEntity.TotalHighRateTime * 100);
        TotalLowRateTime.setText(totalLowRateTime + "%");
        TotalMediumRateTime.setText(totalMediumRateTime + "%");
        TotalHighRateTime.setText(totalHighRateTime + "%");
        pingjia.setBackgroundResource(R.drawable.layout);
        //柱状图
       // Log.i("myblue", dailySportEntity.TotalLowRateTime + "   " + dailySportEntity.TotalMediumRateTime + "  " + dailySportEntity.TotalHighRateTime);

        low = dailySportEntity.TotalLowRateTime;
        med = dailySportEntity.TotalMediumRateTime;
        hight = dailySportEntity.TotalHighRateTime;
        BarChatSet();

        if (dailySportEntity.TotalTime == 0) {
            mRoundProgressBar2.setMmnun(1000);
        } else {
            mRoundProgressBar2.setMmnun(dailySportEntity.TotalTime);
        }
        if (dailySportEntity.ValidTime > 0) {
            progress = 0;
            // sleep = (int) (Math.random() * (4000 / dailySportEntity.ValidTime - 2000 / dailySportEntity.ValidTime + 1) + 2000 / dailySportEntity.ValidTime);//最多不超过4秒 最低不少于2秒
            sleep = 1;


            new Thread(new Runnable() {
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
            }).start();
        }
        SportRate.setText("等级：" + dailySportEntity.SportRate);
        SportEvaluate.setText("   " + dailySportEntity.SportEvaluate);
        SuggestEnergyCar = dailySportEntity.SuggestEnergy;
        SuggestEnergy.setText("" + SuggestEnergyCar + " 千卡");
        Fat.setText("" + dailySportEntity.Fat + " 克");
     /*
            dataResualtCallBack.DailySportEntity.SportDayInfo SportDayInfo = dailySportEntity.SportDayInfo;
     for (int j = 1; j < 4; j++) {
            View fragment_mai_dongnew_time_view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_mai_dongnew_time_view, null);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
            ImageView fragment_mai_dongnew_view_man = (ImageView) fragment_mai_dongnew_time_view.findViewById(R.id.fragment_mai_dongnew_view_man);
            View fragment_mai_dongnew_view_round = (View) fragment_mai_dongnew_time_view.findViewById(R.id.fragment_mai_dongnew_view_round);
            TextView fragment_mai_dongnew_view_time = (TextView) fragment_mai_dongnew_time_view.findViewById(R.id.fragment_mai_dongnew_view_time);
            fragment_mai_dongnew_view_man.setImageResource(manid[j]);

            OvalShape ovalShape = new OvalShape();
            ShapeDrawable drawable = new ShapeDrawable(ovalShape);
            drawable.getPaint().setColor(color[j]);
            drawable.getPaint().setStyle(Paint.Style.FILL);
            fragment_mai_dongnew_view_round.setBackground(drawable);
            String str = "";
            if (SportDayInfo.DurationList!=null&&SportDayInfo.DurationList.length > 0) {
                str = SportDayInfo.DurationList[j - 1];
            } else {
                str = "0小时0分";
            }
            fragment_mai_dongnew_view_time.setText(str);
            fragment_mai_dongnew_time_view.setLayoutParams(lp);
            fragment_maidong_heartrate_time_fiveman_layout.addView(fragment_mai_dongnew_time_view);
        }
        if (false||SportDayInfo.IntermitDurations != null && SportDayInfo.IntermitDurations.length != 0) {
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
        }*/
    }

    private void setTitle(int location, int size) {
        activity_main_title_center.setText("" + dailySportEntity.MessageInfo);
        activity_main_title_right.setText(getChinese(location + 1, size));
        activity_main_title_left.setText("");
        fragment_maidong_jian.setVisibility(View.INVISIBLE);
        if (size == 1) {
            fragment_maidong_jia.setVisibility(View.INVISIBLE);
        } else {
            fragment_maidong_jia.setVisibility(View.VISIBLE);
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            BarChatSet();
        }
    };

    private void BarChatSet() {
        int public_barchat_layout_hight = public_barchat_layout.getHeight();
        if (public_barchat_layout_hight != 0) {
            for (int i = 0; i < 3; i++) {
                Button button=BarChartList.get(i);
                RelativeLayout.LayoutParams linearParams = (RelativeLayout.LayoutParams) button.getLayoutParams();
                switch (i){
                    case 0:
                        linearParams.height = (int) (public_barchat_layout_hight * low);
                        break;
                    case 1:
                        linearParams.height = (int) (public_barchat_layout_hight * med);
                        break;
                    case 2:
                        linearParams.height = (int) (public_barchat_layout_hight * hight);
                        break;
                }
                button.setLayoutParams(linearParams);
            }
        } else {
            handler.sendEmptyMessageDelayed(0, 1);
        }

    }

    private String getChinese(int i, int size) {
        String str = "";
        if (!(i < 0 || i >= size || size <= 1)) {
            str = "第一次";
        }
        return str;
    }

}
