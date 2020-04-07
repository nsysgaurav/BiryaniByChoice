package com.example.test.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.test.Internet_connection.Check_the_deliver_charges;
import com.example.test.Model.Cat_data;
import com.example.test.OrderCart.Cart;
import com.example.test.R;
import com.example.test.ViewHolder.Cart_layoutAdapter;
import com.example.test.ViewHolder.My_Order_Item_Adapter;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Order_history_Activity extends AppCompatActivity
{

    ShimmerFrameLayout mShimmerViewContainer;
    SessionManager manager;
    ArrayList<HashMap<String,String>> hash_order_data = new ArrayList<>();
    HashMap<String,String> hashMap_order;
    RecyclerView recyclerView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        mShimmerViewContainer = (ShimmerFrameLayout)findViewById(R.id.shimmer_view_container);
        recyclerView = findViewById(R.id.cartview);
        toolbar = findViewById(R.id.my_cart_toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        manager = new SessionManager(Order_history_Activity.this);
        if(manager.isLoggedIn())
        {
            set_the_order_history(manager.getUserSession().getId());
        }
        else
        {

        }


    }

    public void  set_the_order_history(final String user_id)
    {
        mShimmerViewContainer.startShimmerAnimation();
        HashMap map=new HashMap();
        map.clear();
        map.put("uid",Integer.parseInt(user_id));
        Gson gson = new Gson();
        String jsno_data = gson.toJson(map);
        Log.e("jsno_data",jsno_data.toString());
        RequestQueue queue = Volley.newRequestQueue(Order_history_Activity.this);
        String url = "http://3.6.27.167/api/get-orders";
        JsonObjectRequest postRequest = null;
        try {
            postRequest = new JsonObjectRequest(Request.Method.POST,url, new JSONObject(jsno_data),
                    new Response.Listener<JSONObject>()
                    {
                        @Override
                        public void onResponse(JSONObject response)
                        {
                            //Process os success response
                            mShimmerViewContainer.stopShimmerAnimation();
                            mShimmerViewContainer.setVisibility(View.GONE);
                            Log.e("response",response.toString());

                            if(response!=null)
                            {
                                try
                                {
                                    int status = response.getInt("status");
                                    if(status == 200)
                                    {
                                        JSONObject object_data = response.getJSONObject("data");
                                        JSONArray jsonArray = object_data.getJSONArray("result");
                                        for(int i=0;i<jsonArray.length();i++)
                                        {

                                            hashMap_order =new HashMap<>();
                                            hashMap_order.clear();
                                            JSONObject object_artical = jsonArray.getJSONObject(i);
                                            String id = object_artical.getString("id");
                                            String shipping_option = object_artical.getString("shipping_option");
                                            String payment_option = object_artical.getString("payment_option");
                                            String order_num = object_artical.getString("order_num");
                                            String user_id = object_artical.getString("user_id");
                                            String shipping_address_id = object_artical.getString("shipping_address_id");
                                            String billing_address_id = object_artical.getString("billing_address_id");
                                            String shipping_amount = object_artical.getString("shipping_amount");
                                            String tax_amount = object_artical.getString("tax_amount");
                                            String total_amount = object_artical.getString("total_amount");
                                            String coupon_code = object_artical.getString("coupon_code");
                                            String discount_amount = object_artical.getString("discount_amount");
                                            String track_code = object_artical.getString("track_code");
                                            String is_active = object_artical.getString("is_active");
                                            String created_at = object_artical.getString("created_at");
                                            String updated_at = object_artical.getString("updated_at");
                                            String order_status_text = object_artical.getString("order_status_text");

                                            hashMap_order.put("id",id);
                                            hashMap_order.put("shipping_option",shipping_option);
                                            hashMap_order.put("payment_option",payment_option);
                                            hashMap_order.put("order_num",order_num);
                                            hashMap_order.put("user_id",user_id);
                                            hashMap_order.put("shipping_address_id",shipping_address_id);
                                            hashMap_order.put("billing_address_id",billing_address_id);
                                            hashMap_order.put("shipping_amount",shipping_amount);
                                            hashMap_order.put("tax_amount",tax_amount);
                                            hashMap_order.put("total_amount",total_amount);
                                            hashMap_order.put("coupon_code",coupon_code);
                                            hashMap_order.put("discount_amount",discount_amount);
                                            hashMap_order.put("track_code",track_code);
                                            hashMap_order.put("is_active",is_active);
                                            hashMap_order.put("created_at",created_at);
                                            hashMap_order.put("updated_at",updated_at);
                                            hashMap_order.put("order_status_text",order_status_text);

                                            hash_order_data.add(hashMap_order);

                                        }



                                        if(hash_order_data.isEmpty())
                                        {

                                        }
                                        else
                                        {
                                            My_Order_Item_Adapter adapter=new My_Order_Item_Adapter(Order_history_Activity.this,hash_order_data);
                                            recyclerView.setAdapter(adapter);
                                        }







                                    }
                                }
                                catch (JSONException e)
                                {
                                    e.printStackTrace();
                                }


                            }
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



}
