package ru.samsung.itschool.book.cells;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.GridLayout;

import task.Task;

public class CellsActivity extends Activity implements OnClickListener, OnLongClickListener
{
    private int WIDTH = 3;
    private Button cells[][];
    private byte field[][];
    private boolean end = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cells);
        makeCells();
        generate();
    }

    void generate()
    {
        field = new byte[WIDTH][WIDTH];
        for (int i = 0; i < WIDTH; i++)
        {
            for (int j = 0; j < WIDTH; j++)
            {
                field[i][j] = 0;
            }
        }
    }

    private byte win(byte f[][])
    {
        for (int i = 0; i < WIDTH; i++)
        {
            if (f[i][0] == f[i][1] && f[i][1] == f[i][2] && f[i][0] != 0)
            {
                return f[i][0];
            }
            if (f[0][i] == f[1][i] && f[1][i] == f[2][i] && f[0][i] != 0)
            {
                return f[0][i];
            }
        }
        if (f[0][0] == f[1][1] && f[1][1] == f[2][2] && f[0][0] != 0 ||
            f[0][2] == f[1][1] && f[1][1] == f[2][0] && f[0][2] != 0)
        {
            return f[1][1];
        }
        return 0;
    }

    @Override
    public boolean onLongClick(View v)
    {
        return true;
    }

    @Override
    public void onClick(View v)
    {
        if (end)
        {
            return;
        }
        Button tappedCell = (Button)v;

        int hor = getX(tappedCell);
        int ver = getY(tappedCell);

        if (field[ver][hor] == 0)
        {
            field[ver][hor] = 1;
            cells[ver][hor].setBackgroundColor(Color.GREEN);
            cells[ver][hor].setText("X");
            if (win(field) == 1)
            {
                Task.showMessage(this, "You win!");
                end = true;
            }

            for (int i = 0; i < WIDTH; i++)
            {
                for (int j = 0; j < WIDTH; j++)
                {
                    if (field[i][j] == 0)
                    {
                        byte f[][] = field;
                        f[i][j] = 1;
                        if (win(f) == 1)
                        {
                            field[i][j] = -1;
                            cells[i][j].setBackgroundColor(Color.RED);
                            cells[i][j].setText("O");
                            return;
                        }
                    }
                }
            }
            for (int i = 0; i < WIDTH; i++)
            {
                for (int j = 0; j < WIDTH; j++)
                {
                    if (field[i][j] == 0)
                    {
                        byte f[][] = field;
                        f[i][j] = -1;
                        if (win(f) == -1)
                        {
                            field[i][j] = -1;
                            cells[i][j].setBackgroundColor(Color.RED);
                            cells[i][j].setText("O");
                            Task.showMessage(this, "You lose!");
                            end = true;
                            return;
                        }
                    }
                }
            }
            int a, b;
            do
            {
                a = (int) (Math.random() * 3);
                b = (int) (Math.random() * 3);
            } while (field[a][b] != 0);
            field[a][b] = -1;
            cells[a][b].setBackgroundColor(Color.RED);
            cells[a][b].setText("O");
        }
    }

	/*
     * NOT FOR THE BEGINNERS
	 * ==================================================
	 */

    int getX(View v)
    {
        return Integer.parseInt(((String)v.getTag()).split(",")[1]);
    }

    int getY(View v)
    {
        return Integer.parseInt(((String)v.getTag()).split(",")[0]);
    }

    void makeCells()
    {
        cells = new Button[WIDTH][WIDTH];
        GridLayout cellsLayout = (GridLayout)findViewById(R.id.CellsLayout);
        cellsLayout.removeAllViews();
        cellsLayout.setColumnCount(WIDTH);
        for (int i = 0; i < WIDTH; i++)
        {
            for (int j = 0; j < WIDTH; j++)
            {
                LayoutInflater inflater = (LayoutInflater) getApplicationContext()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                cells[i][j] = (Button)inflater.inflate(R.layout.cell, cellsLayout, false);
                cells[i][j].setOnClickListener(this);
                cells[i][j].setOnLongClickListener(this);
                cells[i][j].setTag(i + "," + j);
                cellsLayout.addView(cells[i][j]);
            }
        }
    }

}