package com.example.mithrandir.valeralibrary;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by Mithrandir on 29-Jul-16.
 */
public class ModuleView extends View
{
    private Random rand;
    private Paint paint;
    private int width = 0, height = 0;

    private int cx = 0, cy = 0, diameter = 0;

    int[] percents;
    int[] colors;

    public ModuleView(Context context)
    {
        super(context);
        initialize(context);
    }

    public ModuleView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        initialize(context);
    }

    private void initialize(Context context)
    {
        rand = new Random();
        paint = new Paint();
        paint.setTextSize(30);
    }

    public void setPercent(int[] percents)
    {
        int sum = 0;

        for (int i=0; i<percents.length; i++) {
            sum += percents[i];
        }

        if(sum != 100) {
            ; // PROBLEM
            percents = new int[1];
            percents[0] = 100;
            colors = new int[1];
            colors[0] = Color.RED;
        }
        else
        {
            this.percents = percents;
            colors = new int[percents.length];

            for(int i=0; i<colors.length; i++){
                colors[i] = MyRandomColor();
            }
        }

        invalidate();
    }

    private int MyRandomColor()
    {
        int R = rand.nextInt(256);
        int G = rand.nextInt(256);
        int B = rand.nextInt(256);

        return Color.rgb(R, G, B);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        RectF rect = new RectF(cx-diameter,cy-diameter, cx+diameter, cy+diameter); // Left,Top,Right,Buttom

        int startAngle = 0;

        Log.i("test", "test");

        if(percents != null)
        {
            Log.i("test1", "test1");

            for(int i=0; i<percents.length; i++)
            {
                Log.i("test2", "test2");

                int sweepAngle = 360*percents[i]/100;
                paint.setColor(colors[i]);
                canvas.drawText(String.valueOf(percents[i]) + "%", 0, 50 + 40*i, paint);
                canvas.drawArc(rect, startAngle, sweepAngle, true, paint);
                startAngle += sweepAngle;
            }
        }

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        width = w - (getPaddingLeft() + getPaddingRight());
        height = h - (getPaddingTop() + getPaddingBottom());

        diameter = (width+height)/5;

        cx = width/2;
        cy = height/2;
    }

}
