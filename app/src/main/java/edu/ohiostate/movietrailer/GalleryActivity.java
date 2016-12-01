package edu.ohiostate.movietrailer;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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
import java.util.ArrayList;

/**
 * Created by andrewpetrilla on 10/29/16.
 */

public class GalleryActivity extends AppCompatActivity {


    ShareVideo share;
    ShareVideoContent content;
    private Spinner galleryList;
    private Template movieTemplate;
    private Button deleteButton;
    ShareButton shareButton;
    private VideoView videoView;
    private MediaController mediaController;
    private ArrayAdapter<String> mAdapter;
    public int position = 0;
    private String filePath;
    private boolean selected;
    private String userName;
    ProfileDataBaseAdapter profileDataBaseAdapter;

    private static final String TAG = "GalleryActivity";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate(Bundle) called");
        setContentView(R.layout.gallery);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        userName = TrailerApp.getInstance().getUser();
        galleryList = (Spinner) findViewById(R.id.gallery_list);
        videoView = (VideoView) findViewById(R.id.videoView);
        getSupportActionBar().setTitle( userName+ "'s Trailer Gallery");

        profileDataBaseAdapter = new ProfileDataBaseAdapter(this);
        profileDataBaseAdapter = profileDataBaseAdapter.open();

        if (mediaController == null) {
            mediaController = new MediaController(GalleryActivity.this);

            // Set the videoView that acts as the anchor for the MediaController.
            mediaController.setAnchorView(videoView);


            // Set MediaController for VideoView
            videoView.setMediaController(mediaController);
        }

        ArrayList<String> trailerNames = new ArrayList<String>();
        Cursor cursor2 = profileDataBaseAdapter.db.query(userName,null,null,null,null,null,null);
        if(cursor2.getCount()<1) // UserName Not Exist
        {
            cursor2.close();
        }else {
            cursor2.moveToFirst();
            while (!cursor2.isAfterLast()) {

                String trailerName = cursor2.getString(cursor2.getColumnIndex("NAME"));

                trailerNames.add(trailerName);
                cursor2.moveToNext();
            }

            cursor2.close();
        }

        shareButton = (ShareButton) findViewById(R.id.shareButton);

        selected = false;

        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,trailerNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        galleryList.setSelection(0);
        galleryList.setAdapter(adapter);
        galleryList.setOnItemSelectedListener((new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    selected= true;
                    shareButton.setEnabled(true);
                    filePath = profileDataBaseAdapter.getSingleEntry(userName,galleryList.getSelectedItem().toString());
                Uri uri = Uri.fromFile(new File(filePath));
                share = new ShareVideo.Builder().setLocalUrl(uri).build();
                content = new ShareVideoContent.Builder().setVideo(share).build();
                shareButton.setShareContent(content);

                videoView.setVideoPath(filePath);
                videoView.requestFocus();
                // When the video file ready for playback.
                videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                    public void onPrepared(MediaPlayer mediaPlayer) {


                        videoView.seekTo(getPosition());
                        if (getPosition() == 0) {
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
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selected = false;
                shareButton.setEnabled(false);
            }
        }));








//        deleteButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                movieTemplate = new Template(genre,mSSDataBaseAdapter);
//                movieTemplate.setClipArray();
//                TrailerApp.getInstance().mainTemplate = movieTemplate;
//                Log.d(TAG, "CHECK: " + movieTemplate.clipArray.size());
//                Intent intentChooseActors = new Intent(getApplicationContext(),ChooseActorsActivity.class);
//                startActivity(intentChooseActors);
//            }
//        });



    }

//    public boolean checkLogin(){
//
//    }

    // When you change direction of phone, this method will be called.
    // It store the state of video (Current position)
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        // Store current position.
        savedInstanceState.putInt("CurrentPosition", videoView.getCurrentPosition());
        videoView.pause();
    }

    public int getPosition(){
        return position;
    }


    // After rotating the phone. This method is called.
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // Get saved position.
        position = savedInstanceState.getInt("CurrentPosition");
        videoView.seekTo(position);
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

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Continue Or Not");
        builder.setMessage("Do you want to leave and discard your progress? ");
        builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        builder.setNegativeButton("Leave", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
                startActivity(intent);
                finish();

            }
        });
        builder.show();
    }

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
        profileDataBaseAdapter.close();
        Log.d(TAG,"onDestroy() called");
    }


}
