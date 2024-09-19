package com.moutamid.easyroomapp.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.fxn.stash.Stash;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.moutamid.easyroomapp.Adapter.ImageAdapter;
import com.moutamid.easyroomapp.Adapter.ImagePagerAdapter;
import com.moutamid.easyroomapp.Model.VillaModel;
import com.moutamid.easyroomapp.R;
import com.moutamid.easyroomapp.helper.Config;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.glailton.expandabletextview.ExpandableTextView;

public class EasyRoomDetailsActivity extends AppCompatActivity implements OnMapReadyCallback {
    VillaModel villaModel;
    Button map;
    ImageView favourite_img, unfavourite_img, image;
    String token_admin;
    RecyclerView recyclerView;
    private GoogleMap mMap;
    NumberPicker numberPicker;

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy_room_details);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            getWindow().getDecorView().setSystemUiVisibility(View);
        }

// Set the status bar background color to white
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#202020"));
        }
        villaModel = (VillaModel) Stash.getObject(Config.currentModel, VillaModel.class);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);
        recyclerView = findViewById(R.id.recyclerView);
        image = findViewById(R.id.image);
        map = findViewById(R.id.show_map);
        unfavourite_img = findViewById(R.id.unfavourite);
        favourite_img = findViewById(R.id.favourite);
        image = findViewById(R.id.image);
        unfavourite_img = findViewById(R.id.unfavourite);
        favourite_img = findViewById(R.id.favourite);
        // Assign IDs to views
        Toolbar toolbar = findViewById(R.id.toolbar_);
        ImageView backpress = findViewById(R.id.backpress);
        ImageView image = findViewById(R.id.image);
        TextView name = findViewById(R.id.name);
        TextView price = findViewById(R.id.price);
        name.setText(villaModel.getName());
        price.setText(villaModel.getBill() + " $/month");
        Glide.with(EasyRoomDetailsActivity.this).load(villaModel.getImage()).into(image);
        TextView descriptionTitle = findViewById(R.id.description_title);
        ExpandableTextView description = findViewById(R.id.description);
        description.setText(villaModel.getDescription());
        ImageView availability = findViewById(R.id.availability);
        showImagesInRecyclerView();
        TextView location = findViewById(R.id.location);
        location.setText(villaModel.getTitle());
//        availability.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                CalenderDialogClass cdd = new CalenderDialogClass(EasyRoomDetailsActivity.this);
//                cdd.show();
//            }
//        });
        DatabaseReference z = FirebaseDatabase.getInstance().getReference().child("RentApp").child("Admin");
        z.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Get the value of the "token" field
                token_admin = dataSnapshot.child("token").getValue().toString();

                // Use the token as needed
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("FirebaseError", "Failed to read value.", databaseError.toException());
            }
        });
        ArrayList<VillaModel> villaModels = Stash.getArrayList(Config.favourite, VillaModel.class);
        if (villaModels != null) {
            for (int i = 0; i < villaModels.size(); i++) {

                if (villaModel.getKey().equals(villaModels.get(i).getKey())) {
                    unfavourite_img.setVisibility(View.VISIBLE);

                } else {
                    favourite_img.setVisibility(View.VISIBLE);

                }

            }
        }
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("latlng", villaModel.getLat() + "   " + villaModel.getLng());

                if (villaModel.getLat() > -90 && villaModel.getLat() < 90 && villaModel.getLng() > -180 && villaModel.getLng() < 180) {
                    Intent intent = new Intent(EasyRoomDetailsActivity.this, MapActivity.class);
                    intent.putExtra("lat", villaModel.getLat());
                    intent.putExtra("lng", villaModel.getLng());
                    intent.putExtra("name", villaModel.getTitle());
                    startActivity(intent);

                } else {
                    Toast.makeText(EasyRoomDetailsActivity.this, "Invalid Coordinates to show marker", Toast.LENGTH_SHORT).show();
                }

            }
        });
        favourite_img.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                ArrayList<VillaModel> villaModelArrayList = Stash.getArrayList(Config.favourite, VillaModel.class);
                villaModelArrayList.add(villaModel);
                Stash.put(Config.favourite, villaModelArrayList);
                unfavourite_img.setVisibility(View.VISIBLE);
                favourite_img.setVisibility(View.GONE);
            }
        });
        unfavourite_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<VillaModel> villaArrayList = Stash.getArrayList(Config.favourite, VillaModel.class);
                for (int i = 0; i < villaArrayList.size(); i++) {
                    if (villaArrayList.get(i).getKey().equals(villaModel.getKey())) {
                        villaArrayList.remove(i);
                    }
                }
                Stash.put(Config.favourite, villaArrayList);
                unfavourite_img.setVisibility(View.GONE);
                favourite_img.setVisibility(View.VISIBLE);
            }
        });

    }

    public void onBack(View view) {
        onBackPressed();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)


    private void showImagesInRecyclerView() {
        // Retrieve VillaModel models from Stash

        if (villaModel != null) {
            // Process the VillaModel models and display images in RecyclerView
            List<String> imageUrls = new ArrayList<>();

            Map<String, String> images = villaModel.getImages();
            if (images != null) {
                for (String imageUrl : images.values()) {
                    imageUrls.add(imageUrl);
                }
            }
            Log.d("dataaaa", imageUrls.toString());
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

            // Now you can use imageUrls to display images in your RecyclerView
            // Use your RecyclerView adapter to show images (similar to the ImageAdapter in your previous code)
            ImageAdapter imageAdapter = new ImageAdapter(imageUrls);
            recyclerView.setAdapter(imageAdapter);
            imageAdapter.notifyDataSetChanged();


            ViewPager viewPager = findViewById(R.id.viewPager);

            // Get image URLs and position from the intent
            int position = getIntent().getIntExtra("position", 0);

            // Set up the ViewPager with the ImagePagerAdapter
        ImagePagerAdapter imagePagerAdapter = new ImagePagerAdapter(this, imageUrls);
            viewPager.setAdapter(imagePagerAdapter);

            // Set the starting position
            viewPager.setCurrentItem(position);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker at the specific LatLng and set the title
        LatLng markerLatLng = new LatLng(villaModel.getLat(), villaModel.getLng());
        mMap.addMarker(new MarkerOptions().position(markerLatLng).title(villaModel.getTitle()).icon(BitmapDescriptorFactory.fromResource(R.drawable.small_logo))).showInfoWindow();
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10.0f));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(markerLatLng, 13.0f));

    }

    public void map_(View view) {
        Log.d("latlng", villaModel.getLat() + "   " + villaModel.getLng());

        if (villaModel.getLat() > -90 && villaModel.getLat() < 90 && villaModel.getLng() > -180 && villaModel.getLng() < 180) {
            Intent intent = new Intent(EasyRoomDetailsActivity.this, MapActivity.class);
            intent.putExtra("lat", villaModel.getLat());
            intent.putExtra("lng", villaModel.getLng());
            intent.putExtra("name", villaModel.getTitle());
            startActivity(intent);

        } else {
            Toast.makeText(EasyRoomDetailsActivity.this, "Invalid Coordinates to show marker", Toast.LENGTH_SHORT).show();
        }
    }


}