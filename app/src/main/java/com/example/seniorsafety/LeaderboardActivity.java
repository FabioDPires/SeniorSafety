package com.example.seniorsafety;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.seniorsafety.adapters.ScoresAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LeaderboardActivity extends AppCompatActivity {
    private RecyclerView rvScores;
    private List<Score> scoreList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        String gameMode=getIntent().getStringExtra("gamemode");
        this.rvScores=(RecyclerView) findViewById(R.id.scoresRecyclerView);
        rvScores.addItemDecoration(new DividerItemDecoration(rvScores.getContext(), DividerItemDecoration.VERTICAL));
        this.scoreList=new ArrayList<>();
        ScoresAdapter scoresAdapter=new ScoresAdapter(this,this.scoreList);
        rvScores.setAdapter(scoresAdapter);
        rvScores.setLayoutManager(new LinearLayoutManager(this));
        Score score=new Score();
        score.setScoreID("as");
        score.setScore(36);
        scoreList.add(score);
        score=new Score();
        score.setScoreID("assadasddas");
        score.setScore(92);
        scoreList.add(score);
        Collections.sort(scoreList, Collections.reverseOrder());
    }
}