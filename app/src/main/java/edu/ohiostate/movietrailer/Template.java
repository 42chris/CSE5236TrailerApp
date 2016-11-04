package edu.ohiostate.movietrailer;

import android.os.Parcel;
import android.os.Parcelable;

import com.googlecode.mp4parser.authoring.tracks.H263TrackImpl;

import java.io.IOException;
import java.io.ObjectStreamException;
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


    private void writeObject(java.io.ObjectOutputStream out)
        throws IOException
    {
        out.writeObject(genre);
        out.writeInt(numPlayers);
        out.writeObject(promptArray);
        out.writeObject(clipArray);
        out.flush();
    }

    private void readObject(java.io.ObjectInputStream in)
        throws IOException, ClassNotFoundException
    {
        genre = (String)in.readObject();
        numPlayers = in.readInt();
        promptArray = (ArrayList<Prompt>)in.readObject();
        clipArray = (ArrayList<Clip>)in.readObject();
    }

    private void readObjectNoData()
        throws ObjectStreamException
    {
    }



    public Template(String genre, SSDataBaseAdapter db){
        this.genre = genre;
        this.clipArray= new ArrayList<Clip>();
        this.promptArray = new ArrayList<Prompt>();
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

    public ArrayList<Prompt> getPromptArray(){
        return this.promptArray;
    }


}
