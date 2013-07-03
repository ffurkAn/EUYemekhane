package com.euyemekhane;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;

public class YemekListesi extends SherlockActivity {

	protected int yemekListesiIndir() {

		Document doc;
		Elements baslik;
		String[] baslikStr;
		String yemek = null;
		String yemekTarihi = null;
		Menu entMenu;
		MenuDAL dalMenu = new MenuDAL(this);
		SevilmeyenYemekDAL dalSevilmeyen = new SevilmeyenYemekDAL(this);
		final Calendar c = Calendar.getInstance();
		int hafta;
		int sonuc = 0;

		entMenu = dalMenu.SonOgleYemekGetir();
		//Log.d("#MONTHCHECK", entMenu.getAy() + " - " + ayBul(entMenu.getAy()) + " - " + c.get(Calendar.MONTH));

		if (entMenu != null && (ayBul(entMenu.getAy()) == c.get(Calendar.MONTH))) {
			//Log.d("#UPDATECHECK", "if");
		} else {
			//Log.d("#UPDATECHECK", "else");

			try {
				doc = Jsoup.connect("http://sksdb.ege.edu.tr/genel/oele-yemek-menuesue").get();
				baslik = doc.select("tr td p span"); //sayfanin ustundeki basligi cekiyor
				baslikStr = baslik.text().toLowerCase().split("\\s+");

				entMenu = dalMenu.SonOgleYemekGetir();
				if (entMenu != null && entMenu.getAy().equals(baslikStr[2])) { //baslikta yazan ay ile veritabanindaki ay aynýysa guncelleme yapmiyor

					sonuc = 1;

				} else {

					sonuc = 2;
					hafta = 1;
					dalMenu.TumKayitlariSil(1);
					Elements yemekler = doc.select("tr td p"); //tarihi ve o gunku yemegi cekiyor

					for (Element x : yemekler) {
						//Log.d("#OGLEYEMEK", x.text());

						entMenu = new Menu();

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
				Toast.makeText(getApplicationContext(), "Baðlantý hatasý", Toast.LENGTH_SHORT).show();
			}

			try {
				doc = null;
				baslik = null;
				baslikStr = null;
				doc = Jsoup.connect("http://sksdb.ege.edu.tr/genel/akam-yemek-menuesue").get();
				baslik = doc.select("tr td p span"); //sayfanin ustundeki basligi cekiyor
				baslikStr = baslik.text().toLowerCase().split("\\s+");

				entMenu = dalMenu.SonAksamYemekGetir();
				if (entMenu != null && entMenu.getAy().equals(baslikStr[2])) { //baslikta yazan ay ile veritabanindaki ay aynýysa guncelleme yapmiyor

					sonuc = 1;

				} else {

					sonuc = 2;
					hafta = 1;
					dalMenu.TumKayitlariSil(2);				
					Elements yemekler = doc.select("tr td p"); //tarihi ve o gunku yemegi cekiyor

					for (Element x : yemekler) {
						//Log.d("#AKSAMYEMEK", x.text());

						entMenu = new Menu();

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
				//Toast.makeText(getApplicationContext(), "Baðlantý hatasý", Toast.LENGTH_SHORT).show();
			}

		}

		return sonuc;
	}
	
	protected void haftalikEskiKayitlariSil() {
		MenuDAL dalMenu = new MenuDAL(this);
		dalMenu.HaftalikEskiKayitlariSil();
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
