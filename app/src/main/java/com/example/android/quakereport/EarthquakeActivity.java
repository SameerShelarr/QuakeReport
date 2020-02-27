/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Earthquake>> {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    private static final String USGS_REQUEST_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query";
    private EarthquakeArrayAdapter earthquakeArrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        ListView mainListView = (ListView) findViewById(R.id.list);
        TextView emptyTextView = (TextView) findViewById(R.id.empty_text_view);
        LoaderManager loaderManager = getLoaderManager();

        ConnectivityManager cm = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if (isConnected){
            loaderManager.initLoader(0, null, this);
        } else {
            emptyTextView.setText("No internet connection.");
            ProgressBar circularProgressBar = (ProgressBar) findViewById(R.id.progress_circular);
            circularProgressBar.setVisibility(View.GONE);
        }

        mainListView.setEmptyView(emptyTextView);
    }

    @Override
    public Loader<ArrayList<Earthquake>> onCreateLoader(int id, Bundle args) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String minMagnitude = sharedPrefs.getString(getString(R.string.settings_min_magnitude_key), getString(R.string.settings_min_magnitude_default));
        Uri baseUri = Uri.parse(USGS_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        String orderBy = sharedPrefs.getString(getString(R.string.settings_order_by_key), getString(R.string.settings_order_by_default));

        uriBuilder.appendQueryParameter("format", "geojson");
        uriBuilder.appendQueryParameter("limit", "10");
        uriBuilder.appendQueryParameter("minmag", minMagnitude);
        uriBuilder.appendQueryParameter("orderby", orderBy);

        return new EarthquakeLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Earthquake>> loader, ArrayList<Earthquake> data) {

        if (data == null){
                Log.e(LOG_TAG, "List of earthquakes received at onLoadFinished method is NULL");
                return;
            }

        TextView EmptyTextView = (TextView) findViewById(R.id.empty_text_view);
        ProgressBar circularProgressBar = (ProgressBar) findViewById(R.id.progress_circular);

        circularProgressBar.setVisibility(View.GONE);

        EmptyTextView.setText("No Earthquakes, Good times!");

            final ArrayList<Earthquake> earthquakes1 = data;

            earthquakeArrayAdapter = new EarthquakeArrayAdapter(EarthquakeActivity.this, data);
            ListView mainListView = (ListView) findViewById(R.id.list);
            mainListView.setAdapter(earthquakeArrayAdapter);

            mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                    Earthquake currentEarthquake = earthquakes1.get(position);

                    Uri webPage = Uri.parse(currentEarthquake.getUrl());
                    Intent intent = new Intent(Intent.ACTION_VIEW, webPage);
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    }
                }
            });
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Earthquake>> loader) {
        earthquakeArrayAdapter.clear();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}