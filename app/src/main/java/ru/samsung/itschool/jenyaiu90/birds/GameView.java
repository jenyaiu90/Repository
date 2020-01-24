package ru.samsung.itschool.jenyaiu90.birds;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public class GameView extends SurfaceView implements SurfaceHolder.Callback
{
	class Timer extends CountDownTimer
	{
		public Timer()
		{
			super(Integer.MAX_VALUE, timerInterval);
		}
		@Override
		public void onTick(long millisUntilFinished)
		{
			update();
		}
		@Override
		public void onFinish()
		{

		}
	}

	private int viewWidth, viewHeight;
	private int points = 0;
	private Sprite playerBird, enemyBird;
	private final int timerInterval = 30;
	private boolean bonus, two;
	private boolean over, pause;
	private int level = 1;
	private DrawThread dt;
	@Override
	public void surfaceCreated(SurfaceHolder holder)
	{
	}
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
	{
		// изменение размеров SurfaceView
	}
	@Override
	public void surfaceDestroyed(SurfaceHolder holder)
	{
		dt.requestStop();
		boolean retry = true;
		while (retry)
		{
			try
			{
				dt.join();
				retry = false;
			}
			catch (InterruptedException e)
			{
				//
			}
		}
	}
	public GameView(Context context)
	{
		super(context);
		getHolder().addCallback(this);
		Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.player);
		int w = b.getWidth() / 5;
		int h = b.getHeight() / 3;
		Rect firstFrame = new Rect(0, 0, w, h);
		playerBird = new Sprite(10, 0, 0, 100, firstFrame, b);
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 4; j++)
			{
				if (i == 0 && j == 0)
				{
					continue;
				}
				if (i == 2 && j == 3)
				{
					continue;
				}
				playerBird.addFrame(new Rect(j * w, i * h, j * w + w, i * w + w));
			}
		}
		teleportEnemy();
		dt = new DrawThread(getContext(),getHolder());
		dt.start();
		Timer timer = new Timer();
		timer.start();
	}
	@Override
	public void onSizeChanged(int w, int h, int oldw, int oldh)
	{
		super.onSizeChanged(w, h, oldw, oldh);
		viewWidth = w;
		viewHeight = h;
	}
	@Override
	public void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
	}
	protected void update()
	{
		if (over) return;
		if (pause)
		{
			invalidate();
			return;
		}
		playerBird.update(timerInterval);
		enemyBird.update(timerInterval);
		invalidate();
		if (playerBird.getY() + playerBird.getFrameHeight() > viewHeight)
		{
			playerBird.setY(viewHeight - playerBird.getFrameHeight());
			playerBird.setVy(-playerBird.getVy());
			points--;
		}
		else if (playerBird.getY() < 0)
		{
			playerBird.setY(0);
			playerBird.setVy(-playerBird.getVy());
			points--;
		}
		if (enemyBird.getX() < -enemyBird.getFrameWidth())
		{
			if (!bonus)
			{
				points += 10;
			}
			teleportEnemy();
		}
		if (enemyBird.intersect(playerBird))
		{
			if (bonus)
			{
				points += 10;
			}
			else
			{
				points -= 40;
			}
			teleportEnemy();
		}
		if (points >= 50)
		{
			nextLevel();
		}
		else if (points <= -50)
		{
			over = true;
		}
		dt.playerBird = playerBird;
		dt.enemyBird = enemyBird;
		dt.points = points;
		dt.viewWidth = viewWidth;
		dt.level = level;
		dt.over = over;
		dt.pause = pause;
	}
	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		int eventAction = event.getAction();
		if (eventAction == MotionEvent.ACTION_DOWN)
		{
			if (!bonus && two &&
				event.getY() > enemyBird.getY() && event.getY() < enemyBird.getY() + enemyBird.getFrameHeight() &&
				event.getX() > enemyBird.getX() && event.getX() < enemyBird.getX() + enemyBird.getFrameWidth())
			{
				teleportEnemy();
				points += 10;
			}
			else if (event.getY() > playerBird.getY() && event.getY() < playerBird.getY() + playerBird.getFrameHeight() &&
				event.getX() > playerBird.getX() && event.getX() < playerBird.getX() + playerBird.getFrameWidth())
			{
				pause = !pause;
			}
			else if (event.getY() < playerBird.getBoundingBoxRect().top)
			{
				playerBird.setVy(-80 - 20 * level);
				points--;
			}
			else if (event.getY() > (playerBird.getBoundingBoxRect().bottom))
			{
				playerBird.setVy(100 + 20 * level);
				points--;
			}
		}
		return true;
	}
	private void nextLevel()
	{
		points = 0;
		level++;
		if (playerBird.getVy() > 0)
		{
			playerBird.setVy(80 + 20 * level);
		}
		else
		{
			playerBird.setVy(-80 - 20 * level);
		}
		teleportEnemy();
	}
	private void teleportEnemy()
	{
		Bitmap b;
		int h, w;
		Rect firstFrame;
		bonus = Math.random() * 2 < 1;
		if (bonus)
		{
			b = BitmapFactory.decodeResource(getResources(), R.drawable.bonus);
			w = b.getWidth();
			h = b.getHeight();
			firstFrame = new Rect(0, 0, w, h);
		}
		else
		{
			two = Math.random() * 2 < 1;
			if (two)
			{
				b = BitmapFactory.decodeResource(getResources(), R.drawable.enemy2);
			}
			else
			{
				b = BitmapFactory.decodeResource(getResources(), R.drawable.enemy);
			}
			w = b.getWidth() / 5;
			h = b.getHeight() / 3;
			firstFrame = new Rect(4 * w, 0, 5 * w, h);
		}
		enemyBird = new Sprite(2000, 250, -340 - 60 * level, 0, firstFrame, b);
		if (!bonus)
		{
			for (int i = 0; i < 3; i++)
			{
				for (int j = 4; j >= 0; j--)
				{
					if (i == 0 && j == 4)
					{
						continue;
					}
					if (i == 2 && j == 0)
					{
						continue;
					}
					enemyBird.addFrame(new Rect(j * w, i * h, j * w + w, i * w + w));
				}
			}
		}
		enemyBird.setX(viewWidth + Math.random() * 500);
		enemyBird.setY(Math.random() * (viewHeight - enemyBird.getFrameHeight()));
	}
}
