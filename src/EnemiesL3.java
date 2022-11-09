package com.example.androidgame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import static com.example.androidgame.GameStateLevel3.Ry3;
import static com.example.androidgame.GameStateLevel3.Rx3;

public class EnemiesL3 {
    public int movement = 3;
    Bitmap enemy1;
    int x = 0, y, W, H;
    int enemyC = 0;
    int bossC = 300; //Boss health
    public boolean isShot = true;

    /**
     * Enemy bitmap initialisation and sizes
     * @param res
     */
    EnemiesL3(Resources res){
        enemy1 = BitmapFactory.decodeResource(res, R.drawable.enemyboss); //enemy image

        W = enemy1.getWidth();
        H = enemy1.getHeight();


        W = (int) (Rx3 * W);
        H = (int) (Ry3 * W);

        enemy1 = Bitmap.createScaledBitmap(enemy1, W, H, false);//creating enemy on defined sizes.

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
        return enemy1;

    }
    /**
     * function to check collisions with this bitmap
     */
    Rect checkCollision(){
        return new Rect(x, y, x + W, y + H);
    }//check collision with enemy
}
