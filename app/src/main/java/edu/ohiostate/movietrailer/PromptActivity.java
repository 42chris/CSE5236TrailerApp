package edu.ohiostate.movietrailer;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.coremedia.iso.boxes.Container;
import com.googlecode.mp4parser.authoring.Movie;
import com.googlecode.mp4parser.authoring.Track;
import com.googlecode.mp4parser.authoring.builder.DefaultMp4Builder;
import com.googlecode.mp4parser.authoring.tracks.AppendTrack;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static java.security.AccessController.getContext;

/**
 * Created by andrewpetrilla on 11/7/16.
 */

public class PromptActivity extends Activity {



    private static final String TAG = "PromptActivity";

    private Template movieTemplate;
    private Clip videoClip;
    private Prompt selectedPrompt;
    private TextView question;
    private Button shootVideoButton;
    private String questionText;
    private int index;
    private boolean isComplete;
    static final int REQUEST_VIDEO_CAPTURE = 1;

    //    private Prompt[] mPromptBank = new Prompt[]{
//            new Prompt(R.string.new_account,PromptType.TEXT),
//            new Prompt(R.string.genre_string,PromptType.TEXT)};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate(Bundle) called");
        setContentView(R.layout.fragment_video);

        movieTemplate = TrailerApp.getInstance().mainTemplate;
        Intent mIntent = getIntent();
        int clipIndex = mIntent.getIntExtra("index",0);
        this.index = clipIndex;
        question = (TextView) findViewById(R.id.questionView);
        shootVideoButton = (Button) findViewById(R.id.go_button);


            questionText = mIntent.getStringExtra("question");

            question.setText(questionText);
            shootVideoButton.setEnabled(true);
            shootVideoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent video_intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                    video_intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 10);
                    video_intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                    if (video_intent.resolveActivity(getPackageManager()) != null) {
                        startActivityForResult(video_intent, 1);
                    }
//                    Intent video_intent = new Intent(getApplicationContext(),RecorderActivity.class);
//                    if (video_intent.resolveActivity(getPackageManager()) != null){
//                        startActivityForResult(video_intent, 1);
//                    }
                    //startActivity(video_intent);
                }
            });





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


    }

    @Override
    public void onStop(){
        super.onStop();
        Log.d(TAG,"onStop() called");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG,"onDestroy() called");
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            Uri videoUri = intent.getData();
            Clip newClip = new Clip(videoUri,PromptActivity.this);
            TrailerApp.getInstance().mainTemplate.setClip(newClip, this.index);
            finish();
        }
    }

}
