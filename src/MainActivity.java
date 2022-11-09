package com.example.androidgame;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ComponentCallbacks2;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
/*class levelActs{
    MainActivity main;
    void level1Act(){
        try{
            main.startActivity(new Intent(main, GameAct.class)); //when clicked, load specified level
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
    void level2Act() {
        try {
            main.startActivity(new Intent(main, Level2Act.class)); //when clicked, load specified level
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
    void level3Act() {
        try {
            main.startActivity(new Intent(main, Level3Act.class)); //when clicked, load specified level
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
class myThreadsLevel1 extends Thread{
    levelActs level;
    myThreadsLevel1(levelActs level1){
        this.level = level1;
    }
    @Override
    public void run(){
        level.level1Act();
    }
}
class myThreadsLevel2 extends Thread{
    levelActs level;
    myThreadsLevel2(levelActs level){
        this.level = level;
    }
    @Override
    public void run(){
        level.level2Act();
    }
}
class myThreadsLevel3 extends Thread{
    levelActs level;
    myThreadsLevel3(levelActs level){
        this.level = level;
    }
    @Override
    public void run(){
        level.level3Act();
    }
}*/

public class MainActivity extends AppCompatActivity implements Runnable{
    private boolean Mute;

    @RequiresApi(api = Build.VERSION_CODES.R)
    @SuppressLint("ResourceType")
    @Override
    /**
     * main function to add all, contentview for main menu and holds the key to using the level functions.
     * contains on click for level activiation, and to exit game.
     * @param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*levelActs level = new levelActs();
        myThreadsLevel1 level1 = new myThreadsLevel1(level);
        myThreadsLevel2 level2 = new myThreadsLevel2(level);
        myThreadsLevel3 level3 = new myThreadsLevel3(level);*/

        Thread level1Thread = new Thread();
        Thread level2Thread = new Thread();
        Thread level3Thread = new Thread();


        String TAG = "Main";

        SharedPreferences preferences = getSharedPreferences("Game Save", MODE_PRIVATE); //using a key for the saving of scores

        findViewById(R.id.Level1).setOnClickListener(new View.OnClickListener() {
                @Override
            public void onClick(View v) {
                        try {
                            //level1.start();
                            level1Thread.sleep(2000);
                            startActivity(new Intent(MainActivity.this, GameAct.class));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                }
        });
        findViewById(R.id.Level2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    //level2.start();
                    startActivity(new Intent(MainActivity.this, Level2Act.class));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        findViewById(R.id.Level3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    //level3.start();
                    startActivity(new Intent(MainActivity.this, Level3Act.class));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        findViewById(R.id.exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Thread.sleep(1000); //take 1000ml before exiting app
                    finish();//end activity
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        });

        findViewById(R.id.Options).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Thread.sleep(1000); //take 1000ml before proceeding
                    startActivity(new Intent(MainActivity.this, OptionsPage.class));
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        });

        TextView score = findViewById(R.id.userScore);
        score.setText("HighScore: " + preferences.getInt("Score: ", 0)); //default score as 0, get key for score

        Mute = preferences.getBoolean("mute", false);
        final ImageView notMute = findViewById(R.id.notmute); //final image of not mute image by default.

        if(!Mute){
            notMute.setImageResource(R.drawable.notmute); //if mute is not true, set this image
        }
        else{
            notMute.setImageResource(R.drawable.mute);
        }

        notMute.setOnClickListener(v -> {
            Mute = !Mute;

            if(Mute){
                notMute.setImageResource(R.drawable.notmute); //on click, add this image.
            }
            else{
                notMute.setImageResource(R.drawable.mute);
            }
            SharedPreferences.Editor edit = preferences.edit();
            edit.putBoolean("mute", Mute); //save mute preferences for user.
            edit.apply();
        });

        /*public void onTrimMemory() {

            // Determine which lifecycle or system event was raised.
            switch (level) {

                case ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN:
                    android.os.Process.killProcess(android.os.Process.myPid());
                /*
                   Release any UI objects that currently hold memory.

                   The user interface has moved to the background.


                    break;

                case ComponentCallbacks2.TRIM_MEMORY_RUNNING_MODERATE:
                case ComponentCallbacks2.TRIM_MEMORY_RUNNING_LOW:
                case ComponentCallbacks2.TRIM_MEMORY_RUNNING_CRITICAL:
                    android.os.Process.getThreadPriority(android.os.Process.myTid());
                /*
                   Release any memory that your app doesn't need to run.

                   The device is running low on memory while the app is running.
                   The event raised indicates the severity of the memory-related event.
                   If the event is TRIM_MEMORY_RUNNING_CRITICAL, then the system will
                   begin killing background processes.

                    break;

                case ComponentCallbacks2.TRIM_MEMORY_BACKGROUND:
                case ComponentCallbacks2.TRIM_MEMORY_MODERATE:
                case ComponentCallbacks2.TRIM_MEMORY_COMPLETE:

                /*
                   Release as much memory as the process can.

                   The app is on the LRU list and the system is running low on memory.
                   The event raised indicates where the app sits within the LRU list.
                   If the event is TRIM_MEMORY_COMPLETE, the process will be one of
                   the first to be terminated.


                    break;

                default:
                /*
                  Release any non-critical data structures.

                  The app received an unrecognized memory level value
                  from the system. Treat this as a generic low-memory message.

                    break;
            }*/
    }

    @Override
    public void run() {

    }
}