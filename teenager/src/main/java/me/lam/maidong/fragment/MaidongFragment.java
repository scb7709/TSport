package me.lam.maidong.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.lam.maidong.R;
import me.lam.maidong.activity.MainActivity;
import me.lam.maidong.entity.dataResualtCallBack;
import me.lam.maidong.entity.spvscl;
import me.lam.maidong.myview.NoPreloadViewPager;
import me.lam.maidong.utils.OKHttp;
import me.lam.maidong.utils.ShareUitls;
import me.lam.maidong.welcomepage.MyFragmentPagerAdapter;


public class MaidongFragment extends Fragment {

    private NoPreloadViewPager vPager;
    private ArrayList<Fragment> fragmentsList;
    private dataResualtCallBack dataRes;
    private String EduCode, EducationalCode;
    int cishu;
    public static boolean ISEND;//由于本界面有网络请求，有延迟线程 所以设立此标记当本Fragment结束时 终止线程 和网络任务

    @Override
    public void onDestroy() {
        super.onDestroy();
        ISEND = true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_analize, null);
        vPager = (NoPreloadViewPager) view.findViewById(R.id.vPager);
        return view;
    }

    public TextView activity_main_title_left;

    public TextView activity_main_title_center;

    public TextView activity_main_title_right;
    private MyFragmentPagerAdapter myFragmentPagerAdapter;
    private Gson gson = new Gson();

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity_main_title_center = (TextView) getActivity().findViewById(R.id.activity_main_title_center);
        activity_main_title_right = (TextView) getActivity().findViewById(R.id.activity_main_title_right);
        activity_main_title_left = (TextView) getActivity().findViewById(R.id.activity_main_title_left);

        activity_main_title_center.setText("");
        activity_main_title_right.setText("");
        activity_main_title_left.setText("");
        EduCode = ShareUitls.getString(getActivity(), "EducationCode", "");
        EducationalCode = ShareUitls.getString(getActivity(), "EducationCode", "");
    }

    public MaidongFragment() {
    }

    private void InitViewPager(String response) {
        vPager.removeAllViews();
        if (fragmentsList != null) {
            fragmentsList.clear();
        } else {
            fragmentsList = new ArrayList<Fragment>();
        }
        if (myFragmentPagerAdapter != null) {
            myFragmentPagerAdapter.notifyDataSetChanged();
        }


        dataRes = null;
        if (response != null) {
            try {
                dataRes = gson.fromJson(response, dataResualtCallBack.class);
            } catch (Exception e) {
                return;
            }

        }
        if (dataRes == null || dataRes.DailySport == null) {
            return;
        }
        cishu = dataRes.DailySport.size();

        if (dataRes.StatusCode == 0 || cishu == 0) {
            return;
        }


        //
        List<dataResualtCallBack.DailySportEntity> dailySportEntity = dataRes.DailySport;
        int size = dailySportEntity.size();
        if (dailySportEntity == null || size == 0) {
            return;
        }
        // Log.i("DailySportsize1", size + "");

        for (int i = 0; i < dailySportEntity.size(); i++) {
            dataResualtCallBack.DailySportEntity dailySportEntity1 = dailySportEntity.get(i);
            if (i == 0) {
                activity_main_title_center.setText(dailySportEntity1.MessageInfo);
                fragmentsList.add(new MaiDongTodayFragment(dailySportEntity1, size, i, vPager));
            } else
                fragmentsList.add(new MaiDongNotodayFragment(dailySportEntity1, size, i, vPager));
        }
        myFragmentPagerAdapter = new MyFragmentPagerAdapter(getChildFragmentManager(), fragmentsList);
        vPager.setAdapter(myFragmentPagerAdapter);
        vPager.setOnPageChangeListener(new MyOnPageChangeListener());

        if (freash) {
            freash = false;
            MaidongFragment maidongFragment = new MaidongFragment();
            MainActivity.changeFragment(maidongFragment, "MaidongFragment");
        }
        // vPager.setCurrentItem(1);
    }


    public class MyOnPageChangeListener implements NoPreloadViewPager.OnPageChangeListener {

        @Override
        public void onPageSelected(int arg0) {

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }


        @Override
        public void onPageScrollStateChanged(int arg0) {
            if (arg0 == 2) {
            }
        }


    }

    @Override
    public void onStart() {
        super.onStart();
     /*   activity_main_title_center.setText("");
        activity_main_title_right.setText("");
        activity_main_title_left.setText("");*/
        // getOKHttp();
        String maidongdata = ShareUitls.getString(getActivity(), "maidongdata", "");
        String maidongflag = ShareUitls.getString(getActivity(), "maidongflag", "");

        long OldTime = Long.parseLong(ShareUitls.getString(getActivity(), "OldTime", "0"));
        long NewTime = new Date().getTime();
        if (!maidongflag.equals("1") && !maidongdata.equals("") && (NewTime - OldTime <= 15 * 60000)) {
            InitViewPager(maidongdata);
        } else {
            ShareUitls.putString(getActivity(), "OldTime", NewTime + "");
            ShareUitls.putString(getActivity(), "analizeflag", "1");//分析界面需要刷新
            ShareUitls.putString(getActivity(), "analizedata", "");
            getOKHttp();
        }

    }

    boolean freash;

    private void getOKHttp() {
        String LastSportDay = ShareUitls.getString(getActivity(), "LastSportDay", "");
        Log.i("myblue", "" + LastSportDay);
        String url = "NewSportDate/?EducationalCode=" + EducationalCode + "&Date=" + LastSportDay;
        OKHttp.sendRequestRequestParams(getActivity(), "", true, url, new OKHttp.ResponseListener() {
            @Override
            public void onResponse(String response) {
                freash = true;
                Log.i("myblue", "" + response);
                ShareUitls.putString(getActivity(), "maidongdata", response);
                ShareUitls.putString(getActivity(), "maidongflag", "0");
                InitViewPager(response);//
            }

            @Override
            public void onErrorResponse() {

                Toast.makeText(getActivity(), "网络请求失败", Toast.LENGTH_SHORT).show();

            }
        });
    }


    public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        private ArrayList<Fragment> fragmentsList;

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public MyFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {

            super(fm);
            this.fragmentsList = fragments;
        }

        @Override
        public int getCount() {
            Log.e("tag", "getCount");
            return fragmentsList.size();
        }

        @Override
        public Fragment getItem(int arg0) {
            Log.e("nage", arg0 + "");
            return fragmentsList.get(arg0);
        }


    }
}


