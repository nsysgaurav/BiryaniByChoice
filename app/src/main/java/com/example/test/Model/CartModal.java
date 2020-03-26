package com.example.test.Model;

import android.util.Base64;

public class CartModal {

   private int cart_item_id;
    private String cart_item_name;
    private  String cart_item_desc;
    private String cart_item_cat;
     private String cart_item_img_url;
     private byte[] image;
    private int cart_quantity;
    private int cart_item_price;

    public static final String tablename="cart";
    public static final String tablenamecard="ards";
    public  static final   String column_id="_id";
    public  static final  String column_desc="desc";
   public static final String column_name ="name";
   public static final String column_price ="price";
    public static final String column_img_url="image_url";
    public static final String column_quantity="quantity";
    public static final String column_cat="category";

    public static final String create_table="create table "+tablename+
            "("
            + column_id + " Integer primary key AUTOINCREMENT,"+
            column_name +" text ,"+ column_quantity +" Integer ,"+
            column_price +" Integer ,"+column_cat+" text ,"+ column_img_url +" BLOB )";



    public CartModal() {
    }

    public CartModal( int id,String cart_item_name, String cart_item_img_url, int cart_item_price,int quantity) {


            this.cart_item_id =id;
            this.cart_item_name = cart_item_name;
            this.cart_item_img_url = cart_item_img_url;
            this.cart_item_price = cart_item_price;
            this.cart_quantity =quantity;


    }

    public String getCart_item_cat() {
        return cart_item_cat;
    }

    public void setCart_item_cat(String cart_item_cat) {
        this.cart_item_cat = cart_item_cat;
    }

    public String getCart_item_desc() {
        return cart_item_desc;
    }

    public void setCart_item_desc(String cart_item_desc) {
        this.cart_item_desc = cart_item_desc;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getCart_item_id() {
        return cart_item_id;
    }

    public void setCart_item_id(int cart_item_id) {
        this.cart_item_id = cart_item_id;
    }

    public int getQuantity() {
        return cart_quantity;
    }

    public void setQuantity(int quantity) {
        this.cart_quantity = quantity;
    }

    public String getCart_item_name() {
        return cart_item_name;
    }

    public void setCart_item_name(String cart_item_name) {
        this.cart_item_name = cart_item_name;
    }

    public String getCart_item_img_url() {
        return cart_item_img_url;
    }

    public void setCart_item_img_url(String cart_item_img_url) {
        String imageData[] = cart_item_img_url.split(",");

        cart_item_img_url = imageData[1];
        this.cart_item_img_url = cart_item_img_url;
        setImage(Base64.decode(cart_item_img_url,Base64.DEFAULT));

    }

    public int getCart_item_price() {
        return cart_item_price;
    }

    public void setCart_item_price(int cart_item_price) {
        this.cart_item_price = cart_item_price;
    }

}
