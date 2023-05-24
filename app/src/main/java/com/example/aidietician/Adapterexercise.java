package com.example.aidietician;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapterexercise extends RecyclerView.Adapter<Adapterexercise.ViewHolder> {

    Context context;
    ArrayList<Exercisemodel> exercisemodels;

    public Adapterexercise(Context context, ArrayList<Exercisemodel> exercisemodels){
        this.context = context;
        this.exercisemodels = exercisemodels;
    }

    @NonNull
    @Override
    public Adapterexercise.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.exercise_details,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Adapterexercise.ViewHolder holder, int position) {
        holder.exerciseName.setText(exercisemodels.get(position).getExerciseName());
        holder.caloriesBurned.setText(exercisemodels.get(position).getCaloriesBurned());
        holder.image.setImageResource(exercisemodels.get(position).getImg());
    }

    @Override
    public int getItemCount() {
        return exercisemodels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView exerciseName,caloriesBurned;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.image = itemView.findViewById(R.id.plank);
            this.exerciseName = itemView.findViewById(R.id.exerciseName);
            this.caloriesBurned = itemView.findViewById(R.id.calorieBurned);
        }
    }
}
