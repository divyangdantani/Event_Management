package com.example.divyangdantani.eventmanagementfinal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

public class Splash_Screen extends Activity {

    LinearLayout l1,l2;
    Animation uptodown,downtoup;
    private final int SPLASH_DISPLAY_LENGTH = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash__screen);
        l1 = findViewById(R.id.icon_layout);
        l2 = findViewById(R.id.text_layout);
        uptodown = AnimationUtils.loadAnimation(this,R.anim.uptodown);
        downtoup = AnimationUtils.loadAnimation(this,R.anim.downtoup);
        l1.setAnimation(uptodown);
        l2.setAnimation(downtoup);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Splash_Screen.this.startActivity(new Intent(Splash_Screen.this,BottomActivity.class));
                Splash_Screen.this.finish();
            }
        },SPLASH_DISPLAY_LENGTH);
    }
}
