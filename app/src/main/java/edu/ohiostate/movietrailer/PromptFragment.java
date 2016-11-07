package edu.ohiostate.movietrailer;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.net.URI;

import static android.app.Activity.RESULT_OK;


public class PromptFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TYPE = "type";
    private static final String QUESTION = "question";
    private static final String LENGTH = "float";
    private static final String INDEX = "index";

    static final int REQUEST_VIDEO_CAPTURE = 1;
    // TODO: Rename and change types of parameters

    private Clip videoClip;
    private Prompt selectedPrompt;
    private TextView question;
    private Button shootVideoButton;
    private String questionText;
    private int index;
    private boolean isComplete;


    public PromptFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static PromptFragment newInstance(Prompt poppedPrompt, int index) {
        PromptFragment fragment = new PromptFragment();
        Bundle args = new Bundle();
        args.putString(TYPE, poppedPrompt.getType());
        args.putString(QUESTION, poppedPrompt.getQuestion());
        args.putFloat(LENGTH,poppedPrompt.getLength());
        args.putInt(INDEX,index);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("PromptFragment","onCreate(Bundle) called");
        isComplete = false;
        questionText = this.getArguments().getString(QUESTION);
        index = this.getArguments().getInt(INDEX);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_video, container, false);
        question = (TextView) v.findViewById(R.id.questionView);
        shootVideoButton = (Button) v.findViewById(R.id.go_button);
        question.setText(questionText);
        shootVideoButton.setEnabled(true);
        shootVideoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent video_intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                if (video_intent.resolveActivity(getContext().getPackageManager()) != null) {
                    startActivityForResult(video_intent, 1);
                }
                //startActivity(video_intent);
            }
        });
        //Intent video_intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        //startActivity(video_intent);
        return v;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            Uri videoUri = intent.getData();
            Clip newClip = new Clip(videoUri,getContext());
            TrailerApp.getInstance().mainTemplate.setClip(newClip, index);
            isComplete = true;
        }
    }

    public boolean isCompleted(){
        return isComplete;
    }




}
