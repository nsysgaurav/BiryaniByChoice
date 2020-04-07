package com.example.test.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.test.R;
import com.example.test.ViewHolder.Addresss_adapter;
import com.example.test.ViewHolder.My_Order_Item_Adapter;
import com.example.test.ViewHolder.Order_Summary_adapter;
import com.example.test.ViewHolder.Order_Tracking_Adapter;
import com.example.test.util.utility;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Order_Details_Tracking_Activity extends AppCompatActivity {


    Intent intent;
    ShimmerFrameLayout mShimmerViewContainer;
    ArrayList<HashMap<String,String>> hash_order_data = new ArrayList<>();
    HashMap<String,String> hashMap_order;


    ArrayList<HashMap<String,String>> product_data = new ArrayList<>();
    HashMap<String,String> hashMap_product_order;

    String user_id,order_id;
    RecyclerView recycle;
    NestedScrollView scroll_view;
    TextView oder_number;
    String order_num;
    TextView type,address;
    TextView deliver_charge,tax_charges,order_status;
    TextView pay_to;
    String shipping_amount ;
    String tax_amount ;
    String total_amount ;

    Toolbar toolbar;

    String action_datetime ;
    String order_status_text_status;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order__details__tracking);
        recycle = (RecyclerView)findViewById(R.id.recycle);
        oder_number=(TextView)findViewById(R.id.oder_number);
        type=(TextView)findViewById(R.id.type);
        address=(TextView)findViewById(R.id.address);
        pay_to = (TextView)findViewById(R.id.pay_to);
        deliver_charge =findViewById(R.id.deliver_charge);
        tax_charges=findViewById(R.id.tax_charges);
        order_status=findViewById(R.id.order_status);

        recycle.setHasFixedSize(true);
        recycle.setLayoutManager(new LinearLayoutManager(this));

        toolbar = findViewById(R.id.my_cart_toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        scroll_view=findViewById(R.id.scroll_view);
        mShimmerViewContainer = (ShimmerFrameLayout)findViewById(R.id.shimmer_view_container);
        intent = getIntent();

        if(intent!=null)
        {
            user_id=intent.getStringExtra("user_id");
            order_id=intent.getStringExtra("order_id");

            try {

                set_the_order_history(user_id,order_id);
            }
            catch (Exception e)
            {

            }


        }
    }

    public void  set_the_order_history(final String user_id,final String order_id)
    {
        mShimmerViewContainer.startShimmerAnimation();
        HashMap map=new HashMap();
        map.clear();
        map.put("uid",Integer.parseInt(user_id));
        map.put("order_id",Integer.parseInt(order_id));
        Gson gson = new Gson();
        String jsno_data = gson.toJson(map);
        Log.e("jsno_data",jsno_data.toString());
        RequestQueue queue = Volley.newRequestQueue(Order_Details_Tracking_Activity.this);
        String url = "http://3.6.27.167/api/get-order-products";
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
                                            hashMap_order = new HashMap<>();
                                            hashMap_order.clear();
                                            JSONObject object_artical = jsonArray.getJSONObject(i);
                                            String id = object_artical.getString("id");
                                            String shipping_option = object_artical.getString("shipping_option");
                                            String payment_option = object_artical.getString("payment_option");
                                             order_num = object_artical.getString("order_num");
                                            String user_id = object_artical.getString("user_id");
                                            String shipping_address_id = object_artical.getString("shipping_address_id");
                                            String billing_address_id = object_artical.getString("billing_address_id");
                                             shipping_amount = object_artical.getString("shipping_amount");
                                             tax_amount = object_artical.getString("tax_amount");
                                             total_amount = object_artical.getString("total_amount");
                                            String coupon_code = object_artical.getString("coupon_code");
                                            String discount_amount = object_artical.getString("discount_amount");
                                            String track_code = object_artical.getString("track_code");
                                            String is_active = object_artical.getString("is_active");
                                            String created_at = object_artical.getString("created_at");
                                            String updated_at = object_artical.getString("updated_at");
                                            String order_status_text = object_artical.getString("order_status_text");

                                            JSONArray jsonArray1 = object_artical.getJSONArray("order_products");

                                            JSONArray order_address_array = object_artical.getJSONArray("order_address");
                                            JSONArray order_track_data_array = object_artical.getJSONArray("order_track_data");





                                            for(int j=0;j<jsonArray1.length();j++)
                                            {

                                                hashMap_product_order=new HashMap<>();
                                                hashMap_product_order.clear();

                                                JSONObject object_product = jsonArray1.getJSONObject(j);
                                                String product_id = object_product.getString("product_id");
                                                String qty = Integer.toString(object_product.getInt("qty"));
                                                String price = object_product.getString("price");
                                                String product_name = object_product.getString("product_name");
                                                String product_images_path = object_product.getString("product_images_path");

                                                hashMap_product_order.put("product_id",product_id);
                                                hashMap_product_order.put("qty",qty);
                                                hashMap_product_order.put("price",price);
                                                hashMap_product_order.put("product_name",product_name);
                                                hashMap_product_order.put("product_images_path",product_images_path);

                                                product_data.add(hashMap_product_order);

                                            }



                                            for(int a=0;a<order_address_array.length();a++)
                                            {
                                                JSONObject address_object = order_address_array.getJSONObject(a);
                                                String address_type = address_object.getString("address_type");
                                                String first_name = address_object.getString("first_name");
                                                String last_name = address_object.getString("last_name");
                                                String address1 = address_object.getString("address1");
                                                String address2 = address_object.getString("address2");
                                                String postcode = address_object.getString("postcode");
                                                String phone = address_object.getString("phone");
                                                String landmark = address_object.getString("landmark");
                                                String city = address_object.getString("city");
                                                String state = address_object.getString("state");

                                                address.setText(first_name+""+last_name+", "+address1+", "+address2+", "+landmark+", "+city+"," +postcode+", "+state);
                                                type.setText(address_type);


                                            }


                                            for(int d=0;d<order_track_data_array.length();d++)
                                            {
                                                JSONObject order_track_data_array_object = order_track_data_array.getJSONObject(d);

                                                 action_datetime = order_track_data_array_object.getString("action_datetime");
                                                 order_status_text_status = order_track_data_array_object.getString("order_status_text");

                                            }

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

                                        if(product_data.isEmpty())
                                        {

                                        }
                                        else
                                        {
                                            try
                                            {
                                                oder_number.setText(order_num);
                                                deliver_charge.setText("₹"+shipping_amount);
                                                tax_charges.setText("₹"+tax_amount);
                                                pay_to.setText("₹"+total_amount);
                                                order_status.setText(order_status_text_status+" at "+ utility.get_date_string_format(action_datetime));
                                            }
                                            catch (Exception e)
                                            {

                                            }


                                            recycle.setAdapter(new Order_Tracking_Adapter(Order_Details_Tracking_Activity.this,product_data,scroll_view));
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
