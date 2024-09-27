package com.moutamid.easyroomapp.landlord.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.fxn.stash.Stash;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.moutamid.easyroomapp.R;
import com.moutamid.easyroomapp.helper.Config;
import com.moutamid.easyroomapp.landlord.activities.Home.VillaDetailsActivity;
import com.moutamid.easyroomapp.landlord.model.Villa;

import java.util.ArrayList;
import java.util.List;

public class OwnVillaAdapter extends RecyclerView.Adapter<OwnVillaAdapter.GalleryPhotosViewHolder> {


    Context ctx;
    List<Villa> productModels;
    Activity activity;

    public OwnVillaAdapter(Activity activity, Context ctx, List<Villa> productModels) {
        this.ctx = ctx;
        this.productModels = productModels;
        this.activity = activity;
    }

    @NonNull
    @Override
    public GalleryPhotosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.own_villa_item, parent, false);
        return new GalleryPhotosViewHolder(view);
    }

    public void filterList(ArrayList<Villa> filterlist) {
        productModels = filterlist;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryPhotosViewHolder holder, final int position) {
        Villa villa = productModels.get(position);
        holder.villa_name.setText(villa.getName());
        Log.d("dataa", villa.getAvailable() + "  dtaa");


        Glide.with(ctx).load(villa.getImage()).into(holder.image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Stash.put(Config.currentModel, villa);
                Stash.put("onetime", true);
                ctx.startActivity(new Intent(ctx, VillaDetailsActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {

        return productModels.size();
    }

    public class GalleryPhotosViewHolder extends RecyclerView.ViewHolder {

        TextView villa_name;
        ImageView image;


        public GalleryPhotosViewHolder(@NonNull View itemView) {
            super(itemView);
            villa_name = itemView.findViewById(R.id.villa_name);
            image = itemView.findViewById(R.id.image);
        }
    }
}
