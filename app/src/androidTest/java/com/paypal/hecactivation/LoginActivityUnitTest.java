package com.paypal.hecactivation;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.MediumTest;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by amadhani on 6/18/15.
 */
public class LoginActivityUnitTest extends ActivityUnitTestCase<LoginActivity> {

    private Intent mLaunchIntent;

    public LoginActivityUnitTest() {
        super(LoginActivity.class);
    }


    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mLaunchIntent = new Intent(getInstrumentation()
        .getTargetContext(), LoginActivity.class);
    }

    @MediumTest
    public void testSubscriptionTypeActivityLaunched() {
        startActivity(mLaunchIntent, null, null);

        String testUser = "foo@bar.com";
        ((TextView) getActivity().findViewById(R.id.username)).setText(testUser);

        final Button launchNextButton =
                (Button) getActivity()
                        .findViewById(R.id.loginButton);
        launchNextButton.performClick();

        final Intent launchIntent = getStartedActivityIntent();
        assertNotNull("Intent was null", launchIntent);

        final String username =
                launchIntent.getStringExtra(LoginActivity.USERNAME);
        assertEquals("Intent username not equal to given username", testUser, username);
    }

}