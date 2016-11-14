package edu.ohiostate.movietrailer;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import com.facebook.share.model.ShareVideo;
import com.facebook.share.model.ShareVideoContent;

import java.io.File;
import java.io.IOException;

/**
 * Created by Chris on 11/7/2016.
 */
public class FacebookActivity extends Activity {

    Uri videoFileUri;
    ShareVideo share;
    ShareVideoContent content;
    Button shareButton;

    private static final String TAG = "FacebookActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_and_share);

        Intent mIntent = getIntent();
        String filePath = mIntent.getStringExtra("file");
        shareButton = (Button) findViewById(R.id.shareButton);
        Uri uri = Uri.fromFile(new File(filePath));
        share = new ShareVideo.Builder().setLocalUrl(uri).build();
        content = new ShareVideoContent.Builder().setVideo(share).build();

        MediaPlayer mp = new MediaPlayer();
        try{
            mp.setDataSource(filePath);
        }catch(IOException e){
            Log.d(TAG, "Video failed to play");
        }

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }

    @Override
    protected void onStart(){
        super.onStart();
    }

    @Override
    protected void onPause(){
        super.onPause();
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public void onStop(){
        super.onStop();
    }
}
