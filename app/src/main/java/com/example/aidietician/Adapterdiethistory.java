package com.example.aidietician;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapterdiethistory extends RecyclerView.Adapter<Adapterdiethistory.MyViewHolder>{

    Context context;
    ArrayList<Diethistorymodel> list;

    public Adapterdiethistory(Context context, ArrayList<Diethistorymodel> list) {
        this.context = context;
        this.list = list;
    }

    public  static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView Date,Time,Meal,Calorie;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            Date = itemView.findViewById(R.id.txtDate);
            Time = itemView.findViewById(R.id.txtTime);
            Meal = itemView.findViewById(R.id.txtMeal);
            Calorie = itemView.findViewById(R.id.txtCalorie);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycle_diet_history,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Diethistorymodel model = list.get(position);
        holder.Date.setText(model.getDate());
        holder.Meal.setText(model.getTime());
        holder.Meal.setText(model.getMeal());
        holder.Calorie.setText(model.getCalorie());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

