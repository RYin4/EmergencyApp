package com.androidclass.bhupen.emergencycallingapp;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;


/**
 * A simple {@link Fragment} subclass.
 * import
 * com.google.maps.android.PolyUtil
 * in gradle
 * compile 'com.google.maps.android:android-maps-utils:0.5+'
 *
 */
public class EmergencyMapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;

    static double lat;
    static double lng;

    static double addresslat;
    static double addresslng;

    List<Polyline> routes = new ArrayList<>();


    public static final String PREFERENCE_ID = "UserData";
    public static final String Addressname = "Address";

    public static final String currentLatitude = "CurrentLatitude";
    public static final String currentLongitude = "CurrentLongitude";
    public static final String addressLatitude = "AddressLatitude";
    public static final String addressLongitude = "AddressLongitude";
    public static final String LocSet = "LocationSet";
    public static final String LostSet = "LostSet";

    SharedPreferences sharedPreferences;


    public EmergencyMapFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        SupportMapFragment supportMapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this );

        sharedPreferences = getActivity().getSharedPreferences(PREFERENCE_ID, Context.MODE_PRIVATE);

        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        SharedPreferences.Editor editor = sharedPreferences.edit();


        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            mMap.setOnMyLocationButtonClickListener(this);
            mMap.setOnMyLocationClickListener(this);


            if( !sharedPreferences.getBoolean(LocSet, false)) {
                LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
              /*  Criteria criteria = new Criteria();
                Location location = locationManager.getLastKnownLocation(
                        locationManager.getBestProvider(criteria, false)
                );
                lat = location.getLatitude();
                lng = location.getLongitude();
                */

                LocationListener locationListener = new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        lat = location.getLatitude();
                        lng = location.getLongitude();
                        SharedPreferences.Editor innereditor = sharedPreferences.edit();
                        innereditor.putLong(currentLatitude, Double.doubleToRawLongBits(lat));
                        innereditor.putLong(currentLongitude, Double.doubleToRawLongBits(lng));
                        innereditor.commit();
                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {

                    }

                    @Override
                    public void onProviderEnabled(String provider) {

                    }

                    @Override
                    public void onProviderDisabled(String provider) {

                    }
                };

                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 10, locationListener);




                AddressCoordinatesTask addressCoordinatesTask = new AddressCoordinatesTask();
                try {
                    addressCoordinatesTask.execute().get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Log.i("Debug coordinates", "" + addresslat + "," + addresslng);



//                editor.putLong(currentLatitude, Double.doubleToRawLongBits(lat));
//                editor.putLong(currentLongitude, Double.doubleToRawLongBits(lng));
                editor.putLong(addressLatitude, Double.doubleToRawLongBits(addresslat));
                editor.putLong(addressLongitude, Double.doubleToRawLongBits(addresslng));
                editor.putBoolean(LocSet, true);
                editor.commit();
            }
            else{
                lat = Double.longBitsToDouble(sharedPreferences.getLong(currentLatitude, 0));
                lng = Double.longBitsToDouble(sharedPreferences.getLong(currentLongitude, 0));

                addresslat = Double.longBitsToDouble(sharedPreferences.getLong(addressLatitude, 0));
                addresslng = Double.longBitsToDouble(sharedPreferences.getLong(addressLongitude, 0));
            }

            if(!sharedPreferences.getBoolean(LostSet, false)) {
                URL url = NetUtilities.buildPlaceURL(getContext().getString(R.string.PLACES_DIRECT_API_KEY), lat, lng);

                PlacesQueryTask task = new PlacesQueryTask();
                task.execute(url);

                mMap.setOnMarkerClickListener(this);


            }
            else
            {

                LatLng addresslatlng = new LatLng(addresslat, addresslng);
                mMap.addMarker(new MarkerOptions().position(addresslatlng).title("Home"));
                URL url = NetUtilities.buildDirectURL(getContext().getString(R.string.PLACES_DIRECT_API_KEY), lat, lng, addresslat, addresslng);

                DirectionsQueryTask task = new DirectionsQueryTask();
                task.execute(url);
            }

            LatLng mylatlng = new LatLng(lat, lng);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mylatlng, 11f));

        }
        else {
            mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        }

        editor.putBoolean(LostSet, false);
        editor.commit();




    }



    @Override
    public boolean onMyLocationButtonClick() {
        //       Toast.makeText(getActivity(), "MyLocation button clicked", Toast.LENGTH_SHORT).show();

        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        //      Toast.makeText(getActivity(), "Current location:\n" + location, Toast.LENGTH_LONG).show();

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        LatLng markerPosition = marker.getPosition();
        double destLat = markerPosition.latitude;
        double destLng = markerPosition.longitude;

        URL url = NetUtilities.buildDirectURL(getContext().getString(R.string.PLACES_DIRECT_API_KEY), lat, lng, destLat, destLng);

        DirectionsQueryTask task = new DirectionsQueryTask();
        task.execute(url);
        return false;
    }


    class PlacesQueryTask extends AsyncTask<URL, Void, String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(URL... urls) {
            String placesSearchResults = "";
            try {
                placesSearchResults = NetUtilities.getResponseFromHttpUrl(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return placesSearchResults;
        }
        @Override
        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);
            if (s != null && !s.equals(""))
            {
                ArrayList<PlacesItem> places = JsonUtilities.parsePlaces(s);

                for(int i = 0; i < places.size(); i++ )
                {
                    PlacesItem place = places.get(i);
                    LatLng placelatlng = new LatLng(place.getLatitude(), place.getLongitude());
                    mMap.addMarker(new MarkerOptions().position(placelatlng).title(place.getName()));
                }
            }
        }
    }

    class AddressCoordinatesTask extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected Void doInBackground(Void... voids) {
            Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
            try {
                String addressString = sharedPreferences.getString(Addressname, "");

                Log.i("Debug Start task", "getting results for" + addressString);

                List<Address> addresses = geocoder.getFromLocationName(addressString, 5);
                if( addresses!= null && addresses.size() > 0)
                {
                    Log.i("Debug found an address", "got an address");
                    Address emergencyAddress = addresses.get(0);
                    Log.i("Debug data", emergencyAddress.toString());
                    addresslat = emergencyAddress.getLatitude();
                    Log.i("Debug lat", "" + addresslat);
                    addresslng = emergencyAddress.getLongitude();
                    Log.i("Debug long", "" + addresslng);

                }
                Log.i("Debug End task", "finished getting results for" + addressString);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }


    class DirectionsQueryTask extends AsyncTask<URL, Void, String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            clearPolylines();
        }
        @Override
        protected String doInBackground(URL... urls) {
            String directionsSearchResults = "";
            try {
                directionsSearchResults = NetUtilities.getResponseFromHttpUrl(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return directionsSearchResults;
        }
        @Override
        protected void onPostExecute(String s)
        {
            super.onPostExecute(s);
            if (s != null && !s.equals(""))
            {
                ArrayList<DirectionsItem> directions = JsonUtilities.parseDirections(s);

                for(int i = 0; i < directions.size(); i++ )
                {
                    DirectionsItem direction = directions.get(i);
                    List<LatLng> route = PolyUtil.decode(direction.getEncodedpoly());

                    Polyline polyline = mMap.addPolyline(new PolylineOptions()
                            .addAll(route));

                    routes.add(polyline);

                }
            }
        }

        private void clearPolylines()
        {
            for(Polyline route : routes)
                route.remove();

            routes.clear();
        }

    }




}