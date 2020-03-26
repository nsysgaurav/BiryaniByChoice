package com.example.test.activity;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class BacgroundAboutUs extends AsyncTask<Void,Void, Void> {
    String read;


    @Override
    protected Void doInBackground(Void... voids) {




        try {
            URL url =new URL("http://localhost:8080/aboutus") ;
            HttpURLConnection connection= (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            InputStream stream=connection.getInputStream();
            BufferedReader reader=new BufferedReader(new InputStreamReader(stream));



            read=reader.readLine();


        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {

    }
}
