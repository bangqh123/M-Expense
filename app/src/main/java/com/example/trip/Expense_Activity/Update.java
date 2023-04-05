package com.example.trip.Expense_Activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.trip.Database.Expense;
import com.example.trip.Database.Trip;
import com.example.trip.Database.DatabaseHandler;
import com.example.trip.Database.DateHelper;
import com.example.trip.R;
import com.example.trip.Trip_Activity.TripArrayAdapter;

import java.util.List;

public class Update extends Dialog {

    private final Expense expense;

    private EditText edtType, edtDate, edtComment, edtAmount;
    private Spinner spinnerTrip;
    private int y, m, d;

    private final DatePickerDialog dialog = new DatePickerDialog(this.getContext());

    public Update(@NonNull Context context, Expense expense) {
        super(context);
        this.expense = expense;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.update_expense);

        edtType = findViewById(R.id.edt_type_expense_update);
        edtDate = findViewById(R.id.edt_date_expense_update);
        edtAmount = findViewById(R.id.edt_amount_expense_update);
        edtComment = findViewById(R.id.edt_comment_expense_update);
        spinnerTrip = findViewById(R.id.spinner_trip_expense_update);
        Button btnCancel = findViewById(R.id.btn_cancel_expense_update);
        Button btnConfirm = findViewById(R.id.btn_confirm_expense_update);
        Button btnPickDate = findViewById(R.id.btn_pick_date_expense_update);

        List<Integer> dmy = DateHelper.getDmyFromString(expense.getDate());
        d = dmy.get(0);
        m = dmy.get(1);
        y = dmy.get(2);
        edtDate.setText(DateHelper.getStringFromDmy(d, m, y));

        DatabaseHandler db = new DatabaseHandler(this.getContext());
        List<Trip> listTrip = db.getAllTrips();
        TripArrayAdapter adapter = new TripArrayAdapter(this.getContext(), R.layout.trip_spinner_item_view, R.id.txt_name_trip_spinner_item, listTrip);
        spinnerTrip.setAdapter(adapter);
        spinnerTrip.setSelection(listTrip.indexOf(db.getTripById(expense.getTripId())));
        Log.d("aaa", db.getTripById(expense.getTripId()).getName());
        Log.d("aaa", String.valueOf(listTrip.indexOf(db.getTripById(expense.getTripId()))));

        edtType.setText(expense.getType());
        edtAmount.setText(String.valueOf(expense.getAmount()));
        edtComment.setText(expense.getComment());

        btnCancel.setOnClickListener(view -> dismiss());

        btnConfirm.setOnClickListener(view -> {
            int id = expense.getId();
            if (edtType.getText().toString().length() == 0){
                Toast.makeText(this.getContext(), "Type is required", Toast.LENGTH_SHORT).show();
                return;
            }
            if (edtAmount.getText().toString().length() == 0){
                Toast.makeText(this.getContext(), "Amount is required", Toast.LENGTH_SHORT).show();
                return;
            }
            final String type = edtType.getText().toString();
            final String date = DateHelper.getStringFromDmy(d, m, y);
            final int amount = Integer.parseInt(edtAmount.getText().toString());
            final String comment = edtComment.getText().toString();
            final int tripId = ((Trip) spinnerTrip.getSelectedItem()).getId();
            Expense expenseUpdate = new Expense(id,type, date, amount, comment, tripId);
            db.updateExpense(expenseUpdate);
            Toast.makeText(this.getContext(), "Update expense successfully", Toast.LENGTH_SHORT).show();
            this.dismiss();
        });

        btnPickDate.setOnClickListener(view -> {
            dialog.setOnDateSetListener((datePicker, i, i1, i2) -> {
                y = i;
                m = i1 + 1;
                d = i2;
                edtDate.setText(DateHelper.getStringFromDmy(d, m, y));
            });
            dialog.show();
        });
    }
}
