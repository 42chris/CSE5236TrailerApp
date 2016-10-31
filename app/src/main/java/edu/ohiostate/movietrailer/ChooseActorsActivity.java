package edu.ohiostate.movietrailer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

/**
 * Created by andrewpetrilla on 10/29/16.
 */

public class ChooseActorsActivity extends Activity {


    Spinner spinner;
    private Button mGoButton;
    private int numActors;
    private Template movieTemplate;
    public static final String PREFS_NAME = "MyPrefsFile";

    SSDataBaseAdapter mSSDataBaseAdapter;

    private static final String TAG = "ChooseActorsActivity";

    //    private Prompt[] mPromptBank = new Prompt[]{
//            new Prompt(R.string.new_account,PromptType.TEXT),
//            new Prompt(R.string.genre_string,PromptType.TEXT)};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate(Bundle) called");
        setContentView(R.layout.num_actors);
        numActors = 0;
        //Get movie template from Genre activity
        movieTemplate = (Template) getIntent().getSerializableExtra(ChooseGenreActivity.MOVIE_TEMPLATE);

        spinner = (Spinner) findViewById(R.id.spinnerActor);
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
                    numActors = Integer.parseInt(spinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mGoButton.setEnabled(false);
            }
        }));

        mGoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movieTemplate.setPromptArray(numActors);
                Intent intentProcessing = new Intent(getApplicationContext(),ProcessingActivity.class);
                intentProcessing.putExtra(ChooseGenreActivity.MOVIE_TEMPLATE,movieTemplate);
                startActivity(intentProcessing);
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
