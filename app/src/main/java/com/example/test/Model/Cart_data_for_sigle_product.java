package com.example.test.Model;

import java.util.ArrayList;
import java.util.HashMap;

public class Cart_data_for_sigle_product
{
    String uid;

    ArrayList<HashMap<String,String>> products;

    public String getUid()
    {
        return uid;
    }

    public void setUid(String uid)
    {
        this.uid = uid;
    }

    public ArrayList<HashMap<String, String>> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<HashMap<String, String>> products) {
        this.products = products;
    }



}
