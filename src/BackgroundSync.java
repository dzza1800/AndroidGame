package com.example.androidgame;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BackgroundSync {
    public int x, y;
    Bitmap bg, stopButton;

    /**
     * Bitmap for background and scaled to chosen sizes.
     * @param Sx
     * @param Sy
     * @param resource
     */
    BackgroundSync(int Sx, int Sy, Resources resource){
        bg = BitmapFactory.decodeResource(resource, R.drawable.gamebackground); //creating background
        stopButton = BitmapFactory.decodeResource(resource, R.drawable.pause);
        stopButton = Bitmap.createScaledBitmap(stopButton, Sx, Sy, false);
        bg = Bitmap.createScaledBitmap(bg, Sx, Sy, false); //creating background
    }

}
