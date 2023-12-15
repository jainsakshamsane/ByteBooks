package com.bytebooks;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.bytebooks.Adapters.ImageSliderAdapter;
import com.bytebooks.Adminlogin;
import com.bytebooks.R;
import com.bytebooks.UserRegister;

public class Selector extends AppCompatActivity {

    private RadioGroup radioGroup;
    private TextView btnNext;
    private ProgressDialog progressDialog;
    private ViewPager imageViewPager;
    private int[] imageArray = {R.drawable.pic1, R.drawable.pic2, R.drawable.pic3, R.drawable.pic4, R.drawable.bytebooklogo}; // Add your image resources
    private int currentPage = 0;
    private final int delay = 1000; // Delay in milliseconds (1 second)

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selector_page);

        radioGroup = findViewById(R.id.radioGroup);
        btnNext = findViewById(R.id.btnNext);
        imageViewPager = findViewById(R.id.imageViewPager);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        ImageSliderAdapter adapter = new ImageSliderAdapter(this, imageArray);
        imageViewPager.setAdapter(adapter);

        // Automatically slide images after a delay
        final Handler handler = new Handler();
        final Runnable update = new Runnable() {
            public void run() {
                if (currentPage < imageArray.length - 1) {
                    currentPage++;
                    imageViewPager.setCurrentItem(currentPage, true);
                    handler.postDelayed(this, delay);
                } else {
                    // Stop the animation when it reaches the last image
                    handler.removeCallbacksAndMessages(null);
                }
            }
        };

        // Start the initial delay for the image slider
        handler.postDelayed(update, delay);

        // Set a page change listener to reset the timer when the user manually changes the page
        imageViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                currentPage = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = radioGroup.getCheckedRadioButtonId();

                if (selectedId != -1) {
                    RadioButton radioButton = findViewById(selectedId);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            if (radioButton.getText().equals("Admin")) {
                                Intent intent = new Intent(Selector.this, Adminlogin.class);
                                startActivity(intent);
                            } else if (radioButton.getText().equals("User")) {

                                SharedPreferences sharedpreferences = getSharedPreferences("my_preferences", MODE_PRIVATE);
                                int userid = sharedpreferences.getInt("userid", 0);
                                    if (userid == 0) {
                                        Intent intent1 = new Intent(Selector.this, Userlogin.class);
                                        startActivity(intent1);
                                    } else {
                                        Intent intent2 = new Intent(Selector.this, MainActivity.class);
                                        startActivity(intent2);
                                    }

                                    Intent intent1 = new Intent();
                                    Selector.this.startActivity(intent1);
                                    Selector.this.finish();

                                    Intent intent2 = new Intent();
                                    Selector.this.startActivity(intent2);
                                    Selector.this.finish();
                            }
                        }
                    }, 500);
                } else {
                    Toast.makeText(Selector.this, "Please select Admin or User", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
