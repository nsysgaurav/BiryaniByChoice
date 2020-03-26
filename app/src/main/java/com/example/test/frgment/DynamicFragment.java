package com.example.test.frgment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.test.R;
import com.example.test.ViewHolder.Adapter_for_product_gride;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class DynamicFragment extends Fragment
{

    View view;
    RecyclerView list;
    ArrayList<HashMap<String,String>> cat_list_product_details = new ArrayList<HashMap<String,String>>() ;
    HashMap<String,String> product_setails_map;
    String id;
    RecyclerView recyclerView;
    LinearLayout bottomsheet;
    TextView cart_amout,items_total;


    public DynamicFragment()
    {

    }


    public  DynamicFragment(LinearLayout bottomsheet,TextView cart_amout,TextView items_total)
    {
        this.bottomsheet = bottomsheet;
        this.cart_amout = cart_amout;
        this.items_total = items_total;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        try
        {

            String slug[] = getArguments().getString("id").split(",");

            Log.e("id--------------------->",getArguments().getString("id"));
              get_all_product_list(getActivity(),slug[1]);
        }
        catch (Exception e)
        {

        }
    }

    public static DynamicFragment newInstance(String val, String id, LinearLayout bottomsheet,TextView cart_amout, TextView items_total)
    {
        DynamicFragment fragment = new DynamicFragment(bottomsheet,cart_amout,items_total);
        Bundle args = new Bundle();
        args.putString("someValue", val);
        args.putString("id", id);
        fragment.setArguments(args);
        return fragment;
    }
    ArrayList<String> videoModelArrayList = new ArrayList<>();
    //hello
    String node;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_tab1, container, false);
        recyclerView = view.findViewById(R.id.layout);
        node = getArguments().getString("someValue");

        String slug[] = getArguments().getString("id").split(",");

      //  Log.e("id--------------------->",getArguments().getString("id"));

        return view;
    }


    public void get_all_product_list(final Context context, final String slug)
    {
        RequestQueue queue = Volley.newRequestQueue(context.getApplicationContext());
        String url = "http://3.6.27.167/api/category/cat-pro";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        // response
                        Log.e("Response", response);

                        if(response!=null)
                        {
                            try
                            {

                                JSONObject object = new JSONObject(response.toString());
                                int status = object.getInt("status");
                                if(status == 200)
                                {
                                    JSONArray jsonArray = object.getJSONArray("result");
                                    for(int i=0;i<jsonArray.length();i++)
                                    {
                                        JSONObject obj=jsonArray.getJSONObject(i);
                                        product_setails_map = new HashMap<String,String>();
                                        product_setails_map.clear();
                                        String id = obj.getString("id");
                                        String type = obj.getString("type");
                                        String name = obj.getString("name");
                                        String slug = obj.getString("slug");
                                        String sku = obj.getString("sku");
                                        String description = obj.getString("description");
                                        String statuss = obj.getString("status");
                                        String in_stock = obj.getString("in_stock");
                                        String price = obj.getString("price");

                                        product_setails_map.put("id",id);
                                        product_setails_map.put("type",type);
                                        product_setails_map.put("name",name);
                                        product_setails_map.put("slug",slug);
                                        product_setails_map.put("sku",sku);
                                        product_setails_map.put("description",description);
                                        product_setails_map.put("statuss",statuss);
                                        product_setails_map.put("in_stock",in_stock);
                                        product_setails_map.put("price",price);


                                        cat_list_product_details.add(product_setails_map);
                                    }

                                    recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(),2));
                                    recyclerView.setAdapter(new Adapter_for_product_gride(getContext(),cat_list_product_details,cart_amout,items_total,bottomsheet));


                                }
                                else
                                {

                                }


                            }
                            catch (JSONException e)
                            {
                                e.printStackTrace();
                            }
                        }
                        else
                        {

                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        // error

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("nsyskey", "082057a4d249514389bb90c6d50ecf23");
                params.put("slug", slug.trim());

                return params;
            }
        };
        postRequest.setShouldCache(false);
        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(postRequest);

    }

}