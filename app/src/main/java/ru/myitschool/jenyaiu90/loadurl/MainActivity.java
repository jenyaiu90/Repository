package ru.myitschool.jenyaiu90.loadurl;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;

public class MainActivity extends AppCompatActivity
{
	ImageView imageV;
	TextView textV;
	Handler imageHandler;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		imageV = (ImageView)findViewById(R.id.imageV);
		textV = (TextView)findViewById(R.id.textV);
		imageHandler = new Handler()
		{
			@Override
			public void handleMessage(Message msg)
			{
				super.handleMessage(msg);
				InputStream content = (InputStream)msg.obj;
				if (content == null)
				{
					textV.setText(R.string.fail);
				}
				else
				{
					imageV.setImageBitmap(BitmapFactory.decodeStream(content));
				}
			}
		};
		Load load = new Load();
		load.start();
	}
	private class Load extends Thread
	{
		@Override
		public void run()
		{
			try
			{
				URL url = new URL("https://myitschool.ru/book/pluginfile.php/1/core_admin/logocompact/0x70/1577264997/IT%20School%20logo.jpg");
				Message msg = new Message();
				msg.obj = url.getContent();
				imageHandler.dispatchMessage(msg);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}
