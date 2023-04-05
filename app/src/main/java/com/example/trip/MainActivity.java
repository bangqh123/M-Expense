package com.example.trip;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.trip.Database.DatabaseHandler;
import com.example.trip.Expense_Activity.ExpenseFragment;
import com.example.trip.Export_Activity.ExportFragment;
import com.example.trip.Trip_Activity.TripFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawer;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setHomeAsUpIndicator(R.drawable.menu);
        }

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        SharedPreferences pref = getSharedPreferences("PREF", MODE_PRIVATE);

        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new TripFragment()).commit();


        navigationView.setCheckedItem(R.id.nav_trip);
        navigationView.setNavigationItemSelectedListener(
                menuItem -> {
                    drawer.closeDrawers();
                    switch (menuItem.getItemId()) {
                        case R.id.nav_trip:
                            if (actionbar != null) {
                                actionbar.setTitle("Trip Management");
                            }
                            menuItem.setChecked(true);
                            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new TripFragment()).commit();
                            break;
                        case R.id.nav_expense:
                            if (actionbar != null) {
                                actionbar.setTitle("Expense Management");
                            }
                            menuItem.setChecked(true);
                            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new ExpenseFragment()).commit();
                            break;
                        case R.id.nav_export:
                            if (actionbar != null) {
                                actionbar.setTitle("Export Trip Data");
                            }
                            menuItem.setChecked(true);
                            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new ExportFragment()).commit();
                            break;
                        case R.id.nav_clear:
                            AlertDialog.Builder builderClear = new AlertDialog.Builder(this);
                            builderClear.setTitle("Clear data").setMessage("You want clear data?");
                            builderClear.setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss());
                            builderClear.setPositiveButton("OK", (dialogInterface, i) -> {
                                pref.edit().putBoolean("isLoggedIn", false).apply();
                                pref.edit().remove("name").apply();
                                DatabaseHandler db = new DatabaseHandler(this);
                                db.clearDatabases();
                                dialogInterface.dismiss();
                                Intent intent = new Intent(this, Login.class);
                                startActivity(intent);
                                finish();
                                Toast.makeText(this, "Clear data successfully", Toast.LENGTH_SHORT).show();
                            });
                            builderClear.create().show();
                            break;
                        case R.id.nav_logout:
                            AlertDialog.Builder builder = new AlertDialog.Builder(this);
                            builder.setTitle("Log out").setMessage("You want log out?");
                            builder.setNegativeButton("Cancel", (dialogInterface, i) -> dialogInterface.dismiss());
                            builder.setPositiveButton("OK", (dialogInterface, i) -> {
                                pref.edit().putBoolean("isLoggedIn", false).apply();
                                pref.edit().remove("name").apply();
                                dialogInterface.dismiss();
                                Intent intent = new Intent(this, Login.class);
                                startActivity(intent);
                                finish();
                                Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show();
                            });
                            builder.create().show();
                            break;
                    }

                    return true;
                });
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawer.openDrawer(GravityCompat.START);
                break;
            case R.id.menu_search:
                Intent intent = new Intent(this, Search.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return true;
    }
}