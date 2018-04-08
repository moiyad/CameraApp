package com.example.pc.cameraapp;

import android.graphics.Bitmap;

public class Event {

    String Name;
    String location;
    String image;

    public Event(String name, String location, String image) {
        Name = name;
        this.location = location;
        this.image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
