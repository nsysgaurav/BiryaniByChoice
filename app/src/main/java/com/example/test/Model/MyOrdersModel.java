package com.example.test.Model;

public class MyOrdersModel {

    int my_Order_id;
    String order_date;
    String order_time;
    int order_price;
    int order_item_count;

    public MyOrdersModel() {
    }

    public MyOrdersModel(int my_Order_id, String order_date, String order_time, int order_price, int order_item_count) {
        this.my_Order_id = my_Order_id;
        this.order_date = order_date;
        this.order_time = order_time;
        this.order_price = order_price;
        this.order_item_count = order_item_count;
    }

    public int getMy_Order_id() {
        return my_Order_id;
    }

    public void setMy_Order_id(int my_Order_id) {
        this.my_Order_id = my_Order_id;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public String getOrder_time() {
        return order_time;
    }

    public void setOrder_time(String order_time) {
        this.order_time = order_time;
    }

    public int getOrder_price() {
        return order_price;
    }

    public void setOrder_price(int order_price) {
        this.order_price = order_price;
    }

    public int getOrder_item_count() {
        return order_item_count;
    }

    public void setOrder_item_count(int order_item_count) {
        this.order_item_count = order_item_count;
    }
}
