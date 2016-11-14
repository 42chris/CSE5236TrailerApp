package edu.ohiostate.movietrailer;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Spinner;
import android.widget.VideoView;

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
    private VideoView videoView;
    private MediaController mediaController;
    private int position = 0;

    private static final String TAG = "FacebookActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_and_share);

        Intent mIntent = getIntent();
        String filePath = mIntent.getStringExtra("file");
        videoView = (VideoView) findViewById(R.id.videoView);

        if (mediaController == null) {
            mediaController = new MediaController(FacebookActivity.this);

            // Set the videoView that acts as the anchor for the MediaController.
            mediaController.setAnchorView(videoView);


            // Set MediaController for VideoView
            videoView.setMediaController(mediaController);
        }
        shareButton = (Button) findViewById(R.id.shareButton);
        Uri uri = Uri.fromFile(new File(filePath));
        share = new ShareVideo.Builder().setLocalUrl(uri).build();
        content = new ShareVideoContent.Builder().setVideo(share).build();

            videoView.setVideoPath(filePath);

        videoView.requestFocus();

        // When the video file ready for playback.
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            public void onPrepared(MediaPlayer mediaPlayer) {


                videoView.seekTo(position);
                if (position == 0) {
                    videoView.start();
                }

                // When video Screen change size.
                mediaPlayer.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                    @Override
                    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {

                        // Re-Set the videoView that acts as the anchor for the MediaController
                        mediaController.setAnchorView(videoView);
                    }
                });
            }
        });


        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    // When you change direction of phone, this method will be called.
    // It store the state of video (Current position)
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        // Store current position.
        savedInstanceState.putInt("CurrentPosition", videoView.getCurrentPosition());
        videoView.pause();
    }


    // After rotating the phone. This method is called.
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // Get saved position.
        position = savedInstanceState.getInt("CurrentPosition");
        videoView.seekTo(position);
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
