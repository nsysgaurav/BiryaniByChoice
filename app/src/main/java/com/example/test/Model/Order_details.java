package com.example.test.Model;

import java.util.ArrayList;
import java.util.HashMap;

public class Order_details
{
    String uid;
    String cart_id;
    String shipping_address_id;
    String billing_address_id;
    String shipping_amount;
    String tax_amount;
    String total_amount;
    String shipping_option;
    String payment_option;
    ArrayList<HashMap<String,String>> products;


    public String getShipping_address_id() {
        return shipping_address_id;
    }

    public void setShipping_address_id(String shipping_address_id) {
        this.shipping_address_id = shipping_address_id;
    }

    public String getBilling_address_id() {
        return billing_address_id;
    }

    public void setBilling_address_id(String billing_address_id) {
        this.billing_address_id = billing_address_id;
    }

    public String getShipping_amount() {
        return shipping_amount;
    }

    public void setShipping_amount(String shipping_amount) {
        this.shipping_amount = shipping_amount;
    }

    public String getTax_amount() {
        return tax_amount;
    }

    public void setTax_amount(String tax_amount) {
        this.tax_amount = tax_amount;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getShipping_option() {
        return shipping_option;
    }

    public void setShipping_option(String shipping_option) {
        this.shipping_option = shipping_option;
    }

    public String getPayment_option() {
        return payment_option;
    }

    public void setPayment_option(String payment_option) {
        this.payment_option = payment_option;
    }


    public String getCart_id() {
        return cart_id;
    }

    public void setCart_id(String cart_id) {
        this.cart_id = cart_id;
    }





    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public ArrayList<HashMap<String, String>> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<HashMap<String, String>> products) {
        this.products = products;
    }



}
