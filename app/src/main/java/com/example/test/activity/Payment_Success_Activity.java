
package com.example.test.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.test.Internet_connection.volley_for_get_category;
import com.example.test.R;

public class Payment_Success_Activity extends AppCompatActivity {


   TextView order_number,amount;
   Intent intent;
   TextView track_your_order;
   ProgressBar progress_bar;

    @Override
    public void onBackPressed() {
       // super.onBackPressed();

       // Intent in=new Intent(Payment_Success_Activity.this,Home_Screen.class);
        //startActivity(in);
        //finish();
        get_cat();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment__success);
        order_number=findViewById(R.id.order_number);
        amount=findViewById(R.id.amount);
        progress_bar=findViewById(R.id.progress_bar);
        track_your_order=findViewById(R.id.track_your_order);


        intent=getIntent();
        if(intent!=null)
        {
            String order_no=intent.getStringExtra("Order_number");
            String order_amount=intent.getStringExtra("Total_Amount");
            order_number.setText(order_no);
            amount.setText(order_amount);
        }

        track_your_order.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                get_cat();
            }
        });

    }


    public void get_cat()
    {
        volley_for_get_category obj_cat=new volley_for_get_category();
        obj_cat.get_all_category(Payment_Success_Activity.this,progress_bar);

    }




}
