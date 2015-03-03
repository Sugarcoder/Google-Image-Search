package com.sugarcoder.googleimagesearch.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
// import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.sugarcoder.googleimagesearch.adapters.ImageResultsAdapter;
import com.sugarcoder.googleimagesearch.models.ImageResult;
import com.sugarcoder.googleimagesearch.R;


import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;



public class SearchActivity extends ActionBarActivity {
    private EditText etQuery;           // You can access this within the activity
    private GridView gvResults;         // Need to connect these methods to the views
    private ArrayList<ImageResult> imageResults;       // ImageResult is the model that we will be building
    private ImageResultsAdapter aImageResults;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setupViews();      // This is the method responsible for finding views within our application


        // Create the data source for our list
        imageResults = new ArrayList<>();      // Initializing the array list

        // Attaches the data source to an adapter (Now, the data source and the adapter is linked)
        aImageResults = new ImageResultsAdapter(this, imageResults);

        // Link the adapter to the adapter view (GridView)
        gvResults.setAdapter(aImageResults);

    }


    private void setupViews() {
        etQuery = (EditText) findViewById(R.id.etQuery);
        // Whenever you want to locate a template associated with the activity, use 'findViewbyId', then specify the ID you are looking for.
        gvResults = (GridView) findViewById(R.id.gvResults);


        // Set up a ClickListener, so that when you press on each item, the new Activity will open up.

        gvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Launch the image display activity

                // First, create an Intent.
                Intent i = new Intent(SearchActivity.this, ImageDisplayActivity.class);

                // Second, get the image result to display.
                ImageResult result = imageResults.get(position);

                // Third, pass the image result into the Intent
                // One way to write it =>  i.putExtra("url", result.fullUrl);


                i.putExtra("result", result);          // All things that pass into it needs to be serializable or parsable. (Serializable is easier to implement)


                // Fourth, launch the new activity
                startActivity(i);

            }
        });
    }


    // This will be fired whenever the button is pressed (Android onClick)

    public void onImageSearch(View v) {

        String query = etQuery.getText().toString();      // converts the 'editable' to a string

            // Toast.makeText(this,"Search for: " + query, LENGTH_SHORT).show();
            // A Toast is an alert that temporarily shows on the screen, and goes away on its own.


            AsyncHttpClient client = new AsyncHttpClient();       // This creates a new network client

            String searchUrl = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=" + query + "&rsz=8";   // Google Image Search API

            client.get(searchUrl, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("DEBUG", response.toString());

                JSONArray imageResultsJson = null;

                try {
                    imageResultsJson = response.getJSONObject("responseData").getJSONArray("results");
                    imageResults.clear();         // This will clear the existing images from the array (only clear this in cases where it's a new search)

                    // When you make changes to the adapter (like add new items), it modifies the underlying data automatically.

                    aImageResults.addAll(ImageResult.fromJSONArray(imageResultsJson));         // Add all of the items that are parsed from the Json array

                    // Instead of imageResults, you can use aImageResults to update the array list through the adapter.

                    // One way:  aImageResults.notifyDataSetChanged();      This notifies the adapter that there is new item added, so the adapter will make the changes.

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.i("INFO", imageResults.toString());

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);

    }
}
