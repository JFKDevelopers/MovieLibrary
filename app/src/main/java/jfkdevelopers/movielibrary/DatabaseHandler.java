package jfkdevelopers.movielibrary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper{
    //database version
    private static final int DATABASE_VERSION = 1;
    //database name
    private static final String DATABASE_NAME = "moviesManager";
    //table name
    private static final String TABLE_MOVIES = "movies";
    //table column names
    private static final String KEY_ID = "Id";

    private static final String KEY_ADULT = "adult";
    private static final String KEY_BACKDROP = "BDURL";
    private static final String KEY_COLLECTION = "Collection";
    private static final String KEY_BUDGET = "Budget";
    private static final String KEY_GENRES = "Genres";
    private static final String KEY_HOMEPAGE = "Homepage";

    private static final String KEY_IMDBID = "IMDBID";
    private static final String KEY_LANGUAGE = "Language";
    private static final String KEY_ORIGINAL_TITLE = "Original_Title";
    private static final String KEY_OVERVIEW = "Overview";
    private static final String KEY_POPULARITY = "Popularity";
    private static final String KEY_POSTER = "URL";
    private static final String KEY_PRODUCTION_COMPANIES = "Production_Companies";
    private static final String KEY_PRODUCTION_COUNTRIES = "Production_Countries";
    private static final String KEY_RELEASE_DATE = "Release_Date";
    private static final String KEY_REVENUE = "Revenue";
    private static final String KEY_RUNTIME = "Runtime";
    private static final String KEY_SPOKEN_LANGUAGES = "Spoken_Languages";
    private static final String KEY_STATUS = "Status";
    private static final String KEY_TAGLINE = "Tagline";
    private static final String KEY_TITLE = "Title";
    private static final String KEY_VIDEO = "Video";
    private static final String KEY_VOTE_AVERAGE = "Vote_Average";
    private static final String KEY_VOTE_COUNT = "Vote_Count";

    public DatabaseHandler(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    //creating tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MOVIES_TABLE = "CREATE TABLE " + TABLE_MOVIES + " ("
                + KEY_ID + " INTEGER PRIMARY KEY, "
                + KEY_ADULT + " TEXT, "
                + KEY_BACKDROP + " TEXT, "
                + KEY_COLLECTION + " TEXT, "
                + KEY_BUDGET + " INTEGER, "
                + KEY_GENRES + " TEXT, "
                + KEY_HOMEPAGE + " TEXT, "
                + KEY_IMDBID + " TEXT, "
                + KEY_LANGUAGE + " TEXT, "
                + KEY_ORIGINAL_TITLE + " TEXT, "
                + KEY_OVERVIEW + " TEXT, "
                + KEY_POPULARITY + " REAL, "
                + KEY_POSTER + " TEXT, "
                + KEY_PRODUCTION_COMPANIES + " TEXT, "
                + KEY_PRODUCTION_COUNTRIES + " TEXT, "
                + KEY_RELEASE_DATE + " TEXT, "
                + KEY_REVENUE + " INTEGER, "
                + KEY_RUNTIME + " INTEGER, "
                + KEY_SPOKEN_LANGUAGES + " TEXT, "
                + KEY_STATUS + " TEXT, "
                + KEY_TAGLINE + " TEXT, "
                + KEY_TITLE + " TEXT, "
                + KEY_VIDEO + " TEXT, "
                + KEY_VOTE_AVERAGE + " REAL, "
                + KEY_VOTE_COUNT + " INTEGER" + ")";
        db.execSQL(CREATE_MOVIES_TABLE);
    }

    //upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIES);
        //create table again
        onCreate(db);
    }

    //CRUD (Create, Read, Update, Delete) Operations
    //add new movie
    void addMovie(Movie movie){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, movie.getId());
        values.put(KEY_ADULT, movie.isAdult());
        values.put(KEY_BACKDROP, movie.getBackdropPath());
        values.put(KEY_COLLECTION, movie.getBelongsToCollection());
        values.put(KEY_GENRES, movie.getGenres());
        values.put(KEY_HOMEPAGE, movie.getHomepage());
        values.put(KEY_IMDBID, movie.getImdbId());
        values.put(KEY_LANGUAGE, movie.getOriginalLanguage());
        values.put(KEY_ORIGINAL_TITLE, movie.getOriginalTitle());
        values.put(KEY_OVERVIEW, movie.getOverview());
        values.put(KEY_POPULARITY, movie.getPopularity());
        values.put(KEY_POSTER, movie.getPosterPath());
        values.put(KEY_PRODUCTION_COMPANIES, movie.getProductionCompanies());
        values.put(KEY_PRODUCTION_COUNTRIES, movie.getProductionCountries());
        values.put(KEY_RELEASE_DATE, movie.getReleaseDate());
        values.put(KEY_REVENUE, movie.getRevenue());
        values.put(KEY_RUNTIME, movie.getRuntime());
        values.put(KEY_SPOKEN_LANGUAGES, movie.getSpokenLanguages());
        values.put(KEY_STATUS, movie.getStatus());
        values.put(KEY_TAGLINE, movie.getTagline());
        values.put(KEY_TITLE, movie.getTitle());
        values.put(KEY_VIDEO, movie.isVideo());
        values.put(KEY_VOTE_AVERAGE, movie.getVoteAverage());
        values.put(KEY_VOTE_COUNT, movie.getVoteCount());

        //Log.e("poster:", values.get(KEY_POSTER).toString());

        //Inserting row
        db.insert(TABLE_MOVIES, null, values);
        //Log.e("Poster in DB: ",getMovie(movie.getId()).getImgUrl());
        db.close(); //closing database connection
    }

    //getting single movie
    /*public Movie getMovie(String id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_MOVIES, new String[] { KEY_IMDBID,
                        KEY_TITLE, KEY_YEAR, KEY_RATING, KEY_POSTER },
                        KEY_IMDBID + "=?", new String[] { id }, null, null,
                        null, null);
        if(cursor != null)
            cursor.moveToFirst();

        Movie movie = new Movie(cursor.getString(0),cursor.getString(1),
                cursor.getString(2),cursor.getString(3),cursor.getString(4),"","","","","",
                "","","","","","","","","","");
        cursor.close();
        return movie;
    }*/

    public List<Movie> getAllMovies(){
        List<Movie> movieList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_MOVIES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        //looping through all rows and adding to list
        if(cursor.moveToFirst()) {
            do{
                boolean adult = cursor.getString(1).equals("true");
                boolean video = cursor.getString(22).equals("true") ;
                Movie movie = new Movie(cursor.getInt(0),adult,cursor.getString(2),
                                        cursor.getString(3),cursor.getInt(4),cursor.getString(5),
                                        cursor.getString(6),cursor.getString(7),cursor.getString(8),
                                        cursor.getString(9),cursor.getString(10),cursor.getFloat(11),
                                        cursor.getString(12),cursor.getString(13),cursor.getString(14),
                                        cursor.getString(15),cursor.getInt(16),cursor.getInt(17),
                                        cursor.getString(18),cursor.getString(19),cursor.getString(20),
                                        cursor.getString(21),video,cursor.getFloat(23),cursor.getInt(24));

                movieList.add(movie);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return movieList;
    }

    //updating single movie
    /*public int updateMovie(Movie movie){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, movie.getTitle());
        values.put(KEY_YEAR, movie.getYear());
        values.put(KEY_RATING, movie.getRated());
        values.put(KEY_POSTER, movie.getPoster());
        values.put(KEY_RELEASED, movie.getReleased());
        values.put(KEY_RUNTIME, movie.getRuntime());
        values.put(KEY_GENRE, movie.getGenre());
        values.put(KEY_DIRECTOR, movie.getDirector());
        values.put(KEY_WRITER, movie.getWriter());
        values.put(KEY_ACTORS, movie.getActors());
        values.put(KEY_PLOT, movie.getPlot());
        values.put(KEY_LANGUAGE, movie.getLanguage());
        values.put(KEY_COUNTRY, movie.getCountry());
        values.put(KEY_AWARDS, movie.getAwards());
        values.put(KEY_METASCORE, movie.getMetascore());
        values.put(KEY_IMDBRATING, movie.getImdbRating());
        values.put(KEY_IMDBVOTES, movie.getImdbVotes());
        values.put(KEY_TYPE, movie.getType());
        values.put(KEY_RESPONSE, movie.getResponse());

        return db.update(TABLE_MOVIES, values, KEY_IMDBID + " = ?",
                new String[] { movie.getImdbID()});
    }*/

    //deleting single movie
    public void deleteMovie(Movie movie) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MOVIES, KEY_ID + " = ?",
                new String[] { Integer.toString(movie.getId())});
        db.close();
    }

    public void deleteAllMovies(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_MOVIES);
        db.close();
    }
    //getting movies count
    public int getMoviesCount() {
        String countQuery  = "SELECT  * FROM " + TABLE_MOVIES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery,null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public String getTableAsString(){
        HashMap<String,Movie> movieList = new HashMap<String,Movie>();
        String selectQuery = "SELECT  * FROM " + TABLE_MOVIES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        String tableString = String.format("Table %s:\n",TABLE_MOVIES);
        //looping through all rows and adding to list
        if(cursor.moveToFirst()) {
            String[] columnNames = cursor.getColumnNames();
            do{
                for(String name:columnNames){
                    tableString+=String.format("%s: %s\n",name,cursor.getString(cursor.getColumnIndex(name)));
                }
                tableString+="\n";
            }while(cursor.moveToNext());
        }
        cursor.close();
        return tableString;
    }
}