package edu.ohiostate.movietrailer;

/**
 * Created by petri on 10/10/2016.
 */



public class Prompt {

    private int mQuestion;

    private PromptType mType;

    public Prompt(int question, PromptType pT){
        mQuestion = question;
        mType = pT;
    }

    public int getQuestion() {
        return mQuestion;
    }

    public void setQuestion(int question) {
        mQuestion = question;
    }

    public PromptType getType() {
        return mType;
    }

    public void setType(PromptType type) {
        mType = type;
    }
}
