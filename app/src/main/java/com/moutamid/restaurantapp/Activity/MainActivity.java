package com.moutamid.restaurantapp.Activity;


import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.moutamid.restaurantapp.Fragment.FavouriteFragment;
import com.moutamid.restaurantapp.Fragment.ResturantFragment;
import com.moutamid.restaurantapp.Fragment.TypesFragment;
import com.moutamid.restaurantapp.R;
import com.moutamid.restaurantapp.helper.Config;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = findViewById(R.id.bottomNavigationView);
        Config.checkApp(MainActivity.this);
        replaceFragment(new ResturantFragment());
        binding.setBackground(null);
        binding.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.types) {
                    replaceFragment(new TypesFragment());
                } else if (item.getItemId() == R.id.home) {
                    replaceFragment(new ResturantFragment());
                }else if (item.getItemId() == R.id.favourite) {
                    replaceFragment(new FavouriteFragment());
                }
                return true;
            }
        });

    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }


}