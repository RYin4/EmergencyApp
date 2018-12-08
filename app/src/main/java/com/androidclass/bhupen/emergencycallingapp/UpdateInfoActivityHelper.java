package com.androidclass.bhupen.emergencycallingapp;

import android.app.Application;
import com.firebase.client.Firebase;

public class UpdateInfoActivityHelper extends Application {

        @Override
        public void onCreate() {
            super.onCreate();

            Firebase.setAndroidContext(this);

    }
}
