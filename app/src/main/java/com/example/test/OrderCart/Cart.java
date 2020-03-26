package com.example.test.OrderCart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.test.Model.CartModal;
import com.example.test.Model.Cat_data;
import com.example.test.Model.UserDetails;
import com.example.test.R;
import com.example.test.Sqldirectory.CartLitedb;
import com.example.test.Sqldirectory.DatabaseHelper;
import com.example.test.ViewHolder.Cart_layoutAdapter;
import com.example.test.activity.SessionManager;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Cart extends AppCompatActivity {
    String food ="";
    ArrayList<CartModal> cartModalArrayList = new ArrayList<>();
    DatabaseHelper data_base;
    RelativeLayout relativeLayout;
    TextView remove_donation , item_total;
    private ShimmerFrameLayout mShimmerViewContainer;
    SessionManager sessionManager;
    String user_id;
    UserDetails userDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        init();
        item_total = findViewById(R.id.items_total);
        DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());
        RecyclerView recyclerView = findViewById(R.id.cartview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        sessionManager = new SessionManager(this);
        data_base = new DatabaseHelper(this);
        mShimmerViewContainer.startShimmerAnimation();
        userDetails = sessionManager.getUserSession();
        if(userDetails.getId()!= null)
         {
             save_the_data_to_cart(userDetails.getId());
         }

       //  recyclerView.setAdapter(new Cart_layoutAdapter((ArrayList<CartModal>) databaseHelper.getdata(),item_total));

    }


    public void init()
    {
        mShimmerViewContainer = (ShimmerFrameLayout)findViewById(R.id.shimmer_view_container);
    }


    //save the product to the cart-->
    public void  save_the_data_to_cart(String user_id)
    {
        Cat_data cart = new Cat_data();
        cart.setUid(user_id);

        cart.setProducts(get_cart());

        Gson gson=new Gson();
       String jsno_data = gson.toJson(cart);
       Log.e("jsno_data",jsno_data.toString());


        RequestQueue queue = Volley.newRequestQueue(Cart.this);
        String url = "http://3.6.27.167/api/addtocart";
        JsonObjectRequest postRequest = null;
        try {
            postRequest = new JsonObjectRequest(url, new JSONObject(jsno_data),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            //Process os success response
                            mShimmerViewContainer.stopShimmerAnimation();
                            mShimmerViewContainer.setVisibility(View.GONE);
                            Log.e("response",response.toString());

                        }
                    }, new Response.ErrorListener()
            {
                @Override
                public void onErrorResponse(VolleyError error)
                {
                    mShimmerViewContainer.stopShimmerAnimation();
                    mShimmerViewContainer.setVisibility(View.GONE);


                    Log.e("error",error.toString());
                }

            }){
                    @Override
                    public String getBodyContentType()
                    {
                    return "application/json";
                }



            };
        } catch (JSONException e) {
            e.printStackTrace();
        }

        postRequest.setShouldCache(false);
        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(postRequest);

    }


    public ArrayList<HashMap<String,String>>  get_cart()
    {

        ArrayList<HashMap<String,String>> cat_list=new ArrayList<>();

        if(data_base.get_the_cart_data()!=null)
        {
            cat_list =  data_base.get_the_cart_data();
        }
        else
        {
            return null;
        }

        return cat_list;

    }






}
