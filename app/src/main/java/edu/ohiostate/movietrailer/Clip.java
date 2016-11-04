package edu.ohiostate.movietrailer;

import android.util.Log;

import com.googlecode.mp4parser.FileDataSourceImpl;
import com.googlecode.mp4parser.authoring.Movie;
import com.googlecode.mp4parser.authoring.Track;
import com.googlecode.mp4parser.authoring.container.mp4.MovieCreator;
import com.googlecode.mp4parser.authoring.tracks.H263TrackImpl;
import com.googlecode.mp4parser.authoring.tracks.h264.H264TrackImpl;

import java.io.IOException;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by andrewpetrilla on 10/29/16.
 */

public class Clip implements Serializable{

    private String clipID;
    private String path;
    private boolean created;
    private Movie h264Track;

    public Clip(String path){
        this.path = path;
        this.created = true;
        try {
            h264Track = MovieCreator.build(path);
        }catch(IOException e){
            Log.d("Clip"+clipID,"File not Found. video not added to Clip object");
        }
    }

    public Clip(){
        this.created = false;
    }

    public boolean isCreated(){
        return this.created;
    }
    public String getPath(){
        return this.path;
    }

    public Movie getH264Track(){
        return this.h264Track;
    }

    private void writeObject(java.io.ObjectOutputStream out)
            throws IOException
    {
        if(clipID != null){
            out.writeObject(clipID);
        }
        else{
            out.writeObject("no ID");
        }
        if(path != null){
            out.writeObject(path);
        }
        else{
            out.writeObject("no Path");
        }
        out.writeBoolean(created);
        out.writeObject(h264Track);
        out.flush();
    }

    private void readObject(java.io.ObjectInputStream in)
            throws IOException, ClassNotFoundException
    {

        clipID = (String)in.readObject();
        path = (String)in.readObject();
        created = in.readBoolean();
        h264Track = (Movie)in.readObject();

        /*
        h264Track = (Movie)in.readObject();
        created = in.readBoolean();
        path = (String)in.readObject();
        clipID = (String)in.readObject();
        */
    }

    private void readObjectNoData()
            throws ObjectStreamException
    {
    }
}

