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
    private Path path = new Path();
    private float eventX;
    private float eventY;
    private boolean fingerDown = false;

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
        canvas.drawPath(path, paint);
        paint.setColor(Color.GRAY);
        canvas.drawCircle(Globals.width/2, Globals.height/2, Globals.width/3, paint);
        if (fingerDown) { 
            canvas.drawCircle(eventX, eventY, 20, paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        eventX = event.getX();
        eventY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                fingerDown = true;
                path.moveTo(eventX, eventY);

                return true;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(eventX, eventY);
                break;
            case MotionEvent.ACTION_UP:
                fingerDown = false;
                // nothing to do
                break;
            default:
                return false;
        }

        // Schedules a repaint.
        invalidate();
        return true;
    }
}