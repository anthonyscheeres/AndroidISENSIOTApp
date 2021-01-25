package com.example.isensiotapplication.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.isensiotapplication.R;
import com.example.isensiotapplication.service.models.Data;
import com.example.isensiotapplication.service.models.Interval;
import com.example.isensiotapplication.service.models.IntervalCollection;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;


public class HomeLatestSampleFragment extends Fragment {

    TextView isWellMoisturised;
    TextView isLongerThanGivenAmount;
    TextView isWellLid;

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

                Interval latestSample = dataSnapshot.getValue(Interval.class);

                updateTextForPlant(latestSample);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });
    }

    public void updateTextForPlant(Interval latestSample) {
        View view = getView();
        isWellMoisturised = view.findViewById(R.id.textView2);
        isLongerThanGivenAmount = view.findViewById(R.id.textView3);
        isWellLid = view.findViewById(R.id.textView4);
        if (latestSample == null) {
            isWellMoisturised.setText("Geen plant data");
            return;
        }
        if (latestSample.moistureSensorIsMoist) {
            isWellMoisturised.setText("De plant is goed bewaterd");
        } else isWellMoisturised.setText("De plant is niet goed bewaterd");
        if (latestSample.laserLengthReached) {
            isLongerThanGivenAmount.setText("De plant is langer geworden dan: " + latestSample.laserLengthInMm + " milimeter");
        } else
            isLongerThanGivenAmount.setText("De plant is nog niet langer geworden dan: " + latestSample.laserLengthInMm + " milimeter");
        isWellLid.setText("De plant krijgt genoeg licht");
    }
}