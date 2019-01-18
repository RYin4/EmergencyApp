package com.androidclass.bhupen.emergencycallingapp;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class LostFragment extends Fragment {
    public static final String locurl = "http://maps.google.com/?q=";

    public static final String PREFERENCE_ID = "UserData";
    public static final String Name = "Name";
    public static final String phoneNumber = "Number";
    public static final String LostSet = "LostSet";
    public static final String currentLatitude = "CurrentLatitude";
    public static final String currentLongitude = "CurrentLongitude";

    String mNumber;
    String mName;

    double lat;
    double lng;

    SharedPreferences sharedPreferences;

    public LostFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lost, container, false);

        sharedPreferences = getActivity().getSharedPreferences(PREFERENCE_ID, Context.MODE_PRIVATE);

        sharedPreferences = getActivity().getSharedPreferences(PREFERENCE_ID, Context.MODE_PRIVATE);
        mName = sharedPreferences.getString(Name, "");
        mNumber = sharedPreferences.getString(phoneNumber, "");
        lat = Double.longBitsToDouble(sharedPreferences.getLong(currentLatitude, 0));
        lng = Double.longBitsToDouble(sharedPreferences.getLong(currentLongitude, 0));

        if (!mNumber.isEmpty()) {
            final String latlngstring = "" + lat + "," + lng;

            Button textcontact = view.findViewById(R.id.losttextcontact);
            textcontact.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Code here executes on main thread after user presses button
                    Intent smsIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + mNumber));
                    smsIntent.putExtra("sms_body", "It's " + mName
                            + ". I am lost!\n"
                            + locurl + latlngstring);

                    if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.SEND_SMS)
                            != PackageManager.PERMISSION_GRANTED)
                        return;

                    startActivity(smsIntent);
                }
            });
        }

        Button findHome = view.findViewById(R.id.findhome);
        findHome.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(LostSet, true);
                editor.commit();


                EmergencyMapFragment emergencyMapFragment = new EmergencyMapFragment();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_map_layout, emergencyMapFragment).commit();

            }
        });
        return view;
    }

}