package jfkdevelopers.movielibrary;

import android.content.Intent;
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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Movie movie = (Movie)getIntent().getSerializableExtra(MovieAdapter.SER_KEY);

        TextView tv_Title = (TextView) findViewById(R.id.movie_title);
        ImageView iv_Poster = (ImageView) findViewById(R.id.movie_poster);
        //TextView tv_Year = (TextView) findViewById(R.id.movie_year);
        TextView tv_Rating = (TextView) findViewById(R.id.movie_rating);
        TextView tv_Runtime = (TextView) findViewById(R.id.movie_runtime);
        TextView tv_Genre = (TextView) findViewById(R.id.movie_genres);
        TextView tv_Director = (TextView) findViewById(R.id.movie_director);
        TextView tv_Writer = (TextView) findViewById(R.id.movie_writer);
        TextView tv_Actors = (TextView) findViewById(R.id.movie_actors);
        TextView tv_Plot = (TextView) findViewById(R.id.movie_plot);
        //TextView tv_Language = (TextView) findViewById(R.id.movie_language);
        TextView tv_Country = (TextView) findViewById(R.id.movie_country);
        //TextView tv_Awards = (TextView) findViewById(R.id.movie_awards);
        //TextView tv_imdbRating = (TextView) findViewById(R.id.movie_imdbRating);

        tv_Title.setText(movie.getTitle()+" ("+movie.getReleaseDate()+")");
        Picasso.with(this)
                .load("https://image.tmdb.org/t/p/w500" +movie.getPosterPath())
                .placeholder(R.mipmap.ic_theaters_black_24dp)
                .error(R.mipmap.ic_theaters_black_24dp)
                .into(iv_Poster);

        //tv_Year.setText(movie.getYear());
        tv_Rating.setText("n/a");
        tv_Runtime.setText(String.format(Locale.US,"%d min",movie.getRuntime()));
        tv_Genre.setText(movie.getGenres());
        //tv_Director.setText("Director: "+movie.get);
        //tv_Writer.setText("Writer: "+movie.getWriter());
        //tv_Actors.setText("Stars: "+movie.get);
        tv_Plot.setText(movie.getOverview());
//        tv_Language.setText(movie.getLanguage());
        tv_Country.setText(movie.getProductionCountries());
        //tv_Awards.setText(movie.getAwards());
        //tv_imdbRating.setText(movie.getImdbRating());
    }

}
