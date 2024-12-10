package com.fabridev.apppeliculas.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fabridev.apppeliculas.R;

public class InfoFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);
        TextView textView = view.findViewById(R.id.bmcWEb);
        textView.setMovementMethod(LinkMovementMethod.getInstance());

        return view;
    }
}