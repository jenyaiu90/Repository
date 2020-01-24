package com.example.calcapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    private EditText ETs[];
    private TextView resultTV;
    private Button pB, sB, mB, dB;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ETs = new EditText[2];
        ETs[0] = (EditText)findViewById(R.id.arg1);
        ETs[1] = (EditText)findViewById(R.id.arg2);
        resultTV = (TextView)findViewById(R.id.answer);
        pB = (Button)findViewById(R.id.add);
        sB = (Button)findViewById(R.id.subtr);
        mB = (Button)findViewById(R.id.mul);
        dB = (Button)findViewById(R.id.divide);
    }
    public void click(View view)
    {
        for (int i = 0; i < 2; i++)
        {
            String str = ETs[i].getText().toString();
            for (int j = 0; j < str.length(); j++)
            {
                char t = str.charAt(j);
                if (t != '0' && t != '1' && t != '2' &&
                    t != '3' && t != '4' && t != '5' &&
                    t != '6' && t != '7' && t != '8' && t != '9')
                {
                    resultTV.setText(R.string.error);
                    return;
                }
            }
        }
        if (view == pB)
            resultTV.setText(Integer.toString(Integer.parseInt(ETs[0].getText().toString()) +
                Integer.parseInt(ETs[1].getText().toString())));
        else if (view == sB)
            resultTV.setText(Integer.toString(Integer.parseInt(ETs[0].getText().toString()) -
                Integer.parseInt(ETs[1].getText().toString())));
        else if (view == mB)
            resultTV.setText(Integer.toString(Integer.parseInt(ETs[0].getText().toString()) *
                Integer.parseInt(ETs[1].getText().toString())));
        else
        {
            if (Integer.parseInt(ETs[1].getText().toString()) == 0)
            {
                resultTV.setText(R.string.dbz);
                return;
            }
            resultTV.setText(Integer.toString(Integer.parseInt(ETs[0].getText().toString()) /
                Integer.parseInt(ETs[1].getText().toString())));
        }
    }
}