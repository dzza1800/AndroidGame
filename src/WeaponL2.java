package com.example.androidgame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.androidgame.GameStateLevel2.Rx2;
import static com.example.androidgame.GameStateLevel2.Ry2;

public class WeaponL2 {
    int x, y;
    int H, W;
    Bitmap laser1;

    /**
     * Laser bitmap Initialisation
     * @param res
     */
    WeaponL2(Resources res){
        laser1 = BitmapFactory.decodeResource(res, R.drawable.laser);//define image

        W = laser1.getWidth();
        H = laser1.getHeight();

        W /= 4;
        H /= 4;

        W = (int) (Rx2 * W);
        H = (int) (Ry2 * W);

        laser1 = Bitmap.createScaledBitmap(laser1, W, H, false);//create laser image with defined sizes
    }
    /**
     * check collision with bitmap
     * @return
     */
    Rect checkCollision(){
        return new Rect(x, y, x + W, y + H);
    }//check collision with laser
}
