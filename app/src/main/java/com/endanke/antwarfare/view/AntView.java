package com.endanke.antwarfare.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import com.endanke.antwarfare.model.Ant;

/**
 * Created by danieleke on 14. 10. 08..
 */
public class AntView extends View{
    private Ant ant;
    private Paint paint = new Paint();
    private Path path = new Path();
    public int width, height;

    public AntView(Context context, AttributeSet attrs, Ant ant) {
        super(context, attrs);
        width = height = 30;

        paint.setAntiAlias(true);
        paint.setStrokeWidth(6f);
        paint.setColor(Color.DKGRAY);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeJoin(Paint.Join.ROUND);
        this.ant = ant;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(path, paint);
        canvas.drawCircle(width/2, height/2, ant.size, paint);
        this.setLayerType(LAYER_TYPE_SOFTWARE, paint);
        paint.setShadowLayer(4.0f, 0.0f, 2.0f, Color.BLACK);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(width, height);
    }
}
