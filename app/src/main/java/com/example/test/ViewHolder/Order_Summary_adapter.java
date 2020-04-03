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

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.test.Model.Cat_data;
import com.example.test.Model.UserDetails;
import com.example.test.R;
import com.example.test.Sqldirectory.DatabaseHelper;
import com.example.test.activity.SessionManager;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Order_Summary_adapter extends RecyclerView.Adapter<Order_Summary_adapter.CartViewHolder> {


    View view;
    ArrayList<HashMap<String,String>> cart_map;
    Context context;
    DatabaseHelper data_base;
    SessionManager sessionManager;
    UserDetails userDetails;
    String total_amount;
    NestedScrollView scroll_view;

    public Order_Summary_adapter(Context context, ArrayList<HashMap<String,String>> cart_map,NestedScrollView scroll_view)
    {
        this.context = context;
        this. scroll_view=scroll_view;
        this.cart_map = cart_map;
        sessionManager = new SessionManager(context);
        data_base = new DatabaseHelper(context);
        userDetails = sessionManager.getUserSession();

    }


    public void updateData(ArrayList<HashMap<String,String>> cart_map1)
    {
        cart_map.clear();
        cart_map.addAll(cart_map1);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.order_summry,parent,false);

        CartViewHolder holder = new CartViewHolder(view);


        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CartViewHolder holder, final int position)
    {
        try
        {
            final int[] quintity = {0};

            final String cat_id  =  cart_map.get(position).get("cat_id");
            final String product_cat_id  =  cart_map.get(position).get("product_cat_id");
            holder.itemName.setText(cart_map.get(position).get("product_name"));
            holder.itemquantity.setText("Qty: "+cart_map.get(position).get("qty"));
            holder.itemPrice.setText("₹"+(int)Double.parseDouble(cart_map.get(position).get("total_amount")));
            final String product_id = cart_map.get(position).get("product_id");
            final String qty = cart_map.get(position).get("qty");
            quintity[0]=Integer.parseInt(cart_map.get(position).get("qty"));

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


        TextView itemName ,itemquantity ,itemPrice;
        ImageButton btn_minus,btn_pluss;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.cart_name);
            itemquantity = itemView.findViewById(R.id.cart_quantity);
            itemPrice = itemView.findViewById(R.id.cart_price);
            btn_minus= itemView.findViewById(R.id.btnminus);
            btn_pluss = itemView.findViewById(R.id.btnplus);
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


}
