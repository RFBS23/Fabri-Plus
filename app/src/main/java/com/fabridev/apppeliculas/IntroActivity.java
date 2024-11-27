package com.fabridev.apppeliculas;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.fabridev.apppeliculas.Activities.Dashboard;
import com.fabridev.apppeliculas.databinding.ActivityIntroBinding;

public class IntroActivity extends AppCompatActivity {
    ActivityIntroBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Verificar si es la primera vez que se abre la aplicación
        SharedPreferences preferences = getSharedPreferences("AppPreferences", MODE_PRIVATE);
        boolean isFirstTime = preferences.getBoolean("isFirstTime", true);

        if (!isFirstTime) {
            // Si no es la primera vez, redirigir directamente al Dashboard
            startActivity(new Intent(IntroActivity.this, Dashboard.class));
            finish(); // Finalizar la actividad actual
            return;
        }

        // Si es la primera vez, mostrar la pantalla de introducción
        binding = ActivityIntroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Configurar el botón para ir al Dashboard
        binding.btnInicio.setOnClickListener(v -> {
            // Guardar que ya no es la primera vez
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("isFirstTime", false);
            editor.apply();

            // Ir al Dashboard
            startActivity(new Intent(IntroActivity.this, Dashboard.class));
            finish();
        });

        Window w=getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }
}