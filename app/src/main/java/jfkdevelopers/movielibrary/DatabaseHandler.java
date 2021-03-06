package jfkdevelopers.movielibrary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.gson.Gson;

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
    private static final String KEY_JSON = "JSON";

    public DatabaseHandler(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    //creating tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MOVIES_TABLE = "CREATE TABLE " + TABLE_MOVIES + " ("
                + KEY_ID + " INTEGER PRIMARY KEY, "
                + KEY_JSON + " STRING" + ")";
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
    void addMovie(int id, String json){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, id);
        values.put(KEY_JSON, json);
        //Inserting row
        db.insert(TABLE_MOVIES, null, values);
        db.close(); //closing database connection
    }

    //getting single movie
    public boolean movieInTable(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT "+KEY_JSON+" FROM "+TABLE_MOVIES+" where Id = ?",new String[]{String.valueOf(id)});
        if(cursor.getCount()<=0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }
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
              //try {
                    if(cursor.getString(1).substring(0,1).equals("{")) {
                        Gson gson = new Gson();
                        Movie movie = gson.fromJson(cursor.getString(1), Movie.class);
                        movieList.add(movie);
                /*}catch(Exception e){
                    Log.e("DB",e.getMessage());
                }*/
                    }
            }while(cursor.moveToNext());
        }
        cursor.close();
        return movieList;
    }

    //updating single movie
    /*public int updateMovie(Movie movie){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, movie.getId());

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