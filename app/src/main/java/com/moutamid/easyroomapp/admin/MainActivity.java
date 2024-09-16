package com.moutamid.easyroomapp.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.moutamid.halalfoodadmin.Activities.Like_Dislike.Like_Dislike_Activity;
import com.moutamid.halalfoodadmin.Activities.Products.AllProductsActivity;
import com.moutamid.halalfoodadmin.Activities.Resturants.AllResturantsActivity;
import com.moutamid.halalfoodadmin.Activities.User_product.UserProductActivity;
import com.moutamid.halalfoodadmin.helper.Config;

public class MainActivity extends AppCompatActivity {
    Button add_products, add_restuarant, like_dislike, user_products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        add_restuarant = findViewById(R.id.add_restuarant);
        like_dislike = findViewById(R.id.like_dislike);
        add_products = findViewById(R.id.add_products);
        user_products = findViewById(R.id.user_products);
        Config.checkApp(MainActivity.this);
        add_products.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AllProductsActivity.class));
            }
        });
        add_restuarant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AllResturantsActivity.class));
            }
        }); like_dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, Like_Dislike_Activity.class));
            }
        });
        user_products.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, UserProductActivity.class));
            }
        });
    }
}