package com.moutamid.easyroomapp.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fxn.stash.Stash;
import com.moutamid.easyroomapp.Adapter.AllResturantsAdapter;
import com.moutamid.easyroomapp.Model.ResturantModel;
import com.moutamid.easyroomapp.R;
import com.moutamid.easyroomapp.helper.Config;

import java.util.ArrayList;
import java.util.List;

public class FavouriteFragment extends Fragment {

    RecyclerView content_rcv;
    public List<ResturantModel> productModelList = new ArrayList<>();
    AllResturantsAdapter retaurantAdapter;
    TextView no_text;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_favourite, container, false);
        content_rcv = view.findViewById(R.id.content_rcv);
        no_text = view.findViewById(R.id.no_text);
        content_rcv.setLayoutManager(new GridLayoutManager(getContext(), 1));
        ArrayList<ResturantModel> resturantModelArrayList = Stash.getArrayList(Config.favourite, ResturantModel.class);
        retaurantAdapter = new AllResturantsAdapter(getContext(), resturantModelArrayList);
        content_rcv.setAdapter(retaurantAdapter);
        retaurantAdapter.notifyDataSetChanged();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ArrayList<ResturantModel> resturantModelArrayList = Stash.getArrayList(Config.favourite, ResturantModel.class);
        retaurantAdapter = new AllResturantsAdapter(getContext(), resturantModelArrayList);
        content_rcv.setAdapter(retaurantAdapter);
        retaurantAdapter.notifyDataSetChanged();

    }
}