package com.example.androidgame;

import android.graphics.Point;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class Level3Act extends AppCompatActivity {
    private GameStateLevel3 state;
    @Override
    /** this function to output the gamestate level 3 content, mainly the canvas, bitmaps and other additons
     * @param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);//creating window for level3

        state = new GameStateLevel3(this, point.x, point.y);//object of constructor
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