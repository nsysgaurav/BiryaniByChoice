package com.example.test.Sqldirectory;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.android.volley.toolbox.StringRequest;
import com.example.test.Model.CartModal;

import java.util.ArrayList;
import java.util.List;

public class CartLitedb extends SQLiteOpenHelper {


    ArrayList<CartModal> cartModalArrayList = new ArrayList<>();
    ArrayList<CartModal> cartModalArrayListCard = new ArrayList<>();


    public static final String database ="cart_db";

    int x;

    public CartLitedb(@Nullable Context context) {
        super(context,database, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CartModal.create_table);


    }




    public void insertdata(String name, int price,int quantity,String cat, String url){
        SQLiteDatabase database=getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CartModal.column_name,name);
        values.put(CartModal.column_price,price);
        values.put(CartModal.column_quantity,quantity);
        values.put(CartModal.column_cat,cat);
        values.put(CartModal.column_img_url,url);

database.insert(CartModal.tablename,null,values);
database.close();
    }



    public List<CartModal> getdata(){
       try{
        SQLiteDatabase database = getReadableDatabase();
                Cursor cursor= database.rawQuery("select * from "+CartModal.tablename,null);

        if (cursor !=null){

            cursor.moveToFirst();

            do
                {
                    CartModal cartModal = new CartModal();
                cartModal.setCart_item_name(cursor.getString(cursor.getColumnIndex(CartModal.column_name)));
                cartModal.setQuantity(Integer.parseInt(cursor.getString(cursor.getColumnIndex(CartModal.column_quantity))));
                cartModal.setCart_item_price(Integer.parseInt(cursor.getString(cursor.getColumnIndex(CartModal.column_price))));
                cartModal.setCart_item_img_url(cursor.getString(cursor.getColumnIndex(CartModal.column_img_url)));
                cartModalArrayList.add(cartModal);
            }while (cursor.moveToNext());

        }

        System.out.println("ye ahi list me ......."+cartModalArrayList+x);
       database.close();
       }
       catch (Exception e)
       {
           e.printStackTrace();
       }

        return cartModalArrayList;
    }

    public void deletebyname(String name){
        SQLiteDatabase database = getReadableDatabase();
        database.delete(CartModal.tablename,CartModal.column_name+"=?",new String[]{name});
    }
    public void deleteall() {
        SQLiteDatabase sqLiteDatabase=getReadableDatabase();
        Cursor cursor=sqLiteDatabase.rawQuery("delete FROM "+CartModal.tablename,null);
        cursor.moveToNext();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
