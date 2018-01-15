package me.lam.maidong.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

import me.lam.maidong.R;
import me.lam.maidong.adapter.MyFragmentPagerAdapter;
import me.lam.maidong.entity.spvscl;
import me.lam.maidong.myview.MyToash;
import me.lam.maidong.myview.NoPreloadViewPager;
import me.lam.maidong.utils.OKHttp;
import me.lam.maidong.utils.ShareUitls;



public class AnalizeFragment extends Fragment {

    private NoPreloadViewPager vPager;
    ArrayList<Fragment> fragmentsList;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            InitViewPager((spvscl) msg.obj);
        }
    };

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


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity_main_title_center = (TextView) getActivity().findViewById(R.id.activity_main_title_center);
        activity_main_title_right = (TextView) getActivity().findViewById(R.id.activity_main_title_right);
        activity_main_title_left = (TextView) getActivity().findViewById(R.id.activity_main_title_left);

        activity_main_title_center.setText("有效运动");
        activity_main_title_right.setText("卡路里");
        activity_main_title_left.setText("");

     /*   ripaihang = (Button) getActivity().findViewById(R.id.activity_main_ripaihang);
        ripaihang.setTextSize(13);
        ripaihang.setText("日排行");
        ripaihang.setClickable(true);*/

    }

    public AnalizeFragment() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String analizedata = ShareUitls.getString(getActivity(), "analizedata", "");
        String analizeflag = ShareUitls.getString(getActivity(), "analizeflag", "");

        if (!analizeflag.equals("1") && !analizedata.equals("")) {
            spvscl spvscl = new Gson().fromJson(analizedata, spvscl.class);
            InitViewPager(spvscl);
        } else {
            getData();
        }


    }

    private void InitViewPager(spvscl spcl) {
        fragmentsList = new ArrayList<Fragment>();
        fragmentsList.add(new SportFragment(spcl));
        fragmentsList.add(new ClFragment(spcl));
        fragmentsList.add(new RankingFragment(spcl));
        vPager.setAdapter(new MyFragmentPagerAdapter(getChildFragmentManager(), fragmentsList));
        vPager.setOnPageChangeListener(new MyOnPageChangeListener());
        vPager.setCurrentItem(0);
    }


    public class MyOnPageChangeListener implements NoPreloadViewPager.OnPageChangeListener {

        @Override
        public void onPageSelected(int arg0) {


            switch (arg0 % 3) {
                case 0:

                    activity_main_title_center.setText("有效运动");
                    activity_main_title_right.setText("卡路里");
                    activity_main_title_left.setText("");
             /*       activity_main_title_left.setTextColor(Color.parseColor("#ffffff"));
                    activity_main_title_center.setTextColor(Color.parseColor("#8ad6f1"));
                    activity_main_title_right.setTextColor(Color.parseColor("#8ad6f1"));
                    activity_main_title_left.setTextSize(17);
                    activity_main_title_center.setTextSize(13);
                    activity_main_title_right.setTextSize(13);*/

                    break;
                case 1:
                    activity_main_title_center.setText("卡路里");
                    activity_main_title_right.setText("日排行");
                    activity_main_title_left.setText("有效运动");
               /*     activity_main_title_left.setTextColor(Color.parseColor("#8ad6f1"));
                    activity_main_title_center.setTextColor(Color.parseColor("#ffffff"));
                    activity_main_title_right.setTextColor(Color.parseColor("#8ad6f1"));
                    activity_main_title_left.setTextSize(13);
                    activity_main_title_center.setTextSize(17);
                    activity_main_title_right.setTextSize(13);*/
                    break;
                case 2:
                    activity_main_title_center.setText("日排行");
                    activity_main_title_right.setText("");
                    activity_main_title_left.setText("卡路里");

               /*     activity_main_title_left.setTextColor(Color.parseColor("#8ad6f1"));
                    activity_main_title_center.setTextColor(Color.parseColor("#8ad6f1"));
                    activity_main_title_right.setTextColor(Color.parseColor("#ffffff"));
                    activity_main_title_left.setTextSize(13);
                    activity_main_title_center.setTextSize(13);
                    activity_main_title_right.setTextSize(17);*/
                    break;


            }

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
          /*  *//**//*onPageScrolled(int arg0,float arg1,int arg2)    ，当页面在滑动的时候会调用此方法，在滑动被停止之前，此方法回一直得到
           调用。其中三个参数的含义分别为：
           arg0 :当前页面，及你点击滑动的页面
           arg1:当前页面偏移的百分比
           arg2:当前页面偏移的像素位置 *//**//**/
           // Log.e("huadong", "onPageScrolled？");
          //  Log.e("huadong", vPager.getCurrentItem() + "---------onPageScrolled");
        }


        @Override
        public void onPageScrollStateChanged(int arg0) {
            if (arg0 == 2) {
            }
        }


    }

    private void getData() {
        String EduCode = ShareUitls.getString(getActivity(), "EducationCode", "");
        String LastSportDate = ShareUitls.getString(getActivity(), "LastSportDay", "");

        Log.i("LastSportDay", LastSportDate+"  "+EduCode);
        String url = "NewUser7DayStatistic/?EducationCode=" + EduCode + "&StatDate=" + LastSportDate;
        OKHttp.sendRequestRequestParams(getActivity(), "", true, url, new OKHttp.ResponseListener() {
            @Override
            public void onResponse(String response) {
               // Log.i("getspvscl", response.toString());
               // try {
                    spvscl spvscl = new Gson().fromJson(response, spvscl.class);

                //    Log.i("getspvscl", response.toString());
                    if (spvscl != null) {
                        ShareUitls.putString(getActivity(), "analizedata", response);
                        ShareUitls.putString(getActivity(), "analizeflag", "0");
                        InitViewPager(spvscl);
                    }
               /* } catch (Exception e) {
                }*/
            }

            @Override
            public void onErrorResponse() {
                MyToash.ToashNoNet(getActivity());


            }
        });



      /*  RequestParams params = new RequestParams(Constant.BASE_URL + "/api/NewUser7DayStatistic");
        params.addQueryStringParameter("EducationCode", EduCode);
        params.addQueryStringParameter("StatDate", LastSportDate);
        HttpUtils.getInstance(getActivity()).sendGetRequest(params, new HttpUtils.ResponseListener() {
                    @Override
                    public void onResponse(String response) {
                        spvscl spvscl = new Gson().fromJson(response, spvscl.class);
                        if (spvscl != null) {
                            InitViewPager(spvscl);
                        }

                    }

                    @Override
                    public void onErrorResponse(Throwable ex) {
                        Toast.makeText(getActivity(), "网络请求失败", Toast.LENGTH_SHORT).show();

                    }


                }

        );*/

    }


    String str="{\n" +
            "    \"StatusCode\": 1,\n" +
            "    \"Data\": {\n" +
            "        \"Summary\": [\n" +
            "            {\n" +
            "                \"StudentID\": \"0\",\n" +
            "                \"AvgEffectTime\": \"1000\",\n" +
            "                \"AvgTotalTime\": \"1000\",\n" +
            "                \"Percentage\": \"\",\n" +
            "                \"AvgCal\": \"500\",\n" +
            "                \"TotalCal\": \"1000\",\n" +
            "                \"TotalDays\": \"0\",\n" +
            "                \"MaxTotalTime\": \"1500\",\n" +
            "                \"MaxCalory\": \"100\",\n" +
            "                \"CaloryRate\": \"\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"Detail\": [\n" +
            "            {\n" +
            "                \"Day\": \"周五\",\n" +
            "                \"EffectTime\": \"100\",\n" +
            "                \"TotalTime\": \"300\",\n" +
            "                \"Calory\": \"50\",\n" +
            "                \"StatDate\": \"2017-10-20\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"Day\": \"周六\",\n" +
            "                \"EffectTime\": \"800\",\n" +
            "                \"TotalTime\": \"1000\",\n" +
            "                \"Calory\": \"50\",\n" +
            "                \"StatDate\": \"2017-10-21\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"Day\": \"周日\",\n" +
            "                \"EffectTime\": \"832\",\n" +
            "                \"TotalTime\": \"1111\",\n" +
            "                \"Calory\": \"50\",\n" +
            "                \"StatDate\": \"2017-10-22\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"Day\": \"周一\",\n" +
            "                \"EffectTime\": \"800\",\n" +
            "                \"TotalTime\": \"1500\",\n" +
            "                \"Calory\": \"50\",\n" +
            "                \"StatDate\": \"2017-10-23\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"Day\": \"周二\",\n" +
            "                \"EffectTime\": \"1200\",\n" +
            "                \"TotalTime\": \"1500\",\n" +
            "                \"Calory\": \"50\",\n" +
            "                \"StatDate\": \"2017-10-24\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"Day\": \"周三\",\n" +
            "                \"EffectTime\": \"700\",\n" +
            "                \"TotalTime\": \"900\",\n" +
            "                \"Calory\": \"50\",\n" +
            "                \"StatDate\": \"2017-10-25\"\n" +
            "            },\n" +
            "            {\n" +
            "                \"Day\": \"周四\",\n" +
            "                \"EffectTime\": \"880\",\n" +
            "                \"TotalTime\": \"1100\",\n" +
            "                \"Calory\": \"50\",\n" +
            "                \"StatDate\": \"2017-10-26\"\n" +
            "            }\n" +
            "        ],\n" +
            "        \"DayRanking\": {\n" +
            "            \"SchoolClassName\": \"\",\n" +
            "            \"EffectTime\": \"\",\n" +
            "            \"SchoolRanking\": \"100\",\n" +
            "            \"ClassRanking\": \"60\",\n" +
            "            \"SameAgeRanking\": \"33\"\n" +
            "        }\n" +
            "    },\n" +
            "    \"StatusMessage\": \"获取成功\"\n" +
            "}";
}


