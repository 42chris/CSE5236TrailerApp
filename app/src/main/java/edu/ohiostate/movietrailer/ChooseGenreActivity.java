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

import java.io.Serializable;

/**
 * Created by andrewpetrilla on 10/29/16.
 */

public class ChooseGenreActivity extends Activity {


    Spinner spinner;
    private Button mGoButton;
    private String genre;
    private Template movieTemplate;

    public static final String MOVIE_TEMPLATE = "MyMovie";

    SSDataBaseAdapter mSSDataBaseAdapter;

    private static final String TAG = "ChooseGenreActivity";

    //    private Prompt[] mPromptBank = new Prompt[]{
//            new Prompt(R.string.new_account,PromptType.TEXT),
//            new Prompt(R.string.genre_string,PromptType.TEXT)};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate(Bundle) called");
        setContentView(R.layout.genre_choice);

        spinner = (Spinner) findViewById(R.id.spinner);
        mGoButton = (Button)findViewById(R.id.go_button);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.genre_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        mSSDataBaseAdapter=new SSDataBaseAdapter(this);
        mSSDataBaseAdapter=mSSDataBaseAdapter.open();

        spinner.setOnItemSelectedListener((new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    mGoButton.setEnabled(true);
                    genre = spinner.getSelectedItem().toString().toLowerCase();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mGoButton.setEnabled(false);
            }
        }));

        mGoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movieTemplate = new Template(genre,mSSDataBaseAdapter);
                movieTemplate.setClipArray();
                Log.d(TAG, "CHECK: " + movieTemplate.clipArray.size());
                Intent intentChooseActors = new Intent(getApplicationContext(),ChooseActorsActivity.class);
                Bundle chooseActorsBundle = new Bundle();
                chooseActorsBundle.putSerializable(MOVIE_TEMPLATE, movieTemplate);
                intentChooseActors.putExtras(chooseActorsBundle);
                startActivity(intentChooseActors);
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
        mSSDataBaseAdapter.close();
        Log.d(TAG,"onDestroy() called");
    }


}
