package edu.ohiostate.movietrailer;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.Spinner;
import android.widget.VideoView;

import com.facebook.share.model.ShareVideo;
import com.facebook.share.model.ShareVideoContent;
import com.facebook.share.widget.ShareButton;

import java.io.File;
import java.io.IOException;

/**
 * Created by Chris on 11/7/2016.
 */
public class FacebookActivity extends AppCompatActivity {

    Uri videoFileUri;
    ShareVideo share;
    ShareVideoContent content;
    ShareButton shareButton;
    private VideoView videoView;
    private MediaController mediaController;
    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    private int position = 0;
    private Button saveToGallery;
    private String filePath;
    ProfileDataBaseAdapter profileDataBaseAdapter;

    private static final String TAG = "FacebookActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_and_share);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        getSupportActionBar().setTitle("Movie Trailer : "+TrailerApp.getInstance().mainTemplate.getName());
        Intent mIntent = getIntent();
        filePath = mIntent.getStringExtra("file");
        videoView = (VideoView) findViewById(R.id.videoView);

        saveToGallery = (Button) findViewById(R.id.save_button);

        if (mediaController == null) {
            mediaController = new MediaController(FacebookActivity.this);

            // Set the videoView that acts as the anchor for the MediaController.
            mediaController.setAnchorView(videoView);


            // Set MediaController for VideoView
            videoView.setMediaController(mediaController);
        }
        shareButton = (ShareButton) findViewById(R.id.shareButton);

        Uri uri = Uri.fromFile(new File(filePath));
        share = new ShareVideo.Builder().setLocalUrl(uri).build();
        content = new ShareVideoContent.Builder().setVideo(share).build();
        shareButton.setShareContent(content);
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

        profileDataBaseAdapter = new ProfileDataBaseAdapter(this);
        profileDataBaseAdapter = profileDataBaseAdapter.open();

        saveToGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileDataBaseAdapter.insertEntry(TrailerApp.getInstance().getUser(),TrailerApp.getInstance().mainTemplate.getName(),filePath);
                profileDataBaseAdapter.close();
                Intent intentGallery = new Intent(getApplicationContext(),GalleryActivity.class);
                startActivity(intentGallery);
                finish();
            }
            });



    }

    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.choose_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                Intent intentMainMenu = new Intent(getApplicationContext(),MainMenuActivity.class);
                startActivity(intentMainMenu);
                return true;

            case R.id.action_profile:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                Intent intentSettings = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intentSettings);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
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

    private void addDrawerItems() {
        String[] osArray = { "Android", "iOS", "Windows", "OS X", "Linux" };
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mAdapter);
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
