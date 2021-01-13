package com.example.isensiotapplication;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
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


                    updateGraph(Data.intervals);


    }
    private void updateGraph(List<Interval> intervals){

        View view = getView();
        BarChart chart = (BarChart) view.findViewById(R.id.barchart);
        chart.getDescription().setEnabled(false);
        List<BarEntry> entries = new ArrayList<>();
        chart.setNoDataText("Geen data");

        int dry = 0;

        int wet = 0;

        int counter = 0;


        if(intervals!=null){

            List<Interval> collection = intervals;
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
        BarDataSet set = new BarDataSet(entries, "BarDataSet");
        BarData data = new BarData(set);
        chart.setData(data);
        chart.setDescription(new Description( ));
        chart.animateXY(Y, 2);
        chart.invalidate();
    }
}