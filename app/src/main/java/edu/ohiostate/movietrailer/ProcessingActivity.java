package edu.ohiostate.movietrailer;

import android.Manifest;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.os.Process;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.coremedia.iso.boxes.Container;
import com.facebook.share.model.ShareVideo;
import com.facebook.share.model.ShareVideoContent;
import com.facebook.share.widget.ShareButton;
import com.googlecode.mp4parser.authoring.Movie;
import com.googlecode.mp4parser.authoring.Track;
import com.googlecode.mp4parser.authoring.builder.DefaultMp4Builder;
import com.googlecode.mp4parser.authoring.tracks.AppendTrack;
import com.googlecode.mp4parser.authoring.tracks.h264.H264TrackImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ProcessingActivity extends AppCompatActivity {


    public static final String PREFS_NAME = "MyPrefsFile";

    public static final String MOVIE_TEMPLATE = "MyMovie";


    SSDataBaseAdapter mSSDataBaseAdapter;

    private static final String TAG = "ProcessingActivity";

    private Template movieTemplate;
    private int currentClipIndex;
    private Fragment currentFragment;
    private Button shootVideoButton;

    ShareVideo share;
    ShareVideoContent content;
    ShareButton shareButton;


    private String processingText;
    private TextView processingDialogue;

    //    private Prompt[] mPromptBank = new Prompt[]{
//            new Prompt(R.string.new_account,PromptType.TEXT),
//            new Prompt(R.string.genre_string,PromptType.TEXT)};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate(Bundle) called");
        setContentView(R.layout.fragment_video);
        processingDialogue = (TextView) findViewById(R.id.questionView);
        processingDialogue.setText("Click Go to continue creating your trailer!");
        movieTemplate = TrailerApp.getInstance().mainTemplate;
        shootVideoButton = (Button) findViewById(R.id.go_button);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        shootVideoButton.setEnabled(true);
        shootVideoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i =0 ; i<movieTemplate.clipArray.size();i++) {
                    if (!movieTemplate.clipArray.get(i).isCreated() && !movieTemplate.promptArray.isEmpty()){
                        Prompt poppedPrompt = TrailerApp.getInstance().mainTemplate.getPromptArray().remove();
                        Intent intentPromptActivity = new Intent(ProcessingActivity.this,PromptActivity.class);
                        intentPromptActivity.putExtra("question",poppedPrompt.getQuestion());
                        intentPromptActivity.putExtra("index",i);
                        intentPromptActivity.putExtra("time",poppedPrompt.getLength());
                        i = movieTemplate.clipArray.size();
                        startActivity(intentPromptActivity);
                    }
                }
                boolean process = true;
                for (Clip c:movieTemplate.clipArray){
                    if (!c.isCreated()){
                        process = false;
                    }
                }
                if (process){
                    ArrayList<Clip> clipArray  = movieTemplate.clipArray;
                    processingDialogue.setText("Processing Your Trailer Now...");
                    shootVideoButton.setVisibility(View.INVISIBLE);
                    shootVideoButton.setEnabled(false);
                    String filePath  = processClips(clipArray);
                    processingDialogue.setText("Processing Done");

                    Intent intentPlayAndShare = new Intent(getApplicationContext(),FacebookActivity.class);
                    intentPlayAndShare.putExtra("file",filePath);
                    startActivity(intentPlayAndShare);


                }
            }
        });






    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        movieTemplate = TrailerApp.getInstance().mainTemplate;

    }

    public int getCurrentClipIndex(){
        return this.currentClipIndex;
    }

    public String processClips(ArrayList<Clip> clipArray){
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
        if (!audioTracks.isEmpty()) {
            try {
                result.addTrack(new AppendTrack(audioTracks.toArray(new Track[audioTracks.size()])));
            }catch(IOException e){
                Log.d(TAG,"failed to append audio tracks");
            }
        }
        if (!videoTracks.isEmpty()) {
            try {
                result.addTrack(new AppendTrack(videoTracks.toArray(new Track[videoTracks.size()])));
            }catch(IOException e){
                Log.d(TAG,"failed to append tracks");
            }
        }

        Container out = new DefaultMp4Builder().build(result);
        FileChannel fc = null;
        String videoPath = null;
        boolean writable = isExternalStorageWritable();
        try {
            videoPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES).getAbsolutePath()+"/"+"convert"+ SystemClock.elapsedRealtime()+".mp4";
            fc = new RandomAccessFile(videoPath, "rw").getChannel();

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

        for (Clip c: clipArray ){
            if (c.isCreated()){
                String clipPath = c.getPath();
                File fdelete = new File(clipPath);
                if (fdelete.exists()) {
                    if (fdelete.delete()) {
                        System.out.println("file Deleted :" + clipPath);
                    } else {
                        System.out.println("file not Deleted :" + clipPath);
                    }
                }
            }
        }



//        SharedPreferences settings  = getSharedPreferences(PREFS_NAME,0);
//        boolean silent = settings.getBoolean()
//        mQuestionTextView = (TextView)findViewById(R.id.prompt_text_view);
//        int question = mPromptBank[mCurrentIndex].getQuestion();
//        mQuestionTextView.setText(question);



        return videoPath;
    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

//    public boolean checkLogin(){
//
//    }

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
                for (Clip c: movieTemplate.clipArray ){
                    if (c.isCreated()){
                        String clipPath = c.getPath();
                        File fdelete = new File(clipPath);
                        if (fdelete.exists()) {
                            if (fdelete.delete()) {
                                System.out.println("file Deleted :" + clipPath);
                            } else {
                                System.out.println("file not Deleted :" + clipPath);
                            }
                        }
                    }
                    c.unCreate();
                }
                ProcessingActivity.super.onBackPressed();
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
//        Log.d(TAG,"onResume() called");
//        movieTemplate = TrailerApp.getInstance().mainTemplate;
//
//        ArrayList<Clip> clipArray = movieTemplate.clipArray;
//        for (int i =0 ; i<clipArray.size();i++) {
//            if (!clipArray.get(i).isCreated()){
//                Intent intentPromptActivity = new Intent(getApplicationContext(),PromptActivity.class);
//                intentPromptActivity.putExtra("index",i);
//                startActivity(intentPromptActivity);
//            }
//        }
//
//        boolean process = true;
//        for (Clip c:clipArray){
//            if (!c.isCreated()){
//                process = false;
//            }
//        }
//        if (process){
//            processClips(clipArray);
//        }

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
}
