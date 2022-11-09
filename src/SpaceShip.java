package com.example.androidgame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.androidgame.GameState.Rx;
import static com.example.androidgame.GameState.Ry;
import static com.example.androidgame.GameStateLevel2.Rx2;
import static com.example.androidgame.GameStateLevel2.Ry2;
import static com.example.androidgame.GameStateLevel3.Rx3;
import static com.example.androidgame.GameStateLevel3.Ry3;


import static com.example.androidgame.GameStateLevel2.Rx2;
import static com.example.androidgame.GameStateLevel2.Ry2;


public class SpaceShip {

    int x, y, x2, x3, y2, y3, flightTime = 0;
    int H, W, H2, W2, H3, W3;
    boolean spaceUp = false;
    int laserCounter = 0;
    int laserShot = 0;
    public GameState state;
    public GameStateLevel2 state2;
    public GameStateLevel3 state3;
    Bitmap space1;
/**
 * @param state
 * @param Sy
 * @param res
 * **/
    public SpaceShip(GameState state, int Sy, Resources res){
        this.state = state;
        space1 = BitmapFactory.decodeResource(res, R.drawable.spaceship); //bitmap initialisation for character

        W = space1.getWidth();
        H = space1.getHeight();

        W /= 2;
        H /= 2;

        W = (int) (Rx * W);
        H = (int) (Ry * W);

        space1 = Bitmap.createScaledBitmap(space1, W, H, false); //image to be scaled according to defined measurements^


        y = Sy / 2;
        x = (int)(64 * Rx);
    }
    /**
     * @param state2
     * @param Sy
     * @param res
     * **/
    public SpaceShip(GameStateLevel2 state2, int Sy, Resources res){
        this.state2 = state2;
        space1 = BitmapFactory.decodeResource(res, R.drawable.spaceshipl2);

        W = space1.getWidth();
        H = space1.getHeight();

        W /= 4;
        H /= 4;

        W = (int) (Rx2 * W);
        H = (int) (Ry2 * W);

        space1 = Bitmap.createScaledBitmap(space1, W, H, false);


        y = Sy / 2;
        x = (int)(64 * Rx2);

    }
    /**
     * this function for the initialisation of the spaceship bitmaps
     * @param state3
     * @param Sy
     * @param res
     * **/
    public SpaceShip(GameStateLevel3 state3, int Sy, Resources res){
        this.state3 = state3;
        space1 = BitmapFactory.decodeResource(res, R.drawable.spaceshipl3);

        W = space1.getWidth();
        H = space1.getHeight();

        W /= 4;
        H /= 4;

        W = (int) (Rx3 * W);
        H = (int) (Ry3 * W);

        space1 = Bitmap.createScaledBitmap(space1, W, H, false);


        y = Sy / 2;
        x = (int)(64 * Rx3);

    }
    /**
     * Functions for all spaceships for each level, for different behaviours in following gameStates.
     * **/
    Bitmap getSpaceship(){
        if(laserShot > 0){
            if(laserCounter == 1){
                laserCounter++;
                return space1;
            }
            laserCounter = 1;
            laserShot--;
            state.newLaser();
            return space1;
        }

        if(flightTime == 0){
            flightTime++;
            return space1;
        }
        flightTime--;
        return space1;
    }

    Bitmap getSpaceshipL2(){
        if(laserShot > 0){
            if(laserCounter == 1){
                laserCounter++;
                return space1;
            }
            laserCounter = 1;
            laserShot--;
            state2.newLaser();
            return space1;
        }

        if(flightTime == 0){

            flightTime++;
            return space1;
        }
        flightTime--;
        return space1;
    }

    Bitmap getSpaceshipL3(){
        if(laserShot > 0){
            if(laserCounter == 1){
                laserCounter++;
                return space1;
            }
            laserCounter = 1;
            laserShot--;
            state3.newLaser();
            return space1;
        }

        if(flightTime == 0){
            flightTime++;
            return space1;
        }
        flightTime--;
        return space1;
    }

    /**
     * check collisions with this bitmap using a rectangle
     * @return
     */
    Rect checkCollision(){
        return new Rect(x, y, x + W, y + H);//checking for collisions with the spaceship using rectangle function.
    }

}
