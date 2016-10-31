package edu.ohiostate.movietrailer;

    import android.app.Activity;
    import android.content.Intent;
    import android.os.Bundle;
    import android.util.Log;
    import android.view.View;
    import android.widget.AdapterView;
    import android.widget.ArrayAdapter;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.ImageButton;
    import android.widget.Spinner;
    import android.widget.TextView;
    import android.widget.Toast;

    import java.util.ArrayList;

/**
     * Created by andrewpetrilla on 10/29/16.
     */

    public class PromptDisplayActivity extends Activity {


        Spinner spinner;
        private Button mGoButton;
        private String genre;
        private Template movieTemplate;

        SSDataBaseAdapter mSSDataBaseAdapter;

        private static final String TAG = "ChooseGenreActivity";

        //    private Prompt[] mPromptBank = new Prompt[]{
//            new Prompt(R.string.new_account,PromptType.TEXT),
//            new Prompt(R.string.genre_string,PromptType.TEXT)};


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Log.d(TAG,"onCreate(Bundle) called");
            setContentView(R.layout.prompt_display);

            movieTemplate = (Template) getIntent().getSerializableExtra(ChooseGenreActivity.MOVIE_TEMPLATE);

            ArrayList<Prompt> promptList = movieTemplate.getPromptArray();

            for (Prompt p: promptList) {
                //Prompt handling/ opening up Prompt Fragments
            }



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
            mSSDataBaseAdapter.close();
            Log.d(TAG,"onDestroy() called");
        }


    }



