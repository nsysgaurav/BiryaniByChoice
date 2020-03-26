package com.example.test.ViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.Model.MyOrdersModel;
import com.example.test.R;

import java.util.ArrayList;

public class MyordersAdapter extends RecyclerView.Adapter<MyordersAdapter.Viewholder> {
    ArrayList<MyOrdersModel> myOrdersModels;
View view;

    public MyordersAdapter(ArrayList<MyOrdersModel> myOrdersModels) {
        this.myOrdersModels = myOrdersModels;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        view =inflater.inflate(R.layout.my_orders_card,parent,false);

        Viewholder holder = new Viewholder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final Viewholder holder, int position) {
        //holder.title.setText("My Order");
       holder.title.setText(myOrdersModels.get(position).getOrder_date()+" "+myOrdersModels.get(position).getOrder_time());
        holder.order_count.setText(myOrdersModels.get(position).getOrder_item_count()+" item(s) ordered");
        holder.price.setText(String.valueOf("Rs "+myOrdersModels.get(position).getOrder_price()));
        holder.repeat_order_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             Toast.makeText(v.getContext(), "repeating", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return myOrdersModels.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView title,order_count,price;
        Button repeat_order_btn;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.my_oder_title);
            order_count=itemView.findViewById(R.id.my_orders_items);
            price=itemView.findViewById(R.id.my_oder_price);
            repeat_order_btn=itemView.findViewById(R.id.repeat_order_btn);


        }
    }
}





