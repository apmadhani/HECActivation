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
        setContentView(R.layout.activity_cancelled);
    }

    public void cancel(View v) {
        Intent intent=new Intent(this, SubscriptionTypeActivity.class);
        intent.putExtra(LoginActivity.USERNAME, email);
        startActivity(intent);
    }
}
