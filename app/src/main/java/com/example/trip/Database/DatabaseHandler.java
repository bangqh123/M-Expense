package com.example.trip.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DatabaseHandler extends SQLiteOpenHelper {

    private final Context ctx;

    private static final String DATABASE_NAME = "Trip";
    private static final int DATABASE_VERSION = 1;

    private static final String USER_TABLE_NAME = "users";
    private static final String TRIP_TABLE_NAME = "trips";
    private static final String EXPENSE_TABLE_NAME = "expenses";

    private static final String KEY_ID = "id";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_NAME = "name";
    private static final String KEY_DATE = "date";
    private static final String KEY_REQUIRE = "require";
    private static final String KEY_DESC = "desc";
    private static final String KEY_TYPE = "type";
    private static final String KEY_AMOUNT = "amount";
    private static final String KEY_COMMENT = "comment";
    private static final String KEY_TRIP_ID = "trip_id";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        ctx = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String create_users_table = String.format(
                "CREATE TABLE %s(%s INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, %s TEXT, %s TEXT, %s TEXT)",
                USER_TABLE_NAME, KEY_ID, KEY_NAME, KEY_USERNAME, KEY_PASSWORD);
        sqLiteDatabase.execSQL(create_users_table);
        String create_trips_table = String.format(
                "CREATE TABLE %s(%s INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, %s TEXT, %s TEXT, %s TEXT, %s TEXT)",
                TRIP_TABLE_NAME, KEY_ID, KEY_NAME, KEY_DATE, KEY_REQUIRE, KEY_DESC);
        sqLiteDatabase.execSQL(create_trips_table);
        String create_expenses_table = String.format(
                "CREATE TABLE %s(%s INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, %s TEXT, %s TEXT, %s INTEGER, %s TEXT, %s INTEGER, FOREIGN KEY (%s) REFERENCES %s(%s) )",
                EXPENSE_TABLE_NAME, KEY_ID, KEY_TYPE, KEY_DATE, KEY_AMOUNT, KEY_COMMENT, KEY_TRIP_ID, KEY_TRIP_ID, TRIP_TABLE_NAME, KEY_ID);
        sqLiteDatabase.execSQL(create_expenses_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TRIP_TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + EXPENSE_TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void clearDatabases(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TRIP_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + EXPENSE_TABLE_NAME);
        onCreate(db);
    }

    public boolean addUser(User user) {
        List<User> list = getAllUsers();
        for (int i = 0; i < list.size(); i++){
            if (Objects.equals(list.get(i).getUsername(), user.getUsername())){
                Toast.makeText(ctx, "Username existed", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, user.getName());
        values.put(KEY_USERNAME, user.getUsername());
        values.put(KEY_PASSWORD, user.getPassword());

        db.insert(USER_TABLE_NAME, null, values);
        db.close();
        return true;
    }

    public List<User> getAllUsers(){
        List<User> list = new ArrayList<>();
        String query = "SELECT * FROM " + USER_TABLE_NAME + " ORDER BY " + KEY_ID + " DESC";
        Log.d("SQL", query);

        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            User user = new User(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
            list.add(user);
            cursor.moveToNext();
        }
        return list;
    }

    @Nullable
    public User login(String username, String password){
        List<User> list = new ArrayList<>();
        String query = "SELECT * FROM " + USER_TABLE_NAME
                + " WHERE " + KEY_USERNAME + " LIKE " + "'" + username + "'" + " AND " + KEY_PASSWORD + " LIKE " + "'" + password + "'";
        Log.d("SQL", query);
        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            User user = new User(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
            list.add(user);
            cursor.moveToNext();
        }
        if (list.isEmpty()) return null;
        return list.get(0);
    }

    public void addTrip(Trip trip) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, trip.getName());
        values.put(KEY_DATE, trip.getDate());
        values.put(KEY_REQUIRE, trip.getRequire());
        values.put(KEY_DESC, trip.getDesc());

        db.insert(TRIP_TABLE_NAME, null, values);
        db.close();
    }

    public List<Trip> getAllTrips() {
        List<Trip> list = new ArrayList<>();
        String query = "SELECT * FROM " + TRIP_TABLE_NAME + " ORDER BY " + KEY_ID + " DESC";
        Log.d("SQL", query);

        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Trip trip = new Trip(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
            list.add(trip);
            cursor.moveToNext();
        }

        list.forEach(trip -> Log.d("TRIP", trip.toString()));
        return list;
    }

    public List<Trip> search(String q) {
        List<Trip> list = new ArrayList<>();
        String query = "SELECT * FROM " + TRIP_TABLE_NAME + " WHERE " + KEY_NAME + " LIKE " + "'%" + q + "%'";
        Log.d("SQL", query);

        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Trip trip = new Trip(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
            list.add(trip);
            cursor.moveToNext();
        }
        Log.d("aa", String.valueOf(list.size()));
        return list;
    }

    public Trip getTripById(int tripId){
        List<Trip> list = new ArrayList<>();
        String query = "SELECT * FROM " + EXPENSE_TABLE_NAME + " WHERE " + KEY_ID + "=" + tripId;
        Log.d("SQL", query);

        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Trip trip = new Trip(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
            list.add(trip);
            cursor.moveToNext();
        }
        return list.get(0);
    }

    public void updateTrip(Trip trip) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, trip.getName());
        values.put(KEY_DATE, trip.getDate());
        values.put(KEY_REQUIRE, trip.getRequire());
        values.put(KEY_DESC, trip.getDesc());

        db.update(TRIP_TABLE_NAME, values, KEY_ID + " = ?", new String[] { String.valueOf(trip.getId()) });
        db.close();
    }

    public boolean deleteTrip(Trip trip) {
        if (getAllExpensesByTrip(trip).size() > 0){
            Toast.makeText(ctx, "Delete failed. Please delete expenses of trip before", Toast.LENGTH_SHORT).show();
            return false;
        }
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TRIP_TABLE_NAME, KEY_ID + " = ?", new String[] { String.valueOf(trip.getId()) });
        db.close();
        return true;
    }

    public void addExpense(Expense expense) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TYPE, expense.getType());
        values.put(KEY_DATE, expense.getDate());
        values.put(KEY_AMOUNT, expense.getAmount());
        values.put(KEY_COMMENT, expense.getComment());
        values.put(KEY_TRIP_ID, expense.getTripId());

        db.insert(EXPENSE_TABLE_NAME, null, values);
        db.close();
    }

    public List<Expense> getAllExpenses() {
        List<Expense> expenseList = new ArrayList<>();
        String query = "SELECT * FROM " + EXPENSE_TABLE_NAME + " ORDER BY " + KEY_ID + " DESC";
        Log.d("SQL", query);

        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Expense expense = new Expense(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getString(4), cursor.getInt(5));
            expenseList.add(expense);
            cursor.moveToNext();
        }
        return expenseList;
    }

    public List<Expense> getAllExpensesByTrip(Trip trip) {
        List<Expense> expenseList = new ArrayList<>();
        String query = "SELECT * FROM " + EXPENSE_TABLE_NAME + " WHERE " + KEY_TRIP_ID + "=" + trip.getId();
        Log.d("SQL", query);

        SQLiteDatabase db = this.getReadableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Expense expense = new Expense(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getString(4), cursor.getInt(5));
            expenseList.add(expense);
            cursor.moveToNext();
        }
        return expenseList;
    }

    public void updateExpense(Expense expense) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TYPE, expense.getType());
        values.put(KEY_DATE, expense.getDate());
        values.put(KEY_AMOUNT, expense.getAmount());
        values.put(KEY_COMMENT, expense.getComment());
        values.put(KEY_TRIP_ID, expense.getTripId());

        db.update(EXPENSE_TABLE_NAME, values, KEY_ID + " = ?", new String[] { String.valueOf(expense.getId()) });
        db.close();
    }

    public void deleteExpense(Expense expense) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(EXPENSE_TABLE_NAME, KEY_ID + " = ?", new String[] { String.valueOf(expense.getId()) });
        db.close();
    }

}
