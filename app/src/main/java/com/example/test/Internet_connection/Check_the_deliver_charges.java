package com.example.test.Internet_connection;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.test.activity.Add_address_before_payment;
import com.example.test.activity.Order_Summary_Activity;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Check_the_deliver_charges
{
    HashMap<String,String> address_hashMap;
    ArrayList<HashMap<String,String>> hashMapArrayList=new ArrayList<>();


    public void  get_the_delivery_charges(final Context context, String user_id, String total_amount, final TextView deliver_charge, final TextView tax_charges, final TextView pay_to)
    {

        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("uid",user_id);
        hashMap.put("order_amount",total_amount);

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
                                                pay_to.setText("₹"+total_order_amount);

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



    public void  get_the_delivery_charges(final Context context, String user_id, String total_amount, final TextView tax_charges, final TextView pay_to)
    {

        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("uid",user_id);
        hashMap.put("order_amount",total_amount);

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

                                              //  deliver_charge.setText("₹"+shipping_charge);
                                                tax_charges.setText("₹"+cgst_amount);
                                                pay_to.setText("₹"+total_order_amount);

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



    public void  get_the_delivery_charges(final Context context, String user_id, String total_amount, final TextView total_price)
    {

        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("uid",user_id);
        hashMap.put("order_amount",total_amount);

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
