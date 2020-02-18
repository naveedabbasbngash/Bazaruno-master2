package com.example.bazaruno;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Clothes_RecycleViw extends RecyclerView.Adapter<Clothes_RecycleViw.View_Holder> {

    ArrayList<Clothes_Recycler_Data_Container> containers;
    Context context;

    public Clothes_RecycleViw(Context context,ArrayList<Clothes_Recycler_Data_Container> containers){
        this.containers=containers;
        this.context=context;
    }

    @NonNull
    @Override
    public View_Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.clothes_recycler_view,viewGroup,false);
        return new View_Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final View_Holder view_holder, int i) {
      Clothes_Recycler_Data_Container clothes=containers.get(i);

      if (clothes.getImage() != "")
      {
          Picasso.get().load(clothes.getImage()).into(view_holder.imageView, new
                  com.squareup.picasso.Callback() {
                      @Override
                      public void onSuccess() {
                          view_holder.progressBar.setVisibility(View.GONE);
                      }

                      @Override
                      public void onError(Exception e) {

                      }
                  });
      }

        view_holder.textView.setText(clothes.getName());
    }

    @Override
    public int getItemCount() {
        return containers.size();
    }

    class View_Holder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView;
        ProgressBar progressBar;

        public View_Holder(@NonNull View itemView) {
            super(itemView);
            imageView=(ImageView) itemView.findViewById(R.id.image);
            textView=(TextView) itemView.findViewById(R.id.text);
            progressBar=(ProgressBar) itemView.findViewById(R.id.progress);
        }
    }

}
