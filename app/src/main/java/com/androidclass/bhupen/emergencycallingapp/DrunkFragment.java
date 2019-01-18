package com.androidclass.bhupen.emergencycallingapp;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lyft.lyftbutton.LyftButton;
import com.lyft.lyftbutton.RideParams;
import com.lyft.lyftbutton.RideTypeEnum;
import com.lyft.networking.ApiConfig;

import com.uber.sdk.android.core.UberSdk;
import com.uber.sdk.android.rides.RideParameters;
import com.uber.sdk.android.rides.RideRequestButton;
import com.uber.sdk.android.rides.RideRequestButtonCallback;
import com.uber.sdk.core.auth.Scope;
import com.uber.sdk.rides.client.SessionConfiguration;
import com.uber.sdk.rides.client.ServerTokenSession;
import com.uber.sdk.rides.client.error.ApiError;


/**
 * A simple {@link Fragment} subclass.
 */
public class DrunkFragment extends Fragment {

    public static final String PREFERENCE_ID = "UserData";
    public static final String currentLatitude = "CurrentLatitude";
    public static final String currentLongitude = "CurrentLongitude";
    public static final String addressLatitude = "AddressLatitude";
    public static final String addressLongitude = "AddressLongitude";
    //

    SharedPreferences sharedPreferences;

    static double lat;
    static double lng;

    static double addresslat;
    static double addresslng;


    public DrunkFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_drunk, container, false);

        sharedPreferences = getActivity().getSharedPreferences(PREFERENCE_ID, Context.MODE_PRIVATE);
        lat = Double.longBitsToDouble(sharedPreferences.getLong(currentLatitude, 0));
        lng = Double.longBitsToDouble(sharedPreferences.getLong(currentLongitude, 0));

        addresslat = Double.longBitsToDouble(sharedPreferences.getLong(addressLatitude, 0));
        addresslng = Double.longBitsToDouble(sharedPreferences.getLong(addressLongitude, 0));

        ApiConfig apiConfig = new ApiConfig.Builder()
                .setClientId(getContext().getString(R.string.LYFT_CLIENT_ID))
                .setClientToken(getContext().getString(R.string.LYFT_CLIENT_TOKEN))
                .build();

        LyftButton lyftButton = (LyftButton) view.findViewById(R.id.lyft_button);
        lyftButton.setApiConfig(apiConfig);

        RideParams.Builder rideParamsBuilder = new RideParams.Builder()
                .setPickupLocation(lat, lng)
                .setDropoffLocation(addresslat, addresslng);
        rideParamsBuilder.setRideTypeEnum(RideTypeEnum.CLASSIC);

        lyftButton.setRideParams(rideParamsBuilder.build());
        lyftButton.load();



        SessionConfiguration config = new SessionConfiguration.Builder()
                // mandatory
                .setClientId(getContext().getString(R.string.UBER_CLIENT_ID))
                // required for enhanced button features
                .setServerToken(getContext().getString(R.string.UBER_SERVER_TOKEN))
                .build();

//        UberSdk.initialize(config);

        RideRequestButton requestButton = (RideRequestButton) view.findViewById(R.id.rideRequestButton);

        RideParameters rideParams = new RideParameters.Builder()
                // Required for price estimates; lat (Double), lng (Double), nickname (String), formatted address (String) of dropoff location
                .setDropoffLocation(
                        addresslat, addresslng, "", "")
                // Required for pickup estimates; lat (Double), lng (Double), nickname (String), formatted address (String) of pickup location
                .setPickupLocation(lat, lng, "", "")
                .build();
// set parameters for the RideRequestButton instance

        RideRequestButtonCallback callback = new RideRequestButtonCallback() {

            @Override
            public void onRideInformationLoaded() {

            }

            @Override
            public void onError(ApiError apiError) {

            }

            @Override
            public void onError(Throwable throwable) {

            }
        };

        requestButton.setRideParameters(rideParams);

        ServerTokenSession session = new ServerTokenSession(config);
        requestButton.setSession(session);
        requestButton.setCallback(callback);

        requestButton.loadRideInformation();




        return view;
    }

}