package jfkdevelopers.movielibrary;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
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

        ImageView iv_backdrop = (ImageView) findViewById(R.id.backdrop);
        ImageView iv_Poster = (ImageView) findViewById(R.id.movie_poster);
        TextView tv_tagline = (TextView) findViewById(R.id.movie_tagline);
        TextView tv_Title = (TextView) findViewById(R.id.movie_title);
        TextView tv_Overview = (TextView) findViewById(R.id.movie_overview);
        TextView tv_Genres = (TextView) findViewById(R.id.movie_genres);
        TextView tv_Runtime = (TextView) findViewById(R.id.movie_runtime);
        TextView tv_Release = (TextView) findViewById(R.id.movie_releaseDate);
        Picasso.with(this)
                .load("https://image.tmdb.org/t/p/w500" +movie.getBackdropPath())
                .into(iv_backdrop);
        Picasso.with(this)
                .load("https://image.tmdb.org/t/p/w500" +movie.getPosterPath())
                .placeholder(R.mipmap.ic_theaters_black_24dp)
                .error(R.mipmap.ic_theaters_black_24dp)
                .into(iv_Poster);

        tv_tagline.setText(movie.getTagline());
        tv_Title.setText(movie.getTitle()+" ("+movie.getReleaseDate().substring(0,4)+")");
        tv_Title.setTypeface(null, Typeface.BOLD);
        tv_Overview.setText(movie.getOverview());
        tv_Genres.setText(movie.genreString);
        String runtime = movie.getRuntime()+" min";
        tv_Runtime.setText(runtime);

        String releaseDate = movie.getReleaseDate().substring(5,7) + "/" + movie.getReleaseDate().substring(8) + "/" + movie.getReleaseDate().substring(0,4);
        tv_Release.setText(releaseDate);
    }

}
