package com.example.androidgame;

import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.view.Surface;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class Level2Act extends AppCompatActivity {
    private GameStateLevel2 state;

    @Override
    /** this function to output the gamestate level 2 content, mainly the canvas, bitmaps and other additons
     * @param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Point point = new Point();


        getWindowManager().getDefaultDisplay().getSize(point);//creating window for level2

        state = new GameStateLevel2(this, point.x, point.y);//object of constructor
        setContentView(state);//showing contenting on screen
    }
    /**
     * run pause function
     */
    public void onPause(){
        super.onPause();
        state.Pause();//run pause function
    }
    /**
     * run resume function
     */
    public void onResume(){
        super.onResume();
        state.unPause();//unpause function
    }

}