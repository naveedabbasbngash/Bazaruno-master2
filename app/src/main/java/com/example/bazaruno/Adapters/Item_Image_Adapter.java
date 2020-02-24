package com.example.bazaruno.Adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.bazaruno.R;

import java.util.ArrayList;

public class Item_Image_Adapter extends RecyclerView.Adapter<Item_Image_Adapter.MyViewHolder> {

    public ArrayList moviesList=new ArrayList();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView title, year, genre;

        public MyViewHolder(View view) {
            super(view);
            title = (ImageView) view.findViewById(R.id.item_image2);

        }
    }


    public Item_Image_Adapter(ArrayList moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_image_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

       /* Bitmap bmImg = BitmapFactory.decodeFile(String.valueOf(moviesList.get(position)));

        holder.title.setImageBitmap(bmImg);*/
        Log.d("my image paths", String.valueOf(moviesList.get(position)));
        Bitmap bmImg = BitmapFactory.decodeFile(String.valueOf(moviesList.get(position)));

        holder.title.setImageBitmap(bmImg);



    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}