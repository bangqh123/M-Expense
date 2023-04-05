package com.example.trip.Trip_Activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
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

public class Add extends Dialog implements View.OnClickListener {

    private EditText edtName, edtDate, edtDesc;
    private Spinner spinnerRequire;
    private int y, m, d;

    DatePickerDialog dialog = new DatePickerDialog(this.getContext());

    public Add(@NonNull Context context) {
        super(context);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.add_trip);

        edtName = findViewById(R.id.edt_trip_name_add);
        edtDate = findViewById(R.id.edt_date_add_trip);
        edtDesc = findViewById(R.id.edt_trip_desc_add);
        spinnerRequire = findViewById(R.id.spinner_trip_require_add);
        Button btnCancel = findViewById(R.id.btn_cancel_add_trip);
        Button btnConfirm = findViewById(R.id.btn_confirm_add_trip);
        Button btnPickDate = findViewById(R.id.btn_pick_date_add_trip);

        d = dialog.getDatePicker().getDayOfMonth();
        m = dialog.getDatePicker().getMonth() + 1;
        y = dialog.getDatePicker().getYear();
        edtDate.setText(DateHelper.getStringFromDmy(d, m, y));

        // Spinner require
        List<String> listRequire = new ArrayList<>();
        listRequire.add("Yes");
        listRequire.add("No");
        ArrayAdapter<String> spinnerAdapter
                = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_spinner_item, listRequire);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRequire.setAdapter(spinnerAdapter);

        btnCancel.setOnClickListener(this);
        btnConfirm.setOnClickListener(this);
        btnPickDate.setOnClickListener(this);

    }

    @SuppressLint({"NonConstantResourceId", "SetTextI18n"})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel_add_trip:
                this.cancel();
                break;
            case R.id.btn_confirm_add_trip:
                String name = edtName.getText().toString();
                String date = DateHelper.getStringFromDmy(d, m, y);
                String require = spinnerRequire.getSelectedItem().toString();
                String desc = edtDesc.getText().toString();
                if (name.length() == 0){
                    Toast.makeText(this.getContext(), "Name is required", Toast.LENGTH_SHORT).show();
                    return;
                }
                Trip trip = new Trip(name, date, require, desc);
                DatabaseHandler db = new DatabaseHandler(this.getContext());
                db.addTrip(trip);
                Toast.makeText(this.getContext(), "Add new trip successfully", Toast.LENGTH_SHORT).show();
                this.dismiss();
                break;
            case R.id.btn_pick_date_add_trip:
                dialog.setOnDateSetListener((datePicker, i, i1, i2) -> {
                    y = i;
                    m = i1 + 1;
                    d = i2;
                    edtDate.setText(d + "/" + m + "/" + y);
                });
                dialog.show();
                break;
        }
    }
}
