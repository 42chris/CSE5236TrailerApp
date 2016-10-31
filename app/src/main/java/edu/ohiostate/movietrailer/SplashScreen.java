package edu.ohiostate.movietrailer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class SplashScreen extends Activity {


    public static final String PREFS_NAME = "MyPrefsFile";

    SSDataBaseAdapter mSplashScreenDataBaseAdapter;

    private static final String TAG = "SplashScreen";

    //    private Prompt[] mPromptBank = new Prompt[]{
//            new Prompt(R.string.new_account,PromptType.TEXT),
//            new Prompt(R.string.genre_string,PromptType.TEXT)};
    private  int mCurrentIndex = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate(Bundle) called");
        setContentView(R.layout.splash_screen);

        mSplashScreenDataBaseAdapter=new SSDataBaseAdapter(this);
        mSplashScreenDataBaseAdapter= mSplashScreenDataBaseAdapter.open();


        mSplashScreenDataBaseAdapter.populateTemplates();
        String[] promptNames = {"test"};
        mSplashScreenDataBaseAdapter.populatePrompts(promptNames);
        String[] clipSetNames = {"TESTC"};
        mSplashScreenDataBaseAdapter.populateClips(clipSetNames);


        Intent intentLogin = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(intentLogin);

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
        //mSplashScreenDataBaseAdapter.close();
        Log.d(TAG,"onDestroy() called");
    }
}
