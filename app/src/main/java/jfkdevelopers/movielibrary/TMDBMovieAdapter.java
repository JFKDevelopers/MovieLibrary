package jfkdevelopers.movielibrary;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class TMDBMovieAdapter extends RecyclerView.Adapter<TMDBMovieAdapter.ViewHolder>{
    private List<TMDBMovie> movies;

    public TMDBMovieAdapter(List<TMDBMovie> movies) {
        this.movies = movies;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{ // implements View.OnClickListener
        public ImageView movieImage;
        public TextView movieTitle;
        public TextView movieYear;
        public TextView movieRating;
        public TextView movieGenre;
        public ViewHolder(View v){
            super(v);
            movieImage = (ImageView) v.findViewById(R.id.cover);
            movieTitle = (TextView) v.findViewById(R.id.title);
            movieYear = (TextView) v.findViewById(R.id.year);
            movieRating = (TextView) v.findViewById(R.id.rating);
            movieGenre = (TextView) v.findViewById(R.id.genre);
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
        holder.movieTitle.setText(m.getTitle());
        holder.movieYear.setText(m.getRelease_date().substring(0,4));
        holder.movieRating.setText("");
        if(m.getGenre_ids().size()>0) {
            holder.movieGenre.setText(String.format("%s",m.getGenre_ids().get(0).toString()));
        }
        Picasso.with(holder.movieImage.getContext())
                .load("https://image.tmdb.org/t/p/w500"+m.getPoster_path())
                .placeholder(R.mipmap.ic_theaters_black_24dp)
                .error(R.mipmap.ic_theaters_black_24dp)
                .into(holder.movieImage);
        holder.itemView.setTag(m);
    }
    @Override
    public int getItemCount(){
        return movies.size();
    }
}
