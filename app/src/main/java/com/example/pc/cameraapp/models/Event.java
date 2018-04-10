package com.example.pc.cameraapp.models;

import android.graphics.Bitmap;

import io.realm.RealmObject;

public class Event extends RealmObject{

    String Name;
    String location;
    String image;

    public Event(String name, String location, String image) {
        Name = name;
        this.location = location;
        this.image = image;
    }

    public Event() {
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
