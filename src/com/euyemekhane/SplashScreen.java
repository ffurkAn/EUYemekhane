package com.euyemekhane;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SplashScreen extends YemekListesi {
	
	private static final int SPLASH_DISPLAY_TIME = 2500;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);
		
		ImageView logo = (ImageView) findViewById(R.id.splashLogo);
		Animation slide = AnimationUtils.loadAnimation(this, R.anim.slide_out_top);
		slide.setDuration(2500);
		logo.startAnimation(slide);

		TextView text = (TextView) findViewById(R.id.splashText);
		Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
		fadeIn.setDuration(2500);
		text.startAnimation(fadeIn);

		int s = yemekListesiIndir();
		haftalikEskiKayitlariSil();
		
		if (s != -1) {
			if (s == 1)
				Toast.makeText(getApplicationContext(), "Yemek listesi güncel", Toast.LENGTH_SHORT).show();
			else if (s == 2)
				Toast.makeText(getApplicationContext(), "Yemek listesi güncelleniyor", Toast.LENGTH_SHORT).show();
		}

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				Intent i = new Intent(SplashScreen.this, MainActivity.class);
				startActivity(i);
				SplashScreen.this.finish();
			}
		}, SPLASH_DISPLAY_TIME);

	}

}
