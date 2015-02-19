package com.mad.demo.instafamous.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.mad.demo.instafamous.R;
import com.mad.demo.instafamous.adapter.InstagramPhotoAdapter;
import com.mad.demo.instafamous.model.InstagramPhoto;
import com.mad.demo.instafamous.restclient.InstagramRestClient;
import com.mad.demo.instafamous.utilities.Utils;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PopularFeedActivity extends ActionBarActivity {

    private Context mContext;
    private ArrayList<InstagramPhoto> mPhotosList;
    private InstagramPhotoAdapter mPhotoAdapter;
    private SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_popular_feed);

        //Initiate the list
        initUI();

        // Send out API request to popular mPhotosList
        fetchPopularPhotos();

    }

    private void initUI() {
        // Get the ListView
        ListView lvPhotos = (ListView) findViewById(R.id.lvPhotos);

        // Create a custom ArrayAdapter for the Instagram photos
        mPhotosList = new ArrayList<InstagramPhoto>();

        //Init adapter
        mPhotoAdapter = new InstagramPhotoAdapter(this, mPhotosList);

        // Assign the custom ArrayAdapter to the ListView
        lvPhotos.setAdapter(mPhotoAdapter);

        // This section of code is used to swipe to refresh
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeContainer.setRefreshing(true);
                fetchPopularPhotos();
            }
        });
        swipeContainer.setColorSchemeResources(R.color.instagram_blue,
                R.color.location_blue,
                R.color.instagram_blue,
                R.color.location_blue);
    }

    // Method to trigger API request for popular mPhotosList
    public void fetchPopularPhotos() {

        if (Utils.isNetworkAvailable(mContext)) {
            // Trigger the GET request
            // This request is asynchronous i.e. we do not send the request and waiting.
            // It sends the request in the background, and when it finishes retrieving,
            // the responseHandler is called.

            InstagramRestClient.getPopularFeed(null, new JsonHttpResponseHandler() {

                // Choosing JSONObject (as opposed to a JSONArray) because our root is a dictionary
                // rather than an array (determined by { - for dictionary vs [ for array)
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    //Iterate over response and decode each popular photo item into a java object
                    JSONArray photosJSON;
                    mPhotosList.clear();
                    try {
                        photosJSON = response.getJSONArray("data"); //array of posts
                        //Iterate array of posts
                        for (int i = 0; i < photosJSON.length(); i++) {
                            JSONObject photoJSON = photosJSON.getJSONObject(i);

                            //Decode JSONObject and convert to POJO
                            InstagramPhoto photo = InstagramPhoto.fromJson(photoJSON);

                            //Add to list
                            mPhotosList.add(photo);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    //Callback
                    mPhotoAdapter.notifyDataSetChanged();
                    swipeContainer.setRefreshing(false);
                }

                @Override
                public void onFailure(int statusCode,
                                      Header[] headers,
                                      String responseString,
                                      Throwable throwable) {

                    Toast.makeText(mContext, getString(R.string.err_cannotload), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(mContext, getString(R.string.err_no_internet), Toast.LENGTH_SHORT).show();
            swipeContainer.setRefreshing(false);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_popular_feed, menu);
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
