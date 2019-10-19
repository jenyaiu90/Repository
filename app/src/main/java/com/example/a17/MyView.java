package com.example.a17;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

public class MyView extends View
{
	double xe, ye, w, x, y, h = 0.1, xmin = -4, xmax = 4;
	int k = 100;
	public MyView(Context context)
	{
		super(context);
	}
	@Override
	protected void onDraw(Canvas canvas)
	{
		int x0 = canvas.getWidth() / 2;
		int y0 = canvas.getHeight() / 2;

		Paint paint = new Paint();
		paint.setStrokeWidth(5);
		paint.setColor(Color.BLACK);
		paint.setStyle(Paint.Style.STROKE);
		paint.setTextSize(30);

		canvas.drawLine(x0, 0, x0, canvas.getHeight(), paint);
		canvas.drawLine(0, y0, canvas.getWidth(), y0, paint);
		canvas.drawLine(x0, 0, x0 - 10, 20, paint);
		canvas.drawLine(x0, 0, x0 + 10, 20, paint);
		canvas.drawLine(canvas.getWidth(), y0, canvas.getWidth() - 20, y0 - 10, paint);
		canvas.drawLine(canvas.getWidth(), y0, canvas.getWidth() - 20, y0 + 10, paint);
		canvas.drawLine(x0 + k, y0 - 10, x0 + k, y0 + 10, paint);
		canvas.drawLine(x0 - 10, y0 - k, x0 + 10, y0 - k, paint);
		canvas.drawText("Y", x0 - 40, 35, paint);
		canvas.drawText("X", canvas.getWidth() - 40, y0 + 40, paint);
		canvas.drawText("0", x0 + 10, y0 + 30, paint);
		paint.setTextAlign(Paint.Align.CENTER);
		canvas.drawText("1", x0 + k, y0 - 30, paint);
		canvas.drawText("1", x0 - 30, y0 - k, paint);

		Path path = new Path();
		boolean first = true;
		paint.setColor(Color.BLUE);
		for (x = xmin; x < xmax; x += h)
		{
			y = Math.abs(x);
			xe = x0 + k * x;
			ye = y0 - k * y;
			if (first)
			{
				path.moveTo((float)xe, (float)ye);
				first = false;
			}
			else
			{
				path.lineTo((float)xe, (float)ye);
			}
			canvas.drawPath(path, paint);
		}
		Path path2 = new Path();
		first = true;
		paint.setColor(Color.RED);
		for (x = xmin; x < xmax; x += h)
		{
			y = -x * x + 3;
			xe = x0 + k * x;
			ye = y0 - k * y;
			if (first)
			{
				path2.moveTo((float)xe, (float)ye);
				first = false;
			}
			else
			{
				path2.lineTo((float)xe, (float)ye);
			}
			canvas.drawPath(path2, paint);
		}
	}
}
