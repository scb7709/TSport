package me.lam.maidong.circle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.RectF;
import android.view.View;

public class DrawView extends View {

	public DrawView(Context context) {
		super(context);
	}


	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		/*
		 * 方法 说明 drawRect 绘制矩形 drawCircle 绘制圆形 drawOval 绘制椭圆 drawPath 绘制任意多边形
		 * drawLine 绘制直线 drawPoin 绘制点
		 */
		// 创建画笔
		Paint p = new Paint();
		p.setAntiAlias(true);// 设置画笔的锯齿效果。 true是去除
		p.setColor(Color.GRAY);// 设置红色
		// 设置画笔宽度
		p.setStrokeWidth(3);
		// 设置镂空（方便查看效果）
		p.setStyle(Style.STROKE);

		Path path = new Path();
		path.moveTo(140, 135);
		path.lineTo(240, 135);
		path.lineTo(298, 178);

		// path.lineTo(150, 250); */

		canvas.drawPath(path, p);
		Path path2 = new Path();
		path2.moveTo(450, 178);
		path2.lineTo(538, 135);

		path2.lineTo(638, 135);
		canvas.drawPath(path2, p);

	}
}