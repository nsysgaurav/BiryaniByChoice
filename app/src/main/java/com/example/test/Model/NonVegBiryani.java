package com.example.test.Model;



import android.util.Base64;

import java.util.ArrayList;

public class NonVegBiryani {

     private int id;
    private String dish_name;
    private String desc;
    private String dish_cat;
    private String image_url;
    byte[] image;
    private int price;

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    private    ArrayList<NonVegBiryani> biryanilists;

    public ArrayList<NonVegBiryani> getBiryanilists() {
        return biryanilists;
    }

    public void setBiryanilists(ArrayList<NonVegBiryani> biryanilists) {
        this.biryanilists = biryanilists;
    }



    public NonVegBiryani() {
    }

    public NonVegBiryani(int id, String dish_name, String desc, int price ,String image_url) {
        this.id = id;
        this.dish_name = dish_name;
        this.desc = desc;
        this.price = price;
        this.image_url=image_url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDish_name() {
        return dish_name;
    }

    public void setDish_name(String dish_name) {
        this.dish_name = dish_name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        String imageData[] = image_url.split(",");
        try {
            image_url =imageData[1];
            this.image_url = image_url;
            setImage(Base64.decode(image_url,Base64.DEFAULT));
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public String toString() {
        return "NonVegBiryani{" +
                "id=" + id +
                ", dish_name='" + dish_name + '\'' +
                ", desc='" + desc + '\'' +
                ", price=" + price +
                '}';
    }
}
