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
		TextView txtView = (TextView) findViewById(R.id.secilenYemeklerTextView);
		
		seciliKayitlar = dalMenu.SeciliKayitlariGetir(1);
		if (seciliKayitlar != null) {
			txtView.setText("Öðle\n");
			for (com.euyemekhane.Menu x : seciliKayitlar) {
				txtView.append(x.getTarih() + "\n");
			}
		}
		
		seciliKayitlar = dalMenu.SeciliKayitlariGetir(2);
		if (seciliKayitlar != null) {
			txtView.append("\nAkþam\n");
			for (com.euyemekhane.Menu x : seciliKayitlar) {
				txtView.append(x.getTarih() + "\n");
			}
		}
	}

}
