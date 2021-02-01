package com.example.isensiotapplication.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.isensiotapplication.R;
import com.example.isensiotapplication.service.models.Data;
import com.example.isensiotapplication.service.models.Interval;
import com.example.isensiotapplication.service.models.IntervalCollection;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


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
        makeTheBarChart();


    }

    private List<Interval> fetchData(DataSnapshot dataSnapshot) {
        List<Interval> collection = new ArrayList<Interval>();

        for (DataSnapshot postSnapShot : dataSnapshot.getChildren()) {
            Interval day = postSnapShot.getValue(Interval.class);
            collection.add(day);
        }
        return collection;
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

        isWellLid.setText("De plant krijgt " + latestSample.lightSensorLuxValue + " Lux aan licht");
    }

    public void makeTheBarChart() {

        List<Interval> collection = new ArrayList<Interval>();


        Data.myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                List<Interval> collection = fetchData(dataSnapshot);
                Interval value = null;
                if (collection.size() > 0) {
                    value = collection.get(collection.size() - 1);

                }
                updateTextForPlant(value);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}