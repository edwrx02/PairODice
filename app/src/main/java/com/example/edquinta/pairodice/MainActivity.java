package com.example.edquinta.pairodice;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.edquinta.pairodice.Player2;


public class MainActivity extends ActionBarActivity {
    private FrameLayout die1, die2;
    private Button roll, hold;
    private int score, roundScore, round, player1, player2;
    private TextView txtValue, p1, p2, player;
    private String me;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        die1 = (FrameLayout)findViewById(R.id.die1);
        die2 = (FrameLayout)findViewById(R.id.die2);
        txtValue = (TextView) findViewById(R.id.round);
        p1 = (TextView) findViewById(R.id.p1);
        p2 = (TextView) findViewById(R.id.them);
        player = (TextView) findViewById(R.id.player);

        player1 = 0;
        player2 = 0;
        me = "Player1:";


        hold = (Button)findViewById(R.id.hold);
        hold.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                player1 += round;
                if (player1 >= 100) {
                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                    alertDialog.setTitle("You Win!");
                    alertDialog.setMessage("You da the man");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
                else{
                    Intent intent = new Intent(MainActivity.this, Player2.class);
                    intent.putExtra("score", round);
                   // player1 = player1 + round;
                    intent.putExtra("p1", player1);
                    intent.putExtra("p2", player2);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(intent);
                }

            }


        });

        Intent intent = getIntent();
        score = intent.getIntExtra("score", 0);
        player1 = intent.getIntExtra("p1", 0);
        player2 = intent.getIntExtra("p2", 0);
        Toast.makeText(this, "The Score is: " + score, Toast.LENGTH_LONG).show();

        setPlayer1(player1);
        setPlayer2(player2);
        setMe(me);

        round = 0;
        roll = (Button)findViewById(R.id.button);
        roll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rollDice();

            }
        });
    }

    public void setPlayer1(int value){
        p1.setText("P1: " + Integer.toString(value));
    }

    public void setPlayer2(int value){
        p2.setText("P2: " + Integer.toString(value));
    }

    public void setMe(String name){
        player.setText(name);
    }
    public void rollDice(){
        int roll1 = 1 + (int)(6*Math.random());
        int roll2 = 1 + (int)(6*Math.random());

        int num1 = roll1;
        int num2 = roll2;

        roundScore = checkScore(num1, num2);
        round = round + roundScore;
        setRound(round);

        setDie(roll1, die1);
        setDie(roll2, die2);
    }

    public int checkScore(int num1, int num2){
        int round1;
        if(num1 == 1 || num2 == 1){
            round1 = 0;
            round = 0;
            Intent intent = new Intent(MainActivity.this, Player2.class);
            score = round;
            player1 = player1 + round;
            intent.putExtra("p1", player1);
            intent.putExtra("p2", player2);
            intent.putExtra("score", score);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        }
        else{
            round1 = num1 + num2;
        }
        return round1;
    }

    public void setRound(int value){
        txtValue.setText("Round: " + Integer.toString(value));

    }


    public void setDie(int value, FrameLayout die) {
        Drawable pic =  null;
        switch (value) {
            case 1:
                pic = getResources().getDrawable(R.drawable.die_face_1);
                break;
            case 2:
                pic = getResources().getDrawable(R.drawable.die_face_2);
                break;
            case 3:
                pic = getResources().getDrawable(R.drawable.die_face_3);
                break;
            case 4:
                pic = getResources().getDrawable(R.drawable.die_face_4);
                break;
            case 5:
                pic = getResources().getDrawable(R.drawable.die_face_5);
                break;
            case 6:
                pic = getResources().getDrawable(R.drawable.die_face_6);
                break;
        }
        die.setBackground(pic);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
