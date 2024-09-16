package com.moutamid.easyroomapp.admin.Activities.Resturants;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.moutamid.halalfoodadmin.Model.ResturantModel;
import com.moutamid.halalfoodadmin.R;
import com.moutamid.halalfoodadmin.helper.Config;

import java.util.Calendar;

public class AddResturantsActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_GALLERY = 111;
    private Uri uri = null;
    ImageView image;
    TextView upload;
    EditText edt_name, edt_description, edt_phone, edt_website, edt_address, edt_lat, edt_lng;

    TextView mon_opnening, tue_opnening, wed_opnening, thursday_opnening, fri_opnening, sat_opnening, sun_opnening;
    TextView mon_closing, tue_closing, wed_closing, thursday_closing, fri_closing, sat_closing, sun_closing;
    CheckBox mon_available, tue_available, wed_available, thursday_available, fri_available, sat_available, sun_available;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_resturants);
        edt_name = findViewById(R.id.edt_name);
        edt_description = findViewById(R.id.edt_description);
        edt_phone = findViewById(R.id.edt_phone);
        edt_website = findViewById(R.id.edt_website);
        edt_description = findViewById(R.id.edt_description);
        edt_address = findViewById(R.id.edt_address);
        mon_opnening = findViewById(R.id.mon_opnening);
        tue_opnening = findViewById(R.id.tue_opnening);
        wed_opnening = findViewById(R.id.wed_opnening);
        thursday_opnening = findViewById(R.id.thursday_opnening);
        fri_opnening = findViewById(R.id.fri_opnening);
        sat_opnening = findViewById(R.id.sat_opnening);
        sun_opnening = findViewById(R.id.sun_opnening);
        mon_closing = findViewById(R.id.mon_closing);
        tue_closing = findViewById(R.id.tue_closing);
        wed_closing = findViewById(R.id.wed_closing);
        thursday_closing = findViewById(R.id.thur_closing);
        fri_closing = findViewById(R.id.fru_closing);
        sat_closing = findViewById(R.id.sat_closing);
        sun_closing = findViewById(R.id.sun_closing);
        image = findViewById(R.id.image);
        mon_available = findViewById(R.id.mon_available);
        tue_available = findViewById(R.id.tue_available);
        wed_available = findViewById(R.id.wed_available);
        thursday_available = findViewById(R.id.thursday_available);
        fri_available = findViewById(R.id.fri_available);
        sat_available = findViewById(R.id.sat_available);
        sun_available = findViewById(R.id.sun_available);
        edt_lat = findViewById(R.id.edt_lat);
        edt_lng = findViewById(R.id.edt_lng);
        upload = findViewById(R.id.upload);
        available(mon_available);
        available(tue_available);
        available(wed_available);
        available(thursday_available);
        available(fri_available);
        available(sat_available);
        available(sun_available);
        mon_opnening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                time_picker(mon_opnening);
            }
        });
        tue_opnening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                time_picker(tue_opnening);
            }
        });
        wed_opnening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                time_picker(wed_opnening);
            }
        });
        thursday_opnening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                time_picker(thursday_opnening);
            }
        });
        fri_opnening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                time_picker(fri_opnening);
            }
        });
        sat_opnening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                time_picker(sat_opnening);
            }
        });
        sun_opnening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                time_picker(sun_opnening);
            }
        });
        mon_closing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                time_picker(mon_closing);
            }
        });
        tue_closing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                time_picker(tue_closing);
            }
        });
        wed_closing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                time_picker(wed_closing);
            }
        });
        thursday_closing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                time_picker(thursday_closing);
            }
        });
        fri_closing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                time_picker(fri_closing);
            }
        });
        sat_closing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                time_picker(sat_closing);
            }
        });
        sun_closing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                time_picker(sun_closing);
            }
        });


        image.setOnClickListener(v -> image_Select());

        upload.setOnClickListener(v -> {

            if (edt_name.getText().toString().isEmpty()) {
                edt_name.setError("Please Enter");
            } else {
                if (edt_phone.getText().toString().isEmpty()) {
                    edt_phone.setError("Please Enter");
                } else {
                    if (edt_description.getText().toString().isEmpty()) {
                        edt_description.setError("Please Enter");
                    } else {
                        if (edt_website.getText().toString().isEmpty()) {
                            edt_website.setError("Please Enter");
                        } else {

                            if (edt_address.getText().toString().isEmpty()) {
                                edt_address.setError("Please Enter");
                            } else {
                                if (edt_lat.getText().toString().isEmpty()) {
                                    edt_lat.setError("Please Enter");
                                } else {
                                    if (edt_lng.getText().toString().isEmpty()) {
                                        edt_lng.setError("Please Enter");
                                    } else {


                                            if (Config.isNetworkAvailable(AddResturantsActivity.this)) {
                                                uploadvideo();
                                            } else {
                                                Toast.makeText(AddResturantsActivity.this, "No network connection available", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                }
                            }
                        }
                    }
                }
            }
        });

    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    protected void onActivityResult(int requestCode, int resultCode,
                                    @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_GALLERY && resultCode == RESULT_OK) {
            uri = data.getData();
            image.setImageURI(uri);
            image.setVisibility(View.VISIBLE);
        }
    }


    public void uploadvideo() {
        if (uri != null) {
            Config.showProgressDialog(AddResturantsActivity.this);
            String filePathName = "Resturants/";
            final String timestamp = "" + System.currentTimeMillis();

            StorageReference storageReference = FirebaseStorage.getInstance().getReference(filePathName + timestamp);
            UploadTask urlTask = storageReference.putFile(uri);
            Task<Uri> uriTask = urlTask.continueWithTask(task -> {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return storageReference.getDownloadUrl();
            }).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Uri downloadImageUri = task.getResult();
                    if (downloadImageUri != null) {
                        String key = Config.databaseReference().child("Restaurants").push().getKey();
                        ResturantModel item = new ResturantModel();
                        item.setImage_url(downloadImageUri.toString());
                        item.setKey(key);
                        item.setName(edt_name.getText().toString());
                        item.setAddress(edt_address.getText().toString());
                        item.setWebsite(edt_website.getText().toString());
                        item.setPhone(edt_phone.getText().toString());
                        item.setShort_description(edt_description.getText().toString());
                        item.setFri_opnening(fri_opnening.getText().toString());
                        item.setSat_opnening(sat_opnening.getText().toString());
                        item.setSun_opnening(sun_opnening.getText().toString());
                        item.setMon_opnening(mon_opnening.getText().toString());
                        item.setTue_opnening(tue_opnening.getText().toString());
                        item.setWed_opnening(wed_opnening.getText().toString());
                        item.setThursday_opnening(thursday_opnening.getText().toString());
                        item.setFri_closing(fri_closing.getText().toString());
                        item.setSat_closing(sat_closing.getText().toString());
                        item.setSun_closing(sun_closing.getText().toString());
                        item.setMon_closing(mon_closing.getText().toString());
                        item.setTue_closing(tue_closing.getText().toString());
                        item.setWed_closing(wed_closing.getText().toString());
                        item.setThursday_closing(thursday_closing.getText().toString());
                        item.setMon_available(mon_available.getText().toString());
                        item.setTue_available(tue_available.getText().toString());
                        item.setWed_available(wed_available.getText().toString());
                        item.setThursday_available(thursday_available.getText().toString());
                        item.setFri_available(fri_available.getText().toString());
                        item.setSat_available(sat_available.getText().toString());
                        item.setSun_available(sun_available.getText().toString());
                        item.setLat(Double.parseDouble(edt_lat.getText().toString()));
                        item.setLng(Double.parseDouble(edt_lng.getText().toString()));
                        ResturantModel item1 = new ResturantModel();
                        item1.setLat(Double.parseDouble(edt_lat.getText().toString()));
                        item1.setLng(Double.parseDouble(edt_lng.getText().toString()));
                        item1.setName((edt_name.getText().toString()));

                        Config.databaseReference().child("Restaurants").child(key).setValue(item)
                                .addOnSuccessListener(aVoid -> {
                                    Config.databaseReference().child("Locations").child(key).setValue(item1)
                                            .addOnSuccessListener(aVoid1 -> {
                                                Config.dismissProgressDialog();
                                                Toast.makeText(AddResturantsActivity.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                                                onBackPressed();
                                                finish();

                                            });


                                })
                                .addOnFailureListener(e -> {
                                    Config.dismissProgressDialog();
                                    Toast.makeText(this, "Please try again", Toast.LENGTH_SHORT).show();
                                });
                    }
                }

            }).addOnFailureListener(e -> {
                Config.dismissProgressDialog();
                Toast.makeText(this, "Failed to upload", Toast.LENGTH_SHORT).show();
            });
        } else {
            Toast.makeText(this, "Please select Image", Toast.LENGTH_SHORT).show();

        }
    }

    public void image_Select() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_GALLERY);


    }

    public void time_picker(TextView textView) {
        Calendar cal = Calendar.getInstance();
        final int hour = cal.get(Calendar.HOUR_OF_DAY);
        final int min = cal.get(Calendar.MINUTE);

        TimePickerDialog sTimePickerDialog = new TimePickerDialog(AddResturantsActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String AM_PM = " AM";
                String mm_precede = "";
                if (hourOfDay >= 12) {
                    AM_PM = " PM";
                    if (hourOfDay >= 13 && hourOfDay < 24) {
                        hourOfDay -= 12;
                    } else {
                        hourOfDay = 12;
                    }
                } else if (hourOfDay == 0) {
                    hourOfDay = 12;
                }
                if (minute < 10) {
                    mm_precede = "0";
                }
                textView.setText("" + hourOfDay + ":" + mm_precede + minute + AM_PM);
            }
        }, hour, min, android.text.format.DateFormat.is24HourFormat(AddResturantsActivity.this));
        sTimePickerDialog.show();

    }

    public void available(CheckBox checkBox) {
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    checkBox.setText("Available");
                } else {
                    checkBox.setText("Unavailable");

                }
            }
        });
    }


    public void backpress(View view) {
        onBackPressed();
    }
}