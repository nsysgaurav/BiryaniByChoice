
package com.example.test.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
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
import com.bumptech.glide.util.Util;
import com.example.test.Constants.Cons;
import com.example.test.Model.UserDetails;
import com.example.test.OrderCart.Cart;
import com.example.test.R;
import com.example.test.ViewHolder.Cart_layoutAdapter;
import com.example.test.util.utility;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Add_address_before_payment extends AppCompatActivity
{
    Toolbar toolbar;
    EditText house_text,road_name,land_mark,state,pincode,last_name,name,phone_number;
    RadioButton home_address,work_address;
    TextView save;
    AppCompatSpinner city;
    int address_type=0;
    UserDetails userDetails;
    SessionManager sessionManager;

    private ProgressBar progressBar;
    ArrayList<String> spinner_city_list=new ArrayList<>();
    Intent in;
    String total_amount;
    ArrayList<HashMap<String,String>> hashMapArrayList=new ArrayList<>();
    HashMap address_hashMap;

    public void spinner_list_data()
    {
        spinner_city_list.add("Select City");
        spinner_city_list.add("CENTRAL DELHI");
        spinner_city_list.add("NORTH DELHI");
        spinner_city_list.add("SOUTH WEST DELHI");
        spinner_city_list.add("WEST DELHI");
        spinner_city_list.add("NEW DELHI");
        spinner_city_list.add("EAST DELHI");


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address_before_payment);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        sessionManager=new SessionManager(this);
        userDetails = sessionManager.getUserSession();

        in = getIntent();
        if (in != null)
        {
            total_amount = in.getStringExtra("total_amount");
        }

        spinner_list_data();

        init();

    }

    public void init()
    {
        house_text=(EditText)findViewById(R.id.house_text);
        road_name=(EditText)findViewById(R.id.road_name);
        land_mark=(EditText)findViewById(R.id.land_mark);
        city=(AppCompatSpinner)findViewById(R.id.city);
        state=(EditText)findViewById(R.id.state);
        pincode=(EditText)findViewById(R.id.pincode);
        name=(EditText)findViewById(R.id.name);
        last_name=(EditText)findViewById(R.id.last_name) ;
        phone_number=(EditText)findViewById(R.id.phone_number);
        home_address=(RadioButton)findViewById(R.id.home_address);
        work_address =(RadioButton)findViewById(R.id.work_address);
        save =(TextView) findViewById(R.id.save);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar_buttom);

        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,spinner_city_list);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        city.setAdapter(aa);



        home_address.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {

                if(isChecked)
                {
                    work_address.setChecked(false);
                    address_type=1;

                }

            }
        });


        work_address.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {

                if(isChecked)
                {
                    home_address.setChecked(false);
                    address_type=2;

                }

            }
        });


        //save the address
        save.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String house_txt=house_text.getText().toString().trim();
                String road_txt=road_name.getText().toString().trim();
                String land_txt=land_mark.getText().toString().trim();
                String city_txt = city.getSelectedItem().toString();
                String state_txt=state.getText().toString().trim();
                String pincode_txt=pincode.getText().toString().trim();
                String name_txt=name.getText().toString().trim();


                String mobile_number=phone_number.getText().toString().trim();

                if(house_txt.length()<=0)
                {
                    Toast.makeText(Add_address_before_payment.this,"Please enter the house number",Toast.LENGTH_SHORT).show();
                }
                else if(road_txt.length()<=0)
                {
                    Toast.makeText(Add_address_before_payment.this,"Please enter the area",Toast.LENGTH_SHORT).show();

                }
                else if(city_txt.length()<=0)
                {
                    Toast.makeText(Add_address_before_payment.this,"Please enter the city",Toast.LENGTH_SHORT).show();

                }
                else if(state_txt.length()<=0)
                {
                    Toast.makeText(Add_address_before_payment.this,"Please enter the state",Toast.LENGTH_SHORT).show();

                }
                else if(pincode_txt.length()<6)
                {
                    Toast.makeText(Add_address_before_payment.this,"Please enter the Postal code",Toast.LENGTH_SHORT).show();

                }
                else if(name_txt.length()<=0)
                {
                    Toast.makeText(Add_address_before_payment.this,"Please enter the name",Toast.LENGTH_SHORT).show();

                }

                else if(last_name.getText().length()<=0)
                {
                    Toast.makeText(Add_address_before_payment.this,"Please enter the last name",Toast.LENGTH_SHORT).show();

                }

                else if(mobile_number.length()<10)
                {
                    Toast.makeText(Add_address_before_payment.this,"Please enter the mobile number",Toast.LENGTH_SHORT).show();

                }
                else if(address_type==0)
                {
                    Toast.makeText(Add_address_before_payment.this,"Please select the address type",Toast.LENGTH_SHORT).show();
                }
                else if(utility.isOnline(Add_address_before_payment.this)==false)
                {
                    Toast.makeText(Add_address_before_payment.this,"Kindly check your internet connection!",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    save.setVisibility(View.GONE);
                    save_the_address(userDetails.getId(),address_type,"nys",name_txt,last_name.getText().toString(),house_txt,road_txt,city_txt,state_txt,pincode_txt,mobile_number,land_txt);
                }
            }
        });

    }




    public void save_the_address(final String id, int address_type, String company_name, String first_name, String last_name, String addres1, String address2, String city, String state, String post_code, String phone, String landmark)
    {
        HashMap hashMap = new HashMap<>();
        hashMap.put("uid",Integer.parseInt(id));
        hashMap.put("type","SHIPPING");
        if(address_type==1)
        {
            hashMap.put("address_type","home");
        }
        if(address_type==2)
        {
            hashMap.put("address_type","work");
        }

        if(city.equalsIgnoreCase("CENTRAL DELHI"))
        {
            hashMap.put("city","1");

        }
        else if(city.equalsIgnoreCase("NORTH DELHI"))
        {
            hashMap.put("city","2");
        }
        else if(city.equalsIgnoreCase("SOUTH WEST DELHI"))
        {
            hashMap.put("city","3");
        }
        else if(city.equalsIgnoreCase("WEST DELHI"))
        {
            hashMap.put("city","4");
        }
        else if(city.equalsIgnoreCase("NEW DELHI"))
        {
            hashMap.put("city","5");
        }
        else if(city.equalsIgnoreCase("EAST DELHI"))
        {
            hashMap.put("city","6");
        }


        hashMap.put("company_name",company_name);
        hashMap.put("first_name",first_name);
        hashMap.put("last_name",last_name);
        hashMap.put("address1",addres1);
        hashMap.put("address2",address2);
        hashMap.put("state","1");
        hashMap.put("postcode",post_code);
        hashMap.put("phone",phone);
        hashMap.put("landmark",landmark);

        Gson gson = new Gson();
        String jsno_data = gson.toJson(hashMap);
        Log.e("jsno_data",jsno_data.toString());
        RequestQueue queue = Volley.newRequestQueue(Add_address_before_payment.this);
        String url = "http://3.6.27.167/api/add-customer-address";
        progressBar.setVisibility(View.VISIBLE);

        JsonObjectRequest postRequest = null;
        try {
            postRequest = new JsonObjectRequest(url, new JSONObject(jsno_data),
                    new Response.Listener<JSONObject>()
                    {
                        @Override
                        public void onResponse(JSONObject response)
                        {

                            if (progressBar.isShown()) {
                                progressBar.setVisibility(View.GONE);
                            }

                            if(response.has("status"))
                            {
                                try {
                                    int status=response.getInt("status");

                                    if(status==200)
                                    {
                                        JSONObject object=response.getJSONObject("success");
                                        if(object.getString("internal_message").equalsIgnoreCase("Address added successfully"))
                                        {

                                            //Toast.makeText(Add_address_before_payment.this,"Address added successfully ",Toast.LENGTH_SHORT).show();
                                          //  Intent in=new Intent(Add_address_before_payment.this,My_Adrress.class);
                                          //  startActivity(in);
                                      //      finish();

                                            get_the_address(Add_address_before_payment.this,id,total_amount);


                                        }
                                    }

                                    else
                                    {
                                        save.setVisibility(View.VISIBLE);
                                        Toast.makeText(Add_address_before_payment.this,"Failed to save the address",Toast.LENGTH_SHORT).show();
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
                    //      mShimmerViewContainer.stopShimmerAnimation();
                    //    mShimmerViewContainer.setVisibility(View.GONE);

                    if (progressBar.isShown()) {
                        progressBar.setVisibility(View.GONE);
                        save.setVisibility(View.VISIBLE);
                    }
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














    public void  get_the_address(final Context context, String user_id, final String total_amount)
    {

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
                          //  mShimmerViewContainer.stopShimmerAnimation();
                         //   mShimmerViewContainer.setVisibility(View.GONE);

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
                   // mShimmerViewContainer.stopShimmerAnimation();
                   // mShimmerViewContainer.setVisibility(View.GONE);

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
