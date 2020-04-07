package com.example.test.activity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.test.Back_Ground_Services.Job_intent_Service_for_update_the_cart_data;
import com.example.test.Internet_connection.volley_for_get_category;
import com.example.test.R;
import com.example.test.util.utility;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
public class FlashScreen extends AppCompatActivity {

    Button login ,signup;
    TextView text;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_screen);

        text = findViewById(R.id.text);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.getIndeterminateDrawable().setColorFilter(getColor(R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        /*if(ActivityCompat.checkSelfPermission(FlashScreen.this,ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(FlashScreen.this, new String[]{ACCESS_FINE_LOCATION},1);
        }
*/

        if(utility.isOnline(FlashScreen.this))
        {
            Intent mIntent = new Intent(this, Job_intent_Service_for_update_the_cart_data.class);
            Job_intent_Service_for_update_the_cart_data.enqueueWork(this, mIntent);

        }

try {
    get_cat();
    }
catch (Exception e)
{

}

Handler handler = new Handler();

handler.postDelayed(new Runnable() {
    @Override
    public void run() {
        finish();
    }
},4000);


    }


    public void get_cat()
    {
        progressBar.setVisibility(View.VISIBLE);
        volley_for_get_category obj_cat=new volley_for_get_category();
        obj_cat.get_all_category(FlashScreen.this,progressBar);

    }


}

