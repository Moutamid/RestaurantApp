package com.moutamid.easyroomapp.landlord.activities.Home;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.fxn.stash.Stash;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.moutamid.easyroomapp.Adapter.ImageAdapter;
import com.moutamid.easyroomapp.Adapter.ImagePagerAdapter;
import com.moutamid.easyroomapp.R;
import com.moutamid.easyroomapp.helper.Config;
import com.moutamid.easyroomapp.landlord.model.Villa;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.glailton.expandabletextview.ExpandableTextView;

public class VillaDetailsActivity extends AppCompatActivity implements OnMapReadyCallback {
    Villa villaModel;
    Button map;
    ImageView image;
    String token_admin;
    RecyclerView recyclerView;
    private GoogleMap mMap;
    static DatabaseReference propertyRef;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    ImageView edit, delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_villa_details_edit);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            getWindow().getDecorView().setSystemUiVisibility(View);
        }

// Set the status bar background color to white
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.parseColor("#202020"));
        }
        fetch_data();

    }

    @Override
    protected void onResume() {
        super.onResume();
        fetch_data();
    }

    public void fetch_data() {
        villaModel = (Villa) Stash.getObject(Config.currentModel, Villa.class);
        propertyRef = database.getReference("EasyRoomsApp").child("Rooms").child(villaModel.getKey());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);
        recyclerView = findViewById(R.id.recyclerView);
        edit = findViewById(R.id.edit);
        delete = findViewById(R.id.delete);
        image = findViewById(R.id.image);
        map = findViewById(R.id.show_map);
        image = findViewById(R.id.image);
        // Assign IDs to views
        Toolbar toolbar = findViewById(R.id.toolbar_);
        ImageView backpress = findViewById(R.id.backpress);
        ImageView image = findViewById(R.id.image);
        TextView name = findViewById(R.id.name);
        TextView price = findViewById(R.id.price);
        name.setText(villaModel.getName());
        price.setText(villaModel.getBill() + " $/month");
        Glide.with(VillaDetailsActivity.this).load(villaModel.getImage()).into(image);
        TextView noOfBedroom = findViewById(R.id.no_of_bedroom);
        noOfBedroom.setText(villaModel.getBedroom() + "");
        TextView noOfBathroom = findViewById(R.id.no_of_bathroom);
        noOfBathroom.setText(villaModel.getBathRoom() + "");
        TextView descriptionTitle = findViewById(R.id.description_title);
        ExpandableTextView description = findViewById(R.id.description);
        description.setText(villaModel.getDescription());
        ImageView availability = findViewById(R.id.availability);
        TextView house_rules = findViewById(R.id.house_rules);
        TextView pet_friendly = findViewById(R.id.pet_friendly);
        TextView smoke_friendly = findViewById(R.id.smoke_friendly);
        TextView no_of_persons = findViewById(R.id.no_of_persons);
        TextView distance = findViewById(R.id.distance);
        distance.setText(Stash.getString("distance") + " km away from you");
        no_of_persons.setText("Available for " + villaModel.no_of_persons + " persons");
        no_of_persons.setVisibility(View.GONE);
        showImagesInRecyclerView();
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new android.app.AlertDialog.Builder(VillaDetailsActivity.this)
                        .setTitle("Are you sure?")
                        .setMessage("You want to delete this Villa permanently.")
                        .setCancelable(true)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                Config.showProgressDialog(VillaDetailsActivity.this);
                                propertyRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Config.dismissProgressDialog();
                                            finish();
                                        } else {
                                            Config.dismissProgressDialog();
                                            Toast.makeText(VillaDetailsActivity.this, "Something went worng", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                        })
                        .setIcon(R.mipmap.ic_launcher_round)
                        .show();

            }
        });
        if (!villaModel.rules.equals(",")) {
            house_rules.setVisibility(View.VISIBLE);
//            pet_friendly.setVisibility(View.VISIBLE);
//            pet_friendly.setText(villaModel.rules.trim());
        } else {
            house_rules.setVisibility(View.GONE);
            pet_friendly.setVisibility(View.GONE);
        }
        TextView propertyAmenitiesTitle = findViewById(R.id.property_amenities_title);
        LinearLayout dryerLayout = findViewById(R.id.dryer_layout);
        LinearLayout furnishedLayout = findViewById(R.id.furnished_layout);
        LinearLayout equippedKitchenLayout = findViewById(R.id.equipped_kitchen_layout);
        LinearLayout gardenLayout = findViewById(R.id.garden_layout);
        LinearLayout heaterLayout = findViewById(R.id.heater_layout);
        LinearLayout tvLayout = findViewById(R.id.tv_layout);
        LinearLayout wifiLayout = findViewById(R.id.wifi_layout);
        LinearLayout machine_layout = findViewById(R.id.machine_layout);
        LinearLayout parking_layout = findViewById(R.id.parking_layout);
        LinearLayout air_layout = findViewById(R.id.air_layout);
        TextView location = findViewById(R.id.location);
        location.setText(villaModel.getTitle());
