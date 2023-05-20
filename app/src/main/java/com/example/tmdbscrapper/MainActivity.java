package com.example.tmdbscrapper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    EditText searchBarView;
    Button searchBtnView;
    TextView movieTitleView, movieTagLineView, movieReleaseView, movieCertificationView, movieRuntimeView, movieOverviewView, movieGenreView, movieCastView;
    ImageView moviePosterBackgroundView;
//    String url;
    String searchQuery, moviePosterLink;



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
        movieCastView = findViewById(R.id.cast);

        searchBtnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchQuery = searchBarView.getText().toString().trim();
                if (!searchQuery.isEmpty()) {
                    TMDBSearchScrape queryscrape = new TMDBSearchScrape(MainActivity.this);
                    queryscrape.execute(searchQuery);

//                    Scrape urlscrape = new Scrape(MainActivity.this);
//                    urlscrape.execute(searchQuery);
                }
            }
        });

    }

}