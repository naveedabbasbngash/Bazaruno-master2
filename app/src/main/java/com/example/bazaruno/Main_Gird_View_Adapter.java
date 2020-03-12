package com.example.bazaruno;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.bazaruno.Helpers.MySharePreferences;
import com.example.bazaruno.Model.ItemModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Main_Gird_View_Adapter extends ArrayAdapter<ItemModel> {

    Context context;
    ArrayList<ItemModel> list;

    public Main_Gird_View_Adapter(Context context,ArrayList<ItemModel> list) {
        super(context,R.layout.main_gird_view, list);
        this.context=context;
        this.list=list;
    }


    @Override
    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        view=inflater.inflate(R.layout.main_gird_view,parent,false);


        ImageView imageView=(ImageView) view.findViewById(R.id.image);
        TextView textView=(TextView) view.findViewById(R.id.text);
        TextView rating=(TextView) view.findViewById(R.id.rating);
        TextView price=(TextView) view.findViewById(R.id.price);
        final ProgressBar progressBar=(ProgressBar) view.findViewById(R.id.progress);

        final ItemModel data_container=list.get(position);

        if (data_container.getItem_images_url() != "")
        {
            String output = list.get(position).getItem_images_url().substring(0, list.get(position).getItem_images_url().indexOf(','));
            Picasso.get().load("http://34.74.24.185:9999/upload/"+output).resize(110,150).into(imageView, new com.squareup.picasso.Callback() {
                @Override
                public void onSuccess() {
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onError(Exception e) {

                }
            });
        }

        textView.setText(data_container.getItem_name());
//        rating.setText(Html.fromHtml("&#9733;") + " "+data_container.getRating());
        price.setText("Rs : "+String.valueOf(data_container.getItem_price()));

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MySharePreferences mySharePreferences=new MySharePreferences();
                ItemModel itemModel=new ItemModel();

                itemModel.setItem_name(data_container.getItem_name());
                itemModel.setId(data_container.getId());
                itemModel.setShop_Id(data_container.getShop_Id());
                itemModel.setShop_name(data_container.getShop_name());
                itemModel.setMain_cat(data_container.getMain_cat());
                itemModel.setSub_sub_cat(data_container.getSub_sub_cat());
                itemModel.setSize(data_container.getSize());
                itemModel.setColor(data_container.getColor());
                itemModel.setBrand_name(data_container.getBrand_name());
                itemModel.setItem_images_url(data_container.getItem_images_url());
                itemModel.setItem_price(data_container.getItem_price());
                itemModel.setItem_descount(data_container.getItem_descount());
                itemModel.setItem_city(data_container.getItem_city());
                itemModel.setItem_bazzar(data_container.getItem_bazzar());
                mySharePreferences.SaveItemData(context,itemModel);
                context.startActivity(new Intent(context,Item_Details.class));


            }
        });
        return view;
    }
}
