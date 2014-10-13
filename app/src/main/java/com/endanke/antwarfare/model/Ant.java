package com.endanke.antwarfare.model;

import android.graphics.Color;

import com.endanke.antwarfare.view.AntView;

import java.util.Random;
import java.lang.Math;

/**
 * Created by endankehome on 14. 10. 05..
 */
public class Ant {
    public float x, y, tx, ty;
    public float size;
    public Color c;
    public AntView view;
    private PerlinNoise p;

    public Ant(int variator) {
        x = y = 100;
        size = 0;

        Random random = new Random();
        p = new PerlinNoise(10);
        tx = random.nextFloat() * 100;
        ty = random.nextFloat() * 100;
    }

    public void step() {
        if (this.view != null) {

            x = (float) (p.interpolatedNoise(tx));
            y = (float) (p.interpolatedNoise(ty));

            // Szélesség magasság az x-ből le! Nem középen van, bal felső az x,y, ugye?

            this.view.setX((x * Globals.width / 3) + Globals.width / 2);
            this.view.setY((y * Globals.height / 3) + Globals.height / 2);
            System.out.println(this.view.getX());

            tx += 0.01;
            ty += 0.01;

            if (size < 10.0) {
                size += 0.1;
                this.view.invalidate();
            }
        }
    }

}
