package com.example.tmdbscrapper;

import android.annotation.SuppressLint;
import android.os.AsyncTask;

import com.bumptech.glide.Glide;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class Scrape extends AsyncTask<String, Void, MovieData> {

    @SuppressLint("StaticFieldLeak")
    private final MainActivity mainActivity;

    public Scrape(MainActivity activity) {
        mainActivity = activity;
    }

    protected MovieData doInBackground(String... urls) {
        try {
            String url = urls[0];
            Document doc = Jsoup.connect(url).get();

            Elements Title = doc.select("div.title > *:first-child > a");
            Elements ReleaseYear = doc.select("div.title > *:first-child > span.release_date");
            Elements Release = doc.select("div.title > div.facts > span.release");
            Elements Certification = doc.select("div.title > div.facts > span.certification");
            Elements Poster = doc.select("div.image_content > img.poster");
            Elements Tagline = doc.select("div.header_info > *.tagline");
            Elements Overview = doc.select("div.header_info > div.overview > p");
//                Elements Cast = doc.select("div#cast_scroller > ol.people > *.card > p > a");
            Elements Runtime = doc.select("div.title > div.facts > span.runtime");
            Elements Genre = doc.select("div.title > div.facts > span.genres > a");
//            Elements Director = doc.select("ol.people > li.profile > p.character");


            String movieTitleString = null;
            for (Element data : Title) {
                movieTitleString = data.text();
            }
            String releaseYearString = null;
            for (Element data : ReleaseYear) {
                releaseYearString = data.text();
            }

            String taglineString = null;
            for (Element data : Tagline) {
                taglineString = data.text();
            }

            String releaseString = null;
            for (Element data : Release) {
                releaseString = data.text();
            }

            String certificationString = null;
            for (Element data : Certification) {
                certificationString = data.text();
            }

            String runtimeString = null;
            for (Element data : Runtime) {
                runtimeString = data.text();
            }

            String overviewString = null;
            for (Element data : Overview) {
                overviewString = data.text();
            }

            String posterPathString = null;
            for (Element data : Poster) {
                posterPathString = data.attr("src");
            }

            List<String> genreList = new ArrayList<>();
            for (Element data : Genre) {
                genreList.add(data.text());
            }
            String genreListString = genreList.toString();
            String genreString = genreListString.substring(1,genreListString.length()-1);

            System.out.println(genreString);

            MovieData movieData = new MovieData();
            movieData.setTitle(movieTitleString + " " + releaseYearString);
            movieData.setTagline(taglineString);
            movieData.setRelease(releaseString);
            movieData.setCertification(certificationString);
            movieData.setRuntime(runtimeString);
            movieData.setOverview(overviewString);
            movieData.setPosterPath(posterPathString);
            movieData.setGenre(genreString);

            return movieData;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void onPostExecute(MovieData movieData) {
        if (movieData != null) {
            mainActivity.movieTitleView.setText(movieData.getTitle());
            mainActivity.movieTagLineView.setText(movieData.getTagline());
            mainActivity.movieReleaseView.setText(movieData.getRelease());
            mainActivity.movieCertificationView.setText(movieData.getCertification());
            mainActivity.movieRuntimeView.setText(movieData.getRuntime());
            mainActivity.movieOverviewView.setText(movieData.getOverview());
            mainActivity.movieGenreView.setText(movieData.getGenre());

            mainActivity.moviePosterLink = "http://image.tmdb.org/t/p/w500/" + extractPosterPath(movieData.getPosterPath());
//            System.out.println(movieData.getPosterPath());
            Glide.with(mainActivity).load(mainActivity.moviePosterLink).placeholder(R.drawable.ic_launcher_background).into(mainActivity.moviePosterBackgroundView);

        }
    }

    String extractPosterPath(String src) {
        if (src != null) {
            int lastIndex = src.lastIndexOf('/');
            if (lastIndex >= 0 && lastIndex < src.length() - 1) {
                return src.substring(lastIndex + 1);
            }
        }
        return "";
    }

}

