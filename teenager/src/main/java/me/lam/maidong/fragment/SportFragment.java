package me.lam.maidong.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import me.lam.maidong.R;
import me.lam.maidong.circle.RoundProgressBar;
import me.lam.maidong.entity.sevenDataTime;
import me.lam.maidong.entity.spvscl;
import me.lam.maidong.utils.StringForTime;

public class SportFragment extends Fragment implements View.OnClickListener {
    @InjectView(R.id.AvgEffectTime)
    TextView AvgEffectTime;
    @InjectView(R.id.zhouyiall)
    Button zhouyiall;
    @InjectView(R.id.zhouyi)
    Button zhouyi;
    @InjectView(R.id.zhouerall)
    Button zhouerall;
    @InjectView(R.id.zhouer)
    Button zhouer;
    @InjectView(R.id.zhousanall)
    Button zhousanall;
    @InjectView(R.id.zhousan)
    Button zhousan;
    @InjectView(R.id.zhousiall)
    Button zhousiall;
    @InjectView(R.id.zhousi)
    Button zhousi;
    @InjectView(R.id.zhouwuall)
    Button zhouwuall;
    @InjectView(R.id.zhouwu)
    Button zhouwu;
    @InjectView(R.id.zhouliuall)
    Button zhouliuall;
    @InjectView(R.id.zhouliu)
    Button zhouliu;
    @InjectView(R.id.zhouriall)
    Button zhouriall;
    @InjectView(R.id.zhouri)
    Button zhouri;
    @InjectView(R.id.t1)
    TextView t1;
    @InjectView(R.id.t2)
    TextView t2;
    @InjectView(R.id.t3)
    TextView t3;
    @InjectView(R.id.t4)
    TextView t4;
    @InjectView(R.id.t5)
    TextView t5;
    @InjectView(R.id.t6)
    TextView t6;
    @InjectView(R.id.t7)
    TextView t7;
    @InjectView(R.id.show1)
    Button show1;
    @InjectView(R.id.show2)
    Button show2;
    @InjectView(R.id.show3)
    Button show3;
    @InjectView(R.id.show4)
    Button show4;
    @InjectView(R.id.show5)
    Button show5;
    @InjectView(R.id.show6)
    Button show6;
    @InjectView(R.id.show7)
    Button show7;

