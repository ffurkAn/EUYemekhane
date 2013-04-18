package com.euyemekhane;

import java.util.ArrayList;
import java.util.Calendar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;

public class OgleYemegi extends Activity implements OnGestureListener {

	private static final int SWIPE_MIN_DISTANCE = 120;
	private static final int SWIPE_MAX_OFF_PATH = 250;
	private static final int SWIPE_THRESHOLD_VELOCITY = 200;
	private GestureDetector gestureScanner;
	private String sorguTarihi;
	private String[] baslikStr;
	private String yemek = null;
	private String yemekTarihi = null;
	private MenuDAL dalMenu = new MenuDAL(this);
	private Menu entMenu;
	private ArrayList<Menu> menuListe = new ArrayList<Menu>();
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
		setContentView(R.layout.activity_ogle_yemegi);

		Intent preIntent = getIntent();
		glIntent = preIntent;
		
		gestureScanner = new GestureDetector((OnGestureListener) this);

		final Calendar c = Calendar.getInstance();
		sorguTarihi = "" + c.get(Calendar.DAY_OF_MONTH) + "." + c.get(Calendar.MONTH) + "." + c.get(Calendar.YEAR); //sistemin tarihi

		final TextView view1 = (TextView) findViewById(R.id.txtViewOgle);

		try {
			Document doc = Jsoup.connect("http://sksdb.ege.edu.tr/genel/oele-yemek-menuesue").get();
			Elements baslik = doc.select("td h1"); //sayfanin ustundeki basligi cekiyor
			baslikStr = baslik.text().toLowerCase().split("\\s+");

			if (dalMenu.SonOgleYemekGetir() != null && dalMenu.SonOgleYemekGetir().getAy().equals(baslikStr[0])) { //baslikta yazan ay ile veritabanindaki ay aynýysa guncelleme yapmiyor

				Toast.makeText(getApplicationContext(), "Güncelleme yok", Toast.LENGTH_LONG).show();

			} else {

				Toast.makeText(getApplicationContext(), "Güncelleniyor", Toast.LENGTH_LONG).show();
				Elements yemekler = doc.select("td p span"); //tarihi ve o gunku yemegi cekiyor

				for (Element x : yemekler) {
					//System.out.println("\n" + x.text());
					entMenu = new Menu();
					if (x.text().matches("\\d.*\\d.*\\d.*")) { //bos gelen veriler eleniyor
						yemekTarihi = x.text().trim();
					}
					if (x.text().contains("cal")) {
						yemek = x.text().trim();
					}
					if (yemek != null && yemekTarihi != null) { //yemek ve tarihin ikisi de null degilse veritabanina ekleniyor
						entMenu.setAy(baslikStr[0]);
						entMenu.setTur("ogle");
						entMenu.setTarih(yemekTarihi);
						entMenu.setMenu(yemek);
						dalMenu.MenuKaydet(entMenu);
						yemek = null;
						yemekTarihi = null;
					}
				}

			}

		} catch (Exception e) {
			// TODO: handle exception
		}

		menuListe = dalMenu.TumOgleGetir();

		for (Menu k : menuListe) {
			view1.append("\n\n" + k.getTarih() + "\n" + k.getMenu());
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

		if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
			Intent i = new Intent(OgleYemegi.this, AksamYemegi.class);
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
