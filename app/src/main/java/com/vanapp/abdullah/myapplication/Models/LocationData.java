package com.vanapp.abdullah.myapplication.Models;


public class LocationData {

    public double latitude;
    public double longitude;
    public String date;
    public String time;


    public LocationData() {
    }




    public LocationData(double latitude, double longitude, String date ,String time) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.date = date;
        this.time = time;
    }
}