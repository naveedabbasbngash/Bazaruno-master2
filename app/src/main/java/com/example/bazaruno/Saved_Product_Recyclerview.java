package com.example.bazaruno;

import android.app.Activity;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Saved_Product_Recyclerview extends RecyclerView.Adapter<Saved_Product_Recyclerview.View_Holder> {

    ArrayList<Saved_Data_Container>  list;
    Activity context;
    boolean show;
    View view;

    public Saved_Product_Recyclerview( Activity context,ArrayList<Saved_Data_Container> list,boolean show) {
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

    @Override
    public void onBindViewHolder(@NonNull final View_Holder view_holder, int i) {

        final Saved_Data_Container container=list.get(i);
        if (show == true)
        {
            view_holder.title.setText(container.getName());
            view_holder.shop.setText(container.getShop());
            view_holder.price.setText("Rs-"+container.getPrice());
            if (container.getImage() != "")
            {
                Picasso.get().load(container.getImage()).into(view_holder.image, new com.squareup.picasso.Callback() {
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
            view_holder.title.setText(container.getName());
            view_holder.shop.setText(container.getShop());
            if (i == list.size()-1)
            {
                view_holder.last.setPadding(0,0,0,150);
            }
            Picasso.get().load(container.getImage()).into(view_holder.image, new com.squareup.picasso.Callback() {
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
                   intent.putExtra("name",container.getName());
                   intent.putExtra("city",container.getShop());
                   intent.putExtra("image",container.getImage());
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
