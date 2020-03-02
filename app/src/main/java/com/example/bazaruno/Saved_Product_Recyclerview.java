package com.example.bazaruno;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.bazaruno.AppConstants.AppConstant;
import com.example.bazaruno.Model.ItemModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Saved_Product_Recyclerview extends RecyclerView.Adapter<Saved_Product_Recyclerview.View_Holder> {

    ArrayList<ItemModel>  list;
    Context context;
    boolean show;
    View view;

    public Saved_Product_Recyclerview( Activity context,ArrayList<ItemModel> list,boolean show) {
        this.list = list;
        this.context = context;
        this.show=show;
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
    public void onBindViewHolder(@NonNull final View_Holder view_holder, int i) {

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
                Picasso.get().load("https://kheloaurjeeto.net/bazarona/php/"+items.get(0)).into(view_holder.image, new com.squareup.picasso.Callback() {
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
                    //context.startActivity(new Intent(context,Location.class));

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

            Picasso.get().load("https://kheloaurjeeto.net/bazarona/php/"+items.get(0)).into(view_holder.image, new com.squareup.picasso.Callback() {
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
        Button view,location;
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
}
