package ru.samsung.itschool.book.cells;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.GridLayout;


import task.Stub;
import task.Task;

public class CellsActivity extends Activity implements OnClickListener, OnLongClickListener
{
    private int WIDTH = 13;
    private int HEIGHT = WIDTH; //Я напутал, где ширина, где высота, поэтому программа работает только с квадратным полем
    private Button cells[][];
    boolean[][] bombs, opened, flags; //Есть ли тут бомба, открыта ли клетка, установлен ли на клетке флаг
    private boolean start = false, end = false; //Началась ли игра? Закончилась ли игра?

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cells);
        makeCells();
    }

    void easterEgg()
    {
        for (int i = 0; i < WIDTH; i++)
        {
            bombs[0][i] = false;
            bombs[1][i] = (i < 2 || i > 2 && i < 6 ||
                i > 6 && i < 9 || i == 11);
            bombs[2][i] = (i == 0 || i == 3 ||
                i == 5 || i == 7 || i == 9 || i > 10);
            bombs[3][i] = (i < 2 || i > 3 && i < 6 ||
                i == 7 || i == 11);
            bombs[4][i] = (i == 1 || i == 5 || i == 7 || i == 11);
            bombs[5][i] = (i < 2 || i == 5 ||
                i == 7 || i > 10);
            bombs[6][i] = false;

            bombs[7][i] = (i == 1 || i == 8 || i == 11);
            bombs[8][i] = (i == 0 || i == 7 || i == 8 || i == 12);
            bombs[9][i] = (i == 0 || i == 3 || i == 4 ||
                i == 8 || i == 12);
            bombs[10][i] = (i == 0 || i == 8 || i == 12);
            bombs[11][i] = (i == 1 || i > 6 && i < 10 || i == 11);
            bombs[12][i] = false;
        }
    }

    void generate(int x, int y)
    {
        bombs = new boolean[HEIGHT][WIDTH];
        opened = new boolean[HEIGHT][WIDTH];
        flags = new boolean[HEIGHT][WIDTH];
        for (int i = 0; i < WIDTH; i++)
        {
            for (int j = 0; j < HEIGHT; j++)
            {
                bombs[i][j] = (Math.random() < 0.3);
                opened[i][j] = false;
                flags[i][j] = false;
                cells[i][j].setBackgroundColor(Color.WHITE);
            }
        }
        if (Math.random() <= 0.01)
        {
            easterEgg();
            return;
        }
        bombs[y][x] = false; //Под первой открытой игроком клеткой не должно быть бомбы
        for (int i = y - 1; i <= y + 1; i++)
        {
            for (int j = x - 1; j <= x + 1; j++)
            {
                if (i < 0 || j < 0 || i >= HEIGHT || j >= WIDTH)
                {
                    continue;
                }
                bombs[i][j] = false; //Под клетками, граничащими с первой открытой также не должно быть бомб
            }
        }
    }

    void flag(int x, int y) //Установка/снятие флага
    {
        if (opened[y][x]) //Нельзя установить флаг на открытую клетку
        {
            return;
        }
        if (flags[y][x]) //Снятие флага
        {
            cells[y][x].setBackgroundColor(Color.WHITE);
            flags[y][x] = false;
        }
        else //Установка флага
        {
            cells[y][x].setBackgroundColor(Color.YELLOW);
            flags[y][x] = true;
        }
    }

    @Override
    public boolean onLongClick(View v) //Длинное нажатие — установка/снятие флага
    {
        if (end) //Нельзя установить флаг после конца игры
        {
            return true;
        }
        Button tappedCell = (Button)v;

        int tappedX = getX(tappedCell);
        int tappedY = getY(tappedCell);

        flag(tappedX, tappedY);

        return true;
    }

    int checkBombs(int x, int y) //Проверка количества бомб в соседних клетках
    {
        int counter = 0;
        for (int i = y - 1; i <= y + 1; i++)
        {
            for (int j = x - 1; j <= x + 1; j++)
            {
                if (i < 0 || j < 0 || i >= HEIGHT || j >= WIDTH)
                {
                    continue;
                }
                if (bombs[i][j])
                {
                    counter++;
                }
            }
        }
        if (counter == 0) //Если вокруг нет ни одной бомбы, открыть все соседние клетки
        {
            for (int i = y - 1; i <= y + 1; i++)
            {
                for (int j = x - 1; j <= x + 1; j++)
                {
                    if (i < 0 || j < 0 || i >= HEIGHT || j >= WIDTH)
                    {
                        continue;
                    }
                    open(j, i);
                }
            }
        }
        return counter;
    }

    void open(int x, int y) //Открытие клетки
    {
        if (opened[y][x]) //Нельзя повторно открыть клетку
        {
            return;
        }
        opened[y][x] = true;
        if (bombs[y][x]) //Попадание в бомбу
        {
            for (int i = 0; i < WIDTH; i++)
            {
                for (int j = 0; j < HEIGHT; j++)
                {
                    if (bombs[i][j])
                    {
                        cells[i][j].setBackgroundColor(Color.RED);
                    }
                }
            }
            Task.showMessage(this, "You lose!");
            end = true;
        }
        else
        {
            cells[y][x].setBackgroundColor(Color.GREEN);
            cells[y][x].setText(Integer.toString(checkBombs(x, y)));
        }
    }

    @Override
    public void onClick(View v)
    {
        if (end) //Нельзя открывать клетки после конца игры
        {
            return;
        }
        Button tappedCell = (Button)v;

        int tappedX = getX(tappedCell);
        int tappedY = getY(tappedCell);
        if (!start) //Если это первая нажатая клетка, запустить генерацию бомб
        {
            start = true;
            generate(tappedX, tappedY);
        }
        if (flags[tappedY][tappedX]) //Если на клетке установлен флаг, не открывать её, а снять флаг
        {
            flag(tappedX, tappedY);
        }
        else
        {
            open(tappedX, tappedY);
        }
        //Далее идёт проверка на победу
        if (end) //Нельзя победить после конца игры
        {
            return;
        }
        boolean clear = true;
        for (int i = 0; i < WIDTH; i++)
        {
            for (int j = 0; j < HEIGHT; j++)
            {
                if (!opened[i][j] && !bombs[i][j]) //Если существует неотрытая клетка без бомбы, не победа.
                {
                    clear = false;
                }
            }
        }
        if (clear)
        {
            Task.showMessage(this, "You win!");
            end = true;
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
        cells = new Button[HEIGHT][WIDTH];
        GridLayout cellsLayout = (GridLayout)findViewById(R.id.CellsLayout);
        cellsLayout.removeAllViews();
        cellsLayout.setColumnCount(HEIGHT);
        for (int i = 0; i < HEIGHT; i++)
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