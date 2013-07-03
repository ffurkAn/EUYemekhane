package com.euyemekhane;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import android.widget.Button;

import com.actionbarsherlock.app.SherlockActivity;

public class MainActivity extends SherlockActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button btnGunluk = (Button) findViewById(R.id.btnGunluk);
		Button btnAylik = (Button) findViewById(R.id.btnAylik);
		Button btnSevYemek = (Button) findViewById(R.id.btnSevYemek);
		Button btnSecilenYemek = (Button) findViewById(R.id.btnSecilenYemek);

		btnGunluk.setOnClickListener(btnGunlukOnClickListener);
		btnAylik.setOnClickListener(btnAylikOnClickListener);
		btnSevYemek.setOnClickListener(btnSevYemekOnClickListener);
		btnSecilenYemek.setOnClickListener(btnSecilenYemekOnClickListener);
	}

	private Button.OnClickListener btnGunlukOnClickListener = new Button.OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent i = new Intent(MainActivity.this, OgleAksam.class);
			i.putExtra("GosterimTipi", 1); //sonraki activity'e yemeklerin gosterim seklini aktariyor
			startActivity(i);
		}
	};

	private Button.OnClickListener btnAylikOnClickListener = new Button.OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent i = new Intent(MainActivity.this, OgleAksam.class);
			i.putExtra("GosterimTipi", 2);
			startActivity(i);
		}
	};

	private Button.OnClickListener btnSevYemekOnClickListener = new Button.OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent i = new Intent(MainActivity.this, SevilmeyenYemekEkle.class);
			startActivity(i);
		}
	};

	private Button.OnClickListener btnSecilenYemekOnClickListener = new Button.OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent i = new Intent(MainActivity.this, SecilenYemekler.class);
			startActivity(i);
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.settings_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
		case R.id.Ayarlar:
			Intent i = new Intent(MainActivity.this, Ayarlar.class);
			startActivity(i);
			return true;

		case R.id.Hakkinda:
			i = new Intent(MainActivity.this, Hakkinda.class);
			startActivity(i);
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}    

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			moveTaskToBack(true);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
