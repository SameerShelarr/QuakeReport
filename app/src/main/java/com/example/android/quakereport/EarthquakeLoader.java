package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

public class EarthquakeLoader extends AsyncTaskLoader<ArrayList<Earthquake>> {

    private static final String LOG_TAG = EarthquakeLoader.class.getName();
    private String mURL;

    public EarthquakeLoader(Context context, String URL) {
        super(context);
        mURL = URL;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<Earthquake> loadInBackground() {

        if (mURL == null){
            Log.e(LOG_TAG, "NULL URL passed in mURL variable");
            return null;
        }

        return QueryUtils.fetchEarthquakeData(mURL);
    }
}
