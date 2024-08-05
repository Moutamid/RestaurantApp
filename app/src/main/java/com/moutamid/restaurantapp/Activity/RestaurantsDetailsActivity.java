package com.moutamid.restaurantapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.fxn.stash.Stash;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.moutamid.restaurantapp.Model.ResturantModel;
import com.moutamid.restaurantapp.R;
import com.moutamid.restaurantapp.helper.Config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RestaurantsDetailsActivity extends AppCompatActivity {
    DatabaseReference databaseReference;

    //TODO name change of variable
    ImageView resImage_img, favourite_img, unfavourite_img;
    TextView name, phone_res, address_res, description, title;
    double lat, lng;
    ImageView map;
    Button request;
    ResturantModel current_resturantModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants_details);
         databaseReference = Config.databaseReference().child("Bookings");
        resImage_img = findViewById(R.id.image);
        unfavourite_img = findViewById(R.id.unfavourite);
        favourite_img = findViewById(R.id.favourite);
        map = findViewById(R.id.map);
        phone_res = findViewById(R.id.phone_res);
        address_res = findViewById(R.id.address);
        description = findViewById(R.id.description);
        name = findViewById(R.id.name);
        title = findViewById(R.id.title);
        request = findViewById(R.id.request);
        current_resturantModel = (ResturantModel) Stash.getObject(Config.currentModel, ResturantModel.class);
        name.setText(current_resturantModel.getName());
        title.setText(current_resturantModel.getName());
        address_res.setText(current_resturantModel.getAddress());
        Glide.with(RestaurantsDetailsActivity.this).load(current_resturantModel.getImage_url()).into(resImage_img);
        phone_res.setText(current_resturantModel.getPhone());
        description.setText(current_resturantModel.getShort_description());
        ArrayList<ResturantModel> resturantModels = Stash.getArrayList(Config.favourite, ResturantModel.class);
        if (resturantModels != null) {
            for (int i = 0; i < resturantModels.size(); i++) {

                if (current_resturantModel.getKey().equals(resturantModels.get(i).getKey())) {
                    unfavourite_img.setVisibility(View.VISIBLE);

                } else {
                    favourite_img.setVisibility(View.VISIBLE);

                }

            }
}
        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RestaurantsDetailsActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.dialog_booking, null);

                Spinner tableSpinner = dialogView.findViewById(R.id.table_spinner);
                Button confirmButton = dialogView.findViewById(R.id.confirm_button);

                builder.setView(dialogView);
                AlertDialog dialog = builder.create();

                confirmButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Get selected table
                        String selectedTable = tableSpinner.getSelectedItem().toString();
                        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        String restaurantId = ""; // Replace with actual restaurant ID or key
                        Map<String, Object> bookingData = new HashMap<>();
                        bookingData.put("userId", userId);
                        bookingData.put("restaurantId", restaurantId);
                        bookingData.put("table", selectedTable);

                        // Save data to Firebase
                        databaseReference.push().setValue(bookingData).addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(RestaurantsDetailsActivity.this, "Booking confirmed for table: " + selectedTable, Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            } else {
                                Toast.makeText(RestaurantsDetailsActivity.this, "Failed to confirm booking. Please try again.", Toast.LENGTH_SHORT).show();
                            }
                        });     // Show confirmation message
                        Toast.makeText(RestaurantsDetailsActivity.this, "Booking confirmed for table: " + selectedTable, Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("latlng", current_resturantModel.getLat() + "   " + current_resturantModel.getLng());

                if (current_resturantModel.getLat() > -90 && current_resturantModel.getLat() < 90 && current_resturantModel.getLng() > -180 && current_resturantModel.getLng() < 180) {
                    Intent intent = new Intent(RestaurantsDetailsActivity.this, MapActivity.class);
                    intent.putExtra("lat", current_resturantModel.getLat());
                    intent.putExtra("lng", current_resturantModel.getLng());
                    intent.putExtra("name", current_resturantModel.getName());
                    startActivity(intent);

                } else {
                    Toast.makeText(RestaurantsDetailsActivity.this, "Invalid Coordinates to show marker", Toast.LENGTH_SHORT).show();
                }

            }
        });
        favourite_img.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                ArrayList<ResturantModel> resturantModelArrayList = Stash.getArrayList(Config.favourite, ResturantModel.class);
                resturantModelArrayList.add(current_resturantModel);
                Stash.put(Config.favourite, resturantModelArrayList);
                unfavourite_img.setVisibility(View.VISIBLE);
                favourite_img.setVisibility(View.GONE);
            }
        });
        unfavourite_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<ResturantModel> resturantModel = Stash.getArrayList(Config.favourite, ResturantModel.class);
                for (int i = 0; i < resturantModel.size(); i++) {
                    if (resturantModel.get(i).getKey().equals(current_resturantModel.getKey())) {
                        resturantModel.remove(i);
                    }
                }
                Stash.put(Config.favourite, resturantModel);
                unfavourite_img.setVisibility(View.GONE);
                favourite_img.setVisibility(View.VISIBLE);
            }
        });
    }

    public void onBack(View view) {
        onBackPressed();
    }
}