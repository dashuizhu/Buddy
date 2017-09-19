package com.example.administrator.buddy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhuj on 2017/8/16 10:48.
 */
public class BootInterfaceActivity extends FragmentActivity {
    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bootinterface);
        List<Fragment> fragments =new ArrayList<Fragment>();
        fragments.add(new FragmentOne());
        fragments.add(new FragmentTwo());
        fragments.add(new FragmentThree());
        FragAdapter adapter =new FragAdapter(getSupportFragmentManager(), fragments);
        //设定适配器
        ViewPager vp = (ViewPager)findViewById(R.id.tv_frag);
        vp.setAdapter(adapter);
    }


    public class FragAdapter extends FragmentPagerAdapter {

        protected List<Fragment> slidefarAdapter;//声明一个列表

        //请了一个Fragment的List对象，用于保存用于滑动的Fragment对象，并在创造函数中初始化
        public FragAdapter(FragmentManager fm,List<Fragment> farAdapter) {
            super(fm);
            slidefarAdapter =farAdapter;
        }
        //得到参数位置slide
        @Override public Fragment getItem(int slide) {
            return slidefarAdapter.get(slide);
        }
        //得到数量
        @Override public int getCount() {
            return slidefarAdapter.size();
        }

    }




}




