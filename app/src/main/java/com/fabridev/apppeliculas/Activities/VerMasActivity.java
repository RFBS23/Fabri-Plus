package com.fabridev.apppeliculas.Activities;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.fabridev.apppeliculas.Adapters.Film_ListAdapter;
import com.fabridev.apppeliculas.Adapters.List_Adapter;
import com.fabridev.apppeliculas.Domains.Film;
import com.fabridev.apppeliculas.databinding.ActivityMainBinding;
import com.fabridev.apppeliculas.databinding.ActivityVerMasBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class VerMasActivity extends AppCompatActivity {

    ActivityVerMasBinding binding;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVerMasBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        database = FirebaseDatabase.getInstance();

        Window w=getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        inittodas();
    }

    private void inittodas(){
        DatabaseReference myRef = database.getReference("Todas");
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
                        binding.TituloCatTxt.setText("Todas Las Peliculas");
                        binding.movieListView.setLayoutManager(new LinearLayoutManager(VerMasActivity.this, LinearLayoutManager.VERTICAL, false));
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