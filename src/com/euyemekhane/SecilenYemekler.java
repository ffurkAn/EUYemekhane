package com.euyemekhane;

import java.util.ArrayList;
import java.util.Calendar;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;

public class SecilenYemekler extends SherlockActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_secilen_yemekler);
		
		boolean uyari = true;
		int yemekSayisi = 0;
		MenuDAL dalMenu = new MenuDAL(this);
		final Calendar c = Calendar.getInstance();
		ArrayList<com.euyemekhane.Menu> seciliKayitlar;
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		TextView txtViewOgle = (TextView) findViewById(R.id.secilenYemeklerTextViewOgle);
		TextView txtViewAksam = (TextView) findViewById(R.id.secilenYemeklerTextViewAksam);
		TextView txtViewOgleFiyat = (TextView) findViewById(R.id.ogleToplamFiyat);
		TextView txtViewAksamFiyat = (TextView) findViewById(R.id.aksamToplamFiyat);
		
		seciliKayitlar = dalMenu.SeciliKayitlariGetir(1);
		if (!seciliKayitlar.isEmpty()) {
			int hafta = 1;
			txtViewOgle.setText("");
			
			for (com.euyemekhane.Menu x : seciliKayitlar) {
				if (c.get(Calendar.WEEK_OF_MONTH) != x.getHafta()) {
					if (hafta != x.getHafta())
						txtViewOgle.append("\n");
					
					txtViewOgle.append(x.getTarih() + "\n");
					hafta = x.getHafta();
					uyari = false;
					yemekSayisi++;
				}
			}
			
			if (yemekSayisi > 0) {
				txtViewOgleFiyat.setText("Toplam Fiyat = " + Float.parseFloat(sharedPreferences.getString("birimFiyat", ""))*yemekSayisi + " TL");
			}
		}
		
		yemekSayisi = 0;
		seciliKayitlar = dalMenu.SeciliKayitlariGetir(2);
		if (!seciliKayitlar.isEmpty()) {
			int hafta = 1;
			txtViewAksam.setText("");
			
			for (com.euyemekhane.Menu x : seciliKayitlar) {
				if (c.get(Calendar.WEEK_OF_MONTH) != x.getHafta()) {
					if (hafta != x.getHafta())
						txtViewAksam.append("\n");
					
					txtViewAksam.append(x.getTarih() + "\n");
					hafta = x.getHafta();
					uyari = false;
					yemekSayisi++;
				}
			}
			
			if (yemekSayisi > 0) {
				txtViewAksamFiyat.setText("Toplam Fiyat = " + Float.parseFloat(sharedPreferences.getString("birimFiyat", ""))*yemekSayisi + " TL");
			}
		}
		
		if (uyari)
			Toast.makeText(getApplicationContext(), "Seçili yemek yok", Toast.LENGTH_SHORT).show();
	}

}
