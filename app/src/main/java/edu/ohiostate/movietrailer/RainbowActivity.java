//package edu.ohiostate.movietrailer;
//
//import android.app.Activity;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.Button;
//import android.widget.TextView;
//
//import org.w3c.dom.Text;
//
///**
// * Created by petri on 10/10/2016.
// */
//public class RainbowActivity extends Activity implements OnClickListener {
//
//    private TextView mTv;
//    private Button mButton;
//    private String[] mRainbowArr = {"red", "orange","yellow"};
//    private final String PREFS_NAME "prefs";
//    private int mIndex = 0;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState){
//        super.onCreate(savedInstanceState);
//        setContentView(...)
//
//        //Assume we can get shared preferences in mPrefs
//        SharedPreferences mPrefs = getSharedPreferences("prefs",...)
//
//
//        mTv = (TextView) findViewById(R.id.textview) ;
//        mButton = (Button) findViewById(R.id.login_button);
//        mButton.setOnClickListener(this);
//
//        //Or
////        mButton.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v){
////                mIndex = (mIndex+1) % mRainbowArr.length;
////                String color = mRainbowArr[mIndex];
////
////                if (mTv != null){
////                    mTv.setText(color);
////                }
////            }
////
////        });
//    }
//
//    @Override
//    protected void onResume(){
//        super.onResume();
//        SharedPreferences prefs = getSharedPreferences(PREFS_NAME,0);
//        String color = prefs.getString("color","");
//
//    }
//
//    @Override
//    protected void onPause(){
//        super.onPause();
//        SharedPreferences prefs = getSharedPreferences("prefs",0);
//        if (prefs != null){
//            SharedPreferences.Editor editor = mPrefs.edits();
//        }
//
//    }
//
//
//}
