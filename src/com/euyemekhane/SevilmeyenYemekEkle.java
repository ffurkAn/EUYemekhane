package com.euyemekhane;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager.LayoutParams;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class SevilmeyenYemekEkle extends Activity {

	private int textChanged = 0;
	private LinearLayout ll;
	private EditText editText;
	private List<EditText> editTextList = new ArrayList<EditText>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sevilmeyen_yemek_ekle);

		ll = (LinearLayout) findViewById(R.id.dynamicLayout);

		editText = getEditText();
		editTextList.add(editText);
		ll.addView(editText);
		editText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				textChanged = 1;
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
		});

	}

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
