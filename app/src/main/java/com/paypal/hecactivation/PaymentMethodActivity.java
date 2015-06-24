package com.paypal.hecactivation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PaymentMethodActivity extends Activity {
//    Bitmap paypal;
    private String username;
    private String subscription;
    public static final String CART_TOKEN = "com.paypal.hecactivation.cartToken";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method);
        Intent intent = getIntent();
        username = intent.getStringExtra(LoginActivity.USERNAME);
        subscription = intent.getStringExtra(SubscriptionTypeActivity.SUBSCRIPTION);
    }

    public void sendNotificationPage(View view) {
        Intent intent = new Intent(this, ActivationCodeActivity.class);
        intent.putExtra(LoginActivity.USERNAME, username);
        intent.putExtra(SubscriptionTypeActivity.SUBSCRIPTION, subscription);
        String cartToken = createCart(1,
                getResources().getString(R.string.client_key),
                new String[]{subscription},
                getResources().getString(R.string.submit_cart_url));
        if(cartToken.equals("error")){
            Context context = getApplicationContext();
            CharSequence text = "ERROR";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        } else{
            intent.putExtra(CART_TOKEN, cartToken);
            startActivity(intent);
        }
    }

    public static String createCart(int clientID, String clientKey, String[] cartString, String url) {
        JSONObject json = new JSONObject();
        JSONObject cartObj = new JSONObject();

        try {
            JSONArray cart = new JSONArray(cartString);
            cartObj.put("cart", cart);
            json.put("clientID", clientID);
            json.put("clientKey", clientKey);
            json.put("data", cartObj);

            JSONObject jsonResponse = HTTPNetworkManager.postRequest(json, url);

            if (jsonResponse != null && "success".equals((String) jsonResponse.get("status"))) {
                return (String) ((JSONObject) jsonResponse.get("data")).get("token");
            } else {
                return "error";
            }
        }catch(JSONException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public void doNothing(View view) {
    }
}
