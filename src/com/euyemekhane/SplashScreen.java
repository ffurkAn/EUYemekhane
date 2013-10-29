package com.euyemekhane;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SplashScreen extends YemekListesi {

	private int SPLASH_DISPLAY_TIME;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        
		boolean isShowSplash = sharedPreferences.getBoolean("splashKontrol", false);
		if (!isShowSplash) {
			setContentView(R.layout.activity_splash_screen);
			
			ImageView logo = (ImageView) findViewById(R.id.splashLogo);
			Animation slide = AnimationUtils.loadAnimation(this, R.anim.slide_out_top);
			slide.setDuration(2000);
			logo.startAnimation(slide);

			TextView text = (TextView) findViewById(R.id.splashText);
			Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
			fadeIn.setDuration(2000);
			text.startAnimation(fadeIn);
			
			SPLASH_DISPLAY_TIME = 2000;
		} else {
			SPLASH_DISPLAY_TIME = 0;
		}

		int sonuc = yemekListesiIndir(false);
		//haftalikEskiKayitlariSil();
		gunlukEskiKayitlariSil();

		Log.d("#SONUC", "" + sonuc);
		if (sonuc != -1) {
			if (sonuc == 1)
				Toast.makeText(getApplicationContext(), "Yemek listesi g�ncel", Toast.LENGTH_SHORT).show();
			else if (sonuc == 2)
				Toast.makeText(getApplicationContext(), "Yemek listesi g�ncellendi", Toast.LENGTH_SHORT).show();
		}

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Intent i;
				
				if (sharedPreferences.getBoolean("firstrun", true)) {
					i = new Intent(SplashScreen.this, Tutorial.class);
					sharedPreferences.edit().putBoolean("firstrun", false).commit();
		        } else {
		        	i = new Intent(SplashScreen.this, MainActivity.class);
		        }

				startActivity(i);
				SplashScreen.this.finish();
			}
		}, SPLASH_DISPLAY_TIME);

	}

}
