package com.example.test.Sqldirectory;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.test.Model.CartModal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Deepak Nishad on 26-Jul-16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    ArrayList<CartModal> cartModalArrayList = new ArrayList<>();

    private static final String TAG = DatabaseHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "BIRYANI_DB";
    private static final int DATABASE_VERSION = 3;
    private static DatabaseHelper dbHelper = null;
    private static SQLiteDatabase db = null;

    private static final String RC_DETAILS_TABLE = "RC_DETAILS_TABLE";

    private static final String ID = "ID";
    private static final String product_id = "Product_ID";
    private static final String product_name = "PRODUCT_NAME";
    private static final String product_dec = "PRODUCT_DEC";
    private static final String product_Food_image = "PRODUCT_FOOD_IMAGE";
    private static final String product_PRICE = "PRODUCT_PRICE";
    private static final String product_Quantity = "PRODUCT_QUANTITY";
    private static final String product_total_amount = "PRODUCT_TOTAL_AMOUNT";
    private static final String product_category_id = "PRODUCT_CATEGORY_ID";



    private static final String RC_DETAILS_TABLE_QUERY = "create table " + RC_DETAILS_TABLE + " ( " + ID + " integer primary key autoincrement, "
            + product_id + " text NOT NULL unique, " + product_name + " text, " + product_dec + " text, "+ product_Food_image+ " BLOB, "+product_PRICE+ " text, "+product_Quantity+ " text, "+product_total_amount+ " text, "+product_category_id+ " text);";




    private Context context;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
        dbHelper = this;
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(RC_DETAILS_TABLE_QUERY);
//        database.execSQL(DL_DETAILS_TABLE_QUERY);
//        database.execSQL(RC_SHARING_TABLE_QUERY);
//        database.execSQL(CITIZEN_OFFENCE_LIST_QUERY);
//        database.execSQL(STATE_WISE_SERVICE_TABLE_QUERRY);
//        database.execSQL(GENERAL_NOTIFICATIONS_TABLE_QUERY);
//        database.execSQL(RECENT_SEARCH_TABLE_QUERY);
//        database.execSQL(MOCK_DATA_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion,
                          int newVersion) {
//        database.execSQL("DROP TABLE IF EXISTS " + RC_DETAILS_TABLE);
//        database.execSQL("DROP TABLE IF EXISTS " + RC_SHARING_TABLE);
//        database.execSQL("DROP TABLE IF EXISTS " + DL_DETAILS_TABLE);
//        database.execSQL("DROP TABLE IF EXISTS " + CITIZEN_OFFENCE_LIST_TABLE);
//        database.execSQL("DROP TABLE IF EXISTS " + STATE_WISE_SERVICE_TABLE);
//        database.execSQL("DROP TABLE IF EXISTS " + GENERAL_NOTIFICATIONS_TABLE);
//        database.execSQL("DROP TABLE IF EXISTS " + RECENT_SEARCH_TABLE);
//        database.execSQL("DROP TABLE IF EXISTS " + MOCK_DATA_TABLE_QUERY);


        database.execSQL("DROP TABLE IF EXISTS " + RC_DETAILS_TABLE);
//        database.execSQL("DROP TABLE IF EXISTS " + RC_SHARING_TABLE);
//        database.execSQL("DROP TABLE IF EXISTS " + DL_DETAILS_TABLE);
//        database.execSQL("DROP TABLE IF EXISTS " + CITIZEN_OFFENCE_LIST_TABLE);
//        database.execSQL("DROP TABLE IF EXISTS " + STATE_WISE_SERVICE_TABLE);
//        database.execSQL("DROP TABLE IF EXISTS " + GENERAL_NOTIFICATIONS_TABLE);
//        database.execSQL("DROP TABLE IF EXISTS " + RECENT_SEARCH_TABLE);
//        database.execSQL("DROP TABLE IF EXISTS " + MOCK_DATA_TABLE);
        onCreate(database);
    }





