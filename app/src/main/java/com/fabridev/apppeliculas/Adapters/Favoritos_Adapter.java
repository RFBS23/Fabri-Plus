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

public class Favoritos_Adapter extends RecyclerView.Adapter<Favoritos_Adapter.ViewHolder> {
    ArrayList<Film> items;
    Context context;

    public Favoritos_Adapter(ArrayList<Film> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public Favoritos_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new Favoritos_Adapter.ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull Favoritos_Adapter.ViewHolder holder, int position) {
        holder.titleTxt.setText(items.get(position).getTitle());
        String genreString = String.join(", ", items.get(position).getGenre());
        holder.categoriaTxt.setText(genreString);
        holder.timeTxt.setText(items.get(position).getTime());
        holder.yearTxt.setText(items.get(position).getYear() + "  |");
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transform(new CenterCrop(), new RoundedCorners(30));
        Glide.with(context)
                .load(items.get(position).getPoster())
                .apply(requestOptions)
                .into(holder.picList);

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

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleTxt, categoriaTxt, yearTxt, timeTxt;
        ImageView picList;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTxt = itemView.findViewById(R.id.titleTxt);
            categoriaTxt = itemView.findViewById(R.id.categoriaTxt);
            yearTxt = itemView.findViewById(R.id.yearTxt);
            timeTxt = itemView.findViewById(R.id.timeTxt);
            picList = itemView.findViewById(R.id.picList);
        }
    }
}
