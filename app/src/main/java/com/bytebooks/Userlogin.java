package com.bytebooks;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

public class Userlogin extends AppCompatActivity {

    EditText emailLogin, passwordLogin;
    Button loginBtn;
    TextView newUser;
    CheckBox savePasswordCheckboxx;

    UserDatabase userDatabase;
    UserDao userDao;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_login);

        emailLogin = findViewById(R.id.emailuseruserlogin);
        passwordLogin = findViewById(R.id.passworduseruserlogin);
        loginBtn = findViewById(R.id.loginuseruserbtn);
        newUser = findViewById(R.id.registeruseruserbtn);
        savePasswordCheckboxx = findViewById(R.id.checkBoxuser);

        userDatabase = Room.databaseBuilder(getApplicationContext(), UserDatabase.class, "user_database").build();
        userDao = userDatabase.userDao();

//        sharedPreferences = getSharedPreferences("userId", MODE_PRIVATE);
//        editor = sharedPreferences.edit();

//        emailLogin.setText(sharedPreferences.getString("email", ""));
//        passwordLogin.setText(sharedPreferences.getString("password", ""));

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Logging in...");
        progressDialog.setCancelable(false);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailLogin.getText().toString();
                String password = passwordLogin.getText().toString();

                Userlogging user = new Userlogging(email, password);
                login(user);

                if (user != null) {
                    progressDialog.show();

                    // Simulate a delay to show the loading bar
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();



                            Intent intent = new Intent(Userlogin.this, MainActivity.class);
                            startActivity(intent);

                            // Save login details if needed
                            editor.putString("email", email);
                            editor.putString("password", password);
                            editor.apply();

                            // Navigate to the next screen or perform further actions
                        }
                    }, 2000); // Simulate a 2-second delay, replace with your actual login logic
                } else {
                    Toast.makeText(Userlogin.this, "Wrong email or password. Try Again", Toast.LENGTH_LONG).show();
                }
            }
        });

        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Userlogin.this, UserRegister.class);
                startActivity(intent);
            }
        });
    }

    private void login(Userlogging user) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                UserDao userDao = com.bytebooks.UserDatabase.getInstance(Userlogin.this).userDao();

               UserEntity users = userDao.login(user.getEmail(), user.getPassword());

                SharedPreferences sharedPreferences = getSharedPreferences("my_preferences", MODE_PRIVATE);
                SharedPreferences.Editor myedit = sharedPreferences.edit();
                myedit.putInt("userid", users.getUserid());
                myedit.commit();
                String id = String.valueOf(users.getUserid());
            }
        }).start();
    }
}
