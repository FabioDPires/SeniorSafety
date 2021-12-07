package com.example.seniorsafety.models;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LocationData {
    double latitude;
    double longitude;
    String hour;
    String data;
    Date d;
    SimpleDateFormat formatedDate;
    SimpleDateFormat formatedHour;



    public LocationData(double latitude, double longitude ) {
        this.d=new Date();
        this.latitude = latitude;
        this.longitude = longitude;
        this.formatedDate = new SimpleDateFormat("dd_MM_yyyy");
        this.formatedHour = new SimpleDateFormat("HH:mm:ss");
        this.hour=formatedHour.format(d);
        this.data=formatedDate.format(d);
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getHora() {
        return hour;
    }

    public void setHora(String hora) {
        this.hour = hora;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
