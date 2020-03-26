package com.example.test.activity;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.test.BAckgrounddata.GetData;
import com.example.test.Model.Book;
import com.example.test.Model.FoodModel;
import com.example.test.Model.NonVegBiryani;
import com.example.test.R;
import com.example.test.Sqldirectory.DatabaseHelper;
import com.example.test.ViewHolder.BiryaniAdapter;
import com.example.test.ViewHolder.ComboAdapter;
import com.example.test.ViewHolder.NewCardAdapter;
import com.example.test.ViewHolder.VIewAdapter;
import com.example.test.utlity.SpacesItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class Tab2 extends Fragment {
     View view;
    ArrayList<NonVegBiryani> biryaniList = new ArrayList<>();
   ArrayList<FoodModel> foodModels=new ArrayList<>();
    TextView cart_amout, items_total, add_new_serv_name,add_new_serving_size, bottom_sheet_view_cart_btn;
    DatabaseHelper data_base;
    LinearLayout bottomsheet;
    RelativeLayout add_new_serv_layout;
    GetData getData=new GetData();
    RecyclerView recyclerView;
    Button add_new_serv_btn,repeat_last_serv_btn, close_new_serv_layout_btn;


    public Tab2() {
        // Required empty public constructor
    }
public Tab2(ArrayList<FoodModel> foodModels, TextView cart_amout, TextView items_total,
            LinearLayout bottomsheet, RelativeLayout add_new_serv_layout,
            Button add_new_serv_btn,Button repeat_last_serv_btn, Button close_new_serv_layout_btn,
            TextView add_new_serv_name,TextView add_new_serving_size, TextView bottom_sheet_view_cart_btn){
        this.cart_amout =cart_amout;
        this.items_total =items_total;
        this.bottomsheet=bottomsheet;
        this.foodModels=foodModels;
        this.add_new_serv_layout=add_new_serv_layout;
        this.add_new_serv_btn=add_new_serv_btn;
        this.repeat_last_serv_btn=repeat_last_serv_btn;
        this.close_new_serv_layout_btn=close_new_serv_layout_btn;
        this.add_new_serv_name=add_new_serv_name;
        this.add_new_serving_size=add_new_serving_size;
        this.bottom_sheet_view_cart_btn=bottom_sheet_view_cart_btn;

}

public Tab2(ArrayList<FoodModel> foodModels){
        this.foodModels =foodModels;
}

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

   //getData.getalldata(nonveg,context);
        System.out.println("data ye hai on attach me"+getData.getFoodModels());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
   view = inflater.inflate(R.layout.fragment_tab2,container,false);


        data_base=new DatabaseHelper(getActivity());
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

        recyclerView= view.findViewById(R.id.biryanirecycler);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(),2));
        recyclerView.setAdapter(new NewCardAdapter(foodModels,getContext(),cart_amout,items_total,bottomsheet,
                add_new_serv_layout,add_new_serv_btn,repeat_last_serv_btn, close_new_serv_layout_btn,
                add_new_serv_name,add_new_serving_size, bottom_sheet_view_cart_btn,recyclerView));
   return view;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);

    }
}
