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
	Paint paint = new Paint();
	float x = 0;
	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		canvas.drawCircle(x, 300, 20, paint);
		x += 0.5f;
		invalidate();
	}
}
