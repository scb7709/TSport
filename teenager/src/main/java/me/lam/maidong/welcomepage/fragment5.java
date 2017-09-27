package me.lam.maidong.welcomepage;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import me.lam.maidong.R;
import me.lam.maidong.activity.LogActivity;


/**
 * Created by 1 on 2015/11/24.
 */
public class fragment5 extends Fragment {
    Button bt_exp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.e("time", "onCreateView");

        View view = inflater.inflate(R.layout.wel3, null);

        return view;
    }

    @Override
    public void onStart() {
        Log.e("time", "onStart");
        bt_exp = (Button) getActivity().findViewById(R.id.bt_exp);
        bt_exp.setOnClickListener(new Click());
        super.onStart();

    }

    public class Click implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            startActivity(new Intent(getActivity(), LogActivity.class));
            getActivity().finish();
        }
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
    int num = 100;
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
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();
    }*/


}
