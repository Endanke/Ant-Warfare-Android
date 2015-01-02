package com.endanke.antwarfare.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import com.endanke.antwarfare.model.*;

public class BackgroundView extends View {
    private Paint paint = new Paint();
    public float scale = 0;

    public BackgroundView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(5f);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setColor(Color.GRAY);

        drawTriangle(canvas);

        canvas.drawCircle(Globals.width / 2, Globals.height / 2, Globals.width / 3, paint);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(Globals.width/2, Globals.height/2, (float)((Globals.width/3)*((Math.sin(scale)+1.0)/2)), paint);
        paint.setStyle(Paint.Style.STROKE);
    }

    private void drawTriangle(Canvas canvas){
        Point a,b,c;
        if(GameController.getInstance().position == 1){
            a = new Point(0, (Globals.height / 2) - 50);
            b = new Point(0, (Globals.height / 2) + 50);
            c = new Point(20, Globals.height / 2);
        }else{
            a = new Point(Globals.width, (Globals.height / 2) - 50);
            b = new Point(Globals.width, (Globals.height / 2) + 50);
            c = new Point(Globals.width-20, Globals.height / 2);
        }


        Path path = new Path();
        path.setFillType(Path.FillType.EVEN_ODD);
        path.moveTo(b.x, b.y);
        path.lineTo(c.x, c.y);
        path.lineTo(a.x, a.y);
        path.lineTo(b.x, b.y);
        path.close();

        canvas.drawPath(path, paint);
    }
}