package me.lam.maidong.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import me.lam.maidong.R;
import me.lam.maidong.column.HistogramView;

import me.lam.maidong.circle.RoundProgressBar;

/**
 * A placeholder fragment containing a simple view.
 */


public class fragment2 extends LazFragment {
    private RoundProgressBar mRoundProgressBar1, mRoundProgressBar2,
            mRoundProgressBar3, mRoundProgressBar4, mRoundProgressBar5;
    private int progress = 0;
    TextView all;
    TextView last;

    static int num1 = (int) 40, num2 = (int) 11.04, num3 = (int) 2.52;

    //圆柱
    private int[] data = new int[]{(int) (num1 * 1.5f), (int) (num2 * 1.5f), (int) (num3 * 1.5f)};
    private int[] text = new int[]{0, 0, 0};
    private HistogramView histogramView;
    static int roundnum = 60;

    /*public void fragment2(){
        mRoundProgressBar2 = (RoundProgressBar) getActivity().findViewById(R.id.roundProgressBar2);
        Log.e("time555",mRoundProgressBar2+"");
        this.mRoundProgressBar2=mRoundProgressBar2;

    }*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mai_dong_datashow2, null);
        //  ButterKnife.inject(this, view);

        return view;
    }

    public Handler dcHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                last.setText("有效运动时长2222");
            }
            if (msg.what == 2) {
                mRoundProgressBar2 = (RoundProgressBar) getActivity().findViewById(R.id.roundProgressBar2);
                Log.e("new", mRoundProgressBar2 + "");
            }
        }
    };

 /*   //管理预加载
    private boolean mHasLoadedOnce = false;
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (this.isVisible()) {
            // we check that the fragment is becoming visible
            if (isVisibleToUser && !mHasLoadedOnce) {

                // async http request here
                mHasLoadedOnce = true;
            }
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        setUserVisibleHint(true);
        super.onActivityCreated(savedInstanceState);
    }*/

    LinearLayout ll;
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.e("time", "onStart2");
       // mRoundProgressBar2 = (RoundProgressBar) getActivity().findViewById(R.id.roundProgressBar2);
        Log.e("time", mRoundProgressBar2 + "");
        //画线
     /*   LinearLayout ll = (LinearLayout) getActivity().findViewById(R.id.ll);
        ll.addView(new DrawView(getActivity()));*/

        //画折线
         ll = (LinearLayout) getActivity().findViewById(R.id.drawlineback);
        Log.e("step", ll + "");

       // ll.addView(new MyView(getActivity(), dcHandler));
        all = (TextView) getActivity().findViewById(R.id.all);
        last = (TextView) getActivity().findViewById(R.id.last);
        all.setText(33.56 + "");



      /*  *//*viewpager操作*//*
        mainContentViewpager.setCurrentItem(0);
        mainContentViewpager.setAdapter(new MyAdapter());
        mainContentViewpager.setOffscreenPageLimit(1);*/




       /* new Thread(new Runnable() {
            @Override
            public void run() {

                while (progress <= roundnum) {

                    System.out.println(progress);


                    mRoundProgressBar2.setProgress(progress);
                    Message msg = new Message();
                    msg.obj = progress - 1;
                    msg.what = 1;
                    dcHandler.sendMessage(msg);
                    progress += 1;

                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();*/

//按钮点击下的转圈
        ((Button) getActivity().findViewById(R.id.jia))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       /* new Thread(new Runnable() {

                            @Override
                            public void run() {

                                while (progress <= 87) {

                                    System.out.println(progress);

                                    // mRoundProgressBar1.setProgress(progress);
                                    mRoundProgressBar2.setProgress(progress);

                                    Message msg = new Message();
                                    msg.obj = progress - 1;
                                    msg.what = 1;
                                    dcHandler.sendMessage(msg);
                                    progress += 1;

                                    try {
                                        Thread.sleep(10);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }

                            }
                        }).start();*/


                        Toast.makeText(getActivity(), "按钮减", Toast.LENGTH_LONG);
                        roundnum = 87;
                        num1 = (int) 40;
                        num2 = (int) 20;
                        num3 = (int) 10;
                        MaiDongActivityFragment maiDongActivityFragment = new MaiDongActivityFragment();
                        replaceFragment(maiDongActivityFragment);
                    }
                });
        ((Button) getActivity().findViewById(R.id.jian))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(), "按钮加", Toast.LENGTH_LONG);
                        roundnum = 60;
                        num1 = (int) 0;
                        num2 = (int) 10;
                        num3 = (int) 2;
                        MaiDongActivityFragment maiDongActivityFragment = new MaiDongActivityFragment();
                        replaceFragment(maiDongActivityFragment);
                    }
                });


        //圆柱
        boolean a = true;
        histogramView = (HistogramView) getActivity().findViewById(R.id.histogram);
        if (a) {
         /*   histogramView.setProgress(data);*/
            Log.e("ff", "执行几次");
        }
        //点击按钮显示字体的
       /* histogramView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int step = (v.getWidth() - 30) / 8;
                int x = (int) event.getX();
                for (int i = 0; i < 7; i++) {
                    if (x > (30 + step * (i + 1) - 30)
                            && x < (30 + step * (i + 1) + 30)) {
                        text[i] = 1;
                        for (int j = 0; j < 7; j++) {
                            if (i != j) {
                                text[j] = 0;
                            }
                        }
                        histogramView.setText(text);
                    }
                }

                return false;
            }
        });*/
        super.onActivityCreated(savedInstanceState);
    }
    //滑动fragment
   /* public class MyAdapter extends FragmentPagerAdapter {


        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            switch (position){
                case 0:
                    return filelistFragment;
                case 1:
                    return new PhotoFragment();
                case 2:
                    return new OtherFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }*/


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

    int i = 0;
    @Override
    protected void lazyLoad() {


      //  ll.addView(new MyView(getActivity(), dcHandler,4,3,1,4,5,2));
        //histogramView.setProgress(data);
        new Thread(new Runnable() {
            @Override
            public void run() {

                while (progress <= roundnum) {

                    System.out.println(progress);
                    if (i == 0) {
                        Log.e("rp", "onStart2");
                        Message msg2 = new Message();
                        msg2.what = 2;
                        dcHandler.sendMessage(msg2);
                    }
                    if(mRoundProgressBar2!=null){
                        mRoundProgressBar2.setProgress(progress);
                    }else{
                        if (i == 0) {
                            Log.e("rp", "onStart2");
                            Message msg2 = new Message();
                            msg2.what = 2;
                            dcHandler.sendMessage(msg2);
                        }
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        mRoundProgressBar2.setProgress(progress);
                    }
                    Message msg = new Message();
                    msg.obj = progress - 1;
                    msg.what = 1;
                    dcHandler.sendMessage(msg);
                    progress += 1;
                    i++;
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();
    }
}
