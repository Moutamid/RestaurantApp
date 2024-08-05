package com.moutamid.restaurantapp.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fxn.stash.Stash;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.moutamid.restaurantapp.Activity.MainActivity;
import com.moutamid.restaurantapp.Activity.MapActivity;
import com.moutamid.restaurantapp.Adapter.AllResturantsAdapter;
import com.moutamid.restaurantapp.Model.LocationModel;
import com.moutamid.restaurantapp.Model.ResturantModel;
import com.moutamid.restaurantapp.R;
import com.moutamid.restaurantapp.helper.Config;

import java.util.ArrayList;
import java.util.List;

public class ResturantFragment extends Fragment {

    RecyclerView content_rcv;
    public List<ResturantModel> productModelList = new ArrayList<>();
    AllResturantsAdapter herbsAdapter;

    EditText search;
    TextView no_text;
    ImageView mic;
    String lcode = "en-US";
    ImageView map;
    ArrayList<LocationModel> userArrayList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_resturant, container, false);
        map = view.findViewById(R.id.map);
        content_rcv = view.findViewById(R.id.content_rcv);
        search = view.findViewById(R.id.search);
        no_text = view.findViewById(R.id.no_text);
        mic = view.findViewById(R.id.mic);
        content_rcv.setLayoutManager(new GridLayoutManager(getContext(), 1));
        herbsAdapter = new AllResturantsAdapter(getContext(), productModelList);
        content_rcv.setAdapter(herbsAdapter);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), MapActivity.class));
            }
        });
        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        // if result is not empty
                        if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                            // get data and append it to editText
                            ArrayList<String> d = result.getData().getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                            search.setText(" " + d.get(0));
                        }
                    }
                });
        mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, lcode);
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak now!");
                activityResultLauncher.launch(intent);
            }
        });


        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filter(charSequence.toString());

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());


            }
        });

        if (Config.isNetworkAvailable(getContext())) {
            getProducts();
        } else {
            Toast.makeText(getContext(), "No network connection available.", Toast.LENGTH_SHORT).show();
        }

        return view;
    }


    private void getProducts() {
        Config.showProgressDialog(getContext());
        Config.databaseReference().child(Config.restaurants).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                productModelList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    ResturantModel herbsModel = ds.getValue(ResturantModel.class);
                    productModelList.add(herbsModel);
                }
                herbsAdapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });
        Config.databaseReference().child("Locations").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    ResturantModel herbsModel1 = ds.getValue(ResturantModel.class);
                    userArrayList.add(new LocationModel(herbsModel1.getLat(), herbsModel1.getLng(), herbsModel1.getName()));
                    Config.dismissProgressDialog();

                }
                Stash.put("Locations", userArrayList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Config.dismissProgressDialog();

    }

    private void filter(String text) {
        // creating a new array list to filter our data.
        ArrayList<ResturantModel> filteredlist = new ArrayList<ResturantModel>();

        // running a for loop to compare elements.
        for (ResturantModel item : productModelList) {
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {
            content_rcv.setVisibility(View.GONE);
            no_text.setVisibility(View.VISIBLE);
        } else {
            no_text.setVisibility(View.GONE);
            content_rcv.setVisibility(View.VISIBLE);

            // at last we are passing that filtered
            // list to our adapter class.
            herbsAdapter.filterList(filteredlist);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}