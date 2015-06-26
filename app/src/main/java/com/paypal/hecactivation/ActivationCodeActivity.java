package com.paypal.hecactivation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;


public class ActivationCodeActivity extends Activity {
    private String email;
    private String cartID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activation_code);
        Intent intent = getIntent();
        email = intent.getStringExtra(LoginActivity.USERNAME);
        cartID=intent.getStringExtra(PaymentMethodActivity.CART_TOKEN);
        String code = intent.getStringExtra(PaymentMethodActivity.ACTIVATION_CODE);

        ((TextView) findViewById(R.id.activation_code)).setText(code);

        new Thread(new Runnable() {
            public void run() {
                getStatus(cartID);
            }
        }).start();
    }

    private String getStatus(String cartID) {
        JSONObject json = new JSONObject();
        JSONObject cartToken = new JSONObject();

        try {
            cartToken.put("ec_token", cartID);
            json.put("clientID", 1);
            json.put("clientKey", getResources().getString(R.string.client_key));
            json.put("data", cartToken);

            JSONObject jsonResponse = HTTPNetworkManager.postRequest(json, getResources().getString(R.string.payment_status_url));

            if (jsonResponse != null && "success".equals((String) jsonResponse.get("status"))) {
                String status = (String) ((JSONObject) jsonResponse.get("data")).get("status");
                if(status.equals("completed")) {
                    goToNetflix();
                } else {
                    cancel();
                }
                return status;
            } else {
                return "error";
            }
        } catch(JSONException e) {
            e.printStackTrace();
            return "error";
        }
    }


    public void cancel(View view) {
        cancel();
    }

    public void cancel() {
        Intent intent=new Intent(this, CancelledActivity.class);
        intent.putExtra(LoginActivity.USERNAME, email);
        startActivity(intent);
    }

    private void goToNetflix() {
        Intent intent=new Intent(this, TVActivity.class);
        startActivity(intent);
    }
 }
