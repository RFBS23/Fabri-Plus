package com.fabridev.apppeliculas.ViewMore;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.fabridev.apppeliculas.Activities.Dashboard;
import com.fabridev.apppeliculas.Adapters.Film_ListAdapter;
import com.fabridev.apppeliculas.Adapters.List_Adapter;
import com.fabridev.apppeliculas.Domains.Film;
import com.fabridev.apppeliculas.R;
import com.fabridev.apppeliculas.databinding.ActivityTvVmactivityBinding;
import com.fabridev.apppeliculas.databinding.ActivityVerMasBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TvVMActivity extends AppCompatActivity {
    
    ActivityTvVmactivityBinding binding;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTvVmactivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        database = FirebaseDatabase.getInstance();

        Window w=getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        initPelisTV();
    }

    private void initPelisTV(){
        DatabaseReference myRef = database.getReference("Peliculatelevision");
        binding.btnBack.setOnClickListener(v -> finish());
        binding.progressBar.setVisibility(View.VISIBLE);
        ArrayList<Film> items = new ArrayList<>();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for(DataSnapshot issue:snapshot.getChildren()){
                        items.add(issue.getValue(Film.class));
                    }
                    if(!items.isEmpty()){
                        binding.TituloCatTxt.setText("Peliculas de Television");
                        binding.movieListView.setLayoutManager(new LinearLayoutManager(TvVMActivity.this, LinearLayoutManager.VERTICAL, false));
                        binding.movieListView.setAdapter(new List_Adapter(items));
                    }
                    binding.progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}