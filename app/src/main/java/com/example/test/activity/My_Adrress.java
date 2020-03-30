package com.example.test.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.test.Model.Cat_data;
import com.example.test.Model.UserDetails;
import com.example.test.OrderCart.Cart;
import com.example.test.R;
import com.example.test.ViewHolder.Addresss_adapter;
import com.example.test.ViewHolder.Cart_layoutAdapter;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class My_Adrress extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    SessionManager sessionManager;
    UserDetails userDetails;
    private ShimmerFrameLayout mShimmerViewContainer;
    ArrayList<HashMap<String,String>> hashMapArrayList=new ArrayList<>();
    HashMap<String,String> address_hashMap;
    TextView textView;
    ProgressBar progress_bar;
    LinearLayout add_the_address;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my__adrress);
        toolbar = (Toolbar) findViewById(R.id.my_address_toolbar);
        mShimmerViewContainer = (ShimmerFrameLayout)findViewById(R.id.shimmer_view_container);
        sessionManager = new SessionManager(this);
        userDetails = sessionManager.getUserSession();
        recyclerView = findViewById(R.id.recycle);
        progress_bar = (ProgressBar) findViewById(R.id.progress_bar);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mShimmerViewContainer.startShimmerAnimation();
        textView=(TextView)findViewById(R.id.address_counter);
        add_the_address=(LinearLayout)findViewById(R.id.add_the_address);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

//add the new
        add_the_address.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(My_Adrress.this,Add_new_Address_Activity.class);
                intent.putExtra("edit",1);
                startActivity(intent);

            }
        });

        if(userDetails.getId()!= null)
        {
            get_the_address(userDetails.getId());
        }


    }


    public void  get_the_address(String user_id)
    {
        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put("nsyskey","082057a4d249514389bb90c6d50ecf23");
        hashMap.put("uid",user_id);

        Gson gson = new Gson();
        String jsno_data = gson.toJson(hashMap);
        Log.e("jsno_data",jsno_data.toString());
        RequestQueue queue = Volley.newRequestQueue(My_Adrress.this);
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
                                 for(int i=0;i<jsonArray.length();i++)
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

                                 if(hashMapArrayList.size()>0)
                                 {
                                     textView.setText(hashMapArrayList.size()+" SAVED ADDRESS");
                                     Addresss_adapter adapter = new Addresss_adapter(My_Adrress.this,hashMapArrayList,progress_bar,textView);
                                     recyclerView.setAdapter(adapter);

                                 }
                                 else
                                 {
                                     textView.setText("NO SAVED ADDRESS");
                                    // Addresss_adapter adapter = new Addresss_adapter(My_Adrress.this,hashMapArrayList);
                                    // recyclerView.setAdapter(adapter);

                                 }
                                    }

                                    else
                                    {
                                        textView.setText("NO SAVED ADDRESS");
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
