package com.culqi;

import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;

import com.google.gson.GsonBuilder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class Token {
    public static final String TAG = Token.class.getSimpleName();

    public class RetrieveFeedTask extends AsyncTask<String, Void, HttpResponse> {
        HttpResponse response = null;
        protected HttpResponse doInBackground(String... urls) {
            try {
                HttpPost httpPost = new HttpPost(urls[0]);
                httpPost.setEntity(new StringEntity(urls[1]));
                httpPost.setHeader("Accept", "application/json");
                httpPost.setHeader("Content-type", "application/json");
                httpPost.setHeader("Authorization", "Bearer " + urls[2]);
                response = new DefaultHttpClient().execute(httpPost);
                if (response != null) Log.e(TAG, response.toString());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                Log.e(TAG, e.getMessage());
            } catch (ClientProtocolException e) {
                e.printStackTrace();
                Log.e(TAG, e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
                Log.e(TAG, e.getMessage());
            }
            return response;
        }

        public HttpResponse getResponse() {
            return response;
        }
    }

    class TheTask extends AsyncTask<String, String, HttpResponse> {
        @Override
        protected HttpResponse doInBackground(String... urls) {
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(urls[0]);
                httpPost.setEntity(new StringEntity(urls[1]));
                httpPost.setHeader("Accept", "application/json");
                httpPost.setHeader("Content-type", "application/json");
                httpPost.setHeader("Authorization", "Bearer " + urls[2]);

                HttpResponse response = httpclient.execute(httpPost);
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    return response;
                } else {
                    return null;
                }
            } catch (Exception e) {
                return null;
            }
        }
    }

    public HttpResponse makeRequest(String uri, String json, String apiKey) {
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            HttpPost httpPost = new HttpPost(uri);
            httpPost.setEntity(new StringEntity(json));
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setHeader("Authorization", "Bearer " + apiKey);
            return new DefaultHttpClient().execute(httpPost);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Log.e(TAG, e.toString());
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            Log.e(TAG, e.toString());
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, e.toString());
        }
        return null;
    }

    public String getToken(Card card, final String merchantCode) {
        String responseToken;
        try {
            Map<String, Object> informacion_pago = new HashMap<String, Object>();
            informacion_pago.put("correo_electronico", card.getCorreo_electronico());
            informacion_pago.put("nombre", card.getNombre());
            informacion_pago.put("apellido", card.getApellido());
            informacion_pago.put("numero", card.getNumero());
            informacion_pago.put("cvv", card.getCvv());
            informacion_pago.put("a_exp", card.getA_exp());
            informacion_pago.put("m_exp", card.getM_exp());

            String json = new GsonBuilder().create().toJson(informacion_pago, Map.class);
            final String url = "https://integ-pago.culqi.com/api/v1/tokens";
            HttpResponse response = makeRequest(url, json, merchantCode);

            if (response != null) Log.d(TAG, response.toString());

            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
            String json_response = reader.readLine();
            TokenResponse tokenResponse = null;
            try {
                tokenResponse = new ObjectMapper().readValue(json_response, TokenResponse.class);
                if (tokenResponse.getObjeto().equals("token")) {
                    responseToken = tokenResponse.getId();
                    Log.d(TAG, responseToken.toString());
                    return responseToken;
                } else {
                    responseToken = "error";
                    Log.d(TAG, responseToken.toString());
                    return responseToken;
                }
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
                return "error";
            }
        } catch (Exception e) {
            Log.e(TAG, e.toString());
            return "error";
        }
    }
}
