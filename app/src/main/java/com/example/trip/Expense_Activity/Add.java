package com.example.trip.Expense_Activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.trip.Database.Expense;
import com.example.trip.Database.Trip;
import com.example.trip.Database.DatabaseHandler;
import com.example.trip.R;
import com.example.trip.Trip_Activity.TripArrayAdapter;

import java.util.ArrayList;
import java.util.List;

public class Add extends Dialog {

    private EditText edtType, edtDate, edtComment, edtAmount;
    private Spinner spinnerTrip;
    private int y, m, d;

    private final DatePickerDialog dialog = new DatePickerDialog(this.getContext());

    public Add(@NonNull Context context) {
        super(context);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.add_expense);

        edtType = findViewById(R.id.edt_type_expense_add);
        edtDate = findViewById(R.id.edt_date_expense_add);
        edtAmount = findViewById(R.id.edt_amount_expense_add);
        edtComment = findViewById(R.id.edt_comment_expense_add);
        spinnerTrip = findViewById(R.id.spinner_trip_expense_add);
        Button btnCancel = findViewById(R.id.btn_cancel_expense_add);
        Button btnConfirm = findViewById(R.id.btn_confirm_expense_add);
        Button btnPickDate = findViewById(R.id.btn_pick_date_expense_add);

        d = dialog.getDatePicker().getDayOfMonth();
        m = dialog.getDatePicker().getMonth() + 1;
        y = dialog.getDatePicker().getYear();
        edtDate.setText(getStringFromDmy(d, m, y));

        // Spinner trip
        DatabaseHandler db = new DatabaseHandler(this.getContext());
        List<Trip> listTrip = db.getAllTrips();
        TripArrayAdapter adapter = new TripArrayAdapter(this.getContext(), R.layout.trip_spinner_item_view, R.id.txt_name_trip_spinner_item, listTrip);
        spinnerTrip.setAdapter(adapter);

        btnCancel.setOnClickListener(view -> dismiss());
        btnConfirm.setOnClickListener(view -> {
            if (edtType.getText().toString().length() == 0){
                Toast.makeText(this.getContext(), "Type is required", Toast.LENGTH_SHORT).show();
                return;
            }
            if (edtAmount.getText().toString().length() == 0){
                Toast.makeText(this.getContext(), "Amount is required", Toast.LENGTH_SHORT).show();
                return;
            }
            final String type = edtType.getText().toString();
            final String date = getStringFromDmy(d, m, y);
            final int amount = Integer.parseInt(edtAmount.getText().toString());
            final String comment = edtComment.getText().toString();
            final int tripId = ((Trip) spinnerTrip.getSelectedItem()).getId();
            Expense expense = new Expense(type, date, amount, comment, tripId);
            db.addExpense(expense);
            Toast.makeText(this.getContext(), "Add new expense successfully", Toast.LENGTH_SHORT).show();
            this.dismiss();
        });
        btnPickDate.setOnClickListener(view -> {
            dialog.setOnDateSetListener((datePicker, i, i1, i2) -> {
                y = i;
                m = i1 + 1;
                d = i2;
                edtDate.setText(getStringFromDmy(d, m, y));
            });
            dialog.show();
        });
    }

    public static String getStringFromDmy(int d, int m, int y) {
        return d + "/" + m + "/" + y;
    }

    public static List<Integer> getDmyFromString(String date) {
        List<Integer> list = new ArrayList<>();
        int index1 = date.indexOf("/");
        int d = Integer.parseInt(date.substring(0, index1));
        int index2 = date.indexOf("/", 3);
        int m = Integer.parseInt(date.substring(index1 + 1, index2));
        int y = Integer.parseInt(date.substring(index2 + 1));
        list.add(d);
        list.add(m);
        list.add(y);
        return list;
    }
}
