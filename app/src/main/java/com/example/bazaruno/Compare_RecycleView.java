package com.example.bazaruno;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Compare_RecycleView extends PagerAdapter {

     ArrayList<Compare_Data_Container> list;
     Context context;
    private LayoutInflater inflater;
    ImageView backarrow;
    ImageView forwordarrow;
    ProgressBar progressBar;
    ImageView image;
    TextView name;
    TextView price_card;
    TextView rating_card;
    TextView type;
    TextView price;
    TextView color;
    TextView size;
    TextView rating;
    TextView shop_rating;

    public Compare_RecycleView(Context context,ArrayList<Compare_Data_Container> list) {
        this.list = list;
        this.context=context;
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
    public Object instantiateItem(@NonNull ViewGroup containers, int position) {

        inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=inflater.inflate(R.layout.compare_recycleview,containers,false);
        Compare_Data_Container container=list.get(position);

            progressBar=(ProgressBar) view.findViewById(R.id.progress);
            image=(ImageView) view.findViewById(R.id.image);
            name=(TextView) view.findViewById(R.id.name);
            price_card=(TextView) view.findViewById(R.id.price_card);
            rating_card=(TextView) view.findViewById(R.id.rating_card);
            type=(TextView) view.findViewById(R.id.type);
            price=(TextView) view.findViewById(R.id.price);
            color=(TextView) view.findViewById(R.id.color);
            size=(TextView) view.findViewById(R.id.size);
            rating=(TextView) view.findViewById(R.id.rating);
            shop_rating=(TextView) view.findViewById(R.id.shop_rating);

        if (container.getImage() != "")
        {
            Picasso.get().load(container.getImage()).into(image, new
                    com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                            progressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError(Exception e) {

                        }
                    });
        }

        name.setText(container.getName());
        price_card.setText("$"+container.getPrice());
        rating_card.setText(Html.fromHtml("&#9733;") + " "+container.getRating());
       type.setText(container.getType());
       price.setText("$"+container.getPrice());
      color.setText(container.getColor());
        size.setText(container.getSize());
       rating.setText(container.getRating());
        shop_rating.setText(container.getShop_rating());

            containers.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout) object);
    }

}
