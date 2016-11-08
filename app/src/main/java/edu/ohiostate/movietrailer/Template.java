package edu.ohiostate.movietrailer;

import android.app.Application;
import android.os.Parcel;
import android.os.Parcelable;

import com.googlecode.mp4parser.authoring.tracks.H263TrackImpl;

import java.io.IOException;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by andrewpetrilla on 10/29/16.
 */

public class Template{

    private String genre = "";
    int numPlayers = 0;
    Queue<Prompt> promptArray;
    ArrayList<Clip> clipArray;
    SSDataBaseAdapter mSSDataBaseAdapter;

    public Template(String genre, SSDataBaseAdapter db){
        this.genre = genre;
        this.clipArray= new ArrayList<Clip>();
        this.promptArray = new ConcurrentLinkedQueue<Prompt>();
        this.mSSDataBaseAdapter = db;
    }


    public void setPromptArray(int numPlayers, SSDataBaseAdapter db){
        this.numPlayers = numPlayers;
        this.mSSDataBaseAdapter= db.open();
        this.promptArray = mSSDataBaseAdapter.getPromptArray(this.genre,this.numPlayers);
        mSSDataBaseAdapter.close();
        //TODO I think there are some loose ends with this database, look into making sure it's closed

    }

    public void setClipArray(){
        mSSDataBaseAdapter=mSSDataBaseAdapter.open();

        this.clipArray = mSSDataBaseAdapter.getClipArray(this.genre);
        mSSDataBaseAdapter.close();
    }

    public void setClip (Clip newClip, int index){
        this.clipArray.set(index,newClip);
    }

    public Queue<Prompt> getPromptArray(){
        return this.promptArray;
    }


}
