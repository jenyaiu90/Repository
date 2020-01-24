package ru.samsung.itschool.jenyaiu90.birds;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;

public class DrawThread extends Thread
{
	private SurfaceHolder surfaceHolder;
	private volatile boolean running = true;
	public Sprite playerBird, enemyBird;
	public int points, viewWidth, level;
	public boolean over, pause;
	public DrawThread(Context context, SurfaceHolder surfaceHolder)
	{
		this.surfaceHolder = surfaceHolder;
	}
	public void requestStop()
	{
		running = false;
	}
	@Override
	public void run()
	{
		while (running)
		{
			Canvas canvas = surfaceHolder.lockCanvas();
			if (canvas != null)
			{
				try
				{
					Paint p = new Paint();
					p.setAntiAlias(true);
					p.setTextSize(55.f);
					p.setColor(Color.WHITE);
					canvas.drawARGB(250, 127, 199, 255);
					playerBird.draw(canvas);
					enemyBird.draw(canvas);
					canvas.drawText(points + "", viewWidth - 100, 70, p);
					canvas.drawText(level + "", 300, 70, p);
					if (over)
					{
						canvas.drawText("Game over", 200, 200, p);
					}
					else if (pause)
					{
						canvas.drawText("Pause", 200, 200, p);
					}
				}
				finally
				{
					surfaceHolder.unlockCanvasAndPost(canvas);
				}
			}
		}
	}
}
