package com.example.bazaruno;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class SignUp_ViewPager_Adapter extends FragmentPagerAdapter
{
     List<Fragment> fragment_item=new ArrayList<>();
     List<String> fragment_title=new ArrayList<>();

    public SignUp_ViewPager_Adapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return fragment_item.get(i);
    }

    @Override
    public int getCount() {
        return fragment_title.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragment_title.get(position);
    }

    void addFragment(Fragment fragment,String title)
    {
        fragment_item.add(fragment);
        fragment_title.add(title);
    }
}
