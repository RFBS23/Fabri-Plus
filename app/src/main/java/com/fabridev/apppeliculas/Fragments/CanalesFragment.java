package com.fabridev.apppeliculas.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fabridev.apppeliculas.Activities.VerMasActivity;
import com.fabridev.apppeliculas.Adapters.Canal_Adapter;
import com.fabridev.apppeliculas.Adapters.List_Adapter;
import com.fabridev.apppeliculas.Domains.Film;
import com.fabridev.apppeliculas.R;
import com.fabridev.apppeliculas.databinding.FragmentCanalesBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CanalesFragment extends Fragment {

    private FragmentCanalesBinding binding; // Usa el binding correcto según tu layout
    private FirebaseDatabase database;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCanalesBinding.inflate(inflater, container, false);
        database = FirebaseDatabase.getInstance();
        inittelevision();
        return binding.getRoot();
    }

    private void inittelevision() {
        DatabaseReference myRef = database.getReference("AmericaTV");
        binding.progressBar.setVisibility(View.VISIBLE);
        ArrayList<Film> items = new ArrayList<>();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot issue : snapshot.getChildren()) {
                        items.add(issue.getValue(Film.class));
                    }
                    if (!items.isEmpty()) {
                        binding.movieListView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
                        binding.movieListView.setAdapter(new Canal_Adapter(items));
                    }
                    binding.progressBar.setVisibility(View.GONE);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                binding.progressBar.setVisibility(View.GONE);
            }
        });
    }
}