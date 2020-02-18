package com.example.bazaruno;

import android.app.Activity;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class More_Recycleview extends RecyclerView.Adapter<More_Recycleview.View_Holder> {

    Activity context;
    ArrayList<String> name;
    ArrayList<Integer>  image;


    public More_Recycleview(Activity context, ArrayList<String> name,ArrayList<Integer> image) {
        this.context = context;
        this.name = name;
        this.image=image;
    }

    @NonNull
    @Override
    public View_Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater=LayoutInflater.from(viewGroup.getContext());
        View view=inflater.inflate(R.layout.more_recyclview,viewGroup,false);

        return new View_Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull View_Holder view_holder, int i) {

       view_holder.imageView.setImageResource(image.get(i));
       view_holder.textView.setText(name.get(i));
    }

    @Override
    public int getItemCount() {
        return image.size();
    }

    public class View_Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView textView;

        public View_Holder(@NonNull View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.image);
            textView = (TextView) itemView.findViewById(R.id.text);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            // click for my shop
            if (getLayoutPosition() == 0)
            {

            }
            // click for notification
            else if (getLayoutPosition() == 1)
            {

            }
            // click for my product review
            else if (getLayoutPosition() == 2)
            {

            }
            // click for payment
            else if (getLayoutPosition() == 3)
            {

            }
            //  for account option
            else if (getLayoutPosition() == 4)
            {
                context.startActivity(new Intent(context,Account_More.class));
            }
        }
    }
}
