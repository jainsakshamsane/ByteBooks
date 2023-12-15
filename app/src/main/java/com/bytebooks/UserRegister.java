package com.bytebooks;

import static android.provider.SyncStateContract.Helpers.insert;

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
import com.bytebooks.UserDao;
import com.bytebooks.UserDatabase;
 public class UserRegister extends AppCompatActivity {

    EditText name,phone;
    EditText email4;
    EditText password4;
    EditText age;
    EditText address;
    Button registerdata;
    TextView olduser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_register);
        name = findViewById(R.id.nameuser);
        phone = findViewById(R.id.mobileuser);
        email4 = findViewById(R.id.emailinputuser);
        password4 = findViewById(R.id.passwordinputuser);
        age = findViewById(R.id.ageuser);
        address = findViewById(R.id.addressuser);
        registerdata = findViewById(R.id.registeruseruserbtn);
        olduser = findViewById(R.id.olduseruser);

        registerdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEmpty(name) || isEmpty(phone) || isEmpty(email4) ||
                        isEmpty(password4) || isEmpty(age)|| isEmpty(address)) {
                    Toast.makeText(UserRegister.this, "Please enter all details", Toast.LENGTH_LONG).show();
                } else {

                    String names = name.getText().toString();
                    String mobiles = phone.getText().toString();
                    String emails = email4.getText().toString();
                    String passwords = password4.getText().toString();
                    String ages = age.getText().toString();
                    String addresses = address.getText().toString();

                    UserEntity newUser = new UserEntity(names,mobiles,emails,passwords,ages,addresses);
                    insert(newUser);

                    Toast.makeText(UserRegister.this, "User registered successfully", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(UserRegister.this, Userlogin.class);
                    startActivity(intent);
                }
            }
        });

        olduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserRegister.this, Userlogin.class);
                startActivity(intent);
            }
        });
    }
    private boolean isEmpty(EditText editText) {
        return editText.getText().toString().trim().isEmpty();
    }

    private void insert(UserEntity user) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                UserDao userDao = com.bytebooks.UserDatabase.getInstance(UserRegister.this).userDao();

                userDao.insert(user);
            }
        }).start();
    }
}
