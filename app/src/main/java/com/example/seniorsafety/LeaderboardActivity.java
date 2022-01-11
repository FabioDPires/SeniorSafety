package com.example.seniorsafety;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.seniorsafety.adapters.ScoresAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
        String gameMode = getIntent().getStringExtra("gamemode")+"Score";
        this.rvScores = (RecyclerView) findViewById(R.id.scoresRecyclerView);
        rvScores.addItemDecoration(new DividerItemDecoration(rvScores.getContext(), DividerItemDecoration.VERTICAL));
        this.scoreList = new ArrayList<>();
        ScoresAdapter scoresAdapter = new ScoresAdapter(this, this.scoreList);
        rvScores.setAdapter(scoresAdapter);
        rvScores.setLayoutManager(new LinearLayoutManager(this));

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference mDatabaseReference = database.getReference();
        mDatabaseReference.child(gameMode).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                scoreList.clear();
                for (DataSnapshot subjectDataSnapshot : dataSnapshot.getChildren()) {
                    Score score = subjectDataSnapshot.getValue(Score.class);
                    scoreList.add(score);
                }
                Collections.sort(scoreList, Collections.reverseOrder());
                scoresAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}