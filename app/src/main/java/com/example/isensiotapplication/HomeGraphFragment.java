package com.example.isensiotapplication;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.isensiotapplication.service.models.Data;
import com.example.isensiotapplication.service.models.Interval;
import com.example.isensiotapplication.service.models.IntervalCollection;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeGraphFragment extends Fragment {

    final int Y = 100;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_graph_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



        makeTheBarChart();

    }
    public void makeTheBarChart() {

        // Read from the database
        Data.myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                IntervalCollection intervals = dataSnapshot.getValue(IntervalCollection.class);


                if (intervals!=null) {

                    Data.intervals = intervals.intervals;
                    updateGraph(intervals);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });


    }
    private void updateGraph(IntervalCollection intervals){
        View view = getView();
        BarChart chart = (BarChart) view.findViewById(R.id.barchart);

        List<BarEntry> entries = new ArrayList<>();
        List<Interval> collection = intervals.intervals;

        int dry = 0;

        int wet = 0;

        int counter = 0;

        if(collection !=null){
            for (Interval interval : collection) {
                if (counter >= Y) {
                    break;
                }
                if (interval.moistureSensorIsMoist) {
                    wet++;
                } else {
                    dry++;
                }

                entries.add(new BarEntry(dry, 1f));
                entries.add(new BarEntry(wet, 2f));
                counter++;
            }
        }
        else chart.setNoDataText("Geen data");
        BarDataSet set = new BarDataSet(entries, "BarDataSet");
        BarData data = new BarData(set);
        chart.setData(data);
        chart.setDescription(new Description( ));
        chart.animateXY(Y, 2);
        chart.invalidate();
    }
}