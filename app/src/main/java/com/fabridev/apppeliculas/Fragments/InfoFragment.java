package com.fabridev.apppeliculas.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fabridev.apppeliculas.R;

public class InfoFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info, container, false);

        // Referencias a los botones
        ImageView telegramBtn = view.findViewById(R.id.imageView6);
        ImageView tiktokBtn = view.findViewById(R.id.imageView7);
        ImageView instagramBtn = view.findViewById(R.id.imageView8);
        ImageView facebookBtn = view.findViewById(R.id.imageView10);
        ImageView youtubeBtn = view.findViewById(R.id.imageView11);

        // URLs de redes sociales
        String telegramUrl = "https://t.me/fabri_plus";
        String tiktokUrl = "https://www.tiktok.com/@fabriziobarrios_19";
        String instagramUrl = "https://www.instagram.com/fabridevperu";
        String facebookUrl = "https://www.facebook.com/share/1AZCnRLyRw/?mibextid=qi2Omg";
        String youtubeUrl = "https://www.youtube.com/@FabriDev";
        // Listeners para abrir las redes sociales
        telegramBtn.setOnClickListener(v -> openUrl(telegramUrl));
        tiktokBtn.setOnClickListener(v -> openUrl(tiktokUrl));
        instagramBtn.setOnClickListener(v -> openUrl(instagramUrl));
        facebookBtn.setOnClickListener(v -> openUrl(facebookUrl));
        youtubeBtn.setOnClickListener(v->openUrl(youtubeUrl));
        return view;
    }

    private void openUrl(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}