package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdvertAdapter extends RecyclerView.Adapter<AdvertAdapter.AdvertViewHolder> {

    private List<Advert> advertList;
    private Context context;

    public AdvertAdapter(List<Advert> advertList, Context context) {
        this.advertList = advertList;
        this.context = context;
    }

    @NonNull
    @Override
    public AdvertViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_advert, parent, false);
        return new AdvertViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdvertViewHolder holder, int position) {
        Advert advert = advertList.get(position);
        holder.itemName.setText(advert.getName());

        holder.removeButton.setOnClickListener(v -> {
            // Remove the item from the list
            advertList.remove(position);
            notifyItemRemoved(position);

            // Update the database or perform any other necessary operations

            // Optional: Notify the user that the item has been removed
            Toast.makeText(context, "Item removed", Toast.LENGTH_SHORT).show();
        });
    }


    @Override
    public int getItemCount() {
        return advertList.size();
    }

    public static class AdvertViewHolder extends RecyclerView.ViewHolder {
        Button removeButton;
        TextView itemName;

        public AdvertViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.item_name);
            removeButton = itemView.findViewById(R.id.remove_button); // Initialize the button
        }
    }
}
