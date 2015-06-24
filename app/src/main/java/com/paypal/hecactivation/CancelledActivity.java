package com.paypal.hecactivation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class CancelledActivity extends Activity {
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        findViewById(R.id.progressLoadingPage).setVisibility(View.INVISIBLE);
        email = getIntent().getStringExtra(LoginActivity.USERNAME);
        ((TextView) findViewById(R.id.loading_title)).setText(getString(R.string.payment_cancelled));
        ((TextView) findViewById(R.id.prompt_continue)).setText(getString(R.string.cancelled_extra_info));
        ((Button) findViewById(R.id.cancel_button)).setText(R.string.goto_subscription);
    }

    public void cancel(View v) {
        Intent intent=new Intent(this, SubscriptionTypeActivity.class);
        intent.putExtra(LoginActivity.USERNAME, email);
        startActivity(intent);
    }
}
