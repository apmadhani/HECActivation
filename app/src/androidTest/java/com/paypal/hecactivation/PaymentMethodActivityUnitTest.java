package com.paypal.hecactivation;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.MediumTest;
import android.widget.ImageButton;

/**
 * Created by amadhani on 6/18/15.
 */
public class PaymentMethodActivityUnitTest extends ActivityUnitTestCase<PaymentMethodActivity> {

    private Intent mLaunchIntent;
    private String testUser;
    private String subscription;

    public PaymentMethodActivityUnitTest() {
        super(PaymentMethodActivity.class);
        testUser = "foo@bar.com";
        subscription="premium";
    }


    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mLaunchIntent = new Intent(getInstrumentation()
                .getTargetContext(), PaymentMethodActivity.class);
        mLaunchIntent.putExtra(LoginActivity.USERNAME, testUser);
        mLaunchIntent.putExtra(SubscriptionTypeActivity.SUBSCRIPTION, subscription);
    }

    @MediumTest
    public void testCreditCart() {
        startActivity(mLaunchIntent, null, null);

        final ImageButton launchNextButton =
                (ImageButton) getActivity()
                        .findViewById(R.id.credit_card);
        launchNextButton.performClick();

        final Intent launchIntent = getStartedActivityIntent();
        assertNull("Intent was not null", launchIntent);
    }

    @MediumTest
    public void testPayPal() {
        startActivity(mLaunchIntent, null, null);

        final ImageButton launchNextButton =
                (ImageButton) getActivity()
                        .findViewById(R.id.paypal_button);
        launchNextButton.performClick();

        final Intent launchIntent = getStartedActivityIntent();
        assertNotNull("Intent was null", launchIntent);

        final String username =
                launchIntent.getStringExtra(LoginActivity.USERNAME);
        final String intentSubscription = launchIntent.getStringExtra(SubscriptionTypeActivity.SUBSCRIPTION);
        final String ECToken = launchIntent.getStringExtra(PaymentMethodActivity.CART_TOKEN);
        assertEquals("Intent username not equal to given username", testUser, username);
        assertEquals("Intent doesn't contain premium subsciption type", subscription, intentSubscription);
        assertTrue("Intent doesn't have EC token", ECToken.indexOf("EC-")==0);
    }
}