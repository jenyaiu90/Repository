package com.example.a17;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class MyView extends View
{
	public MyView(Context context)
	{
		super(context);
	}
	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		Paint paint = new Paint();
		paint.setColor(Color.RED);
		paint.setStrokeWidth(5);
		paint.setStyle(Paint.Style.STROKE);
		for (int i = 10; i < canvas.getHeight() + canvas.getWidth() - 1; i += 10)
		{
			canvas.drawLine(i > canvas.getHeight() ? i - canvas.getHeight() : 0,
				i > canvas.getHeight() ? canvas.getHeight() : i,
				i > canvas.getWidth() ? canvas.getWidth() : i,
				i > canvas.getWidth() ? i - canvas.getWidth() : 0, paint);
		}
	}
}
