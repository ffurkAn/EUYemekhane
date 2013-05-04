package com.euyemekhane;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;

public class SevilmeyenYemeklerListesi extends Activity {
	
	
	MenuDAL dalManu = new MenuDAL(SevilmeyenYemeklerListesi.this);
	SevilmeyenYemekDAL dalSevilmeyen = new SevilmeyenYemekDAL(getApplicationContext());
	private ArrayList<SevilmeyenYemek> sevilmeyenListe = new ArrayList<SevilmeyenYemek>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sevilmeyen_yemekler_listesi);
		
		
		final ListView lwSevilmeyenYemekler = (ListView) findViewById(R.id.sevilmeyenListView);	
		sevilmeyenListe=dalSevilmeyen.TumYemekleriGetir();
		
		
		//	////// checkBox li listView
		
		
		
		/*SQLiteDatabase db = dalManu.getDatabase();
		
		for(SevilmeyenYemek y : sevilmeyenListe.Checked)
		{
			db.delete("SevilmeyenYemek", "YemekAdi ="+y.getYemekAdi(), null);
			dalSevilmeyen.SevilmeyenGuncelle(y, 0);
		}*/
		
	
	}

}
