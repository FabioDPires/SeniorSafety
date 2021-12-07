package com.example.seniorsafety.games;

import java.util.Random;

public class MathQuestion {
    private static final int MAX_NUMBER = 10;
    private int n1;
    private int n2;
    private String operation;
    private int answer;
    private int[] options;
    private int correctAnswerPos;
    private String questionDescription;

    public MathQuestion() {
        Random numberRandomizer = new Random();
        this.n1 = numberRandomizer.nextInt(this.MAX_NUMBER + 1);
        this.n2 = numberRandomizer.nextInt(this.MAX_NUMBER + 1);
        int randomOperation = numberRandomizer.nextInt(3);
        this.operation = this.getOperation(randomOperation);
        this.calculateCorrectAnswer();
        this.correctAnswerPos = numberRandomizer.nextInt(4);

        //filling the array of answer options with some values
        this.options = new int[]{0, 0, 0, 0};
        this.options[0] = this.answer + 1;
        this.options[1] = this.answer + 7;
        this.options[2] = this.answer - 3;
        this.options[3] = this.answer - 5;

        this.options = this.shuffleOptions(this.options);
        this.options[this.correctAnswerPos] = this.answer;

    }

    public int getAnswer() {
        return this.answer;
    }

    public int[] getOptions(){
        return this.options;
    }

    public String getQuestionDescription(){
        return this.questionDescription;
    }

    private String getOperation(int randomNumber) {
        System.out.println("RANDOM NUMBER: " + randomNumber);
        String toBePerformedOperation = null;
        switch (randomNumber) {
            case 0:
                toBePerformedOperation = "+";
                break;
            case 1:
                toBePerformedOperation = "-";
                break;
            case 2:
                toBePerformedOperation = "*";
                break;
        }
        return toBePerformedOperation;
    }

    private void calculateCorrectAnswer() {
        switch (this.operation) {
            case "+":
                this.answer = this.n1 + this.n2;
                this.questionDescription = this.n1 + " + " + this.n2;
                break;
            case "-":
                this.answer = this.n1 - this.n2;
                this.questionDescription = this.n1 + " - " + this.n2;
                break;
            case "*":
                this.answer = this.n1 * this.n2;
                this.questionDescription = this.n1 + " * " + this.n2;
                break;
        }
    }

    private int[] shuffleOptions(int[] array) {
        int index, temp;
        Random r = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            index = r.nextInt(i + 1);
            temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
        return array;
    }

}
