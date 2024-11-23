package com.fabridev.apppeliculas.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fabridev.apppeliculas.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VerMasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VerMasFragment extends Fragment {

    public VerMasFragment() {
        // Required empty public constructor
    }

    public static VerMasFragment newInstance(String param1, String param2) {
        VerMasFragment fragment = new VerMasFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ver_mas, container, false);
    }
}