package com.example.isensiotapplication.service.models;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class Data {

        public static List<Interval> intervals;
        private static FirebaseDatabase database = FirebaseDatabase.getInstance();
public static DatabaseReference myRef = database.getReference("Anthony");
}
