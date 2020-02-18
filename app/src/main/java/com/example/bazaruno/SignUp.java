package com.example.bazaruno;

import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;

public class SignUp extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

       tabLayout =(TabLayout) findViewById(R.id.tablayout);
       viewPager=(ViewPager) findViewById(R.id.viewpager);

       SignUp_ViewPager_Adapter adapter=new SignUp_ViewPager_Adapter(getSupportFragmentManager());
       adapter.addFragment(new SignUp_Seller(),"Are you a seller?");
       adapter.addFragment(new SignUp_Customer(),"Are you a customer?");
       viewPager.setAdapter(adapter);
       tabLayout.setupWithViewPager(viewPager);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home)
        finish();
        return super.onOptionsItemSelected(item);
    }
}
