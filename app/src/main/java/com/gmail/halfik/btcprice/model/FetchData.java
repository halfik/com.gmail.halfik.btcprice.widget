package com.gmail.halfik.btcprice.model;


import android.net.Uri;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


public class FetchData
{
    private static final String TAG = "FetchData";

    private static final Uri ENDPOINT = Uri.parse("https://www.bitmarket.pl/json/BTCPLN/ticker.json")
            .buildUpon()
            .build();

    public Map<String, String> sync(){
        try{
            String jsonString = getUrlString(buildUrl());
            JSONObject jsonBody = new JSONObject(jsonString);
            return parseData(jsonBody);
        }catch (JSONException e){
            Log.e(TAG, "failed to parse json", e);
        }
        catch (IOException ioe){
            Log.e(TAG, "failed to fetch items", ioe);
        }

        return new HashMap<>();
    }

    private Map<String, String> parseData(JSONObject jsonBody) throws JSONException{
        String last = jsonBody.get("last").toString();
        String low = jsonBody.get("low").toString();
        String high = jsonBody.get("high").toString();

        Map<String, String> result = new HashMap<>();
        result.put("last", last);
        result.put("low", low);
        result.put("high", high);

        return result;
    }

    public String buildUrl(){
        Uri.Builder uriBuilder = ENDPOINT.buildUpon();
        return uriBuilder.build().toString();
    }

    public String getUrlString(String urlSpec) throws MalformedURLException, IOException {
        return new String(getUrlBytes(urlSpec));
    }

    public byte[] getUrlBytes(String urlSpec) throws MalformedURLException, IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try{
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK){
                throw new IOException(connection.getResponseMessage() + ": with " + urlSpec);
            }

            int bytesRead = 0;
            byte[] buffer = new byte[256];
            while((bytesRead = in.read(buffer)) > 0){
                out.write(buffer, 0, bytesRead);
            }

            connection.disconnect();

            Log.i(TAG, "Fetch data size: " + String.valueOf(out.size()) + " bytes");
            return out.toByteArray();
        }finally {
            connection.disconnect();
        }
    }
}
