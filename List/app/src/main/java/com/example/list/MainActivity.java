package com.example.list;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.widget.ListView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView lvMonAn;
    ArrayList<MonAn> arrayMonAn;
    FoodAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageSlider imageSlider = findViewById(R.id.slider);

        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.banner1));
        slideModels.add(new SlideModel(R.drawable.banner5));
        slideModels.add(new SlideModel(R.drawable.banner3));
        slideModels.add(new SlideModel(R.drawable.banner4));
        imageSlider.setImageList(slideModels,false);

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
}