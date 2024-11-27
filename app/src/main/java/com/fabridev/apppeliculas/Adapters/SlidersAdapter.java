package com.fabridev.apppeliculas.Adapters;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.fabridev.apppeliculas.Activities.Dashboard;
import com.fabridev.apppeliculas.Activities.DetailsActivity;
import com.fabridev.apppeliculas.Activities.playmovie_Activity;
import com.fabridev.apppeliculas.Domains.SliderItems;
import com.fabridev.apppeliculas.R;
import com.fabridev.apppeliculas.databinding.ActivityDetailsBinding;

import java.util.List;

public class SlidersAdapter extends RecyclerView.Adapter<SlidersAdapter.SliderViewholder> {

    private List<SliderItems> sliderItems;
    private ViewPager2 viewPager2;
    private Context context;
    private ActivityDetailsBinding binding;

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            sliderItems.addAll(sliderItems);
            notifyDataSetChanged();
        }
    };

    public SlidersAdapter(List<SliderItems> sliderItems, ViewPager2 viewPager2) {
        this.sliderItems = sliderItems;
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public SlidersAdapter.SliderViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new SliderViewholder(LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_viewholder, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SlidersAdapter.SliderViewholder holder, int position) {
        holder.setImage(sliderItems.get(position));
        if(position == sliderItems.size() - 2){
            viewPager2.post(runnable);
        }
    }

    @Override
    public int getItemCount() {
        return sliderItems.size();
    }

    public class SliderViewholder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView nametxt, genretxt, agetxt, yeartxt, timetxt;
        private Button btnTrailer;

        public SliderViewholder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageSlider);
            nametxt = itemView.findViewById(R.id.nameTxt);
            genretxt = itemView.findViewById(R.id.genreView);
            agetxt = itemView.findViewById(R.id.agetxt);
            yeartxt = itemView.findViewById(R.id.yeartxt);
            timetxt = itemView.findViewById(R.id.timetxt);
            btnTrailer = itemView.findViewById(R.id.btnTrailer);
        }
        void setImage(SliderItems sliderItems){
            RequestOptions requestOptions = new RequestOptions();
            requestOptions = requestOptions.transform(new CenterCrop(), new RoundedCorners(60));
            Glide.with(context)
                    .load(sliderItems.getImage())
                    .apply(requestOptions)
                    .into(imageView);
            nametxt.setText(sliderItems.getName());
            genretxt.setText(sliderItems.getGenre());
            agetxt.setText(sliderItems.getAge());
            yeartxt.setText("" + sliderItems.getYear());
            timetxt.setText(sliderItems.getTime());

            btnTrailer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String id = sliderItems.getTrailer().replace("https://www.youtube.com/watch?v=", "");
                    Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
                    Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(sliderItems.getTrailer()));
                    try {
                        context.startActivity(appIntent);
                    } catch (ActivityNotFoundException ex) {
                        context.startActivity(webIntent);
                    }
                }
            });
        }
    }
}
