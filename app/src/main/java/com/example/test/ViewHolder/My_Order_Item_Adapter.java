package com.example.test.ViewHolder;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.test.R;
import com.example.test.Sqldirectory.DatabaseHelper;
import com.example.test.activity.Add_new_Address_Activity;
import com.example.test.activity.Order_Details_Tracking_Activity;
import com.example.test.activity.SessionManager;
import com.example.test.util.utility;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class My_Order_Item_Adapter extends RecyclerView.Adapter<My_Order_Item_Adapter.CartViewHolder> {


    View view;
    ArrayList<HashMap<String,String>> cart_map;
    Context context;
    DatabaseHelper data_base;
    SessionManager sessionManager;




    public My_Order_Item_Adapter(Context context, ArrayList<HashMap<String,String>> cart_map )
    {
        this.context = context;
        this.cart_map = cart_map;

    }
    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.order_item_details,parent,false);

        CartViewHolder holder = new CartViewHolder(view);


        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CartViewHolder holder, final int position)
    {

        try
        {
            final String id=cart_map.get(position).get("id");
            final String shipping_option=cart_map.get(position).get("shipping_option");
            final String payment_option=cart_map.get(position).get("payment_option");
            final String order_num=cart_map.get(position).get("order_num");
            final String user_id=cart_map.get(position).get("user_id");
            final String shipping_address_id=cart_map.get(position).get("shipping_address_id");
            final String billing_address_id=cart_map.get(position).get("billing_address_id");
            final String shipping_amount=cart_map.get(position).get("shipping_amount");
            final String tax_amount=cart_map.get(position).get("tax_amount");
            final String total_amount=cart_map.get(position).get("total_amount");
            final String created_at=cart_map.get(position).get("created_at");
            final String order_status_text=cart_map.get(position).get("order_status_text");


            holder.order_number.setText(order_num);
            holder.date.setText(utility.get_date_string_format(created_at));
            holder.amount.setText("₹"+total_amount);
            holder.order_statu.setText(order_status_text);
            holder.payment_mode.setText(payment_option);

            holder.track_your_order.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {

                    Intent in=new Intent(context, Order_Details_Tracking_Activity.class);
                    in.putExtra("user_id",user_id);
                    in.putExtra("order_id",id);
                    context.startActivity(in);


                }
            });


        }
        catch (Exception e)
        {

        }


    }

    @Override
    public int getItemCount()
    {
        return cart_map.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder{


        TextView order_number,date,amount,order_statu,payment_mode,track_your_order;
        ImageButton btn_minus,btn_pluss;

        public CartViewHolder(@NonNull View itemView)
        {
            super(itemView);

            order_number = itemView.findViewById(R.id.order_number);
            date = itemView.findViewById(R.id.date);
            amount = itemView.findViewById(R.id.amount);
            order_statu = itemView.findViewById(R.id.order_status);
            payment_mode = itemView.findViewById(R.id.payment_mode);
           track_your_order = itemView.findViewById(R.id.track_your_order);
//            remove = itemView.findViewById(R.id.remove);

                   }
    }






}
