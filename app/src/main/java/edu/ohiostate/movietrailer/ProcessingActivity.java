package edu.ohiostate.movietrailer;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.coremedia.iso.boxes.Container;
import com.googlecode.mp4parser.authoring.Movie;
import com.googlecode.mp4parser.authoring.Track;
import com.googlecode.mp4parser.authoring.builder.DefaultMp4Builder;
import com.googlecode.mp4parser.authoring.tracks.AppendTrack;
import com.googlecode.mp4parser.authoring.tracks.h264.H264TrackImpl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ProcessingActivity extends Activity {


    public static final String PREFS_NAME = "MyPrefsFile";

    public static final String MOVIE_TEMPLATE = "MyMovie";

    SSDataBaseAdapter mSSDataBaseAdapter;

    private static final String TAG = "ProcessingActivity";

    private Template movieTemplate;

    //    private Prompt[] mPromptBank = new Prompt[]{
//            new Prompt(R.string.new_account,PromptType.TEXT),
//            new Prompt(R.string.genre_string,PromptType.TEXT)};
    private  int mCurrentIndex = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate(Bundle) called");
        setContentView(R.layout.processing_screen);

        movieTemplate = TrailerApp.getInstance().mainTemplate;

        ArrayList<Clip> clipArray = movieTemplate.clipArray;
        for (Clip c:clipArray) {
            if (!c.isCreated()){
                Intent intentDisplayPrompt = new Intent(getApplicationContext(),PromptDisplayActivity.class);
                startActivity(intentDisplayPrompt);
            }
        }


        List<Track> videoTracks = new LinkedList<Track>();
        List<Track> audioTracks = new LinkedList<Track>();
        for (Clip c: clipArray) {
            Movie m = c.getH264Track();
            for (Track t:m.getTracks()){
                if (t.getHandler().equals("soun")) {
                    audioTracks.add(t);
                }
                if (t.getHandler().equals("vide")) {
                    videoTracks.add(t);
                }
            }
        }
        Movie result = new Movie();
        if (!videoTracks.isEmpty()) {
            try {
                result.addTrack(new AppendTrack(videoTracks.toArray(new Track[videoTracks.size()])));
            }catch(IOException e){
                Log.d(TAG,"failed to append tracks");
            }
        }

        Container out = new DefaultMp4Builder().build(result);
        FileChannel fc = null;
        try {
            fc = new RandomAccessFile(String.format("output.mp4"), "rw").getChannel();
        }catch (FileNotFoundException e){
            Log.d(TAG,"File not found when appending Tracks");
        }
        try {
            out.writeContainer(fc);
        }catch(IOException e){
            Log.d(TAG,"Failed to write to container");
        }
        try {
            fc.close();
        }catch(IOException e){
            Log.d(TAG,"File failed to close");
        }

//        SharedPreferences settings  = getSharedPreferences(PREFS_NAME,0);
//        boolean silent = settings.getBoolean()
//        mQuestionTextView = (TextView)findViewById(R.id.prompt_text_view);
//        int question = mPromptBank[mCurrentIndex].getQuestion();
//        mQuestionTextView.setText(question);

        mSSDataBaseAdapter=new SSDataBaseAdapter(this);
        mSSDataBaseAdapter=mSSDataBaseAdapter.open();




    }

//    public boolean checkLogin(){
//
//    }

    @Override
    public void onStart(){
        super.onStart();
        Log.d(TAG,"onStart() called");
    }
    @Override
    public void onPause(){
        super.onPause();
        Log.d(TAG,"onPause() called");
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d(TAG,"onResume() called");
        setContentView(R.layout.processing_screen);

        movieTemplate = TrailerApp.getInstance().mainTemplate;

        ArrayList<Clip> clipArray = movieTemplate.clipArray;
        for (Clip c:clipArray) {
            if (!c.isCreated()){
                Intent intentDisplayPrompt = new Intent(getApplicationContext(),PromptDisplayActivity.class);
                startActivity(intentDisplayPrompt);
            }
        }

        List<Track> videoTracks = new LinkedList<Track>();
        List<Track> audioTracks = new LinkedList<Track>();
        for (Clip c: clipArray) {
            Movie m = c.getH264Track();
            for (Track t:m.getTracks()){
                if (t.getHandler().equals("soun")) {
                    audioTracks.add(t);
                }
                if (t.getHandler().equals("vide")) {
                    videoTracks.add(t);
                }
            }
        }
        Movie result = new Movie();
        if (!videoTracks.isEmpty()) {
            try {
                result.addTrack(new AppendTrack(videoTracks.toArray(new Track[videoTracks.size()])));
            }catch(IOException e){
                Log.d(TAG,"failed to append tracks");
            }
        }

        Container out = new DefaultMp4Builder().build(result);
        FileChannel fc = null;
        try {
            fc = new RandomAccessFile(String.format("output.mp4"), "rw").getChannel();
        }catch (FileNotFoundException e){
            Log.d(TAG,"File not found when appending Tracks");
        }
        try {
            out.writeContainer(fc);
        }catch(IOException e){
            Log.d(TAG,"Failed to write to container");
        }
        try {
            fc.close();
        }catch(IOException e){
            Log.d(TAG,"File failed to close");
        }

//        SharedPreferences settings  = getSharedPreferences(PREFS_NAME,0);
//        boolean silent = settings.getBoolean()
//        mQuestionTextView = (TextView)findViewById(R.id.prompt_text_view);
//        int question = mPromptBank[mCurrentIndex].getQuestion();
//        mQuestionTextView.setText(question);

        mSSDataBaseAdapter=new SSDataBaseAdapter(this);
        mSSDataBaseAdapter=mSSDataBaseAdapter.open();
    }

    @Override
    public void onStop(){
        super.onStop();
        Log.d(TAG,"onStop() called");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        mSSDataBaseAdapter.close();
        Log.d(TAG,"onDestroy() called");
    }
}
