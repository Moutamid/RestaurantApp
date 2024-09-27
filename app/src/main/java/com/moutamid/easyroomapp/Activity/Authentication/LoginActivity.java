package com.moutamid.easyroomapp.Activity.Authentication;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.icu.lang.UCharacter;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.fxn.stash.Stash;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.moutamid.easyroomapp.Activity.MainActivity;
import com.moutamid.easyroomapp.Model.UserModel;
import com.moutamid.easyroomapp.R;
import com.moutamid.easyroomapp.helper.Config;
import com.moutamid.easyroomapp.helper.Constants;

import java.util.Objects;


public class LoginActivity extends AppCompatActivity {
    EditText email, password;

    String email_str, password_str;
    String name, emailstr, image_gmail;
    private static final int RC_SIGN_IN = 1;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private DatabaseReference mDatabase;
    Button login;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(LoginActivity.this);
        auth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_login);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        initComponent();
        firebaseAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initValues();
                loginRequest();
            }
        });
    }

    public void initComponent() {
        login = findViewById(R.id.login);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
    }

    public void initValues() {
        email_str = email.getText().toString();
        password_str = password.getText().toString();
    }

    public void sign_up(View view) {
        startActivity(new Intent(LoginActivity.this, SignupActivity.class));
    }


    private void loginRequest() {
        if (email_str.length() == 0) {
            email.setError("Please Error");
        } else if (password_str.length() == 0) {
            password.setError("Please Error");
        } else {

            Dialog lodingbar = new Dialog(LoginActivity.this);
            lodingbar.setContentView(R.layout.loading);
            Objects.requireNonNull(lodingbar.getWindow()).setBackgroundDrawable(new ColorDrawable(UCharacter.JoiningType.TRANSPARENT));
            lodingbar.setCancelable(false);
            lodingbar.show();
            Constants.auth().signInWithEmailAndPassword(
                    email.getText().toString(),
                    password.getText().toString()
            ).addOnSuccessListener(authResult -> {

                Constants.UserReference.child(authResult.getUser().getUid()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            UserModel userModel = snapshot.getValue(UserModel.class);
                            Stash.put("UserDetails", userModel);
                            Stash.put("guest", false);

                            lodingbar.dismiss();

                            if (userModel.user_type.equals("Landlord")) {
                                startActivity(new Intent(LoginActivity.this, com.moutamid.easyroomapp.landlord.MainActivity.class));
                                finishAffinity();
                            } else {
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finishAffinity();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }).addOnFailureListener(e -> {
                lodingbar.dismiss();
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            });
        }


    }

    public void forgot_password(View view) {
        startActivity(new Intent(this, ResetPasswordActivity.class));
    }

    public void guest(View view) {
        Dialog loadingBar = new Dialog(LoginActivity.this);
        loadingBar.setContentView(R.layout.loading);
        Objects.requireNonNull(loadingBar.getWindow()).setBackgroundDrawable(new ColorDrawable(UCharacter.JoiningType.TRANSPARENT));
        loadingBar.setCancelable(false);
        loadingBar.show();
        auth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = auth.getCurrentUser();
                            if (user != null) {
                                Stash.put("guest", true);
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                loadingBar.dismiss();
                                finish();
                            }
                        } else {

                            show_toast("Anonymous sign-in failed: " + task.getException(), 0);
                            loadingBar.dismiss();
                            finish();
                        }
                    }
                });
    }
    public void show_toast(String message, int type) {
        LayoutInflater inflater = getLayoutInflater();
        View layout;
        if (type == 0)
        {
            layout = inflater.inflate(R.layout.toast_wrong, findViewById(R.id.toast_layout_root));
        }
        else
        {
            layout = inflater.inflate(R.layout.toast_right, findViewById(R.id.toast_layout_root));
        }
        TextView text = layout.findViewById(R.id.text);
        text.setText(message);
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.BOTTOM, 0, 10);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

}