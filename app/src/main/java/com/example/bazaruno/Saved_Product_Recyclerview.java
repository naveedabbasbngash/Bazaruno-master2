package com.example.bazaruno;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.location.Location;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.example.bazaruno.AppConstants.AppConstant;
import com.example.bazaruno.DB.DBAdapter;
import com.example.bazaruno.Helpers.MySharePreferences;
import com.example.bazaruno.Model.ItemModel;
import com.example.bazaruno.Services.VolleyService;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Saved_Product_Recyclerview extends RecyclerView.Adapter<Saved_Product_Recyclerview.View_Holder> {

    ArrayList<ItemModel>  list;
    Context context;
    boolean show;
    View view;
    private VolleyService volleyService;

    public Saved_Product_Recyclerview( Activity context,ArrayList<ItemModel> list,boolean show) {
        this.list = list;
        this.context = context;
        this.show=show;
        volleyService=new VolleyService(context);
    }

    @NonNull
    @Override
    public View_Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater=LayoutInflater.from(viewGroup.getContext());

        if (show == true)
        {
            view=inflater.inflate(R.layout.saved_product_recyclerview,viewGroup,false);
        }
        else if (show == false)
        {
            view=inflater.inflate(R.layout.shop_recyclerview,viewGroup,false);
        }

        return new View_Holder(view);
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onBindViewHolder(@NonNull final View_Holder view_holder, final int i) {

        final ItemModel container=list.get(i);
        if (show == true)
        {
            view_holder.title.setText(container.getItem_name());
            view_holder.shop.setText(container.getShop_name());
            view_holder.price.setText("Rs-"+container.getItem_price());
            if (container.getItem_images_url() != "")
            {
                List<String> items = Arrays.asList(container.getItem_images_url().split("\\s*,\\s*"));

                Log.d(AppConstant.TAG+" : Saved Product Recycleview :","onBindViewHolder Data"+
                        "image url"+container.getItem_images_url()+" single image :"+items.get(0));
                Picasso.get().load(AppConstant.DomainName+AppConstant.Dir +items.get(0)).into(view_holder.image, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        view_holder.progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
            }

            if (i == list.size()-1)
            {
                view_holder.last.setPadding(0,0,0,150);
            }

            view_holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    MySharePreferences mySharePreferences=new MySharePreferences();
                    mySharePreferences.SaveItemData(context,container);
                    context.startActivity(new Intent(context, Item_Details.class));


                }
            });
            view_holder.location.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    BringLocationData(container.getShop_Id());
                }
            });
            view_holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DBAdapter dbAdapter=new DBAdapter(context);
                    dbAdapter.removeSingleContact(container.getShop_Id());
                    removeAt(i);

                }
            });
        }

        else if (show == false)
        {
            view_holder.title.setText(container.getItem_name());
            view_holder.shop.setText(container.getShop_name());
            if (i == list.size()-1)
            {
                view_holder.last.setPadding(0,0,0,150);
            }
            final List<String> items = Arrays.asList(container.getItem_images_url().split("\\s*,\\s*"));

            Picasso.get().load(AppConstant.DomainName+AppConstant.Dir +items.get(0)).into(view_holder.image, new com.squareup.picasso.Callback() {
                @Override
                public void onSuccess() {
                    view_holder.progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onError(Exception e) {

                }
            });

            // onclick listner
            view_holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   Intent intent=new Intent(context,Shop_Details_Activity.class);
                   intent.putExtra("name",container.getItem_name());
                   intent.putExtra("city",container.getShop_name());
                   intent.putExtra("image",items.get(0));
                   context.startActivity(intent);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class View_Holder extends RecyclerView.ViewHolder
    {
        TextView title;
        TextView shop;
        TextView price;
        ImageView image;
        Button view,location,delete;
        ProgressBar progressBar;
        RelativeLayout last;


        public View_Holder(@NonNull View itemView) {
            super(itemView);

            if (show == true)
            {
                title=(TextView) itemView.findViewById(R.id.title);
                shop=(TextView) itemView.findViewById(R.id.shop);
                price=(TextView) itemView.findViewById(R.id.price);
                image=(ImageView) itemView.findViewById(R.id.image);
                view=(Button) itemView.findViewById(R.id.view);
                delete=(Button) itemView.findViewById(R.id.remove);
                location=(Button) itemView.findViewById(R.id.location);
                progressBar=(ProgressBar) itemView.findViewById(R.id.progress);
                last=(RelativeLayout) itemView.findViewById(R.id.last);
            }
            else if (show == false)
            {
              title=(TextView) itemView.findViewById(R.id.shop_title);
              shop=(TextView) itemView.findViewById(R.id.city);
              image=(ImageView) itemView.findViewById(R.id.shop_image);
              view=(Button) itemView.findViewById(R.id.visit);
              location=(Button) itemView.findViewById(R.id.shop_location);
              progressBar=(ProgressBar) itemView.findViewById(R.id.progress);
                last=(RelativeLayout) itemView.findViewById(R.id.last);
            }

        }
    }

    public void BringLocationData(String getShop_Id){
        final ProgressDialog progress = new ProgressDialog(context);
        progress.setTitle("Loading");
        progress.setMessage("Wait while loading...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();
// To dismiss the dialog

        volleyService.GetItemLocation(AppConstant.DomainName + AppConstant.Dir + AppConstant.get_item_location,
                getShop_Id, new VolleyService.VolleyResponseListener() {
                    @Override
                    public void onSuccess(String response) {
                        Log.d(AppConstant.TAG+": Item location",response);
                        try {
                            JSONArray jsonArray=new JSONArray(response);
                            progress.dismiss();
                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject= (JSONObject) jsonArray.get(i);
//                                area=jsonObject.getString("city_area");
                                String shop_lat_lang = jsonObject.getString("shop_lat_lang");
                                List<String> items = Arrays.asList(shop_lat_lang.split("\\s*,\\s*"));

                                String strUri = "http://maps.google.com/maps?q=loc:" + items.get(0) + "," + items.get(1) + " (" + "Label which you want" + ")";
                                Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(strUri));

                                intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");

                                context.startActivity(intent);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            progress.dismiss();
                        }

                    }

                    @Override
                    public void onError(VolleyError error) {
                        progress.dismiss();

                    }
                });


    }

    public void removeAt(int position) {
        list.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, list.size());
    }
}
