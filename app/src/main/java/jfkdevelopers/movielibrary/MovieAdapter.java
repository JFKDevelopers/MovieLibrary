package jfkdevelopers.movielibrary;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{
    private static final int PENDING_REMOVAL_TIMEOUT = 3000; //3 sec
    private List<Movie> movies;
    private List<Movie> moviesPendingRemoval;
    private Context context;
    private int lastPosition = -1;

    private boolean undoOn;
    private Handler handler = new Handler();
    private HashMap<Movie, Runnable> pendingRunnables = new HashMap<>();


    public MovieAdapter(){
        this.moviesPendingRemoval = new ArrayList<>();
    }

    public MovieAdapter(Context context, List<Movie> movies) {
        this.movies = movies;
        this.context = context;
        this.moviesPendingRemoval = new ArrayList<>();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView movieImage;
        public TextView movieTitle;
        public TextView movieYear;
        public TextView movieRating;
        public TextView movieGenre;
        //public TextView moviePlot;
        public Button undoButton;
        public ViewHolder(View v){
            super(v);
            movieImage = (ImageView) v.findViewById(R.id.cover);
            movieTitle = (TextView) v.findViewById(R.id.title);
            movieYear = (TextView) v.findViewById(R.id.year);
            movieRating = (TextView) v.findViewById(R.id.rating);
            movieGenre = (TextView) v.findViewById(R.id.genre);
            //moviePlot = (TextView) v.findViewById(R.id.plot);
            undoButton = (Button) v.findViewById(R.id.undo_button);
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
                        .error(R.mipmap.ic_theaters_black_24dp)
                        .placeholder(R.mipmap.ic_theaters_black_24dp)
                        .into(holder.movieImage);
                holder.movieTitle.setText(movie.getTitle());
                holder.movieRating.setText(movie.getRated());
                holder.movieYear.setText(movie.getYear());
                //moviePlot.setText(movie.getPlot());
                holder.movieGenre.setText(movie.getGenre());
                holder.undoButton.setVisibility(View.GONE);
                holder.undoButton.setOnClickListener(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
            setAnimation(holder.itemView, position);
        }
    }

    @Override
    public int getItemCount(){
        return movies.size();
    }

    private void setAnimation(View viewToAnimate, int position){
        if(position>lastPosition){
            Animation animation = AnimationUtils.loadAnimation(context,android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    public void setUndoOn(boolean undoOn){
        this.undoOn = undoOn;
    }

    public boolean isUndoOn(){
        return undoOn;
    }

    public void pendingRemoval(int position){
        final Movie movie = movies.get(position);
        if(!moviesPendingRemoval.contains(movie)) {
            moviesPendingRemoval.add(movie);
            notifyItemChanged(position);

            Runnable pendingRemovalRunnable = new Runnable(){
                @Override
                public void run(){
                    remove(movies.indexOf(movie));
                }
            };
            handler.postDelayed(pendingRemovalRunnable, PENDING_REMOVAL_TIMEOUT);
            pendingRunnables.put(movie, pendingRemovalRunnable);
        }
    }

    public void remove(int position){
        Movie movie = movies.get(position);
        if(moviesPendingRemoval.contains(movie)){
            moviesPendingRemoval.remove(movie);
        }
        if(movies.contains(movie)){
            movies.remove(position);
            notifyItemRemoved(position);
        }
    }

    public boolean isPendingremoval(int position){
        Movie movie = movies.get(position);
        return moviesPendingRemoval.contains(movie);
    }
   /* public MovieAdapter(Context context, List<Movie> movies) {
        super(context,0,movies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Movie movie = getItem(position);

        if(convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }

        ImageView movieImage = (ImageView) convertView.findViewById(R.id.cover);
        TextView movieTitle = (TextView) convertView.findViewById(R.id.title);
        TextView movieYear = (TextView) convertView.findViewById(R.id.year);
        TextView movieRating = (TextView) convertView.findViewById(R.id.rating);
        TextView movieGenre = (TextView) convertView.findViewById(R.id.genre);
        //TextView moviePlot = (TextView) convertView.findViewById(R.id.plot);
        try {
            Picasso.with(this.getContext())
                    .load(movie.getImgUrl())
                    .error(R.mipmap.ic_theaters_black_24dp)
                    .placeholder(R.mipmap.ic_theaters_black_24dp)
                    .into(movieImage);
            movieTitle.setText(movie.getTitle());
            movieYear.setText(movie.getYear());
            movieRating.setText(movie.getRating());
            movieGenre.setText(movie.getGenre());
            //moviePlot.setText(movie.getPlot());
        }catch(Exception e){
            e.printStackTrace();
        }
        return convertView;
    }*/
}