package com.example.bazaruno.Model;

public class ShopModel {

    String id,shop_name,shop_img,shop_lat_lang,city_area,city;

    public ShopModel(String shop_name, String shop_img, String shop_lat_lang, String city_area, String city) {
        this.shop_name = shop_name;
        this.shop_img = shop_img;
        this.shop_lat_lang = shop_lat_lang;
        this.city_area = city_area;
        this.city = city;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ShopModel() {

    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getShop_img() {
        return shop_img;
    }

    public void setShop_img(String shop_img) {
        this.shop_img = shop_img;
    }

    public String getShop_lat_lang() {
        return shop_lat_lang;
    }

    public void setShop_lat_lang(String shop_lat_lang) {
        this.shop_lat_lang = shop_lat_lang;
    }

    public String getCity_area() {
        return city_area;
    }

    public void setCity_area(String city_area) {
        this.city_area = city_area;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
