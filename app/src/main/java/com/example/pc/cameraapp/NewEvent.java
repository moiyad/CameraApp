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

public class NewEvent extends AppCompatActivity  {

    EditText eventName;
    Button startEvent;
    ImageView locationImage;
    TextView locationText, tv;
    LinkedList<Event> eventLinkedList = new LinkedList<Event>();
    private LocationManager locationManager;
    private Location location;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);


        eventName = findViewById(R.id.event_name);
        startEvent = findViewById(R.id.start_event_B);
        locationImage = findViewById(R.id.location_image);
        locationText = findViewById(R.id.location_text);
        tv = findViewById(R.id.textView2);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        startEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("newEvent", "onClick: startEvent");
                Event event = new Event(eventName.getText().toString(),"Riyadh","image");
                eventLinkedList.add(event);
                Intent intent = new Intent(NewEvent.this,EventActivity.class);
                intent.putExtra("name",eventName.getText().toString());
                Log.d("moiyadN", "onClick: "+eventName.getText().toString());
                startActivity(intent);
            }
        });
    }


    private void callCityLocation(){
        if ((ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            Log.d("newEvent", "onClick: if");

            return;
        }
        Log.d("newEvent", "onClick: out if");

        location = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);
        location_name(location);

    }

    private void location_name(Location location){
        try{
            Geocoder geocoder = new Geocoder(this);
            List<Address> addresses = null ;
            addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1 );
            String counter = addresses.get(0).getCountryName();
            String city =  addresses.get(0).getLocality();
            Log.d("newEvent", "location_name: country : "+counter+" city : "+city);
            tv.setText("country : "+counter+" city : "+city);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
