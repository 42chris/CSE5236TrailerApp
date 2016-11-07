package edu.ohiostate.movietrailer;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

public class SplashScreen extends Activity {


    public static final String PREFS_NAME = "MyPrefsFile";

    SSDataBaseAdapter mSplashScreenDataBaseAdapter;

    private static final String TAG = "SplashScreen";
    private static final String SHARED_PREFS_NAME = "mySharedPrefs";
    private static final String SHARED_PREFS_DB_CREATED = "dbCreated";
    private static final String SHARED_PREFS_RESULT = "dbHasBeenCreated";

    //    private Prompt[] mPromptBank = new Prompt[]{
//            new Prompt(R.string.new_account,PromptType.TEXT),
//            new Prompt(R.string.genre_string,PromptType.TEXT)};
    private  int mCurrentIndex = 0;

    static final String DATABASE_NAME = "template.db";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        Log.d(TAG,"onCreate(Bundle) called");

        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS_NAME, MODE_PRIVATE);
        String created = prefs.getString(SHARED_PREFS_DB_CREATED, null);

        mSplashScreenDataBaseAdapter=new SSDataBaseAdapter(this);
        mSplashScreenDataBaseAdapter= mSplashScreenDataBaseAdapter.open();

        if(created == null){
            Log.d(TAG, "Database not yet created");

            mSplashScreenDataBaseAdapter.populateTemplates();

<<<<<<< Updated upstream
            String[] promptNames = {"p1"};
=======
        String[] promptNames = {"test"};
>>>>>>> Stashed changes

            mSplashScreenDataBaseAdapter.populatePrompts(promptNames);

            String[] clipSetNames = {"TESTC"};
            mSplashScreenDataBaseAdapter.populateClips(clipSetNames);

            SharedPreferences.Editor editor = getSharedPreferences(SHARED_PREFS_NAME, MODE_PRIVATE).edit();
            editor.putString(SHARED_PREFS_DB_CREATED, SHARED_PREFS_RESULT);
            editor.commit();
        }

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
