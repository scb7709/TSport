package me.lam.maidong.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


import me.lam.maidong.R;
import me.lam.maidong.circle.RoundProgressBar;
import me.lam.maidong.welcomepage.MyFragmentPagerAdapter;

/**
 * A placeholder fragment containing a simple view.
 */


public class MaiDongActivityFragmentTest extends Fragment {

    ViewPager mPager;
    ArrayList<Fragment> fragmentsList;
    fragment1 frag1;
    fragment2 frag2;
    private RoundProgressBar mRoundProgressBar2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mai_dong_datacontent, null);
        InitViewPager(view);
        return view;
    }

    private void InitViewPager(View parentView) {
        mPager = (ViewPager) parentView.findViewById(R.id.vPager);
        fragmentsList = new ArrayList<Fragment>();

        frag1 = new fragment1();
        frag2 = new fragment2();
        fragmentsList.add(frag1);
        fragmentsList.add(frag2);
        //跟换的画会有问题
       mPager.setAdapter(new MyFragmentPagerAdapter(getChildFragmentManager(), fragmentsList));
      /*  mPager.setAdapter(new MyFragmentPagerAdapter(getFragmentManager(), fragmentsList));*/
      //  mPager.setOnPageChangeListener(new MyOnPageChangeListener());
        mPager.setOffscreenPageLimit(0);
       mPager.setCurrentItem(0);


    }

   /* public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageSelected(int arg0) {
            Log.e("onPageSelected", arg0+"");
            Log.e("huadong", mPager.getCurrentItem() + "--------onPageSelected");
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

            *//*onPageScrolled(int arg0,float arg1,int arg2)    ，当页面在滑动的时候会调用此方法，在滑动被停止之前，此方法回一直得到
           调用。其中三个参数的含义分别为：
           arg0 :当前页面，及你点击滑动的页面
           arg1:当前页面偏移的百分比
           arg2:当前页面偏移的像素位置 *//*
            Log.e("huadong", "onPageScrolled？");
            Log.e("huadong", mPager.getCurrentItem() + "---------onPageScrolled");
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
            if (arg0 == 2) {
              //  mPager.setCurrentItem(0, false);
                //arg0==0表示什么都没做，arg0=1表示正在滑动，arg0==2表示滑动结束
                Toast.makeText(getActivity(), arg0 + "当前是" + mPager.getCurrentItem() + "", Toast.LENGTH_LONG).show();
                Log.e("huadong", mPager.getCurrentItem() + "----------onPageScrollStateChanged");
            }
        }


    }*/
}
