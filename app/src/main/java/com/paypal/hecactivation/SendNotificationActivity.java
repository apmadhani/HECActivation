package com.paypal.hecactivation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;


public class SendNotificationActivity extends Activity {

    private EditText inputBox;
    private String givenEmail;
    private String cartID;
    private String notification_method;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activation_code);
        Intent intent = getIntent();
        givenEmail = intent.getStringExtra(LoginActivity.USERNAME);
        cartID=intent.getStringExtra(PaymentMethodActivity.CART_TOKEN);
        inputBox = null;
        //inputBox = (EditText) findViewById(R.id.contact);
        if (notification_method.equals(getResources().getString(R.string.email))) {
            inputBox.setText(givenEmail);
            inputBox.setHint(getResources().getString(R.string.email));
            inputBox.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        }
    }

    public void sendCustomNotification(View view) {
        String resp = "error";
        if  (notification_method.equals(getResources().getString(R.string.sms))) {
            resp = sendNotification("phone", inputBox.getText().toString());
        } else if (notification_method.equals(getResources().getString(R.string.email))) {
            resp = sendNotification("email", inputBox.getText().toString());
        }
        if(resp.equals("error")) {
           showError();
        } else {
            goToLoading();
        }
    }

    private void showError() {
        Context context = getApplicationContext();
        CharSequence text = "ERROR";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    private String sendNotification(final String method, final String destination) {
        JSONObject json = new JSONObject();
        JSONObject cartToken = new JSONObject();
        JSONObject payment = new JSONObject();

        try {
            payment.put("method", method);
            payment.put("destination", destination);
            cartToken.put("ec_token", cartID);
            cartToken.put("payment", payment);
            json.put("clientID", 1);
            json.put("clientKey", getResources().getString(R.string.client_key));
            json.put("data", cartToken);

            JSONObject jsonResponse = HTTPNetworkManager.postRequest(json, getResources().getString(R.string.payment_send_url));

            if (jsonResponse != null && "success".equals((String) jsonResponse.get("status"))) {
                return (String) ((JSONObject) jsonResponse.get("data")).get("message");
            } else {
                return "error";
            }
        } catch(JSONException e) {
            e.printStackTrace();
            return "error";
        }
    }

    public void goToLoading() {
        Intent intent=new Intent(this, ActivationCodeActivity.class);
        intent.putExtra(LoginActivity.USERNAME, givenEmail);
        intent.putExtra(PaymentMethodActivity.CART_TOKEN, cartID);
        startActivity(intent);
    }
}