//insert the cart data into the app--
    public long save_cart_value(String  product_id_txt, String product_name_txt,String product_dec_txt,String product_Food_image_txt,String product_PRICE_txt,String product_Quantity_txt,String product_cat_id,String total_amount) {
        //Log.v("DB_RC_DETAILS", rcID);
        SQLiteDatabase db = null;
        long count = -1;
        try {
            db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(product_id, product_id_txt);
            values.put(product_name,product_name_txt);
            values.put(product_dec, product_dec_txt);
            values.put(product_Food_image, product_Food_image_txt);
            values.put(product_PRICE, product_PRICE_txt);
            values.put(product_Quantity, product_Quantity_txt);
            values.put(product_total_amount, total_amount);
            values.put(product_category_id,product_cat_id);
            Cursor cursor = null;
            try {
                db = this.getReadableDatabase();
                String query = "select *" + " from " + RC_DETAILS_TABLE + " WHERE " + product_id + " =" + "'" + product_id_txt + "'";
                cursor = db.rawQuery(query, null);
                if (cursor != null) {
                    if (cursor.getCount() > 0) {
                        count = db.update(RC_DETAILS_TABLE, values, product_id + "=?", new String[]{String.valueOf(product_id_txt)});
                        return count;
                    } else {
                        count = db.insert(RC_DETAILS_TABLE, null, values);
                        //Log.v("RC_SAVE_STATUS", "Count : " + count);
                        return count;
                    }
                } else {
                    count = db.insert(RC_DETAILS_TABLE, null, values);
                    //Log.v("RC_SAVE_STATUS", "Count : " + count);
                    return count;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            //  count = db.insert(RC_DETAILS_TABLE, null, values);
            //Log.v("RC_SAVE_STATUS", "Count : " + count);
            return count;
        } catch (SQLiteConstraintException e) {
            e.printStackTrace();
            return count;
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            return count;
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }


    public List<CartModal> getdata() {
        try {
            SQLiteDatabase database = getWritableDatabase();
            Cursor cursor = database.rawQuery("select * from " + RC_DETAILS_TABLE, null);

            if (cursor != null) {

                cursor.moveToFirst();

                do {
                    CartModal cartModal = new CartModal();
                    cartModal.setCart_item_id(cursor.getInt(cursor.getColumnIndex(DatabaseHelper.product_id)));
                    cartModal.setCart_item_name(cursor.getString(cursor.getColumnIndex(DatabaseHelper.product_name)));
                    cartModal.setQuantity(Integer.parseInt(cursor.getString(cursor.getColumnIndex(DatabaseHelper.product_Quantity))));
                    cartModal.setCart_item_price(Integer.parseInt(cursor.getString(cursor.getColumnIndex(DatabaseHelper.product_PRICE))));
                    //cartModal.setCart_item_img_url(cursor.getString(cursor.getColumnIndex(DatabaseHelper.product_Food_image)));
                    cartModalArrayList.add(cartModal);
                } while (cursor.moveToNext());
            }

            System.out.println("cart modal array list "+ cartModalArrayList);
            database.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return cartModalArrayList;
    }



    //get the cart details-->

    public String get_the_total_amount()
    {
        SQLiteDatabase db = null;
        String amount_total = null;
        double total_amount = 0;

        Cursor cursor = null;
        try {
            db = this.getReadableDatabase();
            String query = "select *" + " from " + RC_DETAILS_TABLE ;
            cursor = db.rawQuery(query, null);
            if (cursor != null) {

                if (cursor.moveToFirst())
                {
                    do
                        {

                     String amount =   cursor.getString(5);
                     String quantity =  cursor.getString(6);

                            total_amount = total_amount+(Double.parseDouble(amount) * (Integer.parseInt(quantity)));

                     Log.e("Amount"," "+amount+" "+quantity);

                            amount_total=amount;
                    } while (cursor.moveToNext());
                }
            }
            return Integer.toString((int)total_amount);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
        finally
        {
            if (cursor != null && !cursor.isClosed())
            {
                cursor.close();
            }
            if (db != null)
            {
                db.close();
            }
        }
    }





    public String get_the_total_quantity()
    {
        SQLiteDatabase db = null;
        String quantity_total = null;
        int total_amount = 0;

        Cursor cursor = null;
        try {
            db = this.getReadableDatabase();
            String query = "select *" + " from " + RC_DETAILS_TABLE ;
            cursor = db.rawQuery(query, null);
            if (cursor != null) {

                if (cursor.moveToFirst())
                {
                    do
                    {

                        String amount =   cursor.getString(5);
                        String quantity =  cursor.getString(6);

                        total_amount = total_amount+Integer.parseInt(quantity);

                        Log.e("Amount"," "+amount+" "+quantity);


                    } while (cursor.moveToNext());
                }
            }
            return Integer.toString(total_amount);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
        finally
        {
            if (cursor != null && !cursor.isClosed())
            {
                cursor.close();
            }
            if (db != null)
            {
                db.close();
            }
        }
    }

    public ArrayList<HashMap<String,String>> get_the_cart_data()
    {
        SQLiteDatabase db = null;
        String amount_total = null;
        ArrayList<HashMap<String,String>> cat_list=new ArrayList<>();
        cat_list.clear();
        HashMap<String,String> cat_list_hash;


        Cursor cursor = null;
        try {
            db = this.getReadableDatabase();
            String query = "select *" + " from " + RC_DETAILS_TABLE ;
            cursor = db.rawQuery(query, null);
            if (cursor != null) {

                if (cursor.moveToFirst())
                {
                    do
                    {
                        cat_list_hash = new HashMap<>();
                        cat_list_hash.clear();

                        String product_id =   cursor.getString(1);
                        String quantity =  cursor.getString(6);
                        cat_list_hash.put("product_id",product_id);
                        cat_list_hash.put("qty",quantity);

                        cat_list.add(cat_list_hash);



                    }
                    while (cursor.moveToNext());
                }
            }
            return cat_list;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
        finally
        {
            if (cursor != null && !cursor.isClosed())
            {
                cursor.close();
            }
            if (db != null)
            {
                db.close();
            }
        }
    }






    public String get_the_total_quantity_by_id(String product_id1)
    {
        SQLiteDatabase db = null;
        String quantity_total = null;
        int total_amount = 0;

        Cursor cursor = null;
        try {
            db = this.getReadableDatabase();
            String query = "select *" + " from " + RC_DETAILS_TABLE + " WHERE " + product_id + " =" + "'" + product_id1 + "'";
            cursor = db.rawQuery(query, null);
            if (cursor != null) {

                if (cursor.moveToFirst())
                {
                    do
                    {

                        String amount =   cursor.getString(5);
                        String quantity =  cursor.getString(6);

                        total_amount = total_amount+Integer.parseInt(quantity);

                        Log.e("Amount-----"," "+" "+quantity);


                    } while (cursor.moveToNext());
                }
            }
            return Integer.toString(total_amount);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
        finally
        {
            if (cursor != null && !cursor.isClosed())
            {
                cursor.close();
            }
            if (db != null)
            {
                db.close();
            }
        }
    }



//upate the product in the cart before order-->
    public long update_cart_value(String  product_id_txt,String product_total_amounts,String product_Quantity_txt)
    {
        //Log.v("DB_RC_DETAILS", rcID);
        SQLiteDatabase db = null;
        long count = -1;
        try {
            db = this.getWritableDatabase();

            ContentValues values = new ContentValues();

            values.put(product_Quantity, product_Quantity_txt);
            values.put(product_total_amount, product_total_amounts);
            Cursor cursor = null;
            try {
                db = this.getReadableDatabase();
                String query = "select *" + " from " + RC_DETAILS_TABLE + " WHERE " + product_id + " =" + "'" + product_id_txt + "'";
                cursor = db.rawQuery(query, null);
                if (cursor != null)
                {
                    if (cursor.getCount() > 0)
                    {
                        count = db.update(RC_DETAILS_TABLE, values, product_id + "=?", new String[]{String.valueOf(product_id_txt)});
                        return count;
                    }
                    else
                        {

                        //Log.v("RC_SAVE_STATUS", "Count : " + count);
                        return 0;
                    }
                } else {

                    //Log.v("RC_SAVE_STATUS", "Count : " + count);
                    return 0;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            //  count = db.insert(RC_DETAILS_TABLE, null, values);
            //Log.v("RC_SAVE_STATUS", "Count : " + count);
            return count;
        } catch (SQLiteConstraintException e) {
            e.printStackTrace();
            return count;
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
            return count;
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

//get the cart data from the with cart amount

    public ArrayList<HashMap<String,String>> get_the_cart_data_with_product_name()
    {
        SQLiteDatabase db = null;
        String amount_total = null;
        ArrayList<HashMap<String,String>> cat_list=new ArrayList<>();
        HashMap<String,String> cat_list_hash;


        Cursor cursor = null;
        try {
            db = this.getReadableDatabase();
            String query = "select *" + " from " + RC_DETAILS_TABLE ;
            cursor = db.rawQuery(query, null);
            if (cursor != null) {

                if (cursor.moveToFirst())
                {
                    do
                    {
                        cat_list_hash = new HashMap<>();
                        cat_list_hash.clear();

                        String product_id =   cursor.getString(1);
                        String product_name =   cursor.getString(2);
                        String quantity =  cursor.getString(6);
                        String total_amount =  cursor.getString(7);
                        String product_cat_id =  cursor.getString(8);
                        cat_list_hash.put("product_id",product_id);
                        cat_list_hash.put("product_name",product_name);
                        cat_list_hash.put("qty",quantity);
                        cat_list_hash.put("total_amount",total_amount);
                        cat_list_hash.put("product_cat_id",product_cat_id);

                        cat_list.add(cat_list_hash);
                    }
                    while (cursor.moveToNext());
                }
            }
            return cat_list;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
        finally
        {
            if (cursor != null && !cursor.isClosed())
            {
                cursor.close();
            }
            if (db != null)
            {
                db.close();
            }
        }
    }



    //get the delect -->
    public int deleteItem(String prduct_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = "Product_ID=?";
        String whereArgs[] = {prduct_id};
     int result = db.delete(RC_DETAILS_TABLE, whereClause, whereArgs);

     return  result;

    }


    public void delet_database()
    {
        SQLiteDatabase db = this.getWritableDatabase();
     //   db.execSQL("DROP TABLE IF EXISTS " + RC_DETAILS_TABLE);

        db.execSQL("delete from "+ RC_DETAILS_TABLE);

    }




}
