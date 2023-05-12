package com.example.aidietician;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Adapterexercise extends RecyclerView.Adapter<Adapterexercise.ViewHolder> {

    Exercisemodel[] emodel;
    Context context;

    public Adapterexercise(Exercisemodel[] emodel, Exercisechart echart){
        this.emodel = emodel;
        this.context = echart;
    }

    @NonNull
    @Override
    public Adapterexercise.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.exercise_details,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Adapterexercise.ViewHolder holder, int position) {
        final Exercisemodel exercisemodel = emodel[position];
        holder.exerciseName.setText(exercisemodel.getExerciseName());
        holder.exerciseDesc.setText(exercisemodel.getExerciseDesc());
        holder.caloriesBurned.setText(exercisemodel.getCaloriesBurned());
        holder.image.setImageResource(exercisemodel.getImg());
    }

    @Override
    public int getItemCount() {
        return emodel.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView exerciseName,exerciseDesc,caloriesBurned;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.image = itemView.findViewById(R.id.plank);
            this.exerciseName = itemView.findViewById(R.id.exerciseName);
            this.exerciseDesc = itemView.findViewById(R.id.exerciseDesc);
            this.caloriesBurned = itemView.findViewById(R.id.calorieBurned);
        }
    }
}
