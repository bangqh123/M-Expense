package com.example.trip.Expense_Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.trip.Database.Expense;
import com.example.trip.Database.DatabaseHandler;
import com.example.trip.R;

public class Detail extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expense_details);

        Toolbar toolbar = findViewById(R.id.toolbar_expense_details);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        }

        TextView txtTripName = findViewById(R.id.txt_name_trip_expense_details);
        TextView txtType = findViewById(R.id.txt_type_expense_details);
        TextView txtTime = findViewById(R.id.txt_time_expense_details);
        TextView txtAmount = findViewById(R.id.txt_amount_expense_details);
        TextView txtComment = findViewById(R.id.txt_comment_expense_details);

        Intent intent = getIntent();
        Expense expense = (Expense) intent.getSerializableExtra("expense");

        DatabaseHandler db = new DatabaseHandler(this);

        txtTripName.setText(db.getTripById(expense.getTripId()).getName());
        txtType.setText(expense.getType());
        txtTime.setText(expense.getDate());
        txtAmount.setText(expense.getAmount() + " VNĐ");
        txtComment.setText(expense.getComment());
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