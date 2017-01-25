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
    private static final String KEY_IMDBID = "ImdbId";
    private static final String KEY_TITLE = "Title";
    private static final String KEY_YEAR = "Year";
    private static final String KEY_RATING = "Rating";
    private static final String KEY_POSTER = "URL";

    private static final String KEY_RELEASED = "Released";
    private static final String KEY_RUNTIME = "Runtime";
    private static final String KEY_GENRE = "Genre";
    private static final String KEY_DIRECTOR = "Director";
    private static final String KEY_WRITER = "Writer";
    private static final String KEY_ACTORS = "Actors";
    private static final String KEY_PLOT = "Plot";
    private static final String KEY_LANGUAGE = "Language";
    private static final String KEY_COUNTRY = "Country";
    private static final String KEY_AWARDS = "Awards";
    private static final String KEY_METASCORE = "Metascore";
    private static final String KEY_IMDBRATING = "imdbRating";
    private static final String KEY_IMDBVOTES = "imdbVotes";
    private static final String KEY_TYPE = "Type";
    private static final String KEY_RESPONSE = "Response";

    public DatabaseHandler(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    //creating tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MOVIES_TABLE = "CREATE TABLE " + TABLE_MOVIES + " ("
                + KEY_IMDBID + " STRING PRIMARY KEY, " + KEY_TITLE + " TEXT, "
                + KEY_YEAR + " TEXT, " + KEY_RATING + " TEXT, "
                + KEY_POSTER + " TEXT, "  + KEY_RELEASED + " TEXT, "
                + KEY_RUNTIME + " TEXT, "  + KEY_GENRE + " TEXT, "
                + KEY_DIRECTOR + " TEXT, "  + KEY_WRITER + " TEXT, "
                + KEY_ACTORS + " TEXT, "  + KEY_PLOT + " TEXT, "
                + KEY_LANGUAGE + " TEXT, "  + KEY_COUNTRY + " TEXT, "
                + KEY_AWARDS + " TEXT, "  + KEY_METASCORE + " TEXT, "
                + KEY_IMDBRATING + " TEXT, "  + KEY_IMDBVOTES + " TEXT, "
                + KEY_TYPE + " TEXT, "  + KEY_RESPONSE + " TEXT" + ")";
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
        values.put(KEY_IMDBID, movie.getImdbID());
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

        //Log.e("poster:", values.get(KEY_POSTER).toString());

        //Inserting row
        db.insert(TABLE_MOVIES, movie.getImdbID(), values);
        //Log.e("Poster in DB: ",getMovie(movie.getId()).getImgUrl());
        db.close(); //closing database connection
    }

    //getting single movie
    public Movie getMovie(String id){
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
    }

    public List<Movie> getAllMovies(){
        List<Movie> movieList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_MOVIES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        //looping through all rows and adding to list
        if(cursor.moveToFirst()) {
            do{
                Movie movie = new Movie(cursor.getString(0),cursor.getString(1),
                        cursor.getString(2),cursor.getString(3),cursor.getString(4),
                        cursor.getString(5),cursor.getString(6),cursor.getString(7),
                        cursor.getString(8),cursor.getString(9),cursor.getString(10),
                        cursor.getString(11),cursor.getString(12),cursor.getString(13),
                        cursor.getString(14),cursor.getString(15),cursor.getString(16),
                        cursor.getString(17),cursor.getString(18),cursor.getString(19));
                movieList.add(movie);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return movieList;
    }

    //updating single movie
    public int updateMovie(Movie movie){
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
    }

    //deleting single movie
    public void deleteMovie(Movie movie) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MOVIES, KEY_IMDBID + " = ?",
                new String[] { movie.getImdbID()});
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