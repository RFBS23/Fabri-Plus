package com.fabridev.apppeliculas.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.fabridev.apppeliculas.Adapters.Favoritos_Adapter;
import com.fabridev.apppeliculas.Domains.Film;
import com.fabridev.apppeliculas.Adapters.Film_ListAdapter;
import com.fabridev.apppeliculas.Temp.FavoritosTemp;
import com.fabridev.apppeliculas.databinding.FragmentFavoritosBinding;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class FavoritosFragment extends Fragment {
    private FragmentFavoritosBinding binding;
    private Film_ListAdapter adapter;
    private List<Film> listaFavoritos = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentFavoritosBinding.inflate(inflater, container, false);

        View view = binding.getRoot();

        listaFavoritos.clear();
        listaFavoritos.addAll(FavoritosTemp.listaFavoritos);

        adapter = new Film_ListAdapter((ArrayList<Film>) listaFavoritos);

        setupRecyclerView();
        actualizarVista();

        return view;
    }

    private void setupRecyclerView() {
        FlexboxLayoutManager flexboxLayoutManager = new FlexboxLayoutManager(requireContext());
        flexboxLayoutManager.setFlexDirection(FlexDirection.ROW);
        flexboxLayoutManager.setFlexWrap(FlexWrap.WRAP);
        binding.movieListView.setLayoutManager(flexboxLayoutManager);
        binding.movieListView.setAdapter(adapter);
    }

    private void actualizarVista() {
        if (listaFavoritos.isEmpty()) {
            binding.movieListView.setVisibility(View.GONE);
            binding.progressBar.setVisibility(View.GONE);
            binding.textViewEmpty.setVisibility(View.VISIBLE);
        } else {
            binding.movieListView.setVisibility(View.VISIBLE);
            binding.progressBar.setVisibility(View.GONE);
            binding.textViewEmpty.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        listaFavoritos.clear();
        listaFavoritos.addAll(FavoritosTemp.listaFavoritos);
        adapter.notifyDataSetChanged();
        actualizarVista();
    }
}
