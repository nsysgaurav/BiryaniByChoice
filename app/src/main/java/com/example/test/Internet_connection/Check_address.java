package com.example.test.Internet_connection;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.test.ViewHolder.Addresss_adapter;
import com.example.test.activity.Add_address_before_payment;
import com.example.test.activity.My_Adrress;
import com.example.test.activity.Order_Summary_Activity;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Check_address
{
    HashMap<String,String> address_hashMap;
    ArrayList<HashMap<String,String>> hashMapArrayList=new ArrayList<>();


    public void  get_the_address(final Context context, String user_id, final ShimmerFrameLayout mShimmerViewContainer, final String total_amount)
    {
        mShimmerViewContainer.setVisibility(View.VISIBLE);
        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put("nsyskey","082057a4d249514389bb90c6d50ecf23");
        hashMap.put("uid",user_id);

        Gson gson = new Gson();
        String jsno_data = gson.toJson(hashMap);
        Log.e("jsno_data",jsno_data.toString());
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://3.6.27.167/api/get-customer-address";
        JsonObjectRequest postRequest = null;
        try {
            postRequest = new JsonObjectRequest(url, new JSONObject(jsno_data),
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
                                    if(status==200)
                                    {

                                        JSONObject json_success_obj = response.getJSONObject("success");
                                        JSONObject json_data_obj = json_success_obj.getJSONObject("data");
                                        JSONArray jsonArray = json_data_obj.getJSONArray("address");
                                        for(int i=0;i<1;i++)
                                        {
                                            JSONObject obj_address=jsonArray.getJSONObject(i);
                                            address_hashMap = new HashMap<>();
                                            address_hashMap.clear();
                                            String id=obj_address.getString("id");
                                            String first_name=obj_address.getString("first_name");
                                            String last_name=obj_address.getString("last_name");
                                            String address1=obj_address.getString("address1");
                                            String address2=obj_address.getString("address2");
                                            String city=obj_address.getString("city");
                                            String zipcode=obj_address.getString("zipcode");
                                            String phone=obj_address.getString("phone");
                                            String landmark=obj_address.getString("landmark");
                                            String type=obj_address.getString("type");
                                            String state=obj_address.getString("state");
                                            String address_type=obj_address.getString("address_type");

                                            address_hashMap.put("id",id);
                                            address_hashMap.put("first_name",first_name);
                                            address_hashMap.put("last_name",last_name);
                                            address_hashMap.put("address1",address1);
                                            address_hashMap.put("address2",address2);
                                            address_hashMap.put("city",city);
                                            address_hashMap.put("zipcode",zipcode);
                                            address_hashMap.put("phone",phone);
                                            address_hashMap.put("landmark",landmark);
                                            address_hashMap.put("type",type);
                                            address_hashMap.put("state",state);
                                            address_hashMap.put("address_type",address_type);

                                            hashMapArrayList.add(address_hashMap);
                                        }

                                        if(hashMapArrayList.isEmpty())
                                        {
                                            Intent intent=new Intent(context, Add_address_before_payment.class);
                                            intent.putExtra("total_amount",total_amount);
                                            context.startActivity(intent);

                                        }
                                        else
                                        {
                                            Intent intent=new Intent(context, Order_Summary_Activity.class);
                                            intent.putExtra("Address_list",hashMapArrayList);
                                            intent.putExtra("total_amount",total_amount);
                                            context.startActivity(intent);
                                        }

                                    }

                                    else
                                    {
                                         Intent intent=new Intent(context, Add_address_before_payment.class);
                                        intent.putExtra("total_amount",total_amount);
                                         context.startActivity(intent);

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
