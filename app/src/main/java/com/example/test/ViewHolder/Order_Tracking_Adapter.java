package com.example.test.ViewHolder;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.Model.UserDetails;
import com.example.test.R;
import com.example.test.Sqldirectory.DatabaseHelper;
import com.example.test.activity.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;

public class Order_Tracking_Adapter extends RecyclerView.Adapter<Order_Tracking_Adapter.CartViewHolder> {


    View view;
    ArrayList<HashMap<String,String>> cart_map;
    Context context;
    DatabaseHelper data_base;
    SessionManager sessionManager;
    UserDetails userDetails;
    String total_amount;
    NestedScrollView scroll_view;

    public Order_Tracking_Adapter(Context context, ArrayList<HashMap<String,String>> cart_map, NestedScrollView scroll_view)
    {
        this.context = context;
        this. scroll_view=scroll_view;
        this.cart_map = cart_map;
        sessionManager = new SessionManager(context);
        data_base = new DatabaseHelper(context);
        userDetails = sessionManager.getUserSession();

    }




    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.order_tracking_item,parent,false);

        CartViewHolder holder = new CartViewHolder(view);


        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CartViewHolder holder, final int position)
    {
        try
        {
            final String quantity  =  cart_map.get(position).get("qty");
            final String price  =  cart_map.get(position).get("price");
            holder.itemName.setText(cart_map.get(position).get("product_name"));
            holder.itemquantity.setText("X "+quantity);

            holder.itemPrice.setText("₹"+get_total_amount(Integer.parseInt(quantity),(int)Double.parseDouble(price)));


        }
        catch (Exception e)
        {
     Log.e("error",e.toString());
        }

    }

    @Override
    public int getItemCount()
    {
        return cart_map.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder{


        TextView itemName ,itemquantity ,itemPrice;
        ImageButton btn_minus,btn_pluss;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.cart_name);
            itemquantity = itemView.findViewById(R.id.cart_quantity);
            itemPrice = itemView.findViewById(R.id.cart_price);
            }
    }



    public ArrayList<HashMap<String,String>> get_produt_deails(String product_id,String quintity)
    {
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
        list.clear();
        HashMap<String,String> map=new HashMap<>();
        map.clear();
        map.put("product_id",product_id);
        map.put("qty",quintity);
        list.add(map);

        return  list;
    }

    public String get_total_amount(int quntity, int price )
    {
        int amout_total;
        try {
            amout_total = quntity*price;
        }
        catch (Exception e)
        {
            return Integer.toString(0);
        }

        return Integer.toString(amout_total);
    }



}
