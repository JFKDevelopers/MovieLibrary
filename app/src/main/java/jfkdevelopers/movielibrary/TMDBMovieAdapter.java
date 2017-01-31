package jfkdevelopers.movielibrary;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

public class TMDBMovieAdapter extends RecyclerView.Adapter<TMDBMovieAdapter.ViewHolder>{
    public final static String SER_KEY = "com.jfkdevelopers.MovieLibrary.ser";
    private List<TMDBMovie> movies;
    private Context context;

    public TMDBMovieAdapter(Context context, List<TMDBMovie> movies) {
        this.context=context;
        this.movies = movies;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{ // implements View.OnClickListener
        public ImageView movieImage;
        public TextView movieTitle;
        public TextView movieYear;
        public TextView movieRating;
        public TextView movieGenre;
        public ImageButton addBtn;
        public int id;
        public ViewHolder(View v){
            super(v);
            movieImage = (ImageView) v.findViewById(R.id.cover);
            movieTitle = (TextView) v.findViewById(R.id.title);
            movieYear = (TextView) v.findViewById(R.id.year);
            movieRating = (TextView) v.findViewById(R.id.rating);
            movieGenre = (TextView) v.findViewById(R.id.genre);
            addBtn = (ImageButton) v.findViewById(R.id.addButton);
            addBtn.setVisibility(View.VISIBLE);
            addBtn.setOnClickListener(this);
        }
        @Override
        public void onClick(View view){
            Toast.makeText(context,movieTitle.getText()+" added",Toast.LENGTH_LONG).show();
            addBtn.setVisibility(View.INVISIBLE);
            if(context instanceof Search){
                ((Search)context).sendToMain(id);
            }
        }
    }

    @Override
    public TMDBMovieAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        TMDBMovieAdapter.ViewHolder vh = new TMDBMovieAdapter.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        TMDBMovie m = movies.get(position);
        try {
            holder.movieTitle.setText(m.getTitle());
            holder.movieYear.setText(m.getRelease_date().substring(0, 4));
            holder.movieRating.setText("");
            holder.id=m.getId();
            if (m.getGenre_ids().size() > 0) {
                holder.movieGenre.setText(String.format("%s", m.getGenre_ids().get(0).toString())); //need to convert to genre string
            }
            Picasso.with(holder.movieImage.getContext())
                    .load("https://image.tmdb.org/t/p/w500" + m.getPoster_path())
                    .placeholder(R.mipmap.ic_theaters_black_24dp)
                    .error(R.mipmap.ic_theaters_black_24dp)
                    .into(holder.movieImage);
            holder.itemView.setTag(m);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public int getItemCount(){
        return movies.size();
    }
}
