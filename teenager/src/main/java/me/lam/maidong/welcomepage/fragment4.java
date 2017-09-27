package me.lam.maidong.welcomepage;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.lam.maidong.R;


/**
 * Created by 1 on 2015/11/24.
 */
public class fragment4 extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e("time", "onCreateView");

        View view = inflater.inflate(R.layout.wel2, null);
        butterknife.ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onStart() {
        Log.e("time", "onStart");
        super.onStart();

    }

/*
    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == 1) {
                textView.setText(msg.obj.toString());

            }

        }
    };
    int num = 200;
    int progress;
    @Override
    protected void lazyLoad() {


        new Thread(new Runnable() {
            @Override
            public void run() {

                while (progress <= num) {

                    System.out.println(progress);


                    // mRoundProgressBar2.setProgress(progress);
                    Message msg = new Message();
                    msg.obj = progress - 1;
                    msg.what = 1;
                    handler.sendMessage(msg);
                    progress += 1;

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();
    }*/

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        butterknife.ButterKnife.reset(this);
    }
}
