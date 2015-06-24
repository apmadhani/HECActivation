package com.paypal.hecactivation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;


public class SubscriptionTypeActivity extends Activity {

    String username;
    public static final String SUBSCRIPTION = "com.paypal.hecactivation.SUBSCRIPTION_TYPE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription_type);
        username = getIntent().getStringExtra(LoginActivity.USERNAME);
    }

    
    public void paymentPremium(View view) {
        Intent intent = paymentIntent();
        intent.putExtra(SUBSCRIPTION, "premium");
        startActivity(intent);
    }

    public void paymentStandard(View view) {
        Intent intent = paymentIntent();
        intent.putExtra(SUBSCRIPTION, "standard");
        startActivity(intent);
    }

    public Intent paymentIntent() {
        Intent intent = new Intent(this, PaymentMethodActivity.class);
        intent.putExtra(LoginActivity.USERNAME, username);
        return intent;
    }
}
