package com.example.a17;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.CountDownTimer;
import android.view.View;

@SuppressLint("DrawAllocation")
public class MyView extends View
{
	boolean first = true;
	int N = 2, M = 100; //Количество планет, масса Солнца
	double[] x = new double[N], y = new double[N]; //Координаты планет
	double[] vx = new double[N], vy = new double[N]; //Скорости планет
	int[] Red = new int[N], Green = new int[N], Blue = new int[N]; //Цвета планет
	int[] m = new int[N]; //Массы планет

	public MyView(Context context)
	{
		super(context);
		MyTimer timer = new MyTimer();
		timer.start();
	}

	void fillArrayRandom(int[] a, int min, int max)
	{
		for (int i = 0; i < a.length; i++)
		{
			a[i] = (int)(Math.random() * (max - min + 1) + min);
		}
	}

	void fillArrayRandom(double[] a, int min, int max)
{
	for (int i = 0; i < a.length; i++)
	{
		a[i] = (int)(Math.random() * (max - min + 1) + min);
	}
}

	void makeBalls()
	{
		fillArrayRandom(x, 0, this.getWidth());
		fillArrayRandom(y, 0, this.getHeight());
		for (int i = 0; i < N; i++)
		{
			//Изначально планеты неподвижны
			vx[i] = 0;
			vy[i] = 0;
		}
		fillArrayRandom(m, 10, 50);
		fillArrayRandom(Red, 50, 255);
		fillArrayRandom(Green, 50, 255);
		fillArrayRandom(Blue, 50, 255);
	}

	void moveBalls()
	{
		for (int i = 0; i < N; i++)
		{
			double dx, dy, A, Ax = 0, Ay = 0;
			dx = this.getWidth() / 2 - x[i];
			dy = this.getHeight() / 2 - y[i];
			//Если планета не слишком близко к Солнцу
			if (Math.abs(dx) + Math.abs(dy) >= 10)
			{
				A = M / ((Math.pow(dx, 2) + Math.pow(dy, 2)) / 20); //Ускорение, придаваемое планете Солнцем
				Ax = (dx < 0 ? -1 : 1) * A * (dx == 0 ? 0 : Math.cos(Math.atan(dy / dx)));
				Ay = (dy < 0 ? -1 : 1) * A * (dy == 0 ? 0 : Math.cos(Math.atan(dx / dy)));
			}
			for (int j = 0; j < N; j++)
			{
				if (i == j)
				{
					continue;
				}
				dx = x[j] - x[i];
				dy = y[j] - y[i];
				//Если планеты слишком близко
				if (Math.abs(dx) + Math.abs(dy) < 10)
				{
					continue;
				}
				A = m[j] / ((Math.pow(dx, 2) + Math.pow(dy, 2)) / 20); //Ускорение, придаваемое планете №i планетой №j
				Ax += (dx < 0 ? -1 : 1) * A * (dx == 0 ? 0 : Math.cos(Math.atan(dy / dx)));
				Ay += (dy < 0 ? -1 : 1) * A * (dy == 0 ? 0 : Math.cos(Math.atan(dx / dy)));
			}
			vx[i] += Ax;
			vy[i] += Ay;
			if (x[i] <= m[i] || x[i] >= this.getWidth() - m[i]) //Столкновение с границами экрана
			{
				vx[i] = -vx[i];
				if (x[i] <= m[i]) //Радиус планеты равен массе
				{
					x[i] = m[i];
				}
				else
				{
					x[i] = this.getWidth() - m[i];
				}
				//Скорость уменьшается на 10% (иначе на экране творится какая-то вакханалия)
				vx[i] *= 0.8;
				vy[i] *= 0.8;
			}
			if (y[i] <= m[i] || y[i] >= this.getHeight() - m[i])
			{
				vy[i] = -vy[i];
				if (y[i] <= m[i])
				{
					y[i] = m[i];
				}
				else
				{
					y[i] = this.getHeight() - m[i];
				}
				vx[i] *= 0.8;
				vy[i] *= 0.8;
			}
			x[i] += vx[i];
			y[i] += vy[i];
		}
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		if (first)
		{
			first = false;
			makeBalls();
		}
		Paint paint = new Paint();
		paint.setTextSize(30.0f);
		paint.setColor(Color.RED);
		canvas.drawCircle(canvas.getWidth() / 2, canvas.getHeight() / 2, M, paint);
		for (int i = 0; i < N; i++)
		{
			paint.setColor(Color.argb(200, Red[i], Green[i], Blue[i]));
			canvas.drawCircle((int)x[i], (int)y[i], m[i], paint);
			paint.setColor(Color.BLACK);
			canvas.drawText("P " + i + " (" + (int)x[i] + ", " + (int)y[i] + ")", (int)x[i] + 10, (int)y[i] - 15, paint);
		}
	}

	void nextFrame()
	{
		moveBalls();
		invalidate();
	}

	class MyTimer extends CountDownTimer
	{
		MyTimer()
		{
			super(1000000, 1);
		}
		@Override
		public void onTick(long millisUntilFinished)
		{
			nextFrame();
		}
		@Override
		public void onFinish()
		{

		}
	}
}
