package com.example.speechapp;
import java.util.*;

public class Session {

    String description;
    int words_per_minute;
    float accuracy;
    int id;

    Session(String description, int words_per_minute, float accuracy, int id){
        this.description = description;
        this.words_per_minute = words_per_minute;
        this.accuracy = accuracy;
        this.id = id;
    }

    public String getDescription(){
        return this.description;
    }

    public int getWordsPerMinute(){
        return this.words_per_minute;
    }

    public float getAccuracy(){
        return accuracy;
    }
}
