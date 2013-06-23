package com.euyemekhane;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager.LayoutParams;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

public class SevilmeyenYemekEkle extends Activity {

	private int editTextID = 0;
	private int editTextCount = 0;
	private String text;
	private LinearLayout ll;
	private ListView listView;
	private EditText editText;
	private List<EditText> editTextList = new ArrayList<EditText>();
	private SevilmeyenYemek entYemek;
	private ArrayList<SevilmeyenYemek> sevilmeyenYemekListe = new ArrayList<SevilmeyenYemek>();
	private SevilmeyenYemekDAL dalSevilmeyen = new SevilmeyenYemekDAL(this);
	//private RelativeLayout.LayoutParams params;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sevilmeyen_yemek_ekle);

		ll = (LinearLayout) findViewById(R.id.scrollLinear);
		listView = (ListView) findViewById(R.id.sevilmeyenYemekListView);

		TextWatcher watcher = new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				text = s.toString();
				if (text != null && editTextCount < 15) {
					editText.removeTextChangedListener(this);
					editText = getEditText();
					editText.addTextChangedListener(this);
					//params = new RelativeLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
					//params.addRule(RelativeLayout.BELOW, editTextList.get(editTextList.size() - 1).getId());
					//editText.setLayoutParams(params);
					editTextList.add(editText);
					ll.addView(editText);
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
			}
		};

		editText = getEditText();
		editText.addTextChangedListener(watcher);
		editTextList.add(editText);
		ll.addView(editText);

		sevilmeyenYemekListe = dalSevilmeyen.TumYemekleriGetir();
		SevilmeyenYemekAdapter adapter = new SevilmeyenYemekAdapter(this, R.id.sevilmeyenYemekListView, sevilmeyenYemekListe);
		listView.setAdapter(adapter);
		
		Button btnYemekKaydet = (Button) findViewById(R.id.btnSevilmeyenYemekEkle);
		btnYemekKaydet.setOnClickListener(btnYemekKaydetOnClickListener);
		
		Button btnYemekSil = (Button) findViewById(R.id.btnSevilmeyenYemekSil);
		btnYemekSil.setOnClickListener(btnYemekSilOnClickListener);

	}

	private EditText getEditText() {
		editTextID++;
		editTextCount++;
		EditText txt = new EditText(this);
		LayoutParams param = new LayoutParams();
		param.width = android.view.ViewGroup.LayoutParams.FILL_PARENT;
		param.height = android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
		txt.setId(editTextID);
		txt.setLayoutParams(param);
		txt.setText("");

		return txt;
	}

	private Button.OnClickListener btnYemekKaydetOnClickListener = new Button.OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String newYemekAdi;
			for (EditText x : editTextList)
			{
				if (x.getText().length() > 0) {
					entYemek = new SevilmeyenYemek();
					newYemekAdi = x.getText().toString().substring(0,1).toUpperCase() + x.getText().toString().substring(1).toLowerCase();

					entYemek.setYemekAdi(newYemekAdi);
					dalSevilmeyen.YemekKaydet(entYemek);
					dalSevilmeyen.SevilmeyenGuncelle(entYemek,1);
				}
			}
			Intent i = new Intent(SevilmeyenYemekEkle.this, MainActivity.class);
			startActivity(i);
		}
	};
	
	private Button.OnClickListener btnYemekSilOnClickListener = new Button.OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			//Secilen yemekleri silme islemleri
			Intent i = new Intent(SevilmeyenYemekEkle.this, MainActivity.class);
			startActivity(i);
		}
	};

}
