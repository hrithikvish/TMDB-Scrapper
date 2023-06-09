package com.example.tmdbscrapper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import android.Manifest;


public class MainActivity extends AppCompatActivity {

    EditText searchBarView, urlBarView;
    Button searchBtnView, urlBtnView;
    static TextView movieTitleView;
    TextView movieTagLineView;
    TextView movieReleaseView;
    TextView movieCertificationView;
    TextView movieRuntimeView;
    TextView movieOverviewView;
    TextView movieGenreView;
    TextView movieCastView;
    TextView previousTextView;
    TextView nextTextView;
    TextView shareTextView;
    TextView downloadPosterTextView;
    ImageView moviePosterBackgroundView;
    String url;
    String searchQuery;
    static String moviePosterLink;
    ImageButton nextBtnView, previousBtnView, shareBtnView, downloadPosterBtnView;
    SwitchCompat URLTextToggle;
    LinearLayout searchLayout, shareNextLayout;

    Bitmap bitmap;
    BitmapDrawable bitmapDrawable;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchBarView = findViewById(R.id.searchBar);
        urlBarView = findViewById(R.id.urlBar);
        searchBtnView = findViewById(R.id.searchBtn);
        urlBtnView = findViewById(R.id.urlBtn);
        movieTitleView = findViewById(R.id.movieTitle);
        movieTagLineView = findViewById(R.id.tagLine);
        movieReleaseView = findViewById(R.id.release);
        movieCertificationView = findViewById(R.id.certification);
        movieRuntimeView = findViewById(R.id.runtime);
        movieOverviewView = findViewById(R.id.overview);
        moviePosterBackgroundView = findViewById(R.id.backgroundPoster);
        movieGenreView = findViewById(R.id.genre);
        movieCastView = findViewById(R.id.cast);
        nextBtnView = findViewById(R.id.next);
        previousBtnView = findViewById(R.id.previous);
        previousTextView = findViewById(R.id.previoustext);
        nextTextView = findViewById(R.id.nexttext);
        URLTextToggle = findViewById(R.id.urltexttoggle);
        searchLayout = findViewById(R.id.searchLayout);
        shareNextLayout = findViewById(R.id.sharendnext);
        shareBtnView = findViewById(R.id.share);
        downloadPosterBtnView = findViewById(R.id.download);
        shareTextView = findViewById(R.id.sharetext);
        downloadPosterTextView = findViewById(R.id.downloadtext);

        searchBtnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchQuery = searchBarView.getText().toString().trim();
                if (!searchQuery.isEmpty()) {
                    shareNextLayout.setVisibility(View.VISIBLE);

                    String selector = "div.column_wrapper > div.content_wrapper > div.white_column > section.panel > div.search_results > div.results > *:first-child > div.wrapper > div.details > div.wrapper > div.title > div > a.result";

                    TMDBSearchScrape queryscrape = new TMDBSearchScrape(MainActivity.this, selector);
                    queryscrape.execute(searchQuery);
                }
            }
        });

        urlBtnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                url = urlBarView.getText().toString().trim();
                if (!url.isEmpty()) {
                    Scrape scrape = new Scrape(MainActivity.this);
                    scrape.execute(url);
                }
            }
        });

        URLTextToggle.setOnCheckedChangeListener(((compoundButton, isChecked) -> {
            if (isChecked) {
                searchBarView.setVisibility(View.GONE);
                searchBtnView.setVisibility(View.GONE);
                urlBarView.setVisibility(View.VISIBLE);
                urlBtnView.setVisibility(View.VISIBLE);
            } else {
                urlBarView.setVisibility(View.GONE);
                urlBtnView.setVisibility(View.GONE);
                searchBarView.setVisibility(View.VISIBLE);
                searchBtnView.setVisibility(View.VISIBLE);
            }
        }));

        nextBtnView.setOnClickListener(new View.OnClickListener() {
            private int clickCount = 0;
            @Override
            public void onClick(View view) {
                clickCount++;

                String selector = "div.column_wrapper > div.content_wrapper > div.white_column > section.panel > div.search_results > div.results > *:nth-child(" + (clickCount + 1) + ") > div.wrapper > div.details > div.wrapper > div.title > div > a.result";

                TMDBSearchScrape queryscrape = new TMDBSearchScrape(MainActivity.this, selector);
                queryscrape.execute(searchQuery);
            }
        });
        nextTextView.setOnClickListener(new View.OnClickListener() {
            private int clickCount = 0;
            @Override
            public void onClick(View view) {
                clickCount++;

                String selector = "div.column_wrapper > div.content_wrapper > div.white_column > section.panel > div.search_results > div.results > *:nth-child(" + (clickCount + 1) + ") > div.wrapper > div.details > div.wrapper > div.title > div > a.result";

                TMDBSearchScrape queryscrape = new TMDBSearchScrape(MainActivity.this, selector);
                queryscrape.execute(searchQuery);
            }
        });

        previousBtnView.setOnClickListener(new View.OnClickListener() {
            private int clickCount = 1;
            @Override
            public void onClick(View view) {
                clickCount--;

                String selector = "div.column_wrapper > div.content_wrapper > div.white_column > section.panel > div.search_results > div.results > *:nth-child(" + (clickCount + 1) + ") > div.wrapper > div.details > div.wrapper > div.title > div > a.result";

                TMDBSearchScrape queryscrape = new TMDBSearchScrape(MainActivity.this, selector);
                queryscrape.execute(searchQuery);
            }
        });
        previousTextView.setOnClickListener(new View.OnClickListener() {
            private int clickCount = 1;
            @Override
            public void onClick(View view) {
                clickCount--;

                String selector = "div.column_wrapper > div.content_wrapper > div.white_column > section.panel > div.search_results > div.results > *:nth-child(" + (clickCount + 1) + ") > div.wrapper > div.details > div.wrapper > div.title > div > a.result";

                TMDBSearchScrape queryscrape = new TMDBSearchScrape(MainActivity.this, selector);
                queryscrape.execute(searchQuery);
            }
        });



        // Download and Save Poster Section

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

        downloadPosterBtnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DownloadPoster downloadPoster = new DownloadPoster(MainActivity.this);
                downloadPoster.execute(moviePosterLink);
            }
        });

        downloadPosterTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DownloadPoster downloadPoster = new DownloadPoster(MainActivity.this);
                downloadPoster.execute(moviePosterLink);
            }
        });

        shareBtnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Feature Coming in Future Updates", Toast.LENGTH_SHORT).show();
            }
        });

        shareTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Feature Coming in Future Updates", Toast.LENGTH_SHORT).show();

            }
        });

    }

}