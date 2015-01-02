package com.endanke.antwarfare.model;

import android.graphics.Point;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by endankehome on 14. 10. 05..
 */
public class GameController {
    public interface GameEventListener{
        public void sendGameOver();
        public void sendOneAntAt(int y);
    }

    private static GameController mInstance = null;
    private GameEventListener geListener;
    public ArrayList<Ant> ants;
    public boolean gameOver;
    public int position;
    public String gameOverText;

    public static GameController getInstance(){
        if(mInstance == null)
        {
            mInstance = new GameController();
        }
        return mInstance;
    }

    public void newGame(){
        this.gameOver = false;
        if(this.ants != null){
            this.ants.clear();
        }else{
            this.ants = new ArrayList<Ant>();
        }
        this.ants.add(new Ant(0));
        this.ants.add(new Ant(1));
        this.ants.add(new Ant(2));
        this.gameOverText = "GAME OVER \n.·´¯`(>▂<)´¯`·.";
    }

    public void setGeListener(GameEventListener geListener) {
        this.geListener = geListener;
    }

    public void update(){
        int aliveAnts = 0;
        int enemyCount = 0;
        for(int i = 0; i < ants.size(); i++){
            Ant ant = ants.get(i);

            ant.step();

            if(ant.out){
                remoteUpdate();
                ant.view.setVisibility(View.GONE);
                ants.remove(ant);
            }

            if(!ant.enemy && ant.targetSize != 0){
                for(int j = 0; j < ants.size(); j++){
                    Ant other = ants.get(j);
                    if(other.enemy && !other.equals(ant) && other.targetSize != 0){
                        if(isViewContains(ant.view, (int)other.view.getX(), (int)other.view.getY())){
                            antsToFight(ant, other);
                        }
                    }
                }
            }

            if(ant.targetSize != 0){
                aliveAnts++;
                if(ant.enemy){
                    enemyCount++;
                }
            }

        }

        if(aliveAnts == enemyCount){
            if(enemyCount > 0){
                if(this.geListener!=null){
                    this.geListener.sendGameOver();
                }
                gameOver = true;
            }
        }

    }


    public void antsToFight(Ant myAnt, Ant enemyAnt){
        if(new Random().nextInt(1) == 1 || enemyAnt.strike == 1){
            enemyAnt.targetSize = 0;
        }else{
            myAnt.targetSize = 0;
            enemyAnt.strike++;
        }
    }

    public void remoteUpdate(){
        if(this.geListener!=null){
            this.geListener.sendOneAntAt(100);
        }
    }

    public void send(){
        for(Ant ant : ants){
            if(ant.randomStep != false){
                ant.randomStep = false;
                ant.destination = new Point(position == 0 ? Globals.width + 60 : -60, Globals.height/2);
                return;
            }
        }
    }

    public void setWin(){
        this.gameOverText = "YOU WIN!\n(＾_＾)";
        this.gameOver = true;
    }

    private boolean isViewContains(View view, int rx, int ry) {
       // From Stack Overflow, seems alright
        int[] l = new int[2];
        view.getLocationOnScreen(l);
        int x = l[0];
        int y = l[1];
        int w = view.getWidth();
        int h = view.getHeight();

        if (rx < x || rx > x + w || ry < y || ry > y + h) {
            return false;
        }
        return true;
    }
}
