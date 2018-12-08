package com.androidclass.bhupen.emergencycallingapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

/*public class FingerprintActivity extends AppCompatActivity {

    private ImageView mFingerPrintImage;
    private TextView mHeadingLabel;
    private TextView mParaLabel;
    private FingerprintManager fingerManager;
    private  LoginActivity loginActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fingerprint);

        mFingerPrintImage = (ImageView) findViewById(R.id.fingerprint_image);
        mHeadingLabel = (TextView) findViewById(R.id.heading);
        mParaLabel = (TextView) findViewById(R.id.paraLabel);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            fingerManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);
            if (!fingerManager.isHardwareDetected()) {
                mParaLabel.setText("Fingerprint Not detected in the device");


            }else if (ContextCompat.checkSelfPermission(this,Manifest.permission.USE_FINGERPRINT)!=PackageManager.PERMISSION_GRANTED){

                mParaLabel.setText("Permission not granted to use fingerprint scanner");
            }else if(loginActivity=Manifest.permission.USE_FINGERPRINT){

            }

        }
    }
}
*/