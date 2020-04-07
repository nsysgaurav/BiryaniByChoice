
package com.example.test.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.test.Internet_connection.volley_for_get_category;
import com.example.test.R;
import com.example.test.Sqldirectory.DatabaseHelper;

public class Payment_Success_Activity extends AppCompatActivity {


   TextView order_number,amount;
   Intent intent;
   TextView track_your_order;
   ProgressBar progress_bar;
   LinearLayout linear_visible;
    String Order_id;
    SessionManager manager;
    Toolbar toolbar;
    DatabaseHelper data_base;

    @Override
    public void onBackPressed() {
       // super.onBackPressed();

       // Intent in=new Intent(Payment_Success_Activity.this,Home_Screen.class);
        //startActivity(in);
        //finish();
        linear_visible.setVisibility(View.GONE);
        progress_bar.setVisibility(View.VISIBLE);
        get_cat();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment__success);
        toolbar = (Toolbar) findViewById(R.id.my_address_toolbar);
        track_your_order=findViewById(R.id.track_your_order);
        order_number=findViewById(R.id.order_number);
        linear_visible=findViewById(R.id.linear_visible);
        progress_bar=findViewById(R.id.progress_bar);
        manager = new SessionManager(this);
        data_base = new DatabaseHelper(this);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                get_cat();            }
        });



        intent=getIntent();
        if(intent!=null)
        {
            String order_no=intent.getStringExtra("Order_number");
            String order_amount=intent.getStringExtra("Total_Amount");
             Order_id=intent.getStringExtra("Order_id");
             order_number.setText(order_no);
          //  amount.setText(order_amount);
        }

        track_your_order.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try
                {
                    data_base.delet_database();
                    Intent in=new Intent(Payment_Success_Activity.this, Order_Details_Tracking_Activity.class);
                    in.putExtra("user_id",manager.getUserSession().getId());
                    in.putExtra("order_id",Order_id);
                    in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(in);
                    finish();

                }
                catch (Exception e)
                {

                }


            }
        });

    }


    public void get_cat()
    {
        volley_for_get_category obj_cat=new volley_for_get_category();
        obj_cat.get_all_category(Payment_Success_Activity.this,progress_bar);

    }




}
