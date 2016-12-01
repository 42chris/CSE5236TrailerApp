package edu.ohiostate.movietrailer;

import android.app.Instrumentation;
import android.support.annotation.UiThread;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.view.MotionEvent;
import android.widget.Button;

/**
 * Created by Chris on 11/30/2016.
 */

public class MainMenuUITest extends ActivityInstrumentationTestCase2 <MainMenuActivity> {
    private MainMenuActivity mainMenuActivity;
    private Button button;
    private Instrumentation mainMenuActivityInstrumentation = null;
    private Instrumentation.ActivityMonitor activityMonitor;
    private SettingsActivity settingsActivity;

    public MainMenuUITest(){
        super("edu.ohiostate.movietrailer.LoginActivity", MainMenuActivity.class);
    }

    protected void setUp() throws Exception {
        super.setUp();
        setActivityInitialTouchMode(false);
        mainMenuActivityInstrumentation = getInstrumentation();
        mainMenuActivity = getActivity();
        button = (Button) mainMenuActivity.findViewById(R.id.settings_button);
        activityMonitor = mainMenuActivityInstrumentation.addMonitor(SettingsActivity.class.getName(), null, false);
    }

    public void testMenuUI() {
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                System.out.println("Thread ID in testMenuUI.run: " + Thread.currentThread().getId());
                button.requestFocus();
                button.performClick();
            }
        });
        settingsActivity = (SettingsActivity)mainMenuActivityInstrumentation.waitForMonitorWithTimeout(activityMonitor, 5000);
        assertNotNull(settingsActivity);

    }

    protected void tearDown() throws Exception{
        mainMenuActivity.finish();
    }
}
