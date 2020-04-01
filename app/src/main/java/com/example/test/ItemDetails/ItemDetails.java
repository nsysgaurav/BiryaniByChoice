package com.example.test.ItemDetails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.test.BAckgrounddata.GetData;
import com.example.test.Model.FoodModel;
import com.example.test.R;

public class ItemDetails extends AppCompatActivity {
    Toolbar toolbar;
    ImageView foodImage;
    TextView foodTitle,foodDesc,foodPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);



        toolbar =findViewById(R.id.toolbar);
        foodImage =findViewById(R.id.food_image);
        foodTitle =findViewById(R.id.food_title);
        foodDesc =findViewById(R.id.food_desc);
        foodPrice = findViewById(R.id.food_price);


        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Bundle bundle=getIntent().getExtras();
        toolbar.setTitle(bundle.getString("name"));
        foodTitle.setText(bundle.getString("name"));
        foodDesc.setText(bundle.getString("desc"));
        foodPrice.setText(bundle.getString("price"));




    }
}
