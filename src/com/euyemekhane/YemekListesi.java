package com.euyemekhane;

import java.util.Calendar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;

public class YemekListesi extends SherlockActivity {

	private Context appContext = this;

	public YemekListesi()
	{
		appContext = this;
	}

	public YemekListesi(Context c)
	{
		appContext = c;
	}

	protected int yemekListesiIndir(boolean forceUpdate) {
		Document doc;
		Elements baslik;
		String[] baslikStr;
		String yemek = null;
		String yemekTarihi = null;
		Menu entMenu;
		MenuDAL dalMenu = new MenuDAL(appContext);
		SevilmeyenYemekDAL dalSevilmeyen = new SevilmeyenYemekDAL(appContext);
		final Calendar c = Calendar.getInstance();
		int hafta = 0;
		int sonuc = 0;

		entMenu = dalMenu.SonOgleYemekGetir();
		//Log.d("#MONTHCHECK", entMenu.getAy() + " - " + ayBul(entMenu.getAy()) + " - " + c.get(Calendar.MONTH));

		if (forceUpdate == false && (entMenu != null && (ayBul(entMenu.getAy()) == c.get(Calendar.MONTH)))) {
			Log.d("#UPDATECHECK", "if");
		} else {
			Log.d("#UPDATECHECK", "else");
			Toast.makeText(appContext, "Güncelleme denetleniyor...", Toast.LENGTH_SHORT).show();
			
			try {
				doc = Jsoup.connect("https://googledrive.com/host/0Bxo4kmWq3ZluZEtaNzFub3JPcG8/ogle.html").get();
				baslik = doc.select("title"); //sayfanin ustundeki basligi cekiyor
				Log.d("#BASLIKOGLE", baslik.text());
				baslikStr = baslik.text().toLowerCase().split("\\s+");

				if (entMenu != null && entMenu.getAy().equals(baslikStr[1])) { //baslikta yazan ay ile veritabanindaki ay aynýysa guncelleme yapmiyor

					sonuc = 1;

				} else {

					sonuc = 2;
					Elements yemekler = doc.select("div"); //tarihi ve o gunku yemegi cekiyor

					for (Element x : yemekler) {
						Log.d("#OGLE", x.text());

						entMenu = new Menu();

						if (x.text().matches("\\d.*\\d.*\\d.*")) {
							yemekTarihi = x.text();
							String[] tarih = x.text().split("[\\.\\s*]");
							Calendar takvimHaftasi = Calendar.getInstance();
							takvimHaftasi.set(Integer.parseInt(tarih[2]), Integer.parseInt(tarih[1])-1, Integer.parseInt(tarih[0]));
							hafta = takvimHaftasi.get(Calendar.WEEK_OF_MONTH);
						} else {
							yemek = x.text();
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
							entMenu.setAy(baslikStr[1]);
							entMenu.setTur("ogle");
							entMenu.setTarih(yemekTarihi);
							entMenu.setHafta(hafta);
							entMenu.setMenu(yemek);
							entMenu.setSelected(false);
							dalMenu.MenuKaydet(entMenu);
							yemek = null;
							yemekTarihi = null;
						}
					}

					dalSevilmeyen.TumSevilmeyenGuncelle(1);
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				sonuc = -1;
				Toast.makeText(appContext, "Baðlantý hatasý", Toast.LENGTH_SHORT).show();
			}

			try {
				doc = null;
				baslik = null;
				baslikStr = null;
				doc = Jsoup.connect("https://googledrive.com/host/0Bxo4kmWq3ZluZEtaNzFub3JPcG8/aksam.html").get();
				baslik = doc.select("title"); //sayfanin ustundeki basligi cekiyor
				Log.d("#BASLIKAKSAM", baslik.text());
				baslikStr = baslik.text().toLowerCase().split("\\s+");
				Elements yemekler = doc.select("div"); //tarihi ve o gunku yemegi cekiyor

				for (Element x : yemekler) {
					Log.d("#AKSAM", x.text());

					entMenu = new Menu();

					if (x.text().matches("\\d.*\\d.*\\d.*")) {
						yemekTarihi = x.text();
						String[] tarih = x.text().split("[\\.\\s*]");
						Calendar takvimHaftasi = Calendar.getInstance();
						takvimHaftasi.set(Integer.parseInt(tarih[2]), Integer.parseInt(tarih[1])-1, Integer.parseInt(tarih[0]));
						hafta = takvimHaftasi.get(Calendar.WEEK_OF_MONTH);
					} else {
						yemek = x.text();
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
						entMenu.setAy(baslikStr[1]);
						entMenu.setTur("aksam");
						entMenu.setTarih(yemekTarihi);
						entMenu.setHafta(hafta);
						entMenu.setMenu(yemek);
						dalMenu.MenuKaydet(entMenu);
						yemek = null;
						yemekTarihi = null;
					}
				}

				dalSevilmeyen.TumSevilmeyenGuncelle(2);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				sonuc = -1;
				//Toast.makeText(appContext, "Baðlantý hatasý", Toast.LENGTH_SHORT).show();
			}

		}

		return sonuc;
	}

	protected void haftalikEskiKayitlariSil() {
		MenuDAL dalMenu = new MenuDAL(appContext);
		dalMenu.HaftalikEskiKayitlariSil();
	}

	protected void tumKayitlariSil(int x) {
		MenuDAL dalMenu = new MenuDAL(appContext);
		dalMenu.TumKayitlariSil(x);
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
