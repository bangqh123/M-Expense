package com.example.trip.Export_Activity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.trip.Database.Trip;
import com.example.trip.Database.DatabaseHandler;
import com.example.trip.R;

import java.util.List;

public class ExportAdapter extends BaseAdapter {

    private final Context context;
    private final List<Trip> list;

    public ExportAdapter(Context context, List<Trip> list) {
        this.context = context;
        this.list = list;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.export_view, parent, false);

            holder.txtName = convertView.findViewById(R.id.txt_export_trip);

            convertView.setTag(holder);
        } else {
            holder = (ExportAdapter.ViewHolder) convertView.getTag();
        }

        Trip trip = list.get(position);

        holder.txtName.setText(trip.getName());

        DatabaseHandler db = new DatabaseHandler(context);

        convertView.setOnClickListener(view -> {
            Intent intent = new Intent(context, Detail.class);
            intent.putExtra("trip", trip);
            context.startActivity(intent);
        });

        return convertView;
    }

    private static class ViewHolder {
        TextView txtName;
    }
}
