package com.moutamid.easyroomapp.Activity;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.fxn.stash.Stash;
import com.google.firebase.auth.FirebaseAuth;
import com.moutamid.easyroomapp.Activity.Authentication.LoginActivity;
import com.moutamid.easyroomapp.Model.UserModel;
import com.moutamid.easyroomapp.R;


public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        setContentView(R.layout.activity_splash);

        int splashInterval = 3000;
        ImageView imageView = findViewById(R.id.logo);
        ObjectAnimator objectAnimator = ObjectAnimator.ofPropertyValuesHolder(
                imageView,
                PropertyValuesHolder.ofFloat("scaleX", 3.9F),

                PropertyValuesHolder.ofFloat("scaleY", 3.9F)
        );
        objectAnimator.setDuration(2000);
        objectAnimator.start();

        new Handler().postDelayed(this::goToApp, splashInterval);
    }

    public void goToApp() {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            UserModel userNew = (UserModel) Stash.getObject("UserDetails", UserModel.class);
            if (userNew!= null) {
                if (userNew.user_type.equals("Landlord")) {
                    Intent mainIntent = new Intent(SplashActivity.this, com.moutamid.easyroomapp.landlord.MainActivity.class);
                    mainIntent.putExtra("type", "null");
                    startActivity(mainIntent);
                    finish();
                }else {
                    Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                    mainIntent.putExtra("type", "null");
                    startActivity(mainIntent);
                    finish();
                }
            } else {
            Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
            mainIntent.putExtra("type", "null");
            startActivity(mainIntent);
            finish();
            }
        }
        else
        {
            Intent mainIntent = new Intent(SplashActivity.this, LoginActivity.class);
            mainIntent.putExtra("type", "null");
            startActivity(mainIntent);
            finish();
        }
    }
}