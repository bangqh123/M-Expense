package com.example.trip;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trip.Database.User;
import com.example.trip.Database.DatabaseHandler;

public class Register extends AppCompatActivity {

    private EditText edtName, edtUsername, edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        edtName=findViewById(R.id.edt_name_signup);
        edtUsername=findViewById(R.id.edt_username_signup);
        edtPassword=findViewById(R.id.edt_password_signup);
        Button btnSignUp=findViewById(R.id.btn_signup);
        Button btnLoginWithYourAccount=findViewById(R.id.btn_login_with_your_account);

        DatabaseHandler db = new DatabaseHandler(this);

        btnSignUp.setOnClickListener(view -> {
            if (edtName.getText().length() == 0 || edtUsername.getText().toString().length() == 0 || edtPassword.getText().toString().length() == 0){
                Toast.makeText(this, "Incomplete", Toast.LENGTH_SHORT).show();
            }
            User user = new User(edtName.getText().toString(), edtUsername.getText().toString(), edtPassword.getText().toString());
            if (db.addUser(user)) {
                Toast.makeText(this, "Sign up successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        btnLoginWithYourAccount.setOnClickListener(view -> finish());
    }
}