package me.lam.maidong.circle;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.math.BigDecimal;

import me.lam.maidong.R;


/**
 * 仿iphone带进度的进度条，线程安全的View，可直接在线程中更新进度
 */
public class RoundProgressBar extends View {
    /**
     * 画笔对象的引用
     */
    private Paint paint;

    /**
     * 圆环的颜色
     */
    private int roundColor;

    /**
     * 圆环进度的颜色
     */
    private int roundProgressColor;

    /**
     * 中间进度百分比的字符串的颜色
     */
    private int textColor;

    /**
     * 中间进度百分比的字符串的字体
     */
    private float textSize;

    /**
     * 圆环的宽度
     */
    private float roundWidth;

    /**
     * 最大进度
     */


    public static int mmnun;

    public void setMmnun(int mmnun) {
        this.mmnun = mmnun;
    }


    /**
     * 当前进度
     */
    private int progress;
    /**
     * 是否显示中间的进度
     */
    private boolean textIsDisplayable;

    /**
     * 进度的风格，实心或者空心
     */
    private int style;

    public static final int STROKE = 0;
    public static final int FILL = 1;

    public RoundProgressBar(Context context, int mmnun) {
        this(context, null);
        this.mmnun = mmnun;

    }

    public RoundProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        paint = new Paint();


        TypedArray mTypedArray = context.obtainStyledAttributes(attrs,
                R.styleable.RoundProgressBar);

        //获取自定义属性和默认值
        roundColor = mTypedArray.getColor(R.styleable.RoundProgressBar_roundColor, Color.RED);
        roundProgressColor = mTypedArray.getColor(R.styleable.RoundProgressBar_roundProgressColor, Color.GREEN);
        textColor = mTypedArray.getColor(R.styleable.RoundProgressBar_textColor, Color.GREEN);
        textSize = mTypedArray.getDimension(R.styleable.RoundProgressBar_textSize, 15);
        roundWidth = mTypedArray.getDimension(R.styleable.RoundProgressBar_roundWidth, 5);
        Log.e("isNull", "mmnun" + mmnun);
        mmnun = mTypedArray.getInteger(R.styleable.RoundProgressBar_max, mmnun);
        textIsDisplayable = mTypedArray.getBoolean(R.styleable.RoundProgressBar_textIsDisplayable, true);
        style = mTypedArray.getInt(R.styleable.RoundProgressBar_style, 0);

        mTypedArray.recycle();
    }


    @TargetApi(Build.VERSION_CODES.N)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /**
         * 画最外层的大圆环
         */
        int centre = getWidth() / 2; //获取圆心的x坐标
        int radius = (int) (centre - roundWidth / 2); //圆环的半径
        paint.setColor(roundColor); //设置圆环的颜色
        paint.setStyle(Paint.Style.STROKE); //设置空心
        paint.setStrokeWidth(roundWidth); //设置圆环的宽度
        paint.setAntiAlias(true);  //消除锯齿
        canvas.drawCircle(centre, centre, radius, paint); //画出圆环

        Log.e("log", centre + "");

        /**
         * 画进度百分比 new BigDecimal(i).setScale(0, BigDecimal.ROUND_HALF_UP));
         */
        paint.setStrokeWidth(0);
        paint.setColor(textColor);
        paint.setTextSize(textSize);
        paint.setTypeface(Typeface.DEFAULT_BOLD); //设置字体
        //  String percent = new BigDecimal((float) (progress / mmnun) * 100).setScale(0, BigDecimal.ROUND_HALF_UP).toString();//((float)( progress/mmnun)) * 100
        double temp = (((double)progress / (double)mmnun) * 100);
        String percent=  Math.rint(temp)+"";
     //   String percent= new BigDecimal(temp).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue()+"";
        Log.i("percent",""+percent);
        if(percent.contains(".")){
            percent=percent.substring(0,percent.indexOf("."));
        }
        //  int percent = (int) ();  //中间的进度百分比，先转换成float在进行除法运算，不然都为0
        float textWidth = paint.measureText(percent + "%");   //测量字体宽度，我们需要根据字体的宽度设置在圆环中间

        if (textIsDisplayable/* && percent != 0*/ && style == STROKE) {
            canvas.drawText(percent + "%", centre - textWidth / 2, centre + textSize / 2, paint); //画出进度百分比
        }


        /**
         * 画圆弧 ，画圆环的进度
         */

        //设置进度是实心还是空心
        paint.setStrokeWidth(roundWidth); //设置圆环的宽度
        paint.setColor(roundProgressColor);  //设置进度的颜色
        RectF oval = new RectF(centre - radius, centre - radius, centre
                + radius, centre + radius);  //用于定义的圆弧的形状和大小的界限

        switch (style) {
            case STROKE: {
                paint.setStyle(Paint.Style.STROKE);
                Log.e("isNull", "mmnunDDDD" + mmnun);

                //   if (progress != 0 && mmnun != 0)
                mmnun = mmnun == 0 ? 1 : mmnun;
                canvas.drawArc(oval, 270, 360 * progress / mmnun, false, paint);  //根据进度画圆弧


                break;
            }
            case FILL: {
                paint.setStyle(Paint.Style.FILL_AND_STROKE);
                if (progress != 0 && mmnun != 0)
                    canvas.drawArc(oval, 270, 360 * progress / mmnun, true, paint);  //根据进度画圆弧
                break;
            }
        }

    }


    public synchronized int getMax() {
        return mmnun;
    }

    /**
     * 设置进度的最大值
     */
    public synchronized void setMax(int mmnun) {
        if (mmnun < 0) {
            throw new IllegalArgumentException("max not less than 0");
        }
        this.mmnun = mmnun;
    }

    /**
     * 获取进度.需要同步
     *
     * @return
     */
    public synchronized int getProgress() {
        return progress;
    }

    /**
     * 设置进度，此为线程安全控件，由于考虑多线的问题，需要同步
     * 刷新界面调用postInvalidate()能在非UI线程刷新
     *
     * @param progress
     */
    public synchronized void setProgress(int progress) {
        if (progress < 0) {
            throw new IllegalArgumentException("progress not less than 0");
        }
        if (progress > mmnun) {
            progress = mmnun;
        }
        if (progress <= mmnun) {
            this.progress = progress;
            Message msg = new Message();
            msg.what = 1;
            dcHandler.sendMessage(msg);

        }

    }

    public Handler dcHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                postInvalidate();
            }

        }
    };

    public int getCricleColor() {
        return roundColor;
    }

    public void setCricleColor(int cricleColor) {
        this.roundColor = cricleColor;
    }

    public int getCricleProgressColor() {
        return roundProgressColor;
    }

    public void setCricleProgressColor(int cricleProgressColor) {
        this.roundProgressColor = cricleProgressColor;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public float getRoundWidth() {
        return roundWidth;
    }

    public void setRoundWidth(float roundWidth) {
        this.roundWidth = roundWidth;
    }


}
