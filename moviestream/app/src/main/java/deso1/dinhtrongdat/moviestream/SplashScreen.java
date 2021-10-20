package deso1.dinhtrongdat.moviestream;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;

import com.airbnb.lottie.LottieAnimationView;

import maes.tech.intentanim.CustomIntent;

public class SplashScreen extends AppCompatActivity {
    LottieAnimationView splash;
    private static int SPLASH_SCREEN = 4200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        splash = findViewById(R.id.splash_main);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this,LoginScreen.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_SCREEN);
    }
}