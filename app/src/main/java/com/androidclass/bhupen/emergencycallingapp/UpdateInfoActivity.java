package com.androidclass.bhupen.emergencycallingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;

import com.firebase.client.Firebase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import java.net.URL;


public class UpdateInfoActivity extends AppCompatActivity {

    private EditText mValueFieldFirstName;
    private EditText mValueFieldLastName;
    private EditText mValueFieldUpdateInfoEmail;
    private EditText mValueFieldHomeAddress;
    private EditText mValueFieldEmergencyContact;
    private EditText mValueFieldMedicalInformation;
    private Button mUpdateInfoButton;
    private Firebase mRootRef;

    private Firebase mUsersRef;
    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference ref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_info);

        mValueFieldFirstName = (EditText) findViewById(R.id.firstName);
        mValueFieldLastName = (EditText) findViewById(R.id.lastName);
        mValueFieldHomeAddress = (EditText) findViewById(R.id.homeAddress);
        mValueFieldEmergencyContact = (EditText) findViewById(R.id.emergencyContact);
        mValueFieldMedicalInformation = (EditText) findViewById(R.id.medicalInformation);

        mUpdateInfoButton = (Button) findViewById(R.id.updateInfoButton);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userUID = user.getUid();
        ref = FirebaseDatabase.getInstance().getReference().child("Users").child((userUID));


        mUpdateInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String firstName = mValueFieldFirstName.getText().toString();
                String lastName = mValueFieldLastName.getText().toString();
                String homeAddress = mValueFieldHomeAddress.getText().toString();
                String emergencyContact = mValueFieldEmergencyContact.getText().toString();
                String medicalInformation = mValueFieldMedicalInformation.getText().toString();

                if (firstName.isEmpty()) {
                    mValueFieldFirstName.setError("First name required");
                    mValueFieldFirstName.requestFocus();
                    return;
                }

                if (lastName.isEmpty()) {
                    mValueFieldLastName.setError("Last name required");
                    mValueFieldLastName.requestFocus();
                    return;
                }


                if (homeAddress.isEmpty()) {
                    mValueFieldHomeAddress.setError("Home Address required");
                    mValueFieldHomeAddress.requestFocus();
                    return;

                }

                if (emergencyContact.isEmpty()) {
                    mValueFieldEmergencyContact.setError("Emergency Contact required");
                    mValueFieldEmergencyContact.requestFocus();
                    return;

                }

                if (emergencyContact.length() != 10) {
                    mValueFieldEmergencyContact.setError("Emergency contact must be 10 digits long");
                    mValueFieldEmergencyContact.requestFocus();
                    return;
                }

                if (medicalInformation.isEmpty()) {
                    mValueFieldMedicalInformation.setError("Medical Information required");
                    mValueFieldMedicalInformation.requestFocus();
                    return;

                }

                ref.child("firstName").setValue(firstName);
                ref.child("lastName").setValue(lastName);
                ref.child("homeAddress").setValue(homeAddress);
                ref.child("emergencyContact").setValue(emergencyContact);
                ref.child("medicalInformation").setValue(medicalInformation);

                startActivity(new Intent(getApplicationContext(), MainActivity.class));


            }
        });
    }
}