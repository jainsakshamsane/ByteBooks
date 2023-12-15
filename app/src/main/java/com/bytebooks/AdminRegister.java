package com.bytebooks;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bytebooks.DBhandler.DBHandler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdminRegister extends AppCompatActivity {

    EditText name;
    EditText mobile;
    EditText email3;
    EditText password3;
    EditText address;
    Button registerdata;
    TextView olduser;
    DBHandler dbhandler;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_register);
        name = findViewById(R.id.nameadmin);
        mobile = findViewById(R.id.mobileadmin);
        email3 = findViewById(R.id.emailinputadmin);
        password3 = findViewById(R.id.passwordinputadmin);
        address = findViewById(R.id.addressadmin);
        registerdata = findViewById(R.id.registeruserbtn);
        olduser = findViewById(R.id.olduser);
        dbhandler = new DBHandler(AdminRegister.this);

        EditText name = findViewById(R.id.nameadmin);
        EditText phone = findViewById(R.id.mobileadmin);

        String namePattern = "^[A-Za-z]+$";
        String phonePattern = "^[0-9]+$";

        String nameText = name.getText().toString();
        String phoneText = phone.getText().toString();

        Pattern namePatternCompiled = Pattern.compile(namePattern);
        Matcher nameMatcher = namePatternCompiled.matcher(nameText);

        Pattern phonePatternCompiled = Pattern.compile(phonePattern);
        Matcher phoneMatcher = phonePatternCompiled.matcher(phoneText);

        if (!nameMatcher.matches()) {
            name.setError("Please enter a valid name with only alphabets.");
        }

        if (!phoneMatcher.matches()) {
            phone.setError("Please enter a valid phone number with only numbers.");
        }

        registerdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEmpty(name) || isEmpty(mobile) || isEmpty(email3) ||
                        isEmpty(password3) || isEmpty(address)) {
                    Toast.makeText(AdminRegister.this, "Please enter all details", Toast.LENGTH_LONG).show();
                } else {

                    long times = System.currentTimeMillis();
                    SimpleDateFormat dates = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
                    String formats = dates.format(new Date(times));

                    dbhandler.registeradmin(
                            name.getText().toString(),
                            mobile.getText().toString(),
                            email3.getText().toString(),
                            password3.getText().toString(),
                            address.getText().toString(),
                            formats
                    );

                    name.setText("");
                    mobile.setText("");
                    email3.setText("");
                    password3.setText("");
                    address.setText("");

                    Toast.makeText(AdminRegister.this, "Data submitted", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(AdminRegister.this, Adminlogin.class);
                    startActivity(intent);
                }
                }
            });

        olduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminRegister.this, Adminlogin.class);
                startActivity(intent);
            }
        });
    }
    private boolean isEmpty(EditText editText) {
        return editText.getText().toString().trim().isEmpty();
    }
}

