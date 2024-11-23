package com.fabridev.apppeliculas.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.fabridev.apppeliculas.Adapters.ViewPagerAdapter;
import com.fabridev.apppeliculas.R;

public class NavigationActivity extends AppCompatActivity {
    private static final String PREFS_NAME = "AppPrefs";
    private static final String PREF_HAS_SEEN_ONBOARDING = "hasSeenOnboarding";

    ViewPager slideViewPager;
    LinearLayout dotIndicator;
    Button backButton, nextButton, skipButton;
    TextView[] dots;
    ViewPagerAdapter viewPagerAdapter;
    ViewPager.OnPageChangeListener viewPagerListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }
        @Override
        public void onPageSelected(int position) {
            setDotIndicator(position);
            if (position > 0) {
                backButton.setVisibility(View.VISIBLE);
            } else {
                backButton.setVisibility(View.INVISIBLE);
            }
            if (position == 2){
                nextButton.setText("Terminar");
            } else {
                nextButton.setText("Siguiente");
            }
        }
        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        backButton = findViewById(R.id.backButton);
        nextButton = findViewById(R.id.nextButton);
        skipButton = findViewById(R.id.skipButton);
        slideViewPager = findViewById(R.id.slideViewPager);
        dotIndicator = findViewById(R.id.dotIndicator);
        viewPagerAdapter = new ViewPagerAdapter(this);
        slideViewPager.setAdapter(viewPagerAdapter);
        setDotIndicator(0);
        slideViewPager.addOnPageChangeListener(viewPagerListener);

        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean hasSeenOnboarding = preferences.getBoolean(PREF_HAS_SEEN_ONBOARDING, false);
        if (hasSeenOnboarding) {
            Intent i = new Intent(NavigationActivity.this, Dashboard.class);
            startActivity(i);
            finish();
        }

        backButton.setOnClickListener(v -> {
            if (getItem(0) > 0) {
                slideViewPager.setCurrentItem(getItem(-1), true);
            }
        });

        nextButton.setOnClickListener(v -> {
            if (getItem(0) < 2) {
                slideViewPager.setCurrentItem(getItem(1), true);
            } else {
                markOnboardingAsSeen();
            }
        });

        skipButton.setOnClickListener(v -> {
            markOnboardingAsSeen();
        });
    }

    private void markOnboardingAsSeen() {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(PREF_HAS_SEEN_ONBOARDING, true);
        editor.apply();

        Intent i = new Intent(NavigationActivity.this, Dashboard.class);
        startActivity(i);
        finish();
    }

    public void setDotIndicator(int position) {
        dots = new TextView[3];
        dotIndicator.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226", Html.FROM_HTML_MODE_LEGACY));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.grey, getApplicationContext().getTheme()));
            dotIndicator.addView(dots[i]);
        }
        dots[position].setTextColor(getResources().getColor(R.color.lavender, getApplicationContext().getTheme()));
    }

    private int getItem(int i) {
        return slideViewPager.getCurrentItem() + i;
    }
}