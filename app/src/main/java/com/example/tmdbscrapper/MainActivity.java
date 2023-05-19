package com.example.tmdbscrapper;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    EditText searchBarView;
    Button searchBtnView;
    TextView movieTitleView, movieTagLineView, movieReleaseView, movieCertificationView, movieRuntimeView, movieOverviewView, movieGenreView;
    ImageView moviePosterBackgroundView;

    String url;
    String moviePosterLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchBarView = findViewById(R.id.searchBar);
        searchBtnView = findViewById(R.id.searchBtn);
        movieTitleView = findViewById(R.id.movieTitle);
        movieTagLineView = findViewById(R.id.tagLine);
        movieReleaseView = findViewById(R.id.release);
        movieCertificationView = findViewById(R.id.certification);
        movieRuntimeView = findViewById(R.id.runtime);
        movieOverviewView = findViewById(R.id.overview);
        moviePosterBackgroundView = findViewById(R.id.backgroundPoster);
        movieGenreView = findViewById(R.id.genre);


        searchBtnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                url = searchBarView.getText().toString().trim();
                if (!url.isEmpty()) {
                    Scrape scrape = new Scrape(MainActivity.this);
                    scrape.execute(url);
                }
            }
        });
    }
}