package edu.ohiostate.movietrailer;

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
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created by andrewpetrilla on 10/29/16.
 */

public class NameMovieActivity extends AppCompatActivity {


    private EditText movieName;
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
        setContentView(R.layout.movie_name);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        movieName = (EditText) findViewById(R.id.movieName);
        mGoButton = (Button)findViewById(R.id.go_button);


        mSSDataBaseAdapter=new SSDataBaseAdapter(this);
        mSSDataBaseAdapter=mSSDataBaseAdapter.open();



        mGoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movieTemplate = new Template(genre,mSSDataBaseAdapter);
                String retrieve = movieName.getText().toString();
                movieTemplate.setName(retrieve);
                TrailerApp.getInstance().mainTemplate = movieTemplate;
                Log.d(TAG, "CHECK: " + movieTemplate.clipArray.size());
                Intent intentChooseActors = new Intent(getApplicationContext(),ChooseGenreActivity.class);
                startActivity(intentChooseActors);
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
