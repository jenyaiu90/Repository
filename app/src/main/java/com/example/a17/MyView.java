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
		for (int i = 0; i < canvas.getHeight(); i += 10)
		{
			canvas.drawLine(0, i, canvas.getWidth(), i, paint);
		}
	}
}
