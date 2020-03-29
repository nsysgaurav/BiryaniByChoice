package com.example.test.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.test.Internet_connection.volley_for_get_category;
import com.example.test.Model.UserDetails;
import com.example.test.R;
import com.example.test.util.utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;


public class UserProfile extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_CAMERA = 101;
    private static final String CAMERA_IMAGE_NAME = "PROFILE_IMAGE.png";
    private static final int REQUEST_CthoghorODE_CAPthoghorTURE = 1;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int SELECT_PICTURE = 2;
    private static final int RESULT_LOAD_IMAGE = 5;
    private String imagePath;

    private ProgressBar progressBar;
    private EditText nameTxt;
    private EditText emailTxt;
    private TextView careOfTxt;
    private EditText mobileNoTxt;
    // private EditText aadhaarNoTxt;
    private TextView dobTxt;
    private TextView genderTxt;
    private EditText pinCodeTxt;
    private TextView postOfficeTxt;
    private TextView streetTxt;
    private TextView landmarkTxt;
    private TextView localityTxt;
    private EditText stateTxt;
    private TextView districtTxt;
    private TextView subDistrictTxt;
    private TextView villageTxt;
    private TextView accountStatusTxt;
    private TextView createdAtTxt;
    private TextView updatedAtTxt;
    private String profileImageURL;
    private SharedPreferences.Editor editor;
    private Uri uri;
    private Bitmap bitmap;
    private String encodedImage;

    private File mediaStorageDir;
    String callFrom;
    // CardView updateCard;
    int counter = 0;
    EditText et1, et2, et3, et4, et5, et6;
    TextView mob;

    private static ImageView fullImage;
    static TextView nameText;
    private boolean fileUri;
    EditText relativeName1, relativePhoneNumber1, relation1, relativeName2, relativePhoneNumber2, relation2, relativeName3, relativePhoneNumber3, relation3, relativeName4, relativePhoneNumber4, relation4;
    LinearLayout relative2, relative3, relative4;
    ImageView up2, up3, up4;
    private boolean notUpdated = false;
    private boolean fourthUpdated = false;
    private boolean firstUpdated;
    private boolean secondUpdated;
    private boolean thirdUpdated;
    ImageView editRelative1, deleteRelative1, editRelative2, deleteRelative2, editRelative3, deleteRelative3, editRelative4, deleteRelative4, editPersonalInfo;

    String relativeId1, relativeId2, relativeId3, relativeId4, stateValue, stateSavedValue;
    Boolean updatePersonalInfoStatus = false;
    LinearLayout editControl;
    Spinner state;
    String[] stateList;
    LinearLayout stateSpinnerLL;
    int statePosition;
    static TextView myRCCount1;
    static TextView myDLCount1;
    static TextView sharedRCCount1;
    static TextView receivedRCCount1;
    private TextView shortName;

    Button sign_up;

    String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
    Intent intent;
    Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        toolbar = findViewById(R.id.toolbar);


        intent = getIntent();
        init();

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name_string = nameTxt.getText().toString();
                String email_tring = emailTxt.getText().toString();
                String mobile_string = mobileNoTxt.getText().toString();

                if (name_string.length() < 4) {

                } else if (email_tring.length() == 0) {

                } else if (utility.isOnline(UserProfile.this) == false) {

                } else {
                    REGISTRED_USER(name_string, email_tring, mobile_string);
                }


            }
        });


    }


    public void init() {
        nameTxt = (EditText) findViewById(R.id.name_txt);
        emailTxt = (EditText) findViewById(R.id.email_et);
        mobileNoTxt = (EditText) findViewById(R.id.mobile_no_txt);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        sign_up = (Button) findViewById(R.id.sign_up);

        if (intent != null) {
            mobileNoTxt.setText(intent.getStringExtra("mobile_number"));
        }

    }


    //sign_up

    //Added by Lalit kumar on 20.march.2020 at 01:PM
    public void REGISTRED_USER(final String name, final String email, final String mobile_number) {

        RequestQueue queue = Volley.newRequestQueue(UserProfile.this);
        progressBar.setVisibility(View.VISIBLE);
        String url = "http://3.6.27.167/api/user/user-register";

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.e("verify_Response", response);
                        if (progressBar.isShown()) {
                            progressBar.setVisibility(View.GONE);
                        }

                        if (response != null) {
                            try {
                                JSONObject object = new JSONObject(response.toString());
                                Log.d("object", object.toString());
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
                                            SessionManager sessionManager = new SessionManager(UserProfile.this);
                                            sessionManager.createUserSession(userDetails);

                                            get_cat();
                                        }

                                    } else {
                                        Toast.makeText(UserProfile.this, object.getString("msg"), Toast.LENGTH_SHORT).show();
                                    }
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
                        // error
                        //added by Lalit kumar on 20.march.2020 at 2:00 PM
                        if (progressBar.isShown()) {
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("mobile_number", mobile_number);
                params.put("name", name);
                params.put("email", email);


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

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public void onBackPressed() {
        goToBack();
    }

    private void goToBack() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                goToBack();
                break;
        }
        return true;
    }


    public void get_cat()
    {
        volley_for_get_category obj_cat=new volley_for_get_category();
        obj_cat.get_all_category(UserProfile.this,progressBar);
    }

}
