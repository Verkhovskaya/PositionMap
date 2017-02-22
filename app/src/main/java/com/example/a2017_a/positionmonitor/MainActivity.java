package com.example.a2017_a.positionmonitor;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.ObjectInputStream;


public class MainActivity extends AppCompatActivity {

    TextView mLatitudeText;
    TextView mLongitudeText;
    LocationManager lm;
    Location location;
    PositionWithTime currentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try {
            location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
        catch (SecurityException e) {

        }

        mLatitudeText = (TextView) findViewById(R.id.latitude);
        mLongitudeText = (TextView) findViewById(R.id.longitude);


        FileInputStream fis = context.openFileInput(fileName);
        ObjectInputStream is = new ObjectInputStream(fis);
        SimpleClass simpleClass = (SimpleClass) is.readObject();
        is.close();
        fis.close();
    }



    public void onClick(View v) {
        currentPosition = new PositionWithTime(location.getLatitude(), location.getLongitude());
        mLatitudeText.setText(String.valueOf(currentPosition.hour));
        mLongitudeText.setText(String.valueOf(currentPosition.minute));
    }


}
