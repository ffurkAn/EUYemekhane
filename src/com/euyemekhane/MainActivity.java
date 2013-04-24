package com.euyemekhane;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button btnGunluk = (Button) findViewById(R.id.btnGunluk);
		Button btnAylik = (Button) findViewById(R.id.btnAylik);
		Button btnSevYemek = (Button) findViewById(R.id.btnSevYemek);
		btnGunluk.setOnClickListener(btnGunlukOnClickListener);
		btnAylik.setOnClickListener(btnAylikOnClickListener);
		btnSevYemek.setOnClickListener(btnSevYemekOnClickListener);

	}

	private Button.OnClickListener btnGunlukOnClickListener = new Button.OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent i = new Intent(MainActivity.this, OgleYemegi.class);
			i.putExtra("gosterimTipi", 1); //sonraki activity'e yemeklerin gosterim seklini aktariyor
			startActivity(i);
		}
	};

	private Button.OnClickListener btnAylikOnClickListener = new Button.OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent i = new Intent(MainActivity.this, OgleYemegi.class);
			i.putExtra("gosterimTipi", 2);
			startActivity(i);
		}
	};
	
	private Button.OnClickListener btnSevYemekOnClickListener = new Button.OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent i = new Intent(MainActivity.this, SevilmeyenYemekEkle.class);
			startActivity(i);
		}
	};

}
