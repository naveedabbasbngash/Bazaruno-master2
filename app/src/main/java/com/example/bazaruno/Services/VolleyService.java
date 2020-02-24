package com.example.bazaruno.Services;

import android.content.Context;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bazaruno.Model.ItemModel;
import com.example.bazaruno.Model.Users;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;



/**
 * Created by Jaffar on 11/22/2016.
 */
public class VolleyService {

    private Context mContext;

    public VolleyService(Context mContext) {
        this.mContext = mContext;
    }

    /*
    * Register In Seller
    * */

    public void RegisterUser(String url, final Users user, final VolleyResponseListener volleyResponseListener){
        try {
            final RequestQueue queue = Volley.newRequestQueue(mContext);

            StringRequest req = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            volleyResponseListener.onSuccess(s);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    volleyResponseListener.onError(volleyError);
                    Log.v("see error responce",volleyError.toString());
                }
            })

            {


                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    Log.v("see error responce",response.toString());
                    return super.parseNetworkResponse(response);


                }

                @Override
                protected Map<String, String> getParams(){
                    HashMap<String, String> params = new HashMap<String, String>();
                    params.put("seller",user.getType());
                    params.put("email",user.getEmail());
                    params.put("username",user.getUsername());
                    params.put("mobile_no",user.getMobile_no());
                    params.put("cnic",user.getCnic());
                    params.put("shop_name",user.getShop_name());
                    params.put("shop_address",user.getShop_address());
                    params.put("shop_lat_lang",user.getShop_lat_lang());
                    params.put("city",user.getCity());
                    params.put("city_area",user.getCity_area());
                    params.put("bazzar",user.getBazzar());
                    params.put("password",user.getPassword());
                    params.put("status",user.getStatus());



                    return params;
                }
            };
            queue.add(req);



        }catch (Exception e){
            Log.v("see error responce",e.toString());

        }

    }

    /*
    * Register Buyer
    * */
    public void RegisterUserBuyer(String url, final Users user, final VolleyResponseListener volleyResponseListener){
        try {
            final RequestQueue queue = Volley.newRequestQueue(mContext);

            StringRequest req = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            volleyResponseListener.onSuccess(s);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    volleyResponseListener.onError(volleyError);
                    Log.v("see error responce",volleyError.toString());
                }
            })

            {


                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    Log.v("see error responce",response.toString());
                    return super.parseNetworkResponse(response);


                }

                @Override
                protected Map<String, String> getParams(){
                    HashMap<String, String> params = new HashMap<String, String>();
                    params.put("buyer",user.getType());
                    params.put("email",user.getEmail());
                    params.put("username",user.getUsername());
                    params.put("password",user.getPassword());
                    params.put("status",user.getStatus());



                    return params;
                }
            };
            queue.add(req);



        }catch (Exception e){
            Log.v("see error responce",e.toString());

        }

    }
/*
* sign Seller And Buyer
* */
    public void SignInNow(String url, final Users user, final VolleyResponseListener volleyResponseListener){
        try {
            final RequestQueue queue = Volley.newRequestQueue(mContext);

            StringRequest req = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            volleyResponseListener.onSuccess(s);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    volleyResponseListener.onError(volleyError);
                    Log.v("see error responce",volleyError.toString());
                }
            })

            {


                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    Log.v("see error responce",response.toString());
                    return super.parseNetworkResponse(response);


                }

                @Override
                protected Map<String, String> getParams(){
                    HashMap<String, String> params = new HashMap<String, String>();
                    params.put("email",user.getEmail());
                    params.put("password",user.getPassword());
                    params.put("status",user.getStatus());



                    return params;
                }
            };
            queue.add(req);



        }catch (Exception e){
            Log.v("see error responce",e.toString());

        }

    }

    /*
    * Add Item By Buyer
    * */
    public void AddItem(String url, final ItemModel itemModel, final VolleyResponseListener volleyResponseListener){
        try {
            final RequestQueue queue = Volley.newRequestQueue(mContext);

            StringRequest req = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                            volleyResponseListener.onSuccess(s);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    volleyResponseListener.onError(volleyError);
                    Log.v("see error responce",volleyError.toString());
                }
            })

            {


                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    Log.v("see error responce",response.toString());
                    return super.parseNetworkResponse(response);


                }

                @Override
                protected Map<String, String> getParams(){
                    HashMap<String, String> params = new HashMap<String, String>();
                    params.put("shop_Id",itemModel.getId());
                    params.put("item_name",itemModel.getItem_name());
                    params.put("shop_name",itemModel.getShop_name());
                    params.put("main_cat",itemModel.getMain_cat());
                    params.put("sub_cat",itemModel.getSub_cat());
                    params.put("sub_sub_cat",itemModel.getSub_sub_cat());
                    params.put("size",itemModel.getSize());
                    params.put("brand_name",itemModel.getBrand_name());
                    params.put("item_images_url",itemModel.getItem_images_url());
                    params.put("item_price",itemModel.getItem_price());
                    params.put("item_descount",itemModel.getItem_descount());
                    params.put("color",itemModel.getColor());



                    return params;
                }
            };
            queue.add(req);



        }catch (Exception e){
            Log.v("see error responce",e.toString());

        }

    }



    /*------------------------------------------------- /For Headers----------------------------------------------------------*/
    public interface VolleyResponseListener {
      void onSuccess(String response);
        void onError(VolleyError error);
    }

}
