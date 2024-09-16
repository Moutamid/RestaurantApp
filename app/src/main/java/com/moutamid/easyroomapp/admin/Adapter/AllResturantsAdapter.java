package com.moutamid.easyroomapp.admin.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.moutamid.halalfoodadmin.Model.ResturantModel;
import com.moutamid.halalfoodadmin.R;
import com.moutamid.halalfoodadmin.helper.Config;

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

    @Override
    public void onBindViewHolder(@NonNull GalleryPhotosViewHolder holder, final int position) {
        final String name = productModels.get(position).getName();
        final String address = productModels.get(position).getAddress();
        final String image = productModels.get(position).getImage_url();

        holder.resturant_name.setText(name);
        holder.resturant_discription.setText(address);
        Glide.with(ctx).load(image).into(holder.image);
        holder.remove_herb.setOnClickListener(view -> {
            AlertDialog.Builder dialog = new AlertDialog.Builder(ctx);
            dialog.setTitle("Delete Restaurants");
            dialog.setMessage("Are you sure to delete this restaurant");
            dialog.setPositiveButton("Yes", (dialogInterface, i) -> {
                dialogInterface.dismiss();
                Config.showProgressDialog(ctx);
                Config.databaseReference().child("Restaurants").child(productModels.get(position).getKey()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Config.dismissProgressDialog();
//                        if (task.isSuccessful()){
//                            productModels.remove(position);
//                            notifyDataSetChanged();
//                        }
                    }
                }).addOnFailureListener(e -> {
                    Config.dismissProgressDialog();
                    Toast.makeText(ctx, "Something went wrong.", Toast.LENGTH_SHORT).show();
                });
            }).setNegativeButton("No", (dialogInterface, i) -> {
                dialogInterface.dismiss();
            });
            dialog.show();
        });


    }

    @Override
    public int getItemCount() {
        return productModels.size();
    }

    public class GalleryPhotosViewHolder extends RecyclerView.ViewHolder {

        TextView resturant_discription, resturant_name;
        ImageView remove_herb, image;

        public GalleryPhotosViewHolder(@NonNull View itemView) {
            super(itemView);
            resturant_discription = itemView.findViewById(R.id.resturant_discription);
            resturant_name = itemView.findViewById(R.id.resturant_name);
            remove_herb = itemView.findViewById(R.id.remove_herb);
            image = itemView.findViewById(R.id.image);

        }
    }
}
