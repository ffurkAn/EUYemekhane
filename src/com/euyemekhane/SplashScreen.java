package com.euyemekhane;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;

public class SplashScreen extends SherlockActivity {

	private Document doc;
	private Elements baslik;
	private String[] baslikStr;
	private String yemek = null;
	private String yemekTarihi = null;
	private MenuDAL dalMenu = new MenuDAL(this);
	private Menu entMenu;
	private static final int SPLASH_DISPLAY_TIME = 3000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);
	
		ImageView logo = (ImageView) findViewById(R.id.splashLogo);
		Animation slide = AnimationUtils.loadAnimation(this, R.anim.slide_out_top);
		slide.setDuration(3000);
		logo.startAnimation(slide);

		TextView text = (TextView) findViewById(R.id.splashText);
		Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
		fadeIn.setDuration(3000);
		text.startAnimation(fadeIn);

		entMenu = dalMenu.SonOgleYemekGetir();
		final Calendar c = Calendar.getInstance();
		//Log.d("#MONTHCHECK", entMenu.getAy() + " - " + ayBul(entMenu.getAy()) + " - " + c.get(Calendar.MONTH));
		
		if (entMenu != null && (ayBul(entMenu.getAy()) == c.get(Calendar.MONTH))) {
			//Log.d("#UPDATECHECK", "if");
		} else {
			//Log.d("#UPDATECHECK", "else");
			try {
				doc = Jsoup.connect("http://sksdb.ege.edu.tr/genel/oele-yemek-menuesue").get();
				baslik = doc.select("td p"); //sayfanin ustundeki basligi cekiyor
				baslikStr = baslik.text().toLowerCase().split("\\s+");
				//Log.d("#BASLIK", baslikStr[2]);

				if (entMenu != null && entMenu.getAy().equals(baslikStr[2])) { //baslikta yazan ay ile veritabanindaki ay aynýysa guncelleme yapmiyor

					//Toast.makeText(getApplicationContext(), "Yemek listesi güncel", Toast.LENGTH_LONG).show();

				} else {

					//Toast.makeText(getApplicationContext(), "Yemek listesi güncelleniyor", Toast.LENGTH_LONG).show();
					Elements yemekler = doc.select("tr td p"); //tarihi ve o gunku yemegi cekiyor

					for (Element x : yemekler) {
						//Log.d("#OGLEYEMEK", x.text());

						entMenu = new Menu();
						/*if (x.text().matches("\\d.*\\d.*\\d.*")) { //bos gelen veriler eleniyor
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
							entMenu.setTur("ogle");
							entMenu.setTarih(yemekTarihi);
							entMenu.setMenu(yemek);
							dalMenu.MenuKaydet(entMenu);
							yemek = null;
							yemekTarihi = null;
						}*/

						Pattern pattern = Pattern.compile("(\\d.*\\d.*\\d.*(Pazartesi|Salý|Çarþamba|Perþembe|Cuma))(\\s*)(.*)");
						Matcher matcher = pattern.matcher(x.text());

						if (matcher.find()) {
							//Log.d("#GROUP1", matcher.group(1));
							//Log.d("#GROUP4", matcher.group(4));
							yemekTarihi = matcher.group(1);
							yemek = matcher.group(4);
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
							entMenu.setAy(baslikStr[2]);
							entMenu.setTur("ogle");
							entMenu.setTarih(yemekTarihi);
							entMenu.setMenu(yemek);
							entMenu.setSelected(false);
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
				baslik = doc.select("td p"); //sayfanin ustundeki basligi cekiyor
				baslikStr = baslik.text().toLowerCase().split("\\s+");

				entMenu = dalMenu.SonAksamYemekGetir();

				if (entMenu != null && entMenu.getAy().equals(baslikStr[2])) { //baslikta yazan ay ile veritabanindaki ay aynýysa guncelleme yapmiyor

					Toast.makeText(getApplicationContext(), "Yemek listesi güncel", Toast.LENGTH_LONG).show();

				} else {

					Toast.makeText(getApplicationContext(), "Yemek listesi güncelleniyor", Toast.LENGTH_LONG).show();
					Elements yemekler = doc.select("tr td p"); //tarihi ve o gunku yemegi cekiyor

					for (Element x : yemekler) {
						//Log.d("#AKSAMYEMEK", x.text());

						entMenu = new Menu();
						/*if (x.text().matches("\\d.*\\d.*\\d.*")) { //bos gelen veriler eleniyor
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
						}*/

						Pattern pattern = Pattern.compile("(\\d.*\\d.*\\d.*(Pazartesi|Salý|Çarþamba|Perþembe|Cuma))(\\s*)(.*)");
						Matcher matcher = pattern.matcher(x.text());

						if (matcher.find()) {
							//Log.d("#GROUP1", matcher.group(1));
							//Log.d("#GROUP4", matcher.group(4));
							yemekTarihi = matcher.group(1);
							yemek = matcher.group(4);
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
							entMenu.setAy(baslikStr[2]);
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
		}

		//dalMenu.EskiKayitlariSil();

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				Intent i = new Intent(SplashScreen.this, MainActivity.class);
				startActivity(i);
				SplashScreen.this.finish();
			}
		}, SPLASH_DISPLAY_TIME);

	}

	private int ayBul(String ay) {
		if (ay.matches("ocak"))
			return 0;
		else if (ay.matches(".*ubat"))
			return 1;
		else if (ay.matches("mart"))
			return 2;
		else if (ay.matches("n.*san"))
			return 3;
		else if (ay.matches("may.*s"))
			return 4;
		else if (ay.matches("haz.*ran"))
			return 5;
		else if (ay.matches("temmuz"))
			return 6;
		else if (ay.matches("a.*ustos"))
			return 7;
		else if (ay.matches("eyl.*l"))
			return 8;
		else if (ay.matches("ek.*m"))
			return 9;
		else if (ay.matches("kas.*m"))
			return 10;
		else if (ay.matches("aral.*k"))
			return 11;

		return -1;
	}

}
