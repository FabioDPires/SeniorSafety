package com.example.seniorsafety;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.seniorsafety.models.SouthWest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HangmanActivity extends AppCompatActivity implements View.OnClickListener {
    TextView mTextViewWord,tvCategory;
    ImageView hangView;
    Button bA, bB, bC, bD, bE, bF, bG, bH, bI, bJ, bK, bL, bM,
            bN, bO, bP, bQ, bR, bS, bT, bU, bV, bW, bX, bY, bZ;
    private String secretWord, category, sticks;
    private int numberOfTries;
    private char chosenLetter;
    private List<HangmanWord> wordList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hangman);
        this.tvCategory=(TextView) findViewById(R.id.textViewCategory);
        this.wordList = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference mDatabaseReference = database.getReference();
        mDatabaseReference.child("words").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println("VEIO AQI");
                wordList.clear();
                for (DataSnapshot subjectDataSnapshot : dataSnapshot.getChildren()) {
                    System.out.println("HERE 2");
                    HangmanWord word = subjectDataSnapshot.getValue(HangmanWord.class);
                    wordList.add(word);
                }
                prepareGame();
                startGame();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    //implements the behavior of the letter buttons
    @Override
    public void onClick(View v) {
        this.chosenLetter = this.checkClickedLetter(v);
        boolean right = this.checkRight();
        //if the user chose a letter not contained in the secret word, we must add a body part(head,arm,leg,etc)
        if (!right) {
            this.addBodyPart();
            this.checkLost();
        }
        this.checkWin();
    }

    //Sets up the buttons for the game
    private void prepareGame() {
        mTextViewWord = (TextView) this.findViewById(R.id.textViewSecretWord);
        hangView = (ImageView) findViewById(R.id.imageViewHang);

        bA = (Button) this.findViewById(R.id.buttonLetterA);
        bB = (Button) this.findViewById(R.id.buttonLetterB);
        bC = (Button) this.findViewById(R.id.buttonLetterC);
        bD = (Button) this.findViewById(R.id.buttonLetterD);
        bE = (Button) this.findViewById(R.id.buttonLetterE);
        bF = (Button) this.findViewById(R.id.buttonLetterF);
        bG = (Button) this.findViewById(R.id.buttonLetterG);
        bH = (Button) this.findViewById(R.id.buttonLetterH);
        bI = (Button) this.findViewById(R.id.buttonLetterI);
        bJ = (Button) this.findViewById(R.id.buttonLetterJ);
        bK = (Button) this.findViewById(R.id.buttonLetterK);
        bL = (Button) this.findViewById(R.id.buttonLetterL);
        bM = (Button) this.findViewById(R.id.buttonLetterM);
        bN = (Button) this.findViewById(R.id.buttonLetterN);
        bO = (Button) this.findViewById(R.id.buttonLetterO);
        bP = (Button) this.findViewById(R.id.buttonLetterP);
        bQ = (Button) this.findViewById(R.id.buttonLetterQ);
        bR = (Button) this.findViewById(R.id.buttonLetterR);
        bS = (Button) this.findViewById(R.id.buttonLetterS);
        bT = (Button) this.findViewById(R.id.buttonLetterT);
        bU = (Button) this.findViewById(R.id.buttonLetterU);
        bV = (Button) this.findViewById(R.id.buttonLetterV);
        bW = (Button) this.findViewById(R.id.buttonLetterW);
        bX = (Button) this.findViewById(R.id.buttonLetterX);
        bY = (Button) this.findViewById(R.id.buttonLetterY);
        bZ = (Button) this.findViewById(R.id.buttonLetterZ);

        //use the onCLick overrided method
        bA.setOnClickListener(this);
        bB.setOnClickListener(this);
        bC.setOnClickListener(this);
        bD.setOnClickListener(this);
        bE.setOnClickListener(this);
        bF.setOnClickListener(this);
        bG.setOnClickListener(this);
        bH.setOnClickListener(this);
        bI.setOnClickListener(this);
        bJ.setOnClickListener(this);
        bK.setOnClickListener(this);
        bL.setOnClickListener(this);
        bM.setOnClickListener(this);
        bN.setOnClickListener(this);
        bO.setOnClickListener(this);
        bP.setOnClickListener(this);
        bQ.setOnClickListener(this);
        bR.setOnClickListener(this);
        bS.setOnClickListener(this);
        bT.setOnClickListener(this);
        bU.setOnClickListener(this);
        bV.setOnClickListener(this);
        bW.setOnClickListener(this);
        bX.setOnClickListener(this);
        bY.setOnClickListener(this);
        bZ.setOnClickListener(this);
    }

    private void startGame() {
        int position;
        Random rand = new Random();
        int upperbound = this.wordList.size();
        position = rand.nextInt(upperbound);
        HangmanWord hangmanWord = this.wordList.get(position);
        this.secretWord = hangmanWord.getWord();
        this.category=hangmanWord.getCategory();
        this.tvCategory.setText("Category: "+this.category);
        System.out.println("SECRET WORD IS : " +this.secretWord);
        this.numberOfTries = 6;
        this.sticks = "";
        this.chosenLetter = ' ';

        for (int i = 0; i < this.secretWord.length(); i++) {
            if (this.secretWord.charAt(i) == '-') {
                this.sticks += " - ";

            } else if (this.secretWord.charAt(i) == ' ') {
                this.sticks += "   ";

            } else {
                this.sticks += " _ ";
            }
        }

        this.activateButtons();
        this.mTextViewWord.setText(this.sticks);
        this.hangView.setImageResource(R.drawable.hang_6);

    }

    //Checks in what letter the user clicked and disables the button since that letter can't be used anymore
    private char checkClickedLetter(View v) {
        Button b = (Button) v;
        char clickedLetter;
        clickedLetter = b.getText().toString().charAt(0);
        v.setEnabled(false);
        return clickedLetter;
    }

    //checks if the letter the user clicked is contained in the secret word
    private boolean checkRight() {
        boolean right = false;

        for (int i = 0; i < this.secretWord.length(); i++) {
            char letterAtPosition = this.secretWord.charAt(i);
            if (this.chosenLetter == letterAtPosition) {
                System.out.println("ESTA PRESENTE");
                this.sticks = this.sticks.substring(0, 3 * i + 1) + letterAtPosition + this.sticks.substring(3 * i + 2);
                right = true;
            }
        }
        this.mTextViewWord.setText(this.sticks);

        return right;
    }

    private void activateButtons() {
        bA.setEnabled(true);
        bB.setEnabled(true);
        bC.setEnabled(true);
        bD.setEnabled(true);
        bE.setEnabled(true);
        bF.setEnabled(true);
        bG.setEnabled(true);
        bH.setEnabled(true);
        bI.setEnabled(true);
        bJ.setEnabled(true);
        bK.setEnabled(true);
        bL.setEnabled(true);
        bM.setEnabled(true);
        bN.setEnabled(true);
        bO.setEnabled(true);
        bP.setEnabled(true);
        bQ.setEnabled(true);
        bR.setEnabled(true);
        bS.setEnabled(true);
        bT.setEnabled(true);
        bU.setEnabled(true);
        bV.setEnabled(true);
        bW.setEnabled(true);
        bX.setEnabled(true);
        bY.setEnabled(true);
        bZ.setEnabled(true);
    }


    private void deactivateButtons() {
        bA.setEnabled(false);
        bB.setEnabled(false);
        bC.setEnabled(false);
        bD.setEnabled(false);
        bE.setEnabled(false);
        bF.setEnabled(false);
        bG.setEnabled(false);
        bH.setEnabled(false);
        bI.setEnabled(false);
        bJ.setEnabled(false);
        bK.setEnabled(false);
        bL.setEnabled(false);
        bM.setEnabled(false);
        bN.setEnabled(false);
        bO.setEnabled(false);
        bP.setEnabled(false);
        bQ.setEnabled(false);
        bR.setEnabled(false);
        bS.setEnabled(false);
        bT.setEnabled(false);
        bU.setEnabled(false);
        bV.setEnabled(false);
        bW.setEnabled(false);
        bX.setEnabled(false);
        bY.setEnabled(false);
        bZ.setEnabled(false);
    }

    private void addBodyPart() {
        this.numberOfTries--;
        switch (this.numberOfTries) {
            case 5:
                hangView.setImageResource(R.drawable.hang_5);
                break;
            case 4:
                hangView.setImageResource(R.drawable.hang_4);
                break;
            case 3:
                hangView.setImageResource(R.drawable.hang_3);
                break;
            case 2:
                hangView.setImageResource(R.drawable.hang_2);
                break;
            case 1:
                hangView.setImageResource(R.drawable.hang_1);
                break;
            case 0:
                hangView.setImageResource(R.drawable.hang_0);
                break;
        }
    }

    //verifies if the user lost
    private void checkLost() {
        if (this.numberOfTries == 0) {
            deactivateButtons();
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            dialogBuilder.setTitle("You lost :(").setMessage("  The secret word was : " + this.secretWord + "\n \nDo you want to play again? \n").setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0, int arg1) {
                            startGame();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0, int arg1) {
                            finish();
                        }
                    })
                    .create().show();
        }
    }

    private void checkWin() {
        if (this.replaceAll(this.sticks, " ", "").equalsIgnoreCase(this.secretWord)) {
            this.deactivateButtons();
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            dialogBuilder.setTitle("YOU WON !")
                    .setMessage("Do you want to play again?").setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0, int arg1) {
                            startGame();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0, int arg1) {
                            finish();
                        }
                    })
                    .create().show();
        }
    }

    private String replaceAll(String source, String pattern, String replacement) {
        if (source == null) {
            return "";
        } else {
            StringBuffer stringBuffer = new StringBuffer();
            int idx = 0;
            String word = source;
            while ((idx = word.indexOf(pattern, idx)) != -1) {
                stringBuffer.append(word.substring(0, idx));
                stringBuffer.append(replacement);
                stringBuffer.append(word.substring(idx + pattern.length()));
                word = stringBuffer.toString();
                stringBuffer.delete(0, stringBuffer.length());
                idx += replacement.length();
            }
            return word;
        }
    }

}