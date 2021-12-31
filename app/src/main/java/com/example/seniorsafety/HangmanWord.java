package com.example.seniorsafety;

import android.os.Parcel;
import android.os.Parcelable;

public class HangmanWord implements Parcelable {
    private String word;
    private String category;
    private String id;

    public HangmanWord (){

    }
    protected HangmanWord(Parcel in) {
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static final Creator<HangmanWord> CREATOR = new Creator<HangmanWord>() {
        @Override
        public HangmanWord createFromParcel(Parcel in) {
            return new HangmanWord(in);
        }

        @Override
        public HangmanWord[] newArray(int size) {
            return new HangmanWord[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    @Override
    public String toString() {
        return "HangmanWord{" +
                "word='" + word + '\'' +
                ", category='" + category + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
