package com.androidclass.bhupen.emergencycallingapp;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class NewUserActivity extends AppCompatActivity {

    private EditText mValueFieldFirstName;
    private EditText mValueFieldLastName;
    private EditText mValueFieldEmail;
    private EditText mValueFieldPassword;
    private EditText mValueFieldHomeAddress;
    private EditText mValueFieldEmergencyContact;
    private EditText mValueFieldMedicalInformation;
    private Button mSubmitButton;

    private Firebase mRootRef;
    private Firebase mUsersRef;
    private FirebaseAuth mAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

//        mRootRef = new Firebase("https://er-app-3756f.firebaseio.com/Users");

        mValueFieldFirstName = (EditText) findViewById(R.id.firstName);
        mValueFieldLastName = (EditText) findViewById(R.id.lastName);
        mValueFieldEmail = (EditText) findViewById(R.id.email);
        mValueFieldPassword = (EditText) findViewById(R.id.password);
        mValueFieldHomeAddress = (EditText) findViewById(R.id.homeAddress);
        mValueFieldEmergencyContact = (EditText) findViewById(R.id.emergencyContact);
        mValueFieldMedicalInformation = (EditText) findViewById(R.id.medicalInformation);

//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("Users");
//        myRef.setValue("Hello, World!");

        mAuth = FirebaseAuth.getInstance();

        mSubmitButton = (Button) findViewById(R.id.submitButton);

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String firstName = mValueFieldFirstName.getText().toString();
                final String lastName = mValueFieldLastName.getText().toString();
                final String email = mValueFieldEmail.getText().toString();
                final String password = mValueFieldPassword.getText().toString();
                final String homeAddress = mValueFieldHomeAddress.getText().toString();
                final String emergencyContact = mValueFieldEmergencyContact.getText().toString();
                final String medicalInformation = mValueFieldMedicalInformation.getText().toString();

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

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    mValueFieldEmail.setError("Valid Email required");
                    mValueFieldEmail.requestFocus();
                    return;

                }

                if (password.isEmpty()) {
                    mValueFieldPassword.setError("Password required");
                    mValueFieldPassword.requestFocus();
                    return;

                }

                if (password.length() < 6) {
                    mValueFieldPassword.setError("Password length too short");
                    mValueFieldPassword.requestFocus();
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

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()) {

                                    User user = new User(
                                            firstName,
                                            lastName,
                                            email,
                                            password,
                                            homeAddress,
                                            emergencyContact,
                                            medicalInformation
                                    );

                                    FirebaseDatabase.getInstance().getReference("Users")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if (task.isSuccessful()) {
                                                Toast.makeText(NewUserActivity.this, getString(R.string.google_crash_reporting_api_key), Toast.LENGTH_LONG).show();
                                            } else {
                                                //display a failure message
                                            }
                                        }
                                    });

                                } else {
                                    Toast.makeText(NewUserActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                startActivity(new Intent(getApplicationContext(), MainActivity.class));

            }


        });

    }


}








