package com.nizar.abdelhedi.adhd;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Guesmi on 24/11/2015.
 */
public class ServiceHandler {

    JSONObject json;
    public ServiceHandler(JSONObject json){
                this.json=json;
    }



    public String makeCall(String url,int timeout){
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        try{
            URL url2 = new URL(url);
            urlConnection = (HttpURLConnection) url2.openConnection();
            urlConnection.setDoOutput(true);
            // is output buffer writter
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");
    //set headers and method
            Writer writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8"));
            writer.write(json.toString());
            writer.close();
            InputStream inputStream = urlConnection.getInputStream();
//input stream
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String inputLine;
            while ((inputLine = reader.readLine()) != null)
                buffer.append(inputLine + "\n");
            if (buffer.length() == 0) {
                // Stream was empty. No point in parsing.
                return null;
            }
            String response = buffer.toString();
//response data
            Log.i("zzzzzzzzz",response);

            return response;

    } catch (MalformedURLException ex) {
        Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            Log.d("eeeeeeee",ex.getMessage());
    } catch (IOException ex) {
        Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            Log.d("eeeeeeee",ex.getMessage());

        }

        finally {
        if (urlConnection != null) {
            try {
                urlConnection.disconnect();
            } catch (Exception ex) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    return null;
    }

}