    private int progress = 0;
    private RoundProgressBar mRoundProgressBar2;
    int roundnum = 0;
    int sleep;
    private TextView AvgTotalTime;
    private TextView TotalDays;
    private TextView MaxTotalTime;
    private RelativeLayout zhu;
    private TextView midleTime;
    private TextView botomLin;
    View view;
    List<Button> bts = null;
    List<Button> btalls = null;
    List<Button> shows = null;
    List<TextView> ts = null;
    int index;
    private int screenWidth;
    public boolean ISOVER;//由于本界面有网络请求，有延迟线程 所以设立此标记当本Fragment结束时 终止线程 和网络任务

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.layout_sport, null);
        relativeLayout = (RelativeLayout) view.findViewById(R.id.view);
        AvgTotalTime = (TextView) view.findViewById(R.id.TotalCal);
        TotalDays = (TextView) view.findViewById(R.id.TotalDays);
        MaxTotalTime = (TextView) view.findViewById(R.id.MaxTotalTime);
        midleTime = (TextView) view.findViewById(R.id.midleTime);
        zhu = (RelativeLayout) view.findViewById(R.id.zhu);
        botomLin = (TextView) view.findViewById(R.id.t1);
        ButterKnife.inject(this, view);
        return view;
    }

    int x;
    int y;
    int bootom;
    int gap;
    int top;
    int daohangHigh;

    public Handler h = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            if (msg.what == 1) {
                if (relativeLayout.getWidth() != 0 && relativeLayout.getHeight() != 0 && zhu.getWidth() != 0 && zhu.getHeight() != 0 && botomLin.getWidth() != 0 && botomLin.getHeight() != 0) {
                    int[] location666 = new int[2];
                    relativeLayout.getLocationOnScreen(location666);
                    daohangHigh = location666[1];
                    int[] location = new int[2];
                    zhu.getLocationOnScreen(location);
                    x = location[0];
                    y = location[1];

                    int[] location0 = new int[2];
                    botomLin.getLocationOnScreen(location0);
                    int x0 = location0[0];
                    int y0 = location0[1];
                    bootom = zhu.getTop();
                    top = botomLin.getTop();
                    gap = (botomLin.getTop() - zhu.getTop());

                    for (int i = 0; i < spcl.getData().getDetail().size(); i++) {
                        Log.e("zhixin???", "gap:==");
                        FrameLayout.LayoutParams linearParamsall = (FrameLayout.LayoutParams) btalls.get(i).getLayoutParams();
                        // 取控件aaa当前的布局参数
                        spvscl.DataEntity.DetailEntity detailEntity = spcl.getData().getDetail().get(i);
                        linearParamsall.height = gap * (detailEntity.getTotalTime() / 60) / (spcl.getData().getSummary().get(0).getMaxTotalTime() / 60); //
                        btalls.get(i).setLayoutParams(linearParamsall); // 使设置好的布局参数应用到控件aaa

                        FrameLayout.LayoutParams linearParamsshow = (FrameLayout.LayoutParams) shows.get(i).getLayoutParams();
                        // 取控件aaa当前的布局参数
                        linearParamsshow.height = gap * (detailEntity.getTotalTime() / 60) / (spcl.getData().getSummary().get(0).getMaxTotalTime() / 60); //
                        shows.get(i).setLayoutParams(linearParamsall); // 使设置好的布局参数应用到控件aaa


                        FrameLayout.LayoutParams linearParams = (FrameLayout.LayoutParams) bts.get(i).getLayoutParams();
                        // 取控件aaa当前的布局参数
                        linearParams.height = gap * (detailEntity.getEffectTime() / 60) / (spcl.getData().getSummary().get(0).getMaxTotalTime() / 60); // 当控件的高强制设成365象素
                        bts.get(i).setLayoutParams(linearParams); // 使设置好的布局参数应用到控件aaa
                        ts.get(i).setText(spcl.getData().getDetail().get(i).getDay());
                    }

                }
            }
        }
    };
    int count;
    draw d;
    RelativeLayout relativeLayout;

    public class draw extends View {
        int x = 0;
        int y = 0;
        String effect;
        String tatal;

        public draw(Context context, String effect,String tatal, int x, int y) {
            super(context);
            setWillNotDraw(false);
            this.effect = effect;
            this.tatal = tatal;
            this.x = x;
            this.y = y;
        }

        @Override
        public void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            setWillNotDraw(false);

            Paint paint = new Paint();
            paint.setStyle(Paint.Style.FILL);
            paint.setAntiAlias(true);
            paint.setColor(Color.parseColor("#42c3f4"));
            paint.setStrokeWidth((float) 1.0);
            if (screenWidth < 1080) {
                paint.setTextSize(22);
            } else {
                paint.setTextSize(32);
            }


          /*  canvas.drawText(data.getEffect(), x, y - 85, paint);
            canvas.drawLine(x - 10, y - 77, x + zhouyi.getWidth() + 10, y - 77, paint);
            canvas.drawText(data.getTotal(), x, y - 50, paint);
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_triangle_blue);
            canvas.drawBitmap(bitmap, x + zhouyi.getWidth() / 4, y - 45, paint);*/

            int effectwidth = x + (zhouyiall.getWidth() - getTextWidth(effect, paint)) / 2;//要居中的话 柱状图的宽度减去文字的宽度的一半 加上X 就等于文字的起始坐标
            int tatalwidth = x + (zhouyiall.getWidth() - getTextWidth(tatal, paint)) / 2;//要居中的话 柱状图的宽度减去文字的宽度的一半 加上X 就等于文字的起始坐标
            canvas.drawText(effect, effectwidth, y - 90, paint);
            canvas.drawLine(x, y - 85, x + zhouyi.getWidth(), y - 85, paint);
            canvas.drawText(tatal, tatalwidth, y - 60, paint);
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_triangle_blue);
            canvas.drawBitmap(bitmap, x + zhouyi.getWidth() / 4, y - 50, paint);


        }
    }


    spvscl spcl;

    @SuppressLint("ValidFragment")
    public SportFragment(spvscl spcl) {
        this.spcl = spcl;


    }

    public SportFragment() {
    }


    int ShowPossition = 0;
  /*  @Override
    public void onp() {
        super.onDestroy();
        ISEND=true;
    }*/

    @Override
    public void onPause() {
        super.onPause();
        ISOVER = true;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ISOVER = false;
        WindowManager wm = getActivity().getWindowManager();
        screenWidth = wm.getDefaultDisplay().getWidth();

        mRoundProgressBar2 = (RoundProgressBar) getActivity().findViewById(R.id.roundProgressBar2);
        bts = new ArrayList<>();
        bts.add(zhouyi);
        bts.add(zhouer);
        bts.add(zhousan);
        bts.add(zhousi);
        bts.add(zhouwu);
        bts.add(zhouliu);
        bts.add(zhouri);
        btalls = new ArrayList<>();
        btalls.add(zhouyiall);
        btalls.add(zhouerall);
        btalls.add(zhousanall);
        btalls.add(zhousiall);
        btalls.add(zhouwuall);
        btalls.add(zhouliuall);
        btalls.add(zhouriall);
        shows = new ArrayList<>();
        shows.add(show1);
        shows.add(show2);
        shows.add(show3);
        shows.add(show4);
        shows.add(show5);
        shows.add(show6);
        shows.add(show7);
        ts = new ArrayList<>();
        ts.add(t1);
        ts.add(t2);
        ts.add(t3);
        ts.add(t4);
        ts.add(t5);
        ts.add(t6);
        ts.add(t7);


        if (spcl.getStatus() != 0) {
            for (int ShowPossition=0 ; ShowPossition < shows.size(); ShowPossition++) {
                setOnClickListener(ShowPossition);

            }
            spvscl.DataEntity.SummaryEntity summaryEntity = spcl.getData().getSummary().get(0);
            AvgTotalTime.setText(StringForTime.stringForTime3(summaryEntity.getAvgTotalTime()));
            AvgEffectTime.setText(StringForTime.stringForTime3(summaryEntity.getAvgEffectTime()));
            TotalDays.setText("共运动：" + summaryEntity.getTotalDays() + "天");
            MaxTotalTime.setText((summaryEntity.getMaxTotalTime() / 60) + "");
            midleTime.setText("" + (summaryEntity.getMaxTotalTime() / 60) / 2);
            roundnum = (summaryEntity.getAvgEffectTime());
            mRoundProgressBar2.setMax(summaryEntity.getAvgTotalTime());
            //  sleep = (int) (Math.random() * (4000 / roundnum - 2000 / roundnum + 1) + 2000 / roundnum);//最多不超过4秒 最低不少于2秒
            progress = 0;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (progress <= roundnum && !ISOVER) {
                        mRoundProgressBar2.setProgress(progress);
                        progress += 1;
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }).start();
            h.sendEmptyMessageDelayed(1, 1);
        }

    }

    private void setOnClickListener(final int ShowPossition){
        final List<spvscl.DataEntity.DetailEntity> detailEntities = spcl.getData().getDetail();
        final Button show = shows.get(ShowPossition);
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] location55 = new int[2];
                show.getLocationOnScreen(location55);
                String effect = StringForTime.stringForTime3(detailEntities.get(ShowPossition).getEffectTime());
                String tatal = StringForTime.stringForTime3(detailEntities.get(ShowPossition).getTotalTime());
                showdraw(effect,tatal,location55[0],location55[1]);


            }
        });
    }

    private void showdraw(String effect,String tatal,int x,int y) {
        if (d != null) {
            relativeLayout.removeView(d);
            d = null;
        }
        d = new draw(getContext(), effect,tatal, x, (y - daohangHigh));
        relativeLayout.addView(d);

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {


        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    public static int getTextWidth(String content, Paint paint) {
        int width = 0;
        if (content != null && content.length() > 0) {
            int length = content.length();
            float[] widths = new float[length];
            paint.getTextWidths(content, widths);
            for (int i = 0; i < length; i++) {
                width += (int) Math.ceil(widths[i]);
            }
        }
        return width;
    }

}
