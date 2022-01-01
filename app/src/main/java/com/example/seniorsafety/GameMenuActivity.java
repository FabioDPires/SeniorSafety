package com.example.seniorsafety;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class GameMenuActivity extends AppCompatActivity {
    private Button playButton;
    private Button leaderboardButton;
    private ImageView ivGameLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_menu);
        String gameMode=getIntent().getStringExtra("gameMode");
        this.ivGameLogo=(ImageView) findViewById(R.id.gameLogo);
        this.setLogo(gameMode);
        this.playButton=(Button) findViewById(R.id.playButton);
        this.leaderboardButton=(Button) findViewById(R.id.leaderboardButton);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gameIntent = null;
                if(gameMode.equals("hangman")){
                    gameIntent=new Intent(GameMenuActivity.this,HangmanActivity.class);
                }
                else if(gameMode.equals("quickMath")){
                    gameIntent=new Intent(GameMenuActivity.this,QuickMathsActivity.class);
                }
                startActivity(gameIntent);
            }
        });
    }

    private void setLogo(String gameMode){
        if(gameMode.equals("hangman")){
            this.ivGameLogo.setImageResource(R.drawable.hangman);
        }
        else if(gameMode.equals("quickMath")){
            this.ivGameLogo.setImageResource(R.drawable.maths);
        }
    }
}