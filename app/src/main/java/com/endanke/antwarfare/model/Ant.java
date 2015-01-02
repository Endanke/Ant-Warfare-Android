package com.endanke.antwarfare.model;

import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.View;

import com.endanke.antwarfare.view.AntView;

import java.util.Random;
import java.lang.Math;

/**
 * Created by endankehome on 14. 10. 05..
 */
public class Ant {
    public float x, y, tx, ty;
    public float size;
    public float targetSize;
    public int strike;
    public Color c;
    public AntView view;
    public boolean randomStep;
    public boolean out;
    public boolean enemy;
    public Point destination;
    private PerlinNoise p;

    public Ant(int variator) {
        x = y = 100;
        size = 0;
        targetSize = 20;

        Random random = new Random();
        p = new PerlinNoise(10);
        tx = random.nextFloat() * 100;
        ty = random.nextFloat() * 100;
        randomStep = true;
    }

    public void step() {
        if (this.view != null) {
            if(randomStep){
                x = (float) (p.interpolatedNoise(tx));
                y = (float) (p.interpolatedNoise(ty));

                // TODO: something wrong here, I don't remember what

                this.view.setX((x * Globals.width / 3) + Globals.width / 2);
                this.view.setY((y * Globals.height / 3) + Globals.height / 2);

                tx += 0.01;
                ty += 0.01;
            }else{
                if(this.view.getX() < destination.x ){
                    this.view.setX((float) (this.view.getX() + 0.5));
                }
                if(this.view.getY() < destination.y){
                    this.view.setY((float) (this.view.getY() + 0.5));
                }
                if(this.view.getX() > destination.x ){
                    this.view.setX((float) (this.view.getX() - 0.5));
                }
                if(this.view.getY() > destination.y){
                    this.view.setY((float)(this.view.getY() - 0.5));
                }
                int[] l = new int[2];
                this.view.getLocationOnScreen(l);
                // Ant view's with offset + Main view increased with offset
                if(!Rect.intersects(new Rect(l[0], l[1], l[0] + view.getWidth(), l[1] + view.getHeight()), new Rect(0,0,Globals.width,Globals.height))){
                    if(!enemy){
                        out = true;
                    }
                }
            }

            if(targetSize == 0){
                if(size > targetSize){
                    size -= 0.2;
                    this.view.invalidate();
                }else{
                    this.view.setVisibility(View.GONE);
                    GameController.getInstance().ants.remove(GameController.getInstance().ants.indexOf(this));
                }
            }else if(size < targetSize){
                size += 0.1;
                this.view.invalidate();
            }

        }
    }

}
