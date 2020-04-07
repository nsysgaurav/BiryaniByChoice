package com.example.test.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.test.Model.Order_details;
import com.example.test.R;
import com.example.test.Sqldirectory.DatabaseHelper;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Payment_Process_Activity extends AppCompatActivity {

    TextView total_amount_card,order_now;
    DatabaseHelper data_base;
    SessionManager sessionManager;
    Intent intent;
    String Shipping_id,billing_id,total_amount;
    RadioButton cash_on_delivery;
    LinearLayout visible_linera_layout;
    int i=0;
    ProgressBar progress_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment__process);
        total_amount_card = (TextView)findViewById(R.id.total_amount_card);
        order_now =(TextView)findViewById(R.id.order_now);
        cash_on_delivery = (RadioButton)findViewById(R.id.cash_on_delivery);
        visible_linera_layout=(LinearLayout)findViewById(R.id.visible_linera_layout);
        progress_bar = (ProgressBar)findViewById(R.id.progress_bar);
        data_base = new DatabaseHelper(this);
        sessionManager = new SessionManager(Payment_Process_Activity.this);
        intent=getIntent();

        if(intent!=null)
        {
            Shipping_id = intent.getStringExtra("shipping_address_id");
            billing_id = intent.getStringExtra("Billig_address_id");
            total_amount = intent.getStringExtra("total_amount");
            total_amount_card.setText("₹"+total_amount);

        }


        cash_on_delivery.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(isChecked==true)
                {
                    i=1;
                }
                else
                {
                    i=0;
                }
            }
        });


        order_now.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                if(i==1)
                {
                    order_now.setEnabled(false);
                    order_now_Service(sessionManager.getUserSession().getId(),Shipping_id,billing_id,total_amount);
                }
                else
                {

                }

            }
        });

    }

    public void  order_now_Service(String user_id, String shipping_address_id, String Billig_address, final String total_amount)
    {
        visible_linera_layout.setVisibility(View.GONE);
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
        RequestQueue queue = Volley.newRequestQueue(Payment_Process_Activity.this);
        String url = "http://3.6.27.167/api/add-order";
        JsonObjectRequest postRequest = null;
        try {
            postRequest = new JsonObjectRequest(Request.Method.POST,url, new JSONObject(jsno_data),
                    new Response.Listener<JSONObject>()
                    {
                        @Override
                        public void onResponse(JSONObject response)
                        {
                            Log.e("response",response.toString());

                            progress_bar.setVisibility(View.GONE);
                            visible_linera_layout.setVisibility(View.GONE);
                            if(response!=null)
                            {

                                try
                                {
                                    if(response.getInt("status")==200)
                                    {
                                        data_base.delet_database();
                                        Intent in=new Intent(Payment_Process_Activity.this,Payment_Success_Activity.class);
                                        in.putExtra("Order_number",response.getString("message"));
                                        in.putExtra("Total_Amount","₹"+total_amount);
                                        in.putExtra("Order_id",response.getString("orders_id"));
                                        startActivity(in);
                                        finish();
                                    }
                                    else
                                    {
                                        order_now.setEnabled(true);
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

                    order_now.setVisibility(View.VISIBLE);
                    progress_bar.setVisibility(View.GONE);
                    visible_linera_layout.setVisibility(View.GONE);
                    order_now.setEnabled(true);


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

    public ArrayList<HashMap<String,String>> get_cart()
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


}
