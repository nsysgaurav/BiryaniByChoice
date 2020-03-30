
package com.example.test.activity;

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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.test.Model.UserDetails;
import com.example.test.R;
import com.example.test.util.utility;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Add_new_Address_Activity extends AppCompatActivity
{
    Toolbar toolbar;
 public    EditText house_text,road_name,land_mark,state,pincode,name,phone_number,last_name;
    RadioButton home_address,work_address;
    LinearLayout save;
    int address_type=0;
    UserDetails userDetails;
    SessionManager sessionManager;

    private ProgressBar progressBar;
    AppCompatSpinner city;

    ArrayList<String> spinner_city_list=new ArrayList<>();

Intent in;
int isedit;

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_address_activity);
        toolbar = (Toolbar) findViewById(R.id.add_address_toolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // getSupportActionBar().setDisplayShowHomeEnabled(true);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        sessionManager = new SessionManager(this);
        userDetails = sessionManager.getUserSession();
        spinner_list_data();
        init();


        in=getIntent();
        if(in!=null)
        {
            isedit=in.getIntExtra("edit",0);

            if(isedit==2)
            {
                 id_int=in.getStringExtra("id");
                 first_name_int=in.getStringExtra("first_name");
                 last_name_int=in.getStringExtra("last_name");
                 address1_int=in.getStringExtra("address1");
                 address2_int=in.getStringExtra("address2");
                 city_int=in.getStringExtra("city");
                 zipcode_int=in.getStringExtra("zipcode");
                 phone_int=in.getStringExtra("phone");
                 landmark_int=in.getStringExtra("landmark");
                 type_int=in.getStringExtra("type");
                 state_int=in.getStringExtra("state");
                 address_type_int=in.getStringExtra("address_type");

                set_the_data_from_intent();
            }

        }



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
        save =(LinearLayout)findViewById(R.id.save);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

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
                    Toast.makeText(Add_new_Address_Activity.this,"Please enter the house number",Toast.LENGTH_SHORT).show();
                }
                else if(road_txt.length()<=0)
                {
                    Toast.makeText(Add_new_Address_Activity.this,"Please enter the area",Toast.LENGTH_SHORT).show();

                }
                else if(city_txt.length()<=0)
                {
                    Toast.makeText(Add_new_Address_Activity.this,"Please select the city",Toast.LENGTH_SHORT).show();

                }
                else if(state_txt.length()<=0)
                {
                    Toast.makeText(Add_new_Address_Activity.this,"Please enter the state",Toast.LENGTH_SHORT).show();

                }
                else if(pincode_txt.length()<6)
                {
                    Toast.makeText(Add_new_Address_Activity.this,"Please enter the Postal code",Toast.LENGTH_SHORT).show();

                }
                else if(name_txt.length()<=0)
                {
                    Toast.makeText(Add_new_Address_Activity.this,"Please enter the name",Toast.LENGTH_SHORT).show();

                }

            else if(last_name.getText().length()<=0)
            {
                Toast.makeText(Add_new_Address_Activity.this,"Please enter the last name",Toast.LENGTH_SHORT).show();

            }

            else if(mobile_number.length()<10)
                {
                    Toast.makeText(Add_new_Address_Activity.this,"Please enter the mobile number",Toast.LENGTH_SHORT).show();

                }
                else if(address_type==0)
                {
                    Toast.makeText(Add_new_Address_Activity.this,"Please select the address type",Toast.LENGTH_SHORT).show();
                }
                else if(utility.isOnline(Add_new_Address_Activity.this)==false)
                {
                    Toast.makeText(Add_new_Address_Activity.this,"Kindly check your internet connection!",Toast.LENGTH_SHORT).show();
                }
                else
                {

                    if(isedit==2)
                    {
                        update_the_address(id_int,userDetails.getId(),address_type,"nys",name_txt,last_name.getText().toString(),house_txt,road_txt,city_txt,state_txt,pincode_txt,mobile_number,land_txt);

                    }
                    else
                    {
                        save_the_address(userDetails.getId(),address_type,"nys",name_txt,last_name.getText().toString(),house_txt,road_txt,city_txt,state_txt,pincode_txt,mobile_number,land_txt);

                    }
                   // save_the_address(userDetails.getId(),address_type,"nys",name_txt,last_name.getText().toString(),house_txt,road_txt,city_txt,state_txt,pincode_txt,mobile_number,land_txt);
                }
            }
        });

    }

    public void save_the_address(String id,int address_type,String company_name,String first_name,String last_name,String addres1,String address2,String city,String state,String post_code,String phone,String landmark)
    {
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("user_id",id);
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
        RequestQueue queue = Volley.newRequestQueue(Add_new_Address_Activity.this);
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

                                        Toast.makeText(Add_new_Address_Activity.this,"Address added successfully ",Toast.LENGTH_SHORT).show();
Intent in=new Intent(Add_new_Address_Activity.this,My_Adrress.class);
startActivity(in);
finish();


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
              //      mShimmerViewContainer.stopShimmerAnimation();
                //    mShimmerViewContainer.setVisibility(View.GONE);

                    if (progressBar.isShown()) {
                        progressBar.setVisibility(View.GONE);
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



/*
    public void save_the_address(final String id, final int address_type, final String company_name, final String first_name, final String last_name, final String addres1, final String address2, final String city, final String state, final String post_code, final String phone, final String landmark)
    {

        RequestQueue queue = Volley.newRequestQueue(Add_address_before_payment.this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, "http://3.6.27.167/api/add-customer-address",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // response
                        Log.e("Response------------------------->", response);



                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Log.e("Responseerror------------------------->", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("user_id",id);
                hashMap.put("type","SHIPPING");

                if(address_type==1)
                {
                    hashMap.put("address_type","home");


                }
                if(address_type==2)
                {
                    hashMap.put("address_type","work");

                }

                hashMap.put("company_name",company_name);
                hashMap.put("first_name",first_name);
                hashMap.put("last_name",last_name);
                hashMap.put("address1",addres1);
                hashMap.put("address2",address2);
                hashMap.put("city",city);
                hashMap.put("state",state);
                hashMap.put("postcode",post_code);
                hashMap.put("phone",phone);
                hashMap.put("landmark",landmark);

                Log.e("Responseerror------------------------->", hashMap.toString());


                return hashMap;
            }
        };
        postRequest.setShouldCache(false);
        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(postRequest);
    }
*/

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

//set the data from the intent-->
public void set_the_data_from_intent()
{

//    String first_name_int;
//    String last_name_int;
//    String address1_int;
//    String address2_int;
//    String city_int;
//    String zipcode_int;
//    String phone_int;
//    String landmark_int;
//    String type_int;
//    String state_int;
//    String address_type_int;


    //EditText house_text,road_name,land_mark,state,pincode,name,phone_number,last_name;

    house_text.setText(address1_int);
    road_name.setText(address2_int);
    name.setText(first_name_int);
    last_name.setText(last_name_int);
    land_mark.setText(landmark_int);
    pincode.setText(zipcode_int);
    phone_number.setText(phone_int);

    if(address_type_int.equalsIgnoreCase("work"))
    {
        work_address.setChecked(true);
    }
    else if(address_type_int.equalsIgnoreCase("home"))
    {
        home_address.setChecked(true);
    }
    else
    {

    }

    if(city_int.equalsIgnoreCase("CENTRAL DELHI"))
    {
        city.setSelection(1);
    }
    else if(city_int.equalsIgnoreCase("NORTH DELHI"))
    {
        city.setSelection(2);
    }
    else if(city_int.equalsIgnoreCase("SOUTH WEST DELHI"))
    {

        city.setSelection(3);
    }
    else if(city_int.equalsIgnoreCase("WEST DELHI"))
    {
        city.setSelection(4);
    }
    else if(city_int.equalsIgnoreCase("NEW DELHI"))
    {
        city.setSelection(5);
    }
    else if(city_int.equalsIgnoreCase("EAST DELHI"))
    {
        city.setSelection(6);
    }

}


    public void update_the_address(String address_id,String id,int address_type,String company_name,String first_name,String last_name,String addres1,String address2,String city,String state,String post_code,String phone,String landmark)
    {
        HashMap<String,String> hashMap = new HashMap<>();

        hashMap.put("address_id",address_id);
        hashMap.put("user_id",id);
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
        RequestQueue queue = Volley.newRequestQueue(Add_new_Address_Activity.this);
        String url = "http://3.6.27.167/api/update-customer-address";
        progressBar.setVisibility(View.VISIBLE);

        JsonObjectRequest postRequest = null;
        try {
            postRequest = new JsonObjectRequest(url, new JSONObject(jsno_data),
                    new Response.Listener<JSONObject>()
                    {
                        @Override
                        public void onResponse(JSONObject response)
                        {

                            Log.e("responce_status",response.toString());

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

                                            Toast.makeText(Add_new_Address_Activity.this,"Address added successfully ",Toast.LENGTH_SHORT).show();
                                            Intent in=new Intent(Add_new_Address_Activity.this,My_Adrress.class);
                                            startActivity(in);
                                            finish();


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
                    //      mShimmerViewContainer.stopShimmerAnimation();
                    //    mShimmerViewContainer.setVisibility(View.GONE);

                    if (progressBar.isShown()) {
                        progressBar.setVisibility(View.GONE);
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


}
