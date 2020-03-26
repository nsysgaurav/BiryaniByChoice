package com.example.test.ViewHolder;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.test.Model.CartModal;
import com.example.test.R;
import com.example.test.Sqldirectory.CartLitedb;

import java.util.ArrayList;

public class Cart_layoutAdapter extends RecyclerView.Adapter<Cart_layoutAdapter.CartViewHolder> {
    View view;
 ArrayList<CartModal> cartModalArrayList;
 TextView items_total;



    public Cart_layoutAdapter(ArrayList<CartModal> cartModalArrayList, TextView items_total) {
        this.cartModalArrayList = cartModalArrayList;
        this.items_total = items_total;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
       view = inflater.inflate(R.layout.cart_layout,parent,false);

        CartViewHolder holder = new CartViewHolder(view);


        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CartViewHolder holder, final int position) {
   final CounterClass[] counterClasses=new CounterClass[cartModalArrayList.size()];
   counterClasses[position]=new CounterClass();
   counterClasses[position].setCount(1);



       holder.itemName.setText(cartModalArrayList.get(position).getCart_item_name());

       holder.itemquantity.setText((String.valueOf(cartModalArrayList.get(position).getQuantity())));

        final CounterClass counterClass = new CounterClass();

        counterClass.setCount(cartModalArrayList.get(position).getQuantity());


        holder.itemPrice.setText(String.valueOf(cartModalArrayList.get(position).getCart_item_price())+" \u20B9");

        //new DownlordImage(holder.itemimg).execute(cartModalArrayList.get(position).getCart_item_img_url());
        System.out.println("here is cart"+cartModalArrayList);
        ///items_total.setText(cartModalArrayList.get(position).getCart_item_price());

        holder.btn_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.itemquantity.setText(String.valueOf(counterClass.mincount()));
              if (counterClass.getCount()<=1){
                    CartLitedb db = new CartLitedb(v.getContext());
                   db.deletebyname(cartModalArrayList.get(position).getCart_item_name());
                   db.close();

                   cartModalArrayList.remove(position);

                   notifyDataSetChanged();


                }

            }
        });

        holder.btn_pluss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /*int x=counterClasses[position].addcount();*/

                holder.itemquantity.setText(String.valueOf(counterClass.addcount()));


            }
        });
    }

    @Override
    public int getItemCount() {
        return cartModalArrayList.size();
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

}
