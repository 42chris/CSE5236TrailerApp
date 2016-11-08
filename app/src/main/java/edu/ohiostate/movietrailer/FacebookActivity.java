package edu.ohiostate.movietrailer;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;

import com.facebook.share.model.ShareVideo;
import com.facebook.share.model.ShareVideoContent;

/**
 * Created by Chris on 11/7/2016.
 */
public class FacebookActivity extends Activity {

    Uri videoFileUri;
    ShareVideo share;
    ShareVideoContent content;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        share = new ShareVideo.Builder().setLocalUrl(videoFileUri).build();
        content = new ShareVideoContent.Builder().setVideo(share).build();

    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }

    @Override
    protected void onStart(){
        super.onStart();
    }

    @Override
    protected void onPause(){
        super.onPause();
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public void onStop(){
        super.onStop();
    }
}
