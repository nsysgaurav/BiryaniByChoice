package com.example.test.ViewHolder;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.test.Model.CartModal;
import com.example.test.Model.FoodModel;
import com.example.test.OrderCart.Cart;
import com.example.test.R;
import com.example.test.Sqldirectory.DatabaseHelper;
import com.example.test.Sqldirectory.MyDatabse;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;

public class NewCardAdapter extends RecyclerView.Adapter<NewCardAdapter.ViewHolder> {
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


    public NewCardAdapter(ArrayList<FoodModel> food_list, Context context,
                          TextView cart_amout, TextView items_total,
                          LinearLayout bottom_sheet_layout, RelativeLayout add_new_serv_layout,
                          Button add_new_serv_btn, Button repeat_last_serv_btn, Button close_new_serv_layout_btn,
                          TextView add_new_serv_name,TextView add_new_serving_size, TextView bottom_sheet_view_cart_btn,RecyclerView recyclerView) {
        this.food_list = food_list;
        this.context = context;
        this.cart_amout = cart_amout;
        this.items_total = items_total;
        this.bottom_sheet_layout = bottom_sheet_layout;
        myDatabse=new MyDatabse(context);
        data_base = new DatabaseHelper(context);
        this.add_new_serv_layout=add_new_serv_layout;
        this.add_new_serv_btn=add_new_serv_btn;
        this.repeat_last_serv_btn=repeat_last_serv_btn;
        this.close_new_serv_layout_btn=close_new_serv_layout_btn;
        this.add_new_serv_name=add_new_serv_name;
        this.add_new_serving_size=add_new_serving_size;
        this.bottom_sheet_view_cart_btn=bottom_sheet_view_cart_btn;
        this.recyclerView=recyclerView;


    }

