package com.euyemekhane;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.util.Log;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockPreferenceActivity;

public class Ayarlar extends SherlockPreferenceActivity {

	private MenuDAL dalMenu = new MenuDAL(this);
	private SevilmeyenYemekDAL dalSevilmeyen = new SevilmeyenYemekDAL(this);
	private Document doc;
	private Elements baslik;
	private String[] baslikStr;
	private String yemek = null;
	private String yemekTarihi = null;
	private Menu entMenu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.ayarlar);

		Preference prefListeSil = findPreference("listeSil");
		Preference prefGuncelle = findPreference("guncelle");

		prefListeSil.setOnPreferenceClickListener(prefListeSilClickListener);
		prefGuncelle.setOnPreferenceClickListener(prefGuncelleClickListener);
	}

	private Preference.OnPreferenceClickListener prefListeSilClickListener = new OnPreferenceClickListener() {

		@Override
		public boolean onPreferenceClick(Preference preference) {
			// TODO Auto-generated method stub
			dalMenu.TumKayitlariSil(0);
			Toast.makeText(getApplicationContext(), "Silindi", Toast.LENGTH_SHORT).show();
			return false;
		}
	};

	private Preference.OnPreferenceClickListener prefGuncelleClickListener = new OnPreferenceClickListener() {

		@Override
		public boolean onPreferenceClick(Preference preference) {
			// TODO Auto-generated method stub
			int hafta;
			int sonuc = 0;

			try {
				doc = Jsoup.connect("http://sksdb.ege.edu.tr/genel/oele-yemek-menuesue").get();
				baslik = doc.select("tr td p span"); //sayfanin ustundeki basligi cekiyor
				baslikStr = baslik.text().toLowerCase().split("\\s+");

				entMenu = dalMenu.SonOgleYemekGetir();
				if (entMenu != null && entMenu.getAy().equals(baslikStr[2])) { //baslikta yazan ay ile veritabanindaki ay ayn�ysa guncelleme yapmiyor

					sonuc = 1;

				} else {

					sonuc = 2;
					hafta = 1;
					dalMenu.TumKayitlariSil(1);
					Elements yemekler = doc.select("tr td p"); //tarihi ve o gunku yemegi cekiyor

					for (Element x : yemekler) {
						//Log.d("#OGLEYEMEK", x.text());

						entMenu = new Menu();

						Pattern pattern = Pattern.compile("(\\d.*\\d.*\\d.*(Pazartesi|Sal�|�ar�amba|Per�embe|Cuma))(\\s*)(.*)");
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
							else if (yemekTarihi.matches("\\d.*\\d.*\\d.*�a.*"))
								entMenu.setGun(3);
							else if (yemekTarihi.matches("\\d.*\\d.*\\d.*Pe.*"))
								entMenu.setGun(4);
							else if (yemekTarihi.matches("\\d.*\\d.*\\d.*Cu.*"))
								entMenu.setGun(5);

							entMenu.setSevilmeyen(0);
							entMenu.setAy(baslikStr[2]);
							entMenu.setTur("ogle");
							entMenu.setTarih(yemekTarihi);
							entMenu.setHafta(hafta);
							entMenu.setMenu(yemek);
							entMenu.setSelected(false);
							dalMenu.MenuKaydet(entMenu);
							yemek = null;
							yemekTarihi = null;

							if (entMenu.getGun() == 5)
								hafta++;
						}
					}

					dalSevilmeyen.TumSevilmeyenGuncelle(1);
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				sonuc = -1;
				Toast.makeText(getApplicationContext(), "Ba�lant� hatas�", Toast.LENGTH_SHORT).show();
			}

			try {
				doc = null;
				baslik = null;
				baslikStr = null;
				doc = Jsoup.connect("http://sksdb.ege.edu.tr/genel/akam-yemek-menuesue").get();
				baslik = doc.select("tr td p span"); //sayfanin ustundeki basligi cekiyor
				baslikStr = baslik.text().toLowerCase().split("\\s+");

				entMenu = dalMenu.SonAksamYemekGetir();
				if (entMenu != null && entMenu.getAy().equals(baslikStr[2])) { //baslikta yazan ay ile veritabanindaki ay ayn�ysa guncelleme yapmiyor

					sonuc = 1;

				} else {

					sonuc = 2;
					hafta = 1;
					dalMenu.TumKayitlariSil(2);				
					Elements yemekler = doc.select("tr td p"); //tarihi ve o gunku yemegi cekiyor

					for (Element x : yemekler) {
						//Log.d("#AKSAMYEMEK", x.text());

						entMenu = new Menu();

						Pattern pattern = Pattern.compile("(\\d.*\\d.*\\d.*(Pazartesi|Sal�|�ar�amba|Per�embe|Cuma))(\\s*)(.*)");
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
							else if (yemekTarihi.matches("\\d.*\\d.*\\d.*�a.*"))
								entMenu.setGun(3);
							else if (yemekTarihi.matches("\\d.*\\d.*\\d.*Pe.*"))
								entMenu.setGun(4);
							else if (yemekTarihi.matches("\\d.*\\d.*\\d.*Cu.*"))
								entMenu.setGun(5);

							entMenu.setSevilmeyen(0);
							entMenu.setAy(baslikStr[2]);
							entMenu.setTur("aksam");
							entMenu.setTarih(yemekTarihi);
							entMenu.setHafta(hafta);
							entMenu.setMenu(yemek);
							dalMenu.MenuKaydet(entMenu);
							yemek = null;
							yemekTarihi = null;

							if (entMenu.getGun() == 5)
								hafta++;
						}
					}

					dalSevilmeyen.TumSevilmeyenGuncelle(2);
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				sonuc = -1;
				//Toast.makeText(getApplicationContext(), "Ba�lant� hatas�", Toast.LENGTH_SHORT).show();
			}

			Log.d("#SONUC", "" + sonuc);
			if (sonuc != -1) {
				if (sonuc == 1)
					Toast.makeText(getApplicationContext(), "Yemek listesi g�ncel", Toast.LENGTH_SHORT).show();
				else if (sonuc == 2) {
					Toast.makeText(getApplicationContext(), "Yemek listesi g�ncelleniyor", Toast.LENGTH_SHORT).show();
					dalMenu.HaftalikEskiKayitlariSil();
				}
			}

			return false;
		}
	};

}