package com.paypal.hecactivation;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

/**
 * Created by amadhani on 6/15/15.
 */
public class HTTPNetworkManager {

    public static JSONObject postRequest(final JSONObject data, final String url) {
        final JSONObject[] response = new JSONObject[1];

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                HttpClient client = new DefaultHttpClient();
                HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); //Timeout Limit
                HttpResponse httpresponse;

                try {
                    HttpPost post = new HttpPost(url);
                    StringEntity se = new StringEntity(data.toString());
                    se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                    post.setEntity(se);
                    httpresponse = client.execute(post);

            /*Checking response */
                    if (httpresponse != null) {
                        //Get the data in the entity
                        response[0] = new JSONObject(EntityUtils.toString(httpresponse.getEntity()));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    response[0] = null;
                }
            }
        });

        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return response[0];
    }
}
