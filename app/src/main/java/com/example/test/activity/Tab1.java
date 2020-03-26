package com.example.test.activity;


import android.os.Bundle;


import androidx.annotation.Nullable;

import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.GridLayoutManager;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.test.BAckgrounddata.GetData;
import com.example.test.Model.FoodModel;
import com.example.test.R;
import com.example.test.Sqldirectory.DatabaseHelper;
import com.example.test.ViewHolder.NewCardAdapter;
import java.util.ArrayList;



/**
 * A simple {@link Fragment} subclass.
 */
public class Tab1 extends Fragment {
        View view;
    static  String   Url ="http://192.168.1.7:8080/biryaniweb/food/";
    ArrayList<FoodModel> foodlists=new ArrayList<>();
    SwipeRefreshLayout pulltorefresh;
    String tag = "Tab1.class";
    TextView cart_amout,items_total, add_new_serv_name,add_new_serving_size, bottom_sheet_view_cart_btn;

    DatabaseHelper data_base;
    LinearLayout bottom_sheet_layout;
    GetData getData=new GetData();
    RelativeLayout add_new_serv_layout;
    Button add_new_serv_btn,repeat_last_serv_btn, close_new_serv_layout_btn;
    RecyclerView recyclerView;




    public Tab1() {
        // Required empty public constructor
    }
    public  Tab1(ArrayList<FoodModel> foodModels,TextView cart_amout,TextView items_total,
                 LinearLayout bottom_sheet_layout, RelativeLayout add_new_serv_layout,
                 Button add_new_serv_btn, Button repeat_last_serv_btn, Button close_new_serv_layout_btn,
                 TextView add_new_serv_name,TextView add_new_serving_size,TextView bottom_sheet_view_cart_btn){
        this.cart_amout =cart_amout;
        this.items_total=items_total;
        this.bottom_sheet_layout =bottom_sheet_layout;
        this.foodlists = foodModels;
        this.add_new_serv_layout=add_new_serv_layout;
        this.add_new_serv_btn=add_new_serv_btn;
        this.repeat_last_serv_btn=repeat_last_serv_btn;
        this.close_new_serv_layout_btn=close_new_serv_layout_btn;
        this.add_new_serv_name=add_new_serv_name;
        this.add_new_serving_size=add_new_serving_size;
        this.bottom_sheet_view_cart_btn=bottom_sheet_view_cart_btn;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
           view =  inflater.inflate(R.layout.fragment_tab1, container, false);
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

       recyclerView = view.findViewById(R.id.layout);

       // recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new GridLayoutManager(view.getContext(),2));
        recyclerView.setAdapter(new NewCardAdapter(foodlists,getContext(),cart_amout,items_total,bottom_sheet_layout,
                add_new_serv_layout, add_new_serv_btn,repeat_last_serv_btn, close_new_serv_layout_btn,
                add_new_serv_name,add_new_serving_size, bottom_sheet_view_cart_btn, recyclerView));

        return view;

    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }
}

