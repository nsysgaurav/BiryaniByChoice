package com.example.test.ViewHolder;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.Model.FoodModel;
import com.example.test.Model.NonVegBiryani;
import com.example.test.OrderCart.Cart;
import com.example.test.R;
import com.example.test.Sqldirectory.CartLitedb;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class BiryaniAdapter extends RecyclerView.Adapter<BiryaniAdapter.ViewHolder> {
ArrayList<FoodModel> biryani;
    View view;

    public BiryaniAdapter(ArrayList<FoodModel> biryaniList) {

        biryani = biryaniList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
      view = inflater.inflate(R.layout.biryanicards,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

         holder.dishName.setText(biryani.get(position).getFoodName());
        holder.description.setText(biryani.get(position).getFoodCat());
        holder.price.setText(String.valueOf(biryani.get(position).getFoodPrice())+" \u20B9");

        try {
            holder.dishimage.setImageBitmap(BitmapFactory.decodeByteArray(biryani.get(position).getImage(),0,biryani.get(position).getImage().length));
        }catch (Exception e){}


        final CartLitedb  cartLitedb = new CartLitedb(view.getContext());

holder.additem.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        holder.additem.setEnabled(false);
        holder.additem.setText("Added");

        cartLitedb.insertdata(biryani.get(position).getFoodName(),
                biryani.get(position).getFoodPrice()
                ,1,null,biryani.get(position).getFood_imag_url());


            }
});

    }

    @Override
    public int getItemCount() {
       return biryani.size();
//    return 0;
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{
         TextView dishName,description,price;
         ImageView dishimage;
         Button additem;
    public ViewHolder(@NonNull View itemView) {
        super(itemView);
         dishName = itemView.findViewById(R.id.dishname);
        dishimage =itemView.findViewById(R.id.dishImage);
        description =itemView.findViewById(R.id.description);
        price =itemView.findViewById(R.id.price);
        additem = itemView.findViewById(R.id.addbtn);

    }

    }
}
