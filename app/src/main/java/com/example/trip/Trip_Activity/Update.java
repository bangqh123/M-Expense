package com.example.trip.Trip_Activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.trip.Database.Trip;
import com.example.trip.Database.DatabaseHandler;
import com.example.trip.Database.DateHelper;
import com.example.trip.R;

import java.util.ArrayList;
import java.util.List;

public class Update extends Dialog {

    private final Trip trip;

    private EditText edtName, edtDate, edtDesc;
    private Spinner spinnerRequire;
    private int y, m, d;

    DatePickerDialog dialog = new DatePickerDialog(this.getContext());

    public Update(@NonNull Context context, Trip trip) {
        super(context);
        this.trip = trip;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.update_trip);

        edtName = findViewById(R.id.edt_name_trip_update);
        edtDate = findViewById(R.id.edt_date_trip_update);
        edtDesc = findViewById(R.id.edt_desc_trip_update);
        spinnerRequire = findViewById(R.id.spinner_require_trip_update);
        Button btnCancel = findViewById(R.id.btn_cancel_trip_update);
        Button btnConfirm = findViewById(R.id.btn_confirm_trip_update);
        Button btnPickDate = findViewById(R.id.btn_pick_date_trip_update);

        List<Integer> dmy = DateHelper.getDmyFromString(trip.getDate());
        d = dmy.get(0);
        m = dmy.get(1);
        y = dmy.get(2);
        edtDate.setText(DateHelper.getStringFromDmy(d, m, y));

        // Spinner require
        List<String> listRequire = new ArrayList<>();
        listRequire.add("Yes");
        listRequire.add("No");
        ArrayAdapter<String> spinnerAdapter
                = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_spinner_item, listRequire);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRequire.setAdapter(spinnerAdapter);
        spinnerRequire.setSelection(listRequire.indexOf(trip.getRequire()));

        edtName.setText(trip.getName());
        edtDate.setText(trip.getDate());
        edtDesc.setText(trip.getDesc());

        btnCancel.setOnClickListener(view -> dismiss());

        btnConfirm.setOnClickListener(view -> {
            int id = trip.getId();
            String name = edtName.getText().toString();
            String date = DateHelper.getStringFromDmy(d, m, y);
            String require = spinnerRequire.getSelectedItem().toString();
            String desc = edtDesc.getText().toString();
            if (name.length() == 0){
                Toast.makeText(this.getContext(), "Name is required", Toast.LENGTH_SHORT).show();
                return;
            }
            Trip tripUpdate = new Trip(id, name, date, require, desc);
            DatabaseHandler db = new DatabaseHandler(this.getContext());
            db.updateTrip(tripUpdate);
            Toast.makeText(this.getContext(), "Update trip successfully", Toast.LENGTH_SHORT).show();
            this.dismiss();
        });

        btnPickDate.setOnClickListener(view -> {
            dialog.setOnDateSetListener((datePicker, i, i1, i2) -> {
                y = i;
                m = i1 + 1;
                d = i2;
                edtDate.setText(d + "/" + m + "/" + y);
            });
            dialog.show();
        });
    }


}
