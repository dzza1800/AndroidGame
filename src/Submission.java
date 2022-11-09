package com.example.androidgame;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
/*class subIntent{
    Submission sub;
    void subIntent(){
        try{
            sub.startActivity(new Intent(sub, MainActivity.class)); //when clicked, load specified level
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
class myThreads extends  Thread{
    subIntent sub;
    myThreads(subIntent sub){
        this.sub = sub;
    }
    @Override
    public void run(){
        sub.subIntent();
    }
}*/
public class Submission extends Activity {

    @Override
    /** this function for the submission page and to send the score and so on to the firebase database.
     * @param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Point point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);
        setContentView(R.layout.score_sub); //showing contenting on screen
        Toast.makeText(Submission.this,"Connection secured", Toast.LENGTH_LONG).show();
        TextView txtshow = findViewById(R.id.txtshow);

        /*subIntent subInt = new subIntent();
        myThreads sub = new myThreads(subInt);*/



        Intent intent = getIntent();
        int score = intent.getExtras().getInt("Score2");


        String score2 = String.valueOf(score);
        final EditText user, Sid;
        final TextView saScore, exit;
        final Button btn;

        user = findViewById(R.id.usertxt);
        Sid = findViewById(R.id.studentID);
        saScore = findViewById(R.id.sScore);
        exit = findViewById(R.id.exit);
        saScore.setText(score2);
        btn = findViewById(R.id.SubmitB);



        FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = db.getReference("Android Score");

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Thread.sleep(1000); //take 1000ml before exiting app
                    finish();//end activity or activities.
                }
                catch(Exception e){
                    e.printStackTrace();
                };
            }
        });

        btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String ID, name, Sscore;
                ID = Sid.getText().toString();
                name = user.getText().toString();
                Sscore = saScore.getText().toString();

                dataObject dataObj = new dataObject(name, Sscore);
                if(ID.equalsIgnoreCase(" ")){
                    txtshow.setText("Status: Enter Value");
                }
                else{
                    myRef.child(ID).setValue(dataObj);
                    txtshow.setText("Status: Stored, You will be redirected in 5 seconds");
                    Toast.makeText(Submission.this,"Submitted OK", Toast.LENGTH_LONG).show();
                }
                try {
                    /*sub.sleep(5000);
                    sub.start();*/
                    Thread.sleep(1000);
                    startActivity(new Intent(Submission.this, MainActivity.class));

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        });

    }
}