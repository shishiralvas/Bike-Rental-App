package com.example.bikerental.Adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bikerental.Model.VehicleCategory;
import com.example.bikerental.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class VehicleCategoryAdapter extends RecyclerView.Adapter<VehicleCategoryAdapter.VehicleCategoryHolder> {

    private Context context;
    private ArrayList<VehicleCategory> vehicleCategories;
    private onCategoryListener onCategoryListener;

    public VehicleCategoryAdapter(Context context, ArrayList<VehicleCategory> vehicleCategories, onCategoryListener onCategoryListener){
        this.context = context;
        this.vehicleCategories = vehicleCategories;
        this.onCategoryListener = onCategoryListener;
    }

    @NonNull
    @Override
    public VehicleCategoryHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.vehicle_category_card,null);
        return new VehicleCategoryHolder(view,onCategoryListener);
    }

    @Override
    public void onBindViewHolder(@NonNull VehicleCategoryHolder vehicleCategoryHolder, int i) {
        VehicleCategory vehicleCategory = vehicleCategories.get(i);
        vehicleCategoryHolder.vehicleCategory.setText(vehicleCategory.getCategory());
        vehicleCategoryHolder.quantity.setText("Total: 3 ");
        vehicleCategoryHolder.card.setBackgroundTintList(ColorStateList.valueOf(vehicleCategory.getColorCard()));
        String url = vehicleCategory.getCategoryImageURL();

//        Log.d("MainActivity",url);

        Picasso.get().load(url).into(vehicleCategoryHolder.categoryImage);
    }

    @Override
    public int getItemCount() {
        return vehicleCategories.size();
    }

    class VehicleCategoryHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{

        TextView vehicleCategory, quantity;
        ImageView categoryImage;
        Button select;
        ConstraintLayout card;
        onCategoryListener onCategoryListener;
        public VehicleCategoryHolder(@NonNull View itemView, final onCategoryListener onCategoryListener) {
            super(itemView);
            vehicleCategory = itemView.findViewById(R.id.vehicleCategory);
            quantity = itemView.findViewById(R.id.quantity);
            card = itemView.findViewById(R.id.card);
            select = itemView.findViewById(R.id.select);
            categoryImage = itemView.findViewById(R.id.categoryImage);

            this.onCategoryListener = onCategoryListener;
            itemView.setOnClickListener(this);

            select.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onCategoryListener.onSelectClick(getAdapterPosition());
                }
            });
        }

        @Override
        public void onClick(View v) {
            onCategoryListener.onCategoryClick(getAdapterPosition());
        }
    }

    public interface onCategoryListener{
        void onCategoryClick(int position);
        void onSelectClick(int position);
    }

}
