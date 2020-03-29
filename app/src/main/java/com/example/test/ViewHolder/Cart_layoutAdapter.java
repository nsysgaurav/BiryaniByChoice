package com.example.test.ViewHolder;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.test.Model.CartModal;
import com.example.test.Model.Cat_data;
import com.example.test.Model.UserDetails;
import com.example.test.OrderCart.Cart;
import com.example.test.R;
import com.example.test.Sqldirectory.CartLitedb;
import com.example.test.Sqldirectory.DatabaseHelper;
import com.example.test.activity.SessionManager;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Cart_layoutAdapter extends RecyclerView.Adapter<Cart_layoutAdapter.CartViewHolder> {


    View view;
    ArrayList<HashMap<String,String>> cart_map;
    Context context;
    DatabaseHelper data_base;
    SessionManager sessionManager;
    UserDetails userDetails;
    TextView total_amout_of_cart,pay_to;
    String total_amount;

    public Cart_layoutAdapter(Context context,ArrayList<HashMap<String,String>> cart_map,TextView total_amout_of_cart,TextView pay_to)
    {
        this.context = context;
        this.cart_map = cart_map;
        this.total_amout_of_cart=total_amout_of_cart;
        this.pay_to = pay_to;
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
        view = inflater.inflate(R.layout.cart_layout,parent,false);

        CartViewHolder holder = new CartViewHolder(view);


        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CartViewHolder holder, final int position)
    {
        try
        {
            final int[] quintity = {0};

            final String product_cat_id  =  cart_map.get(position).get("product_cat_id");
            holder.itemName.setText(cart_map.get(position).get("product_name"));
            holder.itemquantity.setText(cart_map.get(position).get("qty"));
            holder.itemPrice.setText("₹"+(int)Double.parseDouble(cart_map.get(position).get("total_amount")));
            final String product_id = cart_map.get(position).get("product_id");
            final String qty = cart_map.get(position).get("qty");
            quintity[0]=Integer.parseInt(cart_map.get(position).get("qty"));


     //button for add the product in the cart-->
            holder.btn_pluss.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {

                    quintity[0]=quintity[0]+1;
                    save_the_data_to_cart(userDetails.getId(),product_id,Integer.toString(quintity[0]));

                }
            });


            holder.btn_minus.setOnClickListener(new View.OnClickListener()
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
                                remove_the_product_from_cart(userDetails.getId(),product_id,product_cat_id);
                            }
                            catch (Exception e)
                            {

                            }


                        }
                        else
                        {
                            try
                            {
                                save_the_data_to_cart(userDetails.getId(),product_id,Integer.toString(quintity[0]));
                            }
                            catch (Exception e)
                            {

                            }

                        }


                    }
                    else
                    {

                    }
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



    public void save_the_data_to_cart(String user_id,String product_id,String quintity)
    {
        Cat_data cart = new Cat_data();
        cart.setUid(user_id);
        cart.setProducts(get_produt_deails(product_id,quintity));

        Gson gson = new Gson();
        String jsno_data = gson.toJson(cart);
        Log.e("jsno_data",jsno_data.toString());
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://3.6.27.167/api/addtocart";
        JsonObjectRequest postRequest = null;
        try {
            postRequest = new JsonObjectRequest(url, new JSONObject(jsno_data),
                    new Response.Listener<JSONObject>()
                    {
                        @Override
                        public void onResponse(JSONObject response)
                        {
                            //Process os success response
                        //    mShimmerViewContainer.stopShimmerAnimation();
                         //   mShimmerViewContainer.setVisibility(View.GONE);
                            Log.e("response_button",response.toString());

                            if(response!=null)
                            {
                                try
                                {
                                    int status = response.getInt("status");

                                    if(status == 200)
                                    {

                                        JSONObject object_data = response.getJSONObject("data");

                                        if(response.has("total_amount"))
                                        {
                                            total_amount = response.getString("total_amount");
                                        }


                                        JSONArray jsonArray = object_data.getJSONArray("articles");
                                        for(int i=0;i<jsonArray.length();i++)
                                        {
                                            JSONObject object_artical = jsonArray.getJSONObject(i);

                                            String product_id = object_artical.getString("product_id");
                                            String quantity = object_artical.getString("quantity");
                                            String amount = object_artical.getString("amount");

                                            try
                                            {
                                                data_base.update_cart_value(product_id,amount,quantity);

                                            }
                                            catch (Exception e)
                                            {

                                            }
                                        }

                                        ArrayList<HashMap<String,String>> cart_data = data_base.get_the_cart_data_with_product_name();

                                        if(cart_data!=null)
                                        {
                                            try
                                            {
                                                cart_map.clear();
                                                cart_map=cart_data;
                                                notifyDataSetChanged();
                                                total_amout_of_cart.setText("₹"+total_amount);
                                                pay_to.setText("₹"+total_amount);
                                            }
                                            catch (Exception e)
                                            {
                                                Log.e("eRROR",e.toString());

                                            }


                                        }
                                    }
                                }
                                catch (JSONException e)
                                {
                                    e.printStackTrace();
                                }


                            }
                        }
                    }, new Response.ErrorListener()
            {
                @Override
                public void onErrorResponse(VolleyError error)
                {
                    //mShimmerViewContainer.stopShimmerAnimation();
                  //  mShimmerViewContainer.setVisibility(View.GONE);


                    Log.e("error",error.toString());
                }

            }){
                @Override
                public String getBodyContentType()
                {
                    return "application/json";
                }



            };
        } catch (JSONException e) {
            e.printStackTrace();
        }

        postRequest.setShouldCache(false);
        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(postRequest);

    }


    //remove the product from the cart-->
    public void remove_the_product_from_cart(String user_id, final String product_id, String cat_id)
    {
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("product_id",product_id);
        hashMap.put("uid",user_id);
        hashMap.put("cart_id",cat_id);

        Gson gson = new Gson();
        String jsno_data = gson.toJson(hashMap);
        Log.e("jsno_data",jsno_data.toString());
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://3.6.27.167/api/removefromcart";
        JsonObjectRequest postRequest = null;
        try {
            postRequest = new JsonObjectRequest(url, new JSONObject(jsno_data),
                    new Response.Listener<JSONObject>()
                    {
                        @Override
                        public void onResponse(JSONObject response)
                        {
                            //Process os success response
                            //    mShimmerViewContainer.stopShimmerAnimation();
                            //   mShimmerViewContainer.setVisibility(View.GONE);
                            Log.e("remove_the_product_from_cart: ",response.toString());

                            if(response!=null)
                            {
                                try
                                {
                                    int status = response.getInt("status");

                                    if(status == 200)
                                    {
                                        if(response.has("internal_message"))
                                        {
                                            try {
                                                String status_message = response.getString("internal_message");

                                                if(status_message.equalsIgnoreCase("Item removed successfully"))
                                                {

                                                    int result= data_base.deleteItem(product_id);

                                                    if(result>0) {


                                                        ArrayList<HashMap<String, String>> cart_data = data_base.get_the_cart_data_with_product_name();

                                                        if (cart_data != null) {
                                                            try {
                                                                cart_map.clear();
                                                                cart_map = cart_data;
                                                                notifyDataSetChanged();
                                                                total_amout_of_cart.setText("₹" + total_amount);
                                                                pay_to.setText("₹" + total_amount);
                                                            } catch (Exception e) {
                                                                Log.e("ERROR", e.toString());

                                                            }


                                                        }
                                                    }
                                                }
                                            }
                                            catch (JSONException ex)
                                            {
                                                ex.printStackTrace();
                                            }
                                        }
                                    }
                                }
                                catch (JSONException e)
                                {
                                    e.printStackTrace();
                                }


                            }
                        }
                    }, new Response.ErrorListener()
            {
                @Override
                public void onErrorResponse(VolleyError error)
                {
                    //mShimmerViewContainer.stopShimmerAnimation();
                    //  mShimmerViewContainer.setVisibility(View.GONE);


                    Log.e("error",error.toString());
                }

            }){
                @Override
                public String getBodyContentType()
                {
                    return "application/json";
                }



            };
        } catch (JSONException e) {
            e.printStackTrace();
        }

        postRequest.setShouldCache(false);
        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(postRequest);

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
