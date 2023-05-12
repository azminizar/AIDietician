package com.example.aidietician;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Adapterdiet extends RecyclerView.Adapter<Adapterdiet.ViewHolder> {

    Dietmodel[] model;
    Context c;

    public Adapterdiet(Dietmodel[] model, Dietchart dietchart){
        this.model = model;
        this.c = dietchart;
    }

    @NonNull
    @Override
    public  ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.diet_details,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Dietmodel modellist = model[position];
        holder.meal.setText(modellist.getMeal());
        holder.calorie.setText(modellist.getCalorie());
        holder.item1.setText(modellist.getItem1());
        holder.item2.setText(modellist.getItem2());
        holder.imageView.setImageResource(modellist.getImg());
    }

    @Override
    public int getItemCount() {
        return model.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView meal,item1,item2,calorie;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.breakfast);
            this.meal = itemView.findViewById(R.id.meal);
            this.calorie = itemView.findViewById(R.id.calorie);
            this.item1 = itemView.findViewById(R.id.item1);
            this.item2 = itemView.findViewById(R.id.item2);
        }
    }
}
