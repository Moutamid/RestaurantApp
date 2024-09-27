package com.moutamid.easyroomapp.landlord;


import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.moutamid.easyroomapp.Fragment.FavouriteFragment;
import com.moutamid.easyroomapp.Fragment.InboxFragment;
import com.moutamid.easyroomapp.Fragment.ProfileFragment;
import com.moutamid.easyroomapp.R;
import com.moutamid.easyroomapp.landlord.Fragment.VillaFragment;
import com.moutamid.easyroomapp.landlord.helper.Config;



public class MainActivity extends AppCompatActivity {

    BottomNavigationView binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_owner);
        binding = findViewById(R.id.bottomNavigationView);
        Config.checkApp(MainActivity.this);
        replaceFragment(new com.moutamid.easyroomapp.landlord.Fragment.VillaFragment());
        binding.setBackground(null);
        binding.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.home) {
                    replaceFragment(new com.moutamid.easyroomapp.landlord.Fragment.VillaFragment());
                }else if (item.getItemId() == R.id.types) {
                    replaceFragment(new ProfileFragment());
                }else if (item.getItemId() == R.id.inbox) {
                    replaceFragment(new InboxFragment());
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