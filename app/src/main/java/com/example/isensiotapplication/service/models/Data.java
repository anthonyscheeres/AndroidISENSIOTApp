package com.example.isensiotapplication.service.models;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class Data {

    public static List<Interval> intervals;
    private static FirebaseDatabase database = FirebaseDatabase.getInstance();
    public static DatabaseReference myRef = database.getReference("21232f297a57a5a743894a0e4a801fc3e033ba9cd693f9e833c72e9055d2c491");
}
