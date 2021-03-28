package com.example.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class FoodAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<MonAn> monAnList;



    public FoodAdapter(Context context, int layout, List<MonAn> monAnList) {
        this.context = context;
        this.layout = layout;
        this.monAnList = monAnList;
    }



    @Override
    public int getCount() {
        return monAnList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder {
        ImageView foodImage;
        TextView foodName;
        TextView foodPrice;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        ViewHolder holder;
        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder = new ViewHolder();


            //ánh xạ view
            holder.foodName = (TextView) view.findViewById(R.id.foodName);
            holder.foodPrice = (TextView) view.findViewById(R.id.foodPrice);
            holder.foodImage = (ImageView) view.findViewById(R.id.foodImage);
            view.setTag(holder);
        }

        else {
            holder = (ViewHolder) view.getTag();
        }


        //gán giá trị
        MonAn monAn = monAnList.get(position);
        holder.foodName.setText(monAn.getFoodName());
        holder.foodPrice.setText(monAn.getFoodPrice());
        holder.foodImage.setImageResource(monAn.getFoodImage());

        return view;
    }
}
