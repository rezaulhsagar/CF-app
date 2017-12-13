package com.example.user.codeforces;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by User on 01-Dec-17.
 */

public class HttpHandler {
    private static final String TAG = HttpHandler.class.getSimpleName();

    public HttpHandler(){
    }

    public String makeServiceCall(String reqUrl){
        String response = null;
        Log.d("req",reqUrl);
        try{
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            InputStream in = new BufferedInputStream(conn.getInputStream());
            response = convertStreamToString(in);
        } catch (MalformedURLException e) {
            Log.d("here","malform");
            Log.e(TAG, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e){
            Log.d("here","pro");
            Log.e(TAG, "ProtocolException: " + e.getMessage());
        } catch(IOException e){
            Log.d("here","io");
            Log.e(TAG, "IOException: " + e.getMessage());
        } catch (Exception e){
            Log.d("here","ex");
            Log.e(TAG, "Exception: " + e.getMessage());
        }
        Log.d("res",response);
        return response;
    }

    public Bitmap downloadImage(String reqUrl){
        Bitmap image = null;
        try{
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            InputStream in = conn.getInputStream();
            image = BitmapFactory.decodeStream(in);
            in.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    private String convertStreamToString(InputStream is){
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try{
            while((line = reader.readLine()) != null){
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try{
                is.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
