package com.example.isensiotapplication.controller;

import com.example.isensiotapplication.service.models.Interval;

import java.util.List;

public class MoistureGraphConverter {

    public int dry = 0;

    public int wet = 0;

    int Y;

    public MoistureGraphConverter(List<Interval> intervals, int Y) {
        this.Y = Y;
        Timeswet(intervals);
    }


    public void Timeswet(List<Interval> collection) {
        int counter = 0;
        for (Interval interval : collection) {
            if (counter >= Y) {
                break;
            }
            if (interval.moistureSensorIsMoist) {
                wet++;
            } else {
                dry++;
            }

        }
    }
}
