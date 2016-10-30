package edu.ohiostate.movietrailer;

import com.googlecode.mp4parser.authoring.tracks.H263TrackImpl;

import java.util.ArrayList;

/**
 * Created by andrewpetrilla on 10/29/16.
 */

public class Template {

    private String genre = "";
    int numPlayers = 0;
    ArrayList<Prompt> promptArray1 = new ArrayList<Prompt>();
    ArrayList<Prompt> promptArray2 = new ArrayList<Prompt>();
    ArrayList<Prompt> promptArray3 = new ArrayList<Prompt>();
    ArrayList<Clip> clipArray = new ArrayList<Clip>();
}
