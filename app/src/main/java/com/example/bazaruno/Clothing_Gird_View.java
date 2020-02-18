package com.example.bazaruno;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Clothing_Gird_View extends ArrayAdapter<Clothes_Recycler_Data_Container> {

    Context context;
    ArrayList<Clothes_Recycler_Data_Container> containers;
    LayoutInflater inflater;

    public Clothing_Gird_View(Context context,ArrayList<Clothes_Recycler_Data_Container> containers)
    {
        super(context, R.layout.clothing_gird_view,containers);
        this.containers=containers;
        this.context=context;
    }


    @Override
    public View getView(int position, View view, ViewGroup parent) {

        inflater=LayoutInflater.from(parent.getContext());
        view=inflater.inflate(R.layout.clothing_gird_view,parent,false);


        ImageView imageView=(ImageView) view.findViewById(R.id.image);
        TextView textView=(TextView) view.findViewById(R.id.text);

        Clothes_Recycler_Data_Container data=containers.get(position);

        textView.setText(data.getName());
        if(data.getImage() != "")
        {
            Picasso.get().load(data.getImage()).resize(91,126).into(imageView);
        }

        return view;
    }
}
