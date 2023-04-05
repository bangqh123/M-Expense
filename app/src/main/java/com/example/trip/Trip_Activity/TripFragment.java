package com.example.trip.Trip_Activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.trip.Database.Trip;
import com.example.trip.Database.DatabaseHandler;
import com.example.trip.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


public class TripFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trip, container, false);

        DatabaseHandler db = new DatabaseHandler(view.getContext());
        List<Trip> list = db.getAllTrips();

        ListView listView = view.findViewById(R.id.list_trip);
        TripAdapter adapter = new TripAdapter(view.getContext(), list);
        listView.setAdapter(adapter);

        FloatingActionButton btnAdd = view.findViewById(R.id.btn_add_trip);
        btnAdd.setOnClickListener(view1 -> {
            Add dialog = new Add(view.getContext());
            dialog.setOnDismissListener(dialogInterface -> {
                list.clear();
                list.addAll(db.getAllTrips());
                adapter.notifyDataSetChanged();
            });
            dialog.show();
        });

        return view;
    }
}