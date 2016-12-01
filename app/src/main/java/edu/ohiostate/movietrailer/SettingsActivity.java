package edu.ohiostate.movietrailer;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Chris on 11/7/2016.
 */

public class SettingsActivity extends AppCompatActivity {

    Button btnDelete;
    Button btnLogout;
    Button btnGallery;
    LoginDataBaseAdapter mLoginDataBaseAdaptor;
    private static final String SHARED_PREFS_NAME = "mySharedPrefs";
    private static final String SHARED_PREFS_CURRENT_USER = "currentUser";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);



        mLoginDataBaseAdaptor = new LoginDataBaseAdapter(this.getApplicationContext());
        btnDelete = (Button)findViewById(R.id.delete_account_button);
        btnLogout = (Button)findViewById(R.id.logout_button);
        btnGallery = (Button) findViewById(R.id.gallery_button);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = getSharedPreferences(SHARED_PREFS_NAME, MODE_PRIVATE);
                String currentUser = prefs.getString(SHARED_PREFS_CURRENT_USER, null);
                if(currentUser != null){
                    mLoginDataBaseAdaptor.open();
                    mLoginDataBaseAdaptor.deleteEntry(currentUser);
                }
                SharedPreferences.Editor editor = getSharedPreferences(SHARED_PREFS_NAME, MODE_PRIVATE).edit();
                editor.putString(SHARED_PREFS_CURRENT_USER, null);
                editor.commit();
                Intent intentLogout = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intentLogout);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences(SHARED_PREFS_NAME, MODE_PRIVATE).edit();
                editor.putString(SHARED_PREFS_CURRENT_USER, null);
                editor.commit();
                Intent intentLogout = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intentLogout);
            }
        });

        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentGallery = new Intent(getApplicationContext(),GalleryActivity.class);
                startActivity(intentGallery);
                finish();
            }
        });



    }
}
