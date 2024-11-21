package com.fabridev.apppeliculas.Activities;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.fabridev.apppeliculas.Adapters.Film_ListAdapter;
import com.fabridev.apppeliculas.Adapters.SlidersAdapter;
import com.fabridev.apppeliculas.Domains.Film;
import com.fabridev.apppeliculas.Domains.SliderItems;
import com.fabridev.apppeliculas.R;
import com.fabridev.apppeliculas.databinding.ActivityMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Dashboard extends AppCompatActivity {

    private int selectedTab = 1;

    LinearLayout home_icon, like_icon, series_icon, icon_masinfo;
    ImageView iv_home, iv_like, iv_series, iv_masinfo;
    TextView txthome, txt_like, txt_series, txt_masinfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        database = FirebaseDatabase.getInstance();

        loadUI();
        activitys();

        Window w=getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        initBanner();
        initComedia();
        initAnimacion();
        inittodas();
    }

    ActivityMainBinding binding;
    private FirebaseDatabase database;

    private Handler sliderhandler = new Handler();
    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            binding.viewPagerBanners.setCurrentItem(binding.viewPagerBanners.getCurrentItem() + 1 );
        }
    };

    private void loadUI() {
        home_icon = findViewById(R.id.home_icon);
        like_icon = findViewById(R.id.like_icon);
        series_icon = findViewById(R.id.series_icon);
        icon_masinfo = findViewById(R.id.icon_masinfo);
        iv_home = findViewById(R.id.iv_home);
        iv_like = findViewById(R.id.iv_like);
        iv_series = findViewById(R.id.iv_series);
        iv_masinfo = findViewById(R.id.iv_masinfo);
        txthome = findViewById(R.id.txthome);
        txt_like = findViewById(R.id.txt_like);
        txt_series = findViewById(R.id.txt_series);
        txt_masinfo = findViewById(R.id.txt_masinfo);
    }

    private void activitys() {

        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragmentContainer, HomeFragment.class, null)
                .commit();

        home_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedTab != 1){

                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragmentContainer, HomeFragment.class, null)
                            .commit();

                    txt_like.setVisibility(View.GONE);
                    txt_series.setVisibility(View.GONE);
                    txt_masinfo.setVisibility(View.GONE);

                    iv_like.setImageResource(R.drawable.corazon);
                    iv_series.setImageResource(R.drawable.series);
                    iv_masinfo.setImageResource(R.drawable.info);

                    like_icon.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    series_icon.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    icon_masinfo.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    txthome.setVisibility(View.VISIBLE);
                    txthome.setTextColor(getResources().getColor(R.color.text_inicio));
                    iv_home.setImageResource(R.drawable.inicio);
                    home_icon.setBackgroundResource(R.drawable.borde_redondo);

                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    home_icon.startAnimation(scaleAnimation);
                    selectedTab = 1;
                }
            }
        });

        like_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedTab != 2){

                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragmentContainer, FavoritosFragment.class, null)
                            .commit();

                    txthome.setVisibility(View.GONE);
                    txt_series.setVisibility(View.GONE);
                    txt_masinfo.setVisibility(View.GONE);

                    iv_home.setImageResource(R.drawable.inicio);
                    iv_series.setImageResource(R.drawable.series);
                    iv_masinfo.setImageResource(R.drawable.info);

                    home_icon.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    series_icon.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    icon_masinfo.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    txt_like.setVisibility(View.VISIBLE);
                    txt_like.setTextColor(getResources().getColor(R.color.text_favorito));
                    iv_like.setImageResource(R.drawable.corazon);
                    like_icon.setBackgroundResource(R.drawable.like_redondo);

                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    like_icon.startAnimation(scaleAnimation);
                    selectedTab = 2;
                }
            }
        });

        series_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedTab != 3){

                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragmentContainer, SeriesFragment.class, null)
                            .commit();

                    txthome.setVisibility(View.GONE);
                    txt_like.setVisibility(View.GONE);
                    txt_masinfo.setVisibility(View.GONE);

                    iv_home.setImageResource(R.drawable.inicio);
                    iv_like.setImageResource(R.drawable.corazon);
                    iv_masinfo.setImageResource(R.drawable.info);

                    home_icon.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    like_icon.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    icon_masinfo.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    txt_series.setVisibility(View.VISIBLE);
                    txt_series.setTextColor(getResources().getColor(R.color.text_series));
                    iv_series.setImageResource(R.drawable.series);
                    series_icon.setBackgroundResource(R.drawable.series_redondo);

                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    series_icon.startAnimation(scaleAnimation);
                    selectedTab = 3;
                }
            }
        });

        icon_masinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedTab != 4){
                    getSupportFragmentManager().beginTransaction()
                            .setReorderingAllowed(true)
                            .replace(R.id.fragmentContainer, InfoFragment.class, null)
                            .commit();
                    txthome.setVisibility(View.GONE);
                    txt_series.setVisibility(View.GONE);
                    txt_like.setVisibility(View.GONE);

                    iv_home.setImageResource(R.drawable.inicio);
                    iv_series.setImageResource(R.drawable.series);
                    iv_like.setImageResource(R.drawable.corazon);

                    home_icon.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    series_icon.setBackgroundColor(getResources().getColor(android.R.color.transparent));
                    like_icon.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    txt_masinfo.setVisibility(View.VISIBLE);
                    txt_masinfo.setTextColor(getResources().getColor(R.color.text_info));
                    iv_masinfo.setImageResource(R.drawable.info);
                    icon_masinfo.setBackgroundResource(R.drawable.info_redondo);

                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    icon_masinfo.startAnimation(scaleAnimation);
                    selectedTab = 4;
                }
            }
        });
    }

    private void inittodas(){
        DatabaseReference myRef = database.getReference("Todas");
        binding.progresstodas.setVisibility(View.VISIBLE);
        ArrayList<Film> items = new ArrayList<>();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for(DataSnapshot issue:snapshot.getChildren()){
                        items.add(issue.getValue(Film.class));
                    }
                    if(!items.isEmpty()){
                        binding.recyclerViewtodas.setLayoutManager(new LinearLayoutManager(Dashboard.this, LinearLayoutManager.HORIZONTAL, false));
                        binding.recyclerViewtodas.setAdapter(new Film_ListAdapter(items));
                    }
                    binding.progresstodas.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initAnimacion(){
        DatabaseReference myRef = database.getReference("Animacion");
        binding.progressAnimacion.setVisibility(View.VISIBLE);
        ArrayList<Film> items = new ArrayList<>();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for(DataSnapshot issue:snapshot.getChildren()){
                        items.add(issue.getValue(Film.class));
                    }
                    if(!items.isEmpty()){
                        binding.recyclerViewAnimacion.setLayoutManager(new LinearLayoutManager(Dashboard.this, LinearLayoutManager.HORIZONTAL, false));
                        binding.recyclerViewAnimacion.setAdapter(new Film_ListAdapter(items));
                    }
                    binding.progressAnimacion.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initComedia(){
        DatabaseReference myRef = database.getReference("Comedia");
        binding.progressComedia.setVisibility(View.VISIBLE);
        ArrayList<Film> items = new ArrayList<>();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for(DataSnapshot issue:snapshot.getChildren()){
                        items.add(issue.getValue(Film.class));
                    }
                    if(!items.isEmpty()){
                        binding.recyclerViewComedia.setLayoutManager(new LinearLayoutManager(Dashboard.this, LinearLayoutManager.HORIZONTAL, false));
                        binding.recyclerViewComedia.setAdapter(new Film_ListAdapter(items));
                    }
                    binding.progressComedia.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initBanner(){
        DatabaseReference myRef = database.getReference("Banners");
        binding.progressBanner.setVisibility(View.VISIBLE);
        ArrayList<SliderItems> items = new ArrayList<>();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for(DataSnapshot issue:snapshot.getChildren()){
                        items.add(issue.getValue(SliderItems.class));
                    }
                    banners(items);
                    binding.progressBanner.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void banners(ArrayList<SliderItems> items){
        binding.viewPagerBanners.setAdapter(new SlidersAdapter(items, binding.viewPagerBanners));
        binding.viewPagerBanners.setClipToPadding(false);
        binding.viewPagerBanners.setClipChildren(false);
        binding.viewPagerBanners.setOffscreenPageLimit(4);
        binding.viewPagerBanners.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1-Math.abs(position);
                page.setScaleY(0.85f+r*0.15f);
            }
        });
        binding.viewPagerBanners.setPageTransformer(compositePageTransformer);
        binding.viewPagerBanners.setCurrentItem(1);
        binding.viewPagerBanners.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderhandler.removeCallbacks(sliderRunnable);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        sliderhandler.removeCallbacks(sliderRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sliderhandler.postDelayed(sliderRunnable, 2000);
    }

}