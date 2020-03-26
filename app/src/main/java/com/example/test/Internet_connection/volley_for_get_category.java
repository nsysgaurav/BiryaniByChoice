package com.example.test.Internet_connection;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.test.activity.Home_Screen;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class volley_for_get_category
{

    ArrayList<HashMap<String,String>> cat_list=new ArrayList<>();
    HashMap<String,String> cat_list_hash;

    public void get_all_category(final Context context)
    {
        RequestQueue queue = Volley.newRequestQueue(context.getApplicationContext());

       String url = "http://3.6.27.167/api/cat/category-list";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        // response
                        Log.e("Response", response);

                        if(response!=null)
                        {
                            try
                            {
                                JSONObject object = new JSONObject(response.toString());

                                int status = object.getInt("status");

                                if(status==200)
                                {

                                    JSONArray jsonArray = object.getJSONArray("result");
                                    for(int i=0;i<jsonArray.length();i++)
                                    {
                                        JSONObject obj=jsonArray.getJSONObject(i);
                                        cat_list_hash = new HashMap<String,String>();
                                        cat_list_hash.clear();
                                        String id = obj.getString("id");
                                        String name = obj.getString("name");
                                        String slug = obj.getString("slug");
                                        cat_list_hash.put("id",id);
                                        cat_list_hash.put("name",name);
                                        cat_list_hash.put("slug",slug);
                                        cat_list.add(cat_list_hash);
                                    }

                                    Intent in=new Intent(context,Home_Screen.class);
                                    in.putExtra("All_cat_list",cat_list);
                                    context.startActivity(in);

                                }
                                else
                                {

                                }


                            }
                            catch (JSONException e)
                            {
                                e.printStackTrace();
                            }
                        }
                        else
                        {

                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        // error
                        Log.e("error",error.toString());

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("nsyskey", "082057a4d249514389bb90c6d50ecf23");
                params.put("catid", "");

                return params;
            }
        };
        postRequest.setShouldCache(false);
        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(postRequest);

    }






}
