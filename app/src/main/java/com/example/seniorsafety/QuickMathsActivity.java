package com.example.seniorsafety;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.seniorsafety.games.MathQuestion;
import com.example.seniorsafety.games.QuickMath;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class QuickMathsActivity extends AppCompatActivity {
    private Button startButton, answer0Button, answer1Button, answer2Button, answer3Button;
    private TextView tvTime, tvQuestion, tvScore, tvNumberOfQuestions;
    private ProgressBar progressTime;
    private QuickMath qm;
    private int remainingTime;
    private CountDownTimer timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_maths);

        this.startButton = (Button) findViewById(R.id.button_startQuickMath);
        this.answer0Button = (Button) findViewById(R.id.buttonAnswer0);
        this.answer1Button = (Button) findViewById(R.id.buttonAnswer1);
        this.answer2Button = (Button) findViewById(R.id.buttonAnswer2);
        this.answer3Button = (Button) findViewById(R.id.buttonAnswer3);

        this.tvTime = (TextView) findViewById(R.id.textViewTime);
        this.tvNumberOfQuestions = (TextView) findViewById(R.id.textViewNumberQuestions);
        this.tvQuestion = (TextView) findViewById(R.id.textViewQuestion);
        this.tvScore = (TextView) findViewById(R.id.textViewScore);

        this.progressTime = (ProgressBar) findViewById(R.id.progressBarTime);

        this.tvTime.setText("0seg");
        this.tvQuestion.setText("");
        this.tvScore.setText("0");
        this.progressTime.setProgress(0);

        this.timer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                remainingTime--;
                tvTime.setText(Integer.toString(remainingTime) + "seconds");
                progressTime.setProgress(60 - remainingTime);
            }

            @Override
            public void onFinish() {
                answer0Button.setEnabled(false);
                answer1Button.setEnabled(false);
                answer2Button.setEnabled(false);
                answer3Button.setEnabled(false);

                tvNumberOfQuestions.setText("Time's out!\n"  +"Correct answers:" +qm.getCorrectAnswers() + "/" + (qm.getTotalAnswers() - 1)
                +"\n Score:"+qm.getScore());
                updateScore();
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        answer0Button.setVisibility(View.INVISIBLE);
                        answer1Button.setVisibility(View.INVISIBLE);
                        answer2Button.setVisibility(View.INVISIBLE);
                        answer3Button.setVisibility(View.INVISIBLE);
                        progressTime.setVisibility(View.INVISIBLE);
                        tvTime.setVisibility(View.INVISIBLE);
                        tvScore.setVisibility(View.INVISIBLE);
                        startButton.setVisibility(View.VISIBLE);
                        tvQuestion.setVisibility(View.INVISIBLE);
                    }
                }, 4000);
            }
        };


        this.startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvNumberOfQuestions.setText("0/0");
                tvScore.setText("0");
                v.setVisibility(View.INVISIBLE);
                tvQuestion.setVisibility(View.VISIBLE);
                answer0Button.setVisibility(View.VISIBLE);
                answer1Button.setVisibility(View.VISIBLE);
                answer2Button.setVisibility(View.VISIBLE);
                answer3Button.setVisibility(View.VISIBLE);
                progressTime.setVisibility(View.VISIBLE);
                tvTime.setVisibility(View.VISIBLE);
                tvScore.setVisibility(View.VISIBLE);
                tvNumberOfQuestions.setVisibility(View.VISIBLE);
                remainingTime = 60;
                qm = new QuickMath();
                displayQuestion();
                timer.start();
            }
        });

        View.OnClickListener submitAnswer = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button buttonClicked = (Button) view;
                int selectedAnswer = Integer.parseInt(buttonClicked.getText().toString());
                qm.checkAnswer(selectedAnswer);
                tvScore.setText(Integer.toString(qm.getScore()));
                displayQuestion();
                tvNumberOfQuestions.setText(qm.getCorrectAnswers() + "/" + (qm.getTotalAnswers() - 1));
            }
        };

        this.answer0Button.setOnClickListener(submitAnswer);
        this.answer1Button.setOnClickListener(submitAnswer);
        this.answer2Button.setOnClickListener(submitAnswer);
        this.answer3Button.setOnClickListener(submitAnswer);
    }

    private void displayQuestion() {
        this.qm.newQuestion();
        int[] answerOptions = this.qm.getCurrentQuestion().getOptions();

        this.answer0Button.setText(Integer.toString(answerOptions[0]));
        this.answer1Button.setText(Integer.toString(answerOptions[1]));
        this.answer2Button.setText(Integer.toString(answerOptions[2]));
        this.answer3Button.setText(Integer.toString(answerOptions[3]));

        this.answer0Button.setEnabled(true);
        this.answer1Button.setEnabled(true);
        this.answer2Button.setEnabled(true);
        this.answer3Button.setEnabled(true);

        this.tvQuestion.setText(this.qm.getCurrentQuestion().getQuestionDescription());
    }

    private void updateScore() {
        FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        String userID=firebaseAuth.getCurrentUser().getUid();
        String userName=firebaseAuth.getCurrentUser().getDisplayName();
        System.out.println("CUrrent logged user: " + userID);
        System.out.println("Display name: "+userName);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("quickMathScore");
        databaseReference.orderByChild("userID").equalTo(userID).addListenerForSingleValueEvent
                (new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        boolean scoreFound=false;
                        //if it enters the for cycle it's because there is already a score for that user;
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            System.out.println("JA EXISTE");
                            Score score=snapshot.getValue(Score.class);
                            if(qm.getScore()>score.getScore()){
                                databaseReference.child(score.getScoreID()).child("score").setValue(qm.getScore());

                            }
                            scoreFound=true;
                        }

                        if(!scoreFound){
                            System.out.println("NAO EXISTE");
                            DatabaseReference databaseReferenceAux=database.getReference();
                            Score score=new Score();
                            score.setScore(qm.getScore());
                            score.setUserID(userID);
                            score.setUserName(userName);
                            score.setScoreID(databaseReferenceAux.child("quickMathScore").push().getKey());
                            databaseReferenceAux.child("quickMathScore").child(score.getScoreID()).setValue(score);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });

    }
}