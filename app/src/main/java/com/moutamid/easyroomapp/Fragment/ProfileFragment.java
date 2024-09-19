package com.moutamid.easyroomapp.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import com.fxn.stash.Stash;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.moutamid.easyroomapp.Activity.Authentication.LoginActivity;
import com.moutamid.easyroomapp.R;

public class ProfileFragment extends Fragment {
    TextView name, email;

    String userID;
    LinearLayout logout;
    Button login;
    LinearLayout stranger_section, listing_property, terms_and_conditions, teerms_and_conditions;
    NestedScrollView login_section;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile2, container, false);
        stranger_section = view.findViewById(R.id.stranger_section);
        terms_and_conditions = view.findViewById(R.id.terms_and_conditions);
        teerms_and_conditions = view.findViewById(R.id.teerms_and_conditions);
        listing_property = view.findViewById(R.id.listing_property);
        login = view.findViewById(R.id.login);
        logout = view.findViewById(R.id.logout);
        name = view.findViewById(R.id.name);
        email = view.findViewById(R.id.email);
        login_section = view.findViewById(R.id.login_section);
        userID = Stash.getString("userID");
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            stranger_section.setVisibility(View.GONE);
            login_section.setVisibility(View.VISIBLE);
        } else {
            login_section.setVisibility(View.GONE);
            stranger_section.setVisibility(View.VISIBLE);
        }
//        terms_and_conditions.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getActivity().startActivity(new Intent(getContext(), PrivacyActivity.class));
//
//            }
//        });
//        teerms_and_conditions.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getActivity().startActivity(new Intent(getContext(), PrivacyActivity.class));
//
//            }
//        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Stash.clearAll();
                getActivity().startActivity(new Intent(getContext(), LoginActivity.class));
//                getActivity().finishAffinity();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Stash.clearAll();
                FirebaseAuth.getInstance().signOut();
                getActivity().startActivity(new Intent(getContext(), LoginActivity.class));
                getActivity().finishAffinity();
            }
        });

        return view;
    }


}