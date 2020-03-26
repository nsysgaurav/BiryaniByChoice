package com.example.test.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alimuzaffar.lib.pin.PinEntryEditText;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.test.Constants.Cons;
import com.example.test.Internet_connection.volley_for_get_category;
import com.example.test.Model.UserDetails;
import com.example.test.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Activity_otp_verify_registration extends AppCompatActivity {

    Context mContext;
    PinEntryEditText pinEntry;
    LinearLayout next;
    TextView mob;
    String otpByUser, mobile_number, callFrom;
    ProgressBar progress_bar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verify_registration);
        mContext = Activity_otp_verify_registration.this;
        getId();
    }

    private void getId() {

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {//added by Lalit kumar on 20.march.2020 at 16:46
            mobile_number = bundle.getString("mobile_number");
            callFrom = bundle.getString("call_from");
        }

        pinEntry = (PinEntryEditText) findViewById(R.id.txt_pin_entrys);
        next = (LinearLayout) findViewById(R.id.next);
        mob = (TextView) findViewById(R.id.mob);
        progress_bar = (ProgressBar) findViewById(R.id.progress_bar);
        mob.setText(mobile_number);

        pinEntry.setOnPinEnteredListener(new PinEntryEditText.OnPinEnteredListener() {
            @Override
            public void onPinEntered(CharSequence str) {
                otpByUser = str.toString();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(pinEntry.getWindowToken(), 0);
            }
        });


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callFrom.equalsIgnoreCase(Cons.call_from_register)) {
                    if (otpByUser != null && otpByUser.length() == 6) {
                        verify_otp(mobile_number, otpByUser);
                    } else {

                        Toast.makeText(Activity_otp_verify_registration.this, "Please enter the valid otp", Toast.LENGTH_LONG).show();
                    }
                } else if (callFrom.equalsIgnoreCase(Cons.call_from_login)) {
                    if (otpByUser != null && otpByUser.length() == 6) {
                        login_verify_otp(mobile_number, otpByUser);
                    } else {

                        Toast.makeText(Activity_otp_verify_registration.this, "Please enter the valid otp", Toast.LENGTH_LONG).show();
                    }
                }

            }

        });
    }

    public void verify_otp(final String mobile_number, final String otpByUser) {

        progress_bar.setVisibility(View.VISIBLE);

        RequestQueue queue = Volley.newRequestQueue(Activity_otp_verify_registration.this);

        String url = "http://3.6.27.167/api/user/verify-otp";

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        if (progress_bar.isShown()) {
                            progress_bar.setVisibility(View.GONE);
                        }
                        Log.e("verify_Response", response);

                        if (response != null) {

                            try {
                                JSONObject object = new JSONObject(response.toString());

                                if (object.has("status")) {
                                    if (object.getString("status").equalsIgnoreCase("success")) {


                                        Intent in = new Intent(Activity_otp_verify_registration.this, UserProfile.class);
                                        in.putExtra("mobile_number", mobile_number);
                                        startActivity(in);
                                        finish();


                                        Toast.makeText(Activity_otp_verify_registration.this, object.getString("msg"), Toast.LENGTH_SHORT).show();

                                    } else {
                                        Toast.makeText(Activity_otp_verify_registration.this, object.getString("msg"), Toast.LENGTH_SHORT).show();
                                    }
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                                if (progress_bar.isShown()) {
                                    progress_bar.setVisibility(View.GONE);
                                }
                            }


                        } else {


                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        if (progress_bar.isShown()) {
                            progress_bar.setVisibility(View.GONE);
                        }
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("mobile_number", mobile_number);
                params.put("otp", otpByUser);

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

    public void login_verify_otp(final String mobile_number, final String otpByUser) {


        progress_bar.setVisibility(View.VISIBLE);
        RequestQueue queue = Volley.newRequestQueue(Activity_otp_verify_registration.this);


        StringRequest postRequest = new StringRequest(Request.Method.POST, Cons.login_otp,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.e("verify_Response", response);
                        if (progress_bar.isShown()) {
                            progress_bar.setVisibility(View.GONE);
                        }
                        if (response != null) {

                            try {
                                JSONObject object = new JSONObject(response.toString());

                                if (object.has("status")) {
                                    if (object.getString("status").equalsIgnoreCase("success")) {

                                        JSONObject user_info = object.getJSONObject("user_info");

                                        if (user_info != null) {

                                            UserDetails userDetails = new UserDetails();
                                            if (user_info.has("name")) {
                                                userDetails.setUsername(user_info.getString("name"));
                                            }
                                            if (user_info.has("email")) {
                                                userDetails.setEmail(user_info.getString("email"));
                                            }
                                            if (user_info.has("mobile_number")) {
                                                userDetails.setMobileNumber(user_info.getString("mobile_number"));
                                            }
                                            if (user_info.has("id")) {
                                                userDetails.setId(user_info.getString("id"));
                                            }
                                            if (user_info.has("gender")) {
                                                userDetails.setGender(user_info.getString("gender"));
                                            }
                                            if (user_info.has("dob")) {
                                                userDetails.setDob(user_info.getString("dob"));
                                            }
                                            if (user_info.has("pin_code")) {
                                                userDetails.setPincode(user_info.getString("pin_code"));
                                            }
                                            if (user_info.has("remember_token")) {
                                                userDetails.setRemeber_token(user_info.getString("remember_token"));
                                            }

                                            userDetails.setLogin(true);

                                            Log.d("userDetails", userDetails.toString());
                                            SessionManager sessionManager = new SessionManager(Activity_otp_verify_registration.this);
                                            sessionManager.createUserSession(userDetails);

                                            get_cat();
                                        }




                                        Toast.makeText(Activity_otp_verify_registration.this, object.getString("msg"), Toast.LENGTH_SHORT).show();

                                    } else {
                                        Toast.makeText(Activity_otp_verify_registration.this, object.getString("msg"), Toast.LENGTH_SHORT).show();
                                    }
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                                if (progress_bar.isShown()) {
                                    progress_bar.setVisibility(View.GONE);
                                }
                            }


                        } else {


                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        if (progress_bar.isShown()) {
                            progress_bar.setVisibility(View.GONE);
                        }
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("mobile_number", mobile_number);
                params.put("otp", otpByUser);

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


    public void get_cat()
    {
        volley_for_get_category obj_cat=new volley_for_get_category();
        obj_cat.get_all_category(Activity_otp_verify_registration.this);

    }

}