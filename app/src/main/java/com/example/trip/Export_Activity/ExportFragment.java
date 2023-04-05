package com.example.trip.Export_Activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.trip.Database.DatabaseHandler;
import com.example.trip.Database.Trip;
import com.example.trip.R;

import java.util.List;

public class ExportFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_export, container, false);

        DatabaseHandler db = new DatabaseHandler(view.getContext());
        List<Trip> list = db.getAllTrips();
        ListView listView = view.findViewById(R.id.list_export);
        ExportAdapter adapter = new ExportAdapter(view.getContext(), list);
        listView.setAdapter(adapter);

        return view;
    }

}
