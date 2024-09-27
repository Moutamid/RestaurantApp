package com.moutamid.easyroomapp.Fragment;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.fxn.stash.Stash;
import com.google.firebase.auth.FirebaseAuth;
import com.moutamid.easyroomapp.Activity.Authentication.LoginActivity;
import com.moutamid.easyroomapp.Activity.EditProfileActivity;
import com.moutamid.easyroomapp.Activity.WebViewActivity;
import com.moutamid.easyroomapp.Model.UserModel;
import com.moutamid.easyroomapp.R;

public class ProfileFragment extends Fragment {
    RelativeLayout profile;
    TextView name_txt, name_latter, textView7;
    RelativeLayout rlShare, rlRate, delete_data, privacy;
    UserModel userModel;
    TextView logout_text;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        userModel = (UserModel) Stash.getObject("UserDetails", UserModel.class);
        logout_text = view.findViewById(R.id.logout_text);
        name_txt = view.findViewById(R.id.textView6);
        rlShare = view.findViewById(R.id.rlShare);
        rlRate = view.findViewById(R.id.rlRate);
        privacy = view.findViewById(R.id.privacy);
        delete_data = view.findViewById(R.id.delete_data);
        name_latter = view.findViewById(R.id.textView5);
        profile = view.findViewById(R.id.profile);
        if (userModel != null) {
            if (userModel.name != null) {
                name_txt.setText(userModel.name);
                char c = userModel.name.charAt(0);
                name_latter.setText(c + "");
        } else {
                name_txt.setText("Guest");
                name_latter.setText("G");

            }
        }
        if (Stash.getBoolean("guest")) {
            logout_text.setText("Login/Register");
        }
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Stash.getBoolean("guest")) {
                    Toast.makeText(getContext(), "Please register first", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getContext(), LoginActivity.class));

                } else {
                    startActivity(new Intent(getContext(), EditProfileActivity.class));
                }
            }
        });
        delete_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Stash.getBoolean("guest")) {
                    startActivity(new Intent(getContext(), LoginActivity.class));

                } else {
                    FirebaseAuth.getInstance().signOut();
                    Stash.clear("UserDetails");
                    startActivity(new Intent(getContext(), LoginActivity.class));
                    getActivity().finishAffinity();
                }

            }
        });
        privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), WebViewActivity.class));
            }
        });

        rlShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
                    String shareMessage = userModel.name + " found a helpful application, Here is the link\n\n";


                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + getActivity().getPackageName() + "\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "Choose one"));
                } catch (Exception e) {
                    //e.toString();
                }
            }
        });
        rlRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("market://details?id=" + getActivity().getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    getActivity().startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    getActivity().startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + getActivity().getPackageName())));
                }
            }
        });
        return view;
    }
}