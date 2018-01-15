package me.lam.maidong.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import me.lam.maidong.R;
import me.lam.maidong.entity.spvscl;
import me.lam.maidong.myview.MyToash;
import me.lam.maidong.utils.ImageUtil;


public class ClFragment extends Fragment {


    @InjectView(R.id.AvgCal)
    TextView AvgCal;
    @InjectView(R.id.TotalCal)
    TextView TotalCal;
    @InjectView(R.id.TotalDays)
    TextView TotalDays;
    @InjectView(R.id.MaxTotalTime)
    TextView MaxTotalTime;
    @InjectView(R.id.midleTime)
    TextView midleTime;
    @InjectView(R.id.zhouyiall)
    Button clzhouyi;
    @InjectView(R.id.zhouerall)
    Button clzhouer;
    @InjectView(R.id.zhousanall)
    Button clzhousan;
    @InjectView(R.id.zhousiall)
    Button clzhousi;
    @InjectView(R.id.zhouwuall)
    Button clzhouwu;
    @InjectView(R.id.zhouliuall)
    Button clzhouliu;
    @InjectView(R.id.zhouriall)
    Button clzhouri;

    @InjectView(R.id.zhu)
    LinearLayout zhu;
    RelativeLayout relativeLayout;
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
  //  private TextView botomLin;
    View view;
    List<Button> bts = null;
    List<TextView> ts = null;
    @InjectView(R.id.pingji)
    TextView pingji;
    private int screenWidth;
    private Activity activity;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_layout_cl, null);
        ButterKnife.inject(this, view);

      //  botomLin = (TextView) view.findViewById(R.id.t1);
        mRoundProgressBar4 = (RelativeLayout) getActivity().findViewById(R.id.roundProgressBar4);
        relativeLayout = (RelativeLayout) view.findViewById(R.id.view);
        activity=getActivity();
        return view;
    }

    spvscl spcl;

    @SuppressLint("ValidFragment")
    public ClFragment(spvscl spcl) {
        this.spcl = spcl;
    }

    public ClFragment() {
    }

    private RelativeLayout mRoundProgressBar4;


    int daohangHigh;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        WindowManager wm = getActivity().getWindowManager();
        screenWidth = wm.getDefaultDisplay().getWidth();
        bts = new ArrayList<>();
        bts.add(clzhouyi);
        bts.add(clzhouer);
        bts.add(clzhousan);
        bts.add(clzhousi);
        bts.add(clzhouwu);
        bts.add(clzhouliu);
        bts.add(clzhouri);
        ts = new ArrayList<>();
        ts.add(t1);
        ts.add(t2);
        ts.add(t3);
        ts.add(t4);
        ts.add(t5);
        ts.add(t6);
        ts.add(t7);
        if (spcl.getStatus() != 0) {
            final spvscl.DataEntity dataEntity = spcl.getData();
            if (dataEntity == null) {
                return;
            }
            for (int ShowPossition=0 ; ShowPossition < bts.size(); ShowPossition++) {
                setOnClickListener(dataEntity,ShowPossition);
            }

            TotalCal.setText(dataEntity.getSummary().get(0).getAvgCal());//1
            AvgCal.setText(dataEntity.getSummary().get(0).getTotalCal());//1
            TotalDays.setText("共运动：" + dataEntity.getSummary().get(0).getTotalDays() + "天");

            int temp=Integer.parseInt(dataEntity.getSummary().get(0).getMaxCalory());
            midleTime.setText("" + (temp/2));
            MaxTotalTime.setText(temp + "千卡");
            pingji.setText(dataEntity.getSummary().get(0).getCaloryRate());


            mRoundProgressBar4 = (RelativeLayout) getActivity().findViewById(R.id.roundProgressBar4);
            // Log.e("mRoundProgressBar4", mRoundProgressBar4.getHeight() + "::::::" + mRoundProgressBar4.getWidth());

            mRoundProgressBar4.addView(new MyTestView(getActivity()));

            h.sendEmptyMessageDelayed(1, 1);
        }
    }

    private void setOnClickListener(spvscl.DataEntity dataEntity ,final int ShowPossition){

        final List<spvscl.DataEntity.DetailEntity> detailEntities =dataEntity.getDetail();
        if (detailEntities == null) {
            return;
        }
        final Button show = bts.get(ShowPossition);
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] location55 = new int[2];
                show.getLocationOnScreen(location55);
                showdraw(detailEntities.get(ShowPossition).getCalory(),location55[0],location55[1]);
            }
        });
    }

    private void showdraw(String Calory,int x,int y) {
        if (d != null) {
            relativeLayout.removeView(d);
            d = null;
        }
        d = new draw(getContext(), Calory, x, (y - daohangHigh));
        relativeLayout.addView(d);

    }


    public class draw extends View {
        int x = 0;
        int y = 0;
        String data = "";

        public draw(Context context, String data, int x, int y) {
            super(context);
            setWillNotDraw(false);
            //Log.e("0000", x + "开始画了---1111" + y + "开始画了");

            this.data =data;
            this.x = x;
            this.y = y;
        }

        @Override
        public void onDraw(Canvas canvas) {
            super.onDraw(canvas);

           // Log.e("0000", x + "开始画了---22222" + y + "开始画了");
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
          /*  int width = x+(clzhouyi.getWidth()-SportFragment.getTextWidth(data,paint))/2;//要居中的话 柱状图的宽度减去文字的宽度的一半 加上X 就等于文字的起始坐标
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_triangle_blue);
            canvas.drawText(data, width, y - 80, paint);
            canvas.drawBitmap(bitmap, x + clzhouyi.getWidth() / 4, y - 60, paint);*/

            int width = x+(clzhouyi.getWidth()-SportFragment.getTextWidth(data,paint))/2;//要居中的话 柱状图的宽度减去文字的宽度的一半 加上X 就等于文字的起始坐标
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_triangle_blue);
            canvas.drawText(data, width,y - ImageUtil.dp2px(activity,15) , paint);
            canvas.drawBitmap(bitmap, x + (clzhouyi.getWidth()-bitmap.getWidth())/2, y -ImageUtil.dp2px(activity,12), paint);


        }

    }

    // int x;
    //  int y;
    //  int bootom;
    int gap;
    // int top;
    // int count;
    int mRoundProgressBar4x;
    int mRoundProgressBar4y;
    int mRoundProgressBargap;
    public Handler h = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            try {
                if (msg.what == 1) {//&& botomLin.getWidth() != 0 && botomLin.getHeight() != 0
                    if (mRoundProgressBar4.getWidth() != 0 && mRoundProgressBar4.getHeight() != 0 && relativeLayout.getWidth() != 0 && relativeLayout.getHeight() != 0 && zhu.getWidth() != 0 && zhu.getHeight() != 0 ) {
                        int[] mRoundProgressBar4xlocation = new int[2];
                        mRoundProgressBar4.getLocationOnScreen(mRoundProgressBar4xlocation);
                        mRoundProgressBar4x = mRoundProgressBar4xlocation[0];
                        mRoundProgressBar4y = mRoundProgressBar4xlocation[1];
                        mRoundProgressBargap = mRoundProgressBar4.getWidth();
                        int[] location666 = new int[2];
                        relativeLayout.getLocationOnScreen(location666);
                        daohangHigh = location666[1];
                        int[] location = new int[2];
                        zhu.getLocationOnScreen(location);
                       // int[] location0 = new int[2];
                       // botomLin.getLocationOnScreen(location0);
                        gap = zhu.getHeight();
                        List<spvscl.DataEntity.DetailEntity> detailEntities = spcl.getData().getDetail();
                        double temp2 = Double.parseDouble(spcl.getData().getSummary().get(0).getMaxCalory());
                        if (temp2 != 0) {
                            for (int i = 0; i < detailEntities.size(); i++) {
                                RelativeLayout.LayoutParams linearParams = (RelativeLayout.LayoutParams) bts.get(i).getLayoutParams();
                                MyToash.Log("  cl=" + temp2 + "   " + Double.parseDouble(detailEntities.get(i).getCalory()));
                                linearParams.height = (int) (gap * (Double.parseDouble(detailEntities.get(i).getCalory()) / (temp2))); //
                                bts.get(i).setLayoutParams(linearParams); // 使设置好的布局参数应用到控件aaa
                                ts.get(i).setText(detailEntities.get(i).getDay());

                            }
                        }

                    } else {
                        h.sendEmptyMessageDelayed(1, 1);
                    }
                }
            }catch (Exception e){
                h.sendEmptyMessageDelayed(1, 1);
            }
        }
    };

    draw d;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    public class MyTestView extends View {
        //构造器
        public MyTestView(Context context) {
            super(context);
            // TODO Auto-generated constructor stub
        }

        @Override
        protected void onDraw(Canvas canvas) {
            // TODO Auto-generated method stub
            super.onDraw(canvas);
            //绘制黑色背景
        /*    canvas.drawColor(Color.BLACK);*/
            //创建画笔
            Paint paint = new Paint();
            //设置画笔颜色为红色
            paint.setStyle(Paint.Style.STROKE);
            paint.setAntiAlias(true);
            paint.setColor(Color.parseColor("#42c3f7"));
            canvas.drawCircle(mRoundProgressBargap / 2, mRoundProgressBargap / 2, mRoundProgressBargap / 2, paint);// 小圆
        }

    }


}