package com.moutamid.easyroomapp.landlord.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.moutamid.easyroomapp.R;
public class LandlordResponseActivity extends AppCompatActivity {

    private EditText responseMessage;
    private Button sendResponseButton;
    private DatabaseReference inquiryDatabaseReference;
    private String inquiryId;
    private String renterId;
    private String landlordId = "landlord_id_here"; // Replace with actual landlord ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landlord_response);

        responseMessage = findViewById(R.id.response_message);
        sendResponseButton = findViewById(R.id.send_response_button);

        inquiryDatabaseReference = FirebaseDatabase.getInstance().getReference("inquiries");

        // Get the inquiryId and renterId (pass these values through intent)
        inquiryId = getIntent().getStringExtra("inquiryId");
        renterId = getIntent().getStringExtra("renterId");

        // Set up the button to send the response
        sendResponseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String response = responseMessage.getText().toString().trim();
                if (!TextUtils.isEmpty(response)) {
                    sendResponseToRenter(response);
                } else {
                    Toast.makeText(LandlordResponseActivity.this, "Please type a response", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Send response to renter and update it in Firebase
    private void sendResponseToRenter(String response) {
        inquiryDatabaseReference.child(renterId).child(landlordId).child(inquiryId)
                .child("response").setValue(response).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(LandlordResponseActivity.this, "Response sent.", Toast.LENGTH_SHORT).show();
                        finish(); // Close the activity after sending the response
                    } else {
                        Toast.makeText(LandlordResponseActivity.this, "Failed to send response.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
