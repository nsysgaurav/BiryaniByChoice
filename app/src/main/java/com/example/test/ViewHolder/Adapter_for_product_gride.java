package com.example.test.ViewHolder;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.ItemDetails.ItemDetails;
import com.example.test.Model.CartModal;
import com.example.test.Model.FoodModel;
import com.example.test.OrderCart.Cart;
import com.example.test.R;
import com.example.test.Sqldirectory.DatabaseHelper;
import com.example.test.Sqldirectory.MyDatabse;

import java.util.ArrayList;
import java.util.HashMap;

public class Adapter_for_product_gride extends RecyclerView.Adapter<Adapter_for_product_gride.ViewHolder> {
    View view;
    ArrayList<FoodModel> food_list;
    DatabaseHelper data_base;
    MyDatabse myDatabse;
    Context context;
    TextView cart_amout,items_total ,firstprice,secondprice,thirdprice, add_new_serv_name,add_new_serving_size, bottom_sheet_view_cart_btn;
    LinearLayout bottom_sheet_layout;
    Toolbar toolbar;
    Button biryaniquantitybtn;
    Dialog dialog;
    RadioButton btn1,btn2,btn3;
    RelativeLayout add_new_serv_layout;
    Button add_new_serv_btn,repeat_last_serv_btn,close_new_serv_layout_btn;
    RecyclerView recyclerView;
    ArrayList<CartModal> cartModalArrayList=new ArrayList<>();


    ArrayList<HashMap<String,String>> cat_list_product_details;

