package com.example.test.Back_Ground_Services;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.util.Util;
import com.example.test.Sqldirectory.DatabaseHelper;
import com.example.test.activity.Activity_otp_verify_registration;
import com.example.test.activity.SessionManager;
import com.example.test.util.utility;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Job_intent_Service_for_update_the_cart_data extends JobIntentService {

    private static final String TAG = "MyJobIntentService";
    /**
     * Unique job ID for this service.
     */
    private static final int JOB_ID = 1;
    public static final String RECEIVER = "receiver";
    private static final String ACTION_DOWNLOAD = "action.DOWNLOAD_DATA";

    String power_key;
    DatabaseHelper databaseHelper;
    SessionManager sessionManager;

    public static void enqueueWork(Context context, Intent intent) {
        enqueueWork(context, Job_intent_Service_for_update_the_cart_data.class, JOB_ID, intent);
    }



    @Override
    public void onCreate() {
        super.onCreate();


        databaseHelper = new DatabaseHelper(this);
         sessionManager = new SessionManager(this);



    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        if (intent != null) {
            if (utility.isOnline(this)==true)
            {
                try {

if(sessionManager.isLoggedIn())
{
    save_the_data_to_cart(sessionManager.getUserSession().getId());
}
else
{

}


                }
                catch (Exception e)
                {

                }

            }
        }
    }


    public void  save_the_data_to_cart(String user_id)
    {
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("uid",user_id);

        Gson gson = new Gson();
        String jsno_data = gson.toJson(hashMap);
        Log.e("jsno_data",jsno_data.toString());
        RequestQueue queue = Volley.newRequestQueue(Job_intent_Service_for_update_the_cart_data.this);
        String url = "http://3.6.27.167/api/get-cart";
        JsonObjectRequest postRequest = null;
        try {
            postRequest = new JsonObjectRequest(Request.Method.POST,url, new JSONObject(jsno_data),
                    new Response.Listener<JSONObject>()
                    {
                        @Override
                        public void onResponse(JSONObject response)
                        {
                            Log.e("response",response.toString());
                            if(response!=null)
                            {
                                try
                                {
                                    int status = response.getInt("status");
                                    if(status == 200)
                                    {
                                        JSONObject object_data = response.getJSONObject("data");
                                        JSONArray jsonArray = object_data.getJSONArray("cart_total");
                                        for(int i=0;i<jsonArray.length();i++)
                                        {
                                            JSONObject object_artical = jsonArray.getJSONObject(i);
                                            String product_id = object_artical.getString("product_id");
                                            String quantity = object_artical.getString("quantity");
                                            String amount = object_artical.getString("amount");
                                            String product_amount = object_artical.getString("product_amount");
                                            String product_name = object_artical.getString("product_name");
                                            //   String product_cat_id = object_artical.getString("product_cat_id");

                                            try
                                            {
                                                databaseHelper.save_cart_value(product_id,product_name,"product_dec","image",product_amount,quantity,"",amount);

                                            }
                                            catch (Exception e)
                                            {

                                            }
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
                    // mShimmerViewContainer.stopShimmerAnimation();
                    //mShimmerViewContainer.setVisibility(View.GONE);


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

