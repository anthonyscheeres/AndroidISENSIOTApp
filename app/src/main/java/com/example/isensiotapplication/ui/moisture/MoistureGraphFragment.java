package com.example.isensiotapplication.ui.moisture;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.isensiotapplication.R;
import com.example.isensiotapplication.controller.MoistureGraphConverter;
import com.example.isensiotapplication.service.models.Data;
import com.example.isensiotapplication.service.models.Interval;
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

public class MoistureGraphFragment extends Fragment {
    final int Y = 100;
    public int dry = 0;
    public int wet = 0;



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

    private List<Interval> fetchData(DataSnapshot dataSnapshot)
    {
        List<Interval> collection = new ArrayList<Interval>();

        for(DataSnapshot postSnapShot:dataSnapshot.getChildren())
        {
            Interval day= postSnapShot.getValue(Interval.class);
            collection.add(day);
        }
        return collection;
    }

    public void makeTheBarChart() {

        List<Interval> collection = new ArrayList<Interval>();


        Data.myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                List<Interval> collection= fetchData(dataSnapshot);

                updateGraph(collection);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });




    }

    private void updateGraph(List<Interval> intervals) {

        View view = getView();
        BarChart chart = view.findViewById(R.id.barchart);
        chart.getDescription().setEnabled(false);
        List<BarEntry> entries = new ArrayList<>();

        String desciption = "Geen plant data";


        chart.setNoDataText("Geen data");
        TextView text = view.findViewById(R.id.textView7);
        int counter = 0;
        if (intervals != null) {
            MoistureGraphConverter converter = new MoistureGraphConverter(intervals, Y);
            dry = converter.dry;
            wet = converter.wet;
            desciption = "De plant was " + wet + " keer goed bewaterd, " + dry + " niet goed bewaterd";
            entries.add(new BarEntry(1f, converter.dry));
            entries.add(new BarEntry(2f, converter.wet));
            counter++;
        }
        BarDataSet set = new BarDataSet(entries, desciption);
        BarData data = new BarData(set);
        chart.setData(data);
        chart.setDescription(new Description());
        chart.animateXY(Y, 2);
        chart.invalidate();
    }
}