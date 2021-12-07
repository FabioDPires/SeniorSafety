package com.example.seniorsafety;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MemoryGamesActivity extends AppCompatActivity {
    private Button hangmanButton;
    private Button quickMathsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_games);

        this.hangmanButton=(Button) findViewById(R.id.hangman_button);
        this.quickMathsButton=(Button) findViewById(R.id.quickMaths_button);

        this.hangmanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent hangManIntent=new Intent(MemoryGamesActivity.this,HangmanActivity.class);
                startActivity(hangManIntent);
            }
        });

        this.quickMathsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent quickMathsIntent=new Intent(MemoryGamesActivity.this,QuickMathsActivity.class);
                startActivity(quickMathsIntent);
            }
        });
    }
}