    public Adapter_for_product_gride(Context context, ArrayList<HashMap<String,String>> cat_list_product_details,TextView cart_amout, TextView items_total,LinearLayout bottomsheet)
    {
        this.context = context;
         myDatabse=new MyDatabse(context);
        data_base = new DatabaseHelper(context);
        this.cart_amout = cart_amout;
        this.items_total = items_total;
        this.bottom_sheet_layout=bottomsheet;
        this.cat_list_product_details = cat_list_product_details;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        view=inflater.inflate(R.layout.new_food_card,parent,false);
        ViewHolder holder = new ViewHolder(view);


        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position)
    {
        final int[] quintity = {0};
        final String cat_id = cat_list_product_details.get(position).get("cat_id");
        final String id = cat_list_product_details.get(position).get("id");
        String type = cat_list_product_details.get(position).get("type");
        final String name = cat_list_product_details.get(position).get("name");
        String slug = cat_list_product_details.get(position).get("slug");
        String sku = cat_list_product_details.get(position).get("sku");
        final String description = cat_list_product_details.get(position).get("description");
        String statuss = cat_list_product_details.get(position).get("statuss");
        String in_stock = cat_list_product_details.get(position).get("in_stock");
        final String price = cat_list_product_details.get(position).get("price");


       // holder.food_desc.setText(description);
        holder.food_name.setText(name);
        holder.food_price.setText(Integer.toString((int)Double.parseDouble(price)));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        {
            holder. food_desc.setText(Html.fromHtml(description, Html.FROM_HTML_MODE_COMPACT));
        }
        else
            {
            holder. food_desc.setText(Html.fromHtml(description));
        }

        holder.imageView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //debug issue by Lalit kumar at 20.march.2020 1:30 PM when user click any item then application crashed
                Intent i = new Intent(context, ItemDetails.class);
                i.putExtra("name",name);
                i.putExtra("desc",description);
                i.putExtra("price",price);
               context.startActivity(i);
            }
        });

        if(type.equalsIgnoreCase("BASIC"))
        {
            holder.cat_icon_image.setImageResource(R.drawable.veg);
        }
        else {
            holder.cat_icon_image.setImageResource(R.drawable.nonveg);
        }

        holder.add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                        quintity[0]=quintity[0]+1;
                        holder.counter_text.setText(String.valueOf(quintity[0]));

                        long result = data_base.save_cart_value(id,
                                name,description,"",
                                price,String.valueOf(quintity[0]),cat_id,"");


                        if(result>0)
                        {
                            String amount =  data_base.get_the_total_amount();
                            String quantity = data_base. get_the_total_quantity();

                            if(amount!=null)
                            {
                                cart_amout.setText("₹"+amount);
                            }

                            if(quantity!=null)
                            {
                                items_total.setText(""+quantity+" Item");
                            }

                        }
                        bottom_sheet_layout.setVisibility(View.VISIBLE);
                    //    recyclerView.setPadding(0,0,0,120);

                        holder.linearLayout_btn.setVisibility(View.INVISIBLE);
                        holder.linearLayout.setVisibility(View.VISIBLE);

            }

        });




        holder.add_counter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        quintity[0]=quintity[0]+1;
                        holder.counter_text.setText(String.valueOf(quintity[0]));
                long result = data_base.save_cart_value(id,
                        name,description,"",
                        price,String.valueOf(quintity[0]),cat_id,"");


                if(result>0)
                        {
                            String amount =  data_base.get_the_total_amount();
                            String quantity = data_base. get_the_total_quantity();
                            cart_amout.setText("₹"+amount);
                            if(quantity!=null)
                            {
                                items_total.setText(""+quantity+" Item");
                            }

                        }
                     //   add_new_serv_layout.setVisibility(View.INVISIBLE);
                        bottom_sheet_layout.setVisibility(View.VISIBLE);

                    }

                // Log.e("Result _Add",quintity[0]+"");

                //     long result =   data_base.save_cart_value(food_list.get(position).getFood_id(),  food_list.get(position).getFoodName(),food_list.get(position).getFoodDes(),"image",String.valueOf(food_list.get(position).getFoodPrice()),Integer.toString(food_list.get(position).getQuantity()));



        });
        holder.min_counter.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(quintity[0]>0)
                {
                    quintity[0]=quintity[0]-1;

                    if(quintity[0]<=0)
                    {
                        try
                        {
                           int result= data_base.deleteItem(id);
                           Log.e("isDeleted"," "+result);
                            holder.add_btn.setVisibility(View.VISIBLE);
                            holder.linearLayout_btn.setVisibility(View.VISIBLE);
                            holder.linearLayout.setVisibility(View.INVISIBLE);
                                String amount =  data_base.get_the_total_amount();
                                String quantity = data_base. get_the_total_quantity();
                                cart_amout.setText("₹"+amount);
                                if(quantity!=null)
                                {
                                    items_total.setText(""+quantity+" Item");
                                }
                                Log.e("Result_amount",amount+"");




                    }
                        catch (Exception e)
                        {

                        }

                    }
                    else
                    {
                        holder.counter_text.setText(String.valueOf(quintity[0]));
                        long result = data_base.save_cart_value(id,
                                name,description,"",
                                price,String.valueOf(quintity[0]),cat_id,"");

                        if(result>0)
                        {
                            String amount =  data_base.get_the_total_amount();
                            String quantity = data_base. get_the_total_quantity();
                            cart_amout.setText("₹"+amount);
                            if(quantity!=null)
                            {
                                items_total.setText(""+quantity+" Item");
                            }
                            Log.e("Result_amount",amount+"");
                        }
                        else
                        {
                            bottom_sheet_layout.setVisibility(View.INVISIBLE);
                        }
                    }



                }
                else
                {

                    try
                    {
                        data_base.deleteItem(id);
                        holder.add_btn.setVisibility(View.VISIBLE);
                        holder.linearLayout_btn.setVisibility(View.VISIBLE);
                        holder.linearLayout.setVisibility(View.INVISIBLE);

                    }
                    catch (Exception e)
                    {

                    }

                    if(data_base.get_the_total_quantity()!=null)
                    {
                        if (Integer.parseInt(data_base.get_the_total_quantity()) > 0)
                        {
                            bottom_sheet_layout.setVisibility(View.VISIBLE);
                        }
                    }
                    else
                    {
                        holder.add_btn.setVisibility(View.VISIBLE);
                        holder.linearLayout_btn.setVisibility(View.VISIBLE);
                        holder.linearLayout.setVisibility(View.INVISIBLE);
                        bottom_sheet_layout.setVisibility(View.INVISIBLE);

                    }
                }

                //Log.e("Result Sub",quintity[0]+"");
            }
        });

    }

    @Override
    public int getItemCount()
    {
        return cat_list_product_details.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView, cat_icon_image;
        TextView food_name, food_price, food_desc, counter_text;
        Button add_btn;
        ImageButton  add_counter, min_counter;
        LinearLayout linearLayout, linearLayout_btn;
        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            cat_icon_image=itemView.findViewById(R.id.new_c_cat_icon);
            food_desc=itemView.findViewById(R.id.new_c_desc);
            imageView=itemView.findViewById(R.id.new_c_image);
            food_name=itemView.findViewById(R.id.new_c_f_name);
            food_price=itemView.findViewById(R.id.new_c_price);
            add_btn=itemView.findViewById(R.id.new_c_add_btn);
            linearLayout=itemView.findViewById(R.id.new_c_counter_layout);
            add_counter=itemView.findViewById(R.id.new_c_count_plus);
            min_counter=itemView.findViewById(R.id.new_c_count_min);
            counter_text=itemView.findViewById(R.id.new_c_count_num);
            linearLayout_btn= itemView.findViewById(R.id.linear_layout_button);

        }
    }
}
