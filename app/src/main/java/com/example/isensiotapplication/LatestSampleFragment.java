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





        //myRef.setValue(new Interval( new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS"), 80, true, true, 200));

        // Read from the database
        Data.myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                IntervalCollection intervals = dataSnapshot.getValue(IntervalCollection.class);


                    updateTextForPlant(intervals);



            }




            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });




    }
    public void updateTextForPlant(IntervalCollection collection){

        View view = getView();

        TextView text = (TextView) view.findViewById(R.id.textView2);
        TextView text2 = (TextView) view.findViewById(R.id.textView3);
        TextView text3 = (TextView) view.findViewById(R.id.textView4);
        if (collection==null) {
            text.setText("Geen plant data");
            return;
        }
        //get latest sample from collection
        Interval latestSample = collection.intervals.get(-1);
        if(latestSample.moistureSensorIsMoist){
            text.setText("De plant is goed bewaterd");
        }

        if (latestSample.laserLengthReached){
            text2.setText("De plant is langer geworden dan:"+latestSample.laserLengthInMm + "milimeter");
        }

text3.setText("De plant krijgt genoeg licht");


    }


}