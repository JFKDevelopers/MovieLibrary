package jfkdevelopers.movielibrary;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.google.gson.internal.bind.TypeAdapters.URL;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{
    public final static String EXTRA_MESSAGE = "com.jfkdevelopers.movielibrary.MESSAGE";
    public final static String SER_KEY = "com.jfkdevelopers.MovieLibrary.ser";
    private static final int PENDING_REMOVAL_TIMEOUT = 3000; //3 sec
    private List<Movie> movies;
    private List<Movie> moviesPendingRemoval;
    private Context context;
    private int lastPosition = -1;

    private boolean undoOn;
    private Handler handler = new Handler();
    private HashMap<Movie, Runnable> pendingRunnables = new HashMap<>();

    private HashMap<String, Movie> movieMap = new HashMap<>();
    public MovieAdapter(){
        this.moviesPendingRemoval = new ArrayList<>();
    }

    public MovieAdapter(Context context, List<Movie> movies) {
        this.movies = movies;
        this.context = context;
        this.moviesPendingRemoval = new ArrayList<>();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView movieImage;
        public TextView movieTitle;
        public TextView movieYear;
        public TextView movieRating;
        public TextView movieGenre;
        //public TextView moviePlot;
        public Button undoButton;
        public String id = "";
        public ViewHolder(View v){
            super(v);
            v.setOnClickListener(this);
            movieImage = (ImageView) v.findViewById(R.id.cover);
            movieTitle = (TextView) v.findViewById(R.id.title);
            movieYear = (TextView) v.findViewById(R.id.year);
            movieRating = (TextView) v.findViewById(R.id.rating);
            movieGenre = (TextView) v.findViewById(R.id.genre);
            undoButton = (Button) v.findViewById(R.id.undo_button);
        }
        @Override
        public void onClick(View view){
            Intent intent = new Intent(context,DetailActivity.class);
            Bundle mBundle = new Bundle();
            mBundle.putSerializable(SER_KEY,movieMap.get(id));
            //String message = movieTitle.getText().toString();
            //Toast.makeText(view.getContext(),message,Toast.LENGTH_LONG).show();
            intent.putExtras(mBundle);
            context.startActivity(intent);
        }
    }

    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext())
                               .inflate(R.layout.list_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        final Movie movie = movies.get(position);
        if(moviesPendingRemoval.contains(movie)){
            //show "undo" state
            holder.itemView.setBackgroundColor(Color.RED);
            holder.movieImage.setVisibility(View.GONE);
            holder.movieTitle.setVisibility(View.GONE);
            holder.movieRating.setVisibility(View.GONE);
            holder.movieYear.setVisibility(View.GONE);
            holder.movieGenre.setVisibility(View.GONE);
            holder.undoButton.setVisibility(View.VISIBLE);
            holder.undoButton.setOnClickListener(new View.OnClickListener(){
               @Override
                public void onClick(View v){
                   Runnable pendingRemovalRunnable = pendingRunnables.get(movie);
                   pendingRunnables.remove(movie);
                   if(pendingRemovalRunnable!=null){
                       handler.removeCallbacks(pendingRemovalRunnable);
                   }
                   moviesPendingRemoval.remove(movie);
                   notifyItemChanged(movies.indexOf(movie));
               }
            });
        }
        else {
            try {
                Picasso.with(context)
                        .load(movie.getPoster())
                        .placeholder(R.mipmap.ic_theaters_black_24dp)
                        .error(R.mipmap.ic_theaters_black_24dp)
                        .into(holder.movieImage);
                holder.movieTitle.setText(movie.getTitle());
                holder.movieRating.setText(movie.getRated());
                holder.movieYear.setText(movie.getYear());
                //moviePlot.setText(movie.getPlot());
                holder.movieGenre.setText(movie.getGenre());
                holder.undoButton.setVisibility(View.GONE);
                holder.undoButton.setOnClickListener(null);
                holder.id = movie.getImdbID();
                movieMap.put(movie.getImdbID(),movie);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //setAnimation(holder.itemView, position);
        }
    }

    @Override
    public int getItemCount(){
        return movies.size();
    }

    private void setAnimation(View viewToAnimate, int position){
/*        if(position>lastPosition){
            Animation animation = AnimationUtils.loadAnimation(context,android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }*/
    }

    public void setUndoOn(boolean undoOn){
        this.undoOn = undoOn;
    }

    public boolean isUndoOn(){
        return undoOn;
    }

    public void pendingRemoval(int position, final DatabaseHandler db, final RecyclerView rv){
        final Movie movie = movies.get(position);
        if(!moviesPendingRemoval.contains(movie)) {
            moviesPendingRemoval.add(movie);
            notifyItemChanged(position);

            Runnable pendingRemovalRunnable = new Runnable(){
                @Override
                public void run(){
                    remove(movies.indexOf(movie),db,rv);
                }
            };
            handler.postDelayed(pendingRemovalRunnable, PENDING_REMOVAL_TIMEOUT);
            pendingRunnables.put(movie, pendingRemovalRunnable);
        }
    }

    public void remove(int position, DatabaseHandler db, RecyclerView rv){
        Movie movie = movies.get(position);
        if(moviesPendingRemoval.contains(movie)){
            moviesPendingRemoval.remove(movie);
        }
        if(movies.contains(movie)){
            Snackbar snackbar = Snackbar
                    .make(rv, movies.get(position).getTitle() + " deleted", Snackbar.LENGTH_LONG);
            snackbar.show();
            db.deleteMovie(movies.get(position));
            //MainActivity.db.deleteMovie(movies.get(position));
            movies.remove(position);
            notifyItemRemoved(position);

        }
    }

    public boolean isPendingRemoval(int position){
        Movie movie = movies.get(position);
        return moviesPendingRemoval.contains(movie);
    }

  /*  public void setClickListener(ItemClickListener itemClickListener){
        this.clickListener = itemClickListener;
    }*/

}