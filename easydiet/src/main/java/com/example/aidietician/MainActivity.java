package com.example.aidietician;

import android.app.Activity;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.widget.TextView;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aidietician.databinding.ActivityMainBinding;

public class MainActivity extends Activity {

    private TextView mTextView;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        
        createRecyclerView();
       // mTextView = binding.txtViewPlus;
    }

    private void createRecyclerView() {
        setContentView(R.layout.activity_main);
        RecyclerView recyclerList = (RecyclerView) findViewById(R.id.recyclerList);
        ItemsRVAdapter itemAdapter = new ItemsRVAdapter();
        recyclerList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerList.setAdapter(itemAdapter);
        recyclerList.setHasFixedSize(true);
    }
}