package com.bytebooks;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bytebooks.DBhandler.DBHandler;

public class Adminlogin extends AppCompatActivity {

    Button loginadmin;
    EditText email1;
    EditText password1;
    String email2;
    String password2;
    Button loginregister;
    CheckBox savePasswordCheckbox;

    DBHandler dbHandler;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    ProgressDialog progressDialog;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_login);
        loginadmin = findViewById(R.id.loginuserbtn);
        loginregister = findViewById(R.id.registeruserbtn);
        email1 = findViewById(R.id.emailuserlogin);
        password1 = findViewById(R.id.passworduserlogin);
        savePasswordCheckbox = findViewById(R.id.checkBox);

        dbHandler = new DBHandler(Adminlogin.this);

        sharedPreferences = getSharedPreferences("login_data", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        email1.setText(sharedPreferences.getString("email", ""));
        password1.setText(sharedPreferences.getString("password", ""));
        savePasswordCheckbox.setChecked(!email1.getText().toString().isEmpty()
                && !password1.getText().toString().isEmpty());

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Logging in...");
        progressDialog.setCancelable(false);

        loginadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email2 = email1.getText().toString();
                password2 = password1.getText().toString();
                if (email2.isEmpty() || password2.isEmpty()) {
                    Toast.makeText(Adminlogin.this, "Please Enter Values", Toast.LENGTH_LONG).show();
                } else {
                    progressDialog.show();

                    // Simulate a delay to show the loading bar
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            String data = dbHandler.loginadmin(email2, password2);
                            progressDialog.dismiss();

                            if (data.isEmpty()) {
                                Toast.makeText(Adminlogin.this, "Wrong email or password. Try Again", Toast.LENGTH_LONG).show();
                            } else {
                                SharedPreferences sharedPreferences = getSharedPreferences("my_preferences", MODE_PRIVATE);
                                SharedPreferences.Editor myedit = sharedPreferences.edit();
                                myedit.putString("admin_id", data);
                                myedit.apply();

                                email1.setText("");
                                password1.setText("");

                                if (savePasswordCheckbox.isChecked()) {
                                    editor.putString("email", email2);
                                    editor.putString("password", password2);
                                    editor.apply();

                                    Toast.makeText(Adminlogin.this, "Password saved", Toast.LENGTH_SHORT).show();
                                }
                                Toast.makeText(Adminlogin.this, "Successfully login", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(Adminlogin.this, AddBooks.class);
                                startActivity(intent);
                            }
                        }
                    }, 2000); // Simulate a 2-second delay, replace with your actual login logic
                }
            }
        });

        loginregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Adminlogin.this, AdminRegister.class);
                startActivity(intent);
            }
        });
    }

    private boolean isEmpty(EditText editText) {
        return editText.getText().toString().trim().isEmpty();
    }
}
