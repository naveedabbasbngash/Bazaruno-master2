package com.example.bazaruno;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Main_Gird_View_Adapter extends ArrayAdapter<Gird_Data_Container> {

    Context context;
    ArrayList<Gird_Data_Container> list;

    public Main_Gird_View_Adapter(Context context,ArrayList<Gird_Data_Container> list) {
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

        Gird_Data_Container data_container=list.get(position);

        if (data_container.getImage() != "")
        {
            Picasso.get().load(data_container.getImage()).resize(110,150).into(imageView, new com.squareup.picasso.Callback() {
                @Override
                public void onSuccess() {
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onError(Exception e) {

                }
            });
        }

        textView.setText(data_container.getName());
        rating.setText(Html.fromHtml("&#9733;") + " "+data_container.getRating());
        price.setText("$"+String.valueOf(data_container.getPrice()));

        return view;
    }
}
