package jfkdevelopers.movielibrary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.List;

public class MovieAdapter extends ArrayAdapter<Movie>{
    public MovieAdapter(Context context, List<Movie> movies) {
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
    }
}