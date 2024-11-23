package com.fabridev.apppeliculas.Adapters;

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
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.fabridev.apppeliculas.Activities.DetailsActivity;
import com.fabridev.apppeliculas.Domains.Film;
import com.fabridev.apppeliculas.R;

import java.util.ArrayList;

public class Film_ListAdapter extends RecyclerView.Adapter<Film_ListAdapter.Viewholder> {
    ArrayList<Film> items;
    Context context;

    public Film_ListAdapter(ArrayList<Film> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public Film_ListAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.film_viewholder, parent, false);
        return new Viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull Film_ListAdapter.Viewholder holder, int position) {
        holder.titletxt.setText(items.get(position).getTitle());
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transform(new CenterCrop(), new RoundedCorners(30));
        Glide.with(context)
                .load(items.get(position).getPoster())
                .apply(requestOptions)
                .into(holder.pic);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailsActivity.class);
            intent.putExtra("object", items.get(position));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView titletxt;
        ImageView pic;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            titletxt = itemView.findViewById(R.id.nameTxt);
            pic = itemView.findViewById(R.id.pic);
        }
    }
}
