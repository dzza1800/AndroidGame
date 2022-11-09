package com.example.androidgame;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameState extends SurfaceView implements Runnable {
    private Thread threads;
    public Intent intent;
    private boolean play;
    private BackgroundSync bg1, bg2;
    private SpaceShip spaceship;
    private List<Weapon> lasers;
    private Enemies[] enemy;
    private Random rand;
    public SharedPreferences preferences;
    private int Sound;
    private int Sound2;
    Paint paint;
    GameAct act;
    private SoundPool sound;
    private boolean over = false;
    public static float Rx, Ry;
    int Sx, Sy, score = 0;
    /** this is the main function, the brain of where the code is initialised and programmed for the game
     * flow
     * @param act
     * @param Sx
     * @param Sy
     * **/
    public GameState(GameAct act, int Sx, int Sy) {
        super(act);
        this.act = act;
        preferences = act.getSharedPreferences("Game Save", act.MODE_PRIVATE);


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            AudioAttributes audio = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .build();

            sound = new SoundPool.Builder()
                    .setAudioAttributes(audio)
                    .build();
        }
        else {
            sound = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        }
            Sound = sound.load(act, R.raw.shootlevel1, 1);

        //sound function initialisation

        rand = new Random(); //create random object
        this.Sx = Sx;
        this.Sy = Sy;
        Rx = 1920f / Sx;
        Ry = 1080f / Sy;
        bg1 = new BackgroundSync(this.Sx, this.Sy, getResources()); //getResources for background and spaceship
        bg2 = new BackgroundSync(this.Sx, this.Sy, getResources());
        lasers = new ArrayList<Weapon>();
        spaceship = new SpaceShip(this, this.Sy, getResources());
        bg2.x = Sx;

        paint = new Paint();
        paint.setTextSize(75);//textSize for the score
        paint.setColor(Color.WHITE);//text color for score


        enemy = new Enemies[5];
        for(int i = 0; i < 5; i++){
            Enemies enemies = new Enemies(getResources()); //loop 5 enemies at once each time.
            enemy[i] = enemies;
        }
    }


    @Override
    /**
     * run function while play is true, to update during the game (character, enemy and laser flow)
     * to draw bitmaps/canvas on screen
     * sleep function using threads
     */
    public void run() {
        while(play){
            update(); //while game is on, update game, draw onto canvas and sleep
            draw();
            sleep();
        }
    }

    /**
     * function to update movements.
     * laser array-list for the weapon functions and movements
     * enemy array enhanced for loop for the displaying of enemies
     */
    private void update() {
        bg1.x -= 10 * Rx; //update background movement
        bg2.x -= 10 * Rx;

        if(bg1.x + bg1.bg.getWidth() < 0){
            bg1.x = Sx;
        }

        if(bg2.x + bg2.bg.getWidth() < 0){
            bg2.x = Sx;
        }

        if(spaceship.spaceUp){
            spaceship.y -= 30 * Ry; //spaceship going up
        }
        else{
            spaceship.y += 30 * Ry; //spaceship going down
        }
        if(spaceship.y < 0){
            spaceship.y = 0;
        }
        if(spaceship.y >= Sy - spaceship.H){
            spaceship.y = Sy - spaceship.H;
        }
        List<Weapon> finishedLaser = new ArrayList<Weapon>();//arraylist for lasers

        for(Weapon weapon : lasers) {
            if (weapon.x > Sx) {
                finishedLaser.add(weapon);
            }
            weapon.x += 50 * Rx;

            for (Enemies enemies : enemy) {
                if (Rect.intersects(enemies.checkCollision(), weapon.checkCollision())) {
                    score++;
                    enemies.x = -500;
                    weapon.x = Sx + 500;
                    enemies.isShot = true;
                }
            }
        }
        for(Weapon weapon : finishedLaser){
            lasers.remove(weapon); //remove lasers that reached destination
        }

        for(Enemies enemies : enemy){
            enemies.x -= enemies.movement;

            if(enemies.x + enemies.W < 0){
                if(!enemies.isShot){
                    over = true;
                    return;
                }
                int boundaries = (int) (30 * Rx);
                enemies.movement = rand.nextInt(boundaries);

                if(enemies.movement < (7 * Rx)){
                    enemies.movement = 15;
                }
                enemies.x = Sx;
                enemies.y = rand.nextInt(Sy - enemies.H);
                enemies.isShot = false;

            }
            if(Rect.intersects(enemies.checkCollision(), spaceship.checkCollision())){
                over = true;
                //game ends as player dies
                return;
            }
        }
    }

    /**
     * function for laser and laser sound
     */
    public void newLaser() {
        if (!preferences.getBoolean("Mute", false)){
            sound.play(Sound, 1, 1, 0, 0, 1);
    }
            Weapon laser = new Weapon(getResources());
            laser.x = spaceship.x + spaceship.W;
            int height = spaceship.H / 2;
            laser.y = spaceship.y + height;
            lasers.add(laser);
    }
    /**
     * drawing of canvas, content of the game.
     * enemies, background, character, laser are all displayed from here
     * if statement for game end then to proceed to submitting online score.
     */
    private void draw() {
        if(getHolder().getSurface().isValid()){
            Canvas can = getHolder().lockCanvas();

            can.drawBitmap(bg1.bg, bg1.x, bg1.y, paint);
            can.drawBitmap(bg2.bg, bg2.x, bg2.y, paint);
            can.drawText(score + " ", Sx / 2f, 75, paint);

            can.drawBitmap(spaceship.getSpaceship(),spaceship.x, spaceship.y, paint);

            for(Enemies enemies : enemy){
                can.drawBitmap(enemies.getEnemy(), enemies.x, enemies.y, paint);
            }

            if(over){
                play = false;
                getHolder().unlockCanvasAndPost(can);
                saveScore();
                finishScreen();
                waitBeforeStartOrEnd();
                intent = new Intent(act, Submission.class);
                intent.putExtra("Score2", score);
                act.startActivity(intent);
                return;
            }

            for(Weapon weapon : lasers){
                can.drawBitmap(weapon.laser1, weapon.x, weapon.y, paint);
            }

            getHolder().unlockCanvasAndPost(can);
        }
    }

    /**
     * sleep function using threads
     */
    private void sleep() {
        try{
            threads.sleep(10);
        }
        catch(Exception e){
            System.err.println(e);
            e.printStackTrace();
        }
    }

    /**
     * pause function for game
     */
    public void Pause(){
        try{
            play = false;
            threads.join();
        }
        catch(Exception e){
            System.err.println(e);
            e.printStackTrace();
        }
    }
    /**
     * un-pause function for game
     */
    public void unPause(){
        play = true;
        threads = new Thread(this);
        threads.start();
    }

    /**
     * function for touch event for character movement going up and down.
     * boundaries of where the touch event happens, is defined here.
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event){
        switch (event.getAction()){
            case MotionEvent.ACTION_UP:
                spaceship.spaceUp = false;
                if(event.getX() > Sx / 2){ //if touch event is on the right side of the screen
                    spaceship.laserShot++; //laser will shoot
                }
                break;
            case MotionEvent.ACTION_DOWN:
                if(event.getX() < Sx / 2){ //if touch event is on the left side of the screen
                    spaceship.spaceUp = true; //spaceship will go up
                }
                break;
        }
        return true;
    }

    /**
     * wait function before the next activity
     */
    public void waitBeforeStartOrEnd(){
        try {
            threads.sleep(2000);
            act.startActivity(new Intent(act, MainActivity.class));
            act.finish();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    /**
     * finish screen to be displayed when game is over
     */
    public void finishScreen(){
        if(getHolder().getSurface().isValid()){
            Canvas can = getHolder().lockCanvas();
            can.drawBitmap(bg1.bg, bg1.x, bg1.y, paint);

            if(preferences.getInt("Score: ", 0) < score){
                can.drawText("The game is finished your new High Score is:  " + score, 650, 500, paint);

            }
            else {
                can.drawText("The game is finished your Score is:  " + score, 500, 500, paint);
            }

            getHolder().unlockCanvasAndPost(can);
            act.finish();
        }
    }

    /**
     * using shared preferences, user score is saved if its higher than the previous high score
     * @return
     */
    public int saveScore(){
        if(preferences.getInt("Score: ", 0) < score){
            SharedPreferences.Editor edit = preferences.edit();
            edit.putInt("Score: ", score);
            edit.apply();
        }
        return score;
    }
}
