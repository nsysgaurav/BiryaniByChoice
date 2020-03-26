package com.example.test.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.test.Constants.Cons;
import com.example.test.R;
import com.example.test.util.utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Signup extends AppCompatActivity {

    TextView username, password, otp;
    Button signupBtn;

    TextView go_to_sign_in;
    EditText enter_mob;
    Button login_btn;
    LinearLayout clear_button;


    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        init();

        go_to_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Signup.this, Login.class);
                startActivity(in);
                finish();
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mobile_number = enter_mob.getText().toString().trim();

                if (mobile_number == null && mobile_number.length() == 0) {
                    Toast.makeText(Signup.this, "Please enter the mobile number", Toast.LENGTH_LONG).show();
                } else if (mobile_number.length() < 10) {
                    Toast.makeText(Signup.this, "Please enter the valid mobile number", Toast.LENGTH_LONG).show();
                } else if (utility.isOnline(Signup.this) == false) {
                    Toast.makeText(Signup.this, "Please check your internet connection!", Toast.LENGTH_LONG).show();
                } else {


                    register_the_user(mobile_number);


                }


            }
        });


    }

    public void init() {
        go_to_sign_in = (TextView) findViewById(R.id.go_to_sign_in);
        enter_mob = (EditText) findViewById(R.id.enter_mob);
        login_btn = (Button) findViewById(R.id.login_btn);
        clear_button=(LinearLayout)findViewById(R.id.clear_button);

        clear_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(enter_mob.length()>0)
                {
                    enter_mob.setText("");

                }


            }
        });
    }


    public void register_the_user(final String mobile_number) {

        RequestQueue queue = Volley.newRequestQueue(Signup.this);
        String url = "http://3.6.27.167/api/user/check-registrationv1";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // response
                        Log.e("Response------------------------->", response);

                        if (response != null) {
                            try {
                                JSONObject object = new JSONObject(response.toString());
                                String status = object.getString("status");
                                if (status.equalsIgnoreCase("success")) {


                                    Intent in = new Intent(Signup.this, Activity_otp_verify_registration.class);
                                    in.putExtra("mobile_number", mobile_number);
                                    in.putExtra("call_from", Cons.call_from_register);
                                    startActivity(in);
                                    finish();


                                }
                                else if(status.equalsIgnoreCase("error"))
                                {

                    Toast.makeText(Signup.this,object.getString("msg"),Toast.LENGTH_LONG).show();

                                }
                                else
                                {
                                    Toast.makeText(Signup.this,"Kindly try after some time!",Toast.LENGTH_LONG).show();

                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("nsyskey", "082057a4d249514389bb90c6d50ecf23");
                params.put("mobile_number", mobile_number);

                return params;
            }
        };
        postRequest.setShouldCache(false);
        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(postRequest);
    }
}
