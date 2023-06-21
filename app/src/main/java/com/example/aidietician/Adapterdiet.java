package com.example.aidietician;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

public class Adapterdiet extends BaseAdapter {

    Context context;
    String[] meal,calorie,item1,item2;
    int[] images;
    LayoutInflater inflater;

    public Adapterdiet(Context context, String[] meal,String [] calorie,String[] item1,
                       String[] item2,int[] images){
        this.context = context;
        this.meal = meal;
        this.calorie = calorie;
        this.item1 = item1;
        this.item2 = item2;
        this.images = images;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return meal.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.diet_details,null);
        TextView txtmeal = (TextView) convertView.findViewById(R.id.meal);
        TextView txtcalorie = (TextView) convertView.findViewById(R.id.calorie);
        TextView txtitem1 = (TextView) convertView.findViewById(R.id.item1);
        TextView txtitem2 = (TextView) convertView.findViewById(R.id.item2);
        ImageView mealimg = (ImageView) convertView.findViewById(R.id.breakfast);

        txtmeal.setText(meal[position]);
        txtcalorie.setText(calorie[position]);
        txtitem1.setText(item1[position]);
        txtitem2.setText(item2[position]);
        mealimg.setImageResource(images[position]);
        return convertView;
    }

}
