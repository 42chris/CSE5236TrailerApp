package edu.ohiostate.movietrailer;

import android.util.Log;

import com.googlecode.mp4parser.FileDataSourceImpl;
import com.googlecode.mp4parser.authoring.tracks.h264.H264TrackImpl;

import java.io.IOException;

/**
 * Created by andrewpetrilla on 10/29/16.
 */

public class Clip {

    private String clipID;
    private String path;
    private boolean created;
    private H264TrackImpl h264Track;

    public Clip(String path){
        this.path = path;
        this.created = true;
        try {
            h264Track = new H264TrackImpl(new FileDataSourceImpl(path));
        }catch(IOException e){
            Log.d("Clip"+clipID,"File not Found. video not added to Clip object");
        }
    }

    public Clip(){
        this.created = false;
    }
}

