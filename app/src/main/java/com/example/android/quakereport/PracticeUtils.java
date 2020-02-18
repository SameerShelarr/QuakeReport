package com.example.android.quakereport;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public final class PracticeUtils {

    public PracticeUtils(){}

    public static ArrayList<Earthquake> fetchDataFromUrl(String requestURL){
        //Here will use the various helper methods defined below and return the data in the above defined format.
        return null;
    }

    public static URL createURL(String requestURL){
        //Accepts a string URL and parse it into the URL class and return a URL.
        return null;
    }

    public static String makeHttpRequest(URL url){
        //Accepts a URL, Uses the HttpURLConnection class to connect to the internet, Parses and returns the data received in String format.
        return null;
    }

    private static String readFromStream(InputStream inputStream){
        //A helper method for makeHttpRequest() method.
        //Accepts InputStream, Coverts the InputStream into a String and returns.
        return null;
    }

    public static ArrayList<Earthquake> extractEarthquakesFromJSON(String earthquakeJSON){
        //Accepts JSON file in string dos various operations on the string and returns a array of earthquake data.
        return null;
    }
}
