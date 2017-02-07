package jfkdevelopers.movielibrary;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static jfkdevelopers.movielibrary.MovieAdapter.SER_KEY;

public class Search extends AppCompatActivity {
    String searchTitle;
    private static String urlStart = "http://api.themoviedb.org/3/search/movie?api_key=13de0f310da7852b09b07e6a9f3a16ae&sort_by=popularity.desc&query=";
    public String url = "";
    private String TAG = Search.class.getSimpleName();
    private ProgressDialog pDialog;

    RecyclerView rv;
    RecyclerView.LayoutManager rvLM;

    MovieAdapter moAdapter;
    List<Movie> detailedSearchResults = new ArrayList<>();
    ArrayList<Movie> selectedMovies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        rv = (RecyclerView) findViewById(R.id.searchRecyclerView);
        rv.setHasFixedSize(true);

        rvLM = new LinearLayoutManager(this);
        rv.setLayoutManager(rvLM);

        searchTitle = getIntent().getStringExtra("com.jfkdevelopers.movielibrary.MESSAGE").trim();
        //searchTitle = searchTitle.trim();
        url = urlStart + searchTitle.replace(" ", "+");

        new SearchMovies().execute();
        moAdapter = new MovieAdapter(this,detailedSearchResults);
        rv.setAdapter(moAdapter);
    }

    public void addToSelected(Movie m){
        selectedMovies.add(m);
    }

    public void moviesToBeAdded(){
        Bundle mBundle = new Bundle();
        mBundle.putSerializable(SER_KEY,selectedMovies);
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtras(mBundle);
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            //moviesToBeAdded();
            finish();
        }
        else if (menuItem.getItemId() == R.id.action_add){
            moviesToBeAdded();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private class SearchMovies extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(Search.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }
        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);
            //Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONArray results = jsonObj.getJSONArray("results");
                    for(int i=0;i<results.length();i++){
                        JSONObject temp = results.getJSONObject(i);
                        url = "https://api.themoviedb.org/3/movie/" + temp.get("id") + "?api_key=13de0f310da7852b09b07e6a9f3a16ae&append_to_response=credits";
                        String movieStr = sh.makeServiceCall(url);
                        Gson gson = new Gson();
                        Movie m = gson.fromJson(movieStr,Movie.class);
                        if(m!=null){
                            detailedSearchResults.add(m);
                        }
                    }
                } catch (final Exception e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                           Toast.makeText(getApplicationContext(),
                                    url.substring(url.indexOf("ry=") + 3) + " not found",
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


}
