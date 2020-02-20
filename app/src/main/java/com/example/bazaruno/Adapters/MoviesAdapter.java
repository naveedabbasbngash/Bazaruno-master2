package com.example.bazaruno.Adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Movie;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.bazaruno.R;

import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {

    public ArrayList moviesList=new ArrayList();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView title, year, genre;

        public MyViewHolder(View view) {
            super(view);
            title = (ImageView) view.findViewById(R.id.item_image2);

        }
    }


    public MoviesAdapter(ArrayList moviesList) {
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