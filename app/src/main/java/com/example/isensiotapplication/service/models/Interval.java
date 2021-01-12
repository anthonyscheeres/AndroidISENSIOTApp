package com.example.isensiotapplication.service.models;


import java.text.SimpleDateFormat;
import java.util.Date;


public class Interval {

    public Date time;
    public double lightSensorLuxValue;
    public boolean moistureSensorIsMoist;
    public boolean laserLengthReached;
    public double laserLengthInMm;

    public Interval(Date time, double lightSensorLuxValue, boolean moistureSensorIsMoist, boolean laserLengthReached, double laserLengthInMm) {
        this.time = time;
        this.lightSensorLuxValue = lightSensorLuxValue;
        this.moistureSensorIsMoist = moistureSensorIsMoist;
        this.laserLengthReached = laserLengthReached;
        this.laserLengthInMm = laserLengthInMm;
    }
}
