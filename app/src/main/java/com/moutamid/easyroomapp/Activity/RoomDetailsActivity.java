package com.moutamid.easyroomapp.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
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
import androidx.core.app.ActivityCompat;
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
import com.moutamid.easyroomapp.Activity.Authentication.LoginActivity;
import com.moutamid.easyroomapp.Adapter.ImageAdapter;
import com.moutamid.easyroomapp.Adapter.ImagePagerAdapter;
import com.moutamid.easyroomapp.R;
import com.moutamid.easyroomapp.helper.Config;
import com.moutamid.easyroomapp.landlord.model.Villa;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.github.glailton.expandabletextview.ExpandableTextView;

public class RoomDetailsActivity extends AppCompatActivity implements OnMapReadyCallback {
    Villa villaModel;
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
        villaModel = (Villa) Stash.getObject(Config.currentModel, Villa.class);

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
        Glide.with(RoomDetailsActivity.this).load(villaModel.getImage()).into(image);
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
        TextView number = findViewById(R.id.number);
        numberPicker = findViewById(R.id.numberPicker);
        numberPicker.setMinValue(1);
//        if (villaModel.getBedroom() == 0) {
//            numberPicker.setMaxValue(1);
//            numberPicker.setVisibility(View.GONE);
//            number.setVisibility(View.GONE);
//        } else {
//            number.setVisibility(View.VISIBLE);
//            numberPicker.setVisibility(View.VISIBLE);
//            numberPicker.setMaxValue(villaModel.getBedroom());
//        }
//        numberPicker.setValue(1);
//        numberPicker.setTextColor(Color.WHITE);
        if (Stash.getBoolean("guest")) {
            favourite_img.setVisibility(View.INVISIBLE);
            unfavourite_img.setVisibility(View.INVISIBLE);
        }

        numberPicker.setWrapSelectorWheel(true);
//        if (!villaModel.rules.equals(",")) {
//            house_rules.setVisibility(View.VISIBLE);
//            pet_friendly.setVisibility(View.VISIBLE);
//            pet_friendly.setText(villaModel.rules.trim());
//        } else {
        house_rules.setVisibility(View.GONE);
//            pet_friendly.setVisibility(View.GONE);
//        }
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

        if (villaModel.getPropertyAmenities() != null) {
            propertyAmenitiesTitle.setVisibility(View.VISIBLE);

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
        } else {
            propertyAmenitiesTitle.setVisibility(View.GONE);

        }
        if (Stash.getBoolean("onetime")) {
            displayTextInTextViews(villaModel.rules);
        }


        ArrayList<Villa> villaModels = Stash.getArrayList(Config.favourite, Villa.class);
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
                    Intent intent = new Intent(RoomDetailsActivity.this, MapActivity.class);
                    intent.putExtra("lat", villaModel.getLat());
                    intent.putExtra("lng", villaModel.getLng());
                    intent.putExtra("name", villaModel.getTitle());
                    startActivity(intent);

                } else {
                    Toast.makeText(RoomDetailsActivity.this, "Invalid Coordinates to show marker", Toast.LENGTH_SHORT).show();
                }

            }
        });
        favourite_img.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                ArrayList<Villa> villaModelArrayList = Stash.getArrayList(Config.favourite, Villa.class);
                villaModelArrayList.add(villaModel);
                Stash.put(Config.favourite, villaModelArrayList);
                unfavourite_img.setVisibility(View.VISIBLE);
                favourite_img.setVisibility(View.GONE);
            }
        });
        unfavourite_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Villa> villaArrayList = Stash.getArrayList(Config.favourite, Villa.class);
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
    public void booking(View view) {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            if (Stash.getBoolean("guest")) {
                Toast.makeText(this, "Please login and then book for rent", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RoomDetailsActivity.this, LoginActivity.class));

            } else {
                if (villaModel.getBedroom() == 0) {
                    Config.alertDialogue(RoomDetailsActivity.this, "Sorry", "EasyRooms is fully booked now", true);
                } else {
                    int selectedNumber = numberPicker.getValue();
                    Stash.put("numbers", selectedNumber);
                    startActivity(new Intent(this, ChatActivity.class));
                }
            }
        } else {
            Toast.makeText(this, "Please login and then book for rent", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(RoomDetailsActivity.this, LoginActivity.class));
        }


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
            Intent intent = new Intent(RoomDetailsActivity.this, MapActivity.class);
            intent.putExtra("lat", villaModel.getLat());
            intent.putExtra("lng", villaModel.getLng());
            intent.putExtra("name", villaModel.getTitle());
            startActivity(intent);

        } else {
            Toast.makeText(RoomDetailsActivity.this, "Invalid Coordinates to show marker", Toast.LENGTH_SHORT).show();
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
                textView.setTextColor(ContextCompat.getColor(this, android.R.color.white));

                linearLayout.addView(textView);
                Stash.put("onetime", false);

            }
        }
    }

    public void call(View view) {
        String phone = villaModel.phone;
        Log.d("dataa", phone);
        makePhoneCall(phone);  // Replace with the desired phone number

    }

    public void makePhoneCall(String phoneNumber) {
        // Check if CALL_PHONE permission is granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // Request the permission if not granted
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
        } else {
            // If permission is granted, initiate the call
            String dial = "tel:" + phoneNumber;
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, now you can make the call
                makePhoneCall(villaModel.phone);
            } else {
                // Permission denied
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }

}