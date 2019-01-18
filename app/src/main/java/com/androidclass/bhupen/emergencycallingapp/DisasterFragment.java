package com.androidclass.bhupen.emergencycallingapp;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class DisasterFragment extends Fragment {

    final static String EARTHQUAKE_URL = "https://www.ready.gov/earthquakes";
    final static String TORNADO_URL = "https://www.ready.gov/tornadoes";
    final static String HURRICANE_URL = "https://www.ready.gov/hurricanes";
    final static String VOLCANO_URL = "https://www.ready.gov/volcanoes";
    final static String TSUNAMI_URL = "https://www.ready.gov/tsunamis";
    final static String BLIZZARD_URL = "https://www.ready.gov/winter-weather";


    public DisasterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_disaster, container, false);

        Button quake = view.findViewById(R.id.earthquake);
        quake.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(EARTHQUAKE_URL));
                startActivity(browserIntent);
            }
        });

        Button bliz = view.findViewById(R.id.blizzard);
        bliz.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(BLIZZARD_URL));
                startActivity(browserIntent);
            }
        });

        Button hurricane = view.findViewById(R.id.hurricane);
        hurricane.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(HURRICANE_URL));
                startActivity(browserIntent);
            }
        });

        Button tornado = view.findViewById(R.id.tornado);
        tornado.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(TORNADO_URL));
                startActivity(browserIntent);
            }
        });

        Button tsunami = view.findViewById(R.id.tsunami);
        tsunami.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(TSUNAMI_URL));
                startActivity(browserIntent);
            }
        });

        Button volcano = view.findViewById(R.id.volcano);
        volcano.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(VOLCANO_URL));
                startActivity(browserIntent);
            }
        });

        return view;


    }

}