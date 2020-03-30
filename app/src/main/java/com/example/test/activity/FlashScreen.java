package com.example.test.activity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.test.Internet_connection.volley_for_get_category;
import com.example.test.R;

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

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if(ActivityCompat.checkSelfPermission(FlashScreen.this,ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(FlashScreen.this, new String[]{ACCESS_FINE_LOCATION},1);
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
        progressBar.setBackgroundColor(getColor(R.color.colorPrimary2));
        volley_for_get_category obj_cat=new volley_for_get_category();
        obj_cat.get_all_category(FlashScreen.this,progressBar);

    }


}

