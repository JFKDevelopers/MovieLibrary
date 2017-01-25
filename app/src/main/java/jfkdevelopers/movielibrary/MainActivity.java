package jfkdevelopers.movielibrary;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

//Pre-testing:
//decide which content to include on main page and detail page
//create search activity

//Not necessary, but would like to have:
//Have app remember user's sorting preference and add movies in that order
//include letters/years/rating depending on how it's sorted (like an index)
//have a "detail page" which gives more information when a movie is clicked on
//maybe include tomato meter rating somewhere? - will need to update base url.
//////eventually include user rating, type of movie [physical (DVD/BluRay/VHS?) or Streaming(PC/Netflix/Hulu/Amazon/etc.)] //need to use tmdb for this
public class MainActivity extends AppCompatActivity implements OnClickListener,ItemClickListener{

    public final static String EXTRA_MESSAGE = "com.jfkdevelopers.movielibrary.MESSAGE";
    private String TAG = MainActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private static String urlStart = "http://www.omdbapi.com/?t=";
    private static String urlEnd = "&plot=short&r=json"; //add tomatoes=true to get ratings from Rotten Tomatoes
    private static String url = urlStart + "" + urlEnd;
    //private static String upcUrlStart = "http://api.upcdatabase.org/json/86b095cadd53dcdde3825d0d862adf3a/"; //86b095cadd53dcdde3825d0d862adf3a - API Key
    //private static String upcUrl = upcUrlStart + "";
    final CharSequence[] sortOptions = {"Title: A-Z","Title: Z-A","Year","Rating"};
    Context context = this;
    List<Movie> movies;
    MovieAdapter mAdapter;
    RecyclerView rv;
    RecyclerView.LayoutManager rvLM;
    /*EditText input;
    ImageButton imgBtn;*/
    //ImageButton scnBtn;
    public DatabaseHandler db = new DatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*input = (EditText) findViewById(R.id.search);
        imgBtn = (ImageButton) findViewById(R.id.addBtn);*/
        //scnBtn = (ImageButton) findViewById(R.id.scanBtn);
        rv = (RecyclerView) findViewById(R.id.recyclerView);

        rv.setHasFixedSize(true);
        rvLM = new LinearLayoutManager(this);
        rv.setLayoutManager(rvLM);

        movies = db.getAllMovies();

        mAdapter = new MovieAdapter(this, movies);
        rv.setAdapter(mAdapter);
        setUpItemTouchHelper();
        setUpAnimationDecoratorHelper();
        //Log.e(TAG,db.getTableAsString());

        //imgBtn.setOnClickListener(this);
        //scnBtn.setOnClickListener(this);

        /*input.setOnEditorActionListener(new TextView.OnEditorActionListener(){
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event){
                if(actionId == EditorInfo.IME_ACTION_GO){
                    getMovie();
                    return true;
                }
                return false;
            }
        });*/


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog searchDialog;
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                dialog.setTitle("Add Movie");
                final EditText input = new EditText(context);
                //input.setPadding(4,4,4,4);
                input.setHint("Title");
                dialog.setView(input);
                dialog.setPositiveButton(R.string.search_button,new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface di, int item){
                        Intent intent = new Intent(context,Search.class);
                        String message = input.getText().toString();
                        intent.putExtra(EXTRA_MESSAGE,message);
                        startActivity(intent);
                    }
                });
                dialog.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface di, int which) {
                        di.cancel();
                    }
                });
                searchDialog = dialog.create();
                searchDialog.show();
            }
        });

    }
    @Override
    public void onClick(View view, int position){
        final Movie movie = movies.get(position);
        Intent i = new Intent(this, DetailActivity.class); //Update main activity to new activity which will be the detail page for the Movie
        i.putExtra("Title", movie.getTitle());
        i.putExtra("ID", movie.getImdbID());
        Log.e(TAG,"SUCCESS");
        startActivity(i);
    }

    public void onClick(View v) {

        if (connectedToNetwork()) {
            //barcode scanner
            /*if (v.getId() == R.id.scanBtn) {
                url = "";
                upcUrl = "";
                IntentIntegrator scanIntegrator = new IntentIntegrator(this);
                scanIntegrator.initiateScan();
            }*/
            //text entry
            if (v.getId() == R.id.rating) {
                url = "";
                //upcUrl = "";
                /*String temp = input.getText().toString(); // get text from search bar
                input.setText(""); // clears search bar
                */Boolean test = true;
                //test to see if movie title already exists
                for (Movie m : movies) {
                    //if (m.getTitle().toLowerCase().equals(temp.toLowerCase())) test = false;
                }
                //if movie does not already exist, add it in
                if (test) {
                    //url = urlStart + temp.replace(" ", "+") + urlEnd; // combine movie title with the rest of the url
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
                            Log.e(TAG,movies.get(movies.size()-1).toString());
                        }
                    }, 2000);
                }
                //toast if movie already exists in the list
                else Toast.makeText(getApplicationContext(),
                        "Movie is already in list",
                        Toast.LENGTH_LONG)
                        .show();
            }
        } else Toast.makeText(getApplicationContext(),
                "No internet connection. Please try again later.",
                Toast.LENGTH_LONG)
                .show();
    }

    //Barcode scanner results
