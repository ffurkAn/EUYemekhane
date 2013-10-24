package com.euyemekhane;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockPreferenceActivity;

public class Ayarlar extends SherlockPreferenceActivity {

	private YemekListesi yemekListesi = new YemekListesi(this);

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.ayarlar);
		
		Preference prefListeSil = findPreference("listeSil");
		Preference prefGuncelle = findPreference("guncelle");

		prefListeSil.setOnPreferenceClickListener(prefListeSilClickListener);
		prefGuncelle.setOnPreferenceClickListener(prefGuncelleClickListener);
		
		PreferenceManager.setDefaultValues(this, R.xml.ayarlar, false);
	}

	private Preference.OnPreferenceClickListener prefListeSilClickListener = new OnPreferenceClickListener() {

		@Override
		public boolean onPreferenceClick(Preference preference) {
			// TODO Auto-generated method stub
			yemekListesi.tumKayitlariSil(0);
			Toast.makeText(getApplicationContext(), "Silindi", Toast.LENGTH_SHORT).show();
			return false;
		}
	};

	private Preference.OnPreferenceClickListener prefGuncelleClickListener = new OnPreferenceClickListener() {

		@Override
		public boolean onPreferenceClick(Preference preference) {
			// TODO Auto-generated method stub
			int sonuc = 0;
			sonuc = yemekListesi.yemekListesiIndir(true);

			//Log.d("#SONUC", "" + sonuc);
			if (sonuc != -1) {
				if (sonuc == 1)
					Toast.makeText(getApplicationContext(), "Yemek listesi güncel", Toast.LENGTH_SHORT).show();
				else if (sonuc == 2) {
					Toast.makeText(getApplicationContext(), "Yemek listesi güncellendi", Toast.LENGTH_SHORT).show();
					yemekListesi.haftalikEskiKayitlariSil();
				}
			}

			return false;
		}
	};

}
