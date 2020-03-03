package com.example.bazaruno;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bazaruno.AppConstants.AppConstant;
import com.example.bazaruno.DB.DBAdapter;
import com.example.bazaruno.Model.ItemModel;
import com.example.bazaruno.Model.ShopModel;

import java.util.ArrayList;

public class Saved_Shop extends Fragment
{
    View view;

    public Saved_Shop() {

    }

    @SuppressLint("LongLogTag")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.saved_shop,container,false);

        RecyclerView recyclerView=(RecyclerView) view.findViewById(R.id.recycleview);
        recyclerView.setHasFixedSize(true);
        ArrayList<Saved_Data_Container> list=new ArrayList<>();

        Saved_Data_Container container1=new Saved_Data_Container();
        container1.setName("Wadood sons");
        container1.setImage("https://www.bostonmagazine.com/wp-content/uploads/sites/2/2019/03/stop-shop-strike-vote-t.jpg");
        container1.setShop("Saddar, Peshawar");

        Saved_Data_Container container2=new Saved_Data_Container();
        container2.setName("SD Moblie");
        container2.setImage("https://media1.fdncms.com/stranger/imager/u/original/25099185/sub.png");
        container2.setShop("Saddar, Karachi");


        DBAdapter dbAdapter=new DBAdapter(getActivity());
        ArrayList<ShopModel> fav =dbAdapter.retrieveShop();
        Log.d(AppConstant.TAG+": Saved product in database size =",fav.size()+" Here");
        for (int i=0;i<fav.size();i++){
            ShopModel itemModel=fav.get(i);
            Log.d(AppConstant.TAG+": Saved Data i"+i+" ",
                    "shop id = "+itemModel.getId()+
                            " Shop Name = "+itemModel.getShop_name()+
                            " Price = "+itemModel.getShop_lat_lang()+
                            " image urls = "+itemModel.getShop_img());
        }
        list.add(container1);
        list.add(container2);
        list.add(container1);
        list.add(container1);


        Saved_Shop_Recyclerview adapter=new Saved_Shop_Recyclerview(getActivity(),fav,true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        return view;
    }
}