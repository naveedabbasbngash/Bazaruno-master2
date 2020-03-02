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

import java.util.ArrayList;

public class Saved_Products extends Fragment{

    View view;

    public Saved_Products() {

    }

    @SuppressLint("LongLogTag")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.saved_product,container,false);

        RecyclerView recyclerView=(RecyclerView) view.findViewById(R.id.recycleview);
        recyclerView.setHasFixedSize(true);
        ArrayList<Saved_Data_Container> list=new ArrayList<>();

        Saved_Data_Container container1=new Saved_Data_Container();
        container1.setName("Best T-shirt");
        container1.setImage("https://www.misupplies.co.uk/images/dickies-workwear-plain-t-shirt-p56517-919278_image.jpg");
        container1.setPrice("343");
        container1.setShop("DA shop");

        Saved_Data_Container container2=new Saved_Data_Container();
        container2.setName("Sport shirts");
        container2.setImage("https://www.kitlocker.com/images/stories/virtuemart/product/E546668-720-A.jpg");
        container2.setPrice("3324");
        container2.setShop("Wadud Shop");

        Saved_Data_Container container3=new Saved_Data_Container();
        container3.setName("Samsung A10");
        container3.setImage("https://pisces.bbystatic.com/image2/BestBuy_US/images/products/6251/6251693cv2d.jpg");
        container3.setPrice("343");
        container3.setShop("SD mobile");

        Saved_Data_Container container4=new Saved_Data_Container();
        container4.setName("Iphone 7");
        container4.setImage("https://i.expansys.net/img/b/316057/samsung-galaxy-s10-dual-sim-sm-g9730.jpg");
        container4.setPrice("1212");
        container4.setShop("GN store");
        DBAdapter dbAdapter=new DBAdapter(getActivity());
        ArrayList<ItemModel> fav =dbAdapter.retrieveSpacecrafts();
        Log.d(AppConstant.TAG+": Saved product in database size =",fav.size()+" Here");
        for (int i=0;i<fav.size();i++){
            ItemModel itemModel=fav.get(i);
            Log.d(AppConstant.TAG+": Saved Data i"+i+" ",
                    "item name = "+itemModel.getItem_name()+
                            " Shop Name = "+itemModel.getShop_name()+
                            " Price = "+itemModel.getItem_price()+
                            " image urls = "+itemModel.getItem_images_url());
        }



        list.add(container1);
        list.add(container2);
        list.add(container3);
        list.add(container4);

        Saved_Product_Recyclerview adapter=new Saved_Product_Recyclerview(getActivity(),fav,true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));


        return view;
    }
}
