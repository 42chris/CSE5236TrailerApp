package edu.ohiostate.movietrailer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by andrewpetrilla on 10/29/16.
 */

public class ChoiceActivity extends AppCompatActivity {

    private ImageButton mCreateButton;
    private ImageButton mGalleryButton;
    public static final String PREFS_NAME = "MyPrefsFile";


    private static final String TAG = "ChoiceActivity";

    //    private Prompt[] mPromptBank = new Prompt[]{
//            new Prompt(R.string.new_account,PromptType.TEXT),
//            new Prompt(R.string.genre_string,PromptType.TEXT)};
    private  int mCurrentIndex = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.gallery_or_create);


        mCreateButton = (ImageButton)findViewById(R.id.create_button);
        mCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentCreateAccount = new Intent(getApplicationContext(),ChooseGenreActivity.class);
                startActivity(intentCreateAccount);
            }
        });




    }

}
