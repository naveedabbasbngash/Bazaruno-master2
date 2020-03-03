package com.example.bazaruno.Model;

public class Users {

    private String id, email,username,mobile_no,cnic,shop_name,shop_address,shop_lat_lang,city,city_area
            ,bazzar,password,status,type,shop_image;

    public Users() {
    }

    public Users(String email, String username, String mobile_no, String cnic, String shop_name, String shop_address, String shop_lat_lang, String city,
                 String city_area, String bazzar, String password, String status, String type) {
        this.email = email;
        this.username = username;
        this.mobile_no = mobile_no;
        this.cnic = cnic;
        this.shop_name = shop_name;
        this.shop_address = shop_address;
        this.shop_lat_lang = shop_lat_lang;
        this.city = city;
        this.city_area = city_area;
        this.bazzar = bazzar;
        this.password = password;
        this.status = status;
        this.type = type;
    }

    public String getShop_image() {
        return shop_image;
    }

    public void setShop_image(String shop_image) {
        this.shop_image = shop_image;
    }

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getCnic() {
        return cnic;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getShop_address() {
        return shop_address;
    }

    public void setShop_address(String shop_address) {
        this.shop_address = shop_address;
    }

    public String getShop_lat_lang() {
        return shop_lat_lang;
    }

    public void setShop_lat_lang(String shop_lat_lang) {
        this.shop_lat_lang = shop_lat_lang;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity_area() {
        return city_area;
    }

    public void setCity_area(String city_area) {
        this.city_area = city_area;
    }

    public String getBazzar() {
        return bazzar;
    }

    public void setBazzar(String bazzar) {
        this.bazzar = bazzar;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
