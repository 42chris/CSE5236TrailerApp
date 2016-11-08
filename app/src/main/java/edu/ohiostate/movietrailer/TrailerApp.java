package edu.ohiostate.movietrailer;

import android.app.Application;
import android.net.Uri;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

/**
 * Created by andrewpetrilla on 11/6/16.
 */
public class TrailerApp extends Application {
    private static TrailerApp ourInstance = new TrailerApp();

    public static TrailerApp getInstance() {
        return ourInstance;
    }

    public Template mainTemplate;
    public String user;
    public int numActors;
    public Uri testVideoUri;

    public TrailerApp() {
    }

    @Override
    public void onCreate(){
        super.onCreate();
        //initialize the SDK
        FacebookSdk.sdkInitialize((getApplicationContext()));
        AppEventsLogger.activateApp(this);
    }
}
