package com.example.test.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.test.Constants.Cons;
import com.example.test.R;

import org.json.JSONException;
import org.json.JSONObject;
//Added by lalit kumar on 20.march.2020 at 16:35 PM
import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    TextView send_otp;
    Button login_btn;
    int counter;
    EditText enter_otp, enter_mob;
    CountDownTimer countDownTimer;

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        finish();
    }

    TextView go_to_sign_up;
    Button login_button;
    ProgressBar progressBar;
    LinearLayout clear_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();

        go_to_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Login.this, Signup.class);
                startActivity(in);
                finish();

            }
        });

    }

    public void init() {

        go_to_sign_up = (TextView) findViewById(R.id.go_to_sign_up);
        login_button = (Button) findViewById(R.id.login_btn);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        enter_mob = (EditText) findViewById(R.id.enter_mob);
        clear_button=(LinearLayout)findViewById(R.id.clear_button);

        //added  by Lalit kumar on 20.march.2020 at 16:52 PM

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(enter_mob.getText().toString().trim())) {
                    enter_mob.requestFocus();
                    enter_mob.setError("Please enter your mobile Number");
                } else {
                    login_user(enter_mob.getText().toString().trim());
                }

            }
        });


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
//added  by Lalit kumar on 20.march.2020 at 16:52 PM
    public void login_user(final String mobile_number) {

        progressBar.setVisibility(View.VISIBLE);
        RequestQueue queue = Volley.newRequestQueue(Login.this);
        StringRequest postRequest = new StringRequest(Request.Method.POST, Cons.login_url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // response
                        Log.e("Response------------------------->", response);
                        if (progressBar.isShown()) {
                            progressBar.setVisibility(View.GONE);
                        }
                        if (response != null) {
                            try {
                                JSONObject object = new JSONObject(response.toString());
                                String status = object.getString("status");
                                if (status.equalsIgnoreCase("success")) {

                                    Intent in = new Intent(Login.this, Activity_otp_verify_registration.class);
                                    in.putExtra("mobile_number", mobile_number);
                                    in.putExtra("call_from", Cons.call_from_login);
                                    startActivity(in);
                                    finish();

                                } else {


                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                                if (progressBar.isShown()) {
                                    progressBar.setVisibility(View.GONE);
                                }
                            }
                        } else {

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (progressBar.isShown()) {
                            progressBar.setVisibility(View.GONE);
                        }
                        // error

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

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
