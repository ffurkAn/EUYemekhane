package com.euyemekhane;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class AksamYemegi extends Activity implements OnGestureListener {

	private static final int SWIPE_MIN_DISTANCE = 120;
	private static final int SWIPE_MAX_OFF_PATH = 250;
	private static final int SWIPE_THRESHOLD_VELOCITY = 200;
	private GestureDetector gestureScanner;
	private String[] gunlukMenu;
	private Menu menu;
	private MenuDAL dalMenu = new MenuDAL(this);
	private ArrayList<Menu> menuListe;
	private Intent glIntent;

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		super.dispatchTouchEvent(ev);
		return gestureScanner.onTouchEvent(ev);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_aksam_yemegi);

		Intent preIntent = getIntent();
		glIntent = preIntent;

		gestureScanner = new GestureDetector(this);

		final Calendar c = Calendar.getInstance();
		final ListView listView = (ListView) findViewById(R.id.aksamListView);

		if (preIntent.getIntExtra("gosterimTipi", -1) == 1) {
			LinearLayout ll = (LinearLayout) findViewById(R.id.aksamLinear);
			LayoutParams params = new LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
			TextView txtView = new TextView(this);
			txtView.setLayoutParams(params);
			ll.addView(txtView);
			txtView.setTextSize(25);
			menu = dalMenu.GunlukAksamYemekGetir(c.get(Calendar.DAY_OF_MONTH), (c.get(Calendar.MONTH) + 1));
			if (menu == null) {
				txtView.setText("Yemek bulunamadý");
			} else {
				Pattern splitter = Pattern.compile("[\\/=]");
				gunlukMenu = splitter.split(menu.getMenu());
				txtView.append("" + menu.getTarih() + "\n");
				int size = gunlukMenu.length;
				for (String s : gunlukMenu) {
					if (--size == 0) {
						txtView.append("\nToplam cal = ");
					}
					txtView.append("" + s.trim() + "\n");
				}
			}

		} else if (preIntent.getIntExtra("gosterimTipi", -1) == 2) {
			menuListe = dalMenu.TumAksamGetir();
			CustomAdapter adapter = new CustomAdapter(this, R.id.aksamListView, menuListe);
			listView.setAdapter(adapter);
		}

	}

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub

		if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH) {
			return false;
		}

		if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
			Intent i = new Intent(AksamYemegi.this, OgleYemegi.class);
			i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			i.putExtra("gosterimTipi", glIntent.getIntExtra("gosterimTipi", -1));
			startActivity(i);
		}
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

}
