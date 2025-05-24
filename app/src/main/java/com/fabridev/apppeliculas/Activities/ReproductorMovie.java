package com.fabridev.apppeliculas.Activities;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.OptIn;
import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.MediaItem;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.datasource.DefaultHttpDataSource;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.exoplayer.hls.HlsMediaSource;
import androidx.media3.ui.PlayerView;

import com.fabridev.apppeliculas.R;

import java.util.HashMap;
import java.util.Map;

public class ReproductorMovie extends AppCompatActivity {
  private ExoPlayer player;
  private PlayerView playerView;

  @OptIn(markerClass = UnstableApi.class)
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_reproductor_movie);
    playerView = findViewById(R.id.player_view);
    playerView.setKeepScreenOn(true);
    hideSystemUI();

    String videoUrl = getIntent().getStringExtra("videoUrl");

    if (videoUrl != null) {
      player = new ExoPlayer.Builder(this).build();
      playerView.setPlayer(player);

      Map<String, String> headers = new HashMap<>();
      headers.put("Origin", "https://www.google.com");
      headers.put("Accept", "*/*");

      DefaultHttpDataSource.Factory httpDataSourceFactory =
              new DefaultHttpDataSource.Factory()
                      .setUserAgent(headers.get("User-Agent"))
                      .setDefaultRequestProperties(headers)
                      .setAllowCrossProtocolRedirects(true);

      HlsMediaSource hlsMediaSource = new HlsMediaSource.Factory(httpDataSourceFactory)
              .createMediaSource(MediaItem.fromUri(Uri.parse(videoUrl)));

      player.setMediaSource(hlsMediaSource);
      player.prepare();
      player.setPlayWhenReady(true);
    }
  }

  private void hideSystemUI() {
    playerView.setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
    );
  }

  @Override
  public void onWindowFocusChanged(boolean hasFocus) {
    super.onWindowFocusChanged(hasFocus);
    if (hasFocus) {
      hideSystemUI();
    }
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    if (player != null) {
      player.release();
      player = null;
    }
  }
}