package com.example.trip.Trip_Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.trip.Database.Trip;
import com.example.trip.Database.DatabaseHandler;
import com.example.trip.R;

import java.util.List;

public class TripAdapter extends BaseAdapter {

    private final Context context;
    private final List<Trip> list;

    public TripAdapter(Context context, List<Trip> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.trip_view, parent, false);

            holder.txtName = convertView.findViewById(R.id.txt_name_trip);
            holder.btnEdit = convertView.findViewById(R.id.btn_edit_trip);
            holder.btnDelete = convertView.findViewById(R.id.btn_delete_trip);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Trip trip = list.get(position);

        holder.txtName.setText(trip.getName());

        DatabaseHandler db = new DatabaseHandler(context);
        holder.btnDelete.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Delete trip").setMessage("You want delete trip " + trip.getName() + "?");
            builder.setCancelable(true);
            builder.setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss());
            builder.setPositiveButton("Confirm", (dialogInterface, i) -> {
                if (!db.deleteTrip(trip)){
                    return;
                }
                list.remove(position);
                notifyDataSetChanged();
                Toast.makeText(context, "Deleted trip successfully", Toast.LENGTH_SHORT).show();
                dialogInterface.dismiss();
            });
            builder.create().show();
        });

        holder.btnEdit.setOnClickListener(view -> {
            Update dialog = new Update(view.getContext(), trip);
            dialog.setOnDismissListener(dialogInterface -> {
                list.clear();
                list.addAll(db.getAllTrips());
                notifyDataSetChanged();
            });
            dialog.show();
        });

        convertView.setOnClickListener(view -> {
            Intent intent = new Intent(context, Detail.class);
            intent.putExtra("trip", trip);
            context.startActivity(intent);
        });

        return convertView;
    }

    private static class ViewHolder {
        TextView txtName;
        ImageButton btnEdit, btnDelete;
    }
}
