package com.moutamid.easyroomapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fxn.stash.Stash;
import com.makeramen.roundedimageview.RoundedImageView;
import com.moutamid.easyroomapp.Activity.EasyRoomDetailsActivity;
import com.moutamid.easyroomapp.Model.VillaModel;
import com.moutamid.easyroomapp.R;
import com.moutamid.easyroomapp.helper.Config;

import java.util.ArrayList;
import java.util.List;

public class FavouriteVillaAdapter extends RecyclerView.Adapter<FavouriteVillaAdapter.GalleryPhotosViewHolder> {


    Context ctx;
    List<VillaModel> productModels;
    private static final double EARTH_RADIUS = 6371;


    public FavouriteVillaAdapter(Context ctx, List<VillaModel> productModels) {
        this.ctx = ctx;
        this.productModels = productModels;
    }

    @NonNull
    @Override
    public GalleryPhotosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.favourite, parent, false);
        return new GalleryPhotosViewHolder(view);
    }

    public void filterList(ArrayList<VillaModel> filterlist) {
        productModels = filterlist;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryPhotosViewHolder holder, final int position) {

        VillaModel villa = productModels.get(position);
        double distance = calculateDistance(Config.lat, Config.lng, villa.getLat(), villa.getLng());
        holder.villa_name.setText(villa.getName());
        holder.bill.setText("$"+villa.getBill()+"/month");
        holder.distance.setText(String.format("%.2f ", distance) + " km away from you");

        Glide.with(ctx).load(villa.getImage()).into(holder.image);
        Glide.with(ctx).load(villa.getImage()).into(holder.image_bg);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (villa.available.equals("available")) {
                    Stash.put(Config.currentModel, villa);
                    ctx.startActivity(new Intent(ctx, EasyRoomDetailsActivity.class));
                } else {
                    Toast.makeText(ctx, "VillaModel is not available yet", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return productModels.size();
    }

    public class GalleryPhotosViewHolder extends RecyclerView.ViewHolder {

        TextView bill, villa_name, distance, checkbox;
        RoundedImageView image;
        ImageView image_bg;

        public GalleryPhotosViewHolder(@NonNull View itemView) {
            super(itemView);
            bill = itemView.findViewById(R.id.bill);
            villa_name = itemView.findViewById(R.id.villa_name);
            distance = itemView.findViewById(R.id.distance);
            image = itemView.findViewById(R.id.image);
            checkbox = itemView.findViewById(R.id.checkbox);
            image_bg = itemView.findViewById(R.id.image_bg);

        }
    }
    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double distance = EARTH_RADIUS * c;

        return distance;
    }

}
