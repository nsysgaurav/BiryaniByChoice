package com.example.test.OrderCart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.test.Model.MyOrdersModel;
import com.example.test.R;
import com.example.test.ViewHolder.MyordersAdapter;

import java.util.ArrayList;

public class Myorders extends AppCompatActivity {

Toolbar mytoolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myorders);
        mytoolbar = findViewById(R.id.my_order_toolbar);
        mytoolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        mytoolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        ArrayList<MyOrdersModel> myOrdersModels=new ArrayList<>();
        MyOrdersModel myOrdersModel=new MyOrdersModel();
        myOrdersModel.setOrder_date("21/02/2020");
        myOrdersModel.setOrder_item_count(2);
        myOrdersModel.setOrder_price(200);
        myOrdersModel.setOrder_time("11:20");

        MyOrdersModel myOrdersModel1=new MyOrdersModel();
        myOrdersModel1.setOrder_time("14:22");
        myOrdersModel1.setOrder_price(199);
        myOrdersModel1.setOrder_item_count(4);
        myOrdersModel1.setOrder_date("22/01/2020");

        myOrdersModels.add(myOrdersModel);
        myOrdersModels.add(myOrdersModel1);

        RecyclerView recyclerView = findViewById(R.id.Myordersrecyclerview);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(new MyordersAdapter(myOrdersModels));


    }
}
