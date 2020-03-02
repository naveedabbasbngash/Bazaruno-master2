package com.example.bazaruno.Model;

public class ItemModel {
    String id,shop_Id,shop_name,main_cat,sub_cat,sub_sub_cat,size,color,brand_name,item_images_url,item_price
            ,item_descount,item_name,item_city,item_bazzar;

    public ItemModel() {
    }

    public ItemModel(String id, String shop_Id, String shop_name, String main_cat, String sub_cat,
                     String sub_sub_cat, String size, String color, String brand_name, String item_images_url,
                     String item_price, String item_descount, String item_name, String item_city, String item_bazzar) {
        this.id = id;
        this.shop_Id = shop_Id;
        this.shop_name = shop_name;
        this.main_cat = main_cat;
        this.sub_cat = sub_cat;
        this.sub_sub_cat = sub_sub_cat;
        this.size = size;
        this.color = color;
        this.brand_name = brand_name;
        this.item_images_url = item_images_url;
        this.item_price = item_price;
        this.item_descount = item_descount;
        this.item_name = item_name;
        this.item_city = item_city;
        this.item_bazzar = item_bazzar;
    }

    public String getItem_city() {
        return item_city;
    }

    public void setItem_city(String item_city) {
        this.item_city = item_city;
    }

    public String getItem_bazzar() {
        return item_bazzar;
    }

    public void setItem_bazzar(String item_bazzar) {
        this.item_bazzar = item_bazzar;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_price() {
        return item_price;
    }

    public void setItem_price(String item_price) {
        this.item_price = item_price;
    }

    public String getItem_descount() {
        return item_descount;
    }

    public void setItem_descount(String item_descount) {
        this.item_descount = item_descount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShop_Id() {
        return shop_Id;
    }

    public void setShop_Id(String shop_Id) {
        this.shop_Id = shop_Id;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getMain_cat() {
        return main_cat;
    }

    public void setMain_cat(String main_cat) {
        this.main_cat = main_cat;
    }

    public String getSub_cat() {
        return sub_cat;
    }

    public void setSub_cat(String sub_cat) {
        this.sub_cat = sub_cat;
    }

    public String getSub_sub_cat() {
        return sub_sub_cat;
    }

    public void setSub_sub_cat(String sub_sub_cat) {
        this.sub_sub_cat = sub_sub_cat;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getBrand_name() {
        return brand_name;
    }

    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name;
    }

    public String getItem_images_url() {
        return item_images_url;
    }

    public void setItem_images_url(String item_images_url) {
        this.item_images_url = item_images_url;
    }
}
