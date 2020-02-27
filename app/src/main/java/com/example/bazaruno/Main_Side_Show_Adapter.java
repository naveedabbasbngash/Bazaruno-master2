package com.example.bazaruno;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Main_Side_Show_Adapter extends PagerAdapter {

    private Context context;
    public   ArrayList<Clothes_Recycler_Data_Container> list;
    private LayoutInflater inflater;

   public Main_Side_Show_Adapter(Context context,ArrayList<Clothes_Recycler_Data_Container> list)
    {
        this.context=context;
        this.list=list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {

        return (view == (RelativeLayout) o);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {

       final Clothes_Recycler_Data_Container data=list.get(position);
       inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       View view=inflater.inflate(R.layout.main_slide_show,container,false);
        ImageView imageViev=(ImageView) view.findViewById(R.id.image);
        TextView textView=(TextView) view.findViewById(R.id.text);
        final ProgressBar progressBar=(ProgressBar) view.findViewById(R.id.progress);
        RelativeLayout relativeLayout=(RelativeLayout) view.findViewById(R.id.relative);

        if (data.getImage() != "")
        {
            Picasso.get().load(data.getImage()).resize(500,200).into(imageViev, new com.squareup.picasso.Callback() {
                @Override
                public void onSuccess() {
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onError(Exception e) {

                }
            });
        }
        textView.setText(data.getName());

        // Click Listiner for slide show

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int other = 0;
                if (position==0){
                    other=0;

                }
                if (position==1){
                    other=2;

                }
                if (position==2){
                    other=4;

                }
                if (position==3){
                    other=6;

                }

                /*Toast.makeText(context, data.getName(), Toast.LENGTH_SHORT).show();*/
                context.startActivity(new Intent(context,Bazzar_Items.class).putExtra("upper",other));
            }
        });

        container.addView(view);

        return view;
     }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
       container.removeView((RelativeLayout) object);
    }
}
