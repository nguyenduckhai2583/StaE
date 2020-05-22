package com.example.stapemotion.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stapemotion.GraphActivity;
import com.example.stapemotion.R;
import com.example.stapemotion.model.Location;

import java.util.ArrayList;
import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.myHolder> {

    List<Location> list;

    Context context;

    public LocationAdapter(ArrayList<Location> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public myHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_location, parent, false);
        return new myHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myHolder holder, int position) {
        holder.itemTvLocation_name.setText(list.get(position).getName());
        holder.itemTvLocation_address.setText(list.get(position).getAddress());
        holder.itemHomeImg_img.setImageResource(list.get(position).getImg());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class myHolder extends RecyclerView.ViewHolder {

        TextView itemTvLocation_name, itemTvLocation_address;
        ImageView itemHomeImg_img;
        RelativeLayout itemLocation_container;

        public myHolder(@NonNull View itemView) {
            super(itemView);
            itemTvLocation_name = itemView.findViewById(R.id.itemTvLocation_name);
            itemTvLocation_address = itemView.findViewById(R.id.itemTvLocation_address);
            itemHomeImg_img = itemView.findViewById(R.id.itemHomeImg_img);
            itemLocation_container = itemView.findViewById(R.id.itemLocation_container);

            itemLocation_container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(context, GraphActivity.class));
                }
            });
        }
    }
}
