package com.bytebooks;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
public class WelcomeScreen extends AppCompatActivity {
    private static final int SPLASH_DISPLAY_TIME = 4000;
    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.welcomescreen);

        logo = findViewById(R.id.logo);

        logo.setScaleX(0.5f);
        logo.setScaleY(0.5f);

        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(logo, "alpha", 0f, 1f);
        alphaAnimator.setDuration(1500);

        ObjectAnimator scaleAnimatorX = ObjectAnimator.ofFloat(logo, "scaleX", 0.5f, 1f);
        ObjectAnimator scaleAnimatorY = ObjectAnimator.ofFloat(logo, "scaleY", 0.5f, 1f);
        scaleAnimatorX.setDuration(1500);
        scaleAnimatorY.setDuration(1500);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(alphaAnimator, scaleAnimatorX, scaleAnimatorY);

        animatorSet.start();

      @SuppressLint("ResourceType") Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.drawable.animate);
//        logo.startAnimation(animation);
      @SuppressLint("ResourceType") Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.drawable.fade);
//        logo.startAnimation(animation1);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // Do nothing on start
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // Start the second animation when the first one ends
                logo.startAnimation(animation1);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // Do nothing on repeat
            }
        });

// Start the first animation
        logo.startAnimation(animation);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(WelcomeScreen.this, Selector.class);
                startActivity(intent);
                finish(); // Finish the current activity if needed
            }
        }, SPLASH_DISPLAY_TIME);
    }
}


