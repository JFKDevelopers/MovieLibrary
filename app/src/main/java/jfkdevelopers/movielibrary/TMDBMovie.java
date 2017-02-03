package jfkdevelopers.movielibrary;

import android.util.Log;
import android.util.SparseArray;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TMDBMovie {
    @SerializedName("poster_path")
    public String poster_path;
    @SerializedName("adult")
    public boolean adult;
    @SerializedName("overview")
    public String overview;
    @SerializedName("release_date")
    public String release_date;
    @SerializedName("genre_ids")
    public List<Integer> genre_ids;
    @SerializedName("id")
    public int id;
    @SerializedName("original_title")
    public String original_title;
    @SerializedName("original_language")
    public String original_language;
    @SerializedName("title")
    public String title;
    @SerializedName("backdrop_path")
    public String backdrop_path;
    @SerializedName("popularity")
    public double popularity;
    @SerializedName("vote_count")
    public int vote_count;
    @SerializedName("video")
    public boolean video;
    @SerializedName("vote_average")
    public double vote_average;

    public TMDBMovie(String poster_path, boolean adult, String overview, String release_date, List<Integer> genre_ids,
                     int id, String original_title, String original_language, String title, String backdrop_path,
                     double popularity, int vote_count, boolean video, double vote_average) {
        this.poster_path = poster_path;
        this.adult = adult;
        this.overview = overview;
        this.release_date = release_date;
        this.genre_ids = genre_ids;
        this.id = id;
        this.original_title = original_title;
        this.original_language = original_language;
        this.title = title;
        this.backdrop_path = backdrop_path;
        this.popularity = popularity;
        this.vote_count = vote_count;
        this.video = video;
        this.vote_average = vote_average;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public List<Integer> getGenre_ids() {
        return genre_ids;
    }

    public String getGenres(){
        List<Integer> genreIds = getGenre_ids();
        if(genreIds!=null&&genreIds.size()>0)
        {
            SparseArray<String> genreMap = new SparseArray<>();
            genreMap.put(28, "Action");
            genreMap.put(12, "Adventure");
            genreMap.put(16, "Animation");
            genreMap.put(35, "Comedy");
            genreMap.put(80, "Crime");
            genreMap.put(99, "Documentary");
            genreMap.put(18, "Drama");
            genreMap.put(10751, "Family");
            genreMap.put(14, "Fantasy");
            genreMap.put(36, "History");
            genreMap.put(27, "Horror");
            genreMap.put(10402, "Music");
            genreMap.put(9648, "Mystery");
            genreMap.put(10749, "Romance");
            genreMap.put(878, "Science Fiction");
            genreMap.put(10770, "TV Movie");
            genreMap.put(53, "Thriller");
            genreMap.put(10752, "War");
            genreMap.put(37, "Western");
            String out = "";
            for (int i:genreIds) {
                out = out + genreMap.get(i) + ", ";
            }
            return out.substring(0, out.length() - 2);
        }

    else return"";
}
    public void setGenre_ids(List<Integer> genre_ids) {
        this.genre_ids = genre_ids;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }
}
