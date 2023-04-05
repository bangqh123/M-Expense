package com.example.trip;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trip.Database.User;
import com.example.trip.Database.DatabaseHandler;

import java.util.List;

public class Login extends AppCompatActivity {

    private EditText edtUsername, edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        edtUsername = findViewById(R.id.edt_username_login);
        edtPassword = findViewById(R.id.edt_password_login);
        Button btnLogin = findViewById(R.id.btn_login);
        Button btnCreateNewAccount = findViewById(R.id.btn_create_new_account);

        SharedPreferences pref = getSharedPreferences("PREF", MODE_PRIVATE);

        Intent intent = new Intent(this, MainActivity.class);

        if (pref.getBoolean("isLoggedIn", false)){
            startActivity(intent);
            finish();
        }

        DatabaseHandler db = new DatabaseHandler(this);

        List<User> list = db.getAllUsers();
        Log.d("aaa", String.valueOf(list.size()));

        btnLogin.setOnClickListener(view -> {
            final String username = edtUsername.getText().toString();
            final String password = edtPassword.getText().toString();
            if (username.length() == 0){
                Toast.makeText(this, "Username is required", Toast.LENGTH_SHORT).show();
                return;
            }
            if (password.length() == 0){
                Toast.makeText(this, "Password is required", Toast.LENGTH_SHORT).show();
                return;
            }
            User user = db.login(username, password);
            if (user == null){
                Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
                return;
            }

            pref.edit().putBoolean("isLoggedIn", true).apply();
            pref.edit().putString("name", user.getName()).apply();

            startActivity(intent);
            finish();
        });

        btnCreateNewAccount.setOnClickListener(view -> {
            Intent intentSignUp = new Intent(this, Register.class);
            startActivity(intentSignUp);
        });
    }
}