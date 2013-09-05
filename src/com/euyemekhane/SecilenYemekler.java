package com.euyemekhane;

import java.util.ArrayList;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;

public class SecilenYemekler extends SherlockActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_secilen_yemekler);
		
		boolean uyari = true;
		MenuDAL dalMenu = new MenuDAL(this);
		ArrayList<com.euyemekhane.Menu> seciliKayitlar;
		TextView txtViewOgle = (TextView) findViewById(R.id.secilenYemeklerTextViewOgle);
		TextView txtViewAksam = (TextView) findViewById(R.id.secilenYemeklerTextViewAksam);
		
		seciliKayitlar = dalMenu.SeciliKayitlariGetir(1);
		if (!seciliKayitlar.isEmpty()) {
			int hafta = 1;
			uyari = false;
			txtViewOgle.setText("");
			for (com.euyemekhane.Menu x : seciliKayitlar) {
				if (hafta != x.getHafta())
					txtViewOgle.append("\n");
				
				txtViewOgle.append(x.getTarih() + "\n");
				hafta = x.getHafta();
			}
		}
		
		seciliKayitlar = dalMenu.SeciliKayitlariGetir(2);
		if (!seciliKayitlar.isEmpty()) {
			int hafta = 1;
			uyari = false;
			txtViewAksam.setText("");
			for (com.euyemekhane.Menu x : seciliKayitlar) {
				if (hafta != x.getHafta())
					txtViewAksam.append("\n");
				
				txtViewAksam.append(x.getTarih() + "\n");
				hafta = x.getHafta();
			}
		}
		
		if (uyari)
			Toast.makeText(getApplicationContext(), "Seçili yemek yok", Toast.LENGTH_SHORT).show();
	}

}
