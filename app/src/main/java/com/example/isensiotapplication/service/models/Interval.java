package com.example.isensiotapplication.service.models;


import java.util.Date;


public class Interval {

    public double lightSensorLuxValue;
    public boolean moistureSensorIsMoist;
    public boolean laserLengthReached;
    public double laserLengthInMm;

    public Interval(double lightSensorLuxValue, boolean moistureSensorIsMoist, boolean laserLengthReached, double laserLengthInMm) {
        this.lightSensorLuxValue = lightSensorLuxValue;
        this.moistureSensorIsMoist = moistureSensorIsMoist;
        this.laserLengthReached = laserLengthReached;
        this.laserLengthInMm = laserLengthInMm;
    }

    public Interval() {
    }
}
