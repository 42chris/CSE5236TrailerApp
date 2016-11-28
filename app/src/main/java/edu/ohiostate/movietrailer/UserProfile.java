package edu.ohiostate.movietrailer;

import java.util.ArrayList;

/**
 * Created by petri on 10/11/2016.
 */
public class UserProfile {

    private String mUsername;
    private String mPassword;
    private ArrayList<String> createdVideos;
    private long mId;

    public UserProfile(String user, String p_word){
        mId= -1;
        this.mUsername = user;
        this.mPassword = p_word;
    }

    public void setId(long id) {
        mId = id;
    }

    public long getId() {
        return mId;
    }

    public String getuserName() {
        return mUsername;
    }

    public void setuserName(String muserName) {
        this.mUsername = muserName;
    }

    public String getpassword() {
        return mPassword;
    }

    public void setpassword(String mpassword) {
        this.mPassword = mpassword;
    }
}
