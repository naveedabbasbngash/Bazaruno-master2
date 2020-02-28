package com.example.bazaruno.Model;

public class FilterModel {
String main_cat,sub_cat,item_city,item_bazzar,sub_sub_cat;

    public FilterModel() {
    }

    public FilterModel(String main_cat, String sub_cat, String item_city, String item_bazzar) {
        this.main_cat = main_cat;
        this.sub_cat = sub_cat;
        this.item_city = item_city;
        this.item_bazzar = item_bazzar;
    }

    public String getSub_sub_cat() {
        return sub_sub_cat;
    }

    public void setSub_sub_cat(String sub_sub_cat) {
        this.sub_sub_cat = sub_sub_cat;
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
}
