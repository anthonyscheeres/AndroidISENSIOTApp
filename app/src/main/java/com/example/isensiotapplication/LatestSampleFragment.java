package com.example.isensiotapplication;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.isensiotapplication.service.models.Data;
import com.example.isensiotapplication.service.models.Interval;
import com.example.isensiotapplication.service.models.IntervalCollection;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;


public class LatestSampleFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_latest_sample, container, false);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Read from the database
        Data.myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                IntervalCollection intervals = dataSnapshot.getValue(IntervalCollection.class);



                Interval latestSample = null;
                //get latest sample from collection
                if ( intervals!=null) {
                    Data.intervals = intervals.intervals;
                    latestSample = intervals.intervals.get(-1);
                }
                    updateTextForPlant(latestSample);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });
    }
    public void updateTextForPlant(Interval latestSample){
        View view = getView();
        TextView text = (TextView) view.findViewById(R.id.textView2);
        TextView text2 = (TextView) view.findViewById(R.id.textView3);
        TextView text3 = (TextView) view.findViewById(R.id.textView4);
        if (latestSample==null) {
            text.setText("Geen plant data");
            return;
        }
        if(latestSample.moistureSensorIsMoist){
            text.setText("De plant is goed bewaterd");
        }
        else  text.setText("De plant is niet goed bewaterd");
        if (latestSample.laserLengthReached){
            text2.setText("De plant is langer geworden dan: "+latestSample.laserLengthInMm + " milimeter");
        }
        else  text2.setText("De plant is nog niet langer geworden dan: "+latestSample.laserLengthInMm + " milimeter");
        text3.setText("De plant krijgt genoeg licht");
    }
}