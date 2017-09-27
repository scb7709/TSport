package me.lam.maidong.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;


import java.io.IOException;
import java.util.ArrayList;

import me.lam.maidong.R;
import me.lam.maidong.entity.newsEntity;
import me.lam.maidong.entity.spvscl;
import me.lam.maidong.welcomepage.MyFragmentPagerAdapter;


public class DongtaiFragment extends Fragment {

    private ViewPager vPager;
    ArrayList<Fragment> fragmentsList;
    QuanziFragment frag1;
    TieziFragment frag2;
    NewsActivityFragment2 frag3;
    public TextView activity_main_title_left;

    public TextView activity_main_title_center;

    public TextView activity_main_title_right;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_analize, null);
        vPager = (ViewPager) view.findViewById(R.id.vPager);

        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        activity_main_title_center = (TextView) getActivity().findViewById(R.id.activity_main_title_center);
        activity_main_title_right = (TextView) getActivity().findViewById(R.id.activity_main_title_right);
        activity_main_title_left = (TextView) getActivity().findViewById(R.id.activity_main_title_left);

        activity_main_title_center.setText("圈子");
        activity_main_title_right.setText("新帖");
        activity_main_title_left.setText("资讯");

       /* activity_main_title_left.setTextColor(Color.parseColor("#8ad6f1"));
        activity_main_title_center.setTextColor(Color.parseColor("#ffffff"));
        activity_main_title_right.setTextColor(Color.parseColor("#8ad6f1"));
        activity_main_title_left.setTextSize(13);
        activity_main_title_center.setTextSize(17);
        activity_main_title_right.setTextSize(13);*/

        InitViewPager();
    }

    spvscl spcl;

    @SuppressLint("ValidFragment")
    public DongtaiFragment(spvscl spcl) {
        this.spcl = spcl;
    }

    public DongtaiFragment() {
    }

    private void InitViewPager() {


        fragmentsList = new ArrayList<Fragment>();

      /*  frag1 = new QuanziFragment();
        frag2 = new TieziFragment();
        frag3 = new NewsActivityFragment2();*/

        for(int i=3;i<100;i++){
            if(i%3==0){
                fragmentsList.add(new QuanziFragment());
            }else if(i%3==1){
                fragmentsList.add(new TieziFragment());
            }else {
                fragmentsList.add( new NewsActivityFragment2());
            }
        }




        //跟换的画会有问题
        vPager.setAdapter(new MyFragmentPagerAdapter(getChildFragmentManager(), fragmentsList));
      /*  mPager.setAdapter(new MyFragmentPagerAdapter(getFragmentManager(), fragmentsList));*/
        vPager.addOnPageChangeListener(new MyOnPageChangeListener());
        vPager.setCurrentItem(51);
   /*     vPager.setOffscreenPageLimit(2);
        vPager.setCurrentItem(1);*/
    }

    int i = 1;
    int mesgfirstAsk = 0;
    NewsActivityFragment roundActivityFragment = null;
    int dd = 0;


    //滑动停止之后执行回调
    public static Back back;

    public void setOnShow(Back onShow) {
        this.back = onShow;
    }

    public interface Back {
        void Back(newsEntity news);
    }


    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageSelected(int arg0) {
            Log.e("fff", "顺序1");


            if (arg0%3 == 1) {

                activity_main_title_center.setText("新帖");
                activity_main_title_right.setText("圈子");
                activity_main_title_left.setText("资讯");
            }
            if (arg0%3 == 2) {
                activity_main_title_center.setText("资讯");
                activity_main_title_right.setText("圈子");
                activity_main_title_left.setText("新帖");
            }
            if (arg0%3 == 0) {

                activity_main_title_center.setText("圈子");
                activity_main_title_right.setText("新帖");
                activity_main_title_left.setText("资讯");


            }


        }


        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
          /*  *//**//*onPageScrolled(int arg0,float arg1,int arg2)    ，当页面在滑动的时候会调用此方法，在滑动被停止之前，此方法回一直得到
           调用。其中三个参数的含义分别为：
           arg0 :当前页面，及你点击滑动的页面
           arg1:当前页面偏移的百分比
           arg2:当前页面偏移的像素位置 *//**//**/
       /*     Log.e("huadong", "onPageScrolled？"+arg0+"::::"+arg1+"::::"+arg2);
            Log.e("huadong", vPager.getCurrentItem() + "---------onPageScrolled");*/
        }


        @Override
        public void onPageScrollStateChanged(int arg0) {
            Log.e("huadong", "onPageScrollStateChanged？" + arg0);
            //arg0==0表示什么都没做，arg0=1表示正在滑动，arg0==2表示滑动结束
            if (arg0 == 2) {

            }
        }


    }


/*
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            while (true) {
                                if (dd > 10) {
                                    pd.dismiss();
                                    Log.e("huadong", "onPageSelected？ 循环结束");
                                    return;
                                } else {
                                    try {
                                        Thread.sleep(50);

                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                                dd++;
                            }
                        }
                    }).start();*/


}


