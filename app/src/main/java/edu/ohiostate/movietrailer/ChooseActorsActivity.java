package edu.ohiostate.movietrailer;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.Spinner;

/**
 * Created by andrewpetrilla on 10/29/16.
 */

public class ChooseActorsActivity extends AppCompatActivity {


    Spinner spinner;
    private Button mGoButton;
    private int numActors;
    private Template movieTemplate;
    public static final String PREFS_NAME = "MyPrefsFile";

    SSDataBaseAdapter mSSDataBaseAdapter;

    private static final String TAG = "ChooseActorsActivity";
    public static final String MOVIE_TEMPLATE = "MyMovie";

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
        movieTemplate = TrailerApp.getInstance().mainTemplate;
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Movie Trailer : "+TrailerApp.getInstance().mainTemplate.getName());
        spinner = (Spinner) findViewById(R.id.spinnerActor);
        mGoButton = (Button)findViewById(R.id.go_button);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.actor_array, android.R.layout.simple_spinner_item);
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
                movieTemplate.setPromptArray(numActors, mSSDataBaseAdapter);
                TrailerApp.getInstance().mainTemplate = movieTemplate;
                Intent intentProcessing = new Intent(getApplicationContext(),ProcessingActivity.class);

                startActivity(intentProcessing);
                finish();
            }
        });



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
        mSSDataBaseAdapter.close();
        Log.d(TAG,"onDestroy() called");
    }


}
