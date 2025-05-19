package com.fabridev.apppeliculas.Activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.fabridev.apppeliculas.Adapters.CastListAdapter;
import com.fabridev.apppeliculas.Adapters.CategoryFilmAdapter;
import com.fabridev.apppeliculas.Domains.Film;
import com.fabridev.apppeliculas.R;
import com.fabridev.apppeliculas.databinding.ActivityDetailsBinding;

import eightbitlab.com.blurview.RenderScriptBlur;

public class DetailsActivity extends AppCompatActivity {

    private ActivityDetailsBinding binding;

    private boolean isFavorite = false;  // Variable para saber si la película es favorita
    private Film currentFilm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setVariable();
        Window w=getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    private void setVariable() {
        // Obtener el objeto Film
        currentFilm = (Film) getIntent().getSerializableExtra("object");

        // Cargar los detalles de la película (poster, título, descripción, etc.)
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transform(new CenterCrop(), new GranularRoundedCorners(0, 0, 50, 50));
        Glide.with(this)
                .load(currentFilm.getPoster())
                .apply(requestOptions)
                .into(binding.filmPic);
        binding.titleTxt.setText(currentFilm.getTitle());
        binding.movieTimestxt.setText(currentFilm.getYear() + " - " + currentFilm.getTime());
        binding.movieSummery.setText(currentFilm.getDescription());

        // Reproducir el tráiler
        binding.btnReproducir.setOnClickListener(v -> {
            String videoUrl = currentFilm.getTrailer();
            if (videoUrl != null && !videoUrl.isEmpty()) {
                Intent intent = new Intent(DetailsActivity.this, VideoPlayerActivity.class);
                intent.putExtra("videoUrl", videoUrl);
                startActivity(intent);
            } else {
                Toast.makeText(DetailsActivity.this, "URL del video no válida", Toast.LENGTH_SHORT).show();
            }
        });

        // Volver a la pantalla anterior
        binding.backImg.setOnClickListener(v -> finish());

        // Configuración de las vistas de género y actores
        if (currentFilm.getGenre() != null) {
            binding.genreView.setAdapter(new CategoryFilmAdapter(currentFilm.getGenre()));
            binding.genreView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        }

        if (currentFilm.getCasts() != null) {
            binding.actorsView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            binding.actorsView.setAdapter(new CastListAdapter(currentFilm.getCasts()));
        }

        // Configurar la vista de desenfoque
        float radius = 10f;
        View decorView = getWindow().getDecorView();
        ViewGroup rootView = (ViewGroup) decorView.findViewById(android.R.id.content);
        Drawable windowsBackground = decorView.getBackground();

        binding.blurView.setupWith(rootView, new RenderScriptBlur(this))
                .setFrameClearDrawable(windowsBackground)
                .setBlurRadius(radius);
        binding.blurView.setOutlineProvider(ViewOutlineProvider.BACKGROUND);
        binding.blurView.setClipToOutline(true);

        binding.ivFavorito.setOnClickListener(v -> {
            isFavorite = !isFavorite;
            updateFavoriteIcon();
            // Aquí puedes guardar el estado en SharedPreferences o en una base de datos
        });

        binding.ivCompartir.setOnClickListener(v -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            String shareText = "Mira esta película: " + currentFilm.getTitle() + "\n" + "Descarga la App aqui \uD83D\uDC47" + "\n" + "link";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
            startActivity(Intent.createChooser(shareIntent, "Compartir con"));
        });

        loadFavoriteStatus();
    }

    private void updateFavoriteIcon() {
        if (isFavorite) {
            binding.ivFavorito.setImageResource(R.drawable.favoritos);
        } else {
            binding.ivFavorito.setImageResource(R.drawable.bookmark);
        }
    }

    private void loadFavoriteStatus() {
        isFavorite = false;
        updateFavoriteIcon();
    }
}