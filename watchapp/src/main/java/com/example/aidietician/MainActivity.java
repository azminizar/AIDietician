package com.example.aidietician;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aidietician.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;


public class MainActivity extends Activity {

    private TextView txtviewMinus,txtviewPlus,txtviewGlass;
    private ActivityMainBinding binding;
    Activity context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        createRecyclerView();
        txtviewMinus = findViewById(R.id.txtViewMinus);
        txtviewPlus = findViewById(R.id.txtViewPlus);
        txtviewGlass = findViewById(R.id.txtViewGlassNum);

        txtviewPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentVal = txtviewGlass.getText().toString();
                int value = Integer.parseInt(currentVal);
                value++;
                txtviewGlass.setText(String.valueOf(value));
            }
        });

        txtviewMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentVal = txtviewGlass.getText().toString();
                int value = Integer.parseInt(currentVal);
                value--;
                txtviewGlass.setText(String.valueOf(value));
            }
        });

//        String water = String.valueOf(txtviewGlass.getText());
//
//        PutDataMapRequest dataMap = PutDataMapRequest.create("/data-path"); // specify the path for your data
//        dataMap.getDataMap().putString("water", water);
//        PutDataRequest request = dataMap.asPutDataRequest();
//        Task<DataItem> putDataTask = Wearable.getDataClient(context).putDataItem(request);

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