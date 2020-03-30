package com.example.test.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.test.OrderCart.Myorders;
import com.example.test.R;

public class Customer_Profile extends AppCompatActivity {
TextView name ,review ,pohto;
Button myorders ,edit;
ImageView profilePic;
Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer__profile);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        name= findViewById(R.id.customername);
        review =findViewById(R.id.Reviews);
        pohto = findViewById(R.id.photos);
        myorders = findViewById(R.id.btn_myOrders);
        edit = findViewById(R.id.editProfile);
      /*  profilePic = findViewById(R.id.prodilepic);*/
        toolbar = findViewById(R.id.customer_profile_toolbar);

        Bundle bundle= getIntent().getExtras();

        name.setText(bundle.getString("user_name"));

        myorders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            startActivity(new Intent(Customer_Profile.this, Myorders.class));

            }
        });

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Customer_Profile.this,Edit_Customer_Profile.class));
            }
        });

    }
}
