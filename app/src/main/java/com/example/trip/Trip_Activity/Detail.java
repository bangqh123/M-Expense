package com.example.trip.Trip_Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.trip.Database.Trip;
import com.example.trip.Database.DatabaseHandler;
import com.example.trip.Expense_Activity.ExpenseAdapter;
import com.example.trip.R;

public class Detail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trip_details);

        Toolbar toolbar = findViewById(R.id.toolbar_trip_details);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        }

        TextView txtName = findViewById(R.id.txt_name_trip_details);
        TextView txtDate = findViewById(R.id.txt_date_trip_details);
        TextView txtRequire = findViewById(R.id.txt_require_trip_details);
        TextView txtDesc = findViewById(R.id.txt_desc_trip_details);
        ListView lstExpense = findViewById(R.id.list_expense_trip_details);

        Intent intent = getIntent();
        Trip trip = (Trip) intent.getSerializableExtra("trip");

        Log.d("ddd", String.valueOf(trip.getDate()));

        txtName.setText(trip.getName());
        txtDate.setText(trip.getDate());
        txtRequire.setText(trip.getRequire());
        txtDesc.setText(trip.getDesc());

        DatabaseHandler db = new DatabaseHandler(this);
        ExpenseAdapter adapter = new ExpenseAdapter(this, db.getAllExpensesByTrip(trip));
        lstExpense.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}