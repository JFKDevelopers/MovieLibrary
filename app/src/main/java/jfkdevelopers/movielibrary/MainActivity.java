package jfkdevelopers.movielibrary;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Comparator;
import java.util.List;

//Pre-testing:
//change icons to primary app color and remove button backgrounds - updated button icons and removed background - may want to revisit color
//decide which content to include on main page
//after sorting, store in database in sorted order

//Not necessary, but would like to have:
//include letters/years/rating depending on how it's sorted (like an index)
//have a "detail page" which gives more information when a movie is clicked on
//maybe include tomato meter rating somewhere? - will need to update base url.
//////eventually include user rating, type of movie [physical (DVD/BluRay/VHS?) or Streaming(PC/Netflix/Hulu/Amazon/etc.)]
public class MainActivity extends AppCompatActivity implements OnClickListener{

    private String TAG = MainActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private static String urlStart =  "http://www.omdbapi.com/?t=";
    private static String urlEnd = "&plot=short&r=json"; //add tomatoes=true to get ratings from Rotten Tomatoes
    private static String url = urlStart + "" + urlEnd;
    private static String upcUrlStart = "http://api.upcdatabase.org/json/86b095cadd53dcdde3825d0d862adf3a/"; //86b095cadd53dcdde3825d0d862adf3a - API Key
    private static String upcUrl = upcUrlStart + "";

    Context context = this;
    List<Movie> movies;
    MovieAdapter mAdapter;
    ListView lv;
    EditText input;
    ImageButton imgBtn;
    ImageButton scnBtn;
    DatabaseHandler db = new DatabaseHandler(this);

    float historicX = Float.NaN, historicY = Float.NaN;
    static final int DELTA = 750;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        input = (EditText) findViewById(R.id.search);
        imgBtn = (ImageButton) findViewById(R.id.addBtn);
        scnBtn = (ImageButton) findViewById(R.id.scanBtn);
        lv = (ListView) findViewById(R.id.list);
        Log.e(TAG,db.getTableAsString());
        movies = db.getAllMovies();
        mAdapter = new MovieAdapter(this, movies);
        lv.setAdapter(mAdapter);
        imgBtn.setOnClickListener(this);
        scnBtn.setOnClickListener(this);

