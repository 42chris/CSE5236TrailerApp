package edu.ohiostate.movietrailer;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.net.Uri;
import android.util.Log;
import com.googlecode.mp4parser.FileDataSourceImpl;
import com.googlecode.mp4parser.authoring.Movie;
import com.googlecode.mp4parser.authoring.Track;
import com.googlecode.mp4parser.authoring.container.mp4.MovieCreator;
import com.googlecode.mp4parser.authoring.tracks.H263TrackImpl;
import com.googlecode.mp4parser.authoring.tracks.h264.H264TrackImpl;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectStreamException;
import java.io.Reader;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URI;
import java.util.ArrayList;
import org.apache.commons.io.IOUtils;

/**
 * Created by andrewpetrilla on 10/29/16.
 */

public class Clip implements Serializable{

    private String clipID = "CLIP";
    private String path;
    private boolean created;
    private Movie h264Track;

    public static final String PREFIX = "stream2file";
    public static final String SUFFIX = ".mp4";

    public Clip(String path, Context _context){
        this.path = path;
        this.created = true;
        InputStream test1 =null;
        File testFile = null;
        Movie h264Track = null;
        try{
            test1 = _context.getAssets().open(path);
        }catch(IOException e){
            Log.d("Clip"+clipID,"File not Found. could not find asset");
        }try {
            testFile = stream2file(test1);
        }catch (IOException e){
            Log.d("Clip"+clipID,"Stream 2 file failed");
        }
        try {
            this.h264Track = MovieCreator.build(new FileDataSourceImpl(testFile));
            Log.d("Clip"+clipID,"*******************************CREATED H264 Track!*************************************\n\n\n*************************************\n\n");
        }catch(IOException e){
            Log.d("Clip"+clipID,"File not Found. video not added to Clip object");
        }
    }

    private File stream2file (InputStream in) throws IOException {
        final File tempFile = File.createTempFile(PREFIX, SUFFIX);
        tempFile.deleteOnExit();
        try (FileOutputStream out = new FileOutputStream(tempFile)) {
            IOUtils.copy(in, out);
        }
        return tempFile;
    }

    public Clip(Context _context){
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
        /*out.writeObject(context);*/
        out.flush();
    }

    private void readObject(java.io.ObjectInputStream in)
            throws IOException, ClassNotFoundException
    {

        clipID = (String)in.readObject();
        path = (String)in.readObject();
        created = in.readBoolean();
        h264Track = (Movie)in.readObject();

    }

    private void readObjectNoData()
            throws ObjectStreamException
    {
    }


}

