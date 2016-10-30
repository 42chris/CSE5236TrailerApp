package edu.ohiostate.movietrailer;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainMenuActivity extends Activity {

    private Button mnewTrailerButton;
    private Button msettingsButton;

    private static final String TAG = "MainMenuActivity";

    private  int mCurrentIndex = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate(Bundle) (MainMenu) called");
        setContentView(R.layout.activity_menu);

        mnewTrailerButton = (Button)findViewById(R.id.new_trailer_button);
        msettingsButton = (Button)findViewById(R.id.settings_button);
        mnewTrailerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainMenuActivity.this,"Create new video",Toast.LENGTH_LONG).show();
                Intent video_intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                startActivity(video_intent);
            }
        });

        msettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainMenuActivity.this,"Settings",Toast.LENGTH_LONG).show();
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
}