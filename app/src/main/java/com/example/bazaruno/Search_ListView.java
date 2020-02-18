package com.example.bazaruno;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Search_ListView extends ArrayAdapter<String> {

    Context context;
    ArrayList<String> list;

    public Search_ListView(Context context, ArrayList<String> list) {
        super(context, R.layout.search_listview,list);
        this.context=context;
        this.list=list;
    }


    @Override
    public View getView(int position, View view,ViewGroup parent) {

        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        view=inflater.inflate(R.layout.search_listview,parent,false);

        TextView textView=(TextView) view.findViewById(R.id.text);
        textView.setText(list.get(position));

        return view;
    }
}
