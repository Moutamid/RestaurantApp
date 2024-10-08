package com.moutamid.easyroomapp.helper;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.moutamid.easyroomapp.Model.ResturantModel;
import com.moutamid.easyroomapp.Model.UserModel;
import com.moutamid.easyroomapp.R;

public class BookingDialog extends Dialog {
    private ResturantModel easyroom;
    private UserModel user;
    private BookingListener listener;

    public BookingDialog(Context context, ResturantModel easyroom, UserModel user, BookingListener listener) {
        super(context);
        this.easyroom = easyroom;
        this.user = user;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_booking);

        Spinner tableSpinner = findViewById(R.id.table_spinner); // Spinner to select table
        Button confirmButton = findViewById(R.id.confirm_button);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedTable = tableSpinner.getSelectedItem().toString();

                BookingModel booking = new BookingModel(
                        user.getName(),
                        user.getPhone_number(),
                        easyroom.getName(),
                        easyroom.getAddress(),
                        easyroom.getImage_url(),
                        easyroom.getKey(),
                        selectedTable
                );
                listener.onBookingConfirmed(booking);
                dismiss();
            }
        });
    }

    public interface BookingListener {
        void onBookingConfirmed(BookingModel booking);
    }
}
