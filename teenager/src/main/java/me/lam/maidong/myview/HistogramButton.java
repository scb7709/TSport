package me.lam.maidong.myview;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.RelativeLayout;



/**
 * Created by abc on 2017/12/28.
 */
public class HistogramButton extends Button {
    private int BackgroundColar;
    private RelativeLayout.LayoutParams linearParams1;
    private float[] innerRadii = {60, 60, 60, 60, 0, 0, 0, 0};//内矩形 圆角半径
    private RoundRectShape roundRectShape;
    private ShapeDrawable drawable;
    private int CorrentHeight;

    public HistogramButton(Context context) {
        super(context);
    }

    public HistogramButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HistogramButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setBackgroundResource(int resid) {
        super.setBackgroundResource(resid);

    }

    public void setHistogram(int BackgroundColar, final int MaxHeight,RelativeLayout.LayoutParams linearParams1) {
        this.linearParams1 =linearParams1;
        drawable.getPaint().setColor(BackgroundColar);
        drawable.getPaint().setAntiAlias(true);
        // 指定填充模式
        drawable.getPaint().setStyle(Paint.Style.FILL);
        drawable.getPaint().setColor(BackgroundColar);
        setBackground(drawable);

        for (int i = 0; i <= MaxHeight; i++) {
            CorrentHeight = i;
            linearParams1.height = CorrentHeight;
            setLayoutParams(linearParams1);
          /*  try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
        }


     /*   linearParams1.post(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <= MaxHeight; i++) {
                    CorrentHeight = i;
                    handler.sendEmptyMessage(0);
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });*/
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            linearParams1.height = CorrentHeight;
            setLayoutParams(linearParams1);
            //invalidate();
        }
    };

    void init() {
        roundRectShape = new RoundRectShape(innerRadii, null, null); //无内矩形
        drawable = new ShapeDrawable(roundRectShape);

    }
}
