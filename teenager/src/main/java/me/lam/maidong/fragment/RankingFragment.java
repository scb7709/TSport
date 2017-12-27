package me.lam.maidong.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

import me.lam.maidong.R;
import me.lam.maidong.entity.spvscl;
import me.lam.maidong.utils.ImageUtil;


@ContentView(R.layout.fragment_ranking)
public class RankingFragment extends Fragment {
    spvscl spcl;
   // private XAxis xAxis;
    @ViewInject(R.id.public_barchat_layout)
    private LinearLayout public_barchat_layout;
    @ViewInject(R.id.barChart_1)
    private Button barChart_1;
    @ViewInject(R.id.barChart_2)
    private Button barChart_2;
    @ViewInject(R.id.barChart_3)
    private Button barChart_3;
    int low;
    int med;
    int hight;
    @ViewInject(R.id.fragment_ranking_schoolname)
    private TextView fragment_ranking_schoolname;
    @ViewInject(R.id.fragment_ranking_vaildtime)
    private TextView fragment_ranking_vaildtime;

    @ViewInject(R.id.fragment_ranking_numberProgressbar_classnumber)
    private TextView fragment_ranking_numberProgressbar_classnumber;
    @ViewInject(R.id.fragment_ranking_numberProgressbar_schoolnumber)
    private TextView fragment_ranking_numberProgressbar_schoolnumber;
    @ViewInject(R.id.fragment_ranking_numberProgressbar_agenumber)
    private TextView fragment_ranking_numberProgressbar_agenumber;

    int[] color = {

            -1837057,
            -3740935,
            -7678223
    };

    public RankingFragment() {

    }

    @SuppressLint("ValidFragment")
    public RankingFragment(spvscl spcl) {
        this.spcl = spcl;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // View view = inflater.inflate(R.layout.fragment_mai_dong, null);
        return x.view().inject(this, inflater, container);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        spvscl.DataEntity.DayRanking dayRanking = spcl.getData().DayRanking;
        fragment_ranking_numberProgressbar_classnumber.setText(dayRanking.ClassRanking + "%");
        fragment_ranking_numberProgressbar_schoolnumber.setText(dayRanking.SchoolRanking + "%");
        fragment_ranking_numberProgressbar_agenumber.setText(dayRanking.SameAgeRanking + "%");

        fragment_ranking_schoolname.setText(dayRanking.SchoolClassName);
        ;
        fragment_ranking_vaildtime.setText(dayRanking.EffectTime);
        ;
       // BarChatIaitialize();
        low = dayRanking.SchoolRanking.equals("") ? 0 : Integer.parseInt(dayRanking.SchoolRanking);
        med = dayRanking.ClassRanking.equals("") ? 0 : Integer.parseInt(dayRanking.ClassRanking);
        hight =dayRanking.SameAgeRanking.equals("") ? 0 : Integer.parseInt(dayRanking.SameAgeRanking);
            BarChatSet();

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
            RelativeLayout.LayoutParams linearParams1 = (RelativeLayout.LayoutParams) barChart_1.getLayoutParams();
            RelativeLayout.LayoutParams linearParams2 = (RelativeLayout.LayoutParams) barChart_2.getLayoutParams();
            RelativeLayout.LayoutParams linearParams3 = (RelativeLayout.LayoutParams) barChart_3.getLayoutParams();
            linearParams1.height =public_barchat_layout_hight * low/100;
            linearParams2.height = public_barchat_layout_hight * med/100;
            linearParams3.height = public_barchat_layout_hight * hight/100;
            barChart_1.setLayoutParams(linearParams1);
            barChart_2.setLayoutParams(linearParams2);
            barChart_3.setLayoutParams(linearParams3);
            barChart_1.setBackgroundColor(color[0]);
            barChart_2.setBackgroundColor(color[1]);
            barChart_3.setBackgroundColor(color[2]);
        } else {
            handler.sendEmptyMessageDelayed(0,1);
        }


     /*   ArrayList<String> xValues = new ArrayList<String>();
        xValues.add("");
        xValues.add("");
        xValues.add("");
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//数据位于底部
        ArrayList<BarEntry> yValues = new ArrayList<BarEntry>();
        //new BarEntry(20, 0)前面代表数据，后面代码柱状图的位置；
        yValues.add(new BarEntry(low, 0));
        yValues.add(new BarEntry(med, 1));
        yValues.add(new BarEntry(hight, 2));
        //5、设置柱状图不显示数据
        BarDataSet barDataSet = new BarDataSet(yValues, "");
        barDataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float v) {
                // int n = (int) v;
                return "";
            }
        });
        //6、设置柱状图的颜色
        barDataSet.setColors(new int[]{color[0], color[1], color[2]});
        //7、显示，柱状图的宽度和动画效果

        BarData barData = new BarData(xValues, barDataSet);
        barDataSet.setBarSpacePercent(40f);//值越大，柱状图就越宽度越小；
        fragment_ranking_barChart.animateY(1500);
        fragment_ranking_barChart.setData(barData); //*/

    }
/*
    private void BarChatIaitialize() {

        //1、基本设置
        xAxis = fragment_ranking_barChart.getXAxis();
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(false);
        fragment_ranking_barChart.setDrawGridBackground(false); // 是否显示表格颜色
        fragment_ranking_barChart.getAxisLeft().setDrawAxisLine(false);
        fragment_ranking_barChart.setTouchEnabled(false); // 设置是否可以触摸
        fragment_ranking_barChart.setDragEnabled(false);// 是否可以拖拽
        fragment_ranking_barChart.setScaleEnabled(false);// 是否可以缩放
        //2、y轴和比例尺

        fragment_ranking_barChart.setDescription("");// 数据描述

        fragment_ranking_barChart.getAxisLeft().setEnabled(false);
        fragment_ranking_barChart.getAxisRight().setEnabled(false);

        Legend legend = fragment_ranking_barChart.getLegend();//隐藏比例尺
        legend.setEnabled(false);

        //3、x轴数据,和显示位置

    }*/
}
