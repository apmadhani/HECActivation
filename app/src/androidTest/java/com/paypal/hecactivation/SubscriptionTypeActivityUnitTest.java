package com.paypal.hecactivation;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.MediumTest;
import android.widget.Button;

/**
 * Created by amadhani on 6/18/15.
 */
public class SubscriptionTypeActivityUnitTest extends ActivityUnitTestCase<SubscriptionTypeActivity> {

    private Intent mLaunchIntent;
    private String testUser;

    public SubscriptionTypeActivityUnitTest() {
        super(SubscriptionTypeActivity.class);
        testUser = "foo@bar.com";
    }


    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mLaunchIntent = new Intent(getInstrumentation()
                .getTargetContext(), SubscriptionTypeActivity.class);
        mLaunchIntent.putExtra(LoginActivity.USERNAME, testUser);
    }


    public void testNextActivity(int view, String givenSubscription) {
        startActivity(mLaunchIntent, null, null);

        final Button launchNextButton =
                (Button) getActivity()
                        .findViewById(view);
        launchNextButton.performClick();

        final Intent launchIntent = getStartedActivityIntent();
        assertNotNull("Intent was null", launchIntent);

        final String username =
                launchIntent.getStringExtra(LoginActivity.USERNAME);
        final String subscription = launchIntent.getStringExtra(SubscriptionTypeActivity.SUBSCRIPTION);
        assertEquals("Intent username not equal to given username", testUser, username);
        assertEquals("Intent doesn't contain premium subsciption type", givenSubscription, subscription);
    }

    @MediumTest
    public void testStandard() {
        testNextActivity(R.id.standard, "standard");
    }

    @MediumTest
    public void testPremium() {
        testNextActivity(R.id.premium, "premium");
    }
}