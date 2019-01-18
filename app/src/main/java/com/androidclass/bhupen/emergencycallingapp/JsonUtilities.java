package com.androidclass.bhupen.emergencycallingapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtilities {

    public static ArrayList<PlacesItem> parsePlaces(String s)
    {
        ArrayList<PlacesItem> placesList = new ArrayList<>();

        try {
            JSONObject jObject = new JSONObject(s);
            JSONArray places = jObject.getJSONArray("results");

            for( int i = 0; i < places.length(); i++)
            {
                JSONObject place = places.getJSONObject(i);
                JSONObject placeloc = (place.getJSONObject("geometry")).getJSONObject("location");

                placesList.add(new PlacesItem(place.getString("name"),
                        place.getString("vicinity"),
                        placeloc.getDouble("lat"),
                        placeloc.getDouble("lng")));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return placesList;

    }


    public static ArrayList<DirectionsItem> parseDirections(String s)
    {
        ArrayList<DirectionsItem> directionsList = new ArrayList<>();

        try {
            JSONObject jObject = new JSONObject(s);
            JSONArray directions = jObject.getJSONArray("routes");

            for( int i = 0; i < directions.length(); i++)
            {
                JSONObject overviewpoly = (directions.getJSONObject(i)).getJSONObject("overview_polyline");


                directionsList.add(new DirectionsItem(overviewpoly.getString("points")));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return directionsList;

    }
}