package ru.samsung.itschool.jenyaiu90.birds;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public class GameView extends View
{
	public GameView(Context context)
	{
		super(context);
	}
	@Override
	public void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		Paint p = new Paint();

		p.setColor(Color.YELLOW);
		p.setStyle(Paint.Style.FILL);
		canvas.drawCircle(0, 0, 100, p);
		for (double i = 0.1; i < Math.PI / 2; i += 0.2)
		{
			canvas.drawLine(0, 0, (int)(300 * Math.sin(i)), (int)(300 * Math.cos(i)), p);
		}

		p.setColor(Color.GREEN);
		canvas.drawRect(0, getHeight() - 200, getWidth(), getHeight(), p);

		p.setColor(Color.GRAY);
		canvas.drawRect(getWidth() - 120, getHeight() - 75, getWidth() - 100, getHeight() - 25, p);
		canvas.drawRect(getWidth() - 200, getHeight() - 75, getWidth() - 180, getHeight() - 25, p);
		canvas.drawRect(getWidth() - 225, getHeight() - 95, getWidth() -  75, getHeight() - 75, p);

		p.setColor(Color.rgb(150, 75, 0));
		canvas.drawRect(getWidth() / 2 - 25, getHeight() - 200, getWidth() / 2 + 25, getHeight() - 50, p);
		p.setColor(Color.rgb(0, 150, 0));
		canvas.drawOval(getWidth() / 2 - 100, 100, getWidth() / 2 + 100, getHeight() - 150, p);

		p.setColor(Color.rgb(150, 75, 0));
		canvas.drawRect(100, getHeight() - 400, 500, getHeight() - 100, p);
		Path path = new Path();
		path.moveTo(100, getHeight() - 400);
		path.lineTo(300, getHeight() - 600);
		path.lineTo(500, getHeight() - 400);
		path.close();
		canvas.drawPath(path, p);

		p.setColor(Color.WHITE);
		for (int i = 100; i < 400; i += 25)
		{
			canvas.drawLine(350, getHeight() - i, 450, getHeight() - i + 100, p);
		}
		p.setColor(Color.GREEN);
		canvas.drawRect(350, getHeight() - 100, 450, getHeight(), p);
		p.setColor(Color.rgb(150, 75, 0));
		canvas.drawRect(350, getHeight() - 400, 450, getHeight() - 300, p);
		p.setColor(Color.BLACK);
		p.setStyle(Paint.Style.STROKE);
		canvas.drawRect(350, getHeight() - 300, 450, getHeight() - 100, p);

		p.setColor(Color.BLUE);
		for (int i = 10; i < 100; i += 20)
		{
			canvas.drawLine(150 + i, getHeight() - 300, 150 + i, getHeight() - 200, p);
			canvas.drawLine(150, getHeight() - 300 + i, 250, getHeight() - 300 + i, p);
		}
		p.setColor(Color.BLACK);
		canvas.drawRect(150, getHeight() - 300, 250, getHeight() - 200, p);

		p.setColor(Color.BLUE);
		for (double i = Math.PI * 1.25; i > Math.PI * 0.25; i -= 0.2)
		{
			canvas.drawLine((int)(300 + Math.cos(i) * 50), (int)(getHeight() - 500 + Math.sin(i) * 50), (int)(300 + Math.cos(Math.PI * 2.5 - i) * 50), (int)(getHeight() - 500 + Math.sin(Math.PI * 2.5 - i) * 50), p);
		}
		p.setColor(Color.BLACK);
		canvas.drawCircle(300, getHeight() - 500, 50, p);
	}
}
