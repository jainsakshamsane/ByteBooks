package com.bytebooks;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bytebooks.DBhandler.DBHandler;
import com.bytebooks.ui.notifications.NotificationsFragment;

public class PasswordChange extends AppCompatActivity {

    EditText currentpassword, newpassword;
    ImageView backbutton;
    Button submitpassword;
    int userid;

    DBHandler dbHandler;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.changepassword);

        dbHandler = new DBHandler(this);

        currentpassword = findViewById(R.id.currentpassword);
        newpassword = findViewById(R.id.newpassword);
        backbutton = findViewById(R.id.backbtnpassword);
        submitpassword = findViewById(R.id.submitpasswordbtn);

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PasswordChange.this, MainActivity.class);
                startActivity(intent);
            }
        });

        submitpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("my_preferences", MODE_PRIVATE);
                userid = sharedPreferences.getInt("userid", -1);

                String oldwaalapassword = currentpassword.getText().toString();
                String newwaalapassword = newpassword.getText().toString();


                UserEntity user = new UserEntity();
                user.setUserid(userid);
                user.setPassword(newwaalapassword);
                psword(user,oldwaalapassword);


            }
        });
    }

    private void psword(UserEntity paswords,String old) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                UserDao passwordkaDao = UserDatabase.getInstance(PasswordChange.this).userDao();

                UserEntity users =passwordkaDao.getcontact(paswords.getUserid());
                if(old.equals(users.getPassword())){
                    users.setPassword(paswords.getPassword());
                    passwordkaDao.updatePassword(users);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(PasswordChange.this, "Password updated successfully", Toast.LENGTH_SHORT).show();
                        }
                    });

                }else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                    Toast.makeText(PasswordChange.this,"Please enter old password correctly", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        }).start();
    }
}
