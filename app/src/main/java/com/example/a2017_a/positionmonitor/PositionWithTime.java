package com.example.a2017_a.positionmonitor;

import java.util.Calendar;

public class PositionWithTime {
    public double longitude, latitude;
    public int year, month, day, hour, minute, second;

    public PositionWithTime(double latitude, double longitude) {
        this.longitude = longitude;
        this.latitude = latitude;

        Calendar calendar = Calendar.getInstance();
        this.year = calendar.get(Calendar.YEAR);
        this.month = calendar.get(Calendar.MONTH);
        this.day = calendar.get(Calendar.DAY_OF_MONTH);
        this.hour = calendar.get(Calendar.HOUR);
        this.minute = calendar.get(Calendar.MINUTE);
        this.second = calendar.get(Calendar.SECOND);
    }
}