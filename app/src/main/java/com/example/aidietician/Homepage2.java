package com.example.aidietician;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.ClipData;
import android.os.Bundle;

import com.example.aidietician.databinding.ActivityHomepage2Binding;
import com.example.aidietician.databinding.ActivityMainBinding;

public class Homepage2 extends AppCompatActivity {

    ActivityHomepage2Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomepage2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()){

                case R.id.menuHome:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.menuDiet:
                    replaceFragment(new DietFragment());
                    break;
                case R.id.menuFitness:
                    replaceFragment(new SettingsFragment());
                    break;
                case R.id.menuAccount:
                    replaceFragment(new ProfileFragment());
                    break;
            }
            return true;
        });
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
    }
}