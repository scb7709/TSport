package me.lam.maidong.line;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import me.lam.maidong.entity.PointVsRata;

public class MyView extends View {
    //由手机决定
    private int XPoint = 0;
    //起始点由手机决定
    private int YPoint = 0;

    // 刻度长度暂时不用
    private int YScale = 42;

    //获取x宽度，然后运算得出
    private int XLength = 0;

    //x的刻度由XLength决定
    private int XScale = 0;
    //由手机决定
    private int YLength = 0;
    private List<Integer> data = new ArrayList<Integer>();
    //private String[] YLabel = new String[YLength / YScale];

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            Log.e("step", 5 + "");
            if (msg.what == 0x1234) {
                MyView.this.invalidate();
            }
            if (msg.what == 1) {
                Toast.makeText(getContext(), "ADSAS", Toast.LENGTH_LONG);
            }

        }

        ;
    };
    public static Back back;

    public void setOnShow(Back onShow) {
        this.back = onShow;
    }

    public interface Back {
        void Back(List<PointVsRata> poiintData);
    }


    public MyView(Context context) {
        super(context);

    }


    FrameLayout ll;
    Context context;

    public MyView(FrameLayout ll, int XPoint, int YPoint, int with, int YLength, Context context, final int a, final int b, final int c, final int d, final int e, final int f, final int g, final int h, final int i, final int j, final int k, final int l, final int m, final int n, final int o, final int p, final int q, final int r, final int s, final int t, final int u, final int v, final int w, final int x, final int y, final int z, final int tt, final int ii, final int mm, final int ee) {
        super(context);
        Log.e("zuobiao", YLength + "cccccc");
        this.ll = ll;
        this.context = context;
        this.XPoint = XPoint;
        this.YPoint = YPoint;
        this.YLength = YLength;
        this.XLength = with / 2 - 30;
        this.XScale = (XLength / 30) * 2 + 2;
        Log.e("step", 2 + "");
        new Thread(new Runnable() {

            @Override
            public void run() {
                Log.e("step", 3 + "");
                int i = 0;
                while (true) {
                    Log.e("step", 4 + "");
                    try {
                        Thread.sleep(80);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    /*if (data.size() >= MaxDataSize) {
                        data.remove(0);
					}*/
                    // data.add(new Random().nextInt(4) + 1);

                    if (i == 0) {

                        data.add(a);
                    }
                    if (i == 1) {

                        data.add(b);
                    }
                    if (i == 2) {

                        data.add(c);
                    }
                    if (i == 3) {

                        data.add(d);
                    }
                    if (i == 4) {

                        data.add(e);
                    }
                    if (i == 5) {

                        data.add(f);
                    }
                    if (i == 6) {

                        data.add(g);
                    }
                    if (i == 7) {

                        data.add(h);
                    }
                    if (i == 8) {

                        data.add(i);
                    }
                    if (i == 9) {

                        data.add(j);
                    }
                    if (i == 10) {

                        data.add(k);
                    }
                    if (i == 11) {

                        data.add(l);
                    }
                    if (i == 12) {

                        data.add(m);
                    }
                    if (i == 13) {

                        data.add(n);
                    }
                    if (i == 14) {

                        data.add(o);
                    }
                    if (i == 15) {

                        data.add(p);
                    }
                    if (i == 16) {

                        data.add(q);
                    }
                    if (i == 17) {

                        data.add(r);
                    }
                    if (i == 18) {

                        data.add(s);
                    }
                    if (i == 19) {

                        data.add(t);
                    }
                    if (i == 20) {
                        data.add(u);
                    }
                    if (i == 21) {

                        data.add(v);
                    }
                    if (i == 22) {

                        data.add(w);
                    }
                    if (i == 23) {

                        data.add(x);
                    }
                    if (i == 24) {

                        data.add(y);
                    }
                    if (i == 25) {

                        data.add(z);
                    }
                    if (i == 26) {
                        data.add(tt);
                    }
                    if (i == 27) {

                        data.add(ii);
                    }
                    if (i == 28) {

                        data.add(mm);
                    }
                    if (i == 29) {

                        data.add(ee);
                    }
                    if (i == 30) {
                        return;
                    }
                    i++;
                    handler.sendEmptyMessage(0x1234);

                }
            }
        }).start();
    }

    public static List<PointVsRata> poiintData;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e("zuobiao差", "x1:" + YLength);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true); // 去锯齿
        paint.setColor(Color.WHITE);

        // Y轴添加刻度和文字
        for (int i = 0; i * YScale < YLength; i++) {
			/*// 刻度
			canvas.drawLine(XPoint, YPoint - i * YScale, XPoint + 5, YPoint - i
					* YScale, paint); */
            if (i == 0) {
                canvas.drawText("", XPoint - 50, YPoint - i * YScale, paint);
                continue;
            }
            // 文字
            //canvas.drawText(i+"00次", XPoint - 50, YPoint - i * YScale, paint);
        }
        poiintData = new ArrayList<>();
        paint.setStyle(Paint.Style.FILL);
        if (data.size() > 1) {
            Path path = new Path();
            Log.e("zuobiao差ccccc", "x1:" + YLength);
            path.moveTo(0, 0);
            for (int i = 0; i < data.size(); i++) {
                PointVsRata pointVsRata = new PointVsRata();
                if (data.size() == 30) {
                    pointVsRata.setXpoint(((XPoint + i * XScale) - XPoint));
                    pointVsRata.setRata(data.get(i));
                    poiintData.add(pointVsRata);
                }
                if (i == 8) {
                    path.lineTo((XPoint + i * XScale) - XPoint, (YLength - (data.get(i - 1) * YLength) / 250));
                } else {
                    path.lineTo((XPoint + i * XScale) - XPoint, (YLength - (data.get(i) * YLength) / 250));
                }
            }
            path.lineTo(XPoint + (data.size() - 1) * XScale, 0);
            canvas.drawPath(path, paint);
            if (poiintData.size() == 30) {
                if (back != null) {
                    back.Back(poiintData);
                }
            }

        }
    }
}