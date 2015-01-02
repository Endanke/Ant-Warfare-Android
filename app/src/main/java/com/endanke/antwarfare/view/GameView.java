package com.endanke.antwarfare.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.endanke.antwarfare.R;
import com.endanke.antwarfare.model.Ant;
import com.endanke.antwarfare.model.GameController;
import com.endanke.antwarfare.model.Globals;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by endankehome on 14. 10. 01..
 */
public class GameView extends RelativeLayout{
    private Handler handler;
    private boolean fingerDown;
    private float eventX;
    private float eventY;
    private float counter;
    private TextView gameOverLabel;
    private BackgroundView bg;

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

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        Globals.width = metrics.widthPixels;
        Globals.height = metrics.heightPixels;

        this.setBackgroundColor(255);

        bg = new BackgroundView(context, null);
        this.addView(bg);

        GameController.getInstance().newGame();

        for(Ant ant : GameController.getInstance().ants){
            AntView antView = new AntView(context,null, GameController.getInstance().position == 0 ? Color.RED : Color.DKGRAY, ant);
            ant.view = antView;
            this.addView(antView);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        gameOverLabel = (TextView)findViewById(R.id.gameOverLabel);
        gameOverLabel.setAlpha(0);
    }

    public void setColors(){
        for(Ant ant : GameController.getInstance().ants){
            Log.d("pos", String.valueOf(GameController.getInstance().position));
            ant.view.color = GameController.getInstance().position == 0 ?  Color.RED : Color.DKGRAY ;
            ant.view.invalidate();
        }
    }



    @Override
    protected void onLayout(boolean b, int i, int i2, int i3, int i4) {
        super.onLayout(b,i,i2,i3,i4);

        if(!GameController.getInstance().gameOver){
            //TODO: try to make it actually fair somehow :/
            counter += 0.02;
            if(counter >= 5){
                if(GameController.getInstance().ants.size() < 10){
                    Ant ant = new Ant(0);
                    AntView antView = new AntView(this.getContext(),null, GameController.getInstance().position == 0 ? Color.DKGRAY : Color.RED, ant);
                    ant.view = antView;
                    ant.view.setX(Globals.width/2);
                    ant.view.setY(Globals.height/2);
                    this.addView(ant.view);
                    GameController.getInstance().ants.add(ant);
                }
                counter = 0;
            }

            GameController.getInstance().update();
            bg.scale += 0.01;
            bg.invalidate();
        }else{
            this.bg.setAlpha(0);
            gameOverLabel.setText(GameController.getInstance().gameOverText);
            gameOverLabel.setAlpha(1);
        }

        GameController.getInstance().update();

        for(int j = 0; j < GameController.getInstance().ants.size(); j++){
            Ant ant = GameController.getInstance().ants.get(j);
            ant.step();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        eventX = event.getX();
        eventY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                fingerDown = true;
                Log.d("touch","teszt1");
                GameController.getInstance().send();
                return true;
            case MotionEvent.ACTION_MOVE:
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
        return super.onTouchEvent(event);
    }
}
