package com.euyemekhane;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager.LayoutParams;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class SevilmeyenYemekEkle extends SherlockActivity {

	private int editTextID = 0;
	private int editTextCount = 0;
	private String text;
	private LinearLayout ll;
	private EditText editText;
	private List<EditText> editTextList = new ArrayList<EditText>();
	private SevilmeyenYemekDAL dalSevilmeyen = new SevilmeyenYemekDAL(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sevilmeyen_yemek_ekle);

		ll = (LinearLayout) findViewById(R.id.scrollLinear);

		TextWatcher watcher = new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				text = s.toString();
				if (text != null && editTextCount < 10) {
					if (editText.getId() == 1)
						Toast.makeText(getApplicationContext(), "Lütfen, Türkçe karakter kullanýn", Toast.LENGTH_LONG).show();
					
					editText.removeTextChangedListener(this);
					editText = getEditText();
					editText.addTextChangedListener(this);
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
		if (editText.getId() == 1)
			editText.setHint("Örn. Balýk");
		editText.addTextChangedListener(watcher);
		editTextList.add(editText);
		ll.addView(editText);

	}

	private EditText getEditText() {
		editTextID++;
		editTextCount++;
		EditText txt = new EditText(this);
		LayoutParams param = new LayoutParams();
		param.width = android.view.ViewGroup.LayoutParams.MATCH_PARENT;
		param.height = android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
		txt.setId(editTextID);
		txt.setSingleLine(true);
		txt.setLayoutParams(param);
		txt.setText("");

		return txt;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		com.actionbarsherlock.view.MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.actionbar_check, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.actionbar_check_btn:

			SevilmeyenYemek entYemek;
			String newYemekAdi;
			int yemekSayisi = 0;
			for (EditText x : editTextList) {
				if (x.getText().length() > 0) {
					entYemek = new SevilmeyenYemek();
					newYemekAdi = x.getText().toString().substring(0,1).toUpperCase() + x.getText().toString().substring(1).toLowerCase();
					entYemek.setYemekAdi(newYemekAdi);
					dalSevilmeyen.YemekKaydet(entYemek);
					dalSevilmeyen.SevilmeyenGuncelle(entYemek, 1);
					yemekSayisi++;
				}
			}
			
			if (yemekSayisi > 0) {
				Intent i = new Intent(SevilmeyenYemekEkle.this, SevilmeyenYemekSil.class);
				i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
				finish();
			}

			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