    public NewCardAdapter(ArrayList<FoodModel> food_list) {
        this.food_list = food_list;
    }
    public NewCardAdapter(ArrayList<FoodModel> food_list, Context context, TextView cart_amout, TextView items_total)
    {
        this.food_list = food_list;
        this.context = context;
        this.cart_amout = cart_amout;
        this.items_total = items_total;
        data_base = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        view=inflater.inflate(R.layout.new_food_card,parent,false);
        ViewHolder holder=new ViewHolder(view);
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.biryaniquantity);
        toolbar = dialog.findViewById(R.id.biryanitoolbar);
        firstprice = dialog.findViewById(R.id.popup_price_1);
        secondprice =dialog.findViewById(R.id.popup_price_2);
        thirdprice = dialog.findViewById(R.id.popup_price_3);
        biryaniquantitybtn =dialog.findViewById(R.id.addbutton);
        btn1 =dialog.findViewById(R.id.radio_btn_1);
        btn2 =dialog.findViewById(R.id.radio_btn_2);
        btn3 =dialog.findViewById(R.id.radio_btn_3);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        final int[] quintity = {0};
        holder.food_name.setText(food_list.get(position).getFoodName());
        holder.food_price.setText(String.valueOf(food_list.get(position).getFoodPrice()));
        holder.food_desc.setText(food_list.get(position).getFoodDes());
        quintity[0] = food_list.get(position).getQuantity();





bottom_sheet_view_cart_btn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(view.getContext(), Cart.class);
        context.startActivity(intent);


    }
});

        if(food_list.get(position).getFoodCat().equals("veg"))
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

                toolbar.setTitle(food_list.get(position).getFoodName());
                firstprice.setText(String.valueOf(food_list.get(position).getFoodPrice()));
                secondprice.setText(String.valueOf(food_list.get(position).getMedium_serving_price()));
                thirdprice.setText(String.valueOf(food_list.get(position).getLarge_serving_price()));
                dialog.show();

                biryaniquantitybtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        quintity[0]=quintity[0]+1;
                        holder.counter_text.setText(String.valueOf(quintity[0]));

                        long result = data_base.save_cart_value(String.valueOf(food_list.get(position).getFoodid()),
                                food_list.get(position).getFoodName(),food_list.get(position).getFoodDes(),food_list.get(position).getFood_imag_url(),
                                String.valueOf(food_list.get(position).getFoodPrice()),String.valueOf(quintity[0]));


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
                            bottom_sheet_layout.setVisibility(View.VISIBLE);
                            recyclerView.setPadding(0,0,0,120);

                            holder.linearLayout_btn.setVisibility(View.INVISIBLE);
                            holder.linearLayout.setVisibility(View.VISIBLE);


                            dialog.dismiss();

                        }


                });



            }
        });




        holder.add_counter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                add_new_serv_name.setText(food_list.get(position).getFoodName());
               add_new_serving_size.setText("serving 1");

                if(quintity[0]>0)
                {
                    add_new_serv_layout.setVisibility(View.VISIBLE);
                    bottom_sheet_layout.setVisibility(View.INVISIBLE);

                }
                close_new_serv_layout_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        add_new_serv_layout.setVisibility(View.INVISIBLE);
                    }
                });
               add_new_serv_btn.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       dialog.show();
                       add_new_serv_layout.setVisibility(View.INVISIBLE);

                   }
               });
                repeat_last_serv_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        quintity[0]=quintity[0]+1;
                        holder.counter_text.setText(String.valueOf(quintity[0]));
                        long result = data_base.save_cart_value(String.valueOf(food_list.get(position).getFoodid()),
                                food_list.get(position).getFoodName(),food_list.get(position).getFoodDes(),"image",
                                String.valueOf(food_list.get(position).getFoodPrice()),String.valueOf(quintity[0]));


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
                        add_new_serv_layout.setVisibility(View.INVISIBLE);
                        bottom_sheet_layout.setVisibility(View.VISIBLE);

                    }
                });






               // Log.e("Result _Add",quintity[0]+"");

                //     long result =   data_base.save_cart_value(food_list.get(position).getFood_id(),  food_list.get(position).getFoodName(),food_list.get(position).getFoodDes(),"image",String.valueOf(food_list.get(position).getFoodPrice()),Integer.toString(food_list.get(position).getQuantity()));


            }
        });
        holder.min_counter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quintity[0]>0)
                {
                    quintity[0]=quintity[0]-1;
                    holder.counter_text.setText(String.valueOf(quintity[0]));
                    long result = data_base.save_cart_value(String.valueOf(food_list.get(position).getFoodid()),  food_list.get(position).getFoodName(),food_list.get(position).getFoodDes(),"image",String.valueOf(food_list.get(position).getFoodPrice()),String.valueOf(quintity[0]));

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
                    }else {
                        bottom_sheet_layout.setVisibility(View.INVISIBLE);
                    }
                }
                else
                {
                    holder.add_btn.setVisibility(View.VISIBLE);
                    holder.linearLayout_btn.setVisibility(View.VISIBLE);
                    holder.linearLayout.setVisibility(View.INVISIBLE);
                    bottom_sheet_layout.setVisibility(View.INVISIBLE);
                    recyclerView.setPadding(0,0,0,0);

                }



                Log.e("Result Sub",quintity[0]+"");
            }
        });
if(food_list.get(position).getImage()!=null){
    try{
        holder.imageView.setImageBitmap(BitmapFactory.decodeByteArray(food_list.get(position).getImage(),0,food_list.get(position).getImage().length));
    }catch (Exception e)
    {
        e.printStackTrace();
    }
}
else if(food_list.get(position).getFoodName().equals("Handi pasta"))
{
    holder.imageView.setImageResource(R.drawable.pasta);
}
else  if(food_list.get(position).getFoodName().equals("Chicken Cheese Pizza"))
{
    holder.imageView.setImageResource(R.drawable.chickenpasta);
}
else  if(food_list.get(position).getFoodName().equals("Chicken Tikka"))
{
    holder.imageView.setImageResource(R.drawable.chickentikka);
}
else  if(food_list.get(position).getFoodName().equals("Sahi Paneer"))
{
    holder.imageView.setImageResource(R.drawable.shahipaneer);
}
else {

}

    }

    @Override
    public int getItemCount() {
        return food_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView, cat_icon_image;
        TextView food_name, food_price, food_desc, counter_text;
        Button add_btn;
        ImageButton  add_counter, min_counter;
        LinearLayout linearLayout, linearLayout_btn;
        public ViewHolder(@NonNull View itemView) {
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
