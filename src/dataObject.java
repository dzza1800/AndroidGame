package com.example.androidgame;

public class dataObject {
    String name;
    String score;

    /**
     * constructor for creating objects
     * @param name
     * @param score
     */
    dataObject(String name, String score){
        this.name = name;
        this.score = score;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getScore(){
        return score;
    }
    public void setScore(String score){
        this.score = score;
    }
}
