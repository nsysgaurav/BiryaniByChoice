package com.example.test.activity;

import android.os.AsyncTask;

import com.example.test.Model.Book;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Showdata extends AsyncTask<Void,Void,Void> {
    ArrayList<Book> bookList = new ArrayList<>();
    Book book = new Book();
    String data;
    Tab1 tab1=new Tab1();
    @Override
    protected Void doInBackground(Void... voids) {

        try {
            URL url = new URL("http://192.168.1.60:8080/lol");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            InputStream stream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

          data  = reader.readLine();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            JSONArray array = new JSONArray(data);
            for (int i=0;i<array.length();i++){
                JSONObject object = (JSONObject) array.get(i);
                book.setBookName(object.getString("bookName"));
                book.setBookAuthor(object.getString("bookAuthor"));
                book.setBookPrice(object.getInt("bookPrice"));
               bookList.add(book);
               book.setBooklist(bookList);
            }
            System.out.println(bookList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
