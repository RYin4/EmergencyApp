package com.androidclass.bhupen.emergencycallingapp;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

//https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=34.062769,-118.17092&radius=16100&type=hospital&key=AIzaSyBTdcZINffyHRlUKkdegbo2PpUWqcTYVog
//https://maps.googleapis.com/maps/api/directions/json?origin=34.062769,-118.17092&destination=34.0616966,-118.2039024802915&key=AIzaSyBTdcZINffyHRlUKkdegbo2PpUWqcTYVog

public class NetUtilities {

    final static String PARAM_KEY = "key";

    final static String BASE_PLACE = "https://maps.googleapis.com/maps/api/place/nearbysearch/json";

    final static String PARAM_LOCATION = "location";
    final static String PARAM_RADIUS = "radius";
    final static String PARAM_TYPE = "type";
    final static String type_hostial = "hospital";
    final static String radius_tenmiles = "16100";


    final static String BASE_DIRECT = "https://maps.googleapis.com/maps/api/directions/json";
    final static String PARAM_ORIGIN = "origin";
    final static String PARAM_DEST = "destination";


    public static URL buildPlaceURL(String apikey, double lat, double lng)
    {
        String latlngstring = "" + lat + "," + lng;
/*
        Uri builtUri = Uri.parse(BASE_PLACE).buildUpon()
                .appendQueryParameter(PARAM_LOCATION, latlngstring)
                .appendQueryParameter(PARAM_RADIUS, radius_tenmiles)
                .appendQueryParameter(PARAM_TYPE, type_hostial)
                .appendQueryParameter(PARAM_KEY, apikey)
                .build();
*/
        URL url = null;


        String builturl = BASE_PLACE + "?" + PARAM_LOCATION + "=" + latlngstring + "&"
                + PARAM_RADIUS + "=" + radius_tenmiles + "&"
                + PARAM_TYPE + "=" + type_hostial + "&"
                + PARAM_KEY + "=" + apikey;
        try {
//            url = new URL(builtUri.toString());
            url = new URL(builturl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static URL buildDirectURL(String apikey, double originlat, double originlng, double destlat, double destlng)
    {
        String originlatlng = "" + originlat + "," + originlng;
        String destlatlng = "" + destlat + "," + destlng;
/*
        Uri builtUri = Uri.parse(BASE_DIRECT).buildUpon()
                .appendQueryParameter(PARAM_ORIGIN, originlatlng)
                .appendQueryParameter(PARAM_DEST, destlatlng)
                .appendQueryParameter(PARAM_KEY, apikey)
                .build();
                */
        String builturl = BASE_DIRECT + "?" + PARAM_ORIGIN + "=" + originlatlng + "&"
                + PARAM_DEST + "=" + destlatlng + "&"
                + PARAM_KEY + "=" + apikey;

        URL url = null;
        try {
//            url = new URL(builtUri.toString());
            url = new URL(builturl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }


    public static String getResponseFromHttpUrl( URL url) throws IOException
    {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput)
            {
                return scanner.next();
            }
            else
            {
                return null;
            }

        } finally {
            urlConnection.disconnect();
        }

    }
}