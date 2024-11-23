package com.fabridev.apppeliculas.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.fabridev.apppeliculas.R;

public class GetStarted extends AppCompatActivity {
    private static final String PREFS_NAME = "AppPrefs";
    private static final String PREF_HAS_SEEN_ONBOARDING = "hasSeenOnboarding";

    Button startButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean hasSeenOnboarding = preferences.getBoolean(PREF_HAS_SEEN_ONBOARDING, false);

        setContentView(R.layout.activity_get_started);
        if (hasSeenOnboarding) {
            // Si ya se mostró el onboarding, redirigir directamente al Dashboard
            Intent intent = new Intent(GetStarted.this, Dashboard.class);
            startActivity(intent);
            finish(); // Finalizar esta actividad para que no se regrese
        } else {
            // Si no se ha mostrado el onboarding, mostrar el onboarding
            Intent intent = new Intent(GetStarted.this, NavigationActivity.class);
            startActivity(intent);
            finish(); // Finalizar esta actividad para que no se regrese
        }
    }
}