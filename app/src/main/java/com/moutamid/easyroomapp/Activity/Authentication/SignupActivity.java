package com.moutamid.easyroomapp.Activity.Authentication;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.icu.lang.UCharacter;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.fxn.stash.Stash;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.moutamid.easyroomapp.Activity.MainActivity;
import com.moutamid.easyroomapp.Model.UserModel;
import com.moutamid.easyroomapp.R;
import com.moutamid.easyroomapp.helper.Config;
import com.moutamid.easyroomapp.helper.Constants;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class SignupActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_GALLERY = 111;
    ImageView profile_pic;
    Calendar myCalendar = Calendar.getInstance();
    EditText name, dob, email, password, phone_number;
    Uri image_profile_str = null;
    RadioGroup userTypeRadioGroup;
    RadioButton selectedUserType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        profile_pic = findViewById(R.id.profile_pic);
        initComponent();

        // Date picker for Date of Birth (DOB)
        DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String myFormat = "MM/dd/yyyy"; // The format you need
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            dob.setText(sdf.format(myCalendar.getTime()));
        };

        dob.setOnClickListener(v -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(SignupActivity.this, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        });
    }

    public void initComponent() {
        name = findViewById(R.id.name);
        dob = findViewById(R.id.dob);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        phone_number = findViewById(R.id.phone_number);
        userTypeRadioGroup = findViewById(R.id.user_type); // Initialize the RadioGroup for user type
    }

    public void login(View view) {
        startActivity(new Intent(SignupActivity.this, LoginActivity.class));
    }

    public void sign_up(View view) {
        int selectedRadioButtonId = userTypeRadioGroup.getCheckedRadioButtonId();
        if (selectedRadioButtonId == -1) {
            Toast.makeText(SignupActivity.this, "Please select a user type", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get selected user type
        selectedUserType = findViewById(selectedRadioButtonId);
        if (validation()) {
            registerRequest();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_GALLERY && resultCode == RESULT_OK) {
            image_profile_str = data.getData();
            profile_pic.setImageURI(image_profile_str);
            profile_pic.setVisibility(View.VISIBLE);
        }
    }

    private void registerRequest() {
        Dialog lodingbar = new Dialog(SignupActivity.this);
        lodingbar.setContentView(R.layout.loading);
        Objects.requireNonNull(lodingbar.getWindow()).setBackgroundDrawable(new ColorDrawable(UCharacter.JoiningType.TRANSPARENT));
        lodingbar.setCancelable(false);
        lodingbar.show();

        String filePathName = "users/";
        final String timestamp = "" + System.currentTimeMillis();
        StorageReference storageReference = FirebaseStorage.getInstance().getReference(filePathName + timestamp);
        UploadTask urlTask = storageReference.putFile(image_profile_str);
        Task<Uri> uriTask = urlTask.continueWithTask(task -> {
            if (!task.isSuccessful()) {
                Toast.makeText(SignupActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
            return storageReference.getDownloadUrl();
        }).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Uri downloadImageUri = task.getResult();
                if (downloadImageUri != null) {
                    Constants.auth().createUserWithEmailAndPassword(
                            email.getText().toString(),
                            password.getText().toString()
                    ).addOnCompleteListener(authResult -> {
                        UserModel userModel = new UserModel();
                        userModel.name = name.getText().toString();
                        userModel.dob = dob.getText().toString();
                        userModel.email = email.getText().toString();
                        userModel.phone_number = phone_number.getText().toString();
                        userModel.image_url = downloadImageUri.toString();
                        userModel.user_type = selectedUserType.getText().toString(); // Add user type to model
                        userModel.id = authResult.getResult().getUser().getUid();

                        Constants.UserReference.child(Objects.requireNonNull(Constants.auth().getCurrentUser().getUid())).setValue(userModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Stash.put("UserDetails", userModel);
                                Stash.put("is_first", true);
                                Stash.put("guest", false);

                                lodingbar.dismiss();
                                if (userModel.user_type.equals("Landlord")) {
                                    startActivity(new Intent(SignupActivity.this, com.moutamid.easyroomapp.landlord.MainActivity.class));
                                    finishAffinity();
                                } else {
                                startActivity(new Intent(SignupActivity.this, MainActivity.class));
                                finishAffinity();
                                }
                            }
                        });
                    }).addOnFailureListener(e -> {
                        lodingbar.dismiss();
                        Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
                }
            }
        });
    }

    private boolean validation() {
        if (image_profile_str == null) {
            Config.showToast(this, "Please select your profile picture");
            return false;

        } else if (name.getText().toString().isEmpty()) {
            name.setError("Enter Name");
            name.requestFocus();
            Config.openKeyboard(this);
            return false;

        } else if (dob.getText().toString().isEmpty()) {
            dob.setError("Select Date of Birth");
            dob.requestFocus();
            Config.openKeyboard(this);
            return false;

        } else if (email.getText().toString().isEmpty()) {
            email.setError("Enter Email");
            email.requestFocus();
            Config.openKeyboard(this);
            return false;

        } else if (password.getText().toString().isEmpty()) {
            password.setError("Enter Password");
            password.requestFocus();
            Config.openKeyboard(this);
            return false;

        } else if (phone_number.getText().toString().isEmpty()) {
            phone_number.setError("Enter Phone Number");
            phone_number.requestFocus();
            Config.openKeyboard(this);
            return false;

        } else if (!Config.isNetworkAvailable(this)) {
            Config.showToast(this, "You are not connected to the network");
            return false;
        }

        return true;
    }

    public void profile_image(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_GALLERY);
    }
}
