package com.example.androidgame;

import android.graphics.Point;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.WindowManager;

public class GameAct extends AppCompatActivity {
    private GameState state;
    @Override
    /** this function to output the gamestate content, mainly the canvas, bitmaps and other additons
     * @param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point); //creating window for level1

        state = new GameState(this, point.x, point.y); //object of constructor
        setContentView(state); //showing contenting on screen
    }

    /**
     * run pause function
     */
    public void onPause(){
        super.onPause();
        state.Pause(); //run pause function
    }

    /**
     * run resume function
     */
    public void onResume(){
        super.onResume();
        state.unPause(); //unpause function
    }
}