package jfkdevelopers.movielibrary;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Locale;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Movie movie = (Movie)getIntent().getSerializableExtra(MovieAdapter.SER_KEY);

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(movie.getTitle());

        ImageView iv_backdrop = (ImageView) findViewById(R.id.backdrop);
        ImageView iv_Poster = (ImageView) findViewById(R.id.movie_poster);

        Picasso.with(this)
                .load("https://image.tmdb.org/t/p/w500" +movie.getPosterPath())
                .placeholder(R.mipmap.ic_theaters_black_24dp)
                .error(R.mipmap.ic_theaters_black_24dp)
                .into(iv_Poster);

        Picasso.with(this)
                .load("https://image.tmdb.org/t/p/w500" +movie.getBackdropPath())
                .placeholder(R.drawable.rectangle)
                .error(iv_Poster.getDrawable())
                .into(iv_backdrop);

        TextView tv_tagline = (TextView) findViewById(R.id.movie_tagline);
        TextView tv_Title = (TextView) findViewById(R.id.movie_title);
        TextView tv_Overview = (TextView) findViewById(R.id.movie_overview);
        TextView tv_Genres = (TextView) findViewById(R.id.movie_genres);
        TextView tv_Runtime = (TextView) findViewById(R.id.movie_runtime);
        TextView tv_Release = (TextView) findViewById(R.id.movie_releaseDate);
        TextView tv_Cast = (TextView) findViewById(R.id.movie_cast);

        tv_tagline.setText(movie.getTagline());

        String year = movie.getReleaseDate().length()>=4? " ("+movie.getReleaseDate().substring(0,4)+")":"";
        String title = movie.getTitle()+year;
        tv_Title.setText(title);
        tv_Title.setTypeface(null, Typeface.BOLD);

        tv_Overview.setText(movie.getOverview());

        String genres = "";
        for(Movie.Genres g: movie.genres){
            genres = genres + g.name + ", ";
        }
        if(genres.length()>2) genres = genres.substring(0,genres.length()-2);
        tv_Genres.setText(genres);

        String runtime = movie.getRuntime()+" min";
        tv_Runtime.setText(runtime);

        String releaseDate = movie.getReleaseDate().substring(5,7) + "/" + movie.getReleaseDate().substring(8) + "/" + movie.getReleaseDate().substring(0,4);
        tv_Release.setText(releaseDate);

        String cast = "";
        int count = 0;
        for(Movie.Cast c: movie.getCast()){
            if(count<10) { //Only show up to the first 10 cast members to avoid very long lists.
                String temp = String.format("%s  -  %s",c.name,c.character);
                cast = cast + temp + "\n";
                count++;
            }
        }
        tv_Cast.setText(cast);
    }
}
