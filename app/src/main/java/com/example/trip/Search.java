package com.example.trip;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.trip.Database.Trip;
import com.example.trip.Database.DatabaseHandler;
import com.example.trip.Trip_Activity.TripAdapter;

import java.util.List;

public class Search extends AppCompatActivity {

    private ListView lstView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        Toolbar toolbar = findViewById(R.id.toolbar_search);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setHomeAsUpIndicator(R.drawable.ic_baseline_arrow_back_24);
        }

        EditText edtQuery = findViewById(R.id.edt_query_search);
        lstView = findViewById(R.id.lst_trip_search);

        edtQuery.requestFocus();

        DatabaseHandler db = new DatabaseHandler(this);

        edtQuery.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_SEARCH) {
                String query = textView.getText().toString();
                if (query.length() == 0){
                    return true;
                }
                List<Trip> list = db.search(query);
                if (list.isEmpty()){
                    Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
                }
                TripAdapter adapter = new TripAdapter(this, list);
                lstView.setAdapter(adapter);
                return true;
            }
            return false;
        });
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