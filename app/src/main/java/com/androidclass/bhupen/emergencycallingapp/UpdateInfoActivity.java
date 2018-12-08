package com.androidclass.bhupen.emergencycallingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import com.firebase.client.Firebase;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_info);

        mRootRef = new Firebase("https://er-app-3756f.firebaseio.com/Users");

        mValueFieldFirstName = (EditText) findViewById(R.id.firstName);
        mValueFieldLastName = (EditText) findViewById(R.id.lastName);
        mValueFieldUpdateInfoEmail = (EditText) findViewById(R.id.updateInfoEmail);
        mValueFieldHomeAddress = (EditText) findViewById(R.id.homeAddress);
        mValueFieldEmergencyContact = (EditText) findViewById(R.id.emergencyContact);
        mValueFieldMedicalInformation = (EditText) findViewById(R.id.medicalInformation);

        mUpdateInfoButton = (Button) findViewById(R.id.updateInfoButton);

        mUpdateInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String firstName = mValueFieldFirstName.getText().toString();
                String lastName = mValueFieldLastName.getText().toString();
                String updateInfoEmail = mValueFieldUpdateInfoEmail.getText().toString();
                String homeAddress = mValueFieldHomeAddress.getText().toString();
                String emergencyContact = mValueFieldEmergencyContact.getText().toString();
                String medicalInformation = mValueFieldMedicalInformation.getText().toString();

                Firebase first_name = mRootRef.child("First Name");
                Firebase last_name = mRootRef.child("Last Name");
                Firebase update_info_email = mRootRef.child("Email");
                Firebase home_address = mRootRef.child("Home Address");
                Firebase emergency_contact = mRootRef.child("Emergency Contact");
                Firebase medical_information = mRootRef.child("Medical Information");

                //set value firstName to the firebase variable first_name
                first_name.setValue(firstName);
                last_name.setValue(lastName);
                update_info_email.setValue(updateInfoEmail);
                home_address.setValue(homeAddress);
                emergency_contact.setValue(emergencyContact);
                medical_information.setValue(medicalInformation);
            }
        });


    }
}
