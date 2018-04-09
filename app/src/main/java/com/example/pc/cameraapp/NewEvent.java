package com.example.pc.cameraapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class NewEvent extends AppCompatActivity {

    EditText eventName;
    Button startEvent;
    ImageView locationImage;
    TextView locationText, tv;
    LinkedList<Event> eventLinkedList = new LinkedList<Event>();
    private LocationManager locationManager;
    private Location location;
    private LocationListener listener;
    int i ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);

         i = 0;
        eventName = findViewById(R.id.event_name);
        startEvent = findViewById(R.id.start_event_B);
        locationImage = findViewById(R.id.location_image);
        locationText = findViewById(R.id.location_text);
        tv = findViewById(R.id.textView2);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        configure_button();

        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                location_name(location);
                i++;
                Log.d("NewEvent", "onLocationChanged: "+i);
                return;
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        };

    }

        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            switch (requestCode){
                case 10:
                    configure_button();
                    break;
                default:
                    Log.d("newEvent", "default: ");
                    break;
            }
        }


    private void configure_button() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.INTERNET}
                        ,10);
            }
            return;
        }
        locationImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationManager.requestLocationUpdates("gps", 0, 10000, listener);
                return;
            }
        });
    }

    private void location_name(Location location){
        try{
            Geocoder geocoder = new Geocoder(NewEvent.this);
            List<Address> addresses = null ;
            addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1 );
            String counter = addresses.get(0).getCountryName();
            String city =  addresses.get(0).getLocality();
            Log.d("newEvent", "location_name: country : "+counter+" city : "+city);
            locationText.setText("country : "+counter+"\n city : "+city);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}




//
