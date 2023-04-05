package com.example.trip.Trip_Activity;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.trip.Database.Trip;
import com.example.trip.R;

import java.util.List;

public class TripArrayAdapter extends ArrayAdapter<Trip> {

    private final List<Trip> list;

    public TripArrayAdapter(@NonNull Context context, int resource, int textViewResourceId, @NonNull List<Trip> objects) {
        super(context, resource, textViewResourceId, objects);
        list = objects;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public Trip getItem(int position) {
        return list.get(position);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v =  super.getView(position, convertView, parent);
        TextView txt = v.findViewById(R.id.txt_name_trip_spinner_item);
        txt.setTextColor(Color.BLACK);
        txt.setText(list.get(position).getName());
        return v;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.trip_spinner_item_view, parent, false);

            holder.txtName = convertView.findViewById(R.id.txt_name_trip_spinner_item);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Trip trip = getItem(position);
        if (trip != null) {
            holder.txtName.setText(trip.getName());
        }

        return convertView;
    }

    private static class ViewHolder{
        TextView txtName;
    }
}
