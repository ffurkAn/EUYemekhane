package com.euyemekhane;

import java.util.Calendar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

public class SplashScreen extends Activity {

	private Document doc;
	private Elements baslik;
	private String[] baslikStr;
	private String yemek = null;
	private String yemekTarihi = null;
	private MenuDAL dalMenu = new MenuDAL(this);
	private Menu entMenu;
	private static final int SPLASH_DISPLAY_TIME = 1000;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);
		
		
		
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				try {
					doc = Jsoup.connect("http://sksdb.ege.edu.tr/genel/oele-yemek-menuesue").get();
					baslik = doc.select("td h1"); //sayfanin ustundeki basligi cekiyor
					baslikStr = baslik.text().toLowerCase().split("\\s+");

					if (dalMenu.SonOgleYemekGetir() != null && dalMenu.SonOgleYemekGetir().getAy().equals(baslikStr[0])) { //baslikta yazan ay ile veritabanindaki ay aynýysa guncelleme yapmiyor

						//Toast.makeText(getApplicationContext(), "Yemek listesi güncel", Toast.LENGTH_LONG).show();

					} else {

						//Toast.makeText(getApplicationContext(), "Yemek listesi güncelleniyor", Toast.LENGTH_LONG).show();
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
								
								entMenu.setGun(0);
								if (yemekTarihi.matches("\\d.*\\d.*\\d.*Pa.*"))
									entMenu.setGun(1);
								else if (yemekTarihi.matches("\\d.*\\d.*\\d.*Sa.*"))
									entMenu.setGun(2);
								else if (yemekTarihi.matches("\\d.*\\d.*\\d.*Ça.*"))
									entMenu.setGun(3);
								else if (yemekTarihi.matches("\\d.*\\d.*\\d.*Pe.*"))
									entMenu.setGun(4);
								else if (yemekTarihi.matches("\\d.*\\d.*\\d.*Cu.*"))
									entMenu.setGun(5);
								
								//
									
								
								//
								entMenu.setSevilmeyen(0);
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
				
				try {
					doc = null;
					baslik = null;
					baslikStr = null;
					doc = Jsoup.connect("http://sksdb.ege.edu.tr/genel/akam-yemek-menuesue").get();
					baslik = doc.select("td h1"); //sayfanin ustundeki basligi cekiyor
					baslikStr = baslik.text().toLowerCase().split("\\s+");

					if (dalMenu.SonAksamYemekGetir() != null && dalMenu.SonAksamYemekGetir().getAy().equals(baslikStr[0])) { //baslikta yazan ay ile veritabanindaki ay aynýysa guncelleme yapmiyor

						Toast.makeText(getApplicationContext(), "Yemek listesi güncel", Toast.LENGTH_LONG).show();

					} else {

						Toast.makeText(getApplicationContext(), "Yemek listesi güncelleniyor", Toast.LENGTH_LONG).show();
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
								
								entMenu.setGun(0);
								if (yemekTarihi.matches("\\d.*\\d.*\\d.*Pa.*"))
									entMenu.setGun(1);
								else if (yemekTarihi.matches("\\d.*\\d.*\\d.*Sa.*"))
									entMenu.setGun(2);
								else if (yemekTarihi.matches("\\d.*\\d.*\\d.*Ça.*"))
									entMenu.setGun(3);
								else if (yemekTarihi.matches("\\d.*\\d.*\\d.*Pe.*"))
									entMenu.setGun(4);
								else if (yemekTarihi.matches("\\d.*\\d.*\\d.*Cu.*"))
									entMenu.setGun(5);
								
								
								entMenu.setSevilmeyen(0);
								entMenu.setAy(baslikStr[0]);
								entMenu.setTur("aksam");
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
				
				dalMenu.eskiKayitlariSil();
					//dbTemizle();
				Intent i = new Intent(SplashScreen.this, MainActivity.class);
				startActivity(i);
				SplashScreen.this.finish();
				
			}
		}, SPLASH_DISPLAY_TIME);
		
	}
	
	/*public void dbTemizle()
	{
		final Calendar c = Calendar.getInstance();
		int gun=c.get(Calendar.DAY_OF_MONTH); 
		int ay= (c.get(Calendar.MONTH)+1); 
		int id;
		DAL dal = new DAL();
		
		SQLiteDatabase db = dalMenu.getDatabase();
		
		Cursor crsrOgle = db.rawQuery("select * from EUYemekhane where EUYemekhane.YemekTuru = 'ogle' and EUYemekhane.MenuTarihi like '%" + gun + "%" + ay + "%'", null);
		//id= dal.getCursorInt(crsrOgle, "id"); // id -999 geliyor yani FALSE
		crsrOgle.moveToFirst();
		id = crsrOgle.getInt(0); 
		db.execSQL("delete from EUYemekhane where EUYemekhane.YemekTuru= 'ogle' and EUYemekhane.id <"+id);
		crsrOgle.close();
		
		Cursor crsrAksam = db.rawQuery("select * from EUYemekhane where EUYemekhane.YemekTuru = 'aksam' and EUYemekhane.MenuTarihi like '%" + gun + "%" + ay + "%'",null);
		crsrAksam.moveToFirst();
		id= dal.getCursorInt(crsrAksam, "id");
		//id = crsrAksam.getInt(0);		
		db.execSQL("delete from EUYemekhane where EUYemekhane.YemekTuru='aksam' and EUYemekhane.id <"+id);
		crsrAksam.close();
		
		db.close();
	}*/

}
