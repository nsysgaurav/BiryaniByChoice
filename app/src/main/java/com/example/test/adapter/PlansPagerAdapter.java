package com.example.test.adapter;

import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.test.frgment.DynamicFragment;

import java.util.ArrayList;
import java.util.HashMap;


public class PlansPagerAdapter extends FragmentStatePagerAdapter
{
    int mNumOfTabs;
    ArrayList<String> tabTitle;
    ArrayList<HashMap<String,String>> cat_list;
    LinearLayout bottomsheet;
    TextView cart_amout,items_total;

    public PlansPagerAdapter(FragmentManager fm, int NumOfTabs, ArrayList<String> tabTitle,ArrayList<HashMap<String,String>> cat_list,LinearLayout bottomsheet,TextView cart_amout,TextView items_total)
    {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.tabTitle = tabTitle;
        this.cat_list = cat_list;
        this.bottomsheet = bottomsheet;
        this.cart_amout=cart_amout;
        this.items_total=items_total;
    }

    @Override
    public Fragment getItem(int position)
    {
        return new DynamicFragment().newInstance(tabTitle.get(position),cat_list.get(position).get("id")+","+cat_list.get(position).get("slug"), bottomsheet,cart_amout,items_total);
    }

    @Override
    public int getCount()
    {
        return mNumOfTabs;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitle.get(position);
    }

}