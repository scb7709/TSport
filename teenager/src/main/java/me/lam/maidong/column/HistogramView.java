package me.lam.maidong.column;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.Align;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import me.lam.maidong.R;


public class HistogramView extends View {

	private Paint xLinePaint;// 坐标轴 轴线 画笔：
	private Paint hLinePaint;// 坐标轴水平内部 虚线画笔
	private Paint titlePaint;// 绘制文本的画笔
	private Paint paint;// 矩形画笔 柱状图的样式信息
	private int[] progress;// 7 条
	private int[] aniProgress;// 实现动画的值
	private final int TRUE = 1;// 在柱状图上显示数字
	private int[] text;
	// 坐标轴左侧的数标
	private String[] ySteps;
	// 坐标轴底部的星期数
	private String[] xWeeks;

	private HistogramAnimation ani;

	public HistogramView(Context context) {
		super(context);
		init(context, null);
	}

	public HistogramView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init(context, attrs);
	}
	private void init(Context context, AttributeSet attrs) {

		xWeeks = new String[] { "低强度运动", "中强度运动", "高强度运动" };
		text = new int[] { 0, 0, 0 };
		aniProgress = new int[] { 0, 0, 0 };
		ani = new HistogramAnimation();
		//设置动画效果时间
		ani.setDuration(800);

				xLinePaint = new Paint();
				hLinePaint = new Paint();
				titlePaint = new Paint();
				paint = new Paint();

				xLinePaint.setColor(Color.DKGRAY);
				hLinePaint.setColor(Color.LTGRAY);
				titlePaint.setColor(Color.BLACK);
		        titlePaint.setTextSize(11.5f);
			}

		public void setText(int[] text) {

			this.text = text;
			this.postInvalidate();// 可以子线程 更新视图的方法调用。
		}
		public Handler dcHandler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				if(msg.what==1){
				postInvalidate();
			}if(msg.what==2){
				HistogramView.this.startAnimation(ani);
			}


		}
	};
	int gap;
	public void setProgress(final int[] progress,final int gap) {
		Log.e("ff"," 开启动画执行多次了？");
		this.gap=gap;
		this.progress = progress;
		//this.invalidate(); //失效的意思。
		 //this.postInvalidate(); // 可以子线程 更新视图的方法调用。
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Message msg=new Message();
				msg.what=2;
				dcHandler.sendMessage(msg);

			}
		}).start();

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return super.onTouchEvent(event);
	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);

		int width = getWidth();
		int height = getHeight();
		Log.e("height",height+"");
		Log.e("weight",width+"");

		// 1 绘制坐标线：startX, startY, stopX, stopY, paint
		int startX = dip2px(getContext(), 50);
		int startY = dip2px(getContext(), 10);
		int stopX = dip2px(getContext(), 50);
		int stopY = dip2px(getContext(), 320);
	/*	canvas.drawLine(50, 10, 50, height, xLinePaint);*/

		//canvas.drawLine(45, height, width - 10, height, xLinePaint);

		// 2 绘制坐标内部的水平线

		int leftHeight = height;// 左侧外周的 需要划分的高度：

		int hPerHeight = leftHeight / 4;// 分成四部分

		hLinePaint.setTextAlign(Align.CENTER);
	/*	for (int i = 0; i < 4; i++) {
			canvas.drawLine(50, 20 + i * hPerHeight, width - 10, 20 + i
					* hPerHeight, hLinePaint);
		}*/

		// 3 绘制 Y 周坐标

		/*titlePaint.setTextAlign(Align.RIGHT);
		titlePaint.setTextSize(20);
		titlePaint.setAntiAlias(true);
		titlePaint.setStyle(Paint.Style.FILL);*/
		//画y轴
		/*for (int i = 0; i < ySteps.length; i++) {
			canvas.drawText(ySteps[i], 40, 20 + i * hPerHeight, titlePaint);
		}*/

		// 4 绘制 X 周 做坐标
		int xAxisLength = width - 30;
		int columCount = xWeeks.length + 1;
		int step = xAxisLength / columCount;

		for (int i = 0; i < columCount - 1; i++) {
			// text, baseX, baseY, textPaint
		//	canvas.drawText(xWeeks[i], 55 + step * (i + 1)-60, height+30, titlePaint);
		}

		// 5 绘制矩形
		if (aniProgress != null && aniProgress.length > 0) {
			for (int i = 0; i < aniProgress.length; i++) {// 循环遍历将3条柱状图形画出来
				int value = aniProgress[i];
			 	paint.setAntiAlias(true);// 抗锯齿效果
				paint.setStyle(Paint.Style.FILL);
				paint.setTextSize(20);// 字体大小
				paint.setColor(Color.parseColor("#6DCAEC"));// 字体颜色
				Rect rect = new Rect();// 柱状图的形状
				if(aniProgress[i]==0){
					rect.left = 0;
					rect.right = 0;
				}else{
					//设置左起
					rect.left =  step * (i + 1) - 18;
					//设置右止

					rect.right = 30 + step * (i + 1) + 18;

				}
				int rh = (int) value;
				Log.e("data",rh+"");
				//改变算法
				rect.top = gap-rh ;
				rect.bottom = height;
				Log.e("kuan","上部"+(rect.top)+"-------"+"底部"+rect.bottom );
				if(i ==0){
					Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
							R.drawable.bg_low_move);
					canvas.drawBitmap(bitmap, null, rect, paint);
				}if(i ==1){
					Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
							R.drawable.bg_medium_move);
					canvas.drawBitmap(bitmap, null, rect, paint);
				}
				if(i ==2){
					Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
							R.drawable.bg_high_move);
					canvas.drawBitmap(bitmap, null, rect, paint);
				}




				if (this.text[i] == TRUE) {
					canvas.drawText(value + "", 30 + step * (i + 1) - 30,
							rh + 10, paint);
				}

			}
		}

	}

	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}


	private class HistogramAnimation extends Animation {
		@Override
		protected void applyTransformation(float interpolatedTime,
										   Transformation t) {
			Log.e("gg","postInvalidate执行le??");
			super.applyTransformation(interpolatedTime, t);
			if (interpolatedTime < 1.0f) {
				for (int i = 0; i < aniProgress.length; i++) {
					aniProgress[i] = (int) (progress[i] * interpolatedTime);
				}
			} else {
				for (int i = 0; i < aniProgress.length; i++) {
					aniProgress[i] = progress[i];
				}
			}

			new Thread(new Runnable() {
				@Override
				public void run() {
					Message msg=new Message();
					msg.what=1;
					dcHandler.sendMessage(msg);
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}).start();

		}
	}

}