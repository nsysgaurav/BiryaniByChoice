package com.example.test.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.test.Model.Cat_data;
import com.example.test.Model.Order_details;
import com.example.test.OrderCart.Cart;
import com.example.test.R;
import com.example.test.Sqldirectory.DatabaseHelper;
import com.example.test.ViewHolder.Cart_layoutAdapter;
import com.example.test.ViewHolder.Order_Summary_adapter;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Order_Summary_Activity extends AppCompatActivity {


    TextView name,address,phone_number,type;
    ArrayList<HashMap<String,String>> cart_map;
    Intent in;
    RecyclerView recyclerView;

    String id_int;
    String first_name_int;
    String last_name_int;
    String address1_int;
    String address2_int;
    String city_int;
    String zipcode_int;
    String phone_int;
    String landmark_int;
    String type_int;
    String state_int;
    String address_type_int;
    DatabaseHelper data_base;
    TextView order_now;
    SessionManager sessionManager;
     String id;
     String total_amount;
     TextView total_amount_card;
    ProgressBar progress_bar;
    NestedScrollView scroll_view;

    TextView deliver_charge,tax_charges;
    TextView total_amout_of_cart,pay_to;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order__summary);
        name = findViewById(R.id.name);
        scroll_view=(NestedScrollView)findViewById(R.id.scroll_view);
        address = findViewById(R.id.address);
        phone_number = findViewById(R.id.phone_number);
        total_amout_of_cart=(TextView)findViewById(R.id.total_amout_of_cart);
        pay_to = (TextView)findViewById(R.id.pay_to);


        deliver_charge=findViewById(R.id.deliver_charge);
        tax_charges=findViewById(R.id.tax_charges);

        type = findViewById(R.id.type);
        order_now=findViewById(R.id.order_now);
        total_amount_card=findViewById(R.id.total_amount_card);
        recyclerView = findViewById(R.id.recycle);
        progress_bar = (ProgressBar) findViewById(R.id.progress_bar);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        data_base = new DatabaseHelper(this);
        sessionManager=new SessionManager(Order_Summary_Activity.this);

        in = getIntent();
        if (in != null)
        {
            try
            {
                cart_map = (ArrayList<HashMap<String, String>>) in.getSerializableExtra("Address_list");
                total_amount=in.getStringExtra("total_amount");

                 id=cart_map.get(0).get("id");
                final String first_name=cart_map.get(0).get("first_name");
                final String last_name=cart_map.get(0).get("last_name");
                final String address1=cart_map.get(0).get("address1");
                final String address2=cart_map.get(0).get("address2");
                final String city=cart_map.get(0).get("city");
                final String zipcode=cart_map.get(0).get("zipcode");
                final String phone=cart_map.get(0).get("phone");
                final String landmark=cart_map.get(0).get("landmark");
                final String state=cart_map.get(0).get("state");
                final String address_type=cart_map.get(0).get("address_type");

                name.setText(first_name+" "+last_name);
                phone_number.setText(phone);
                address.setText(address1+", "+address2+", "+landmark+", "+city+"," +zipcode+", "+state);
                type.setText(address_type);



                total_amout_of_cart.setText("₹"+total_amount);

              get_the_delivery_charges(Order_Summary_Activity.this,sessionManager.getUserSession().getId(),total_amount,total_amount_card);


                ArrayList<HashMap<String,String>> cart_data =  data_base.get_the_cart_data_with_product_name();

                if(cart_data!=null)
                {
                    recyclerView.setAdapter(new Order_Summary_adapter(Order_Summary_Activity.this,cart_data,scroll_view));

                    scroll_view.scrollTo(0,0);
                }


            }
            catch (Exception e)
            {

            }

        }


        order_now.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                order_now.setVisibility(View.GONE);

                order_now_Service(sessionManager.getUserSession().getId(),id,id,total_amount);

            }
        });


    }


    public void  order_now_Service(String user_id, String shipping_address_id, String Billig_address, final String total_amount)
    {
        progress_bar.setVisibility(View.VISIBLE);
        Order_details order_details = new Order_details();
        order_details.setUid(user_id);
        order_details.setShipping_address_id(shipping_address_id);
        order_details.setBilling_address_id(Billig_address);
        order_details.setShipping_amount("40");
        order_details.setTax_amount("10");
        order_details.setTotal_amount(total_amount);
        order_details.setShipping_option("Standard Delivery");
        order_details.setPayment_option("Cash on Delivery");
        order_details.setProducts(get_cart());

        Gson gson = new Gson();
        String jsno_data = gson.toJson(order_details);
        Log.e("jsno_data",jsno_data.toString());
        RequestQueue queue = Volley.newRequestQueue(Order_Summary_Activity.this);
        String url = "http://3.6.27.167/api/add-order";
        JsonObjectRequest postRequest = null;
        try {
            postRequest = new JsonObjectRequest(Request.Method.POST,url, new JSONObject(jsno_data),
                    new Response.Listener<JSONObject>()
                    {
                        @Override
                        public void onResponse(JSONObject response)
                        {
                            //Process os success response
                       //     mShimmerViewContainer.stopShimmerAnimation();
                         //   mShimmerViewContainer.setVisibility(View.GONE);
                      //      bottom_sheet.setVisibility(View.VISIBLE);
                            Log.e("response",response.toString());
                            progress_bar.setVisibility(View.GONE);

                            if(response!=null)
                            {

                                try
                                {
                                    if(response.getInt("status")==200)
                                    {
                                        data_base.delet_database();
                                        Intent in=new Intent(Order_Summary_Activity.this,Payment_Success_Activity.class);
                                        in.putExtra("Order_number",response.getString("message"));
                                        in.putExtra("Total_Amount","₹"+total_amount);
                                        startActivity(in);
                                        finish();

                                    }
                                    else
                                    {
                                        order_now.setVisibility(View.VISIBLE);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                            }
                        }
                    }, new Response.ErrorListener()
            {
                @Override
                public void onErrorResponse(VolleyError error)
                {
                  //  mShimmerViewContainer.stopShimmerAnimation();
               //     mShimmerViewContainer.setVisibility(View.GONE);
                    progress_bar.setVisibility(View.GONE);
                    order_now.setVisibility(View.VISIBLE);


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


        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        postRequest.setShouldCache(false);
        queue.getCache().clear();
        queue.add(postRequest);

    }


    public ArrayList<HashMap<String,String>>  get_cart()
    {

        ArrayList<HashMap<String,String>> cat_list=new ArrayList<>();

        if(data_base.get_the_cart_data()!=null)
        {
            cat_list =  data_base.get_the_cart_data_for_order();
        }
        else
        {
            return null;
        }

        return cat_list;

    }

    //total amount-->

    public void  get_the_delivery_charges(final Context context, String user_id, String total_amount1, final TextView total_price)
    {

        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("uid",user_id);
        hashMap.put("order_amount",total_amount1);

        Gson gson = new Gson();
        String jsno_data = gson.toJson(hashMap);
        Log.e("jsno_data",jsno_data.toString());
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://3.6.27.167/api/get-order-charge-master";
        JsonObjectRequest postRequest = null;
        try {
            postRequest = new JsonObjectRequest(url, new JSONObject(jsno_data),
                    new Response.Listener<JSONObject>()
                    {
                        @Override
                        public void onResponse(JSONObject response)
                        {
                            //Process os success response
                            //mShimmerViewContainer.stopShimmerAnimation();
                            //mShimmerViewContainer.setVisibility(View.GONE);

                            Log.e("response",response.toString());

                            if(response!=null)
                            {
                                try
                                {
                                    int status = response.getInt("status");
                                    if(status==200)
                                    {

                                        try
                                        {

                                            JSONObject object=response.getJSONObject("data");
                                            String shipping_charge=object.getString("shipping_charge");
                                            String cgst_amount=object.getString("cgst_amount");
                                            String total_order_amount=object.getString("total_order_amount");


                                            if(shipping_charge!=null)
                                            {

                                                deliver_charge.setText("₹"+shipping_charge);
                                                tax_charges.setText("₹"+cgst_amount);
                                                total_amount=total_order_amount;
                                                pay_to.setText("₹"+total_order_amount);
                                                total_price.setText("₹"+total_order_amount);

                                            }

                                        }
                                        catch (Exception e)
                                        {

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
