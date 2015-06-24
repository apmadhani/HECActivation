package com.paypal.hecactivation;

import android.content.Context;
import android.test.ActivityInstrumentationTestCase2;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by amadhani on 6/18/15.
 */
public class LoginActivityTest extends ActivityInstrumentationTestCase2<LoginActivity> {

    private LoginActivity activity;
    private TextView username;
    private TextView password;
    private Button login;
    private Context context;
    private View wrapper;
    private ImageView logo;

    public LoginActivityTest() {
        super(LoginActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        activity = getActivity();
        username = (TextView) activity.findViewById(R.id.username);
        password = (TextView) activity.findViewById(R.id.password);
        login = (Button) activity.findViewById(R.id.loginButton);
        logo = (ImageView) activity.findViewById(R.id.login_netflix);
        wrapper = activity.findViewById(R.id.activity_login_wrapper);
        context = activity.getApplicationContext();
    }

    public void testPositions() {

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);

        int displayHeight = metrics.heightPixels;
        int displayWidth = metrics.widthPixels;

        int[] usernamePos=new int[2];
        int[] pwdPos=new int[2];
        int[] loginPos=new int[2];
        int[] logoPos = new int[2];
        username.getLocationOnScreen(usernamePos);
        password.getLocationOnScreen(pwdPos);
        login.getLocationOnScreen(loginPos);
        logo.getLocationOnScreen(logoPos);

        //Make sure views are in correct position in relation to each other
        assertTrue("Username box not above password box", logoPos[1] < usernamePos[1]);
        assertTrue("Username box not above password box", usernamePos[1] < pwdPos[1]);
        assertTrue("Password box not above login button", pwdPos[1] < loginPos[1]);
        assertEquals("Username and pwd boxes don't have same x pos", pwdPos[0], usernamePos[0]);
    }


//    public void testOpenNextActivity() {
//        login.performClick();
//
//        final Intent launchIntent = getStartedActivityIntent();
//        assertNotNull("Intent was null", launchIntent);
//        assertTrue(isFinishCalled());
//
//        final String payload =
//                launchIntent.getStringExtra(LoginActivity.USERNAME);
//        assertEquals("String is empty", "", payload);
//    }
}