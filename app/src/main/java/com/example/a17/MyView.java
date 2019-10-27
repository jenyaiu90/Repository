package com.example.a17;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

public class MyView extends View
{

	int N = 50;
	float[] x  = new float[N];
	float[] y  = new float[N];
	float[] vx = new float[N];
	float[] vy = new float[N];

	float rand(float min, float max)
	{
		return (float)(Math.random() * (max - min + 1) + min);
	}

	void fillRandom(float[] array, float min, float max)
	{
		for (int i = 0; i < array.length; i++)
		{
			array[i] = rand(min, max);
		}
	}

	public MyView(Context context)
	{
		super(context);
		fillRandom(x, 0, 500);
		fillRandom(y, 0, 500);
		fillRandom(vx, -3, 3);
		fillRandom(vy, -3, 3);
	}

	void add(float[] a, float[] b)
	{
		for (int i = 0; i < a.length && i < b.length; i++)
		{
			a[i] += b[i];
		}
	}

	Paint paint = new Paint();

	@Override
	protected void onDraw(Canvas canvas)
	{
		for (int i = 0; i < N; i++)
		{
			canvas.drawCircle(x[i], y[i], 20, paint);
		}
		add(x, vx);
		add(y, vy);
		invalidate();
	}
}