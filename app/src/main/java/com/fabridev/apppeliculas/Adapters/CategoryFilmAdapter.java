package com.fabridev.apppeliculas.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fabridev.apppeliculas.R;

import java.util.List;

public class CategoryFilmAdapter extends RecyclerView.Adapter<CategoryFilmAdapter.Viewholder> {
    List<String> items;
    Context context;

    public CategoryFilmAdapter(List<String> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public CategoryFilmAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_category, parent, false);
        return new Viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryFilmAdapter.Viewholder holder, int position) {
        holder.titletxt.setText(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView titletxt;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            titletxt = itemView.findViewById(R.id.titletxt);
        }
    }
}
