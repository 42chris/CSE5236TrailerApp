package edu.ohiostate.movietrailer;

import android.app.Application;

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

    public TrailerApp() {
    }
}
