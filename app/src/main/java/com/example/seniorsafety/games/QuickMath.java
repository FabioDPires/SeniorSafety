package com.example.seniorsafety.games;

import java.util.ArrayList;
import java.util.List;

public class QuickMath {
    private List<MathQuestion> questions;
    private int rightAnswers;
    private int wrongAnswers;
    private int totalAnswers;
    private int score;
    private MathQuestion currentQuestion;

    public QuickMath() {
        this.rightAnswers = 0;
        this.wrongAnswers = 0;
        this.totalAnswers = 0;
        this.score = 0;
        this.questions = new ArrayList<>();
    }

    public int getCorrectAnswers() {
        return this.rightAnswers;
    }

    public int getTotalAnswers() {
        return this.totalAnswers;
    }

    public MathQuestion getCurrentQuestion() {
        return this.currentQuestion;
    }

    public int getScore() {
        return this.score;
    }

    public void newQuestion() {
        this.currentQuestion = new MathQuestion();
        this.questions.add(this.currentQuestion);
        this.totalAnswers++;
    }

    public boolean checkAnswer(int userAnswer) {
        boolean correct;
        if (this.currentQuestion.getAnswer() == userAnswer) {
            correct = true;
            this.rightAnswers++;
            this.score += 10;
        } else {
            correct = false;
            this.wrongAnswers++;
        }
        return correct;
    }
}
