package com.example.seniorsafety;

import android.os.Parcel;
import android.os.Parcelable;

public class Score implements Parcelable {
    private String scoreID;
    private String userID;
    private String userName;
    private int score;

    public Score() {
    }

    public String getScoreID() {
        return scoreID;
    }

    public void setScoreID(String scoreID) {
        this.scoreID = scoreID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    protected Score(Parcel in) {
    }

    public static final Creator<Score> CREATOR = new Creator<Score>() {
        @Override
        public Score createFromParcel(Parcel in) {
            return new Score(in);
        }

        @Override
        public Score[] newArray(int size) {
            return new Score[size];
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
        return "Score{" +
                "scoreID='" + scoreID + '\'' +
                ", userID='" + userID + '\'' +
                ", userName='" + userName + '\'' +
                ", score='" + score + '\'' +
                '}';
    }
}
