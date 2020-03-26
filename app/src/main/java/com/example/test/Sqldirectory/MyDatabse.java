package com.example.test.Sqldirectory;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyDatabse extends SQLiteOpenHelper {
    private  static  final String Database_name="MyDatabase";
    private static final int Database_version=1;

    private static final String Cart_table_name="my_cart";
    private static  final String Cart_food_id="cart_food_id";
    private static  final String Food_id="food_id";
    private static  final String Food_name="food_name";
    private static  final String Food_price="food_price";
    private static  final String Food_quantity="food_quantity";
    private static final String Food_amount="food_amount";
    private static  final String Food_added_time="food_added_time";
    private  static  final String Food_serving_type="food_serving_type";


    private static final String Create_table_query="CREATE TABLE "+Cart_table_name + "("+ Cart_food_id + " INTEGER PRIMARY KEY AUTOINCREMENT,"+ Food_id +" INTEGER NOT NULL UNIQUE,"
            + Food_name+" TEXT, "+Food_price+" INTEGER, "+Food_quantity+" INTEGER, "+Food_amount+ " INTEGER, " + Food_added_time+ " TEXT , " + Food_serving_type + " TEXT " +" ); ";

    public MyDatabse(Context context) {
        super(context,Database_name,null,Database_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Create_table_query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
     db.execSQL( "DROP TABLE IF EXISTS " + Cart_table_name);
     onCreate(db);
    }

    public void insert_food_in_cart(int food_id, String food_name, int food_price, int food_quantity, String food_added_time, String food_serving_type)
    {
        SQLiteDatabase database=null;
        try
        {
            int food_amount=food_quantity*food_price;
            String query="SELECT * FROM "+Cart_table_name ;

           database=this.getWritableDatabase();
            ContentValues values=new ContentValues();
            values.put("food_id",food_id);
            values.put("food_name",food_name);
            values.put("food_price",food_price);
            values.put("food_quantity",food_quantity);
            values.put("food_added_time",food_added_time);
            values.put("food_serving_type",food_serving_type);
            values.put("food_amount",food_amount);

            database.insert(Cart_table_name,null,values);
            Cursor cursor;

            cursor= database.rawQuery(query,null);
            if(cursor!=null)
            {
                cursor.moveToFirst();
            }
            do {
                {

                }
            }while (cursor.moveToNext());



           /* try
            {
                String query="SELECT * FROM "+Cart_table_name ;
                cursor= database.rawQuery(query,null);
                if(cursor.getCount()==0)
                {
                    database.insert(Cart_table_name,null,values);
                }
                else if(cursor.getCount()>0)
                {
                    cursor=database.rawQuery("select food_id from"+Cart_table_name+" where "+Food_id+"="+food_id,null);
                    if(cursor.getCount()==0)
                    {
                        database.insert(Cart_table_name,null,values);
                    }
                    else
                    {
                       *//* String quary="select"+Food_serving_type+"from"+Cart_table_name+"where"+Food_serving_type+"="+food_serving_type;
                        cursor=database.rawQuery(quary,null);*//*

                        database.update(Cart_table_name, values,Food_serving_type+"=",new String[]{food_serving_type});

                    }
                }
            }catch (Exception e)
            {

            }*/

        }
        catch (Exception e)
        {

        }
        finally {
            if(database!=null)
            {
                database.close();
            }
        }


    }

    public ArrayList<String> get_table_data() {
        ArrayList<String> alldata = new ArrayList<>();
        int cart_id = 0;
        int food_id = 0;
        String food_name = "";
        int food_price = 0;
        int food_quantity = 0;
        int food_amount = 0;
        String time = "";
        String type = "";
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "SELECT * FROM " + Cart_table_name;

        Cursor cursor = database.rawQuery(query, new String[]{});
        if (cursor != null) {
            cursor.moveToFirst();

            do {
                String data;
                cart_id = cursor.getInt(0);
                food_id = cursor.getInt(1);
                food_name = cursor.getString(2);
                food_price = cursor.getInt(3);
                food_quantity = cursor.getInt(4);
                food_amount = cursor.getInt(5);
                time = cursor.getString(6);
                type = cursor.getString(7);
                data = cart_id + " " + food_id + " " + food_name + " " + food_price + " " + food_quantity + " " + food_amount + " " + time + " " + type;
                alldata.add(data);

            } while (cursor.moveToNext());


        }
        return alldata;
    }



}
