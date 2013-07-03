package com.euyemekhane;

import java.util.ArrayList;

import android.os.Bundle;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;

public class SecilenYemekler extends SherlockActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_secilen_yemekler);
		
		MenuDAL dalMenu = new MenuDAL(this);
		ArrayList<com.euyemekhane.Menu> seciliKayitlar;
		TextView txtViewOgle = (TextView) findViewById(R.id.secilenYemeklerTextViewOgle);
		TextView txtViewAksam = (TextView) findViewById(R.id.secilenYemeklerTextViewAksam);
		
		seciliKayitlar = dalMenu.SeciliKayitlariGetir(1);
		if (seciliKayitlar != null) {
			int hafta = 1;
			txtViewOgle.setText("");
			for (com.euyemekhane.Menu x : seciliKayitlar) {
				if (hafta != x.getHafta())
					txtViewOgle.append("\n");
				
				txtViewOgle.append(x.getTarih() + "\n");
				hafta = x.getHafta();
			}
		}
		
		seciliKayitlar = dalMenu.SeciliKayitlariGetir(2);
		if (seciliKayitlar != null) {
			int hafta = 1;
			txtViewAksam.setText("");
			for (com.euyemekhane.Menu x : seciliKayitlar) {
				if (hafta != x.getHafta())
					txtViewAksam.append("\n");
				
				txtViewAksam.append(x.getTarih() + "\n");
				hafta = x.getHafta();
			}
		}
	}

}
