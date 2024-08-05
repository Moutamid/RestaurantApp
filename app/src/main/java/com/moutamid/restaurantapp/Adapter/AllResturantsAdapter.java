package com.moutamid.restaurantapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fxn.stash.Stash;
import com.moutamid.restaurantapp.Activity.RestaurantsDetailsActivity;
import com.moutamid.restaurantapp.Model.ResturantModel;
import com.moutamid.restaurantapp.R;
import com.moutamid.restaurantapp.helper.Config;

import java.util.ArrayList;
import java.util.List;

public class AllResturantsAdapter extends RecyclerView.Adapter<AllResturantsAdapter.GalleryPhotosViewHolder> {


    Context ctx;
    List<ResturantModel> productModels;

    public AllResturantsAdapter(Context ctx, List<ResturantModel> productModels) {
        this.ctx = ctx;
        this.productModels = productModels;
    }

    @NonNull
    @Override
    public GalleryPhotosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.allresturants, parent, false);
        return new GalleryPhotosViewHolder(view);
    }
    public void filterList(ArrayList<ResturantModel> filterlist) {

        productModels = filterlist;
        notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(@NonNull GalleryPhotosViewHolder holder, final int position) {
        ResturantModel resturantModel = productModels.get(position);
        holder.resturant_name.setText(resturantModel.getName());
        holder.resturant_discription.setText(resturantModel.getShort_description());
        Glide.with(ctx).load(resturantModel.getImage_url()).into(holder.image);
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(ctx, RestaurantsDetailsActivity.class);
            Stash.put(Config.currentModel, resturantModel);
            ctx.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return productModels.size();
    }

    public class GalleryPhotosViewHolder extends RecyclerView.ViewHolder {

        TextView resturant_discription, resturant_name;
        ImageView image;

        public GalleryPhotosViewHolder(@NonNull View itemView) {
            super(itemView);
            resturant_discription = itemView.findViewById(R.id.resturant_discription);
            resturant_name = itemView.findViewById(R.id.resturant_name);
            image = itemView.findViewById(R.id.image);

        }
    }
}
