package edu.ohiostate.movietrailer;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Chris on 11/30/2016.
 */

public class AccountCreationFailureTest extends ActivityInstrumentationTestCase2 <CreateAccountActivity> {
    private CreateAccountActivity createAccountActivity;
    private EditText name;
    private EditText passwordOne;
    private EditText passwordTwo;
    private Button button;
    private Instrumentation createAccountActivityInstrumentation = null;
    private Instrumentation.ActivityMonitor activityMonitor;
    private MainMenuActivity mainMenuActivity;

    public AccountCreationFailureTest(){
        super("edu.ohiostate.movietrailer.CreateAccountActivity", CreateAccountActivity.class);
    }

    protected void setUp() throws Exception {
        super.setUp();
        setActivityInitialTouchMode(false);
        createAccountActivityInstrumentation = getInstrumentation();
        createAccountActivity = getActivity();
        button = (Button) createAccountActivity.findViewById(R.id.create_acc_button);
        name=(EditText)createAccountActivity.findViewById(R.id.new_username_text);
        passwordOne=(EditText)createAccountActivity.findViewById(R.id.new_password_text);
        passwordTwo=(EditText)createAccountActivity.findViewById(R.id.confirm_password_text);
        activityMonitor = createAccountActivityInstrumentation.addMonitor(MainMenuActivity.class.getName(), null, false);
    }

    public void testCreationFailureUI() {
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                System.out.println("Thread ID in testMenuUI.run: " + Thread.currentThread().getId());
                name.setText("name");
                passwordOne.setText("password");
                passwordTwo.setText("wrong");
                button.requestFocus();
                button.performClick();
            }
        });
        mainMenuActivity = (MainMenuActivity)createAccountActivityInstrumentation.waitForMonitorWithTimeout(activityMonitor, 5000);
        assertNull(mainMenuActivity);

    }

    protected void tearDown() throws Exception{
        createAccountActivity.finish();
    }
}
