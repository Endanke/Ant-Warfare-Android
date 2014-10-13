package com.endanke.antwarfare.view;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.endanke.antwarfare.model.Ant;
import com.endanke.antwarfare.model.GameController;
import com.endanke.antwarfare.model.Globals;

import java.util.ArrayList;

/**
 * Created by endankehome on 14. 10. 01..
 */
public class GameView extends LinearLayout{
    private Handler handler;
    private GameController gc;

    public GameView(Context context){
        super(context);
        init(context);
    }
    public GameView(Context context, AttributeSet attrs){
        super(context, attrs);
        init(context);
    }
    public GameView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }



    public void init(Context context) {
        this.setOrientation(HORIZONTAL);

        gc = new GameController();

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        Globals.width = metrics.widthPixels;
        Globals.height = metrics.heightPixels;

        handler=new Handler();
        final Runnable r = new Runnable(){
            public void run(){
                requestLayout();
                handler.postDelayed(this, 10);
            }
        };
        handler.postDelayed(r, 10);


        this.setBackgroundColor(255);

        BackgroundView bg = new BackgroundView(context, null);
        this.addView(bg);

        for(Ant ant : gc.ants){
            AntView antView = new AntView(context,null, ant);
            ant.view = antView;
            this.addView(antView);
        }
    }


    @Override
    protected void onLayout(boolean b, int i, int i2, int i3, int i4) {
        super.onLayout(b,i,i2,i3,i4);

        gc.update();
        for(Ant ant : gc.ants){
            ant.step();
        }
    }




}
