package com.example.androidgame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.androidgame.GameStateLevel3.Rx3;
import static com.example.androidgame.GameStateLevel3.Ry3;

public class WeaponL3 {
    int x, y;
    int H, W;
    Bitmap laser1;

    /**
     * Laser bitmap Initialisation
     * @param res
     */
    WeaponL3(Resources res){
        laser1 = BitmapFactory.decodeResource(res, R.drawable.level3beam); //define image

        W = laser1.getWidth();
        H = laser1.getHeight();

        W /= 4;
        H /= 4;

        W = (int) (Rx3 * W);
        H = (int) (Ry3 * W);

        laser1 = Bitmap.createScaledBitmap(laser1, W, H, false);//create laser image with defined sizes
    }
    /**
     * check collision with bitmap
     * @return
     */
    Rect checkCollision(){
        return new Rect(x, y, x + W, y + H);
    } //check collision with laser
}
