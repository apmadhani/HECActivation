package com.paypal.hecactivation;

import android.content.Context;
import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.test.suitebuilder.annotation.LargeTest;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by amadhani on 6/18/15.
 */
public class SendNotificationActivityUnitTest extends ActivityUnitTestCase<SendNotificationActivity> {

    private Intent mLaunchIntent;
    private String testUser;
    private String cart;

    public SendNotificationActivityUnitTest() {
        super(SendNotificationActivity.class);
    }


    @Override
    protected void setUp() throws Exception {
        super.setUp();
        Context c = getInstrumentation()
                .getTargetContext();
        mLaunchIntent = new Intent(getInstrumentation()
                .getTargetContext(), SendNotificationActivity.class);
        testUser = "foo@bar.com";
        mLaunchIntent.putExtra(LoginActivity.USERNAME, testUser);
        cart = PaymentMethodActivity.createCart(1,
                c.getResources().getString(R.string.client_key),
                new String[]{"premium"},
                c.getResources().getString(R.string.submit_cart_url));
                mLaunchIntent.putExtra(PaymentMethodActivity.CART_TOKEN, cart);
    }

    @LargeTest
    public void testSubscriptionTypeActivityLaunched() {
        startActivity(mLaunchIntent, null, null);

        ((TextView) getActivity().findViewById(R.id.contact)).setText(testUser);
        final Button launchNextButton =
                (Button) getActivity()
                        .findViewById(R.id.notification_button);
        launchNextButton.performClick();

        final Intent launchIntent = getStartedActivityIntent();
        assertNotNull("Intent was null", launchIntent);

        final String username =
                launchIntent.getStringExtra(LoginActivity.USERNAME);
        final String token =
                launchIntent.getStringExtra(PaymentMethodActivity.CART_TOKEN);
        assertEquals("Intent username not equal to given username", testUser, username);
        assertEquals("Intent username not equal to given username", cart, token);
    }
}