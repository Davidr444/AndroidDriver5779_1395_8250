package com.jct.davidandyair.androiddriver5779_1395_8250.controller.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.widget.ImageView;

import com.jct.davidandyair.androiddriver5779_1395_8250.R;

public class OpeningActivity extends AppCompatActivity {
    AnimationDrawable openingAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening);

        ImageView imageView = (ImageView)findViewById(R.id.animImage);
        imageView.setBackgroundResource(R.drawable.opening_animation);
        openingAnimation = (AnimationDrawable) imageView.getBackground();
        openingAnimation.start();

        Thread myThread = new Thread()
        {
            @Override
            public void run()
            {
                try {
                    sleep(4500);
                    Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(intent);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        myThread.start();
    }


}
