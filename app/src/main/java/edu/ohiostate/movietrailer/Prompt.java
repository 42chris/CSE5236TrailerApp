package edu.ohiostate.movietrailer;

/**
 * Created by petri on 10/10/2016.
 */



public class Prompt {

    private String mQuestion;

    private String mType;

    private float length;

    private String[] choices;

    private boolean complete;

    public Prompt(String question, String pT, float length, String[] choices){
        mQuestion = question;
        mType = pT;
        this.length = length;
        this.choices  = choices;
        this.complete = false;
    }

    public String getQuestion() {
        return mQuestion;
    }

    public void setQuestion(String question) {
        mQuestion = question;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public void setComplete(){
        this.complete = true;
    }

    public boolean getComplete(){
        return this.complete;
    }

    public float getLength(){ return this.length;}
}
