package com.example.androidgame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.androidgame.GameState.Rx;
import static com.example.androidgame.GameState.Ry;

public class Enemies {
    public int movement = 10;
    Bitmap enemy1, enemy2;
    int x = 0, y, W, H;
    int enemyC = 0;
    public boolean isShot = true;

    /**
     * Enemy bitmap initialisation and sizes
     * @param res
     */
    Enemies(Resources res){
        enemy1 = BitmapFactory.decodeResource(res, R.drawable.enemyship); //enemy images
        enemy2 = BitmapFactory.decodeResource(res, R.drawable.enemyship);

        W = enemy1.getWidth();
        H = enemy1.getHeight();

        W /= 3;
        H /= 3;

        W = (int) (Rx * W);
        H = (int) (Ry * W);

        enemy1 = Bitmap.createScaledBitmap(enemy1, W, H, false); //creating enemy on defined sizes.
        enemy2 = Bitmap.createScaledBitmap(enemy2, W, H, false);
        y = -H;
    }

    /**
     * enemy counter and return
     * @return
     */
    Bitmap getEnemy() {
        if (enemyC == 1) {//if enemy counter is 1
            enemyC++;
            return enemy1;
        }

        enemyC = 1;
        return enemy2;
    }
        /**
         * function to check collisions with this bitmap
         */
    Rect checkCollision(){
        return new Rect(x, y, x + W, y + H);//check collision with enemy
    }
}
