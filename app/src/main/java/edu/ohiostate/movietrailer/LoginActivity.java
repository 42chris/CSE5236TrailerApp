package edu.ohiostate.movietrailer;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {

    private Button mloginButton;
    private TextView mQuestionTextView;
    private TextView mnewAccount;
    private EditText userNameEditableField;
    private EditText passwordEditableField;
    public static final String PREFS_NAME = "MyPrefsFile";

    LoginDataBaseAdapter mLoginDataBaseAdapter;

    private static final String TAG = "LoginActivity";

//    private Prompt[] mPromptBank = new Prompt[]{
//            new Prompt(R.string.new_account,PromptType.TEXT),
//            new Prompt(R.string.genre_string,PromptType.TEXT)};
    private  int mCurrentIndex = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate(Bundle) called");
        setContentView(R.layout.activity_login);

//        SharedPreferences settings  = getSharedPreferences(PREFS_NAME,0);
//        boolean silent = settings.getBoolean()
//        mQuestionTextView = (TextView)findViewById(R.id.prompt_text_view);
//        int question = mPromptBank[mCurrentIndex].getQuestion();
//        mQuestionTextView.setText(question);

        mLoginDataBaseAdapter=new LoginDataBaseAdapter(this);
        mLoginDataBaseAdapter=mLoginDataBaseAdapter.open();

        userNameEditableField = (EditText) findViewById(R.id.username_text);
        passwordEditableField = (EditText) findViewById(R.id.password_text);
        mloginButton = (Button)findViewById(R.id.login_button);
        mloginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = userNameEditableField.getText().toString();
                String password = passwordEditableField.getText().toString();

                //fetch password for associated username
                String storedPassword = mLoginDataBaseAdapter.getSingleEntry(userName);

                if (password.equals(storedPassword)){
                    Toast.makeText(LoginActivity.this,"Successful Login",Toast.LENGTH_LONG).show();
                    Intent intentMainMenu = new Intent(getApplicationContext(),MainMenuActivity.class);
                    startActivity(intentMainMenu);
/*
                    Intent intentCreateAccount = new Intent(getApplicationContext(),ChoiceActivity.class);
                    startActivity(intentCreateAccount);
>>>>>>> Stashed changes*/
                }
                else{
                    Toast.makeText(LoginActivity.this,"Username and Password do not match",Toast.LENGTH_LONG).show();
                }
            }
        });

        mnewAccount = (TextView)findViewById(R.id.new_account_text_view);
        mnewAccount.setOnClickListener((new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intentCreateAccount = new Intent(getApplicationContext(),CreateAccountActivity.class);
                startActivity(intentCreateAccount);
            }
        }));


    }

//    public boolean checkLogin(){
//
//    }

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
        mLoginDataBaseAdapter.close();
        Log.d(TAG,"onDestroy() called");
    }
}