/*    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            String scanContent = scanningResult.getContents();
            upcUrl = upcUrlStart + scanContent;
            new GetTitleFromUPC().execute();
            if (!url.equals("")) {
                new GetMovies().execute();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.notifyDataSetChanged();
                    }
                }, 1000);
            } else {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "No scan data received!", Toast.LENGTH_SHORT);
                toast.show();
            }
        } else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        /*SearchManager searchManager = (SearchManager)
                getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView)
                menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.
                                 getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener((SearchView.OnQueryTextListener) this);*/

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        /*if(id == R.id.item_undo_checkBox){
            item.setChecked(!item.isChecked());
            ((MovieAdapter)rv.getAdapter()).setUndoOn(item.isChecked());
        }*/

        if(id == R.id.action_addNew){
            /*final AlertDialog searchDialog;
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("Add Movie");
            final EditText input = new EditText(this);
            //input.setPadding(4,4,4,4);
            input.setHint("Title");
            dialog.setView(input);
            dialog.setPositiveButton(R.string.search_button,new DialogInterface.OnClickListener(){
               @Override
                public void onClick(DialogInterface di, int item){
                    Intent intent = new Intent(context,Search.class);
                    String message = input.getText().toString();
                    intent.putExtra(EXTRA_MESSAGE,message);
                    startActivity(intent);
               }
            });
            dialog.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface di, int which) {
                    di.cancel();
                }
            });
            searchDialog = dialog.create();
            searchDialog.show();*/
        }
        else if(id == R.id.action_sort){
            final AlertDialog sortDialog;
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("Sort By");

            dialog.setSingleChoiceItems(sortOptions,-1,new DialogInterface.OnClickListener(){
                public void onClick(DialogInterface dialog, int item){
                    switch(item){
                        case 0: //Sort A-Z
                            Collections.sort(movies,new Comparator<Movie>(){
                                @Override
                                public int compare(Movie m1, Movie m2){
                                    return m1.getTitle().compareTo(m2.getTitle());
                                }
                            });
                            break;
                        case 1: //Sort Z-A
                            Collections.sort(movies,new Comparator<Movie>(){
                                @Override
                                public int compare(Movie m1, Movie m2){
                                    return m2.getTitle().compareTo(m1.getTitle());
                                }
                            });
                            break;
                        case 2: //Sort by year
                            Collections.sort(movies,new Comparator<Movie>(){
                                @Override
                                public int compare(Movie m1, Movie m2){
                                    if(!m1.getYear().equals(m2.getYear())) return m1.getYear().compareTo(m2.getYear());
                                    return m1.getTitle().compareTo(m2.getTitle());
                                }
                            });
                            break;
                        case 3: //Sort by rating
                            Collections.sort(movies,new Comparator<Movie>(){
                                @Override
                                public int compare(Movie m1, Movie m2){
                                    if(!m1.getRated().equals(m2.getRated())) return m1.getRated().compareTo(m2.getRated());
                                    else if(m1.getYear().equals(m2.getYear())) return m1.getTitle().compareTo(m2.getTitle());
                                    return m1.getYear().compareTo(m2.getYear());
                                }
                            });
                            break;
                        default:
                            break;
                    }
                    dialog.dismiss();
                    db.deleteAllMovies();
                    for(Movie m:movies){
                        db.addMovie(m);
                    }
                    mAdapter.notifyDataSetChanged();
                }
            });
            sortDialog = dialog.create();
            sortDialog.show();
        }
        else if (id == R.id.action_deleteAll) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            builder.setTitle("Are you sure you want to delete all items?");
            builder.setMessage("This process can not be undone");
            builder.setPositiveButton("Delete",
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
                    Gson gson = new Gson();
                    Movie m = gson.fromJson(jsonStr, Movie.class);
                    if(m.getTitle()!=null) {
                        db.addMovie(m);
                        movies.add(m);
                    }
                    else{
                        Snackbar snackbar = Snackbar
                                .make(rv, "Movie not found", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                } catch (final Exception e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    url.substring(url.indexOf("?t=") + 3, url.indexOf("&p")).replace("+", " ") + " not found",
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

    //handles barcode scan to get title of movie
    /*private class GetTitleFromUPC extends AsyncTask<Void, Void, Void> {
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
                    if (id != null) {
                        id = id.substring(0, id.indexOf(":"));
                        url = urlStart + id.replace(" ", "+") + urlEnd;
                    }
                } catch (final JSONException e) {
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
    }*/

    public boolean connectedToNetwork() {
        boolean connected = false;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI ||
                    activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                connected = true;
            }
        }
        return connected;
    }

    private void setUpItemTouchHelper() {
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            Drawable background;
            Drawable xMark;
            int xMarkMargin;
            boolean initiated;

            private void init() {
                background = new ColorDrawable(Color.RED);
                xMark = ContextCompat.getDrawable(MainActivity.this, R.drawable.delete);
                xMark.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                xMarkMargin = (int) MainActivity.this.getResources().getDimension(R.dimen.ic_clear_margin);
                initiated = true;
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public int getSwipeDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                int position = viewHolder.getAdapterPosition();
                MovieAdapter movieAdapter = (MovieAdapter) rv.getAdapter();
                if (movieAdapter.isUndoOn() && movieAdapter.isPendingRemoval(position)) {
                    return 0;
                }
                return super.getSwipeDirs(recyclerView, viewHolder);
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int swipedPosition = viewHolder.getAdapterPosition();
                MovieAdapter adapter = (MovieAdapter) rv.getAdapter();
                boolean undoOn = adapter.isUndoOn();
                if (undoOn) {
                    adapter.pendingRemoval(swipedPosition, db,rv);
                } else {
                    adapter.remove(swipedPosition,db,rv);
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                View itemView = viewHolder.itemView;
                if (viewHolder.getAdapterPosition() == -1) {
                    return;
                }
                if (!initiated) {
                    init();
                }
                background.setBounds(itemView.getRight() + (int) dX, itemView.getTop(), itemView.getRight(), itemView.getBottom());
                background.draw(c);

                int itemHeight = itemView.getBottom() - itemView.getTop();
                int intrinsicWidth = xMark.getIntrinsicWidth();
                int intrinsicHeight = xMark.getIntrinsicHeight();

                int xMarkLeft = itemView.getRight() - xMarkMargin - intrinsicWidth;
                int xMarkRight = itemView.getRight() - xMarkMargin;
                int xMarkTop = itemView.getTop() + (itemHeight - intrinsicHeight) / 2;
                int xMarkBottom = xMarkTop + intrinsicHeight;
                xMark.setBounds(xMarkLeft, xMarkTop, xMarkRight, xMarkBottom);

                xMark.draw(c);
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }

        };
        ItemTouchHelper mItemtouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        mItemtouchHelper.attachToRecyclerView(rv);
    }

    private void setUpAnimationDecoratorHelper() {
        rv.addItemDecoration(new RecyclerView.ItemDecoration() {

            // we want to cache this and not allocate anything repeatedly in the onDraw method
            Drawable background;
            boolean initiated;

            private void init() {
                background = new ColorDrawable(Color.RED);
                initiated = true;
            }

            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {

                if (!initiated) {
                    init();
                }

                // only if animation is in progress
                if (parent.getItemAnimator().isRunning()) {

                    // some items might be animating down and some items might be animating up to close the gap left by the removed item
                    // this is not exclusive, both movement can be happening at the same time
                    // to reproduce this leave just enough items so the first one and the last one would be just a little off screen
                    // then remove one from the middle

                    // find first child with translationY > 0
                    // and last one with translationY < 0
                    // we're after a rect that is not covered in recycler-view views at this point in time
                    View lastViewComingDown = null;
                    View firstViewComingUp = null;

                    // this is fixed
                    int left = 0;
                    int right = parent.getWidth();

                    // this we need to find out
                    int top = 0;
                    int bottom = 0;

                    // find relevant translating views
                    int childCount = parent.getLayoutManager().getChildCount();
                    for (int i = 0; i < childCount; i++) {
                        View child = parent.getLayoutManager().getChildAt(i);
                        if (child.getTranslationY() < 0) {
                            // view is coming down
                            lastViewComingDown = child;
                        } else if (child.getTranslationY() > 0) {
                            // view is coming up
                            if (firstViewComingUp == null) {
                                firstViewComingUp = child;
                            }
                        }
                    }

                    if (lastViewComingDown != null && firstViewComingUp != null) {
                        // views are coming down AND going up to fill the void
                        top = lastViewComingDown.getBottom() + (int) lastViewComingDown.getTranslationY();
                        bottom = firstViewComingUp.getTop() + (int) firstViewComingUp.getTranslationY();
                    } else if (lastViewComingDown != null) {
                        // views are going down to fill the void
                        top = lastViewComingDown.getBottom() + (int) lastViewComingDown.getTranslationY();
                        bottom = lastViewComingDown.getBottom();
                    } else if (firstViewComingUp != null) {
                        // views are coming up to fill the void
                        top = firstViewComingUp.getTop();
                        bottom = firstViewComingUp.getTop() + (int) firstViewComingUp.getTranslationY();
                    }

                    background.setBounds(left, top, right, bottom);
                    background.draw(c);

                }
                super.onDraw(c, parent, state);
            }

        });
    }
        public void getMovie(){
            //upcUrl = "";
            /*url = "";
            String temp = input.getText().toString(); // get text from search bar
            input.setText(""); // clears search bar
            Boolean test = true;
            //test to see if movie title already exists
            for (Movie m : movies) {
                if (m.getTitle().toLowerCase().equals(temp.toLowerCase())) test = false;
            }
            //if movie does not already exist, add it in *//*
            if (test) {
                url = urlStart + temp.replace(" ", "%20") + urlEnd; // combine movie title with the rest of the url
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
                        Log.e(TAG,movies.get(movies.size()-1).toString());
                    }
                }, 2000);
            }
            //toast if movie already exists in the list
            else Toast.makeText(getApplicationContext(),
                    "Movie is already in list",
                    Toast.LENGTH_LONG)
                    .show();*/
    }
}