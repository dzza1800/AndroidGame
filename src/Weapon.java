package com.example.androidgame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.androidgame.GameState.Rx;
import static com.example.androidgame.GameState.Ry;

public class Weapon {
    int x, y;
    int H, W;
    Bitmap laser1;

    /**
     * Laser bitmap Initialisation
     * @param res
     */
    Weapon(Resources res){
        laser1 = BitmapFactory.decodeResource(res, R.drawable.laser); //define image

        W = laser1.getWidth();
        H = laser1.getHeight();

        W /= 4;
        H /= 4;

        W = (int) (Rx * W);
        H = (int) (Ry * W);

        laser1 = Bitmap.createScaledBitmap(laser1, W, H, false); //create laser image with defined sizes
    }

    /**
     * check collision with bitmap
     * @return
     */
    Rect checkCollision(){
        return new Rect(x, y, x + W, y + H);
    }//check collision with laser
}
