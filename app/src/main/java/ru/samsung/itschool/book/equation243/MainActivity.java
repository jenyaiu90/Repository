package ru.samsung.itschool.book.equation243;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
    // Вызывается при создании Активности
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Инициализирует Активность.
        setContentView(R.layout.activity_main);
    }

    /** Вызывается при нажатии пользователем на кнопку Решить */
    public void solveEquation(View view) {
         // ax²+bx+c=0
        double a = Double.parseDouble( ((EditText)
                findViewById(R.id.coefficient_a)).getText().toString());
        double b = Double.parseDouble( ((EditText)
                findViewById(R.id.coefficient_b)).getText().toString());
        double c = Double.parseDouble( ((EditText)
                findViewById(R.id.coefficient_c)).getText().toString());
        double D = b * b - 4 * a * c;
        String res;
        if (a == 0)
        {
            if (b == 0) res = c == 0 ? "Any" : "Error";
            else res = Double.toString(-c / b);
        }
        else
        {
            if (D < 0) res = "Error";
            else if (D == 0) res = Double.toString(-b / (2 * a));
            else res = ((-b + Math.sqrt(D)) / (2 * a)) + "; " + ((-b - Math.sqrt(D)) / (2 * a));
        }
        TextView result = (TextView) findViewById(R.id.result);
        result.setText(res);
    }
}
