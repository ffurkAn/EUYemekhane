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

public class SevilmeyenYemekEkle extends Activity {

	private int textChanged = 0;
	private LinearLayout ll;
	private EditText editText;
	private List<EditText> editTextList = new ArrayList<EditText>();
	private SevilmeyenYemekDAL dalSevilmeyen = new SevilmeyenYemekDAL(this);
	private SevilmeyenYemek entYemek = new SevilmeyenYemek();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sevilmeyen_yemek_ekle);
		
		
		Button btnSevilmeyen = (Button) findViewById(R.id.btnSevilmeyenKaydet);
		btnSevilmeyen.setOnClickListener(btnSevilmeyenKaydet);
		EditText t1 = (EditText)findViewById(R.id.text1);
		//EditText t2 = (EditText)findViewById(R.id.text2);
		editTextList.add(t1);
		//editTextList.add(t2);
		
	}

	private Button.OnClickListener btnSevilmeyenKaydet = new Button.OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			
			String newYemekAdi;
			for(EditText x : editTextList)
			{
				
				newYemekAdi = x.getText().toString().substring(0,1).toUpperCase()+
						x.getText().toString().substring(1).toLowerCase();
				
				entYemek.setYemekAdi(newYemekAdi);
				dalSevilmeyen.YemekKaydet(entYemek);
				dalSevilmeyen.SevilmeyenGüncelle(entYemek);
			}
			
			Intent i = new Intent(SevilmeyenYemekEkle.this, MainActivity.class);
			//i.putExtra("gosterimTipi", 1); //sonraki activity'e yemeklerin gosterim seklini aktariyor
			startActivity(i);
		}
	};

	
	private EditText getEditText() {
		EditText txt = new EditText(this);
		LayoutParams param = new LayoutParams();
		param.width = LayoutParams.FILL_PARENT;
		param.height = LayoutParams.WRAP_CONTENT;
		txt.setLayoutParams(param);
		txt.setText("");

		return txt;
	}

	private Button getButton() {
		Button btn = new Button(this);
		LayoutParams param = new LayoutParams();
		param.width = LayoutParams.WRAP_CONTENT;
		param.height = LayoutParams.WRAP_CONTENT;
		btn.setLayoutParams(param);
		btn.setText("");

		return btn;
	}
	
	

}
