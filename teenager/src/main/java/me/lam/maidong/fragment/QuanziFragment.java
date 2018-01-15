package me.lam.maidong.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import me.lam.maidong.R;
    public class QuanziFragment extends LazyFragment{

        TextView textView77;

        // 标志位，标志已经初始化完成。
        private boolean isPrepared;
        ProgressDialog pd;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         //   Log.e("huadong", "onCreateView");
            View view = inflater.inflate(R.layout.quanzi,null);
            //XXX初始化view的各控件
            textView77= (TextView) view.findViewById(R.id.textView77);
            pd = new ProgressDialog(getActivity());
            pd.setTitle("登录中...");
            pd.setMessage("请稍等...");
//        pd.setCancelable(false);
            pd.setCanceledOnTouchOutside(false);
            isPrepared = true;
            lazyLoad();
            return view;
        }
        int dd=0;
        @Override
        protected void lazyLoad() {
            if(!isPrepared || !isVisible) {
                return;
            }
            pd.show();
            //填充各控件的数据
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        if (dd > 10) {
                            pd.dismiss();
                          //  Log.e("huadong", "onPageSelected？ 循环结束"+dd);

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
            }).start();
        }

    }
