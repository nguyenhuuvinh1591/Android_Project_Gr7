package com.example.list;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;


import android.os.Bundle;
import android.widget.ListView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    ListView lvMonAn;
    ArrayList<MonAn> arrayMonAn;
    FoodAdapter adapter;
    ViewPager mViewPager;

    int[] images = {R.drawable.banner1, R.drawable.banner3, R.drawable.banner4, R.drawable.banner5};

    ViewPagerAdapter mViewPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mViewPager = (ViewPager)findViewById(R.id.viewPagerMain);
        mViewPagerAdapter = new ViewPagerAdapter(MainActivity.this, images);
        mViewPager.setAdapter(mViewPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabDots);
        tabLayout.setupWithViewPager(mViewPager, true);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(), 2000, 4000);

        AnhXa();

        adapter = new FoodAdapter(this, R.layout.dong_mon_an, arrayMonAn);
        lvMonAn.setAdapter(adapter);


    }
    private void AnhXa(){
        lvMonAn = (ListView) findViewById(R.id.listviewMonAn);
        arrayMonAn = new ArrayList<>();
        arrayMonAn.add(new MonAn("Bưởi", "50.000đ", R.drawable.buoi));
        arrayMonAn.add(new MonAn("Cam", "60.000đ", R.drawable.cam));
        arrayMonAn.add(new MonAn("Nho", "70.000đ", R.drawable.nho));
        arrayMonAn.add(new MonAn("Táo", "80.000đ", R.drawable.tao));
        arrayMonAn.add(new MonAn("Sầu Riêng", "90.000đ", R.drawable.saurieng));
        arrayMonAn.add(new MonAn("Măng Cụt", "100.000đ", R.drawable.mangcut));
        arrayMonAn.add(new MonAn("Dưa Hấu", "110.000đ", R.drawable.duahau));
        arrayMonAn.add(new MonAn("Bơ", "120.000đ", R.drawable.bo));
        arrayMonAn.add(new MonAn("Đu Đủ", "130.000đ", R.drawable.dudu));
    }

    public class MyTimerTask extends TimerTask {

        @Override
        public void run() {

            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mViewPager.getCurrentItem() == 0) {
                        mViewPager.setCurrentItem(1);
                    }
                    else if (mViewPager.getCurrentItem() == 1) {
                        mViewPager.setCurrentItem(2);
                    }
                    else if (mViewPager.getCurrentItem() == 2) {
                        mViewPager.setCurrentItem(3);
                    }
                    else {
                        mViewPager.setCurrentItem(0);
                    }
                }
            });
        }
    }
}