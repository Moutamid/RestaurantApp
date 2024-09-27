package com.moutamid.easyroomapp.Activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.icu.lang.UCharacter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.fxn.stash.Stash;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.moutamid.easyroomapp.Model.UserModel;
import com.moutamid.easyroomapp.R;
import com.moutamid.easyroomapp.helper.Config;
import com.moutamid.easyroomapp.helper.Constants;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class EditProfileActivity extends AppCompatActivity {
    ImageView profile_img;
    TextView name, dob, email, phone_number;
    private static final int PICK_IMAGE_GALLERY = 111;
    Calendar myCalendar = Calendar.getInstance();
    Uri image_profile_str = null;
    UserModel userNew;
    Dialog lodingbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        profile_img = findViewById(R.id.profile_pic);
        name = findViewById(R.id.name);
        dob = findViewById(R.id.dob);
        email = findViewById(R.id.email);
        phone_number = findViewById(R.id.phone_number);
        userNew = (UserModel) Stash.getObject("UserDetails", UserModel.class);
        name.setText(userNew.name);
        dob.setText(userNew.dob);
        email.setText(userNew.email);
        phone_number.setText(userNew.phone_number);
        lodingbar = new Dialog(EditProfileActivity.this);

        DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String myFormat = "MM/dd/yyyy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            dob.setText(sdf.format(myCalendar.getTime()));
        };
        dob.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(EditProfileActivity.this, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        });
        Glide.with(EditProfileActivity.this).load(userNew.image_url).into(profile_img);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_GALLERY && resultCode == RESULT_OK) {
            image_profile_str = data.getData();
            profile_img.setImageURI(image_profile_str);
            profile_img.setVisibility(View.VISIBLE);
            Log.d("data_image", image_profile_str + "  " + Uri.parse(userNew.image_url));

        }
    }


    private void registerRequest() {

        lodingbar.setContentView(R.layout.loading);
        Objects.requireNonNull(lodingbar.getWindow()).setBackgroundDrawable(new ColorDrawable(UCharacter.JoiningType.TRANSPARENT));
        lodingbar.setCancelable(false);
        lodingbar.show();
        if (image_profile_str != null) {
            String filePathName = "users/";
            final String timestamp = "" + System.currentTimeMillis();
            StorageReference storageReference = FirebaseStorage.getInstance().getReference(filePathName + timestamp);
            UploadTask urlTask = storageReference.putFile(image_profile_str);
            Task<Uri> uriTask = urlTask.continueWithTask(task -> {
                if (!task.isSuccessful()) {
//                    Log.d("data", image_profile_str + "  " + Uri.parse(userNew.image_url) + "  " + task.getException().getMessage());
                    lodingbar.dismiss();
                    Toast.makeText(EditProfileActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
                return storageReference.getDownloadUrl();
            }).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Uri downloadImageUri = task.getResult();
                    if (downloadImageUri != null) {
                        UserModel userModel = new UserModel();
                        userModel.name = name.getText().toString();
                        userModel.dob = dob.getText().toString();
                        userModel.email = email.getText().toString();
                        userModel.phone_number = phone_number.getText().toString();
                        userModel.image_url = downloadImageUri.toString();
                        userModel.id = userNew.id;
                        Constants.UserReference.child(userNew.id).setValue(userModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Stash.put("UserDetails", userModel);
                            }
                        });
                    }
                }

            });
        } else {
            UserModel userModel = new UserModel();
            userModel.name = name.getText().toString();
            userModel.dob = dob.getText().toString();
            userModel.email = email.getText().toString();
            userModel.phone_number = phone_number.getText().toString();
            userModel.image_url = userNew.image_url;
            userModel.id = userNew.id;
            Constants.UserReference.child(userNew.id).setValue(userModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Stash.put("UserDetails", userModel);
                    finish();
                }
            });
        }

    }

    public void sign_up(View view) {
        if (validation()) {
            registerRequest();
        }
    }

    private boolean validation() {
        if (name.getText().toString().isEmpty()) {
            name.setError("Enter Name");
            name.requestFocus();
            Config.openKeyboard(this);

            return false;
        } else if (dob.getText().toString().isEmpty()) {
            dob.setError("Select Date of Birth");
            dob.requestFocus();
            Config.openKeyboard(this);
            return false;

        } else if (phone_number.getText().toString().isEmpty()) {
            phone_number.setError("Enter Phone Number");
            phone_number.requestFocus();
            Config.openKeyboard(this);

            return false;

        } else if (!Config.isNetworkAvailable(this)) {
            Config.showToast(this, "You are not connected to network");
            return false;
        } else {
            return true;
        }
    }

    public void profile_image(View view) {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_GALLERY);
    }

    public void backPress(View view) {
        onBackPressed();
    }
}