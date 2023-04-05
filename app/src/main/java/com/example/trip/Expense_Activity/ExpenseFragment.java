package com.example.trip.Expense_Activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.trip.Database.Expense;
import com.example.trip.Database.DatabaseHandler;
import com.example.trip.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


public class ExpenseFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_expense, container, false);

        DatabaseHandler db = new DatabaseHandler(view.getContext());
        List<Expense> list = db.getAllExpenses();
        ListView listView = view.findViewById(R.id.list_expense);
        ExpenseAdapter adapter = new ExpenseAdapter(view.getContext(), list);
        listView.setAdapter(adapter);

        FloatingActionButton btnAdd = view.findViewById(R.id.btn_add_expense);
        btnAdd.setOnClickListener(view1 -> {
            if (db.getAllTrips().isEmpty()){
                Toast.makeText(this.getContext(), "No trip found", Toast.LENGTH_SHORT).show();
                return;
            }
            Add dialog = new Add(view.getContext());
            dialog.setOnDismissListener(dialogInterface -> {
                list.clear();
                list.addAll(db.getAllExpenses());
                adapter.notifyDataSetChanged();
            });
            dialog.show();
        });

        return view;
    }
}