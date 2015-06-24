package com.paypal.hecactivation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

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

        ((TextView) findViewById(R.id.activation_code)).setText(getCode());
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

    private String getCode() {
        JSONObject json = new JSONObject();
        JSONObject cartToken = new JSONObject();
        JSONObject payment = new JSONObject();

        try {
            payment.put("method", getString(R.string.payment_method_activation));
            cartToken.put("ec_token", cartID);
            cartToken.put("payment", payment);
            json.put("clientID", 1);
            json.put("clientKey", getString(R.string.client_key));
            json.put("data", cartToken);

            JSONObject jsonResponse = HTTPNetworkManager.postRequest(json, getString(R.string.payment_send_url));

            if (jsonResponse != null && "success".equals((String) jsonResponse.get("status"))) {
                return (String) ((JSONObject) jsonResponse.get("data")).get(getString(R.string.payment_method_activation));
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