// Request for Rent button
        Button requestButton = findViewById(R.id.request_button);
        if (Stash.getBoolean("onetime")) {
            displayTextInTextViews(villaModel.rules);
        }
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(VillaDetailsActivity.this, EditVillaActivity.class));
            }
        });
        if (villaModel.getPropertyAmenities() != null) {
            propertyAmenitiesTitle.setVisibility(View.VISIBLE);
        } else {
            propertyAmenitiesTitle.setVisibility(View.GONE);

        }
        if (villaModel.getPropertyAmenities().isDryer()) {
            dryerLayout.setVisibility(View.VISIBLE);
        } else {
            dryerLayout.setVisibility(View.GONE);
        }
        if (villaModel.getPropertyAmenities().isFurnished()) {
            furnishedLayout.setVisibility(View.VISIBLE);
        } else {
            furnishedLayout.setVisibility(View.GONE);
        }
        if (villaModel.getPropertyAmenities().isGarden()) {
            gardenLayout.setVisibility(View.VISIBLE);
        } else {
            gardenLayout.setVisibility(View.GONE);
        }
        if (villaModel.getPropertyAmenities().isEquippedKitchen()) {
            equippedKitchenLayout.setVisibility(View.VISIBLE);
        } else {
            equippedKitchenLayout.setVisibility(View.GONE);
        }
        if (villaModel.getPropertyAmenities().isHeating()) {
            heaterLayout.setVisibility(View.VISIBLE);
        } else {
            heaterLayout.setVisibility(View.GONE);
        }
        if (villaModel.getPropertyAmenities().isWifi()) {
            wifiLayout.setVisibility(View.VISIBLE);
        } else {
            wifiLayout.setVisibility(View.GONE);
        }
        if (villaModel.getPropertyAmenities().isTv()) {
            tvLayout.setVisibility(View.VISIBLE);
        } else {
            tvLayout.setVisibility(View.GONE);
        }
        if (villaModel.getPropertyAmenities().isWashingMachine()) {
            machine_layout.setVisibility(View.VISIBLE);
        } else {
            machine_layout.setVisibility(View.GONE);
        }
        if (villaModel.getPropertyAmenities().isParking()) {
            parking_layout.setVisibility(View.VISIBLE);
        } else {
            parking_layout.setVisibility(View.GONE);
        }
        if (villaModel.getPropertyAmenities().isAirConditioning()) {
            air_layout.setVisibility(View.VISIBLE);
        } else {
            air_layout.setVisibility(View.GONE);
        }

        availability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                CalenderDialogClass cdd = new CalenderDialogClass(VillaDetailsActivity.this);
//                cdd.show();
            }
        });


        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("latlng", villaModel.getLat() + "   " + villaModel.getLng());

                if (villaModel.getLat() > -90 && villaModel.getLat() < 90 && villaModel.getLng() > -180 && villaModel.getLng() < 180) {
                    Intent intent = new Intent(VillaDetailsActivity.this, MapActivity.class);
                    intent.putExtra("lat", villaModel.getLat());
                    intent.putExtra("lng", villaModel.getLng());
                    intent.putExtra("name", villaModel.getName());
                    startActivity(intent);

                } else {
                    Toast.makeText(VillaDetailsActivity.this, "Invalid Coordinates to show marker", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public void onBack(View view) {
        onBackPressed();
    }



   
    private void showImagesInRecyclerView() {
        // Retrieve Villa models from Stash

        if (villaModel != null) {
            // Process the Villa models and display images in RecyclerView
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

            // Set up the ViewPager with the ImagePagerAdapter
            ImagePagerAdapter imagePagerAdapter = new ImagePagerAdapter(this, imageUrls);
            viewPager.setAdapter(imagePagerAdapter);

            // Set the starting position
            viewPager.setCurrentItem(0);
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
            Intent intent = new Intent(VillaDetailsActivity.this, MapActivity.class);
            intent.putExtra("lat", villaModel.getLat());
            intent.putExtra("lng", villaModel.getLng());
            intent.putExtra("name", villaModel.getName());
            startActivity(intent);

        } else {
            Toast.makeText(VillaDetailsActivity.this, "Invalid Coordinates to show marker", Toast.LENGTH_SHORT).show();
        }
    }


    private void displayTextInTextViews(String inputString) {
        // Split the input string by commas, considering optional spaces
        String[] strings = inputString.split("\\s*,\\s*");
        LinearLayout linearLayout = findViewById(R.id.linearLayout);
linearLayout.removeAllViews();
        Log.d("data", "st" + strings);
        // Create TextViews for non-empty extracted strings and add them to the LinearLayout
        for (String str : strings) {
            // Skip empty strings

            if (!str.trim().isEmpty()) {
                TextView textView = new TextView(this);
                Log.d("data", "str" + str);

                textView.setText(str);
                linearLayout.addView(textView);
                Stash.put("onetime", false);

            }
        }
    }


}