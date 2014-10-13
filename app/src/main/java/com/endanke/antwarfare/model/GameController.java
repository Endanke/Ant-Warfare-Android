package com.endanke.antwarfare.model;

import java.util.ArrayList;

/**
 * Created by endankehome on 14. 10. 05..
 */
public class GameController {
    public ArrayList<Ant> ants;

    public GameController(){
        this.ants = new ArrayList<Ant>();
        this.ants.add(new Ant(0));
        this.ants.add(new Ant(1));
        this.ants.add(new Ant(2));
        this.ants.add(new Ant(3));
        this.ants.add(new Ant(4));
        this.ants.add(new Ant(5));
    }

    public void update(){
        for(Ant ant : ants){
            ant.step();
        }
    }
}
