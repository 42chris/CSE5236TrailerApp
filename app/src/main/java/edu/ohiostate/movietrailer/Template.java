package edu.ohiostate.movietrailer;

import android.os.Parcel;
import android.os.Parcelable;

import com.googlecode.mp4parser.authoring.tracks.H263TrackImpl;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by andrewpetrilla on 10/29/16.
 */

public class Template implements Serializable{

    private String genre = "";
    int numPlayers = 0;
    ArrayList<Prompt> promptArray;
    ArrayList<Clip> clipArray;
    SSDataBaseAdapter mSSDataBaseAdapter;

    public Template(String genre, SSDataBaseAdapter db){
        this.genre = genre;
        this.clipArray= new ArrayList<Clip>();
        this.promptArray = new ArrayList<Prompt>();
        this.mSSDataBaseAdapter = db;
    }


    public void setPromptArray(int numPlayers){
        this.numPlayers = numPlayers;
        mSSDataBaseAdapter=mSSDataBaseAdapter.open();
        this.promptArray = mSSDataBaseAdapter.getPromptArray(this.genre,this.numPlayers);
        mSSDataBaseAdapter.close();


    }

    public void setClipArray(){
        mSSDataBaseAdapter=mSSDataBaseAdapter.open();

        this.clipArray = mSSDataBaseAdapter.getClipArray(this.genre);
        mSSDataBaseAdapter.close();
    }

    public ArrayList<Prompt> getPromptArray(){
        return this.promptArray;
    }


}
