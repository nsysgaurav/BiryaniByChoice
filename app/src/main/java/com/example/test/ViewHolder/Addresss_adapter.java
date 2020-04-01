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
import com.example.test.Model.Cat_data;
import com.example.test.Model.UserDetails;
import com.example.test.R;
import com.example.test.Sqldirectory.DatabaseHelper;
import com.example.test.activity.Add_new_Address_Activity;
import com.example.test.activity.SessionManager;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Addresss_adapter extends RecyclerView.Adapter<Addresss_adapter.CartViewHolder> {


    View view;
    ArrayList<HashMap<String,String>> cart_map;
    Context context;
    DatabaseHelper data_base;
    SessionManager sessionManager;
    ProgressBar progress_bar;
    TextView textView;


    public Addresss_adapter(Context context, ArrayList<HashMap<String,String>> cart_map,   ProgressBar progress_bar,TextView textView)
    {
        this.context = context;
        this.cart_map = cart_map;
        this.progress_bar=progress_bar;
        this. textView=textView;

    }
    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.adress_item,parent,false);

        CartViewHolder holder = new CartViewHolder(view);


        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CartViewHolder holder, final int position)
    {
        final String id=cart_map.get(position).get("id");
        final String first_name=cart_map.get(position).get("first_name");
        final String last_name=cart_map.get(position).get("last_name");
        final String address1=cart_map.get(position).get("address1");
        final String address2=cart_map.get(position).get("address2");
        final String city=cart_map.get(position).get("city");
        final String zipcode=cart_map.get(position).get("zipcode");
        final String phone=cart_map.get(position).get("phone");
        final String landmark=cart_map.get(position).get("landmark");
        final String type=cart_map.get(position).get("type");
        final String state=cart_map.get(position).get("state");
        final String address_type=cart_map.get(position).get("address_type");

        try
        {
            holder.name.setText(first_name+" "+last_name);
            holder.phone_number.setText(phone);
            holder.address.setText(address1+", "+address2+", "+landmark+", "+city+"," +zipcode+", "+state);
            holder.type.setText(address_type);

            holder.edit.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {

                    Intent intent = new Intent(context, Add_new_Address_Activity.class);
                    intent.putExtra("edit",2);
                    intent.putExtra("id",id);
                    intent.putExtra("first_name",first_name);
                    intent.putExtra("last_name",last_name);
                    intent.putExtra("address1",address1);
                    intent.putExtra("address2",address2);
                    intent.putExtra("city",city);
                    intent.putExtra("zipcode",zipcode);
                    intent.putExtra("phone",phone);
                    intent.putExtra("landmark",landmark);
                    intent.putExtra("type",type);
                    intent.putExtra("state",state);
                    intent.putExtra("address_type",address_type);
                    context.startActivity(intent);


                }
            });

            holder.remove.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {

                    try
                    {
                        remove_the_address(id,position);
                    }
                    catch (Exception e)
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


        TextView name,address,phone_number,type,edit,remove;
        ImageButton btn_minus,btn_pluss;

        public CartViewHolder(@NonNull View itemView)
        {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            address = itemView.findViewById(R.id.address);
            phone_number = itemView.findViewById(R.id.phone_number);
            type = itemView.findViewById(R.id.type);
            edit = itemView.findViewById(R.id.edit);
            remove = itemView.findViewById(R.id.remove);

                   }
    }



    public void remove_the_address(String id, final int position)
    {

        progress_bar.setVisibility(View.VISIBLE);
        HashMap hashMap = new HashMap<>();
        hashMap.put("id",Integer.parseInt(id));

        Gson gson = new Gson();
        String jsno_data = gson.toJson(hashMap);
        Log.e("jsno_data",jsno_data.toString());
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "http://3.6.27.167/api/delete-address";
        JsonObjectRequest postRequest = null;
        try {
            postRequest = new JsonObjectRequest(url, new JSONObject(jsno_data),
                    new Response.Listener<JSONObject>()
                    {
                        @Override
                        public void onResponse(JSONObject response)
                        {
                            progress_bar.setVisibility(View.GONE);
                            //Process os success response
                            //    mShimmerViewContainer.stopShimmerAnimation();
                            //   mShimmerViewContainer.setVisibility(View.GONE);
                            Log.e("delet_address: ",response.toString());

                            if(response!=null)
                            {
                                try
                                {
                                    int status = response.getInt("status");

                                    if(status == 200)
                                    {
                                        JSONObject object=response.getJSONObject("success");

                                        if(object.getString("internal_message").equalsIgnoreCase("Address delete successfully"))
                                        {

                                            Toast.makeText(context,"Address delete successfully",Toast.LENGTH_SHORT).show();

                                                 cart_map.remove(position);
                                               notifyDataSetChanged();

                                               if(cart_map.isEmpty())
                                               {
                                                   textView.setText("NO SAVED ADDRESS");
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
                    progress_bar.setVisibility(View.GONE);

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



}