        lv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        historicX = event.getX();
                        historicY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        int heightOfEachItem = lv.getChildAt(0).getHeight();
                        int heightOfFirstItem = lv.getChildAt(0).getTop()+lv.getFirstVisiblePosition()*heightOfEachItem;
                        final int firstPosition = (int) Math.ceil(heightOfFirstItem / heightOfEachItem);
                        final int wantedPosition = (int) Math.floor((historicY - lv.getChildAt(0).getTop()) / heightOfEachItem) + firstPosition;
                        if (event.getX() - historicX < -DELTA) {
                            if(wantedPosition>=0&&wantedPosition<movies.size()) {
                                lv.getChildAt(wantedPosition).startAnimation(FunctionDeleteRowWhenSlidingLeft(500));
                                //after 500 milliseconds remove item from db and movieList, then update the adapter.
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        db.deleteMovie(movies.get(wantedPosition));
                                        movies.remove(wantedPosition);
                                        mAdapter.notifyDataSetChanged();
                                    }
                                }, 500);
                            }
                            return true;
                        }
                        else if (event.getX() - historicX > DELTA) {
                            FunctionDeleteRowWhenSlidingRight();
                            return true;
                        }
                        break;
                    default:
                        return false;
                }
                return false;
            }
        });
    }
    public void updateAdapter(){
        mAdapter.notifyDataSetChanged();
    }
    public Animation FunctionDeleteRowWhenSlidingLeft(int duration){
        Animation outtoLeft = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, -1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        outtoLeft.setDuration(duration);
        outtoLeft.setInterpolator(new AccelerateInterpolator());
        return outtoLeft;

        /*try {
            movies.remove(position);
            mAdapter.notifyDataSetChanged();
        }catch (Exception e){
            e.printStackTrace();
        }*/
        //Toast.makeText(this,"Left",Toast.LENGTH_SHORT).show();
    }
    public void FunctionDeleteRowWhenSlidingRight(){
        Toast.makeText(this,"Right",Toast.LENGTH_SHORT).show();
    }
    public void onClick(View v){
        //barcode scanner
        if(connectedToNetwork()) {
            if (v.getId() == R.id.scanBtn) {
                url = "";
                upcUrl = "";
                IntentIntegrator scanIntegrator = new IntentIntegrator(this);
                scanIntegrator.initiateScan();
            }
            //text entry
            else if (v.getId() == R.id.addBtn) {
                url = "";
                upcUrl = "";
                String temp = input.getText().toString(); // get text from search bar
                input.setText(""); // clears search bar
                Boolean test = true;
                //test to see if movie title already exists
                for (Movie m : movies) {
                    if (m.getTitle().toLowerCase().equals(temp.toLowerCase())) test = false;
                }
                //if movie does not already exist, add it in */
                if (test) {
                    url = urlStart + temp.replace(" ", "+") + urlEnd; // combine movie title with the rest of the url
                    //making a request to url and getting response
                    new GetMovies().execute();
                    //dismiss the keyboard when add button is clicked but only if movie doesn't already exist in list
                    InputMethodManager inputManager = (InputMethodManager)
                            getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                    //Handler will wait 1 second before notifying data set change in order to property update the ListView.
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.notifyDataSetChanged();
                        }
                    }, 1000);
                }
                //toast if movie already exists in the list
                else Toast.makeText(getApplicationContext(),
                        "Movie is already in list",
                        Toast.LENGTH_LONG)
                        .show();
            }
        }
        else Toast.makeText(getApplicationContext(),
                "No internet connection. Please try again later.",
                Toast.LENGTH_LONG)
                .show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,intent);
        if(scanningResult!=null){
            String scanContent = scanningResult.getContents();
            upcUrl = upcUrlStart + scanContent;
            new GetTitleFromUPC().execute();
            if(!url.equals("")){
                new GetMovies().execute();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.notifyDataSetChanged();
                    }
                }, 1000);
            }
            else{
                Toast toast = Toast.makeText(getApplicationContext(),
                        "No scan data received!", Toast.LENGTH_SHORT);
                toast.show();
            }
            //Toast toast = Toast.makeText(getApplicationContext(), scanContent, Toast.LENGTH_SHORT);
            //toast.show();
        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_sortAZ) {
            mAdapter.sort(new Comparator<Movie>() {
                @Override
                public int compare(Movie m1, Movie m2) {
                    return m1.getTitle().compareTo(m2.getTitle());
                }
            });
            mAdapter.notifyDataSetChanged();
            return true;
        }
        else if(id == R.id.action_sortZA){
            mAdapter.sort(new Comparator<Movie>() {
                @Override
                public int compare(Movie m1, Movie m2) {
                    return m2.getTitle().compareTo(m1.getTitle());
                }
            });
            mAdapter.notifyDataSetChanged();
            return true;
        }
        else if(id == R.id.action_sortYear){
            mAdapter.sort(new Comparator<Movie>() {
                @Override
                public int compare(Movie m1, Movie m2) {
                    return m1.getYear().compareTo(m2.getYear());
                }
            });
            mAdapter.notifyDataSetChanged();
            return true;
        }
        else if(id == R.id.action_sortRating){
            mAdapter.sort(new Comparator<Movie>() {
                @Override
                public int compare(Movie m1, Movie m2) {
                    return m1.getRating().compareTo(m2.getRating());
                }
            });
            mAdapter.notifyDataSetChanged();
            return true;
        }
        else if(id == R.id.action_deleteAll){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            builder.setTitle("Are you sure you want to delete all items?");
            builder.setMessage("This process can not be undone");
            builder.setPositiveButton("Confirm",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            movies.clear();
                            db.deleteAllMovies();
                            mAdapter.notifyDataSetChanged();
                        }
                    });
            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class GetMovies extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);
            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject c = new JSONObject(jsonStr);

                        String id = c.getString("imdbID");
                        String title = c.getString("Title");
                        String year = c.getString("Year");
                        String rating = c.getString("Rated");
                        //String plot = c.getString("Plot");
                        String imgUrl = c.getString("Poster");
                        String genre = c.getString("Genre");
                        Movie m = new Movie(id,title,year,rating,imgUrl,genre);
                        //Movie m = new Movie(id,title,year,rating,plot,imgUrl);
                        db.addMovie(m);
                        movies.add(m);
                }catch(final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    url.substring(url.indexOf("?t=")+3,url.indexOf("&p")).replace("+"," ") + " not found",
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                Toast.makeText(getApplicationContext(),
                        "Error downloading information",
                        Toast.LENGTH_LONG)
                        .show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
        }
    }
    private class GetTitleFromUPC extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(upcUrl);
            Log.e(TAG, "Response from upcURL: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject c = new JSONObject(jsonStr);

                    String id = c.getString("itemname");
                    if(id!=null) {
                        id = id.substring(0, id.indexOf(":"));
                        url = urlStart + id.replace(" ", "+") + urlEnd;
                    }
                }catch(final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                        }
                    });
                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                Toast.makeText(getApplicationContext(),
                        "Error downloading information",
                        Toast.LENGTH_LONG)
                        .show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
        }
    }

    public boolean connectedToNetwork(){
        boolean connected = false;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if(activeNetwork!=null){
            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI ||
                    activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE){
                connected = true;
            }
        }
        return connected;
    }

}