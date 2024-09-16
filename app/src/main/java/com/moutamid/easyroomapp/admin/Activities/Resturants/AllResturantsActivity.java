package com.moutamid.easyroomapp.admin.Activities.Resturants;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.moutamid.halalfoodadmin.Adapter.AllResturantsAdapter;
import com.moutamid.halalfoodadmin.Model.ResturantModel;
import com.moutamid.halalfoodadmin.R;
import com.moutamid.halalfoodadmin.helper.Config;

import java.util.ArrayList;
import java.util.List;

public class AllResturantsActivity extends AppCompatActivity {

    RecyclerView content_rcv;

    public List<ResturantModel> productModelList = new ArrayList<>();
    AllResturantsAdapter model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_resturants);
        content_rcv = findViewById(R.id.content_rcv);
        content_rcv.setLayoutManager(new GridLayoutManager(this, 1));
        model = new AllResturantsAdapter(this, productModelList);
        content_rcv.setAdapter(model);



        if (Config.isNetworkAvailable(AllResturantsActivity.this)) {
            getProducts();
        } else {
            Toast.makeText(AllResturantsActivity.this, "No network connection available.", Toast.LENGTH_SHORT).show();
        }

    }

    public void add_details(View view) {
        Intent intent = new Intent(this, AddResturantsActivity.class);
//        intent.putExtra("item_name", "");
//        intent.putExtra("item_barcode", "");
//        intent.putExtra("item_category", "");
//        intent.putExtra("item_type", "");
//        intent.putExtra("key", "");
        startActivity(intent);
    }

    private void getProducts() {
        Config.databaseReference().child("Restaurants").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                productModelList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    ResturantModel herbsModel = ds.getValue(ResturantModel.class);
                    productModelList.add(herbsModel);
                }
                model.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Config.dismissProgressDialog();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Config.isNetworkAvailable(AllResturantsActivity.this)) {
            getProducts();
        } else {
            Toast.makeText(AllResturantsActivity.this, "No network connection available.", Toast.LENGTH_SHORT).show();
        }
    }

    public void backpress(View view) {
        onBackPressed();
    }
}